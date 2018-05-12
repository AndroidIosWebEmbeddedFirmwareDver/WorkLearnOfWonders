package com.wonders.health.venus.open.user.module.home.search;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.DoctorListVO;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import static com.wonders.health.venus.open.user.R.id.tv_item_doctorlist_duty;

/**
 * 搜索结果--医生列表
 * Created by songzhen on 2016/11/9.
 */
public class SearchDoctorListAdapter extends BaseAdapter {
    private static int MAX_SHOW_SIZE = 2;
    private BitmapTools mBitmaptool;
    private Context mContext;
    private List<DoctorListVO> mItems = new ArrayList<>();
    private String keyword = "";

    public SearchDoctorListAdapter(Context mContext, List<DoctorListVO> mItems ){
        new SearchDoctorListAdapter(mContext,mItems,"");
    }
    public SearchDoctorListAdapter(Context mContext, List<DoctorListVO> mItems ,String keyword){
        this.mContext = mContext;
        this.mItems = mItems;
        mBitmaptool = new BitmapTools(mContext);
        this.keyword = keyword;
    }
    @Override
    public int getCount() {
        if (mItems.size()>0){
            if (mItems.size()>MAX_SHOW_SIZE){
                return MAX_SHOW_SIZE;
            }else {
                return mItems.size();
            }
        }else {
            return 0;
        }
    }

    @Override
    public DoctorListVO getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DoctorListHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.registration_doctor_list_item,parent,false);
            holder = new DoctorListHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (DoctorListHolder) convertView.getTag();
        }
        DoctorListVO entity =  getItem(position);
        holder.guahao.setText("");
//        holder.guahao.setBackgroundResource(R.mipmap.icon_guahao);
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
            if(!TextUtils.isEmpty(keyword)&&!TextUtils.isEmpty(entity.doctorName)){
                SpannableStringBuilder builder = new SpannableStringBuilder(formatName(entity.doctorName+"",5));
                ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.parseColor("#2E7AF0"));
                int start = formatName(entity.doctorName+"",5).indexOf(keyword);
                int end = (start + keyword.length())<5?(start + keyword.length()):5;
//                UIUtil.toastShort(mContext,keyword+ "indexof "+entity.doctorName+" start: "+start+" ,end: "+end);
                if (start!=-1){
                    builder.setSpan(blueSpan,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                holder.name.setText(builder);
            }else{
                holder.name.setText(entity.doctorName + "");
            }
            holder.zhicheng.setText(TextUtils.isEmpty(entity.doctorTitle)?"":entity.doctorTitle);
            holder.hospital.setText(formatName(entity.hosName +"",10));
            holder.keshi.setText(entity.deptName+"");
            holder.num.setText("接诊量 " + entity.orderCount);
            holder.expert.setText(entity.expertin+"");
        }
        return convertView;
    }

    //名称显示...格式化
    private String formatName(String name,int size){
        if (!TextUtils.isEmpty(name)&&name.length()>=size){
            return name.substring(0,size)+"...";
        }
        return name;
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
