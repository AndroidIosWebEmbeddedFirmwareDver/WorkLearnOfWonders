package com.wonders.health.venus.open.user.module.msg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.EvaluateEntity;
import com.wonders.health.venus.open.user.entity.MsgEntity;
import com.wonders.health.venus.open.user.entity.event.MessageEvent;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.health.EvaluateAdapter;
import com.wonders.health.venus.open.user.module.health.HospitalHomeActivity;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/*
 * 消息首页
 * Created by zhangjingyang on 2016/11/8.
 */
public class MessageFragment extends BaseFragment implements View.OnClickListener{
    private RelativeLayout rl_system_msg;
    private RelativeLayout rl_pay_msg;
    private TextView txt_sys_content;
    private TextView txt_sys_title;
    private TextView txt_pay_title;
    private TextView txt_sys_count;
    private TextView txt_sys_time;
    private TextView txt_pay_content;
    private TextView txt_pay_count;
    private TextView txt_pay_time;
    private PullToRefreshView mPullToRefreshView;
    private BaseRecyclerView mRecycleView;
    private View mHeader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_msg_home, null);
    }

    @Override
    protected void initViews() {
        mHeader = LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_message_header, null);
        rl_system_msg=(RelativeLayout)mHeader.findViewById(R.id.rl_system_msg);
        rl_pay_msg=(RelativeLayout)mHeader.findViewById(R.id.rl_pay_msg);
        txt_sys_title=(TextView)mHeader.findViewById(R.id.txt_sys_title);
        txt_pay_title=(TextView)mHeader.findViewById(R.id.txt_pay_title);
        txt_sys_content=(TextView)mHeader.findViewById(R.id.txt_sys_content);
        txt_sys_count=(TextView)mHeader.findViewById(R.id.txt_sys_count);
        txt_sys_time=(TextView)mHeader.findViewById(R.id.txt_sys_time);
        txt_pay_content=(TextView)mHeader.findViewById(R.id.txt_pay_content);
        txt_pay_count=(TextView)mHeader.findViewById(R.id.txt_pay_count);
        txt_pay_time=(TextView)mHeader.findViewById(R.id.txt_pay_time);

        rl_system_msg.setOnClickListener(this);
        rl_pay_msg.setOnClickListener(this);

        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.refresh_view);
        mRecycleView = (BaseRecyclerView) findViewById(R.id.recycle_view);

        mPullToRefreshView.setLoadMoreEnable(false);
        mPullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                loadData(Constant.TYPE_NEXT);
            }
        });
        mRecycleView.addHeader(mHeader);
        List mList = new ArrayList();
        SystemMsgAdapter mAdapter = new SystemMsgAdapter(mBaseActivity, mList);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_system_msg:
                startActivity(new Intent(mBaseActivity,SystemMsgListActivity.class));
                break;
            case R.id.rl_pay_msg:
                startActivity(new Intent(mBaseActivity,PayMsgListActivity.class));
                break;
        }
    }
    public void loadData(int Type){
        UserManager.getInstance().getNewMsg(new FinalResponseCallback<MsgEntity>(this, Type){
            @Override
            public void onSuccess(MsgEntity msgNewEntity) {
                super.onSuccess(msgNewEntity);
                ArrayList<MsgEntity.message> msgs = (ArrayList)msgNewEntity.getList();
                for(MsgEntity.message msg:msgs){
                    if("1".equals(msg.type)){
                        if(msg.count>0) {
                            txt_sys_count.setVisibility(View.VISIBLE);
                            txt_sys_count.setText(msg.count>99?"...":(""+msg.count));
                        }else{
                            txt_sys_count.setVisibility(View.GONE);
                        }
                        txt_sys_content.setText(TextUtils.isEmpty(msg.content)?"暂无消息":msg.content);
                        txt_sys_title.setText(TextUtils.isEmpty(msg.name)?"系统消息":msg.name);
                        txt_sys_time.setText(msg.date);
                    }else{
                        if(msg.count>0) {
                            txt_pay_count.setVisibility(View.VISIBLE);
                            txt_pay_count.setText(msg.count>99?"...":(""+msg.count));
                        }else{
                            txt_pay_count.setVisibility(View.GONE);
                        }
                        txt_pay_content.setText(TextUtils.isEmpty(msg.content)?"暂无消息":msg.content);
                        txt_pay_title.setText(TextUtils.isEmpty(msg.name)?"支付通知":msg.name);
                        txt_pay_time.setText(msg.date);
                    }
                }
            }

            @Override
            public void onReload() {
                super.onReload();
                loadData(Constant.TYPE_INIT);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mPullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public boolean isShowNotice() {
                return false;
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser&&UserManager.getInstance().isLogin()) {
                loadData(mPullToRefreshView==null?Constant.TYPE_INIT:Constant.TYPE_RELOAD);
        }
    }

    public void onEvent(MessageEvent event) {
        if(event.mMsgListDestroy && UserManager.getInstance().isLogin()) {
            loadData(Constant.TYPE_RELOAD);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
