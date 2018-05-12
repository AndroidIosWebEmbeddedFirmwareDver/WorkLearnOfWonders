//
//  VanderConfig.h
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

#pragma mark - 开发环境第三方服务 KEY
//#ifdef EDEVELOPMENT
#if EDEVELOPMENT

/// API
#define API_BASE_URL @"http://10.1.93.60:7005/sichuan-user/api"

///
#define UM_TRACK_KEY  @"58219d03c62dca49a40018ed"
#define BAIDU_MAP_KEY @"CCNMET7XW4flcEyday3ik71RcvSmqyEn"
#define SHARE_SDK_KEY @"1940734b5dc96"

#pragma mark 环信
#define EMOPTIONS_APP_KEY @"1118170526115566#healthcloudsc"
#define EMOPTIONS_APNSCERTNAME @"aps_development"

#pragma mark 分享
/// weibo
#define SINA_WEIBO_APP_KEY @"2581310225"
#define SINA_WEIBO_APP_SECRET @"f9c556542f6e9342948fb779f8269917"
#define SINA_WEIBO_REDIRECT_RUL @"http://www.wdjky.com/healthcloud2/"

/// wechat
#define WECHAT_APP_ID @"wx4a14e9ee60eab68a"
#define WECHAT_APP_SECRET @"4adce999662500f74fd7290df08041c5"

/// QQ
#define QQ_APP_ID @"1105413949"
#define QQ_APP_KEY @"cwZmeumz0Ln7nDaU"

//链支付商户号
#define LINK_PAY_APPID         @"308111700000088"
#define LINK_PAY_SUBNUM        @"308111700000088"
#define LINK_PAY_STYLE         @"CS"
#define LINK_PAY_SCHEME        @"com.wonders.health.venus.open.user"

#pragma mark - 测试环境第三方服务 KEY

#elif ETEST

/// API
#define API_BASE_URL @"http://218.80.250.99/sichuan-user-te/api"
#define UM_TRACK_KEY @"58219d03c62dca49a40018ed"
#define BAIDU_MAP_KEY @"CCNMET7XW4flcEyday3ik71RcvSmqyEn"
#define SHARE_SDK_KEY @"1940734b5dc96"

#pragma mark 环信
#define EMOPTIONS_APP_KEY @"1118170526115566#healthcloudsc"
#define EMOPTIONS_APNSCERTNAME @"aps"

#pragma mark 分享
/// weibo
#define SINA_WEIBO_APP_KEY @"2581310225"
#define SINA_WEIBO_APP_SECRET @"f9c556542f6e9342948fb779f8269917"
#define SINA_WEIBO_REDIRECT_RUL @"http://www.wdjky.com/healthcloud2/"

/// wechat
#define WECHAT_APP_ID @"wx4a14e9ee60eab68a"
#define WECHAT_APP_SECRET @"4adce999662500f74fd7290df08041c5"

/// QQ
#define QQ_APP_ID @"1105413949"
#define QQ_APP_KEY @"cwZmeumz0Ln7nDaU"

//链支付商户号
#define LINK_PAY_APPID         @"308111700000088"
#define LINK_PAY_SUBNUM        @"308111700000088"
#define LINK_PAY_STYLE         @"CS"
#define LINK_PAY_SCHEME        @"com.wonders.health.venus.open.user"

#pragma mark - 预发布(同生产)环境第三方服务 KEY

#elif ERELEASE
/// API
#define API_BASE_URL @"http://www.scwdjk.com/sichuan-user/api"
//#define API_BASE_URL @"https://www.scwdjk.com/sichuan-user/api"

#define UM_TRACK_KEY @"58219d03c62dca49a40018ed"
#define BAIDU_MAP_KEY @"CCNMET7XW4flcEyday3ik71RcvSmqyEn"
#define SHARE_SDK_KEY @"1940734b5dc96"

#pragma mark 环信
#define EMOPTIONS_APP_KEY @"1118170526115566#healthcloudsc"
#define EMOPTIONS_APNSCERTNAME @"aps"

#pragma mark 分享
/// weibo
#define SINA_WEIBO_APP_KEY @"2581310225"
#define SINA_WEIBO_APP_SECRET @"f9c556542f6e9342948fb779f8269917"
#define SINA_WEIBO_REDIRECT_RUL @"http://www.wdjky.com/healthcloud2/"

/// wechat
#define WECHAT_APP_ID @"wx4a14e9ee60eab68a"
#define WECHAT_APP_SECRET @"4adce999662500f74fd7290df08041c5"

/// QQ
#define QQ_APP_ID @"1105413949"
#define QQ_APP_KEY @"cwZmeumz0Ln7nDaU"

//链支付商户号
#define LINK_PAY_APPID         @"308111700000075"
#define LINK_PAY_SUBNUM        @"308111700000075"
#define LINK_PAY_STYLE         @"SC"
#define LINK_PAY_SCHEME        @"com.wonders.health.venus.open.user"

#endif










