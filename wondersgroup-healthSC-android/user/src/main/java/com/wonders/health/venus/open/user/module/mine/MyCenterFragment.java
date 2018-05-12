package com.wonders.health.venus.open.user.module.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.entity.event.AccountChangeEvent;
import com.wonders.health.venus.open.user.entity.event.UserUpdateEvent;
import com.wonders.health.venus.open.user.entity.event.VerificationEvent;
import com.wonders.health.venus.open.user.logic.AppConfigManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.mine.attention.MyAttentionActivity;
import com.wonders.health.venus.open.user.module.mine.auth.AuthChooseActivity;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.view.CircleImageView;
import com.wondersgroup.hs.healthcloud.common.view.pulltozoomview.PullToZoomScrollViewEx;

import de.greenrobot.event.EventBus;

/**
 * 类描述：个人中心
 * 创建人：tanghaihua
 * 创建时间：2016/11/7 13:40
 */
public class MyCenterFragment extends BaseFragment implements View.OnClickListener {
    private BitmapTools mBitmapTools;
    private CircleImageView mCiv_photo;
    private TextView mTv_name;
    private PullToZoomScrollViewEx mPullToZoomSv;

    private LinearLayout mOrder_ll;
    private LinearLayout mSetting;
    private ImageView mIv_auth_status;
    private LinearLayout mFavorite_ll;
    private LinearLayout mJkk_ll;
    private LinearLayout mYy_ll;
    private LinearLayout mHelp_ll;

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_center, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initViews() {
        mPullToZoomSv = (PullToZoomScrollViewEx) findViewById(R.id.zoom_sv);
        mPullToZoomSv.getScrollView().setVerticalScrollBarEnabled(false);
        mPullToZoomSv.setParallax(false);
        View headView = LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_mycenter_head, null, false);
        View zoomView = LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_mycenter_head_bg, null, false);
        View contentView = LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_mycenter_content, null, false);
        mPullToZoomSv.setHeaderView(headView);
        mPullToZoomSv.setZoomView(zoomView);
        mPullToZoomSv.setScrollContentView(contentView);

        mCiv_photo = (CircleImageView) headView.findViewById(R.id.civ_photo);
        mTv_name = (TextView) headView.findViewById(R.id.tv_name);
        mIv_auth_status = (ImageView) headView.findViewById(R.id.iv_auth_status);

        mFavorite_ll = (LinearLayout) contentView.findViewById(R.id.favorite_ll);
        mJkk_ll = (LinearLayout) contentView.findViewById(R.id.jkk_ll);
        mYy_ll = (LinearLayout) contentView.findViewById(R.id.yy_ll);
        mHelp_ll = (LinearLayout) contentView.findViewById(R.id.help_ll);
        mOrder_ll = (LinearLayout) contentView.findViewById(R.id.order_ll);
        mSetting = (LinearLayout) contentView.findViewById(R.id.setting);

        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mBaseActivity, UserInfoActivity.class));
            }
        });

        mOrder_ll.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        mFavorite_ll.setOnClickListener(this);
        mJkk_ll.setOnClickListener(this);
        mYy_ll.setOnClickListener(this);
        mHelp_ll.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBitmapTools = new BitmapTools(mBaseActivity);

        loadUserInfo();
    }


    private void loadUserInfo() {
        User user = UserManager.getInstance().getUser();
        if (user != null) {
            if (!TextUtils.isEmpty(user.avatar)) {
                if (!TextUtils.isEmpty(user.gender) && user.gender.equals("2")) {
                    new BitmapTools(mBaseActivity).display(mCiv_photo, user.avatar, R.mipmap.ic_user_woman);
                } else {
                    new BitmapTools(mBaseActivity).display(mCiv_photo, user.avatar, R.mipmap.ic_user_man);
                }
            } else {
                if (!TextUtils.isEmpty(user.gender) && user.gender.equals("2")) {
                    new BitmapTools(mBaseActivity).display(mCiv_photo, null, R.mipmap.ic_user_woman);
                } else {
                    new BitmapTools(mBaseActivity).display(mCiv_photo, null, R.mipmap.ic_user_man);
                }
            }

            String name = "";
            if (!TextUtils.isEmpty(user.name)) {
                name = user.name;
            } else if (!TextUtils.isEmpty(user.nickname)) {
                name = user.nickname;
            }
            mTv_name.setText(name.length() > 9 ? name.substring(0, 9) + "..." : name);

            mIv_auth_status.setImageResource(UserManager.getInstance().getVerifyState()
                    ? R.mipmap.ic_auth : R.mipmap.ic_no_auth);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_ll:
                startActivity(new Intent(mBaseActivity, MyOrderActivity.class));
                break;
            case R.id.setting:
                startActivity(new Intent(mBaseActivity, SettingActivity.class));
                break;
            case R.id.favorite_ll:
                startActivity(new Intent(mBaseActivity, MyAttentionActivity.class));
                break;
            case R.id.jkk_ll:
                //TODO

//                View dialog_view = View.inflate(mBaseActivity, R.layout.dialog_not_develop, null);
//                UIUtil.showAlert(mBaseActivity, dialog_view).setCanceledOnTouchOutside(true);
                if (UserManager.getInstance().getUser().verified) {
                    startActivity(new Intent(mBaseActivity, ResidentHealthCardActivity.class));
                } else {
                    Intent intent = new Intent(mBaseActivity, AuthChooseActivity.class);
                    intent.putExtra(AuthChooseActivity.WAY_FROM, 1);
                    intent.putExtra(AuthChooseActivity.EXTRA_URL, SchemeUtil.getUri(mBaseActivity, R.string.path_resident_health_card));
                    startActivity(intent);
                }
                break;
            case R.id.yy_ll:
                startActivity(new Intent(mBaseActivity, MyAppointmentActivity.class));
                break;
            case R.id.help_ll:
                if (AppConfigManager.getInstance().getAppConfig() != null && !TextUtils.isEmpty(AppConfigManager.getInstance().getAppConfig().helpCenter)) {
                    if (!TextUtils.isEmpty(UserManager.getInstance().getUser().uid)) {
                        SchemeUtil.startActivity(mBaseActivity, AppConfigManager.getInstance().getAppConfig().helpCenter + "?userId=" + UserManager.getInstance().getUser().uid + "&tel=" + UserManager.getInstance().getUser().mobile);
                    } else {
                        SchemeUtil.startActivity(mBaseActivity, AppConfigManager.getInstance().getAppConfig().helpCenter);
                    }
                }
                break;
        }
    }





    public void onEvent(UserUpdateEvent event) {
        loadUserInfo();
    }

    public void onEvent(AccountChangeEvent event) {
        if (event.isLogin) {
            loadUserInfo();
        } else {
            new BitmapTools(mBaseActivity).display(mCiv_photo, null, R.mipmap.ic_user_default);
            mTv_name.setText("");
            mIv_auth_status.setImageResource(R.mipmap.ic_no_auth);
        }
    }
}


