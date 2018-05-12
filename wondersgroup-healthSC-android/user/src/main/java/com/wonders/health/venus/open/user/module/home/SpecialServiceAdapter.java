package com.wonders.health.venus.open.user.module.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.HomeSpecialServiceEntity;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;

import java.util.List;

/**
 * 类：${File}
 * 创建者:carrey on 16-9-1.
 * 描述 ：
 */
public class SpecialServiceAdapter extends BaseAdapter<HomeSpecialServiceEntity.SpecialService, SpecialServiceAdapter.ViewHolder> {

    private BitmapTools mBitmapTools;


    public SpecialServiceAdapter(Context context, List<HomeSpecialServiceEntity.SpecialService> list) {
        super(context, list);
        mBitmapTools = new BitmapTools(context);
    }


    @Override
    public SpecialServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_special_service, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bindView(mItems.get(position));

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvIcon1;
        private TextView mTvTitle1;

        private void findView(View itemview) {
            mIvIcon1 = (ImageView) itemview.findViewById(R.id.iv_icon1);
            mTvTitle1 = (TextView) itemview.findViewById(R.id.tv_title1);
        }


        public ViewHolder(View itemView) {
            super(itemView);
            findView(itemView);
        }

        public void bindView(HomeSpecialServiceEntity.SpecialService entity) {
            mBitmapTools.display(mIvIcon1, entity.imgUrl);
            mTvTitle1.setText(entity.mainTitle);

        }
    }
}
