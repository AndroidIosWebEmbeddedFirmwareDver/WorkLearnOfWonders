package com.wonders.health.venus.open.user.module.health;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.health.venus.open.user.entity.EvaluateEntity;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wonders.health.venus.open.user.R;

import java.util.List;

/**
 * 类描述：医生评价adapter
 * 创建人：zhangjingyang
 * 创建时间：2016/11/8 15:21
 */
public class EvaluateAdapter extends BaseAdapter<EvaluateEntity.Evaluate, EvaluateAdapter.ViewHolder> {

	private Context mContext;
    public List<EvaluateEntity.Evaluate> getItems(){
		return mItems;
	}

	public EvaluateAdapter(Context ctx, List<EvaluateEntity.Evaluate> list) {
		super(ctx, list);
		mContext = ctx;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(mInflater.inflate(R.layout.item_evaluate, parent, false));
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		final EvaluateEntity.Evaluate info = getItem(position);
		holder.mDate.setText(info.createTime);
		holder.mName.setText(info.nickName);
		holder.mContent.setText(info.content);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		TextView mDate;
		TextView mName;
		TextView mContent;

		public ViewHolder(View itemView) {
			super(itemView);
			mDate = (TextView) itemView.findViewById(R.id.txt_date);
			mName = (TextView) itemView.findViewById(R.id.txt_name);
			mContent = (TextView) itemView.findViewById(R.id.txt_content);
		}
	}
}
