package com.wonders.health.venus.open.user.module.home.registration;

import android.os.Bundle;
import android.view.View;

import com.wonders.health.venus.open.user.BaseActivity;


/**
 * 类描述：预约模块搜索医院和医生
 * 创建人：Bob
 * 创建时间：2016/1/5 20:08
 */
public class RegistrationSearchResultActivity extends BaseActivity implements View.OnClickListener {
    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
//    public static final String EXTRA_SEARCH_KEY = "search_key";
//
//    private ImageView mIv_back;
//    private TextView mTv_back;
//    private PullToRefreshView mPull_view_registration;
//    private BaseRecyclerView mRecycler_view_registration;
//    private FitHeightListView mLv_hospital;
//    private TextView mTv_hospital_more;
//    private TextView mTv_doctor_empty;
//    private View mDi_doctor_empty;
//
//    private AdapterDepartmentNameList mDoctorListAdapter;
//    private HospitalListAdapter1 mHospitalListAdapter;
//
//    private HashMap<String, String> mMoreParams;
//    private List<DoctorListVO> mDoctors;
//    private boolean mHasMore;
//    private RegistrationSearchListResponse.HospitalResponse mHospitalResponse;
//    private String mSearchKey;
//
//    @Override
//    protected void initViews() {
//        setContentView(R.layout.activity_registration_search_result);
//
//        mIv_back = (ImageView) findViewById(R.id.iv_back);
//        mTv_back = (TextView) findViewById(R.id.tv_back);
//        mPull_view_registration = (PullToRefreshView) findViewById(R.id.pull_view_registration);
//        mRecycler_view_registration = (BaseRecyclerView) findViewById(R.id.recycler_view_registration);
//
//        View header = LayoutInflater.from(this).inflate(R.layout.registration_search_result_header, null);
//        mLv_hospital = (FitHeightListView) header.findViewById(R.id.lv_hospital);
//        mTv_hospital_more = (TextView) header.findViewById(R.id.tv_hospital_more);
//        mTv_doctor_empty = (TextView) header.findViewById(R.id.tv_doctor_empty);
//        mDi_doctor_empty = header.findViewById(R.id.di_doctor_empty);
//        mRecycler_view_registration.addHeader(header);
//
//        mPull_view_registration.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
//            @Override
//            public void onHeaderRefresh(PullToRefreshView view) {
//                loadData(Constant.TYPE_RELOAD);
//            }
//        });
//
//        mPull_view_registration.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
//            @Override
//            public void onFooterRefresh(PullToRefreshView view) {
//                loadData(Constant.TYPE_NEXT);
//            }
//        });
//
//        mLv_hospital.setOnItemClickListener(new FitHeightListView.OnItemClickListener() {
//            @Override
//            public void onItemClick(ViewGroup parent, View view, int pos) {
//                HospitalInfo.Hospital hospital = mHospitalResponse.hospitalList.get(pos);
//                Intent intent = new Intent(RegistrationSearchResultActivity.this, DepartmentSelectActivity.class);
//                intent.putExtra(DepartmentSelectActivity.EXTRA_HOSPITAL, hospital.id);
//                intent.putExtra(DepartmentSelectActivity.EXTRA_HOSPITAL_NAME, hospital.name);
//                startActivity(intent);
//            }
//        });
//
//        mIv_back.setOnClickListener(this);
//        mTv_back.setOnClickListener(this);
//        mTv_hospital_more.setOnClickListener(this);
//    }
//
//    @Override
//    protected void initData(Bundle savedInstanceState) {
//        mSearchKey = getIntent().getStringExtra(EXTRA_SEARCH_KEY);
//        if (TextUtils.isEmpty(mSearchKey)) {
//            finish();
//            return;
//        }
//        mDoctors = new ArrayList<>();
//        mTv_back.setText(mSearchKey);
//        loadData(Constant.TYPE_INIT);
//    }
//
//    private void loadData(final int type) {
//        HashMap<String, String> params = null;
//        if (type == Constant.TYPE_NEXT) {
//            params = mMoreParams;
//        }
//        RegistrationManager.getInstance().queryAllByKeyWord(mSearchKey, params,
//                new FinalResponseCallback<RegistrationSearchListResponse>(mPull_view_registration, type) {
//                    @Override
//                    public void onSuccess(RegistrationSearchListResponse s) {
//                        super.onSuccess(s);
//                        mHospitalResponse = s.getExtraData(RegistrationSearchListResponse.HospitalResponse.class);
//                        if (type != Constant.TYPE_NEXT && (s == null || s.getList().isEmpty())
//                                && ((mHospitalResponse == null || mHospitalResponse.hospitalList == null || mHospitalResponse.hospitalList.isEmpty()))) {
//                            setIsEmpty(true, "未搜到相关的医生或者医院！");
//                        } else {
//                            mMoreParams = s.more_params;
//                            mHasMore = s.more;
//                            if (type != Constant.TYPE_NEXT) {
//                                mDoctors.clear();
//                            }
//                            mDoctors.addAll(s.getList());
//                            bindView(type, s.getList().size());
//                        }
//                    }
//
//                    @Override
//                    public void onReload() {
//                        super.onReload();
//                        loadData(Constant.TYPE_INIT);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        super.onFinish();
//                        if (type == Constant.TYPE_NEXT) {
//                            mPull_view_registration.onFooterRefreshComplete();
//                        } else {
//                            mPull_view_registration.onHeaderRefreshComplete();
//                        }
//                    }
//                });
//    }
//
//    // 数据加载完成，绑定到UI
//    private void bindView(int type, int itemCount) {
//        if (type != Constant.TYPE_NEXT && mHospitalResponse != null) {
//            if (mHospitalListAdapter == null) {
//                mHospitalListAdapter = new HospitalListAdapter1(this, mHospitalResponse.hospitalList);
//                mLv_hospital.setAdapter(mHospitalListAdapter);
//            } else {
//                mHospitalListAdapter.refresh(mHospitalResponse.hospitalList);
//            }
//        }
//
//        if (mHospitalResponse == null || mHospitalResponse.hospitalList == null || mHospitalResponse.hospitalList.isEmpty()) {
//            mTv_hospital_more.setVisibility(View.VISIBLE);
//            mTv_hospital_more.setClickable(false);
//            mTv_hospital_more.setText("未搜到相关医院！");
//        } else if (mHospitalResponse.has_more_hospital) {
//            mTv_hospital_more.setVisibility(View.VISIBLE);
//            mTv_hospital_more.setClickable(true);
//            mTv_hospital_more.setText("查看更多相关医院");
//        } else {
//            mTv_hospital_more.setVisibility(View.GONE);
//        }
//
//        if (type != Constant.TYPE_NEXT) {
//            mDi_doctor_empty.setVisibility(mDoctors.isEmpty() ? View.VISIBLE : View.GONE);
//            mTv_doctor_empty.setVisibility(mDoctors.isEmpty() ? View.VISIBLE : View.GONE);
//        }
//
//        if (mDoctorListAdapter == null) {
//            mDoctorListAdapter = new AdapterDepartmentNameList(this, mDoctors, null, null);
//            mRecycler_view_registration.setAdapter(mDoctorListAdapter);
//        } else {
//            if (type != Constant.TYPE_NEXT) {
//                mDoctorListAdapter.refreshList(mDoctors);
//            } else {
//                // 这种方法更新adapter效率要比notifyDatasetChanged高
//                mDoctorListAdapter.notifyItemRangeInserted(mDoctors.size() - itemCount, itemCount);
//            }
//        }
//
//        // 如果没有下一页，关掉上拉加载更多
//        mPull_view_registration.setLoadMoreEnable(mHasMore);
//    }
//
//    @Override
//    protected boolean isShowTitleBar() {
//        return false;
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_back:
//            case R.id.tv_back:
//                finish();
//                break;
//            case R.id.tv_hospital_more:
//                startActivity(new Intent(this, HospitalSearchListActivity.class)
//                        .putExtra(HospitalSearchListActivity.EXTRA_SEARCH_KEY, mSearchKey));
//                break;
//            default:
//                break;
//        }
//    }
}
