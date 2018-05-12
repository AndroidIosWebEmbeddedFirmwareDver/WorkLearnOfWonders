package com.wonders.health.venus.open.user.module.health;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.EvaluateEntity;
import com.wonders.health.venus.open.user.entity.HospitalInfo;
import com.wonders.health.venus.open.user.entity.event.AttentHospitalUpdateEvent;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.WebViewActivity;
import com.wonders.health.venus.open.user.module.home.registration.DepartmentSelectActivity;
import com.wonders.health.venus.open.user.module.mine.LoginActivity;
import com.wonders.health.venus.open.user.module.mine.attention.AttentionManager;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.entity.BaseResponse;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by zjy on 2016/11/8.
 */

public class HospitalHomeActivity extends BaseActivity implements View.OnClickListener, PullToRefreshView.OnHeaderRefreshListener {
    public static final String FROM_HOSPITAL_HOME = "FROM_HOSPITAL_HOME";
    private int alpha = 0;
    private String hospitalId;
    private HospitalInfo.Hospital hospitalInfo;

    private View rl_title_bar, title_bar_line;
    private TextView txt_attention, txt_title;
    private View mHeader;
    private ImageView img_back;
    private ImageView img_hospital_img;
    private TextView txt_hospital_name;
    private TextView txt_reservation_count;
    private TextView txt_hospital_level;
    private TextView txt_address;
    private TextView txt_evaluate_count;
    private TextView txt_evaluate_more;
    private ImageView img_phone;
    private LinearLayout ll_overview;
    private LinearLayout ll_reservation;
    private LinearLayout ll_doctor;
    private RelativeLayout ll_guide;
    private LinearLayout ll_add_evaluate;
    private RelativeLayout rl_evaluate_more;
    private Dialog evaluateDialog;
    private EditText et_evaluate;
    private TextView txt_submit_evaluate;
    private String mHospitalPhone;

    private List<EvaluateEntity.Evaluate> mEvaluateList = new ArrayList<EvaluateEntity.Evaluate>();
    private EvaluateAdapter mEvaluateAdapter;

    private PullToRefreshView mPullToRefreshView;
    private BaseRecyclerView mRecycleView;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_hospital_home);

        initMTitleBar();
        initHeaderView();
        initRecycleView();
    }

    /*
     * 初始化titlebar
     */
    public void initMTitleBar() {
        rl_title_bar = findViewById(R.id.rl_title_bar);
        title_bar_line = findViewById(R.id.title_bar_line);
        txt_attention = (TextView) findViewById(R.id.txt_attention);
        txt_title = (TextView) findViewById(R.id.txt_title);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        txt_attention.setOnClickListener(this);
        if (!UserManager.getInstance().isLogin()) {
            txt_attention.setText("未关注");
            txt_attention.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_attention_no, 0, 0, 0);
        }

        if (SystemUtil.isTintStatusBarAvailable(this)) {
            rl_title_bar.setPadding(0, SystemUtil.getStatusBarHeight(), 0, 0);
        }
    }


    /**
     * 初始化头部View
     */
    private void initHeaderView() {
        mHeader = LayoutInflater.from(HospitalHomeActivity.this).inflate(R.layout.layout_hospital_home_header, null);

        img_hospital_img = (ImageView) mHeader.findViewById(R.id.img_hospital_img);
        txt_hospital_name = (TextView) mHeader.findViewById(R.id.txt_hospital_name);
        txt_reservation_count = (TextView) mHeader.findViewById(R.id.txt_reservation_count);
        txt_hospital_level = (TextView) mHeader.findViewById(R.id.txt_hospital_level);
        txt_address = (TextView) mHeader.findViewById(R.id.txt_address);
        txt_evaluate_count = (TextView) mHeader.findViewById(R.id.txt_evaluate_count);
        txt_evaluate_more = (TextView) mHeader.findViewById(R.id.txt_evaluate_more);
        img_phone = (ImageView) mHeader.findViewById(R.id.img_phone);
        ll_overview = (LinearLayout) mHeader.findViewById(R.id.ll_overview);
        ll_reservation = (LinearLayout) mHeader.findViewById(R.id.ll_reservation);
        ll_doctor = (LinearLayout) mHeader.findViewById(R.id.ll_doctor);
        ll_guide = (RelativeLayout) mHeader.findViewById(R.id.ll_guide);
        rl_evaluate_more = (RelativeLayout) mHeader.findViewById(R.id.rl_evaluate_more);
        ll_add_evaluate = (LinearLayout) findViewById(R.id.ll_add_evaluate);

        ll_add_evaluate.setOnClickListener(this);
        rl_evaluate_more.setOnClickListener(this);
        img_phone.setOnClickListener(this);
        ll_overview.setOnClickListener(this);
        ll_reservation.setOnClickListener(this);
        ll_doctor.setOnClickListener(this);
        ll_guide.setOnClickListener(this);

    }

    /**
     * 初始化RecycleView
     */
    public void initRecycleView() {
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.refresh_view);
        mRecycleView = (BaseRecyclerView) findViewById(R.id.recycle_view);

        mPullToRefreshView.setLoadMoreEnable(false);
        mPullToRefreshView.setOnHeaderRefreshListener(HospitalHomeActivity.this);

        mEvaluateAdapter = new EvaluateAdapter(HospitalHomeActivity.this, mEvaluateList);
        mRecycleView.setAdapter(mEvaluateAdapter);

        mRecycleView.addHeader(mHeader);

        scrollTitleBar();
    }

    /**
     * titleBar动画
     */
    public void scrollTitleBar() {
        final ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.bc1));
        drawable.setAlpha(0);
        rl_title_bar.setBackgroundDrawable(drawable);
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int titleBarheight = getResources().getDimensionPixelSize(com.wondersgroup.hs.healthcloud.common.R.dimen.actionbar_height);
                if (mHeader.getHeight() != 0 && rl_title_bar != null) {
                    float delta = -(float) mHeader.getTop() / titleBarheight;
                    delta = delta > 1 ? 1 : delta;
                    //drawable.setAlpha((int) (delta * 255));
                    //rl_title_bar.setBackgroundDrawable(drawable);
//                    img_back.setAlpha(1-delta);
//                    txt_title.setAlpha(1-delta);
//                    txt_attention.setAlpha(1-delta);
//                    if(delta>0.8){
//                        img_back.setEnabled(false);
//                        txt_attention.setEnabled(false);
//                    }else{
//                        img_back.setEnabled(true);
//                        txt_attention.setEnabled(true);
//                    }
                    alpha = 10 + (int) (delta * 245);
                    String alphaStr = Integer.toHexString(alpha);
                    alphaStr = alphaStr.length() == 1 ? "0" + alphaStr : alphaStr;
                    if (alpha > 20) {
                        txt_title.setTextColor(Color.parseColor("#" + alphaStr + "666666"));
                        txt_attention.setTextColor(Color.parseColor("#" + alphaStr + "666666"));
                        img_back.setImageResource(R.mipmap.ic_back);
                        title_bar_line.setVisibility(View.VISIBLE);
                        title_bar_line.setBackgroundColor(getResources().getColor(R.color.divider_color));
                        if ("未关注".equals(txt_attention.getText().toString())) {
                            txt_attention.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_attention_no_gray, 0, 0, 0);
                        }
                    } else {
                        txt_title.setTextColor(Color.parseColor("#ffffffff"));
                        txt_attention.setTextColor(Color.parseColor("#ffffffff"));
                        title_bar_line.setVisibility(View.GONE);//.setBackgroundColor(getResources().getColor(R.color.bc1 ));
                        img_back.setImageResource(R.mipmap.ic_back_light);
                        if ("未关注".equals(txt_attention.getText().toString())) {
                            txt_attention.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_attention_no, 0, 0, 0);
                        }
                    }
//                    rl_title_bar.invalidate();
                    drawable.setAlpha((int) (delta * 255));
                    rl_title_bar.setBackgroundDrawable(drawable);
                }
            }
        });
        mPullToRefreshView.setOnPullBaseScrollChangedListener(new PullToRefreshView.OnPullBaseScrollChanged() {
            @Override
            public void onPullBaseScrollChanged(int l, int t, int oldl, int oldt) {
                if (t < 0 && rl_title_bar.getVisibility() == View.VISIBLE) {
                    AlphaAnimation animation = new AlphaAnimation(1, 0);
                    animation.setDuration(300);
                    rl_title_bar.clearAnimation();
                    rl_title_bar.startAnimation(animation);
                    rl_title_bar.setVisibility(View.GONE);
                } else if (t >= 0 && rl_title_bar.getVisibility() == View.GONE) {
                    AlphaAnimation animation = new AlphaAnimation(0, 1);
                    animation.setDuration(300);
                    rl_title_bar.clearAnimation();
                    rl_title_bar.startAnimation(animation);
                    rl_title_bar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        hospitalId = getIntent().getStringExtra("hospitalId");
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        loadHospitalDate(Constant.TYPE_NEXT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_attention:
                attention();
                break;
            case R.id.ll_overview:
                Intent intent = new Intent(HospitalHomeActivity.this, HospitalDescActivity.class);
                intent.putExtra("img", hospitalInfo.hospitalPhoto);
                intent.putExtra("name", hospitalInfo.hospitalName);
                intent.putExtra("level", hospitalInfo.hospitalGrade);
                intent.putExtra("desc", hospitalInfo.hospitalDesc);
                startActivity(intent);
                break;
            case R.id.ll_reservation://预约挂号
            case R.id.ll_doctor://科室医生
                if (UserManager.getInstance().isLogin()) {
                    Intent intent2 = new Intent(HospitalHomeActivity.this, DepartmentSelectActivity.class);
                    intent2.putExtra(DepartmentSelectActivity.HOS_ORG_CODE, hospitalInfo.hospitalCode);
                    intent2.putExtra(DepartmentSelectActivity.HOS_ORG_ID, hospitalInfo.hospitalId + "");
                    intent2.putExtra(FROM_HOSPITAL_HOME, true);
                    this.startActivity(intent2, true);
                } else {
                    startActivity(new Intent(HospitalHomeActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
                break;
            case R.id.ll_guide:
                new WebViewActivity.Builder(this).setUrl("http://amc.huimeionline.com/?key=E156F227CF8ACC79F19CA744A0A0AD73#/index").create();
                break;
            case R.id.rl_evaluate_more:
                Intent intent1 = new Intent(HospitalHomeActivity.this, EvaluateListActivity.class);
                intent1.putExtra(EvaluateListActivity.EVALUATE_TARGET_ID, hospitalId);
                intent1.putExtra(EvaluateListActivity.EVALUATE_TYPE, EvaluateListActivity.EVALUATE_TYPE_HOSPITAL);
                if (null != hospitalInfo) {
                    intent1.putExtra(EvaluateListActivity.EVALUATE_COUNT, hospitalInfo.evaluateCount + "");
                }
                startActivity(intent1);
                break;
            case R.id.img_phone:
                UIUtil.showAlert(HospitalHomeActivity.this, null, mHospitalPhone, "拨打", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SchemeUtil.callPhone(HospitalHomeActivity.this, mHospitalPhone);
                    }
                });
                break;
            case R.id.ll_add_evaluate:
                addEvaluateView();
                break;
            case R.id.txt_submit_evaluate:
                txt_submit_evaluate.setEnabled(false);
                submitEvaluate();
                break;
        }
    }

    /**
     * 获取医院信息数据
     */
    public void loadHospitalDate(int Type) {
        UserManager.getInstance().queryHospitalInfo(hospitalId, new FinalResponseCallback<HospitalInfo.Hospital>(this, Type) {

            @Override
            public void onSuccess(HospitalInfo.Hospital info) {
                super.onSuccess(info);
                new BitmapTools(HospitalHomeActivity.this).display(img_hospital_img, info.hospitalPhoto, R.mipmap.bg_hospital);
                hospitalInfo = info;
                txt_hospital_name.setText(info.hospitalName);
                txt_hospital_level.setText(info.hospitalGrade);
                txt_reservation_count.setText(info.receiveCount);
                txt_address.setText(info.hospitalAddress);
                txt_evaluate_count.setText("(" + info.evaluateCount + ")");
                if ("1".equals(info.concern)) {
                    txt_attention.setText("已关注");
                    txt_attention.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_attention_yes, 0, 0, 0);
                } else {
                    txt_attention.setText("未关注");
                    txt_attention.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_attention_no, 0, 0, 0);
                }
                mHospitalPhone = info.hospitalTel;

                if (info.evaluateCount <= 5) {
                    rl_evaluate_more.setEnabled(false);
                    txt_evaluate_more.setVisibility(View.INVISIBLE);
                } else {
                    rl_evaluate_more.setEnabled(true);
                    txt_evaluate_more.setVisibility(View.VISIBLE);
                }
                mEvaluateList.clear();
                if (null != info.evaluList) {
                    mEvaluateList.addAll(info.evaluList);
                }
                mEvaluateAdapter.refreshList(mEvaluateList);
                mPullToRefreshView.onHeaderRefreshComplete();
            }

            @Override
            public void onReload() {
                super.onReload();
                loadHospitalDate(Constant.TYPE_INIT);
            }
        });
    }

    /**
     * 提交医院评价
     */
    public void submitEvaluate() {
        if (UserManager.getInstance().isLogin()) {
            if (TextUtils.isEmpty(et_evaluate.getText().toString().trim())) {
                UIUtil.toastShort(this, "请输入评价内容");
                txt_submit_evaluate.setEnabled(true);
                return;
            }
            UserManager.getInstance().postHospitalEvaluate(hospitalId, et_evaluate.getText().toString().trim(), new ResponseCallback<EvaluateEntity>() {
                @Override
                public void onFinish() {
                    super.onFinish();
                    if (mSuccess) {
                        evaluateDialog.cancel();
                    }
                    txt_submit_evaluate.setEnabled(true);
                }
            });
        } else {
            startActivity(new Intent(HospitalHomeActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserManager.getInstance().isLogin()) {
            ll_add_evaluate.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.content_bottom_margin));
            mPullToRefreshView.setLayoutParams(lp);

        } else {
            ll_add_evaluate.setVisibility(View.GONE);

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(0, 0, 0, 0);
            mPullToRefreshView.setLayoutParams(lp);
        }
        loadHospitalDate(Constant.TYPE_INIT);
    }

    /**
     * 关注操作
     */
    public void attention() {
        if (UserManager.getInstance().isLogin()) {
            txt_attention.setEnabled(false);
            AttentionManager mAttentionManager = new AttentionManager();
            mAttentionManager.postHospitalFavorite(hospitalId, new ResponseCallback() {
                @Override
                public void onResponse(BaseResponse response) {
                    super.onResponse(response);
                    if (mSuccess) {
                        boolean flag = false;
                        if ("添加关注成功".equals(response.msg)) {
                            txt_attention.setText("已关注");
                            txt_attention.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_attention_yes, 0, 0, 0);
                            flag = true;
                        } else if ("取消关注成功".equals(response.msg)) {
                            txt_attention.setText("未关注");
                            if (alpha > 20) {
                                txt_attention.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_attention_no_gray, 0, 0, 0);
                            } else {
                                txt_attention.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_attention_no, 0, 0, 0);
                            }
                            flag = false;
                        }
                        EventBus.getDefault().post(new AttentHospitalUpdateEvent(flag, hospitalId + ""));
                    }
                    txt_attention.setEnabled(true);
                }
            });
        } else {
            startActivity(new Intent(HospitalHomeActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    /**
     * 提交医院评价VIEW
     */
    public void addEvaluateView() {
        if (UserManager.getInstance().isLogin()) {
            LinearLayout dialogView = (LinearLayout) LayoutInflater.from(HospitalHomeActivity.this).inflate(
                    com.wondersgroup.hs.healthcloud.base.R.layout.dialog_hospital_evaluate, null);

            et_evaluate = (EditText) dialogView.findViewById(R.id.et_evaluate);
            txt_submit_evaluate = (TextView) dialogView.findViewById(R.id.txt_submit_evaluate);
            txt_submit_evaluate.setOnClickListener(this);

            evaluateDialog = UIUtil.showBottomMenu(HospitalHomeActivity.this, dialogView, 0, true);
        } else {
            startActivity(new Intent(HospitalHomeActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    @Override
    protected boolean isShowTitleBar() {
        return false;
    }

    @Override
    protected boolean isShowTintStatusBar() {
        return false;
    }
}
