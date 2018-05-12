package com.wonders.health.venus.open.user.module.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.AreaEntity;
import com.wonders.health.venus.open.user.entity.ArticleItem;
import com.wonders.health.venus.open.user.entity.HomeBannerFuncEntity;
import com.wonders.health.venus.open.user.entity.event.AccountChangeEvent;
import com.wonders.health.venus.open.user.logic.AreaManager;
import com.wonders.health.venus.open.user.logic.HomeManager;
import com.wonders.health.venus.open.user.logic.LocationManager;
import com.wonders.health.venus.open.user.module.MainActivity;
import com.wonders.health.venus.open.user.module.article.ArticleAdapter;
import com.wonders.health.venus.open.user.module.home.search.HomeSearchActivity;
import com.wonders.health.venus.open.user.module.mine.auth.AuthChooseActivity;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;
import com.wondersgroup.hs.healthcloud.common.view.ViewPagerWithIndicator;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/*
 * Created by sunning on 2016/11/1.
 */

public class HomeFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, View.OnClickListener {
    private static final int REQUSET_CODE = 110;

    private PullToRefreshView mPull_view;
    private BaseRecyclerView mRecycler_view;
    private ArticleAdapter mArticleAdapter;

    private ViewPagerWithIndicator mHomeBannerVp;
    private TextView mTvLocation;
    private TextView mTvSearch;
    private TextView mTvMessage;
    private LinearLayout mFucLl;

    private LinearLayout mHomeArticleLl;
    private View mHeaderBorder;

    private BitmapTools mBitmapTools;

    private HomeManager mHomeManager;
    private HomeBannerFuncEntity mBannerFuncCache;

    private View[] fucll = new View[6];
    private TextView[] funcTitle = new TextView[6];
    private TextView[] funcDesc = new TextView[6];
    private ImageView[] funcImgs = new ImageView[6];

    private String areaName = AreaManager.AREA_NAME_CHENGDU, areaCode = AreaManager.AREA_CODE_CHENGDU;
    private String latitude, longitude;
    private View mHeaderView;

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
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    protected void initViews() {
        mPull_view = (PullToRefreshView) findViewById(R.id.pull_view);
        mRecycler_view = (BaseRecyclerView) findViewById(R.id.recycler_view);

       // mTvLocation = (TextView) findViewById(R.id.tv_location);
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mTvMessage = (TextView) findViewById(R.id.tv_message);
        mHeaderBorder = findViewById(R.id.layout_header_border);

        mPull_view.setLoadMoreEnable(false);
        mPull_view.setOnHeaderRefreshListener(this);

        mHeaderView = LayoutInflater.from(mBaseActivity).inflate(R.layout.layout_home_head, null);
        mRecycler_view.addHeader(mHeaderView);

        initHeaderView(mHeaderView);
        initTopViewAnima();

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mHomeManager = new HomeManager();
        mBitmapTools = new BitmapTools(mBaseActivity);
        mHomeBannerVp.getLayoutParams().height = SystemUtil.getScreenWidth() / 2;

        //绑定缓存数据
        mBannerFuncCache = mHomeManager.getBannerAndFunctionAdsCache();
        if (mBannerFuncCache != null) {
            bindHome(mBannerFuncCache);
        }

//        final AreaEntity localPoint = mHomeManager.getLocalPoint();
//        if (localPoint != null) {
//            mTvLocation.setText(formatLocationName(localPoint.name));
//        }

        mRecycler_view.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {

                if (mBannerFuncCache != null && mBannerFuncCache.news != null) {
                    SchemeUtil.startActivity(mBaseActivity, mBannerFuncCache.news.get(position).url);

                }
            }
        });

        loadData(mBannerFuncCache == null ? Constant.TYPE_INIT : Constant.TYPE_RELOAD);
        LoadNavigationBar();

        startLocation();
    }

    private void initHeaderView(View headView) {
        mHomeBannerVp = (ViewPagerWithIndicator) headView.findViewById(R.id.home_banner_vp);

        mFucLl = (LinearLayout) headView.findViewById(R.id.fuc_ll);
        mHomeArticleLl = (LinearLayout) headView.findViewById(R.id.home_article_ll);

        headView.findViewById(R.id.home_article_more).setOnClickListener(this);
        initFunctionView(headView);
        initHeaderViewEvent();

        if (SystemUtil.isTintStatusBarAvailable(mBaseActivity)) {
            mHeaderBorder.setPadding(0, SystemUtil.getStatusBarHeight(), 0, 0);
        }
    }

    private void initFunctionView(View headView) {
        fucll[0] = headView.findViewById(R.id.ll_fucn1);
        funcTitle[0] = (TextView) headView.findViewById(R.id.tv_func1_title);
        funcDesc[0] = (TextView) headView.findViewById(R.id.tv_func1_desc);
        funcImgs[0] = (ImageView) headView.findViewById(R.id.iv_func1);

        fucll[1] = headView.findViewById(R.id.ll_fucn2);
        funcTitle[1] = (TextView) headView.findViewById(R.id.tv_func2_title);
        funcDesc[1] = (TextView) headView.findViewById(R.id.tv_func2_desc);
        funcImgs[1] = (ImageView) headView.findViewById(R.id.iv_func2);

        fucll[2] = headView.findViewById(R.id.ll_fucn3);
        funcTitle[2] = (TextView) headView.findViewById(R.id.tv_func3_title);
        funcDesc[2] = (TextView) headView.findViewById(R.id.tv_func3_desc);
        funcImgs[2] = (ImageView) headView.findViewById(R.id.iv_func3);

        fucll[3] = headView.findViewById(R.id.ll_fucn4);
        funcTitle[3] = (TextView) headView.findViewById(R.id.tv_func4_title);
        funcDesc[3] = (TextView) headView.findViewById(R.id.tv_func4_desc);
        funcImgs[3] = (ImageView) headView.findViewById(R.id.iv_func4);

        fucll[4] = headView.findViewById(R.id.ll_fucn5);
        funcTitle[4] = (TextView) headView.findViewById(R.id.tv_func5_title);
        funcDesc[4] = (TextView) headView.findViewById(R.id.tv_func5_desc);
        funcImgs[4] = (ImageView) headView.findViewById(R.id.iv_func5);

        fucll[5] = headView.findViewById(R.id.ll_fucn6);
        funcTitle[5] = (TextView) headView.findViewById(R.id.tv_func6_title);
        funcDesc[5] = (TextView) headView.findViewById(R.id.tv_func6_desc);
        funcImgs[5] = (ImageView) headView.findViewById(R.id.iv_func6);
    }

    private void initHeaderViewEvent() {
       // mTvLocation.setOnClickListener(this);
        mTvSearch.setOnClickListener(this);
        mTvMessage.setOnClickListener(this);

        fucll[0].setOnClickListener(this);
        fucll[1].setOnClickListener(this);
        fucll[2].setOnClickListener(this);
        fucll[3].setOnClickListener(this);
        fucll[4].setOnClickListener(this);
        fucll[5].setOnClickListener(this);
    }

    private void initTopViewAnima() {
        final ColorDrawable drawable = new ColorDrawable(mBaseActivity.getResources().getColor(R.color.bc1));
        drawable.setAlpha(0);
        mTvSearch.getBackground().setAlpha(175);
        mHeaderBorder.setBackgroundDrawable(drawable);

        mRecycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mHeaderView.getHeight() != 0 && mHeaderBorder.getHeight() != 0) {
                    float delta = -(float) mHeaderView.getTop() / mHeaderBorder.getHeight();
                    delta = delta > 1 ? 1 : delta;
                    delta = delta < 0 ? 0 : delta;
                    float rent = Math.abs(1 - 2 * delta);
//                    int alpha = (int) (255 * rent + 30 * (1 - rent)); //保留至少30透明度
                    int alpha = (int) (255 * rent);

                    String alphaStr = Integer.toHexString(alpha);
                    alphaStr = alphaStr.length() == 1 ? "0" + alphaStr : alphaStr;
                  //  mTvLocation.setTextColor(delta > 1 / 2f ? Color.parseColor("#" + alphaStr + "333333") : Color.parseColor("#" + alphaStr + "ffffff"));
                    mTvSearch.setHintTextColor(delta > 1 / 2f ? Color.parseColor("#" + alphaStr + "333333") : Color.parseColor("#" + alphaStr + "ffffff"));

                    drawable.setAlpha((int) (delta * 255));
                    int search_icon_res = delta > 1 / 2f ? R.mipmap.icon_search_dark : R.mipmap.icon_search_white;
                    mTvSearch.setCompoundDrawablesWithIntrinsicBounds(search_icon_res, 0, 0, 0);
                    Drawable mIcon_search = mTvSearch.getCompoundDrawables()[0];
                    if (mIcon_search != null) {
                        mIcon_search.setAlpha(alpha);
                    }

                    int location_icon_res = delta > 1 / 2f ? R.mipmap.icon_location_down_dark : R.mipmap.icon_location_down_white;
                 //   mTvLocation.setCompoundDrawablesWithIntrinsicBounds(0, 0, location_icon_res, 0);
                   // Drawable mIcon_location = mTvLocation.getCompoundDrawables()[2];
//                    if (mIcon_location != null) {
//                        mIcon_location.setAlpha(alpha);
//                    }
                    mHeaderBorder.setBackgroundDrawable(drawable);
                    mTvSearch.setBackgroundResource(delta > 1 / 2f ? R.drawable.btn_radius_stroke_home_dark : R.drawable.btn_radius_stroke_white_gray);
                    mTvSearch.getBackground().setAlpha(175 + (int) (80 * delta));
                }
            }
        });

        mPull_view.setOnPullBaseScrollChangedListener(new PullToRefreshView.OnPullBaseScrollChanged() {
            @Override
            public void onPullBaseScrollChanged(int l, int t, int oldl, int oldt) {
                if (t < 0 && mHeaderBorder.getVisibility() == View.VISIBLE) {
                    AlphaAnimation animation = new AlphaAnimation(1, 0);
                    animation.setDuration(300);
                    mHeaderBorder.clearAnimation();
                    mHeaderBorder.startAnimation(animation);
                    mHeaderBorder.setVisibility(View.GONE);
                } else if (t >= 0 && mHeaderBorder.getVisibility() == View.GONE) {
                    AlphaAnimation animation = new AlphaAnimation(0, 1);
                    animation.setDuration(300);
                    mHeaderBorder.clearAnimation();
                    mHeaderBorder.startAnimation(animation);
                    mHeaderBorder.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void startLocation() {
        LocationManager.getInstance().startLocation(new LocationManager.CallBack() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(BDLocation bdLocation) {
                if (bdLocation != null) {
                    LocationManager.getInstance().stopLocation();
                    String province = bdLocation.getProvince();
                    if (!TextUtils.isEmpty(province)) {
                        LocationManager.getInstance().stopLocation();
                        latitude = String.valueOf(bdLocation.getLatitude());
                        longitude = String.valueOf(bdLocation.getLongitude());
                        if (province.contains("四川")) { //在四川省
                            String district_name = bdLocation.getCity();
                            areaName = district_name;
                            areaCode = AreaManager.getInstance().getAreaCodeByName(areaName);
                            AreaManager.getInstance().setSpecArea(areaCode);
                        }
                       // mTvLocation.setText(formatLocationName(areaName));
//                        AreaManager.getInstance().saveLocal(new AreaEntity(areaCode, areaName, latitude, longitude));
                    }
                }

            }

            @Override
            public void onError() {
            }
        });
    }

    private void LoadNavigationBar() {
        mHomeManager.appNavigationBar(new ResponseCallback<String>() {
            @Override
            public void onSuccess(List<String> list) {
                super.onSuccess(list);
                if (list != null && !list.isEmpty()) {
                    ((MainActivity) mBaseActivity).mDynamicTabBar.display(list);
                }
            }

            @Override
            public boolean isShowNotice() {
                return false;
            }
        });
    }

    /**
     * banner 广告 功能区
     */
    private void loadBannerAndFuctionAds(int type) {
        mHomeManager.getBannerAndFunctionAds(new FinalResponseCallback<HomeBannerFuncEntity>(this, type) {
            @Override
            public void onSuccess(HomeBannerFuncEntity entity) {
                super.onSuccess(entity);
                if (entity != null) {
                    mBannerFuncCache = entity;
                    mHomeManager.saveBannerAndFunctionAds(entity);
                    bindHome(entity);
                }

            }

            @Override
            public void onReload() {
                super.onReload();
                loadData(Constant.TYPE_INIT);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mPull_view.onHeaderRefreshComplete();
            }
        });

    }

    private void loadData(int type) {
        loadBannerAndFuctionAds(type);
    }

    private void bindHome(HomeBannerFuncEntity bannerFunc) {
        bindBanner(bannerFunc);
        bindFunction(bannerFunc.functionIcons);
        bindNews(bannerFunc.news);
    }

    private void bindBanner(final HomeBannerFuncEntity entity) {
        if (entity.banners != null && entity.banners.size() > 0) {
            mHomeBannerVp.setVisibility(View.VISIBLE);
            final List<HomeBannerFuncEntity.BannerEntity> banners = entity.banners;
            List<String> images = new ArrayList<String>();
            for (HomeBannerFuncEntity.BannerEntity banner : banners) {
                images.add(banner.imgUrl);
            }
            mHomeBannerVp.initViewPager(mBitmapTools, images, new ViewPagerWithIndicator.OnViewClickListener() {
                @Override
                public void onViewClick(View view, int pos) {
                    if (banners.get(pos) != null) {
                        SchemeUtil.startActivity(mBaseActivity, banners.get(pos).hoplink);
                    }
                }
            });
            mHomeBannerVp.startAutoScroll();
        } else {
//            mHomeBannerVp.setVisibility(View.GONE);
            //四川需求，后台没配，显示一张占位图
            mHomeBannerVp.setVisibility(View.VISIBLE);
            List<String> images = new ArrayList<String>();
            images.add("");
            mHomeBannerVp.initViewPager(mBitmapTools, images, null);
        }

    }

    private void bindFunction(List<HomeBannerFuncEntity.FunctionEntity> funcs) {

        if (funcs != null && funcs.size() > 0) {
            mFucLl.setVisibility(View.VISIBLE);
            for (int i = 0; i < funcs.size(); i++) {
                if (i < fucll.length && fucll[i] != null) {
                    HomeBannerFuncEntity.FunctionEntity entity = funcs.get(i);
                    mBitmapTools.display(funcImgs[i], entity.imgUrl, BitmapTools.SizeType.SMALL);
                    funcTitle[i].setText(entity.mainTitle);
                    funcDesc[i].setText(entity.subTitle);
                    fucll[i].setTag(entity);
                }
            }
        } else {
            mFucLl.setVisibility(View.GONE);
        }
    }

    private void bindNews(List<ArticleItem> news) {
        List<ArticleItem> newsList = news;
        if (newsList != null && newsList.size() > 0) {
            mHomeArticleLl.setVisibility(View.VISIBLE);
            if (mArticleAdapter == null) {
                mArticleAdapter = new ArticleAdapter(mBaseActivity, newsList);
                mRecycler_view.setAdapter(mArticleAdapter);
            } else {
                mArticleAdapter.refreshList(newsList);
            }
        } else {
            mRecycler_view.setAdapter(new ArticleAdapter(mBaseActivity, new ArrayList<ArticleItem>()));
            mHomeArticleLl.setVisibility(View.GONE);
        }
    }

    private String formatLocationName(String name) {
        if (!TextUtils.isEmpty(name) && name.length() > 4) {
            return name.substring(0, 4) + "...";
        }
        return name;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == mBaseActivity.RESULT_OK) {
            if (requestCode == REQUSET_CODE) {//定位返回
                if (data != null) {
                    AreaEntity entity = (AreaEntity) data.getSerializableExtra(AreaSelectActivity.EXTRA_AREA);
                  //  mTvLocation.setText(TextUtils.isEmpty(entity.name) ? "成都市" : formatLocationName(entity.name));
                    areaCode = entity.area_id;
                    latitude = entity.latitude;
                    longitude = entity.longitude;
                    AreaManager.getInstance().setSpecArea(areaCode);
//                    AreaManager.getInstance().saveLocal(entity);
                    LocationManager.getInstance().stopLocation();
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_location:
//                startActivityForResult(new Intent(mBaseActivity, AreaSelectActivity.class), REQUSET_CODE);
//                break;
            case R.id.tv_search:
                startActivity(new Intent(mBaseActivity, HomeSearchActivity.class));
//                startActivity(new Intent(mBaseActivity, MeasurePointActivity.class));
                break;
            case R.id.tv_message:
                break;
            case R.id.home_article_more:
                ((MainActivity) mBaseActivity).mDynamicTabBar.processShowPage(2);
                break;
            case R.id.ll_fucn1:
            case R.id.ll_fucn2:
            case R.id.ll_fucn3:
            case R.id.ll_fucn4:
            case R.id.ll_fucn5:
            case R.id.ll_fucn6:
                if (v.getTag() != null && v.getTag() instanceof HomeBannerFuncEntity.FunctionEntity) {
                    HomeBannerFuncEntity.FunctionEntity function = (HomeBannerFuncEntity.FunctionEntity) v.getTag();
                    if (function == null || TextUtils.isEmpty(function.hoplink)) {
                        return;
                    }
                    Uri uri = Uri.parse(function.hoplink);
                    if (uri != null && "2".equals(uri.getQueryParameter("loginOrVerify"))) {
                        Intent intent = new Intent(mBaseActivity, AuthChooseActivity.class);
                        intent.putExtra(AuthChooseActivity.WAY_FROM, 1);
                        intent.putExtra(AuthChooseActivity.EXTRA_URL, function.hoplink);
                        startActivity(intent);
                    } else {
                        SchemeUtil.startActivity(mBaseActivity, function.hoplink);
                    }
                }
                break;
        }
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        loadData(Constant.TYPE_RELOAD);
    }

    public void onEvent(AccountChangeEvent event) {
//        if (event != null && event.isLogin) {
//
//        }
        loadData(Constant.TYPE_RELOAD);
    }

}
