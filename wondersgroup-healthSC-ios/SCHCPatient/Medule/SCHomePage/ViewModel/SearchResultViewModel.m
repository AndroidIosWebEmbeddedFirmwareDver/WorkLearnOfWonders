//
//  SearchResultViewModel.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SearchResultViewModel.h"

@implementation SearchResultViewModel
-(void)getSearchResultList:(NSString * )keyword success:(void (^)(void))success failure:(void (^)(NSError * error))failure{
   
        NSDictionary * params = @{@"keyword":keyword};
    if(![[Global global] networkReachable])
    {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    [self.adapter request:SearchReslutURL params:params class:[SearchResultAllModel class]responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {

        self.allModel = response.data;
       
        if (success) {
            success();
            if (self.allModel.hospitals.content.count==0&&self.allModel.doctors.content.count==0&&self.allModel.articles.content.count==0) {
                self.requestCompeleteType = RequestCompeleteEmpty;
            }else {
                self.requestCompeleteType = RequestCompeleteSuccess;
            }
//            NSLog(@"********%@",self.allModel.hospitals.content);
//             NSLog(@"^^^^^^^^^^^^^^^医院：%ld 医生：%ld 文章：%ld",self.allModel.hospitals.content.count,self.allModel.doctors.content.count,self.allModel.articles.content.count);
        }
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        if (failure) {
            failure(error);
        }
    }];

}
@end
