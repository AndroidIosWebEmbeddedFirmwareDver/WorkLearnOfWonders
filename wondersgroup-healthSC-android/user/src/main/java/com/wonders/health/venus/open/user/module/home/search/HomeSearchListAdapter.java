package com.wonders.health.venus.open.user.module.home.search;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ArticleItem;
import com.wonders.health.venus.open.user.entity.DoctorListVO;
import com.wonders.health.venus.open.user.entity.HospitalInfo;
import com.wonders.health.venus.open.user.entity.SearchResultInfo;
import com.wonders.health.venus.open.user.module.health.HospitalHomeActivity;
import com.wonders.health.venus.open.user.module.home.registration.RegistrationDoctorDetailActivity;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.FitHeightListView;

import java.util.ArrayList;

/**
 * 搜索结果adapter
 * Created by songzhen on 2016/11/9.
 */
public class HomeSearchListAdapter extends BaseAdapter{
    private ArrayList<SearchResultInfo> mItems;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String key;
    private String from;

    public HomeSearchListAdapter(Context context, ArrayList<SearchResultInfo> mItems,String key,String from){
        this.mContext = context;
        this.mItems = mItems;
        mLayoutInflater = LayoutInflater.from(context);
        this.key = key;
        this.from = from;
    }
    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public SearchResultInfo getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.layout_home_searchresult, parent, false);
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        TextView location = (TextView) view.findViewById(R.id.tv_location);
        FitHeightListView lv_homeresult = (FitHeightListView) view.findViewById(R.id.lv_homeresult);
        TextView loadmore = (TextView) view.findViewById(R.id.tv_loadmore);
        final SearchResultInfo info = mItems.get(position);
        if (info!=null){
            title.setText(info.title);
            loadmore.setText(info.loadmore);
            if (info.type==SearchResultInfo.TYPE_HOSPITAL){
                SearchHospitalListAdapter adapter = new SearchHospitalListAdapter(mContext,info.hospitals.getList(),key);
                lv_homeresult.setAdapter(adapter);
                if (info.hospitals.more){
                    loadmore.setVisibility(View.VISIBLE);
                }else {
                    loadmore.setVisibility(View.GONE);
                }
            }else if (info.type ==SearchResultInfo.TYPE_DOCTOR){
                SearchDoctorListAdapter adapter = new SearchDoctorListAdapter(mContext,info.doctors.getList(),key);
                lv_homeresult.setAdapter(adapter);
                if (info.doctors.more){
                    loadmore.setVisibility(View.VISIBLE);
                }else {
                    loadmore.setVisibility(View.GONE);
                }
            }else if (info.type == SearchResultInfo.TYPE_ARTICLE){
                SearchArticleListAdapter adapter = new SearchArticleListAdapter(mContext,info.articles.getList(),key);
                lv_homeresult.setAdapter(adapter);
                if (info.articles.more){
                    loadmore.setVisibility(View.VISIBLE);
                }else {
                    loadmore.setVisibility(View.GONE);
                }
            }

            //条目点击
            lv_homeresult.setOnItemClickListener(new FitHeightListView.OnItemClickListener() {
                @Override
                public void onItemClick(ViewGroup parent, View view, int pos) {
                    Intent intent = null;
                    if (info.type == SearchResultInfo.TYPE_HOSPITAL) {
                        HospitalInfo.Hospital hospital = info.hospitals.getList().get(pos);
                        if (hospital != null) {
                            if (TextUtils.isEmpty(hospital.hospitalId + "")) {
                                UIUtil.toastShort(mContext, "hospitalId 不能为空");
                            } else {
                                intent = new Intent(mContext, HospitalHomeActivity.class);
                                intent.putExtra("hospitalId", hospital.hospitalId + "");
                                mContext.startActivity(intent);
                            }
                        }
                    } else if (info.type == SearchResultInfo.TYPE_DOCTOR) {
                        DoctorListVO docotor = info.doctors.getList().get(pos);
                        if (docotor != null) {
                            RegistrationDoctorDetailActivity
                                    .startDoctorDetail(mContext, docotor.hosOrgCode, docotor.hosDoctCode, docotor.hosDeptCode, true);
                        }
                    } else if (info.type == SearchResultInfo.TYPE_ARTICLE) {
                        ArticleItem article = info.articles.getList().get(pos);
                        SchemeUtil.startActivity(mContext, article.url);
                    }
                }
            });

            //加载到更多
            loadmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    if (info.type == SearchResultInfo.TYPE_HOSPITAL) {
                        intent = new Intent(mContext, SearchHospitalListActivity.class);
                        intent.putExtra(SearchHospitalListActivity.EXTRA_KEY, key);
                        if (SearchHospitalListActivity.FROM_HOME.equals(from)) { //首页搜索
                            intent.putExtra(SearchHospitalListActivity.EXTRA_FROM, SearchHospitalListActivity.FROM_HOME);
                        } else if (SearchHospitalListActivity.FROM_GUAHAO.equals(from)) {//预约挂号搜索医院
                            intent.putExtra(SearchHospitalListActivity.EXTRA_FROM, SearchHospitalListActivity.FROM_GUAHAO);
                        } else if (SearchHospitalListActivity.FROM_NEARBY.equals(from)) {//附近就医搜索医院
                            intent.putExtra(SearchHospitalListActivity.EXTRA_FROM, SearchHospitalListActivity.FROM_NEARBY);
                        }
                    } else if (info.type == SearchResultInfo.TYPE_DOCTOR) {
                        intent = new Intent(mContext, SearchDoctorListActivity.class);
                        intent.putExtra(SearchDoctorListActivity.EXTRA_KEY, key);
                    } else if (info.type == SearchResultInfo.TYPE_ARTICLE) {
                        intent = new Intent(mContext, SearchArticleListActivity.class);
                        intent.putExtra(SearchArticleListActivity.EXTRA_KEY, key);
                    }
                    mContext.startActivity(intent);
                }
            });
        }
        return view;
    }

}
