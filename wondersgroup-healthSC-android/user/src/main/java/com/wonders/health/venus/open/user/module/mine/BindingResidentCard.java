package com.wonders.health.venus.open.user.module.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.util.DialogUtils;
import com.wonders.health.venus.open.user.util.PatternUtils;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;

/**
 * Created by win10 on 2018/4/14.
 */

public class BindingResidentCard extends BaseActivity {

    private User mMUser = null;
    private TextView mTv_name_add;
    private TextView mTv_idcard;
    private EditText mEt_number;
    private Button mBtn_next;

    @Override
    protected void initViews() {
        mMUser = UserManager.getInstance().getUser();
        mTitleBar.setTitle("绑定居民健康卡");
        setContentView(R.layout.activity_resident_card_add);
        mTv_name_add = (TextView) findViewById(R.id.tv_name_add);
        mTv_idcard = (TextView) findViewById(R.id.tv_idcard);
        mEt_number = (EditText) findViewById(R.id.et_number);
        mBtn_next = (Button) findViewById(R.id.btn_next);
        mTv_name_add.setText(mMUser.name);
        mTv_idcard.setText(PatternUtils.hiddenForIdCard(mMUser.idcard != null ? mMUser.idcard : "", 3, 4));
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
                    UserManager.getInstance().createCardTWO(cardNumber, "02", new ResponseCallback<UserCardsEntity>() {
                        @Override
                        public boolean isShowNotice() {
                            return false;
                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                        }

                        @Override
                        public void onSuccess(UserCardsEntity userCardsEntity) {
                            super.onSuccess(userCardsEntity);
                            DialogUtils.showNormalMessage(BindingResidentCard.this, "绑卡成功");
                            setResult(1002);
                            finish();
                        }

                        @Override
                        public void onFailure(Exception error) {
                            super.onFailure(error);
                            DialogUtils.showNormalMessage(BindingResidentCard.this, null != error && null != error.getMessage() ? error.getMessage() : "绑卡失败");
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
