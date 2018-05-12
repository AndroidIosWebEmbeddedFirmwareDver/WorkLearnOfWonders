package com.wonders.health.venus.open.user.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ExtractHospitalEntity;
import com.wonders.health.venus.open.user.logic.ExtractManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.home.extractreport.ExtractAdapter;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.ClearEditText;

import java.util.ArrayList;
import java.util.List;


/**
 * 类描述：诊间支付
 * 创建人：tanghaihua
 * 创建时间：2016/1/1 14:30
 */
public class PayClinicFragment extends BaseFragment implements View.OnClickListener, ExtractHospitalAdapter.ItemSelectListener {

    private BaseRecyclerView mRecycle_search;
    private Button mBtn_confirm;
    private ClearEditText mEt_nickname;
    private View mLine_blue;
    private LinearLayout ll_container;
    private BaseRecyclerView recyclerView;

    private List<ExtractHospitalEntity> mData = new ArrayList<ExtractHospitalEntity>();
    private ExtractAdapter mExtractAdapter;
    private LinearLayout mLl_search;
    private ExtractManager mExtractManager;
    private ExtractHospitalEntity mCurrentHospital;
    private boolean mIsItemClick;

    private List<ExtractHospitalEntity> mExtractData = new ArrayList<>();
    private ExtractHospitalAdapter extractHospitalAdapter;

    //从查询历史中选择
    private boolean isHistoryItemSelect;


    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payclinic, null, false);
    }

    @Override
    protected void initViews() {
        mBtn_confirm = (Button) findViewById(R.id.btn_confirm);
        mRecycle_search = (BaseRecyclerView) findViewById(R.id.recycle_search);
        mLl_search = (LinearLayout) findViewById(R.id.ll_search);
        mEt_nickname = (ClearEditText) findViewById(R.id.et_nickname);
        mLine_blue = (View) findViewById(R.id.line_blue);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        mBtn_confirm.setOnClickListener(this);
        addView();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (TextUtils.isEmpty(mEt_nickname.getText().toString())) {
            mBtn_confirm.setEnabled(false);
        }

        mExtractManager = new ExtractManager();
        mEt_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mIsItemClick) {
                    if (mEt_nickname.getText().length() > 0) {
                        if (!isHistoryItemSelect) {
                            mBtn_confirm.setVisibility(View.GONE);
                            mLine_blue.setVisibility(View.VISIBLE);
                            mLl_search.setVisibility(View.VISIBLE);
                            loadData();
                        }
                        mBtn_confirm.setEnabled(true);

                    } else {
                        isHistoryItemSelect=false;
                        mCurrentHospital = null;
                        mBtn_confirm.setEnabled(false);
                        mBtn_confirm.setVisibility(View.VISIBLE);
                        mLine_blue.setVisibility(View.GONE);
                        mLl_search.setVisibility(View.GONE);
                    }
                } else {
                    mIsItemClick = false;
                }
            }
        });

        mRecycle_search.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {

            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                ExtractHospitalEntity hospital = mData.get(position);
                if (hospital != null) {
                    mIsItemClick = true;

                    mCurrentHospital = hospital;
                    mEt_nickname.setText(hospital.hospitalName);
                    mEt_nickname.setSelection(mEt_nickname.getText().length());
                    mBtn_confirm.setVisibility(View.VISIBLE);
                    mLine_blue.setVisibility(View.GONE);
                    mLl_search.setVisibility(View.GONE);

                    //本地存储查询医院历史
                    isHistoryItemSelect=false;
//                    putPref();
//                    setHospitalAdapter();
                }
            }
        });
    }

    private void loadData() {
        final String key = mEt_nickname.getText().toString();
        mExtractManager.searchRecommend(key, new FinalResponseCallback<ExtractHospitalEntity>(mLl_search) {
            @Override
            public void onSuccess(List<ExtractHospitalEntity> list) {
                super.onSuccess(list);
                mData.clear();
                if (list != null && list.size() > 0) {
                    mData.addAll(list);
                }

                if (mLl_search.getVisibility() == View.VISIBLE) {
                    setIsEmpty(mData.isEmpty());
                }
                if (mExtractAdapter == null) {
                    mExtractAdapter = new ExtractAdapter(mBaseActivity, mData, key);
                    mRecycle_search.setAdapter(mExtractAdapter);
                } else {
                    mExtractAdapter.changeItemColor(key);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (mCurrentHospital != null) {
                    putPref();
                    startActivity(new Intent(mBaseActivity, PayClinicActivity.class)
                            .putExtra(PayClinicActivity.EXTRA_HOSPITAL_CODE, mCurrentHospital.hospitalCode));
                }
                break;
        }
    }

    //添加查询历史view
    private void addView() {
        View view = LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_extract_hospital, null);
        ll_container.addView(view);
        recyclerView = (BaseRecyclerView) view.findViewById(R.id.recycle_hospital);
        getPref();
        setHospitalAdapter();
    }

    //设置查询历史adapter
    private void setHospitalAdapter() {
        if (extractHospitalAdapter == null) {
            extractHospitalAdapter = new ExtractHospitalAdapter(mBaseActivity, mExtractData, this);
            recyclerView.setAdapter(extractHospitalAdapter);
        } else {
            extractHospitalAdapter.notifyDataSetChanged();
        }
    }

    //得到本地存储的查询历史数据
    private void getPref() {
        List<ExtractHospitalEntity> temList = PrefUtil.getJsonArray(mBaseActivity, getUID() + Constant.KEY_PAY_HOSPITAL_INFO, ExtractHospitalEntity.class);
        if (temList != null && temList.size() > 0) {
            mExtractData.clear();
            mExtractData.addAll(temList);
        }
    }

    //本地存储查询历史数据
    private void putPref() {
        getPref();
        if(extractHospitalAdapter!=null){
            extractHospitalAdapter.putPref(mExtractData,mCurrentHospital,Constant.KEY_PAY_HOSPITAL_INFO);
        }
        setHospitalAdapter();
    }

    private String getUID() {
        return UserManager.getInstance().getUser().uid;
    }

    @Override
    public void onItemSelect(int position) {
        mIsItemClick=false;
        isHistoryItemSelect = true;
        mCurrentHospital = mExtractData.get(position);
        mEt_nickname.setText(mExtractData.get(position).hospitalName);


    }

}
