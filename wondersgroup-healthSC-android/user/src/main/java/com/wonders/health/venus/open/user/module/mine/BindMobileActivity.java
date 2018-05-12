package com.wonders.health.venus.open.user.module.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;

/**
 * 个人设置
 * Created by zhangjingyang on 2016/11/7.
 */

public class BindMobileActivity extends BaseActivity implements View.OnClickListener {
    private TextView txt_get_authcode;
    private TextView txt_error_msg;
    private EditText et_mobile;
    private EditText et_authecode;
    private Button btn_submit;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_bind_mobile);
        mTitleBar.setTitle("绑定手机号");
        txt_get_authcode=(TextView)findViewById(R.id.txt_get_authcode);
        txt_error_msg=(TextView)findViewById(R.id.txt_error_msg);
        et_mobile=(EditText)findViewById(R.id.et_mobile);
        et_authecode=(EditText)findViewById(R.id.et_authecode);
        txt_get_authcode.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.txt_get_authcode:
              UserManager.getInstance().getVerifyCode(UserManager.getInstance().getUser().mobile,0,new ResponseCallback(){
                  @Override
                  public void onFinish() {
                      super.onFinish();
                      if(mSuccess){

                      }else{

                      }
                  }
              });
              break;
          case R.id.btn_submit:
              UserManager.getInstance().bindMobile("1234","18811111111","2345",new ResponseCallback<User>(){
                  @Override
                  public void onFinish() {
                      super.onFinish();
                      if(mSuccess){

                      }else{
                          txt_error_msg.setText(ERR_MSG);
                      }
                  }
              });
              break;
      }
    }

}
