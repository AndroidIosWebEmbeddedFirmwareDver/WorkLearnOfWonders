package com.wonders.health.venus.open.user.module.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.component.upload.UploadFile;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.entity.event.UserUpdateEvent;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.mine.auth.AuthChooseActivity;
import com.wondersgroup.hs.healthcloud.common.entity.PhotoModel;
import com.wondersgroup.hs.healthcloud.common.logic.PhotoManager;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.DateUtil;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.CircleImageView;
import com.wondersgroup.hs.healthcloud.common.view.photopick.PhotoPicker;
import com.wondersgroup.hs.healthcloud.common.view.wheelview.TimePickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 类描述：个人信息
 * 创建人：tanghaihua
 * 创建时间：11/7/16 8:06 PM
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private SimpleDateFormat mAgeFormat;
    private LinearLayout mLl_avator;
    private CircleImageView mIv_avator;
    private LinearLayout mLl_name;
    private TextView mTv_name;
    private LinearLayout mLl_phone;
    private TextView mTv_phone;
    private LinearLayout mLl_gender;
    private TextView mTv_gender;
    private LinearLayout mLl_auth;
    private TextView mTv_auth;
    private LinearLayout mLl_age;
    private TextView mTv_age;
    private BitmapTools mBitmapTools;
    private PhotoPicker mPhotoPicker;
    private ImageView mIv_name_arrow;
    private ImageView mIv_gender_arrow;
    private ImageView mIv_age_arrow;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_userinfo);

        mLl_avator = (LinearLayout) findViewById(R.id.ll_avator);
        mIv_avator = (CircleImageView) findViewById(R.id.iv_avator);
        mLl_name = (LinearLayout) findViewById(R.id.ll_name);
        mTv_name = (TextView) findViewById(R.id.tv_name);
        mIv_name_arrow = (ImageView) findViewById(R.id.iv_name_arrow);
        mLl_phone = (LinearLayout) findViewById(R.id.ll_phone);
        mTv_phone = (TextView) findViewById(R.id.tv_phone);
        mLl_gender = (LinearLayout) findViewById(R.id.ll_gender);
        mTv_gender = (TextView) findViewById(R.id.tv_gender);
        mIv_gender_arrow = (ImageView) findViewById(R.id.iv_gender_arrow);
        mLl_age = (LinearLayout) findViewById(R.id.ll_age);
        mTv_age = (TextView) findViewById(R.id.tv_age);
        mIv_age_arrow = (ImageView) findViewById(R.id.iv_age_arrow);
        mLl_auth = (LinearLayout) findViewById(R.id.ll_auth);
        mTv_auth = (TextView) findViewById(R.id.tv_auth);

        mLl_avator.setOnClickListener(this);
        mLl_name.setOnClickListener(this);
        mLl_gender.setOnClickListener(this);
        mLl_age.setOnClickListener(this);
        mLl_auth.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTitleBar.setTitle("个人信息");

        mBitmapTools = new BitmapTools(this);
        mPhotoPicker = new PhotoPicker(this);
        mPhotoPicker.setMaxCount(1);
        mPhotoPicker.setNeedCrop(true);
        mPhotoPicker.setRequestWH(500, 500);

        mAgeFormat = new SimpleDateFormat("yyyy-MM-dd");

        loadUserInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取用户数据
        UserManager.getInstance().getUserInfo(new ResponseCallback<User>() {
            @Override
            public boolean isShowNotice() {
                return false;
            }
        });
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }

    private void loadUserInfo(){
        User user = UserManager.getInstance().getUser();
        if (user != null) {
            if (!TextUtils.isEmpty(user.avatar)) {
                new BitmapTools(this).display(mIv_avator, user.avatar, R.mipmap.ic_user_default);
            }else{
                if (!TextUtils.isEmpty(user.gender) && user.gender.equals("2")) {
                    new BitmapTools(this).display(mIv_avator, null, R.mipmap.ic_user_woman);
                } else {
                    new BitmapTools(this).display(mIv_avator, null, R.mipmap.ic_user_man);
                }
            }

            String name = "";
            if (!TextUtils.isEmpty(user.name)) {
                name = user.name;
            } else if (!TextUtils.isEmpty(user.nickname)) {
                name = user.nickname;
            }
            mTv_name.setText(name.length() > 9 ? name.substring(0, 9) + "..." : name);

            if ("1".equals(UserManager.getInstance().getUser().gender)) {  //本人为男
                mTv_gender.setText("男");
            } else if ("2".equals(UserManager.getInstance().getUser().gender)) {
                mTv_gender.setText("女");
            } else {
                mTv_gender.setText("男");
            }
            mTv_phone.setText(StringUtil.getCheckedMobile(user.mobile));
            if(!TextUtils.isEmpty(UserManager.getInstance().getUser().age)){
                mTv_age.setText(UserManager.getInstance().getUser().age + "岁");
            }else{
                mTv_age.setText("28岁");
            }

            //实名认证状态 0-未提交,1-认证失败,2-审核中,3-认证成功
            if("0".equals(UserManager.getInstance().getUser().verificationStatus)){
                mTv_auth.setText("未认证");
                mTv_auth.setBackgroundColor(getResources().getColor(R.color.transparent));
                mLl_name.setEnabled(true);
                mLl_gender.setEnabled(true);
                mLl_age.setEnabled(true);
                mIv_name_arrow.setVisibility(View.VISIBLE);
                mIv_gender_arrow.setVisibility(View.VISIBLE);
                mIv_age_arrow.setVisibility(View.VISIBLE);
            }else if("1".equals(UserManager.getInstance().getUser().verificationStatus)){
                mTv_auth.setText("");
                mTv_auth.setBackgroundResource(R.mipmap.ic_auth_fail);
                mLl_name.setEnabled(true);
                mLl_gender.setEnabled(true);
                mLl_age.setEnabled(true);
                mIv_name_arrow.setVisibility(View.VISIBLE);
                mIv_gender_arrow.setVisibility(View.VISIBLE);
                mIv_age_arrow.setVisibility(View.VISIBLE);
            }else if("2".equals(UserManager.getInstance().getUser().verificationStatus)){
                mTv_auth.setText("");
                mTv_auth.setBackgroundResource(R.mipmap.ic_auth_in);
                mLl_name.setEnabled(false);
                mLl_gender.setEnabled(false);
                mLl_age.setEnabled(false);
                mIv_name_arrow.setVisibility(View.GONE);
                mIv_gender_arrow.setVisibility(View.GONE);
                mIv_age_arrow.setVisibility(View.GONE);
            }else if("3".equals(UserManager.getInstance().getUser().verificationStatus)){
                mTv_auth.setText("");
                mTv_auth.setBackgroundResource(R.mipmap.ic_auth_succes);
                mLl_name.setEnabled(false);
                mLl_gender.setEnabled(false);
                mLl_age.setEnabled(false);
                mIv_name_arrow.setVisibility(View.GONE);
                mIv_gender_arrow.setVisibility(View.GONE);
                mIv_age_arrow.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_avator:
                if (mPhotoPicker != null) {
                    mPhotoPicker.pickPhoto();
                }
                break;
            case R.id.ll_name:
                intent = new Intent(UserInfoActivity.this, SetNameActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_gender:
                View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_sex, null);
                RadioButton rb_man = (RadioButton)dialogView.findViewById(R.id.rb_man);
                RadioButton rb_woman = (RadioButton)dialogView.findViewById(R.id.rb_woman);
                TextView tv_cancel = (TextView)dialogView.findViewById(R.id.tv_cancel);

                if("女".equals(mTv_gender.getText())){
                    rb_woman.setChecked(true);
                }else{
                    rb_man.setChecked(true);
                }

                final Dialog dialog = UIUtil.showBottomMenu(this, dialogView);
                rb_man.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        modifyGender("男");
                        dialog.dismiss();
                    }
                });
                rb_woman.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        modifyGender("女");
                        dialog.dismiss();
                    }
                });
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                break;
            case R.id.ll_age:
                TimePickerView dv = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
                try {
                    Calendar start = Calendar.getInstance();
                    start.set(Calendar.YEAR, start.get(Calendar.YEAR) - 100);
                    Calendar current = Calendar.getInstance();
                    current.set(Calendar.YEAR, current.get(Calendar.YEAR) - 28);
                    dv.setRange(start, Calendar.getInstance());
                    if(UserManager.getInstance().getUser().birthday != null){
                        dv.setTime(mAgeFormat.parse(UserManager.getInstance().getUser().birthday));
                    }else{
                        dv.setTime(current.getTime());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dv.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        modifyBirthday(DateUtil.parseDateByFormat(date, DateUtil.DATE_FORMAT));
                    }
                });
                dv.show();
                break;
            case R.id.ll_auth:
                startActivity(new Intent(this, AuthChooseActivity.class));
                break;
        }
    }

    /**
     * 修改性别
     * @param selectedText
     */
    private void modifyGender(final String selectedText) {
        final String gender = "男".equals(selectedText) ? "1" : "2";

        User user = UserManager.getInstance().getUser();
        user.gender = gender;
        UserManager.getInstance().updateUser(user, new ResponseCallback<User>() {
            @Override
            public void onStart() {
                super.onStart();
                UIUtil.showProgressBar(UserInfoActivity.this);
            }

            @Override
            public void onSuccess(User user) {
                super.onSuccess(user);
                mTv_gender.setText(selectedText);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if(!isFinishing()){
                    UIUtil.hideProgressBar(UserInfoActivity.this);
                }
            }
        });
    }

    /**
     * 修改年龄
     * @param birthday
     */
    private void modifyBirthday(final String birthday) {
        User user = UserManager.getInstance().getUser();
        user.birthday = birthday;
        UserManager.getInstance().updateUser(user, new ResponseCallback<User>() {
            @Override
            public void onStart() {
                super.onStart();
                UIUtil.showProgressBar(UserInfoActivity.this);
            }

            @Override
            public void onSuccess(User user) {
                super.onSuccess(user);
                mTv_age.setText(UserManager.getInstance().getUser().age + "岁");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if(!isFinishing()){
                    UIUtil.hideProgressBar(UserInfoActivity.this);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            if (mPhotoPicker != null) {
                mPhotoPicker.dealResult(requestCode, resultCode, intent, new PhotoManager.OnLocalRecentListener() {
                    @Override
                    public void onPhotoLoaded(final List<PhotoModel> photos) {
                        if (photos != null && !photos.isEmpty()) {
                            new UploadFile(UserInfoActivity.this).postUserAvaTar(photos.get(0).getThumbPath(), new ResponseCallback<User>() {
                                @Override
                                public void onStart() {
                                    super.onStart();
                                    UIUtil.showProgressBar(UserInfoActivity.this, "头像上传中");
                                }

                                @Override
                                public void onSuccess(User user) {
                                    super.onSuccess(user);
                                    UIUtil.toastShort(UserInfoActivity.this, "上传成功");
                                    mBitmapTools.display(mIv_avator, user.avatar, R.mipmap.ic_user_default);
                                }

                                @Override
                                public void onFinish() {
                                    super.onFinish();
                                    UIUtil.hideProgressBar(UserInfoActivity.this);
                                }

                                @Override
                                public boolean isShowNotice() {
                                    return !mSuccess;
                                }
                            });
                        }

                    }
                });
            }
        }
    }

    public void onEvent(UserUpdateEvent event) {
        loadUserInfo();
    }
}
