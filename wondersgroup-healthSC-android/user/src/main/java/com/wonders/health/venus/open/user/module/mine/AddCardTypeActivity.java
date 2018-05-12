package com.wonders.health.venus.open.user.module.mine;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.AuthStatus;
import com.wonders.health.venus.open.user.entity.HospitalInfo;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.MainActivity;
import com.wonders.health.venus.open.user.module.home.registration.HospitalListActivity;
import com.wonders.health.venus.open.user.util.DialogUtils;
import com.wonders.health.venus.open.user.util.PatternUtils;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;

import java.util.List;

/**
 * Created by win10 on 2018/4/14.
 * 添加卡类型  1.居民卡  2 医院就诊卡
 */

public class AddCardTypeActivity extends BaseActivity {

    public final static int EXTRA_KEY_CODE = 102;

    private TextView mTv_name_add;
    private TextView mTv_idcard;
    private TextView mTv_select_hospital;
    private EditText mEt_number;
    private Button mBtn_next;

    private User mUser;
    private HospitalInfo.Hospital hospital;


    @Override
    protected void initViews() {

        mUser = UserManager.getInstance().getUser();
        mTitleBar.setTitle("绑定医院就诊卡");
        setContentView(R.layout.activity_see_card_add);
        mTv_name_add = (TextView) findViewById(R.id.tv_name_add);
        mTv_idcard = (TextView) findViewById(R.id.tv_idcard);
        mTv_select_hospital = (TextView) findViewById(R.id.tv_select_hospital);
        mEt_number = (EditText) findViewById(R.id.et_number);
        mBtn_next = (Button) findViewById(R.id.btn_next);
        mTv_name_add.setText(mUser.name);
        mTv_idcard.setText(PatternUtils.hiddenForIdCard(mUser.idcard != null ? mUser.idcard : "", 3, 4));

        mTv_select_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCardTypeActivity.this, HospitalListActivity.class);
                intent.putExtra("JUMP_TAG", "AddCardTypeActivity");
                startActivityForResult(intent, AddCardTypeActivity.EXTRA_KEY_CODE);
            }
        });

        mEt_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mBtn_next.setBackgroundColor(Color.parseColor("#E1E1E1"));
                } else {
                    mBtn_next.setBackgroundColor(getResources().getColor(R.color.tc_addtion2));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBtn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cardNumber = mEt_number.getText().toString().trim();
                if (!TextUtils.isEmpty(cardNumber)) {
                    if (hospital == null) {
                        Toast.makeText(AddCardTypeActivity.this, "医院为空", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    UserManager.getInstance().createCard(hospital.hospitalCode, hospital.hospitalId, hospital.hospitalName, cardNumber, "01", new ResponseCallback<CardsTypeEntity>() {
                        @Override
                        public boolean isShowNotice() {
                            return false;
                        }

                        @Override
                        public void onSuccess(CardsTypeEntity cardTypeEntity) {
                            super.onSuccess(cardTypeEntity);
                            DialogUtils.showNormalMessage(AddCardTypeActivity.this, "绑卡成功");
                            setResult(1001);
                            finish();
                        }

                        @Override
                        public void onFailure(Exception error) {
                            super.onFailure(error);
                            DialogUtils.showNormalMessage(AddCardTypeActivity.this, null != error && null != error.getMessage() ? error.getMessage() : "绑卡失败");
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (resultCode) {
            case AddCardTypeActivity.EXTRA_KEY_CODE:
                hospital = (HospitalInfo.Hospital) intent.getSerializableExtra("hospital");
                if (hospital != null)
                    mTv_select_hospital.setText(hospital.hospitalName);
                break;
            default:
        }
    }


}
