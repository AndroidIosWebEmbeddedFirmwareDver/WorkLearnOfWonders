package com.wonders.health.venus.open.user.component.pay;

import android.content.Context;
import android.os.Handler;

import com.wonders.health.venus.open.user.BuildConfig;
import com.wondersgroup.hs.healthcloud.common.http.HttpException;
import com.wondersgroup.hs.healthcloud.common.logic.Callback;

import cn.wd.checkout.api.CheckOut;
import cn.wd.checkout.api.WDCallBack;
import cn.wd.checkout.api.WDPay;
import cn.wd.checkout.api.WDPayResult;
import cn.wd.checkout.api.WDReqParams;
import cn.wd.checkout.api.WDResult;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/11/2 14:23
 */

public class PayUtil {
//    private final static String APP_SECRET = "hwadrqreli9hmkc0sdrdz1rcgcqajcci";
//    public final static String APP_ID = BuildConfig.IS_RELEASE ? "308111700000075" : "308111700000088";
//    public final static String SUB_MER_NO = BuildConfig.IS_RELEASE ? "308111700000075": "308111700000088";
public final static String APP_ID = BuildConfig.IS_RELEASE ? "308111700000075" : "305111700000109";
    public final static String SUB_MER_NO = BuildConfig.IS_RELEASE ? "308111700000075": "305111700000109";
    /**
     * @param context context 必须是activity
     */
    public static void payByWeixin(Context context, PayOrderInfo payInfo, final Callback<String> callback) {
        init(payInfo.appid,payInfo.appKey);
        WDPay.getInstance(context).reqPayAsync(WDReqParams.WDChannelTypes.wepay, payInfo.submerno,
                payInfo.goodsTitle,               //订单标题
                payInfo.goodsDesc,
                payInfo.i,                           //订单金额(分)
                payInfo.orderTitle,  //订单流水号
                payInfo.orderDesc,
                null,            //扩展参数(可以null)
                new PayCallback(context, callback));
    }

    public static void payByAli(Context context, PayOrderInfo payInfo, final Callback<String> callback) {
        init(payInfo.appid,payInfo.appKey);
        WDPay.getInstance(context).reqPayAsync(WDReqParams.WDChannelTypes.alipay, payInfo.submerno,
                payInfo.goodsTitle,               //订单标题
                payInfo.goodsDesc,
                payInfo.i,                           //订单金额(分)
                payInfo.orderTitle,  //订单流水号
                payInfo.orderDesc,
                null,            //扩展参数(可以null)
                new PayCallback(context, callback));
    }


    private static void init(String appid,String appKey) {
        CheckOut.setAppIdAndSecret(appid, appKey);
        CheckOut.setIsPrint(BuildConfig.LOG_DEBUG);
        if (BuildConfig.IS_RELEASE) {
            CheckOut.setNetworkWay("");
            CheckOut.setLianNetworkWay("");
        } else {
            CheckOut.setNetworkWay("SC");
            CheckOut.setLianNetworkWay("");
        }

        /**
         * 设置访问网络环境  CT 为联调测试环境 不调用此方法为生产环境
         */
//        if(wayid==1){
//            Toast.makeText(getApplicationContext(), "联调测试环境", Toast.LENGTH_SHORT).show();
//            CheckOut.setNetworkWay("CT");
//            CheckOut.setLianNetworkWay("");
//        }else if(wayid==2){
//            Toast.makeText(getApplicationContext(), "正式环境", Toast.LENGTH_SHORT).show();
//            CheckOut.setNetworkWay("");
//            CheckOut.setLianNetworkWay("");
//
//        }else{
//            Toast.makeText(getApplicationContext(), "开发测试环境", Toast.LENGTH_SHORT).show();
//            CheckOut.setNetworkWay("CST");
//            CheckOut.setLianNetworkWay("TS");
//        }
    }

   static class PayCallback implements WDCallBack {
        private Callback<String> callback;
        private Context context;

        PayCallback(Context context, Callback<String> callback) {
            this.callback = callback;
            this.context = context;
            callback.onStart();
        }

       @Override
       public void done(WDResult wdResult) {
           final WDPayResult bcPayResult = (WDPayResult) wdResult;
           new Handler(context.getMainLooper()).post(new Runnable() {
               @Override
               public void run() {
                   String result = bcPayResult.getResult();
                   if (result.equals(WDPayResult.RESULT_SUCCESS)) {
                       callback.onSuccess("用户支付成功");
                   } else if (result.equals(WDPayResult.RESULT_CANCEL)) {
                       callback.onFailure(new HttpException(HttpException.CODE_CANCEL, "用户取消支付"));
                   } else if (result.equals(WDPayResult.RESULT_FAIL)) {
                       String info = bcPayResult.getDetailInfo();
                       callback.onFailure(new HttpException(info));
                   } else if (result.equals(WDPayResult.FAIL_UNKNOWN_WAY)) {
                       callback.onFailure(new HttpException("未知支付渠道"));
                   } else if (result.equals(WDPayResult.FAIL_WEIXIN_VERSION_ERROR)) {
                       callback.onFailure(new HttpException("微信版本不支持"));
                   } else if (result.equals(WDPayResult.FAIL_EXCEPTION)) {
                       callback.onFailure(new HttpException("支付过程中发送错误"));
                   } else if (result.equals(WDPayResult.FAIL_ERR_FROM_CHANNEL)) {
                       callback.onFailure(new HttpException(bcPayResult.getErrMsg()));
                   } else if (result.equals(WDPayResult.FAIL_INVALID_PARAMS)) {
                       callback.onFailure(new HttpException(""));
                   } else if (result.equals(WDPayResult.RESULT_PAYING_UNCONFIRMED)) {
                       callback.onFailure(new HttpException(""));
                   } else {
                       callback.onFailure(new HttpException(""));
                   }
                   callback.onFinish();
               }
           });
       }
   }
}
