package com.wonders.health.venus.open.user.module.home.registration;
/*
 * Created by sunning on 2016/11/10.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.RegisterHeadEntity;
import com.wonders.health.venus.open.user.entity.event.AttentDoctorUpdateEvent;
import com.wonders.health.venus.open.user.logic.RegistrationManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.mine.LoginActivity;
import com.wonders.health.venus.open.user.view.FadingHeadViewHelper;
import com.wonders.health.venus.open.user.view.ObservableScrollView;
import com.wondersgroup.hs.healthcloud.common.CommonActivity;
import com.wondersgroup.hs.healthcloud.common.logic.DialogResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import de.greenrobot.event.EventBus;


public class RegistrationDoctorHead {

    private boolean isAttention;
    private FadingHeadViewHelper headViewHelper;

    View getHeadView(final Context mContext,  final RegisterHeadEntity entity) {
        final View headView = LayoutInflater.from(mContext).inflate(R.layout.register_doctor_detail_header, null);
        if (!entity.isInit) {
            headView.findViewById(R.id.tv_item_doctor_detail_root).setVisibility(View.GONE);
            View back = headView.findViewById(R.id.actionbar_back);
            marginTopByStatusBar(mContext, back);
            setBackBtn(back, mContext);
            return headView;
        }
        ImageView headImg = (ImageView) headView.findViewById(R.id.iv_item_doctor_head_img);
        if (entity.from == RegisterHeadEntity.From.DOCTOR_DETAIL) {
            headView.findViewById(R.id.tv_item_doctor_detail_hospital).setVisibility(View.GONE);
            ((TextView) headView.findViewById(R.id.tv_item_doctor_detail_value)).setText(entity.orderCount);
            isAttention = entity.isAttention;
            if (entity.scrollView != null) {
                if (entity.scrollView.getParent() != null) {
                    RelativeLayout root = (RelativeLayout) entity.scrollView.getParent();
                    ViewGroup viewGroup = (ViewGroup) root.findViewById(R.id.fading_head);
                    final View fadingView = LayoutInflater.from(mContext).inflate(R.layout.fading_head_layout, viewGroup);
                    headViewHelper = new FadingHeadViewHelper(fadingView, mContext.getResources().getDrawable(R.color.bc1));
                    final TextView attentionText = (TextView) fadingView.findViewById(R.id.actionbar_attention);
                    final TextView doctorName = (TextView) fadingView.findViewById(R.id.doctor_name_title);
                    final ImageView attentionIcon = (ImageView) fadingView.findViewById(R.id.actionbar_attention_icon);
                    final ImageView back = (ImageView) fadingView.findViewById(R.id.actionbar_back);
                    setBackBtn(back, mContext);
                    setAttention(isAttention, attentionText, attentionIcon, entity.hosDoctCode);
                    fadingView.findViewById(R.id.attention_ll).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(UserManager.getInstance().getUser().uid)) {
                                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                                return;
                            }
                            RegistrationManager.getInstance().setAttention(entity.hosDoctCode, new DialogResponseCallback<String>((CommonActivity) mContext) {
                                @Override
                                public void onSuccess(String s) {
                                    super.onSuccess(s);
                                    setAttention(!isAttention, attentionText, attentionIcon,entity.hosDoctCode);
                                }
                            });
                        }
                    });
                    headView.findViewById(R.id.actionbar_back).setVisibility(View.INVISIBLE);
                    final int marginValue = marginTopByStatusBar(mContext, fadingView);
                    entity.scrollView.setOnScrollListener(new ObservableScrollView.OnScrollListener() {
                        @Override
                        public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldX, int oldY) {
                            int titleViewHeight = fadingView.getHeight();
                            float progress = headViewHelper.onChange(headView.getHeight() - titleViewHeight - marginValue, y);
                            int pro = (int) (255 - (headViewHelper.getAlpha() * .7));
                            if (progress < .3) {
                                doctorName.setAlpha(0);
                            } else {
                                doctorName.setAlpha(progress);
                            }
                            iconColorFilter(Color.argb(255, pro, pro, pro), back.getDrawable(), attentionIcon.getDrawable(), doctorName, attentionText);
                        }
                    });
                }
            }
        } else {
            TextView hospitalName = (TextView) headView.findViewById(R.id.tv_item_doctor_detail_hospital);
            hospitalName.setVisibility(View.VISIBLE);
            hospitalName.setText(entity.hospitalName);
            headView.findViewById(R.id.tv_item_doctor_detail_root).setVisibility(View.GONE);
            View back = headView.findViewById(R.id.actionbar_back);
            marginTopByStatusBar(mContext, back);
            setBackBtn(back, mContext);
            headImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RegistrationDoctorDetailActivity.class);
                    intent.putExtra(DoctorScheduleActivity.EXTRA_DEPT_CODE, entity.hosDeptCode);
                    intent.putExtra(DoctorScheduleActivity.EXTRA_DOCTOR_CODE, entity.hosDoctCode);
                    intent.putExtra(DoctorScheduleActivity.EXTRA_HOSPITAL_CODE, entity.hosOrgCode);
                    ((CommonActivity)mContext).startActivity(intent,true);
                }
            });

        }
        ((TextView) headView.findViewById(R.id.tv_item_doctor_detail_name)).setText(entity.doctorName);
        TextView titleView = (TextView) headView.findViewById(R.id.tv_item_doctor_detail_title);
        if (TextUtils.isEmpty(entity.doctorTitle)) {
            titleView.setVisibility(View.GONE);
        } else {
            titleView.setText(entity.doctorTitle);
        }
        new BitmapTools(mContext).display(headImg, entity.doctorHead, getGenderHeadRes(entity.gender));
        return headView;
    }

    private int marginTopByStatusBar(final Context mContext, View view) {
        int statusBarHeight = 0;
        if (SystemUtil.isTintStatusBarAvailable(((CommonActivity) mContext))) {
            statusBarHeight = SystemUtil.getStatusBarHeight();
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
            }
//            else {
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                layoutParams.topMargin = statusBarHeight;
//            }
            view.setPadding(0, statusBarHeight, 0, 0);
        }
        return statusBarHeight ;
    }

    private void setBackBtn(View back, final Context context) {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CommonActivity) context).finish();
            }
        });
        UIUtil.setTouchEffect(back);
    }

    /**
     * 1男2女
     *
     * @return 男-女 res
     */
    public static int getGenderHeadRes(String gender) {
        if (TextUtils.isEmpty(gender)) {
            return R.mipmap.ic_doctor_default_man;
        } else {
            if (gender.equals("2")) {
                return R.mipmap.ic_doctor_default_woman;
            }
            return R.mipmap.ic_doctor_default_man;
        }
    }

    private void setAttention(boolean isAttention, TextView attentionText, ImageView attentionIcon, String doctorId) {
        this.isAttention = isAttention;
        if (isAttention) {
            attentionText.setText("已关注");
            attentionIcon.setImageResource(R.mipmap.ic_attentioned);
        } else {
            attentionText.setText("未关注");
            attentionIcon.setImageResource(R.mipmap.ic_unattention);
        }
        EventBus.getDefault().post(new AttentDoctorUpdateEvent(isAttention,doctorId));
    }

    /*
     * 颜色改变
     */
    private void iconColorFilter(int color, Drawable back, Drawable attentionBtn, TextView doctorName, TextView attentionStatus) {
        PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        back.setColorFilter(colorFilter);
        doctorName.setTextColor(color);
        if(!isAttention){
            attentionBtn.setColorFilter(colorFilter);
        }
        attentionStatus.setTextColor(color);
    }
}
