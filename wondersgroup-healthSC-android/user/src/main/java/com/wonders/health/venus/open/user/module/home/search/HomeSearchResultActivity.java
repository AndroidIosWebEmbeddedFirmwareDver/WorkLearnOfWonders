package com.wonders.health.venus.open.user.module.home.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.HospitalInfo;
import com.wonders.health.venus.open.user.entity.SearchResultData;
import com.wonders.health.venus.open.user.entity.SearchResultInfo;
import com.wonders.health.venus.open.user.logic.SearchManager;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.http.HttpException;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.ClearEditText;
import com.wondersgroup.hs.healthcloud.common.view.FitHeightListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 首页搜索结果
 * Created by songzhen on 2016/11/9.
 */
public class HomeSearchResultActivity extends BaseActivity {
    public static String EXTEA_KEY = "key";

    public static String FROM_HOME = "home";
    public static String FROM_NEARBY = "nearby";
    public static String FROM_GUAHAO = "yuyueguahao";
    public static String EXTRA_FROM = "form";
    private String key;
    private String from = FROM_HOME; //从哪个页面跳转过来
    private ListView mRecyclerView;
    private TextView mTvTitle;
    private TextView mTvLocation;
    private RelativeLayout mRlTop;
    private FitHeightListView mLvHomeresult;
    private TextView mTvLoadmore;

    private SearchManager mSearchManager;
    private SearchResultData mResultData;
    private ClearEditText mTitleEdit;
    private TextView mTitleRight;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_home_searchresult);

        mRecyclerView = (ListView) findViewById(R.id.recycler_view);
        View itemLayout = View.inflate(this, R.layout.layout_home_searchresult, null);
        mTvTitle = (TextView) itemLayout.findViewById(R.id.tv_title);
        mTvLocation = (TextView) itemLayout.findViewById(R.id.tv_location);
        mRlTop = (RelativeLayout) itemLayout.findViewById(R.id.rl_top);
        mLvHomeresult = (FitHeightListView) itemLayout.findViewById(R.id.lv_homeresult);
        mTvLoadmore = (TextView) itemLayout.findViewById(R.id.tv_loadmore);

        mTitleEdit = (ClearEditText) findViewById(R.id.title_edit);
        mTitleRight = (TextView) findViewById(R.id.title_right);
        addListener();
    }

    @Override
    protected boolean isShowTitleBar() {
        return false;
    }

    @Override
    protected void initTintStatusBar() {
        super.initTintStatusBar();
        mStatusBarTintView.setBackgroundResource(R.color.colorPrimary);
    }

    private void addListener() {
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
                            HomeSearchResultActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    String search_txt = mTitleEdit.getText().toString().trim();
                    if (!TextUtils.isEmpty(search_txt)) {
                        //搜索
                        mTitleEdit.setText(search_txt);
                        mTitleEdit.setSelection(search_txt.length());
                        once = false;
                        Intent intent = new Intent(HomeSearchResultActivity.this, HomeSearchResultActivity.class);
                        key = search_txt;
//                        intent.putExtra(HomeSearchResultActivity.EXTEA_KEY, search_txt);
                        startActivity(intent);
                    } else {
                        UIUtil.toastShort(HomeSearchResultActivity.this, "请输入搜索内容！");
                    }

                }
                return false;
            }
        });
    }

    boolean once = false; //防止onNewIntent方法被调用两次
    @Override
    protected void onNewIntent(Intent intent) {
        if (!once){
            loadHomeData();
            once = true;
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mSearchManager = new SearchManager();
        key = getIntent().getStringExtra(EXTEA_KEY);
        from = getIntent().getStringExtra(EXTRA_FROM);
        mTitleEdit.setText(key);
        mTitleEdit.setSelection(key.length());
        if (FROM_HOME.equals(from)){ //首页搜索
            loadHomeData();
            once = true;
        }else if(FROM_GUAHAO.equals(from)){//预约挂号搜索医院
            getRegistionData();
        }else if (FROM_NEARBY.equals(from)){//附近就医搜索医院
            getNeaybyData();
        }
    }

    private HomeSearchListAdapter mAdapter ;
    private void loadHomeData() {
        mSearchManager.homeSearch(key, new ResponseCallback<SearchResultData>() {

            @Override
            public void onStart() {
                UIUtil.showLoadingView((RelativeLayout)mRecyclerView.getParent(), "");
            }

            @Override
            public void onSuccess(SearchResultData t) {
                super.onSuccess(t);
                UIUtil.hideAllNoticeView((RelativeLayout) mRecyclerView.getParent());
                if (t != null) {
                    mResultData = t;
                    ArrayList<SearchResultInfo> mList = change(mResultData);
                    if (mList.size()>0){
                        mAdapter = new HomeSearchListAdapter(HomeSearchResultActivity.this,mList, key,SearchHospitalListActivity.FROM_HOME);
                        mRecyclerView.setAdapter(mAdapter);
                    }else {
                        UIUtil.showEmptyView((RelativeLayout)mRecyclerView.getParent(), "暂无相关信息", new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                loadHomeData();
                            }
                        });
                    }
                } else {
                    UIUtil.showEmptyView((RelativeLayout)mRecyclerView.getParent(), "暂无相关信息", new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            loadHomeData();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception error) {
//                super.onFailure(e);
                String errorStr = "";
                int errorCode = 0;
                if (error instanceof HttpException) {
                    errorStr = error.getMessage();
//                    errorCode = ((HttpException) error).getExceptionCode();
                }
                UIUtil.showErrorView((RelativeLayout) mRecyclerView.getParent(), errorStr, errorCode, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        loadHomeData();
                    }
                });
            }

        });

    }

    private ArrayList<SearchResultInfo> change(SearchResultData resultData) {
        ArrayList<SearchResultInfo> mList = new ArrayList<SearchResultInfo>();
        if (resultData.hospitals!=null&&resultData.hospitals.getList().size()>0){
            SearchResultInfo info = new SearchResultInfo();
            info.type = SearchResultInfo.TYPE_HOSPITAL;
            info.hospitals = resultData.hospitals;
            info.title = "相关医院";
            info.loadmore = "查看更多相关医院";
            mList.add(info);
        }
        if (resultData.doctors!=null&&resultData.doctors.getList().size()>0){
            SearchResultInfo info = new SearchResultInfo();
            info.type = SearchResultInfo.TYPE_DOCTOR;
            info.doctors = resultData.doctors;
            info.title = "相关医生";
            info.loadmore = "查看更多相关医生";
            mList.add(info);
        }
        if (resultData.articles!=null&&resultData.articles.getList().size()>0){
            SearchResultInfo info = new SearchResultInfo();
            info.type = SearchResultInfo.TYPE_ARTICLE;
            info.articles = resultData.articles;
            info.title = "相关文章";
            info.loadmore = "查看更多相关文章";
            mList.add(info);
        }





        return mList;
    }

    /**
     * 获得预约挂号医院列表
     */
    private void getRegistionData(){
        HashMap<String, String> params = null;
        mSearchManager.SearchHospitalList(params,key, new FinalResponseCallback<HospitalInfo>((RelativeLayout)mRecyclerView.getParent(), Constant.TYPE_INIT) {
            @Override
            public void onSuccess(HospitalInfo t) {
                super.onSuccess(t);
                if (t!=null&&!t.isListEmpty()){
                    ArrayList<SearchResultInfo> mList = new ArrayList<SearchResultInfo>();
                    SearchResultInfo info = new SearchResultInfo();
                    info.type = SearchResultInfo.TYPE_HOSPITAL;
                    info.hospitals = t;
                    info.title = "相关医院";
                    info.loadmore = "查看更多相关医院";
                    mList.add(info);
                    if (mList.size()>0){
                        mAdapter = new HomeSearchListAdapter(HomeSearchResultActivity.this,mList, key,SearchHospitalListActivity.FROM_GUAHAO);
                        mRecyclerView.setAdapter(mAdapter);
                    }else {
                        setIsEmpty(true);
                    }
                }else {
                    setIsEmpty(true);
                }
            }
        });
    }

    /**
     * 获得附近就医搜索医院列表
     */
    private void getNeaybyData(){
        HashMap<String, String> params = null;
        mSearchManager.nearbyHospital(params,"","",key,"", new FinalResponseCallback<HospitalInfo>((RelativeLayout)mRecyclerView.getParent(), Constant.TYPE_INIT) {
            @Override
            public void onSuccess(HospitalInfo t) {
                super.onSuccess(t);
                if (t!=null&&!t.isListEmpty()){
                    ArrayList<SearchResultInfo> mList = new ArrayList<SearchResultInfo>();
                    SearchResultInfo info = new SearchResultInfo();
                    info.type = SearchResultInfo.TYPE_HOSPITAL;
                    info.hospitals = t;
                    info.title = "相关医院";
                    info.loadmore = "查看更多相关医院";
                    mList.add(info);
                    if (mList.size()>0){
                        mAdapter = new HomeSearchListAdapter(HomeSearchResultActivity.this,mList, key,SearchHospitalListActivity.FROM_NEARBY);
                        mRecyclerView.setAdapter(mAdapter);
                    }else {
                        setIsEmpty(true);
                    }
                }else {
                    setIsEmpty(true);
                }
            }

        });
    }

}
