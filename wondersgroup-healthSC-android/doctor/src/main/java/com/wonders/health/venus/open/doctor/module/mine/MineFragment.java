package com.wonders.health.venus.open.doctor.module.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.AccountChangeEvent;
import com.wonders.health.venus.open.doctor.entity.User;
import com.wonders.health.venus.open.doctor.logic.UserManager;
import com.wonders.health.venus.open.doctor.module.TutorialActivity;
import com.wonders.health.venus.open.doctor.util.BitmapUtil;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.view.CircleImageView;

import de.greenrobot.event.EventBus;

/*
 * Created by sunning on 2017/6/2.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout rl_user;
    private ImageView iv_head_bg;
    private View mask_view;
    private CircleImageView iv_avatar;
    private LinearLayout ll_auth_status;
    private LinearLayout ll_mine_topic, signNum;
    private RelativeLayout rl_about, rl_setting;
    private BitmapTools mBitmapTools;

    private Bitmap blurredBitmap;


    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, null);
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
        if (blurredBitmap != null && !blurredBitmap.isRecycled()) {
            blurredBitmap.recycle();
            blurredBitmap = null;
        }
        System.gc();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    protected void initViews() {
        rl_user = (RelativeLayout) findViewById(R.id.rl_user);
        iv_head_bg = (ImageView) findViewById(R.id.iv_head_bg);
        mask_view = findViewById(R.id.mask_view);
        iv_avatar = (CircleImageView) findViewById(R.id.iv_avatar);
        ll_auth_status = (LinearLayout) findViewById(R.id.ll_auth_status);
        ll_mine_topic = (LinearLayout) findViewById(R.id.ll_mine_topic);
        signNum = (LinearLayout) findViewById(R.id.ll_mine_sign_num);
        rl_about = (RelativeLayout) findViewById(R.id.rl_about);
        rl_setting = (RelativeLayout) findViewById(R.id.rl_setting);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBitmapTools = new BitmapTools(mBaseActivity);
        showData();
        rl_user.setOnClickListener(this);
        ll_mine_topic.setOnClickListener(this);
        signNum.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
    }

    public void onEvent(AccountChangeEvent event) {
        showData();
    }


    private void showData() {
        mBitmapTools.display(iv_avatar, UserManager.getInstance().getUser().avatar, R.mipmap.ic_launcher);
        blurredBitmap = BitmapUtil.doBlur(mBaseActivity, BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher), 25f);
        if (blurredBitmap != null) {
            iv_head_bg.setImageBitmap(blurredBitmap);
            mask_view.setVisibility(View.VISIBLE);
        }
//            if(TextUtils.isEmpty(UserManager.getInstance().getUser().avatar)){
//                mask_view.setVisibility(View.VISIBLE);
//            }else{
//                mBitmapTools.load(UserMSanager.getInstance().getUser().avatar, new ResponseCallback<Drawable>() {
//                    @Override
//                    public void onSuccess(Drawable drawable) {
//                        super.onSuccess(drawable);
//                        blurredBitmap = BitmapUtil.doBlur(mBaseActivity, BitmapUtil.getBitmapFromDrawable(drawable), 25f);
//                        if (blurredBitmap != null) {
//                            iv_head_bg.setImageBitmap(blurredBitmap);
//                            mask_view.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });
//            }
        ll_auth_status.setVisibility(View.VISIBLE);
    }

    private void refreshData() {
        UserManager.getInstance().getUserInfo(new ResponseCallback<User>() {
            @Override
            public void onSuccess(User user) {
            }

            @Override
            public boolean isShowNotice() {
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_about:
                startActivity(new Intent(mBaseActivity, AboutActivity.class));
                break;
            case R.id.rl_setting:
//                startActivity(new Intent(mBaseActivity, SettingActivity.class));
                startActivity(new Intent(mBaseActivity, TutorialActivity.class));
                break;
            case R.id.ll_mine_sign_num:
                startActivity(new Intent(mBaseActivity, MyTeamActivity.class));
                break;

        }
    }
}
