package com.wonders.health.venus.open.user.module.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.logic.AppConfigManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.entity.BaseResponse;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.RSAUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wondersgroup.hs.healthcloud.common.http.HttpMethod.HEAD;

/**
 * 重置密码
 * Created by zhangjingyang on 2016/11/7.
 */

public class ModifyPwdActivity extends BaseActivity implements View.OnClickListener {
    private TextView txt_pwd_error_msg;
    private EditText et_original_pwd;
    private EditText et_new_pwd,et_new_pwd1;
    private Button btn_submit;
    private ImageView mIvSeeOriginalPwd,mIvSeeNewPwd,mIvSeeNewPwd1;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_modify_pwd);
        mTitleBar.setTitle("修改密码");
        txt_pwd_error_msg = (TextView)findViewById(R.id.txt_pwd_error_msg);
        txt_pwd_error_msg.setVisibility(View.INVISIBLE);
        et_original_pwd=(EditText)findViewById(R.id.et_original_pwd);
        et_new_pwd=(EditText)findViewById(R.id.et_new_pwd);
        et_new_pwd1=(EditText)findViewById(R.id.et_new_pwd1);
        mIvSeeOriginalPwd=(ImageView)findViewById(R.id.iv_see_original_pwd);
        mIvSeeNewPwd=(ImageView)findViewById(R.id.iv_see_new_pwd);
        mIvSeeNewPwd1=(ImageView)findViewById(R.id.iv_see_new_pwd1);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        mIvSeeOriginalPwd.setOnClickListener(this);
        mIvSeeNewPwd.setOnClickListener(this);
        mIvSeeNewPwd1.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.btn_submit:
              if(pwdCheck()){
                  txt_pwd_error_msg.setVisibility(View.INVISIBLE);
                  String originalPwd="",newPwd="";
                  try {
                      String key = AppConfigManager.getInstance().getAppConfig().publicKey;
                      originalPwd = RSAUtil.encryptByPublicKey(et_original_pwd.getText().toString(), key);
                      newPwd = RSAUtil.encryptByPublicKey(et_new_pwd.getText().toString(), key);
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
                  UserManager.getInstance().updatePassword(originalPwd,newPwd,new ResponseCallback(){
                      @Override
                      public void onResponse(BaseResponse response) {
                          super.onResponse(response);
                          if(mSuccess){
                              UserManager.getInstance().logout(new ResponseCallback<User>(){
                                  @Override
                                  public void onSuccess(User user) {
                                      super.onSuccess(user);
                                      startActivity(new Intent(ModifyPwdActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                      finish();
                                  }

                                  @Override
                                  public boolean isShowNotice() {
                                      return false;
                                  }
                              });
                          }else{
                              //txt_pwd_error_msg.setVisibility(View.VISIBLE);
                              //txt_pwd_error_msg.setText(response.msg);
                          }
                      }
                  });
              }
              break;
          case R.id.iv_see_original_pwd:
              mIvSeeOriginalPwd.setSelected(!mIvSeeOriginalPwd.isSelected());
              int index = et_original_pwd.getSelectionStart();
              et_original_pwd.setTransformationMethod(
                      mIvSeeOriginalPwd.isSelected() ? HideReturnsTransformationMethod.getInstance()
                              : PasswordTransformationMethod.getInstance());
              et_original_pwd.setSelection(index);
              break;
          case R.id.iv_see_new_pwd:
              mIvSeeNewPwd.setSelected(!mIvSeeNewPwd.isSelected());
              int indexNew = et_new_pwd.getSelectionStart();
              et_new_pwd.setTransformationMethod(
                      mIvSeeNewPwd.isSelected() ? HideReturnsTransformationMethod.getInstance()
                              : PasswordTransformationMethod.getInstance());
              et_new_pwd.setSelection(indexNew);
              break;
          case R.id.iv_see_new_pwd1:
              mIvSeeNewPwd1.setSelected(!mIvSeeNewPwd1.isSelected());
              int indexNew1 = et_new_pwd1.getSelectionStart();
              et_new_pwd1.setTransformationMethod(
                      mIvSeeNewPwd1.isSelected() ? HideReturnsTransformationMethod.getInstance()
                              : PasswordTransformationMethod.getInstance());
              et_new_pwd1.setSelection(indexNew1);
              break;
      }
    }

    /**
     * 输入校验
     * @return
     */
    public boolean pwdCheck(){
        if(TextUtils.isEmpty(et_original_pwd.getText().toString())){
            txt_pwd_error_msg.setVisibility(View.VISIBLE);
            txt_pwd_error_msg.setText("请输入原密码");
            return false;
        }else if(!pwdFormatCheck(et_original_pwd)){
            txt_pwd_error_msg.setVisibility(View.VISIBLE);
            txt_pwd_error_msg.setText("原密码格式不符，请重新输入（长度6-16位，字母与数字组合）");
            return false;
        }else if(TextUtils.isEmpty(et_new_pwd.getText().toString())){
            txt_pwd_error_msg.setVisibility(View.VISIBLE);
            txt_pwd_error_msg.setText("请输入新密码");
            return false;
        }else if(!pwdFormatCheck(et_new_pwd)){
            txt_pwd_error_msg.setVisibility(View.VISIBLE);
            txt_pwd_error_msg.setText("新密码格式不符，请重新输入（长度6-16位，字母与数字组合）");
            return false;
        }else if(TextUtils.isEmpty(et_new_pwd1.getText().toString())){
            txt_pwd_error_msg.setVisibility(View.VISIBLE);
            txt_pwd_error_msg.setText("请输入确认密码");
            return false;
        } else if(!et_new_pwd.getText().toString().equals(et_new_pwd1.getText().toString())){
            txt_pwd_error_msg.setVisibility(View.VISIBLE);
            txt_pwd_error_msg.setText("确认密码不一致，请重新输入");
            return false;
        }else {
            return true;
        }
    }

    /**
     * 校验密码格式（长度6-16位，字母与数字组合）
     * @return
     */
    public boolean pwdFormatCheck(EditText et){
        Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
        Matcher isNum = pattern.matcher(et.getText().toString());
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
