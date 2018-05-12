//
//  TaskService.m
//  VaccinePatient
//
//  Created by Jam on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "TaskService.h"
#import "ResponseAdapter.h"
#import "DBManager.h"
#import <HyphenateLite/HyphenateLite.h>

@implementation TaskService


+ (TaskService *)service {
    
    static dispatch_once_t onceToken;
    static TaskService *service = nil;
    dispatch_once(&onceToken, ^{
        service = [[TaskService alloc] init];
    });
    return service;
}

- (instancetype)init {
    self = [super init];
    if (self) {
    }
    return self;
}

- (void)startService {
    //加载本地全局配置
    [self getlocalAppConfig];
    
    //获取全局配置
    [self getCommonConfig:^{
        //处理广告
        [self changeADSStatus];
    }];
    //获取七牛Token
    [self getQiNiuToken:^{
        
    }];
    
    [self initEMSDK];
}


#pragma mark 初始化环信sdk
- (void)initEMSDK {
    //AppKey:注册的AppKey，详细见下面注释。
    //apnsCertName:推送证书名（不需要加后缀），详细见下面注释。
    EMOptions *options = [EMOptions optionsWithAppkey:EMOPTIONS_APP_KEY];
    options.apnsCertName = EMOPTIONS_APNSCERTNAME;
    [[EMClient sharedClient] initializeSDKWithOptions:options];
    
}

- (void)changeADSStatus {
    //没有广告对象，不需要显示广告
    if(![TaskManager manager].appConfig.advertisement) {
        [[Context context] removeAds];
    }
    else {
        NSString *url = [TaskManager manager].appConfig.advertisement.imgUrl;
        if(url==nil || url.length==0 || [url isEqualToString:@""])
        {
            [[Context context] removeAds];
        }
        else {
            NSString * adsImageurl = [[Context context] getAds];
            if([url isEqualToString: adsImageurl]) {
                NSLog(@"广告图不需要下载");
            }
            else {
                [self downloadImage: url complete:^{
                    [[Context context] saveAds: url];
                    NSLog(@"广告图下载成功");
                } failure:^(NSError *error) {
                    NSLog(@"广告图下载失败 %@", url);
                }];
            }
        }
    }
}

#pragma mark - 获取七牛token
/**
 *  获取七牛token
 *
 */
-(void)getQiNiuToken:(void(^)(void))complete {
    [TaskManager manager].qnToken   = nil;
    [TaskManager manager].domain    = nil;

    [[ResponseAdapter sharedInstance] request: QINIU_TOKEN params: nil class: nil responseType: Response_Object method:Request_GET needLogin:NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        [TaskManager manager].qnToken   = response.data[@"token"];
        [TaskManager manager].domain    = response.data[@"domain"];
        NSLog(@"七牛Token获取成功");
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"获取七牛Token失败");
        complete();
    }];

    
}

#pragma mark - 全局接口
/**
 *  全局接口
 *
 */
-(void)getCommonConfig:(void(^)(void))complete {
    
//    "code": 0,
//    "data": {
//        "updateInfo":{// APP版本更新信息(存在该节点说明有更新,不存在则无更新)
//            "lastVersion":"1.0.0",// 最新版本号
//            "enforceUpdate":0,// 是否强制升级,0-非强制,1-强制
//            "updateLogs":[// 更新信息
//                          "全新上线！",
//                          "敬请期待！"
//                          ]
//        },
//        "advertisement":{// 启动页广告
//            "display":0,// 是否显示,0-不显示,1-显示
//            "skip":0,// 是否允许跳过,0-不允许,1-允许
//            "duration":3000,// 持续时间(毫秒/ms)
//            "imgUrl":"http://www.vaccine.com/",// 图片地址
//            "hoplinks":"http://www.vaccine.com/"// 跳转链接
//        }
//    },
//    "msg":"接口调用失败信息！"

    
    [[ResponseAdapter sharedInstance] request: APP_CONFIG params: nil class: [AppConfigModel class] responseType: Response_Object method:Request_GET needLogin:NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        [TaskManager manager].appConfig = response.data;
        [self saveLocalAppConfig:[TaskManager manager].appConfig];
        NSLog(@"全局接口获取成功");
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"全局接口获取失败");
//        [self performSelector: @selector(getCommonConfig:)  withObject: complete afterDelay: 3.0];
    }];

}

/**
 *  获取本地全局接口数据
 *
 */
- (void)getlocalAppConfig
{
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        [TaskManager manager].appConfig = [[DBManager manager] getLocalAppConfig];
        //清空数据库更新APP对象
        [TaskManager manager].appConfig.appUpdate = nil;
        [[DBManager manager] updateLocalAppConfig: [TaskManager manager].appConfig];
    });
}
/**
 *  保存全局配置数据到本地
 *
 */
- (void)saveLocalAppConfig:(AppConfigModel *)model
{
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        [[DBManager manager] updateLocalAppConfig:model];
    });
}

#pragma mark - 下载图片接口
/**
 *  下载图片接口
 *
 */
//- (void)downloadImage:(NSString *)imageUrl complete:(DownImageBlock)complete {
- (void)downloadImage: (NSString *)imageUrl
             complete: (IMPLCompleteBlock)complete
              failure: (IMPLFailuredBlock)failure {

    if([[SDImageCache  sharedImageCache] imageFromDiskCacheForKey: imageUrl]) {
        NSLog(@"图片本地缓存已有");
        complete();
    }
    else {
        [[SDWebImageManager sharedManager] downloadImageWithURL: [NSURL URLWithString: imageUrl]
                                                        options: 0
                                                       progress: ^(NSInteger receivedSize, NSInteger expectedSize) {
                                                           
                                                       } completed:^(UIImage *image, NSError *error, SDImageCacheType cacheType, BOOL finished, NSURL *imageURL) {
                                                           if (error) {
                                                               NSLog(@"TabImage 下载出错: %@", imageURL);
                                                               failure(error);
                                                           }
                                                           else {
                                                               [[SDImageCache  sharedImageCache] storeImage: image forKey: imageUrl toDisk: YES];
                                                               complete();
                                                           }
                                                       }];
    }
}

#pragma mark - 获取推送别名
/**
 *  获取别名
 *
 */
- (void)getAliasWithClientId: (NSString *)clientId
                    complete: (IMPLCompleteWithResponseBlock)complete
                     failure: (IMPLFailuredBlock)failure
{
    
}

@end
