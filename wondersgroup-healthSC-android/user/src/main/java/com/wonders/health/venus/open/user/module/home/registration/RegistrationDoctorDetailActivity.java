package com.wonders.health.venus.open.user.module.home.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.DoctorDetailVO;
import com.wonders.health.venus.open.user.entity.EvaluateEntity;
import com.wonders.health.venus.open.user.entity.RegisterHeadEntity;
import com.wonders.health.venus.open.user.entity.ScheduleInfo;
import com.wonders.health.venus.open.user.logic.RegistrationManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.health.EvaluateListActivity;
import com.wonders.health.venus.open.user.module.mine.LoginActivity;
import com.wonders.health.venus.open.user.view.ObservableScrollView;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.FitHeightListView;

import java.util.ArrayList;
import java.util.List;

import static com.wonders.health.venus.open.user.module.home.registration.DoctorScheduleActivity.EXTRA_DEPT_CODE;
import static com.wonders.health.venus.open.user.module.home.registration.DoctorScheduleActivity.EXTRA_DOCTOR_CODE;
import static com.wonders.health.venus.open.user.module.home.registration.DoctorScheduleActivity.EXTRA_HOSPITAL_CODE;
import static com.wonders.health.venus.open.user.module.home.registration.DoctorScheduleActivity.EXTRA_SCHEDULE_INFO;

/**
 * Created by sunning on 16/1/4.
 * 预约挂号 医生详情
 */
public class RegistrationDoctorDetailActivity extends UnifyFinishActivity {

    public static final String IS_ATTENTION = "is_attention";
    public static final String SHOW_REGISTER_BTN = "show_register_btn";

    public String hospitalCode, doctorCode, deptCode;
    private boolean isAttention, showRegisterBtn;
    private List<EvaluateEntity.Evaluate> entityList;

    private FitHeightListView listView;
    private View mDoctorDetailMore;
    private TextView mHospitalNameTV, mExpertinTV, mDoctorDetailTV, mevaluNumTV, mCommentMoreTV;
    private LinearLayout mCommentMoreLL;
    private ObservableScrollView scrollView;
    private Button registrationBtn;

    @Override
    protected boolean isShowTitleBar() {
        return false;
    }

    @Override
    protected boolean isShowTintStatusBar() {
        return false;
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.register_doctor_detail);
        listView = (FitHeightListView) findViewById(R.id.doctor_detail_list);
        mHospitalNameTV = (TextView) findViewById(R.id.tv_hospital);
        mExpertinTV = (TextView) findViewById(R.id.tv_expertin);
        mDoctorDetailMore = findViewById(R.id.tv_docotor_more);
        mDoctorDetailTV = (TextView) findViewById(R.id.tv_doctor_detail);
        mevaluNumTV = (TextView) findViewById(R.id.tv_mevalu_num);
        mCommentMoreLL = (LinearLayout) findViewById(R.id.ll_comment_more);
        mCommentMoreTV = (TextView) findViewById(R.id.tv_comment_more);
        scrollView = (ObservableScrollView) findViewById(R.id.doctor_detail_scroll);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        loadParams();
        showRegistrationBtn();
        initAdapter();
        loadData();
    }

    private void showRegistrationBtn() {
        if (showRegisterBtn) {
            registrationBtn = (Button) findViewById(R.id.go_registration);
            if (registrationBtn != null) {
                registrationBtn.setVisibility(View.VISIBLE);
                registrationBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(UserManager.getInstance().getUser().uid)) {
                            startActivity(new Intent(RegistrationDoctorDetailActivity.this, LoginActivity.class));
                            return;
                        }
                        if (RegistrationDoctorListActivity.verification(RegistrationDoctorDetailActivity.this, null)) {
                            RegistrationManager.getInstance().getScheduleInfo(hospitalCode, deptCode, doctorCode, new ResponseCallback<ScheduleInfo>() {

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    UIUtil.showProgressBar(RegistrationDoctorDetailActivity.this);
                                }

                                @Override
                                public void onSuccess(ScheduleInfo t) {
                                    super.onSuccess(t);
                                    if (t != null && t.schedule != null) {
                                        startActivity(new Intent(RegistrationDoctorDetailActivity.this, DoctorScheduleActivity.class)
                                                .putExtra(EXTRA_SCHEDULE_INFO, t));
                                    } else {
                                        registrationBtn.setClickable(false);
                                        registrationBtn.setBackgroundResource(R.color.bc3);
                                        registrationBtn.setTextColor(ContextCompat.getColor(RegistrationDoctorDetailActivity.this, R.color.tc3));
                                    }
                                }

                                @Override
                                public boolean isShowNotice() {
                                    return true;
                                }

                                @Override
                                public void onFinish() {
                                    super.onFinish();
                                    UIUtil.hideProgressBar(RegistrationDoctorDetailActivity.this);
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    private void initAdapter() {
        entityList = new ArrayList<>();
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return entityList.size() >= 5 ? 5 : entityList.size();
            }

            @Override
            public Object getItem(int position) {
                return entityList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                EvaluateEntity.Evaluate info = entityList.get(position);
                ViewHolder holder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(RegistrationDoctorDetailActivity.this).inflate(R.layout.item_evaluate, null);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.mDate.setText(info.createTime);
                holder.mName.setText(info.nickName);
                holder.mContent.setText(info.content);
                return convertView;
            }

            class ViewHolder extends RecyclerView.ViewHolder {
                TextView mDate;
                TextView mName;
                TextView mContent;

                public ViewHolder(View itemView) {
                    super(itemView);
                    mDate = (TextView) itemView.findViewById(R.id.txt_date);
                    mName = (TextView) itemView.findViewById(R.id.txt_name);
                    mContent = (TextView) itemView.findViewById(R.id.txt_content);
                }
            }
        });
    }

    private void loadParams() {
        hospitalCode = getIntent().getStringExtra(EXTRA_HOSPITAL_CODE);
        doctorCode = getIntent().getStringExtra(EXTRA_DOCTOR_CODE);
        deptCode = getIntent().getStringExtra(EXTRA_DEPT_CODE);
        isAttention = getIntent().getBooleanExtra(IS_ATTENTION, false);
        showRegisterBtn = getIntent().getBooleanExtra(SHOW_REGISTER_BTN, false);
    }

    public void loadData() {
        if (TextUtils.isEmpty(hospitalCode) || TextUtils.isEmpty(deptCode) || TextUtils.isEmpty(doctorCode)) {
            UIUtil.toastLong(this, "参数错误");
            this.finish();
            return;
        }
        RegistrationManager.getInstance().queryDoctorDetail(hospitalCode, deptCode, doctorCode, new FinalResponseCallback<DoctorDetailVO>((ViewGroup) findViewById(R.id.register_doctor_detail_root)) {
            @Override
            public void onSuccess(DoctorDetailVO detailVO) {
                super.onSuccess(detailVO);
                bindView(detailVO);
            }

            @Override
            public void onReload() {
                loadData();
            }
        });
    }

    private void bindView(final DoctorDetailVO doctorDetailVO) {
        if (doctorDetailVO != null) {
            isAttention = doctorDetailVO.concern == 1;
            if (doctorDetailVO.evaluList != null) {
                entityList.addAll(doctorDetailVO.evaluList);
                listView.getAdapter().notifyDataSetChanged();
            }
            mHospitalNameTV.setText(doctorDetailVO.hosName);
            mExpertinTV.setText(doctorDetailVO.expertin);
            mDoctorDetailTV.setText(doctorDetailVO.doctorDesc);
            mDoctorDetailTV.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    int lineCount = mDoctorDetailTV.getLineCount();
                    if (lineCount <= 3) {
                        mDoctorDetailMore.setVisibility(View.GONE);
                    }
                }
            });
            mDoctorDetailMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(RegistrationDoctorDetailActivity.this,
                            RegistrationDoctorDetailDescActivity.class)
                            .putExtra(RegistrationDoctorDetailDescActivity.DOCTOR_DESC, doctorDetailVO.doctorDesc));
                }
            });
            mevaluNumTV.setText("患者评价(" + doctorDetailVO.evaluateCount + ")");
            final int doctorId = doctorDetailVO.id;
            final int evaluateCount = doctorDetailVO.evaluateCount;
            if (evaluateCount <= 5) {
                mCommentMoreTV.setVisibility(View.GONE);
            } else {
                mCommentMoreLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(RegistrationDoctorDetailActivity.this, EvaluateListActivity.class)
                                .putExtra(EvaluateListActivity.EVALUATE_TYPE, EvaluateListActivity.EVALUATE_TYPE_DOCTOR)
                                .putExtra(EvaluateListActivity.EVALUATE_TARGET_ID, doctorId + "")
                                .putExtra(EvaluateListActivity.EVALUATE_COUNT, evaluateCount + ""));
                    }
                });
            }
        } else {
            mCommentMoreTV.setVisibility(View.GONE);
            mDoctorDetailMore.setVisibility(View.GONE);
        }
        LinearLayout headView = (LinearLayout) findViewById(R.id.doctor_detail_head);
        if (headView != null) {
            headView.addView(new RegistrationDoctorHead().getHeadView(RegistrationDoctorDetailActivity.this, new RegisterHeadEntity(doctorDetailVO, RegisterHeadEntity.From.DOCTOR_DETAIL, scrollView, isAttention)));
        }
    }

    public static void startDoctorDetail(Context context, String hospitalCode, String doctorCode, String deptCode) {
        RegistrationDoctorDetailActivity.startDoctorDetail(context, hospitalCode, doctorCode, deptCode, false);
    }

    public static void startDoctorDetail(Context context, String hospitalCode, String doctorCode, String deptCode, boolean isAttention) {
        Intent intent = new Intent();
        intent.setClass(context, RegistrationDoctorDetailActivity.class);
        intent.putExtra(EXTRA_HOSPITAL_CODE, hospitalCode);
        intent.putExtra(EXTRA_DOCTOR_CODE, doctorCode);
        intent.putExtra(EXTRA_DEPT_CODE, deptCode);
        intent.putExtra(SHOW_REGISTER_BTN, true);
        intent.putExtra(IS_ATTENTION, isAttention);
        context.startActivity(intent);
    }
}
