package com.wondersgroup.hs.healthcloud.common.share;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.hs.healthcloud.base.R;
import com.wondersgroup.hs.healthcloud.common.entity.Share;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.ShareUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;

import java.util.Arrays;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 类描述：分享组件
 * 创建人：Bob
 * 创建时间：2016/3/10 15:11
 */
public class ShareManager {
    private String[] mPlatforms = new String[] {Wechat.NAME, WechatMoments.NAME};

    public void setSharePlatforms(String... platforms) {
        mPlatforms = platforms;
    }

    public void showShareDialog(final Context context, final Share share, final ResponseCallback<String> callback) {
        final LinearLayout dialogView = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.dialog_share, null);
        TextView cancelBtn = (TextView) dialogView.findViewById(R.id.share_cancel);
        BaseRecyclerView recyclerView = (BaseRecyclerView) dialogView.findViewById(R.id.recycler_view_share);

        final Dialog dialog = UIUtil.showBottomMenu((Activity) context, dialogView);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        recyclerView.setAdapter(new ShareItemAdapter(context, Arrays.asList(mPlatforms)));
        recyclerView.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                dialog.dismiss();
                String platformName = mPlatforms[position];
                if (Wechat.NAME.equals(platformName)) {
                    ShareUtil.shareWechat(share, callback);
                } else if (WechatMoments.NAME.equals(platformName)) {
                    ShareUtil.shareWechatMoments(share, callback);
                } else if (QQ.NAME.equals(platformName)) {
                    ShareUtil.shareQQ(share, callback);
                } else if (SinaWeibo.NAME.equals(platformName)) {
                    ShareUtil.shareSinaWeibo(share, callback);
                } else if (context.getString(R.string.app_name).equals(platformName)) {
//                    ShareUtil.shareWechat(share, callback);
                    // TODO
                }
            }
        });
    }
}
