package com.wondersgroup.hs.healthcloud.common.share;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wondersgroup.hs.healthcloud.base.R;
import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import java.util.List;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/3/10 15:29
 */
public class ShareItemAdapter extends BaseAdapter<String, ShareItemAdapter.ShareHolder> {

    public ShareItemAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public ShareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShareHolder(mInflater.inflate(R.layout.item_share, null));
    }

    @Override
    public void onBindViewHolder(ShareHolder holder, int position) {
        String platformName = getItem(position);
        TextView text = holder.text;
        UIUtil.setTouchEffect(text);
        if (SinaWeibo.NAME.equals(platformName)) {
            text.setText("微博");
            text.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.weibo, 0, 0);
        } else if (Wechat.NAME.equals(platformName)) {
            text.setText("微信分享");
            text.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.weixin, 0, 0);
        } else if (WechatMoments.NAME.equals(platformName)) {
            text.setText("朋友圈分享");
            text.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.pengyouquan, 0, 0);
        } else if (QQ.NAME.equals(platformName)) {
            text.setText("QQ");
            text.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.qq, 0, 0);
        } else if (mContext.getString(R.string.app_name).equals(platformName)) {
            text.setText(mContext.getString(R.string.app_name));
            text.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_launcher, 0, 0);
        }
    }

    static class ShareHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public ShareHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_share);
        }
    }
}
