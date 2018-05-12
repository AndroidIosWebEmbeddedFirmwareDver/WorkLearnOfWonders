package com.wonders.health.venus.open.user.module.home.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.HospitalInfo;
import com.wonders.health.venus.open.user.logic.SearchManager;
import com.wonders.health.venus.open.user.module.health.HospitalHomeActivity;
import com.wonders.health.venus.open.user.module.home.registration.adapter.HospitalListAdapter;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.ClearEditText;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 搜索-医院列表--加载更多
 * Created by songzhen on 2016/11/10.
 */
public class SearchHospitalListActivity extends BaseActivity {
    public static String FROM_HOME = "home";
    public static String FROM_NEARBY = "nearby";
    public static String FROM_GUAHAO = "yuyueguahao";
    public static String EXTRA_KEY = "key";
    public static String EXTRA_FROM = "form";
    private LinearLayout layout_search;
    private BaseRecyclerView mRecyclerView;
    private PullToRefreshView mPullView;
    private HospitalListAdapter mAdapter;

    private HashMap<String, String> mMoreParams;
    private boolean mIsMore;
    private List<HospitalInfo.Hospital> mItems = new ArrayList<>();

    private SearchManager mManager;
    private String key;
    private String from = FROM_HOME;

    private ClearEditText mTitleEdit;
    private TextView mTitleRight;

//    private boolean isShowTitleBar ;
//    private ClearEditText mTitleEdit;
//    private TextView mTitleRight;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_homesearchresult_list);
        layout_search = (LinearLayout) findViewById(R.id.layout_search);
        mRecyclerView = (BaseRecyclerView) findViewById(R.id.recycler_view);
        mPullView = (PullToRefreshView) findViewById(R.id.pull_view);
        layout_search.setVisibility(View.GONE);
        mTitleEdit = (ClearEditText) findViewById(R.id.title_edit);
        mTitleRight = (TextView) findViewById(R.id.title_right);
        addListener();
    }

    private void addListener() {
        mPullView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getHospitalList(Constant.TYPE_RELOAD);
            }
        });
        mPullView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                getHospitalList(Constant.TYPE_NEXT);
            }
        });
        mRecyclerView.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                HospitalInfo.Hospital hospital = mItems.get(position);
                if (hospital != null) {
                    if (TextUtils.isEmpty(hospital.hospitalId+"")){
                        UIUtil.toastShort(SearchHospitalListActivity.this,"hospitalId 不能为空");
                    }else {
                        Intent intent = new Intent(SearchHospitalListActivity.this, HospitalHomeActivity.class);
                        intent.putExtra("hospitalId", hospital.hospitalId+"");
                        startActivity(intent);
                    }
                }
            }
        });

        mTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            SearchHospitalListActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    String search_txt = mTitleEdit.getText().toString().trim();
                    if (!TextUtils.isEmpty(search_txt)) {
                        //搜索
                        mTitleEdit.setText(search_txt);
                        mTitleEdit.setSelection(search_txt.length());
                        once = false;
                        Intent intent = new Intent(SearchHospitalListActivity.this, SearchHospitalListActivity.class);
                        key = search_txt;
                        startActivity(intent);
                    } else {
                        UIUtil.toastShort(SearchHospitalListActivity.this, "请输入搜索内容！");
                    }

                }
                return false;
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mManager = new SearchManager();
        key = getIntent().getStringExtra(EXTRA_KEY);
        from = getIntent().getStringExtra(EXTRA_FROM);
        if (FROM_NEARBY.equals(from)||FROM_GUAHAO.equals(from)){
            mTitleBar.setVisibility(View.GONE);
            layout_search.setVisibility(View.VISIBLE);
            mTitleEdit.setText(key + "");
            mTitleEdit.setSelection(TextUtils.isEmpty(key) ? 0 : key.length());
            if (SystemUtil.isTintStatusBarAvailable(this)){
                layout_search.setPadding(0, SystemUtil.getStatusBarHeight(), 0, 0);
            }
        }else {
            mTitleBar.setTitle("全部医院");
        }
        getHospitalList(Constant.TYPE_INIT);
        once = true;
    }
    boolean once = false; //防止onNewIntent方法被调用两次
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (!once){
            getHospitalList(Constant.TYPE_INIT);
            once = true;
        }
    }

    //获取医院列表
    private void getHospitalList(final int type) {
        HashMap<String, String> params = null;
        if (type == Constant.TYPE_NEXT) {
            params = mMoreParams;
        }

//        if (from.equals(FROM_HOME)||from.equals(FROM_GUAHAO)){//从主页或预约挂号跳转过来的
            mManager.SearchHospitalList(params,key, new FinalResponseCallback<HospitalInfo>(mPullView, type) {
                @Override
                public void onSuccess(HospitalInfo t) {
                    super.onSuccess(t);
                    mIsMore = t.more;
                    mMoreParams = t.more_params;
                    if (type != Constant.TYPE_NEXT) {
                        mItems.clear();
                    }
                    mItems.addAll(t.getList());
                    setIsEmpty(mItems.isEmpty());
                    bindView(type, t.getList().size());
                }

                @Override
                public void onReload() {
                    super.onReload();
                    getHospitalList(Constant.TYPE_INIT);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    if (type == Constant.TYPE_NEXT) {
                        mPullView.onFooterRefreshComplete();
                    } else {
                        mPullView.onHeaderRefreshComplete();
                    }
                }
            });
//        }else if (from.equals(FROM_NEARBY)){ //从附近搜索页跳转过来的
//            mManager.nearbyHospital(params,"","",key,"", new FinalResponseCallback<HospitalInfo>(mPullView, type) {
//                @Override
//                public void onSuccess(HospitalInfo t) {
//                    super.onSuccess(t);
//                    mIsMore = t.more;
//                    mMoreParams = t.more_params;
//                    if (type != Constant.TYPE_NEXT) {
//                        mItems.clear();
//                    }
//                    mItems.addAll(t.getList());
//                    setIsEmpty(mItems.isEmpty());
//                    bindView(type, t.getList().size());
//                }
//
//                @Override
//                public void onReload() {
//                    super.onReload();
//                    getHospitalList(Constant.TYPE_INIT);
//                }
//
//                @Override
//                public void onFinish() {
//                    super.onFinish();
//                    if (type == Constant.TYPE_NEXT) {
//                        mPullView.onFooterRefreshComplete();
//                    } else {
//                        mPullView.onHeaderRefreshComplete();
//                    }
//                }
//            });
//        }

    }


    private void bindView(int type, int itemCount) {
        if (mAdapter == null) {
            mAdapter = new HospitalListAdapter(this, mItems);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            if (type != Constant.TYPE_NEXT) {
                mAdapter.refreshList(mItems);
            } else {
                mAdapter.notifyItemRangeInserted(mItems.size() - itemCount, itemCount);
            }
        }
        mPullView.setLoadMoreEnable(mIsMore);
    }


}
