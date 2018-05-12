//
//  SCHomePageViewModel.m
//  SZCirculationImage
//
//  Created by Wonders_iOS on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCHomePageViewModel.h"
#import "HomePageModel.h"
#define HOMEURL @"home"
@implementation SCHomePageViewModel
-(void)requestHomeDatasuccess:(void (^)(void))success failure:(void (^)(NSError *))failure{
    if(![[Global global] networkReachable])
    {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    [self.adapter request:HOMEURL params:nil class:[HomePageModel class] responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        self.model = response.data;
        if (success) {
            success();
            [self saveDatas:self.model];
//            NSLog(@"********%@",self.allModel.hospitals.content);
//            NSLog(@"^^^^^^^^^^^^^^^医院：%ld 医生：%ld 文章：%ld",self.allModel.hospitals.content.count,self.allModel.doctors.content.count,self.allModel.articles.content.count);
        }
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        if (failure) {
            failure(error);
        }
    
    }];


}
//首页缓存-------------------
-(void)saveDatas:(HomePageModel * )model{
    [[DBManager manager]saveHomePageDatas:model];
}
@end
