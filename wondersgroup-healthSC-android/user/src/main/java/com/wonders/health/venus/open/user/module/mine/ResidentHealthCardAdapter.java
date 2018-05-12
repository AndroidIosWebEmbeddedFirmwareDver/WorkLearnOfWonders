package com.wonders.health.venus.open.user.module.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.util.PatternUtils;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;

import java.util.List;

/**
 */
public class ResidentHealthCardAdapter extends BaseAdapter<UserCardsEntity, ResidentHealthCardAdapter.ViewHolder> {

    private Context mContext;


    public static int ITEM_TYPE_NORMAL = 0;
    public static int ITEM_TYPE_HEADER = 1;
    public User mUser;
    public ViholderViewOnClickListener<UserCardsEntity> mViholderViewOnClickListener;

    public List<UserCardsEntity> getItems() {
        return mItems;
    }

    public ResidentHealthCardAdapter(Context ctx, List<UserCardsEntity> list, User user, ViholderViewOnClickListener<UserCardsEntity> viholderViewOnClickListener) {
        super(ctx, list);
        mContext = ctx;
        mUser = user;
        mViholderViewOnClickListener = viholderViewOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new ViewHolder(mInflater.inflate(R.layout.item_resident_health_card_activity_header, parent, false), viewType);
        } else if (viewType == ITEM_TYPE_NORMAL) {
            return new ViewHolder(mInflater.inflate(R.layout.item_resident_health_card_activity, parent, false), viewType);
        } else {
            return new ViewHolder(mInflater.inflate(R.layout.item_resident_health_card_activity, parent, false), viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE_HEADER : ITEM_TYPE_NORMAL;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final UserCardsEntity cardtype = getItem(position);
        if (mUser != null && mItems != null && mItems.get(position) != null) {
            if (holder.viewType == ITEM_TYPE_HEADER) {
                UserCardsEntity userCardsEntity = mItems.get(position);
                //判断有无居民健康卡
                if (userCardsEntity.showAddHealthCard) {
                    holder.item_resident_health_card_activity_ly_add_health_card.setVisibility(View.VISIBLE);
                    holder.item_resident_health_card_activity_ly_add_health_card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (null != mViholderViewOnClickListener)
                                mViholderViewOnClickListener.onClick(view, holder, position, cardtype);
                        }
                    });
                    holder.item_resident_health_card_activity_ly_show_health_card.setVisibility(View.GONE);
                } else {
                    holder.item_resident_health_card_activity_ly_add_health_card.setVisibility(View.GONE);
                    holder.item_resident_health_card_activity_ly_show_health_card.setVisibility(View.VISIBLE);
                    holder.item_resident_health_card_activity_ly_show_health_card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (null != mViholderViewOnClickListener)
                                mViholderViewOnClickListener.onClick(view, holder, position, cardtype);
                        }
                    });
                    holder.item_resident_health_card_activity_ly_show_health_card_card_no.setText(userCardsEntity.mediacl_card_no != null ? userCardsEntity.mediacl_card_no : "");
                    holder.item_resident_health_card_activity_ly_show_health_card_user_name.setText(mUser != null && mUser.name != null ? mUser.name : "");
                    holder.item_resident_health_card_activity_ly_show_health_card_user_id_card.setText(PatternUtils.hiddenForIdCard(mUser.idcard != null ? mUser.idcard : "", 3, 4));

                }

                //判断是否可显示废话
                if (userCardsEntity.showNoticeHospitalCard) {
                    holder.item_resident_health_card_activity_ly_add_hospital_card_show_msg.setVisibility(View.VISIBLE);
                } else {
                    holder.item_resident_health_card_activity_ly_add_hospital_card_show_msg.setVisibility(View.GONE);
                }
                //判断是否可以继续添加院内就诊卡
                if (userCardsEntity.showAddHospitalCard) {
                    holder.item_resident_health_card_activity_ly_add_hospital_card.setVisibility(View.VISIBLE);
                    holder.item_resident_health_card_activity_ly_add_hospital_card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (null != mViholderViewOnClickListener)
                                mViholderViewOnClickListener.onClick(view, holder, position, cardtype);
                        }
                    });
                } else {
                    holder.item_resident_health_card_activity_ly_add_hospital_card.setVisibility(View.GONE);
                }
                if (userCardsEntity.showAddHospitalCardSize > 0) {
                    holder.item_resident_health_card_activity_ly_show_hospital_card_and_no.setText("医院就诊卡 " + userCardsEntity.showAddHospitalCardSize);
                }

            } else {

                holder.item_resident_health_card_activity_hospital_name.setText(mItems.get(position).hospital_name != null ? mItems.get(position).hospital_name : "");
                holder.item_resident_health_card_activity_mediacl_card_no.setText(mItems.get(position).mediacl_card_no != null ? mItems.get(position).mediacl_card_no : "");
                holder.item_resident_health_card_activity_user_name.setText(mUser != null && mUser.name != null ? mUser.name : "");
                holder.item_resident_health_card_activity_user_idcard.setText(PatternUtils.hiddenForIdCard(mUser.idcard != null ? mUser.idcard : "", 3, 4));

                holder.item_resident_health_card_activity_btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mViholderViewOnClickListener)
                            mViholderViewOnClickListener.onClick(view, holder, position, cardtype);
                    }
                });
                holder.item_resident_health_card_activity_btn_gh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mViholderViewOnClickListener)
                            mViholderViewOnClickListener.onClick(view, holder, position, cardtype);
                    }
                });
            }
        }
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }


    public static interface ViholderViewOnClickListener<T> extends View.OnClickListener {
        void onClick(View var1, ViewHolder holder, final int position, T data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        int viewType;//view 类型

        //头部布局
        //居民健康卡
        View item_resident_health_card_activity_ly_add_health_card;
        View item_resident_health_card_activity_ly_show_health_card;
        TextView item_resident_health_card_activity_ly_show_health_card_card_no;
        TextView item_resident_health_card_activity_ly_show_health_card_user_name;
        TextView item_resident_health_card_activity_ly_show_health_card_user_id_card;
        //院内就诊卡
        View item_resident_health_card_activity_ly_add_hospital_card;
        TextView item_resident_health_card_activity_ly_show_hospital_card_and_no;
        TextView item_resident_health_card_activity_ly_add_hospital_card_show_msg;


        //通用院内卡布局
        TextView item_resident_health_card_activity_hospital_name;
        TextView item_resident_health_card_activity_mediacl_card_no;
        TextView item_resident_health_card_activity_user_name;
        TextView item_resident_health_card_activity_user_idcard;
        Button item_resident_health_card_activity_btn_delete;
        Button item_resident_health_card_activity_btn_gh;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            if (viewType == ITEM_TYPE_HEADER) {
                item_resident_health_card_activity_ly_add_health_card = itemView.findViewById(R.id.item_resident_health_card_activity_ly_add_health_card);
                item_resident_health_card_activity_ly_show_health_card = itemView.findViewById(R.id.item_resident_health_card_activity_ly_show_health_card);
                item_resident_health_card_activity_ly_show_health_card_card_no = (TextView) itemView.findViewById(R.id.item_resident_health_card_activity_ly_show_health_card_card_no);
                item_resident_health_card_activity_ly_show_health_card_user_name = (TextView) itemView.findViewById(R.id.item_resident_health_card_activity_ly_show_health_card_user_name);
                item_resident_health_card_activity_ly_show_health_card_user_id_card = (TextView) itemView.findViewById(R.id.item_resident_health_card_activity_ly_show_health_card_user_id_card);

                item_resident_health_card_activity_ly_add_hospital_card = itemView.findViewById(R.id.item_resident_health_card_activity_ly_add_hospital_card);
                item_resident_health_card_activity_ly_show_hospital_card_and_no = (TextView) itemView.findViewById(R.id.item_resident_health_card_activity_ly_show_hospital_card_and_no);
                item_resident_health_card_activity_ly_add_hospital_card_show_msg = (TextView) itemView.findViewById(R.id.item_resident_health_card_activity_ly_add_hospital_card_show_msg);


            } else {
                item_resident_health_card_activity_hospital_name = (TextView) itemView.findViewById(R.id.item_resident_health_card_activity_hospital_name);
                item_resident_health_card_activity_mediacl_card_no = (TextView) itemView.findViewById(R.id.item_resident_health_card_activity_mediacl_card_no);
                item_resident_health_card_activity_user_name = (TextView) itemView.findViewById(R.id.item_resident_health_card_activity_user_name);
                item_resident_health_card_activity_user_idcard = (TextView) itemView.findViewById(R.id.item_resident_health_card_activity_user_idcard);
                item_resident_health_card_activity_btn_delete = (Button) itemView.findViewById(R.id.item_resident_health_card_activity_btn_delete);
                item_resident_health_card_activity_btn_gh = (Button) itemView.findViewById(R.id.item_resident_health_card_activity_btn_gh);
            }

        }
    }
}