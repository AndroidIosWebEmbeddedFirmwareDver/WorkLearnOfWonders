package com.wonders.health.venus.open.doctor.module.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.PatientInfoList;
import com.wonders.health.venus.open.doctor.logic.SignManager;
import com.wonders.health.venus.open.doctor.logic.UserManager;
import com.wonders.health.venus.open.doctor.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by wang on 2017/6/5.
 */

public class SignPatientFragment extends BaseFragment {

    @BindView(R.id.pull_view)
    PullToRefreshView pullToRefreshView;
    @BindView(R.id.recycler_view)
    BaseRecyclerView recyclerView;

    private String tag;
    private SignPatientAdapter adapter;
    private PatientInfoList infoList;
    private SignManager signManager;

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_patient,null,false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag=getArguments().getString(SignPatientActivity.SIGN_TYPE);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        loadData(Constant.TYPE_INIT);
    }

    private void loadData(int type){
        Map<String,String> params = null;
        signManager=new SignManager();
        signManager.queryPatientList(params,tag,new FinalResponseCallback<PatientInfoList>(this,type){
            @Override
            public void onSuccess(PatientInfoList patientInfoList) {
                super.onSuccess(patientInfoList);
                infoList=patientInfoList;
                if(adapter==null){
                    adapter=new SignPatientAdapter(mBaseActivity,infoList.getList());
                    recyclerView.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Exception error) {
                super.onFailure(error);
            }
        });
    }

}
