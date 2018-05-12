package com.wonders.health.venus.open.user.wxapi;

import android.content.Intent;
import android.widget.Toast;

import cn.sharesdk.wechat.utils.WXAppExtendObject;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sharesdk.wechat.utils.WechatHandlerActivity;

/** 微信客户端回调activity示例 */
public class WXEntryActivity extends WechatHandlerActivity {

    /**
     * 处理微信发出的向第三方应用请求app message
     * <p>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
     * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
    public void onGetMessageFromWXReq(WXMediaMessage msg) {
        Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
        startActivity(iLaunchMyself);
    }
    
    /**
     * 处理微信向第三方应用发起的消息
     * <p>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
     * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
     * 回调。
     * <p>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */
    public void onShowMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null && msg.mediaObject != null
                && (msg.mediaObject instanceof WXAppExtendObject)) {
            WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
            Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
        }
    }

}


/**
 * 微信分享回调
 * WXEntryActivity
 * tanghaihua
 * 2015年3月19日 下午2:59:22
 * @version 1.0
 */
//public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
//    private IWXAPI wxapi;
//    private BaseApp mApp;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mApp = BaseApp.getApp();
//
//        wxapi = WXAPIFactory.createWXAPI(this, Constant.WX_SHARE_ID, true);
//        wxapi.registerApp(Constant.WX_SHARE_ID);
//        wxapi.handleIntent(getIntent(), this);
//    }
//
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
//        setIntent(intent);
//        wxapi.handleIntent(intent, this);
//    }
//
//    @Override
//    public void onReq(BaseReq arg0) {
//        LogUtils.i("mm", "onReq");
//    }
//
//    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
//    @Override
//    public void onResp(BaseResp resp) {
//        int result = 0;
//        LogUtils.i("mm", "onResp");
//        switch (resp.errCode) {
//        case BaseResp.ErrCode.ERR_OK:
//            if (mApp.isWechatFavorite) {
//                result = R.string.errcode_fav_success;
//            } else {
//                result = R.string.errcode_success;
//            }
//            break;
//        case BaseResp.ErrCode.ERR_USER_CANCEL:
//            if (mApp.isWechatFavorite) {
//                result = R.string.errcode_fav_cancel;
//            } else {
//                result = R.string.errcode_cancel;
//            }
//            break;
//        case BaseResp.ErrCode.ERR_AUTH_DENIED:
//            if (mApp.isWechatFavorite) {
//                result = R.string.errcode_fav_deny;
//            } else {
//                result = R.string.errcode_deny;
//            }
//            break;
//        default:
//            if (mApp.isWechatFavorite) {
//                result = R.string.errcode_fav_unknown;
//            } else {
//                result = R.string.errcode_unknown;
//            }
//            break;
//        }
//
//        if(resp.transaction != null){
//            if (resp.transaction.startsWith("1")) {
//            } else {
//            }
//        }
//        
//        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//        mApp.isWechatFavorite = false;
//        this.finish();
//    }
//
//    @Override
//    protected void initViews() {
//    }
//
//    @Override
//    protected void initData(Bundle savedInstanceState) {
//    }
//}
