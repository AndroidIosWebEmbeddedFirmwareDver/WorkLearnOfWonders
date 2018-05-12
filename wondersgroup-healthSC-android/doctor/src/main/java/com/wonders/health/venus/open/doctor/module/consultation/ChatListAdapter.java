package com.wonders.health.venus.open.doctor.module.consultation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.ChatListItem;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.huanxin.utils.EaseCommonUtils;
import com.wondersgroup.hs.healthcloud.common.huanxin.utils.EaseDateUtil;
import com.wondersgroup.hs.healthcloud.common.huanxin.utils.EaseSmileUtils;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.view.CircleImageView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wang on 2017/6/5.
 */

public class ChatListAdapter extends BaseAdapter<EMConversation,ChatListAdapter.ChatViewHolder>{

    EMConversation item;
    BitmapTools bitmapTools;
    Context mContext;
    String msg;

    public ChatListAdapter(Context context, List<EMConversation> list) {
        super(context, list);
        mContext=context;

    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatViewHolder(mInflater.inflate(R.layout.item_chat_list,parent,false));
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        bitmapTools =new BitmapTools(mContext);
        item=mItems.get(position);
        if(item!=null){
//            bitmapTools.display(holder.avatar,item.getLatestMessageFromOthers().);
            holder.name.setText(item.getLastMessage().getUserName());

            holder.time.setText(EaseDateUtil.getTimestampString(new Date(item.getLatestMessageFromOthers().getMsgTime())));
            holder.msg.setText(EaseSmileUtils.getSmiledText(mContext, EaseCommonUtils.getMessageDigest(item.getLastMessage(), (mContext))),
                    TextView.BufferType.SPANNABLE);
        }

    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        CircleImageView avatar;
        @BindView(R.id.tv_name)
        TextView name;
        @BindView(R.id.tv_time)
        TextView time;
        @BindView(R.id.tv_msg)
        TextView msg;

        public ChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
