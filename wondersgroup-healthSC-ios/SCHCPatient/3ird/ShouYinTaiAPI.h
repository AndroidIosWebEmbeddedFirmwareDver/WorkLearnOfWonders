//成功版本
//  ShouYinTaiAPI.h

//
//  Created by WD_王宇超 on 15/10/20.

//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
/**
 *<p>统一收银台调用结果协议</p>
 *返回调用SDK后的响应结果
 */
@protocol ShouYinTaiDelegate <NSObject>
@required
/**
 *响应结果调用方法
 *@param resultDic <p>支付结果信息</p>
 *<p>"err_detail" = "错误详情";</p>
 *<p>"result_msg" = "返回结果信息";</p>
 *<p>"result_code" = "-1";  状态位</p>
 *<p>状态位含义</p>
 *<p> 1:成功</p>
 *<p> -1:取消</p>
 *<p> 0:参数错误无法启动SDK支付</p>
 *<p> -3:失败</p>
 *<p> -4:不支持</p>
 *<p> 999:请求调用支付信息成功,即将吊起对应支付工具进行支付</p>
 */
- (void)getResult:(NSDictionary*)resultDic;
@optional
/**
 *请求调用支付信息成功,即将吊起对应支付工具进行支付
 *@param resultDic 与getResult中的resultDic键值对一致。
 */
-(void)orderDetailFinishedAndWillCallPayUtils:(NSDictionary*)resultDic;
@end


@interface ShouYinTaiAPI : NSObject
/**
 返回SDK版本号
 @return 版本号
 */
+ (NSString*)SDKVersion;

+(void)setDevelopStyle:(NSString*)developStyle;

#pragma mark 如下为通过实例方法进行支付

/**
 *初始化单例对象
 *@return 返回单例对象
 */
+ (instancetype)sharedInstance;

/**
 设置自定义统一支付平台SDK后台服务器地址(需要在初始化之后，设置支付环境之前设定URL地址)。只有当 payStyle 支付环境 设置为 DIY ，才会生效。
 @param serverURL URL地址
 */
+(void)setSYTserverURL:(NSString*)serverURL;

/**
 增加999状态
 */
-(void)add999state DEPRECATED_ATTRIBUTE;
/**
 删除999状态。
 */
-(void)delete999state DEPRECATED_ATTRIBUTE;



/**
 处理微信支付宝等外部app反馈信息
 @param url 外部反馈的url。
 */
- (BOOL)handleOpenUrl:(NSURL *)url;

/**
 设置代理委托对象
 */
- (void)setSYTDelegate:(id<ShouYinTaiDelegate>)delegate;


/*
支付方式一
 如下
 */

/**
 支付方式一 设置支付环境。不执行此方法的情况下为生产环境。
 @param payStyle 支付环境  SC:生产  CS:测试   为nil时是生产环境  默认为生产环境；(新添加DIY类型，设置DIY类型，需要设置+(void)setSYTserverURL:(NSString*)serverURL;中的serverURL)
 */
-(void)setPayStyle:(NSString*)payStyle;

/**
 支付方式一 发起支付流程
 @param appid 商户的appid
 @param channel 支付方式，如支付宝，微信。wdepay:万达链支付   alipay:支付宝  uppay:银联   weixin:微信
 @param submerno 子商户号
 @param order_no 订单号
 @param amount 金额（分）
 @param subject 商品名称
 @param body 商品描述
 @param description 订单描述
 @param scheme 当前app的URLscheme
 @param appkey 商户的appkey
 @param viewcontroller 调用方当前控制器
 */
-(void)pay_use_SYT_with_appid:(NSString *)appid
                      channel:(NSString *)channel
                     submerno:(NSString*)submerno
                     order_no:(NSString *)order_no
                       amount:(NSString *)amount
                      subject:(NSString *)subject
                         body:(NSString *)body
                  description:(NSString *)description
                   fromScheme:(NSString *)scheme
                          key:(NSString *)appkey
               viewcontroller:(UIViewController *)viewcontroller;

/*
 支付方式二
 如下
 */
/**
 支付方式二 发起支付流程
 @param appid 商户的appid
 @param channel 支付方式，如支付宝，微信。wdepay:万达链支付   alipay:支付宝  uppay:银联   weixin:微信
 @param submerno 子商户号
 @param order_no 订单号
 @param amount 金额（分）
 @param subject 商品名称
 @param body 商品描述
 @param description 订单描述
 @param scheme 当前app的URLscheme
 @param appkey 商户的appkey
 @param viewcontroller 调用方当前控制器
 @param style 支付环境  SC:生产  CS:测试   为nil时是生产环境  默认为生产环境；(新添加DIY类型，设置DIY类型，需要设置+(void)setSYTserverURL:(NSString*)serverURL;中的serverURL)
 */
-(void)pay_use_SYT_with_appid:(NSString *)appid
                      channel:(NSString *)channel
                     submerno:(NSString*)submerno
                     order_no:(NSString *)order_no
                       amount:(NSString *)amount
                      subject:(NSString *)subject
                         body:(NSString *)body
                  description:(NSString *)description
                   fromScheme:(NSString *)scheme
                          key:(NSString *)appkey
               viewcontroller:(UIViewController *)viewcontroller
                     payStyle:(NSString*)style;



#pragma mark 以下为类方法进行支付
/**
 处理微信支付宝等外部app反馈信息
 @param url 外部反馈的url。
 */
+ (BOOL)handleOpenUrl:(NSURL *)url;

/*
 #################################支付方式一
 只用一个方法集成支付
 如下#######################################
 */
/**
 支付方式一 发起支付流程
 @param appid 商户的appid
 @param channel 支付方式，如支付宝，微信。wdepay:万达链支付   alipay:支付宝  uppay:银联   weixin:微信
 @param submerno 子商户号
 @param order_no 订单号
 @param amount 金额（分）
 @param subject 商品名称
 @param body 商品描述
 @param description 订单描述
 @param scheme 当前app的URLscheme
 @param appkey 商户的appkey
 @param viewcontroller 调用方当前控制器
 @param style 支付环境  SC:生产  CS:测试   为nil时是生产环境  默认为生产环境；(新添加DIY类型，设置DIY类型，需要设置+(void)setSYTserverURL:(NSString*)serverURL;中的serverURL)
 @param delegate 设置代理对象
 */
+(void)pay_use_SYT_with_appid:(NSString *)appid
                      channel:(NSString *)channel
                     submerno:(NSString*)submerno
                     order_no:(NSString *)order_no
                       amount:(NSString *)amount
                      subject:(NSString *)subject
                         body:(NSString *)body
                  description:(NSString *)description
                   fromScheme:(NSString *)scheme
                          key:(NSString *)appkey
               viewcontroller:(UIViewController *)viewcontroller
                     payStyle:(NSString*)style
                  theDelegate:(id<ShouYinTaiDelegate>)delegate;

/**
 设置代理委托对象
 */
+ (void)setSYTDelegate:(id<ShouYinTaiDelegate>)delegate;
/*
 #################################支付方式一
 如下#######################################
 */
/**
 支付方式二 设置支付环境。不执行此方法的情况下为生产环境。
 @param payStyle 支付环境  SC:生产  CS:测试   为nil时是生产环境  默认为生产环境；(新添加DIY类型，设置DIY类型，需要设置+(void)setSYTserverURL:(NSString*)serverURL;中的serverURL)
 */
+(void)setPayStyle:(NSString*)payStyle;
/**
 支付方式二 发起支付流程
 @param appid 商户的appid
 @param channel 支付方式，如支付宝，微信。wdepay:万达链支付   alipay:支付宝  uppay:银联   weixin:微信
 @param submerno 子商户号
 @param order_no 订单号
 @param amount 金额（分）
 @param subject 商品名称
 @param body 商品描述
 @param description 订单描述
 @param scheme 当前app的URLscheme
 @param appkey 商户的appkey
 @param viewcontroller 调用方当前控制器
 */
+(void)pay_use_SYT_with_appid:(NSString *)appid
                      channel:(NSString *)channel
                     submerno:(NSString*)submerno
                     order_no:(NSString *)order_no
                       amount:(NSString *)amount
                      subject:(NSString *)subject
                         body:(NSString *)body
                  description:(NSString *)description
                   fromScheme:(NSString *)scheme
                          key:(NSString *)appkey
               viewcontroller:(UIViewController *)viewcontroller;
/*
 #################################支付方式三
 如下#######################################
 */
/**
 支付方式三 发起支付流程
 @param appid 商户的appid
 @param channel 支付方式，如支付宝，微信。wdepay:万达链支付   alipay:支付宝  uppay:银联   weixin:微信
 @param submerno 子商户号
 @param order_no 订单号
 @param amount 金额（分）
 @param subject 商品名称
 @param body 商品描述
 @param description 订单描述
 @param scheme 当前app的URLscheme
 @param appkey 商户的appkey
 @param viewcontroller 调用方当前控制器
 @param style 支付环境  SC:生产  CS:测试   为nil时是生产环境  默认为生产环境；(新添加DIY类型，设置DIY类型，需要设置+(void)setSYTserverURL:(NSString*)serverURL;中的serverURL)
 */
+(void)pay_use_SYT_with_appid:(NSString *)appid
                      channel:(NSString *)channel
                     submerno:(NSString*)submerno
                     order_no:(NSString *)order_no
                       amount:(NSString *)amount
                      subject:(NSString *)subject
                         body:(NSString *)body
                  description:(NSString *)description
                   fromScheme:(NSString *)scheme
                          key:(NSString *)appkey
               viewcontroller:(UIViewController *)viewcontroller
                     payStyle:(NSString*)style;

@end
