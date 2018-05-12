package com.wonders.health.venus.open.user.module.health;
/*
 * Created by sunning on 2016/11/1.
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ArticleItem;
import com.wonders.health.venus.open.user.entity.ArticleListResponse;
import com.wonders.health.venus.open.user.entity.ArticleTab;
import com.wonders.health.venus.open.user.entity.ArticleTabListResponse;
import com.wonders.health.venus.open.user.entity.HealthHomeEntity;
import com.wonders.health.venus.open.user.logic.HealthManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.article.ArticleAdapter;
import com.wonders.health.venus.open.user.module.mine.auth.AuthChooseActivity;
import com.wonders.health.venus.open.user.util.Constant;
import com.wonders.health.venus.open.user.view.SlidingTabStrip;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.LogUtils;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;
import com.wondersgroup.hs.healthcloud.common.view.ViewPagerWithIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.wonders.health.venus.open.user.R.id.fuc_ll;


public class HealthFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener,View.OnClickListener {
    private PullToRefreshView mPull_view;
    private BaseRecyclerView mRecycler_view;
    private SlidingTabStrip mTabs_hidden;
    private View mView_titlebar_below_line;
    private FrameLayout mFl_banner;
    private TextView mTv_titlebar;
    private View mHeadView;
    private com.wondersgroup.hs.healthcloud.common.view.ViewPagerWithIndicator mHealth_banner_vp;
    private ViewPager mPager;
    private LinearLayout mFuc_ll;
    private LinearLayout mHealth_func1;
    private ImageView mIv_func1;
    private TextView mTv_func1_title;
    private RelativeLayout mRl_health_func22;
    private RelativeLayout mRl_health_func21;
    private ImageView mIv_func21;
    private TextView mTv_func21_title;
    private ImageView mIv_func22;
    private TextView mTv_func22_title;

    private ImageView mIv_func22_unuse;
    private SlidingTabStrip mTabs;
    private ArticleAdapter mArticleAdapter;
    private BitmapTools mBitmapTools;
    private List<String> mTabList = new ArrayList<>();

    private HealthManager mHealthManager;
    private ArticleTabListResponse mArticleTabListResponseCache;
    private ArticleListResponse mListResponse;
    private HealthHomeEntity mHealthHomeEntityCache;
    private String mCat_id;
    private List<ArticleItem> mArticleItems = new ArrayList<>();
    private boolean mMore;
    private HashMap<String, String> mMoreParams = new HashMap<>();
    private View[] fucll = new View[3];
    private TextView[] funcTitle = new TextView[3];
    private TextView[] funcDesc = new TextView[3];
    private ImageView[] funcImgs = new ImageView[3];
    private boolean mIsPrepareToRefresh = false;

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health,null);
    }

    @Override
    protected void initViews() {
        mPull_view = (PullToRefreshView) findViewById(R.id.pull_view);
        mPull_view.setLoadMoreEnable(false);
        mRecycler_view = (BaseRecyclerView) findViewById(R.id.recycler_view);
        mView_titlebar_below_line= findViewById(R.id.view_titlebar_below_line);
        mTabs_hidden = (SlidingTabStrip) findViewById(R.id.tabs_hidden);
        mTv_titlebar = (TextView) findViewById(R.id.tv_titlebar);
        mHeadView = LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_health_head, null);
        mRecycler_view.addHeader(mHeadView);
        mRecycler_view.setAdapter(new ArticleAdapter(mBaseActivity, new ArrayList<ArticleItem>()));

        initHeaderView(mHeadView);

        initEvent();
    }

    private void initFunctionView() {
        fucll[0] = mHealth_func1;
        funcTitle[0] = mTv_func1_title;
//        funcDesc[0] = mTvFunc1Desc;
        funcImgs[0] = mIv_func1;

        fucll[1] = mRl_health_func21;
        funcTitle[1] = mTv_func21_title;
//        funcDesc[1] = mTvFunc2Desc;
        funcImgs[1] = mIv_func21;

        fucll[2] = mRl_health_func22;
        funcTitle[2] = mTv_func22_title;
//        funcDesc[2] = mTvFunc3Desc;
        funcImgs[2] = mIv_func22;
    }

    private void initHeaderView(View headView) {
        mFl_banner = (FrameLayout) headView.findViewById(R.id.fl_banner);
        mHealth_banner_vp = (com.wondersgroup.hs.healthcloud.common.view.ViewPagerWithIndicator) headView.findViewById(R.id.health_banner_vp);
        mFuc_ll = (LinearLayout) headView.findViewById(fuc_ll);
        mHealth_func1 = (LinearLayout) headView.findViewById(R.id.health_func1);
        mIv_func1 = (ImageView) headView.findViewById(R.id.iv_func1);
        mTv_func1_title = (TextView) headView.findViewById(R.id.tv_func1_title);
        mRl_health_func21 = (RelativeLayout) headView.findViewById(R.id.rl_health_func21);
        mIv_func21 = (ImageView) headView.findViewById(R.id.iv_func21);
        mTv_func21_title = (TextView) headView.findViewById(R.id.tv_func21_title);
        mRl_health_func22 = (RelativeLayout) headView.findViewById(R.id.rl_health_func22);
        mIv_func22 = (ImageView) headView.findViewById(R.id.iv_func22);
        mTv_func22_title = (TextView) headView.findViewById(R.id.tv_func22_title);
        mIv_func22_unuse = (ImageView) headView.findViewById(R.id.iv_func22_unuse);
        mTabs = (SlidingTabStrip) headView.findViewById(R.id.tabs);
        mPager=(ViewPager)headView.findViewById(R.id.pager);

        initFunctionView();

        if (SystemUtil.isTintStatusBarAvailable(mBaseActivity)) {
            mTv_titlebar.setPadding(0, SystemUtil.getStatusBarHeight(), 0, 0);
            mTv_titlebar.getLayoutParams().height=SystemUtil.getStatusBarHeight()+getResources().getDimensionPixelSize(com.wondersgroup.hs.healthcloud.common.R.dimen.actionbar_height);
        }
    }

    private void initEvent() {
        mPull_view.setOnHeaderRefreshListener(this);
        mPull_view.setOnFooterRefreshListener(this);
        final ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.bc1));
        drawable.setAlpha(0);
        mTv_titlebar.setBackgroundDrawable(drawable);
        mRecycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] location=new int[2];
                int[] location2=new int[2];
                mTabs.getLocationOnScreen(location);
                mHealth_banner_vp.getLocationOnScreen(location2);
                int stauteBarHeight= SystemUtil.getStatusBarHeight();
                int titleBarheight=getResources().getDimensionPixelSize(com.wondersgroup.hs.healthcloud.common.R.dimen.actionbar_height);
                int top=stauteBarHeight+titleBarheight;
                if (location[1] <= top) {
                    if (mTabs.getCurrentPosition()==0) {
                        mTabs_hidden.scrollTo(0,0);
                    }
                    mTabs_hidden.setVisibility(View.VISIBLE);
                } else {
                    mTabs_hidden.setVisibility(View.GONE);
                }
                //titlebar下划线
                if (Math.abs(location2[1]) >= mHealth_banner_vp.getHeight() - top||mTabs_hidden.getVisibility()==View.VISIBLE) {
                    mView_titlebar_below_line.setVisibility(View.VISIBLE);
                } else {
                    mView_titlebar_below_line.setVisibility(View.GONE);
                }
//                LogUtils.d("szy-->mHealth_banner_vp.locationY="+location2[1]);
                //设置titlebar背景，标题色
                if (mHeadView.getHeight() != 0&&mTv_titlebar!=null) {
                    float delta = -(float) mHeadView.getTop() / titleBarheight;
                    delta = delta > 1 ? 1 : delta;
                    drawable.setAlpha((int) (delta * 255));
                    mTv_titlebar.setBackgroundDrawable(drawable);

                    TextPaint paint=mTv_titlebar.getPaint();
                    int alpha=10+(int)(delta * 245);
                    String alphaStr=Integer.toHexString(alpha);
                    alphaStr=alphaStr.length()==1?alphaStr+"0":alphaStr;
//                    LogUtils.d("szy-->textAlpha="+alphaStr);
                    if (alpha > 20) {
                        mTv_titlebar.setTextColor(Color.parseColor("#"+alphaStr+"333333"));
                    } else {
                        mTv_titlebar.setTextColor(Color.parseColor("#"+alphaStr+"ffffff"));
                    }
                    mTv_titlebar.invalidate();
                }
            }
        });
        mRecycler_view.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                    SchemeUtil.startActivity(mBaseActivity, mArticleItems.get(position).url);
            }
        });

        mPull_view.setOnHeaderRefreshStateChangeListener(new PullToRefreshView.OnHeaderRefreshStateChangeListener() {
            @Override
            public void onPrepareToRefresh() {
                LogUtils.d("szy--onPrepareToRefresh");
                if (!mIsPrepareToRefresh) {
                    mTv_titlebar.setVisibility(View.GONE);
                    mIsPrepareToRefresh = true;
                }

            }

            @Override
            public void onRefreshing() {
                LogUtils.d("szy--onRefreshing");
                mTv_titlebar.setVisibility(View.GONE);
                mIsPrepareToRefresh = false;
            }

            @Override
            public void onRefreshFinish() {
                LogUtils.d("szy--onRefreshFinish");
                mTv_titlebar.setVisibility(View.VISIBLE);
                mIsPrepareToRefresh = false;
            }
        });
        mPull_view.setOnPullBaseScrollChangedListener(new PullToRefreshView.OnPullBaseScrollChanged() {
            @Override
            public void onPullBaseScrollChanged(int l, int t, int oldl, int oldt) {
                LogUtils.d("szy--top="+t);
                if (t >= 0) {
                    mTv_titlebar.setVisibility(View.VISIBLE);
                }
            }
        });

        mTabs.setOnTabChangeListener(new SlidingTabStrip.OnTabChangeListener() {
            @Override
            public void OnTabChanged(int position) {
                mTabs_hidden.setCurrentTab(position);
                if (mArticleTabListResponseCache != null&&mArticleTabListResponseCache.list.size()>0) {
                    bindArticleTabs(mArticleTabListResponseCache.list, position, Constant.TYPE_RELOAD);
                }
            }
        });
        mTabs_hidden.setOnTabChangeListener(new SlidingTabStrip.OnTabChangeListener() {
            @Override
            public void OnTabChanged(int position) {
                mTabs.setCurrentTab(position);
                if (mArticleTabListResponseCache != null&&mArticleTabListResponseCache.list.size()>0) {
                    bindArticleTabs(mArticleTabListResponseCache.list, position, Constant.TYPE_RELOAD);
                }
            }
        });

        mHealth_func1.setOnClickListener(this);
        mRl_health_func21.setOnClickListener(this);
        mRl_health_func22.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBitmapTools = new BitmapTools(mBaseActivity);
        mHealthManager = new HealthManager();
        mHealth_banner_vp.getLayoutParams().height = SystemUtil.getScreenWidth() * 2 / 5;
        mListResponse = new ArticleListResponse();
        mHealthHomeEntityCache = mHealthManager.getHealthHomeEntityCache();
        mArticleTabListResponseCache = mHealthManager.getArticleTabListResponseCache();
        if (mHealthHomeEntityCache != null) {
            bindFunctionsAndBanners(mHealthHomeEntityCache);
        }
        if (mArticleTabListResponseCache != null) {
            bindArticleTabs(mArticleTabListResponseCache.list,mTabs.getCurrentPosition(),Constant.TYPE_INIT);
        }
        loadData(Constant.TYPE_INIT);
    }


    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        loadData(Constant.TYPE_RELOAD);
    }
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        loadArticles(Constant.TYPE_NEXT,mTabs.getCurrentPosition());
    }

    private void loadData(int type) {
        loadBannersAndFunctions();
        loadArticles(type,mTabs.getCurrentPosition());
    }

    private void loadBannersAndFunctions() {
        mHealthManager.getHealthHome(new ResponseCallback<HealthHomeEntity>(){
            @Override
            public void onSuccess(HealthHomeEntity healthHomeEntity) {
                super.onSuccess(healthHomeEntity);
                if (healthHomeEntity != null) {
                    mHealthManager.saveHealthHomeEntityCache(healthHomeEntity);
                    bindFunctionsAndBanners(healthHomeEntity);
                }
            }
        });
    }

    private void bindFunctionsAndBanners(HealthHomeEntity healthHomeEntity) {
        bindBanner(healthHomeEntity);
        bindFunction(healthHomeEntity.functionIcons);
    }

    private void bindBanner(final HealthHomeEntity entity) {
        if (entity.banners != null && entity.banners.size() > 0) {
            mHealth_banner_vp.setVisibility(View.VISIBLE);
            final List<HealthHomeEntity.Banner> banners = entity.banners;
            List<String> images = new ArrayList<String>();
            for (HealthHomeEntity.Banner banner : banners) {
                images.add(banner.imgUrl);
            }
            mHealth_banner_vp.initViewPager(mBitmapTools, images, new ViewPagerWithIndicator.OnViewClickListener() {
                @Override
                public void onViewClick(View view, int pos) {
                    if (banners.get(pos) != null) {
                        SchemeUtil.startActivity(mBaseActivity, banners.get(pos).hoplink);
                    }
                }
            });
            mHealth_banner_vp.startAutoScroll();
        } else {
//            mHealth_banner_vp.setVisibility(View.GONE);
            //四川需求，后台没配，显示一张占位图
            mHealth_banner_vp.setVisibility(View.VISIBLE);
            List<String> images = new ArrayList<String>();
            images.add("");
            mHealth_banner_vp.initViewPager(mBitmapTools, images, null);
        }
    }

    private void bindFunction(List<HealthHomeEntity.Function> funcs) {

        if (funcs != null && funcs.size() > 0) {
            mFuc_ll.setVisibility(View.VISIBLE);
            for (int i = 0; i < funcs.size(); i++) {
                if (i < 3) {
                    HealthHomeEntity.Function entity = funcs.get(i);
                    mBitmapTools.display(funcImgs[i], entity.imgUrl, BitmapTools.SizeType.SMALL);
                    funcTitle[i].setText(entity.mainTitle);
//                    funcDesc[i].setText(entity.subTitle);
                    fucll[i].setTag(entity.hoplink);
                }
            }
        } else {
            mFuc_ll.setVisibility(View.GONE);
        }
    }

    private void loadArticles(final int type, final int tabPosition) {
        if (Constant.TYPE_NEXT == type) {
            mHealthManager.getZixunList(mCat_id, mMoreParams, new ResponseCallback<ArticleListResponse>() {
                @Override
                public void onSuccess(ArticleListResponse t) {
                    super.onSuccess(t);
                    if (t != null) {
                        if (tabPosition == mTabs.getCurrentPosition()) {
                            if (mArticleTabListResponseCache != null) {
                                ArticleTab articleTab=mArticleTabListResponseCache.list.get(tabPosition);
                                articleTab.more=t.more;
                                articleTab.more_params = t.more_params;
                                articleTab.list.addAll(t.getList());
                            }
                            bindArticleList(type,t.getList(),t.more,t.more_params);
                        }
                    }
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    if (type == Constant.TYPE_NEXT) {
                        mPull_view.onFooterRefreshComplete();
                    }
                    mPull_view.setLoadMoreEnable(mMore);
                }
            });
        } else {
            mHealthManager.getZixunTitle(new ResponseCallback<ArticleTab>() {

                @Override
                public void onSuccess(List<ArticleTab> list) {
                    super.onSuccess(list);
                    if (list != null&&list.size()>0) {
                        //save cache
                        if (mArticleTabListResponseCache == null) {
                            mArticleTabListResponseCache = new ArticleTabListResponse();
                        }
                        mArticleTabListResponseCache.list.clear();
                        mArticleTabListResponseCache.list.addAll(list);
                        mHealthManager.saveArticleTabs(mArticleTabListResponseCache);

                        bindArticleTabs(list, tabPosition, type);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    super.onFailure(e);
                    if (mArticleTabListResponseCache != null) {
                        bindArticleTabs(mArticleTabListResponseCache.list, tabPosition, type);
                    }
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    if (type == Constant.TYPE_NEXT) {
                        mPull_view.onFooterRefreshComplete();
                    } else {
                        mPull_view.onHeaderRefreshComplete();
                    }
                    mPull_view.setLoadMoreEnable(mMore);
                }
            });
        }
    }

    private void bindArticleTabs(List<ArticleTab> list, int tabPosition, int type) {
        initArticleTabs(list,tabPosition);
        mMore= list.get(tabPosition).more;
        mMoreParams = list.get(tabPosition).more_params;
        mCat_id = list.get(tabPosition).cat_id;
        bindArticleList(type,list.get(tabPosition).list,mMore,mMoreParams);
    }

    private void initArticleTabs(List<ArticleTab> tabs, int tabPosition) {
        if(tabs != null && tabs.size() > 0){
            mTabList.clear();
            if (tabs.size() > 4) {
                for (int i=0;i<4;i++)
                    mTabList.add(tabs.get(i).cat_name);
            } else {
                for (ArticleTab tab: tabs) {
                    mTabList.add(tab.cat_name);
                }
            }

            mTabs.setViews(mTabList);
            mTabs.setCurrentTab(tabPosition);
            mTabs_hidden.setViews(mTabList);
            mTabs_hidden.setCurrentTab(tabPosition);
        }
    }

    private void bindArticleList(int type,List<ArticleItem> list, boolean more, HashMap<String,String> more_params) {
        if (list != null) {
            if (type == Constant.TYPE_NEXT) {
                mArticleItems.addAll(list);
            } else if (type == Constant.TYPE_INIT || type == Constant.TYPE_RELOAD) {
                mArticleItems.clear();
                mArticleItems.addAll(list);
            }
        } else {
            mArticleItems.clear();
        }
        mMore = more;
        mPull_view.setLoadMoreEnable(mMore);
        mMoreParams = more_params;
        if (mArticleItems.size()==0) {
            if (mArticleAdapter != null) {
                mArticleAdapter.notifyDataSetChanged();
            }
        } else {
            if (mArticleAdapter == null) {
                mArticleAdapter = new ArticleAdapter(mBaseActivity,mArticleItems);
                mRecycler_view.setAdapter(mArticleAdapter);
            } else {
                mArticleAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.health_func1:
                Intent intent = new Intent(mBaseActivity, AuthChooseActivity.class);
                intent.putExtra(AuthChooseActivity.WAY_FROM, 1);
                intent.putExtra(AuthChooseActivity.EXTRA_URL, (String) v.getTag()+ "?uid=" + UserManager.getInstance().getUser().uid+"&page=0");
                startActivity(intent);
                break;
            case R.id.rl_health_func21:
                if (v.getTag() instanceof String) {
                    SchemeUtil.startActivity(mBaseActivity, (String) v.getTag());
                }
                break;
            case R.id.rl_health_func22:
                startActivity(new Intent(mBaseActivity,SignFamilyDoctorActivity.class));
                break;
        }
    }
}
