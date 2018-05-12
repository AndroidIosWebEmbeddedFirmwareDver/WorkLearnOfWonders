package com.wonders.health.venus.open.user.module.msg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.MsgEntity;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import java.util.List;

/**
 * 类描述：消息adapter
 * 创建人：zhangjingyang
 * 创建时间：2016/11/8 15:21
 */
public class SystemMsgAdapter extends BaseAdapter<MsgEntity.message, SystemMsgAdapter.ViewHolder> {

	private Context mContext;
    public List<MsgEntity.message> getItems(){
		return mItems;
	}

	public SystemMsgAdapter(Context ctx, List<MsgEntity.message> list) {
		super(ctx, list);
		mContext = ctx;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(mInflater.inflate(R.layout.item_msg_system, parent, false));
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		final MsgEntity.message info = getItem(position);
		holder.mDate.setText(info.createDate);
		holder.mTitle.setText(info.title);
		holder.mContent.setText(info.content);
		if (URLUtil.isNetworkUrl(info.url)) {
			holder.mDetail.setVisibility(View.VISIBLE);
			holder.mLine.setVisibility(View.VISIBLE);
			holder.mLMsg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SchemeUtil.startActivity(mContext, info.url);
				}
			});
		}else{
			holder.mDetail.setVisibility(View.GONE);
			holder.mLine.setVisibility(View.GONE);
		}
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		LinearLayout mLMsg;
		TextView mDate;
		TextView mTitle;
		TextView mContent;
		TextView mDetail;
		View mLine;

		public ViewHolder(View itemView) {
			super(itemView);
			mLMsg = (LinearLayout)itemView.findViewById(R.id.ll_msg);
			mDate = (TextView) itemView.findViewById(R.id.txt_date);
			mTitle = (TextView) itemView.findViewById(R.id.txt_title);
			mContent = (TextView) itemView.findViewById(R.id.txt_content);
			mDetail = (TextView) itemView.findViewById(R.id.txt_detail);
			mLine = itemView.findViewById(R.id.v_line);
		}
	}
}
