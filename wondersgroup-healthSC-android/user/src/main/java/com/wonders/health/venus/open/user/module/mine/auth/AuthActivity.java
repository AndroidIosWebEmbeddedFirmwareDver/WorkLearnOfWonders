package com.wonders.health.venus.open.user.module.mine.auth;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.component.upload.UploadFile;
import com.wonders.health.venus.open.user.entity.AuthStatus;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.logic.AppConfigManager;
import com.wonders.health.venus.open.user.logic.AuthManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.entity.PhotoModel;
import com.wondersgroup.hs.healthcloud.common.logic.PhotoManager;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BaseConstant;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.photopick.PhotoPicker;

import java.util.List;

/**
 * Created by wang on 2016/8/11 15:54.
 * Class Name :AuthActivity
 * 实名认证信息提交页
 */
public class AuthActivity extends BaseActivity implements View.OnClickListener {

    public static final String IS_RE_AUTH = "is_re_auth";

    private String isReAuth;

    private EditText etName;
    private EditText etCardID;
    private ImageView ivPic;
    private TextView tvSample;
    private Button btnSubmit;
    private TextView tvRequiredTip;
    private LinearLayout llClause;

    private String mName;
    private String mCardID;
    private String mPicPath;
    private String filePath;

    private AuthStatus entity;

    private PhotoPicker mPhotoPicker;

    AuthStatus authStatus;

    private BitmapTools bitmapTools;
    private boolean isAddPic;


    private Dialog dialog;
    private Dialog dialog2;


    @Override
    protected void initViews() {

        mTitleBar.setTitle("实名认证");
        setContentView(R.layout.activity_auth);
        etName = (EditText) findViewById(R.id.et_real_name);
        etCardID = (EditText) findViewById(R.id.et_cardID);
        ivPic = (ImageView) findViewById(R.id.iv_add_pic);
        ivPic.setOnClickListener(this);
        tvSample = (TextView) findViewById(R.id.tv_sample);
        tvSample.setOnClickListener(this);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        tvRequiredTip = (TextView) findViewById(R.id.tv_info_required_tip);
        llClause = (LinearLayout) findViewById(R.id.ll_clause);
        llClause.setOnClickListener(this);

        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPhotoPicker = new PhotoPicker(this);
        bitmapTools = new BitmapTools(this);
        authStatus = new AuthStatus();
        isReAuth = getIntent().getStringExtra(IS_RE_AUTH);
        setHints();
    }

    private void setHints() {
        EditText[] mArray = new EditText[]{etName, etCardID};
        final String[] hints = new String[]{etName.getHint().toString(), etCardID.getHint().toString()};
        for (int i = 0; i < mArray.length; i++) {
            final int j = i;

            //EditText 获得焦点时hint消失，失去焦点时hint显示
            mArray[j].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (hasFocus) {
                        ((TextView) v).setHint("");
                    } else {
                        ((TextView) v).setHint(hints[j]);

                    }
                }
            });
        }
    }
    private void verify() {
        mName = etName.getText().toString();
        mCardID = etCardID.getText().toString();
        if (TextUtils.isEmpty(mName)) {
//            UIUtil.toastShort(this, "请填写姓名");
            tvRequiredTip.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(mCardID)) {
//            UIUtil.toastShort(this, "请填写身份证号");
            tvRequiredTip.setVisibility(View.VISIBLE);
            return;
        }
//        if (mCardID.length() != 18) {
//            UIUtil.toastShort(this, "身份证号有误，请重新填写");
//            return;
//        }
//        if (mCardID.length() == 18 && StringUtil.getAgeByIdCard(mCardID) == -1) {
//            UIUtil.toastShort(this, "身份证号有误，请重新填写");
//            return;
//        }
        if (!isAddPic) {
            UIUtil.toastShort(this, "请上传图片");
            tvRequiredTip.setVisibility(View.GONE);
            return;
        }

    }


    private void postData() {
        verify();
        if (!TextUtils.isEmpty(mName)
                && !TextUtils.isEmpty(mCardID)
                && isAddPic) {
            tvRequiredTip.setVisibility(View.GONE);
            new UploadFile(AuthActivity.this).uploadImage(filePath, new ResponseCallback<String>() {
                @Override
                public void onStart() {
                    super.onStart();
                    dialog=UIUtil.showProgressBar(AuthActivity.this, "图片上传中");
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    btnSubmit.setEnabled(false);

                }

                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    if (t != null) {
                        mPicPath = t;
                        postData2();
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    dialog.dismiss();
                    super.onFailure(e);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    btnSubmit.setEnabled(false);
//                    UIUtil.hideProgressBar(AuthActivity.this);
                }
            });
        }
    }

    private void postData2() {
        UserManager.getInstance().postAuthInfo(mName, mCardID, mPicPath, new ResponseCallback<String>() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.dismiss();
                dialog2=UIUtil.showProgressBar(AuthActivity.this, "上传中");
                dialog2.setCancelable(false);
                dialog2.setCanceledOnTouchOutside(false);
                btnSubmit.setEnabled(false);

            }

            @Override
            public void onCancelled() {
                super.onCancelled();
                btnSubmit.setEnabled(true);
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                setData();
                UserManager.getInstance().setVerifyState(AuthStatus.verify_checking + "");
                dialog4Auth();
            }

            @Override
            public void onFinish() {
//                UIUtil.hideProgressBar(AuthActivity.this);
                dialog2.dismiss();
                btnSubmit.setEnabled(true);
                super.onFinish();
            }

            @Override
            public boolean isShowNotice() {
                if (mSuccess) {
                    return false;
                } else {
                    return true;
                }

            }
        });
    }

    private void setData() {
        entity = authStatus.getAuthStatus(AuthStatus.verify_checking, mName, mCardID);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sample:
//                startActivity(new Intent(this, AuthSampleActivity.class));
                dialog4Sample();
                break;
            case R.id.iv_add_pic:
                mPhotoPicker.setMaxCount(1);
                mPhotoPicker.setRequestWH(1920, 1920);
                mPhotoPicker.pickPhoto();
                break;
            case R.id.ll_clause:
                if (AppConfigManager.getInstance().getAppConfig() != null && !TextUtils.isEmpty(AppConfigManager.getInstance().getAppConfig().realNameRule)) {
                    SchemeUtil.startActivity(AuthActivity.this, AppConfigManager.getInstance().getAppConfig().realNameRule);
                }
                break;
            case R.id.btn_submit:
                postData();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == BaseConstant.REQUEST_CODE_PICK) {
                mPhotoPicker.dealResult(requestCode, resultCode, data, new PhotoManager.OnLocalRecentListener() {
                    @Override
                    public void onPhotoLoaded(final List<PhotoModel> photos) {
                        bitmapTools.display(ivPic, photos.get(0).getThumbPath());
                        filePath = photos.get(0).getThumbPath();
                        isAddPic = true;
                    }
                });
            }

        }
    }

    public void dialog4Auth() {
        View view3 = LayoutInflater.from(this).inflate(R.layout.layout_auth_success, null);
        TextView tvConfirm = (TextView) view3.findViewById(R.id.tv_confirm);
        final Dialog dialog1 = UIUtil.showAlert(this, view3);
        dialog1.setCancelable(false);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                AuthActivity.this.finish();
                UserManager.getInstance().getUserInfo(new ResponseCallback<User>());
            }
        });
    }

    private void dialog4Sample() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_auth_sample, null);
        TextView tvClose = (TextView) view.findViewById(R.id.tv_close);
        final Dialog dialog = UIUtil.showAlert(this, view);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(etName.getText().toString())
                || !TextUtils.isEmpty(etCardID.getText().toString())
                || !TextUtils.isEmpty(mPicPath)) {
            UIUtil.showConfirm(this, null, "是否放弃实名认证", "确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AuthActivity.this.finish();
                }
            }, "取消", null).setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });
        } else {
            finish();
        }
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }
}
