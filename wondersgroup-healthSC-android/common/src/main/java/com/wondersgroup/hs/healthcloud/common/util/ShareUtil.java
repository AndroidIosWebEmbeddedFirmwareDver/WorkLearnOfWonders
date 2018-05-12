package com.wondersgroup.hs.healthcloud.common.util;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.wondersgroup.hs.healthcloud.common.CommonApp;
import com.wondersgroup.hs.healthcloud.common.R;
import com.wondersgroup.hs.healthcloud.common.entity.Share;
import com.wondersgroup.hs.healthcloud.common.http.HttpException;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;

import java.io.File;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/3/10 14:44
 */
public class ShareUtil {
    /**
     * 分享到QQ shareQQ title：最多30个字符 text：最多40个字符 Title，TitleUrl必须加
     *
     * @since 1.0
     */
    public static void shareQQ(Share share, ResponseCallback<String> callback) {
        if (share == null) {
            return;
        }
        if (TextUtils.isEmpty(share.title) || TextUtils.isEmpty(share.brief)) {
            if (callback != null) {
                callback.onFailure(new Exception("标题或者描述不能为空！"));
                callback.onFinish();
            }
            return;
        }

        QQ.ShareParams sp = new QQ.ShareParams();
        if (!TextUtils.isEmpty(share.title)) {
            sp.setTitle(share.title.length() > 30 ? share.title.substring(0, 30) : share.title);
        }
        if (!TextUtils.isEmpty(share.shareUrl)) {
            sp.setTitleUrl(share.shareUrl);
        }
        if (!TextUtils.isEmpty(share.brief)) {
            sp.setText(share.brief.length() > 40 ? share.brief.substring(0, 40) : share.brief);
        }
        if (!TextUtils.isEmpty(share.thumb)) {
            sp.setImageUrl(share.thumb);
        }

        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        platform.setPlatformActionListener(new PlatformActionCallback(callback)); // 设置分享事件回调
        platform.share(sp);// 执行图文分享
    }

    /**
     * 分享到新浪微博 shareSinaWeibo text：不能超过140个汉字 image：图片最大5M，仅支持JPEG、GIF、PNG格式
     * latitude：有效范围:-90.0到+90.0，+表示北纬 longitude：有效范围：-180.0到+180.0，+表示东经
     * 如果imagePath和imageUrl同时存在，imageUrl将被忽略。
     *
     * @param share
     * @since 1.0
     */
    public static void shareSinaWeibo(Share share, ResponseCallback<String> callback) {
        if (share == null) {
            return;
        }
        final String shareDesc = share.title + "\n" + share.brief + share.shareUrl;
        if (TextUtils.isEmpty(shareDesc)) {
            return;
        }

        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
        //FIX 如果超过140个汉字 保证URL不变,截取内容 2016年09月23日
        int length = shareDesc.length();
        if (length > 140) {
            int subLength = length - 140 + 3;
            int contentLength = share.brief.length();
            if (contentLength > subLength) {
                String contentStr = share.brief.substring(0, contentLength - subLength) + "...";
                String newShare = share.title + "\n" + contentStr + share.shareUrl;
                sp.setText(newShare);
            } else {
                sp.setText(shareDesc.length() > 140 ? shareDesc.substring(0, 140) : shareDesc);
            }
        } else {
            sp.setText(shareDesc);
        }
        if (!TextUtils.isEmpty(share.thumb)) {
            sp.setImageUrl(share.thumb);
        }
        Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
        platform.setPlatformActionListener(new PlatformActionCallback(callback)); // 设置分享事件回调
        platform.share(sp);// 执行图文分享
    }

    /**
     * 分享到微信 shareWechat title：512Bytes以内 text：1KB以内 imageData：10M以内
     * imagePath：10M以内(传递的imagePath路径不能超过10KB) imageUrl：10KB以内 musicUrl：10KB以内
     * shareUrl：10KB以内 微信并无实际的分享网络图片和分享bitmap的功能，如果设置了网络图片，此图片会先下载会本地，之后再当作本地图片分享，
     * 因此延迟较大。bitmap则好一些，但是由于bitmap并不知道图片的格式，因此都会被当作png编码，再提交微信客户端。
     * 此外，SHARE_EMOJI支持gif文件，但是如果使用imageData，则默认只是提交一个png图片，因为bitmap是静态图片。
     *
     * @since 1.0
     */
    public static void shareWechat(Share share, ResponseCallback<String> callback) {
        if (share == null) {
            return;
        }
        if (!checkWXShare()) {
            return;
        }
        if (TextUtils.isEmpty(share.title)) {
            if (callback != null) {
                callback.onFailure(new Exception("标题不能为空！"));
                callback.onFinish();
            }
            return;
        }

        Wechat.ShareParams sp = new Wechat.ShareParams();

        if (!TextUtils.isEmpty(share.title)) {
            sp.setTitle(StringUtil.subStringByCharCount(share.title, 512));
        }
        if (!TextUtils.isEmpty(share.brief)) {
            sp.setText(StringUtil.subStringByCharCount(share.brief, 1024));
        }
        if (!TextUtils.isEmpty(share.shareUrl)) {
            sp.setUrl(share.shareUrl);
//            sp.setUrl(StringUtil.subStringByByteCount(mParam.mShare_url, 10 * 1024));
        }
        if (!TextUtils.isEmpty(share.thumb)) {
            sp.setImageUrl(share.thumb);
        } else if (!TextUtils.isEmpty(share.thumb_local_path)) {
            sp.setImagePath(share.thumb_local_path);
        }

        sp.setShareType(getWechatShareType(share));

        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        platform.setPlatformActionListener(new PlatformActionCallback(callback)); // 设置分享事件回调
        // wxShareFilter(platform, mParam);
        platform.share(sp);// 执行图文分享
    }

    /**
     * 分享到微信朋友圈 shareWXMoments
     *
     * @since 1.0
     */
    public static void shareWechatMoments(Share share, ResponseCallback<String> callback) {
        if (share == null) {
            return;
        }
        if (!checkWXShare()) {
            return;
        }
        if (TextUtils.isEmpty(share.brief)) {
            if (callback != null) {
                callback.onFailure(new Exception("描述不能为空！"));
                callback.onFinish();
            }
            return;
        }

        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        if (!TextUtils.isEmpty(share.title)) {
            sp.setTitle(StringUtil.subStringByCharCount(share.title, 512));
        }
        if (!TextUtils.isEmpty(share.brief)) {
            sp.setText(StringUtil.subStringByCharCount(share.brief, 1024));
        }
        if (!TextUtils.isEmpty(share.shareUrl)) {
            sp.setUrl(share.shareUrl);
//            sp.setUrl(StringUtil.subStringByByteCount(mParam.mShare_url, 10 * 1024));
        }
        if (!TextUtils.isEmpty(share.thumb)) {
            sp.setImageUrl(share.thumb);
        } else if (!TextUtils.isEmpty(share.thumb_local_path)) {
            sp.setImagePath(share.thumb_local_path);
        }

        sp.setShareType(getWechatShareType(share));

        Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);

        platform.setPlatformActionListener(new PlatformActionCallback(callback)); // 设置分享事件回调
        platform.share(sp);// 执行图文分享
    }

    /**
     * 获取微信分享 type getWechatShareType
     *
     * @param share
     * @return
     * @since 1.0
     */
    private static int getWechatShareType(Share share) {
        int shareType = Platform.SHARE_TEXT;
        String imagePath = share.thumb_local_path;
        String imageUrl = share.thumb;
        if (imagePath != null && (new File(imagePath)).exists()) {
            shareType = Platform.SHARE_IMAGE;
            if (imagePath.endsWith(".gif")) {
                shareType = Platform.SHARE_EMOJI;
            } else if (!TextUtils.isEmpty(share.shareUrl)) {
                shareType = Platform.SHARE_WEBPAGE;
            }
        } else if (!TextUtils.isEmpty(imageUrl)) {
            shareType = Platform.SHARE_IMAGE;
            if (String.valueOf(imageUrl).endsWith(".gif")) {
                shareType = Platform.SHARE_EMOJI;
            } else if (!TextUtils.isEmpty(share.shareUrl)) {
                shareType = Platform.SHARE_WEBPAGE;
            }
        } else {
            //text=null shareUrl!=null 则是WEBPAGE
            if (!TextUtils.isEmpty(share.shareUrl)) {
                shareType = Platform.SHARE_WEBPAGE;
            }
        }
        return shareType;
    }


    static class PlatformActionCallback implements PlatformActionListener {
        private ResponseCallback<String> mCallback;

        public PlatformActionCallback(ResponseCallback<String> callback) {
            mCallback = callback;
            if (mCallback != null) {
                mCallback.onStart();
            }
        }

        @Override
        public void onComplete(final Platform platform, int i, HashMap<String, Object> hashMap) {
            new Handler(platform.getContext().getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (!(SinaWeibo.NAME).equals(platform.getName())) {//新浪微博不提示分享成功
                        UIUtil.toastShort(platform.getContext(), R.string.share_complete);
                    } else {
                        if (!checkSinaShare(platform.getContext())) {//未安装新浪微博
                            UIUtil.toastShort(platform.getContext(), R.string.share_complete);
                        }
                    }

                    LogUtils.d("bacy->share:" + platform.getName());

                    if (mCallback != null) {
                        mCallback.onSuccess("分享成功");
                        mCallback.onFinish();
                    }
                }
            });
        }

        @Override
        public void onError(final Platform platform, final int i, final Throwable throwable) {
            LogUtils.e("share->error:" + i + throwable);
            new Handler(platform.getContext().getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    UIUtil.toastShort(platform.getContext(), R.string.share_error);
                    if (mCallback != null) {
                        mCallback.onFailure(new HttpException(i, throwable));
                        mCallback.onFinish();
                    }
                }
            });
        }

        @Override
        public void onCancel(final Platform platform, final int i) {
            new Handler(platform.getContext().getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    UIUtil.toastShort(platform.getContext(), R.string.share_cancel);
                    if (mCallback != null) {
                        mCallback.onFailure(new HttpException(i, "取消分享"));
                        mCallback.onFinish();
                    }
                }
            });
        }
    }

    /**
     * 判断是否安装新浪微博客户端
     *
     * @return
     */
    public static boolean checkSinaShare(Context context) {
        Platform weibo = ShareSDK.getPlatform(context, SinaWeibo.NAME);
        if (weibo.isClientValid()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查微信分享
     * checkWXShare
     *
     * @since 1.0.4
     */
    public static boolean checkWXShare() {
        Platform wx = ShareSDK.getPlatform(Wechat.NAME);
        if (wx.isClientValid()) {
            return true;
        } else {
            UIUtil.toastShort(CommonApp.getApp(), R.string.wx_client_no_exist);
            return false;
        }
    }
}
