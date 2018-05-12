package com.wonders.health.venus.open.user.module.home.extractreport;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ExtractHospitalEntity;
import com.wonders.health.venus.open.user.entity.ExtractTimeEntity;
import com.wonders.health.venus.open.user.logic.ExtractManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.home.ExtractHospitalAdapter;
import com.wonders.health.venus.open.user.module.home.elecprescription.PrescriptionItemActivity;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2016/11/10.
 */

public class ExtractActivity extends BaseActivity implements View.OnClickListener, ExtractHospitalAdapter.ItemSelectListener {

    public static final String TYPE_REPORT = "1";//类型：提取报告
    public static final String TYPE_PRESCRIPTION = "2";//类型：电子处方
    public static final String EXTRACT_TYPE = "type";//类型
    public String type;//入口类型


    private static final int REQUEST_CODE = 001;
    public static final String KEY_EXTRACT_ENTITY = "key_extract_entity";


    private EditText et_hospital_name;
    private View view_line, rl_date;
    private TextView tv_date_choose;
    private BaseRecyclerView recycle_search;
    private LinearLayout ll_search;
    private Button btn_extract;
    private LinearLayout ll_container;
    private BaseRecyclerView recyclerView;

    private ExtractAdapter adapter;
    private List<ExtractHospitalEntity> mData = new ArrayList<>();
    private List<ExtractHospitalEntity> mExtractData = new ArrayList<>();

    private ExtractTimeEntity timeEntity;

    private ExtractManager mExtractManager;

    private ExtractHospitalEntity mHospitalEntity;

    private ExtractHospitalAdapter extractHospitalAdapter;

    private boolean isItemSelect;


    @Override
    protected void initViews() {
        setContentView(R.layout.activity_extract);
        findViews();
        type = getIntent().getStringExtra(EXTRACT_TYPE);
        if (TYPE_REPORT.equals(type)) {
            mTitleBar.setTitle("提取报告");
            mTitleBar.setActionTextColor(getResources().getColor(R.color.tc1));
            mTitleBar.addAction(new TitleBar.TextAction("规则") {
                @Override
                public void performAction(View view) {
                    showWaringDialog();
                }
            });
            btn_extract.setText("提取报告");
            ll_container.setVisibility(View.VISIBLE);
            //时间默认为今天
            setTimeText("今天", "0");

        } else {
            mTitleBar.setTitle("电子处方");
            btn_extract.setText("提取");
            ll_container.setVisibility(View.GONE);
        }
        addView();
        et_hospital_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                view_line.setBackgroundColor(Color.parseColor("#2e7af0"));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_hospital_name.getText().length() > 0) {
                    if (!isItemSelect) {
                        ll_search.setVisibility(View.VISIBLE);
                        rl_date.setVisibility(View.GONE);
                        btn_extract.setVisibility(View.GONE);
                        loadData(et_hospital_name.getText().toString().trim());
                    }

                } else {
                    mHospitalEntity = null;
                    isItemSelect=false;
                    ll_search.setVisibility(View.GONE);
                    rl_date.setVisibility(View.VISIBLE);
                    btn_extract.setVisibility(View.VISIBLE);
                }
            }
        });
        tv_date_choose.setOnClickListener(this);
        btn_extract.setOnClickListener(this);

    }

    private void findViews() {
        et_hospital_name = (EditText) findViewById(R.id.et_hospital_name);
        view_line = findViewById(R.id.view_line);
        tv_date_choose = (TextView) findViewById(R.id.tv_date_choose);
        ll_search = (LinearLayout) findViewById(R.id.ll_search);
        recycle_search = (BaseRecyclerView) findViewById(R.id.recycle_search);
        btn_extract = (Button) findViewById(R.id.btn_extract);
        rl_date = findViewById(R.id.rl_date);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
    }

    //添加查询历史view
    private void addView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_extract_hospital, null);
        ll_container.addView(view);
        recyclerView = (BaseRecyclerView) view.findViewById(R.id.recycle_hospital);
        getPref();
        setHospitalAdapter();
    }

    //设置查询历史adapter
    private void setHospitalAdapter() {
        if (extractHospitalAdapter == null) {
            extractHospitalAdapter = new ExtractHospitalAdapter(this, mExtractData, this);
            recyclerView.setAdapter(extractHospitalAdapter);
        } else {
            extractHospitalAdapter.notifyDataSetChanged();
        }
    }

    //得到本地存储的查询历史数据
    private void getPref() {
        List<ExtractHospitalEntity> temList = PrefUtil.getJsonArray(this, getUID() + Constant.KEY_EXTRACT_HOSPITAL_INFO, ExtractHospitalEntity.class);
        if (temList != null && temList.size() > 0) {
            mExtractData.clear();
            mExtractData.addAll(temList);
        }
    }

    //本地存储查询历史数据
    private void putPref() {
        getPref();
        if (extractHospitalAdapter != null){
            extractHospitalAdapter.putPref(mExtractData, mHospitalEntity, Constant.KEY_EXTRACT_HOSPITAL_INFO);
        }
        setHospitalAdapter();
    }

    private String getUID() {
        return UserManager.getInstance().getUser().uid;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        mExtractManager = new ExtractManager();
        recycle_search.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                mHospitalEntity = mData.get(position);
                isItemSelect = false;
                et_hospital_name.setText(mHospitalEntity.hospitalName);
                et_hospital_name.setSelection(et_hospital_name.getText().length());
                et_hospital_name.clearFocus();
                ll_search.setVisibility(View.GONE);
                rl_date.setVisibility(View.VISIBLE);
                btn_extract.setVisibility(View.VISIBLE);
//                //本地存储查询医院历史
//                putPref();
//                setHospitalAdapter();
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_date_choose:
                if (TYPE_PRESCRIPTION.equals(type)) {
                    intent = new Intent(this, ExtractTimeActivity.class);
                    intent.putExtra(ExtractTimeActivity.EXT_TIME, timeEntity);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    showTimeDialog();
                }

                break;
            case R.id.btn_extract:
                if (mHospitalEntity != null && timeEntity != null) {
                    //本地存储查询医院历史
                    putPref();
                    Intent intent1;
                    if (TYPE_REPORT.equals(type)) {
                        intent1 = new Intent(this, ExtractReportActivity.class);
                    } else {
                        intent1 = new Intent(this, PrescriptionItemActivity.class);
                    }
                    intent1.putExtra(ExtractReportActivity.KEY_DAY, timeEntity.code);
                    intent1.putExtra(ExtractReportActivity.KEY_HOSPITAL, mHospitalEntity);
                    startActivity(intent1);
                } else {
                    UIUtil.toastShort(this, "提交的信息不完善，无法查询");
                }
                break;
        }
    }

    private void showWaringDialog() {
        UIUtil.showWarningAlert(this, getString(R.string.extract_dialog_text), "知道啦");
    }

    private void showTimeDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_extract_time, null);
        RadioButton rb_today = (RadioButton) dialogView.findViewById(R.id.rb_today);
        RadioButton rb_last_month = (RadioButton) dialogView.findViewById(R.id.rb_last_month);
        RadioButton rb_last_sex_month = (RadioButton) dialogView.findViewById(R.id.rb_last_sex_month);
        TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
        final Dialog dialog = UIUtil.showBottomMenu(this, dialogView);
        rb_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeText("今天", "0");
                dialog.dismiss();
            }
        });
        rb_last_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeText("最近一个月", "1");
                dialog.dismiss();
            }
        });
        rb_last_sex_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeText("最近六个月", "2");
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void setTimeText(String name, String code) {
        if (timeEntity == null) {
            timeEntity = new ExtractTimeEntity();
        }
        timeEntity.name = name;
        timeEntity.code = code;
        tv_date_choose.setText(timeEntity.name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                timeEntity = (ExtractTimeEntity) data.getSerializableExtra(KEY_EXTRACT_ENTITY);
                if (timeEntity != null) {
                    tv_date_choose.setText(timeEntity.name);
                }
            }
        }

    }

    private void loadData(final String key) {
        mExtractManager.searchRecommend(key, new FinalResponseCallback<ExtractHospitalEntity>(ll_search, Constant.TYPE_INIT) {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<ExtractHospitalEntity> list) {
                super.onSuccess(list);
                mData.clear();
                if (list != null && list.size() > 0) {
                    mData.addAll(list);
                }
                if (rl_date.getVisibility() != View.VISIBLE) {
                    setIsEmpty(mData.isEmpty());
                }
                if (adapter == null) {
                    adapter = new ExtractAdapter(ExtractActivity.this, mData, key);
                    recycle_search.setAdapter(adapter);
                } else {
                    adapter.changeItemColor(key);
                }
            }
        });
    }

    //查询历史点击事件
    @Override
    public void onItemSelect(int position) {
        isItemSelect = true;
        mHospitalEntity = mExtractData.get(position);
        et_hospital_name.setText(mExtractData.get(position).hospitalName);


    }


    @Override
    protected boolean needCheckLogin() {
        return true;
    }

}
