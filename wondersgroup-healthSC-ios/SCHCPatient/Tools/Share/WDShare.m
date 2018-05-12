//
//  WDShare.m
//  EyeProtection
//
//  Created by 杜凯 on 16/3/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDShare.h"
#import <ShareSDKUI/ShareSDK+SSUI.h>
//#import "WXApi.h"

@implementation WDShare


#pragma mark - Public Methods
+ (instancetype)shareInstance {
    
    static WDShare *instance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [WDShare new];
    });
    return instance;
}
//分享
-(void)shareInView: (UIView *)view
           content: (ShareModel *)model
           success: (void (^)(NSString *success))success
           failure: (void (^)(NSString *failure))failure
            cancel: (void (^)(NSString *cancel))cancel{
    
    //1、创建分享参数（必要）
    NSMutableDictionary *shareParams = [NSMutableDictionary dictionary];
    [shareParams SSDKSetupShareParamsByText:model.desc
                                     images:[NSURL URLWithString:model.thumb]
                                        url:[NSURL URLWithString:model.url]
                                      title:model.title
                                       type:SSDKContentTypeAuto];
    
    // 定制新浪微博的分享内容
    [shareParams SSDKSetupSinaWeiboShareParamsByText:[model.desc stringByAppendingString:model.url] title:model.title image:[NSURL URLWithString:model.thumb] url:[NSURL URLWithString:model.url] latitude:0 longitude:0 objectID:nil type:SSDKContentTypeAuto];
    // 定制      QQ的分享内容
    [shareParams SSDKSetupQQParamsByText:model.desc title:model.title url:[NSURL URLWithString:model.url] thumbImage:[NSURL URLWithString:model.thumb] image:[NSURL URLWithString:model.thumb] type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformTypeQQ];
    
    
    // 定制微信好友的分享内容
    [shareParams SSDKSetupWeChatParamsByText:model.desc title:model.title url:[NSURL URLWithString:model.url] thumbImage:[NSURL URLWithString:model.thumb] image:[NSURL URLWithString:model.thumb] musicFileURL:nil extInfo:nil fileData:nil emoticonData:nil type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformSubTypeWechatSession];// 微信好友子平台
    
     [shareParams SSDKSetupWeChatParamsByText:model.desc title:model.title url:[NSURL URLWithString:model.url] thumbImage:[NSURL URLWithString:model.thumb] image:[NSURL URLWithString:model.thumb] musicFileURL:nil extInfo:nil fileData:nil emoticonData:nil type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformSubTypeWechatTimeline];// 微信朋友圈子平台
    //[shareParams SSDKSetupWeChatParamsByText:@"定制微信的分享内容sssssscesh" title:@"title" url:[NSURL URLWithString:@"http://www.wdjky.com/healthcloud2/web/adpages/visits"] thumbImage:nil image:[UIImage imageNamed:@"传入的图片名"] musicFileURL:nil extInfo:nil fileData:nil emoticonData:nil type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformSubTypeWechatSession];// 微信好友子平台
    
  
    SSUIShareActionSheetController *sheet =[ShareSDK showShareActionSheet:view items:@[[NSNumber numberWithInteger:SSDKPlatformTypeSinaWeibo],[NSNumber numberWithInteger:SSDKPlatformSubTypeWechatSession],[NSNumber numberWithInteger:SSDKPlatformSubTypeWechatTimeline],[NSNumber numberWithInteger:SSDKPlatformSubTypeQQFriend]] shareParams:shareParams onShareStateChanged:^(SSDKResponseState state, SSDKPlatformType platformType, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error, BOOL end) {
        switch (state) {
            case SSDKResponseStateSuccess:
            {
                success(@"分享成功");
                
                
                break;
            }
            case SSDKResponseStateFail:
            {
               failure(@"分享失败");
                
                
                break;
            }
            case SSDKResponseStateCancel:
            {
                failure(@"取消分享");
                
                
                break;
            }

            default:
                break;
        }

    }];
    [sheet.directSharePlatforms addObject:@(SSDKPlatformTypeSinaWeibo)];
}

//- (void)shareInView:(UIView *)view
//            content:(ShareModel *)model{
//    
//    //model.thumb = @"http://h.hiphotos.baidu.com/zhidao/pic/item/eac4b74543a9822628850ccc8c82b9014b90eb91.jpg";
//    //model.desc = @"你还按时打算离开都会卡死接电话卡时间";
//   // model.title = @"按时打算看按时askask加上爱迪生";
//    //1、创建分享参数（必要）
//    NSMutableDictionary *shareParams = [NSMutableDictionary dictionary];
//    [shareParams SSDKSetupShareParamsByText:model.brief
//                                     images:[NSURL URLWithString:model.thumb]
//                                        url:[NSURL URLWithString:model.shareUrl]
//                                      title:model.title
//                                       type:SSDKContentTypeAuto];
//    
//    // 定制新浪微博的分享内容
//    [shareParams SSDKSetupSinaWeiboShareParamsByText:[model.brief stringByAppendingString:model.shareUrl] title:model.title image:[NSURL URLWithString:model.thumb] url:[NSURL URLWithString:model.shareUrl] latitude:0 longitude:0 objectID:nil type:SSDKContentTypeAuto];
//    // 定制      QQ的分享内容
//    [shareParams SSDKSetupQQParamsByText:model.brief title:model.title url:[NSURL URLWithString:model.shareUrl] thumbImage:[NSURL URLWithString:model.thumb] image:[NSURL URLWithString:model.thumb] type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformTypeQQ];
//    
//    
//    // 定制微信好友的分享内容
//    [shareParams SSDKSetupWeChatParamsByText:model.brief title:model.title url:[NSURL URLWithString:model.shareUrl] thumbImage:[NSURL URLWithString:model.thumb] image:[NSURL URLWithString:model.thumb] musicFileURL:nil extInfo:nil fileData:nil emoticonData:nil type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformSubTypeWechatSession];// 微信好友子平台
//    
//    [shareParams SSDKSetupWeChatParamsByText:model.brief title:model.title url:[NSURL URLWithString:model.shareUrl] thumbImage:[NSURL URLWithString:model.thumb] image:[NSURL URLWithString:model.thumb] musicFileURL:nil extInfo:nil fileData:nil emoticonData:nil type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformSubTypeWechatTimeline];// 微信朋友圈子平台
//    //[shareParams SSDKSetupWeChatParamsByText:@"定制微信的分享内容sssssscesh" title:@"title" url:[NSURL URLWithString:@"http://www.wdjky.com/healthcloud2/web/adpages/visits"] thumbImage:nil image:[UIImage imageNamed:@"传入的图片名"] musicFileURL:nil extInfo:nil fileData:nil emoticonData:nil type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformSubTypeWechatSession];// 微信好友子平台
//    
////    
////    [ShareSDK share:SSDKPlatformTypeSinaWeibo //传入分享的平台类型
////         parameters:shareParams
////     onStateChanged:^(SSDKResponseState state, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error) { // 回调处理....}];
////     }];
//    
//    SSUIShareActionSheetController *sheet =[ShareSDK showShareActionSheet:view items:@[[NSNumber numberWithInteger:SSDKPlatformTypeSinaWeibo],[NSNumber numberWithInteger:SSDKPlatformSubTypeWechatSession],[NSNumber numberWithInteger:SSDKPlatformSubTypeWechatTimeline],[NSNumber numberWithInteger:SSDKPlatformSubTypeQQFriend]] shareParams:shareParams onShareStateChanged:^(SSDKResponseState state, SSDKPlatformType platformType, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error, BOOL end) {
//        switch (state) {
//            case SSDKResponseStateSuccess:
//            {
////                UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"分享成功"
////                                                                    message:nil
////                                                                   delegate:nil
////                                                          cancelButtonTitle:@"确定"
////                                                          otherButtonTitles:nil];
////                if (alertView) {
////                    [alertView show];
////                    return ;
////                }
//                [MBProgressHUDHelper showHudWithText:@"分享成功"];
//                break;
//            }
//            case SSDKResponseStateFail:
//            {
////                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"分享失败"
////                                                                message:[NSString stringWithFormat:@"%@",error]
////                                                               delegate:nil
////                                                      cancelButtonTitle:@"OK"
////                                                      otherButtonTitles:nil, nil];
////                [alert show];
//                [MBProgressHUDHelper showHudWithText:@"分享失败"];
//                break;
//            }
//            default:
//                break;
//        }
//        
//    }];
//     [sheet.directSharePlatforms addObject:@(SSDKPlatformTypeSinaWeibo)];
//}
- (void)shareInView:(UIView *)view
            content:(ShareModel *)model{
    
    WS(weakSelf)
    
    self.shareActionSheet = [ShareAcionSheet new];
    self.shareActionSheet.shareBlock = ^(ShareType type) {
        switch (type) {
            case ShareTypeSina:
                [weakSelf shareSinaWithContent:model];
                break;
            case ShareTypeWeiXinFriend:
                [weakSelf shareWeiXinFriendWithContent:model];
                break;
            case ShareTypeWeiXinFriendCircle:
                [weakSelf shareWeiXinFriendCircleWithContent:model];
                break;
            case ShareTypeQQ:
                [weakSelf shareQQWithContent:model];
                break;
                
            default:
                break;
        }
    };
    [self.shareActionSheet show];
}

- (void)shareInView:(UIView *)view
            content:(ShareModel *)model
           topic_id:(NSString *)topic_id
              icons:(NSArray *)icons
             titles:(NSArray *)titles{

    WS(weakSelf)
    
    self.shareActionSheet = [[ShareAcionSheet alloc]initWithCustomIcons:icons withCustomTitles:titles];
    self.shareActionSheet.shareBlock = ^(ShareType type) {
        switch (type) {
            case ShareTypeSina:
                [weakSelf shareSinaWithContent:model];
                break;
            case ShareTypeWeiXinFriend:
                [weakSelf shareWeiXinFriendWithContent:model];
                break;
            case ShareTypeWeiXinFriendCircle:
                [weakSelf shareWeiXinFriendCircleWithContent:model];
                break;
            case ShareTypeQQ:
                [weakSelf shareQQWithContent:model];
                break;
                
            default:
                break;
        }
    };
    
    
    
    
    [self.shareActionSheet show];

}


- (void)shareContent:(NSString *)content
                View:(UIView *)view
                 url:(NSString *)url
                type:(SSDKPlatformType)type{
    if (type == SSDKPlatformTypeSinaWeibo) {
        [ShareSDK cancelAuthorize: type];
    }
    NSMutableDictionary *shareParams = [NSMutableDictionary dictionary];
    [shareParams SSDKSetupShareParamsByText:content
                                     images:[UIImage imageNamed:@"logo"]
                                        url:[NSURL URLWithString:url]
                                      title:@"健康云"
                                       type:SSDKContentTypeAuto];
    
    // 定制新浪微博的分享内容
    [shareParams SSDKSetupSinaWeiboShareParamsByText:content title:@"健康云" image:[UIImage imageNamed:@"logo"] url:[NSURL URLWithString:url] latitude:0 longitude:0 objectID:nil type:SSDKContentTypeAuto];
    // 定制      QQ的分享内容
    [shareParams SSDKSetupQQParamsByText:content title:@"健康云" url:[NSURL URLWithString:url] thumbImage:[UIImage imageNamed:@"logo"] image:[UIImage imageNamed:@"logo"] type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformTypeQQ];
    
    
    // 定制微信好友的分享内容
    [shareParams SSDKSetupWeChatParamsByText:content title:@"健康云" url:[NSURL URLWithString:url] thumbImage:[UIImage imageNamed:@"logo"] image:[UIImage imageNamed:@"logo"] musicFileURL:nil extInfo:nil fileData:nil emoticonData:nil type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformSubTypeWechatSession];// 微信好友子平台
    
    // 定制微信好友的分享内容
    [shareParams SSDKSetupWeChatParamsByText:content title:@"健康云" url:[NSURL URLWithString:url] thumbImage:[UIImage imageNamed:@"logo"] image:[UIImage imageNamed:@"logo"] musicFileURL:nil extInfo:nil fileData:nil emoticonData:nil type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformSubTypeWechatTimeline];// 微信好友子平台;// 微信朋友圈子平台
    //[shareParams SSDKSetupWeChatParamsByText:@"定制微信的分享内容sssssscesh" title:@"title" url:[NSURL URLWithString:@"http://www.wdjky.com/healthcloud2/web/adpages/visits"] thumbImage:nil image:[UIImage imageNamed:@"传入的图片名"] musicFileURL:nil extInfo:nil fileData:nil emoticonData:nil type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformSubTypeWechatSession];// 微信好友子平台
  
    [ShareSDK showShareActionSheet:view items:@[[NSNumber numberWithInteger:SSDKPlatformTypeSinaWeibo],[NSNumber numberWithInteger:SSDKPlatformSubTypeWechatSession],[NSNumber numberWithInteger:SSDKPlatformSubTypeWechatTimeline],[NSNumber numberWithInteger:SSDKPlatformSubTypeQQFriend]] shareParams:shareParams onShareStateChanged:^(SSDKResponseState state, SSDKPlatformType platformType, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error, BOOL end) {
        switch (state) {
            case SSDKResponseStateSuccess:
            {
//                UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"分享成功"
//                                                                    message:nil
//                                                                   delegate:nil
//                                                          cancelButtonTitle:@"确定"
//                                                          otherButtonTitles:nil];
                [MBProgressHUDHelper showHudWithText:@"分享成功"];
//                [alertView show];
                break;
            }
            case SSDKResponseStateFail:
            {
//                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"分享失败"
//                                                                message:[NSString stringWithFormat:@"%@",error]
//                                                               delegate:nil
//                                                      cancelButtonTitle:@"OK"
//                                                      otherButtonTitles:nil, nil];
//                [alert show];
                [MBProgressHUDHelper showHudWithText:@"分享失败"];
                break;
            }
            default:
                break;
        }
        
    }];

    
}

#pragma mark    - Share单个分享
-(void)shareSinaWithContent:(ShareModel *)model {
    WS(weakSelf)

    NSMutableDictionary *shareParams = [NSMutableDictionary dictionary];
    [shareParams SSDKSetupShareParamsByText:model.desc
                                     images:[NSURL URLWithString:model.thumb]
                                        url:[NSURL URLWithString:model.url]
                                      title:model.title
                                       type:SSDKContentTypeAuto];
    
    // 定制新浪微博的分享内容
    [shareParams SSDKSetupSinaWeiboShareParamsByText:[model.desc stringByAppendingString:model.url] title:model.title image:[NSURL URLWithString:model.thumb] url:[NSURL URLWithString:model.url] latitude:0 longitude:0 objectID:nil type:SSDKContentTypeAuto];
    
    [ShareSDK share:SSDKPlatformTypeSinaWeibo //传入分享的平台类型
         parameters:shareParams
     onStateChanged:^(SSDKResponseState state, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error) {
         [weakSelf showHUDBlock:state];
     }];
}

-(void)shareWeiXinFriendWithContent:(ShareModel *)model {
//    if (![WXApi isWXAppInstalled]) {
//        [MBProgressHUDHelper showHudWithText:@""];
//        return;
//    }
    WS(weakSelf)

    NSMutableDictionary *shareParams = [NSMutableDictionary dictionary];
    [shareParams SSDKSetupShareParamsByText:model.desc
                                     images:[NSURL URLWithString:model.thumb]
                                        url:[NSURL URLWithString:model.url]
                                      title:model.title
                                       type:SSDKContentTypeAuto];
    
    // 定制微信好友的分享内容
    [shareParams SSDKSetupWeChatParamsByText:model.desc title:model.title url:[NSURL URLWithString:model.url] thumbImage:[NSURL URLWithString:model.thumb] image:[NSURL URLWithString:model.thumb] musicFileURL:nil extInfo:nil fileData:nil emoticonData:nil type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformSubTypeWechatSession];// 微信好友子平台
    
    [ShareSDK share:SSDKPlatformSubTypeWechatSession //传入分享的平台类型
         parameters:shareParams
     onStateChanged:^(SSDKResponseState state, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error) {
         [weakSelf showHUDBlock:state];
     }];
}

-(void)shareWeiXinFriendCircleWithContent:(ShareModel *)model {
//    if (![WXApi isWXAppInstalled]) {
//        [MBProgressHUDHelper showHudWithText:@""];
//        return;
//    }
    WS(weakSelf)

    NSMutableDictionary *shareParams = [NSMutableDictionary dictionary];
    [shareParams SSDKSetupShareParamsByText:model.desc
                                     images:[NSURL URLWithString:model.thumb]
                                        url:[NSURL URLWithString:model.url]
                                      title:model.title
                                       type:SSDKContentTypeAuto];
    
    
    [shareParams SSDKSetupWeChatParamsByText:model.desc title:model.title url:[NSURL URLWithString:model.url] thumbImage:[NSURL URLWithString:model.thumb] image:[NSURL URLWithString:model.thumb] musicFileURL:nil extInfo:nil fileData:nil emoticonData:nil type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformSubTypeWechatTimeline];// 微信朋友圈子平台
    
    [ShareSDK share:SSDKPlatformSubTypeWechatTimeline //传入分享的平台类型
         parameters:shareParams
     onStateChanged:^(SSDKResponseState state, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error) {
         [weakSelf showHUDBlock:state];
     }];
}

-(void)shareQQWithContent:(ShareModel *)model {
//    if (![QQApiInterface isQQInstalled]) {
//        [MBProgressHUDHelper showHudWithText:@""];
//        return;
//    }
    WS(weakSelf)
    
    NSMutableDictionary *shareParams = [NSMutableDictionary dictionary];
    [shareParams SSDKSetupShareParamsByText:model.desc
                                     images:[NSURL URLWithString:model.thumb]
                                        url:[NSURL URLWithString:model.url]
                                      title:model.title
                                       type:SSDKContentTypeAuto];
    
    // 定制      QQ的分享内容
    [shareParams SSDKSetupQQParamsByText:model.desc title:model.title url:[NSURL URLWithString:model.url] thumbImage:[NSURL URLWithString:model.thumb] image:[NSURL URLWithString:model.thumb] type:SSDKContentTypeAuto forPlatformSubType:SSDKPlatformTypeQQ];
    
    [ShareSDK share:SSDKPlatformSubTypeQQFriend //传入分享的平台类型
         parameters:shareParams
     onStateChanged:^(SSDKResponseState state, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error) {
         [weakSelf showHUDBlock:state];
     }];
}

#pragma mark    - method
-(void)showHUDBlock:(SSDKResponseState)state {
    switch (state) {
        case SSDKResponseStateSuccess:
        {
            [MBProgressHUDHelper showHudWithText:@"分享成功"];
            break;
        }
        case SSDKResponseStateFail:
        {
            [MBProgressHUDHelper showHudWithText:@"分享失败"];
            break;
        }
        case SSDKResponseStateCancel:
        {
            [MBProgressHUDHelper showHudWithText:@"取消分享"];
            break;
        }
        default:
            break;
    }
}



@end
