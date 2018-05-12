package com.wonders.health.venus.open.user.module.home.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.DoctorListVO;
import com.wonders.health.venus.open.user.logic.SearchManager;
import com.wonders.health.venus.open.user.module.home.registration.RegistrationDoctorDetailActivity;
import com.wonders.health.venus.open.user.module.home.registration.response.DoctorListResponse;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.CircleImageView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.wonders.health.venus.open.user.R.id.tv_item_doctorlist_duty;

/**
 * 首页搜索-医生列表--加载更多
 * Created by songzhen on 2016/11/10.
 */
public class SearchDoctorListActivity extends BaseActivity {

    public static String EXTRA_KEY = "key";
    private LinearLayout layout_search;
    private BaseRecyclerView mRecyclerView;
    private PullToRefreshView mPullView;
    private DoctorAdapter mAdapter;

    private HashMap<String, String> mMoreParams;
    private boolean mIsMore;
    private List<DoctorListVO> mItems = new ArrayList<>();

    private SearchManager mManager;
    private String key;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_homesearchresult_list);
        layout_search = (LinearLayout) findViewById(R.id.layout_search);
        mRecyclerView = (BaseRecyclerView) findViewById(R.id.recycler_view);
        mPullView = (PullToRefreshView) findViewById(R.id.pull_view);
        mTitleBar.setTitle("全部医生");
        layout_search.setVisibility(View.GONE);
        addListener();
    }

    private void addListener() {
        mPullView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getDoctorList(Constant.TYPE_RELOAD);
            }
        });
        mPullView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                getDoctorList(Constant.TYPE_NEXT);
            }
        });
        mRecyclerView.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                DoctorListVO docotor = mItems.get(position);
                if (docotor != null) {
                    RegistrationDoctorDetailActivity.startDoctorDetail(SearchDoctorListActivity.this, docotor.hosOrgCode, docotor.hosDoctCode, docotor.hosDeptCode,true);
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mManager = new SearchManager();
        key = getIntent().getStringExtra(EXTRA_KEY);
        getDoctorList(Constant.TYPE_INIT);
    }

    //获取医院列表
    private void getDoctorList(final int type) {
        HashMap<String, String> params = null;
        if (type == Constant.TYPE_NEXT) {
            params = mMoreParams;
        }
        mManager.SearchDoctorList(params, key, new FinalResponseCallback<DoctorListResponse>(this, type) {
            @Override
            public void onSuccess(DoctorListResponse t) {
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
                getDoctorList(Constant.TYPE_INIT);
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
    }


    private void bindView(int type, int itemCount) {
        if (mAdapter == null) {
            mAdapter = new DoctorAdapter(this, mItems);
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

    //名称显示...格式化
    private String formatName(String name,int size){
        if (!TextUtils.isEmpty(name)&&name.length()>size){
            return name.substring(0,size)+"...";
        }
        return name;
    }

    class DoctorAdapter extends BaseAdapter<DoctorListVO,DoctorAdapter.DoctorListHolder>{

        private BitmapTools mBitmaptool;
        public DoctorAdapter(Context context, List<DoctorListVO> list) {
            super(context, list);
            mBitmaptool = new BitmapTools(context);
        }

        @Override
        public DoctorListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.registration_doctor_list_item,parent,false);
            return new DoctorListHolder(view);
        }

        @Override
        public void onBindViewHolder(DoctorListHolder holder, int position) {
            DoctorListVO entity =  getItem(position);
            holder.guahao.setText("");
//            holder.guahao.setBackgroundResource(R.mipmap.icon_guahao);
            holder.guahao.setVisibility(View.GONE);
            holder.iv_guahao.setVisibility(View.VISIBLE);
            if (entity != null) {
                holder.line.setVisibility(View.GONE);
                holder.ll_item_doctorlist_4.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(entity.gender)&&"2".equals(entity.gender)){
                    mBitmaptool.display(holder.headImg, entity.headphoto, BitmapTools.SizeType.MEDIUM,R.mipmap.ic_doctor_default_woman,null);
                }else {
                    mBitmaptool.display(holder.headImg, entity.headphoto, BitmapTools.SizeType.MEDIUM, R.mipmap.ic_doctor_default_man, null);
                }
                holder.name.setText(formatName(entity.doctorName +"",5));
                holder.zhicheng.setText(entity.doctorTitle+"");
                holder.hospital.setText(formatName(entity.hosName +"",10));
                holder.keshi.setText(entity.deptName+"");
                holder.num.setText("接诊量 " + entity.orderCount);
                holder.expert.setText(entity.expertin+"");
            }
        }

        class DoctorListHolder extends RecyclerView.ViewHolder {
            TextView tv_item_doctorList_duty;
            TextView expert;
            CircleImageView headImg;
            TextView name, zhicheng, num;
            LinearLayout ll_item_doctorlist_4; //医院科室条目
            TextView hospital;
            TextView keshi;
            TextView guahao;
            ImageView iv_guahao;
            View line;

            DoctorListHolder(View itemView) {
                super(itemView);
                headImg = (CircleImageView) itemView.findViewById(R.id.iv_item_doctorlist);
                name = (TextView) itemView.findViewById(R.id.tv_item_doctorlist_name);
                zhicheng = (TextView) itemView.findViewById(R.id.tv_item_doctorlist_title);
                num = (TextView) itemView.findViewById(R.id.tv_item_doctorlist_num);
                expert = (TextView) itemView.findViewById(R.id.tv_item_doctorlist_expert);
                tv_item_doctorList_duty = (TextView) itemView.findViewById(tv_item_doctorlist_duty);
                ll_item_doctorlist_4 = (LinearLayout) itemView.findViewById(R.id.ll_item_doctorlist_4);
                hospital = (TextView) itemView.findViewById(R.id.tv_item_doctorlist_hospital);
                keshi = (TextView) itemView.findViewById(R.id.tv_item_doctorlist_keshi);
                guahao = (TextView) itemView.findViewById(R.id.tv_item_doctorlist_duty);
                iv_guahao = (ImageView) itemView.findViewById(R.id.iv_guahao);
                line = itemView.findViewById(R.id.line);
            }
        }
    }

}
