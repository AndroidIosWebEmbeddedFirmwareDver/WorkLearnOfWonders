package com.wonders.health.venus.open.user.module.home.search;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.SearchConfig;
import com.wonders.health.venus.open.user.logic.SearchManager;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.AutoLineBreakLayout;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.ClearEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页面基类
 * Created by songzhen on 2016/11/8.
 */
public abstract class SearchActivity extends BaseActivity implements View.OnClickListener {
    public static final String EXTRA_SEARCH_TEXT = "text";
//    private static final int HISTORY_COUNT_LIMIT = 10;//历史记录显示条数
    private LinearLayout mLinear_hotWords;
    private AutoLineBreakLayout mLayout_hotwords;
    private BaseRecyclerView mRecyclerView;
    private TextView mTitleRight;
    private ClearEditText mTitleEdit;


    private List<String> historyList = new ArrayList<>();
    protected List<String> wordList = new ArrayList<String>();
    private List<String> tipList = new ArrayList<>();
    protected String key_word;
    private SearchConfig mConfig;
    private String history_pref_name;
    private LinearLayout mLinear_history; //历史搜索
    private TextView mClearAll; //清除所有历史记录
    private SearchHisAdapter mHisAdapter;
    private SearchHisAdapter mTipAdapter;
    private BaseRecyclerView mRecyclerViewTip;
    private LinearLayout mLinear_Tip;
    private SearchManager mSearchManager;

    private boolean isUseAssociative  = false; //是否启用关联搜索

    protected abstract SearchConfig initConfig();

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_search);
        mLinear_hotWords = (LinearLayout) findViewById(R.id.ll_hotwords); //热门搜索
        mLinear_history = (LinearLayout) findViewById(R.id.ll_history); //历史搜索
        mLinear_Tip = (LinearLayout) findViewById(R.id.ll_tip); //关联搜索
        mLayout_hotwords = (AutoLineBreakLayout) findViewById(R.id.layout_hotwords);
        mRecyclerView = (BaseRecyclerView) findViewById(R.id.recycler_view); //历史搜索列表
        mRecyclerViewTip = (BaseRecyclerView) findViewById(R.id.recycler_view_tip);//关联搜索列表
        mTitleEdit = (ClearEditText) findViewById(R.id.title_edit);
        mTitleRight = (TextView) findViewById(R.id.title_right);
        View footView = View.inflate(this,R.layout.layout_search_clearall,null);
        mClearAll = (TextView) footView.findViewById(R.id.tv_clearAll);
        mRecyclerView.addFooter(footView);
        mTitleRight.setOnClickListener(this);//取消
        mClearAll.setOnClickListener(this); //清除所有
        mSearchManager = new SearchManager();
        addListener();
    }

    private void addListener(){
        mTitleEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            SearchActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    String search_txt = mTitleEdit.getText().toString().trim();
                    if (!TextUtils.isEmpty(search_txt)) {
                        //搜索
                        doSearchByKeyWord(search_txt, true);
                    } else {
                        UIUtil.toastShort(SearchActivity.this, "请输入搜索内容！");
                    }

                }
                return false;
            }
        });

        mRecyclerView.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                doSearchByKeyWord(historyList.get(position), true);
            }
        });

        mRecyclerViewTip.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                doSearchByKeyWord(tipList.get(position),true);
            }
        });
        mTitleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUseAssociative){
                    if (s.length()>0){
                        mLinear_history.setVisibility(View.GONE);
                        mLinear_Tip.setVisibility(View.VISIBLE);
                        loadTipData(s.toString().trim());
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mRecyclerViewTip.getLayoutParams();
                        params.setMargins(0,20,0,0);
                    }else {
                        mLinear_history.setVisibility(View.VISIBLE);
                        mLinear_Tip.setVisibility(View.GONE);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mRecyclerViewTip.getLayoutParams();
                        params.setMargins(0, 0, 0, 0);
                    }
                }
            }
        });
    }

    private void loadTipData(String key_word){

        //假数据
        tipList.clear();
        for (int i = 0;i<4;i++){
            tipList.add(key_word + i);
        }
        mTipAdapter = new SearchHisAdapter(SearchActivity.this,tipList,key_word);
        mRecyclerViewTip.setAdapter(mTipAdapter);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mConfig = initConfig();
        if (mConfig ==null){
            finish();
            return;
        }
        history_pref_name = mConfig.history_pref_name;
        if (!TextUtils.isEmpty(mConfig.hint_text)){
            mTitleEdit.setHint(mConfig.hint_text);
        }
        loadHistoryData();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_right://取消
                finish();
                break;
            case R.id.tv_clearAll://清除所有
                UIUtil.showConfirm(this, "确定清空历史记录么", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearAllHistoryData();
                    }
                });
                break;
        }
    }

    /**
     * 热门搜索动态布局
     * dynamicLayoutHotWords
     *
     * @since 3.6
     */
    protected void dynamicLayoutHotWords(List<String> words) {
        if (words == null || words.isEmpty()) {
            return;
        }
        wordList = words;
        mLayout_hotwords.removeAllViews();

        if (wordList != null && wordList.size() > 0) {
            mLinear_hotWords.setVisibility(View.VISIBLE);
            for (final String hotWords : wordList) {
                LinearLayout child = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_hotwords, null);
                TextView textView = (TextView) child.getChildAt(0);
                textView.setText(hotWords);

                textView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        doSearchByKeyWord(hotWords, true);
                    }
                });
                mLayout_hotwords.addView(child);
            }
        } else {//热门搜索为空
            mLinear_hotWords.setVisibility(View.GONE);
        }
    }

    /**
     * 加载历史数据 loadHistoryData
     *
     * @since 3.6
     */
    private void loadHistoryData() {
        historyList = PrefUtil.getJsonArray(this, history_pref_name, String.class);
        if (historyList != null) {
            refreshHistoryLv();
        } else {
            historyList = new ArrayList<>();
            mLinear_history.setVisibility(View.GONE);
        }
    }

    /**
     * 清除历史记录
     */
    private void clearAllHistoryData(){
        historyList = new ArrayList<>();
        PrefUtil.putJsonArray(this,history_pref_name,historyList);
        refreshHistoryLv();
    }

    //刷新历史记录列表
    private void refreshHistoryLv() {
        if (historyList !=null && !historyList.isEmpty()){
            mLinear_history.setVisibility(View.VISIBLE);
        }else {
            mLinear_history.setVisibility(View.GONE);
        }

        if (mRecyclerView.getAdapter() == null){
            mHisAdapter = new SearchHisAdapter(SearchActivity.this, historyList);
            mRecyclerView.setAdapter(mHisAdapter);
        }else {
            mHisAdapter.refreshList(historyList);
        }
    }

    private void doSearchByKeyWord(String search_txt, boolean needChageExpanedState) {
        if (historyList.contains(search_txt)) {
            historyList.remove(search_txt);
        }

        historyList.add(0, search_txt);
        if (historyList.size()>5){
            historyList = historyList.subList(0,5);
        }
        key_word = search_txt;
        mTitleEdit.setText(key_word);
        mTitleEdit.setSelection(key_word.length());
        PrefUtil.putJsonArray(SearchActivity.this, history_pref_name, historyList);
        refreshHistoryLv();
        search(search_txt);
    }


    /**
     * 获取热门搜索关键字
     * getAuthStatus
     *
     * @since 1.0
     */
    protected void loadHotWords() {
    }

    protected void search(String key){

    }

}
