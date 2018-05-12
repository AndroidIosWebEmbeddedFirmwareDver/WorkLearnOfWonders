//
//  DoctorDetailMoreJudgeViewModel.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/22.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DoctorDetailMoreJudgeViewModel.h"
#import "DoctorDetailJudgeModel.h"


@implementation DoctorDetailMoreJudgeViewModel


#pragma mark - 获取评价列表

- (void)getJudgeList:(void (^)(void))success failure:(void (^)(void))failure {
    
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    [params setObject:self.request_doctor_id forKey:@"doctorId"];
    
    [self.adapter request:DOCTOR_EVALUATE_LIST_URL params:params class:nil responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        self.dataArray = [DoctorDetailJudgeModel mj_objectArrayWithKeyValuesArray:response.data[@"content"]];
        
        UILabel *tempLabel = [UILabel new];
        tempLabel.font = [UIFont systemFontOfSize:14.];
        tempLabel.numberOfLines = 0;
        for (DoctorDetailJudgeModel *model in self.dataArray) {
            tempLabel.text = model.content;
            CGSize size = [tempLabel sizeThatFits:CGSizeMake(SCREEN_WIDTH-30., MAXFLOAT)];
            model.cellHeight = size.height + 50.;
        }
        
        if (self.dataArray.count == 0) {
            self.requestCompeleteType = RequestCompeleteEmpty;
            success();
            return ;
        }
        else {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }
        
        self.hasMore = [response.data[@"more"] boolValue];
        if (self.hasMore) {
            self.moreParams = response.data[@"more_params"];
        }
        
        success();

    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        if (![Global global].networkReachable) {
            self.requestCompeleteType = RequestCompeleteNoWifi;
        }
        else {
            self.requestCompeleteType = RequestCompeleteError;
        }
        
        failure();
    }];
}

- (void)getMoreJudgeList:(void (^)(void))success failure:(void (^)(void))failure {
    
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    [params setObject:self.request_doctor_id forKey:@"doctorId"];
    [params setObject:self.moreParams[@"flag"] forKey:@"flag"];
    
    [self.adapter request:DOCTOR_EVALUATE_LIST_URL params:params class:nil responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        NSArray *datas = [DoctorDetailJudgeModel mj_objectArrayWithKeyValuesArray:response.data[@"content"]];
        
        UILabel *tempLabel = [UILabel new];
        tempLabel.font = [UIFont systemFontOfSize:14.];
        tempLabel.numberOfLines = 0;
        for (DoctorDetailJudgeModel *model in datas) {
            tempLabel.text = model.content;
            CGSize size = [tempLabel sizeThatFits:CGSizeMake(SCREEN_WIDTH-30., MAXFLOAT)];
            model.cellHeight = size.height + 50.;
        }
        
        [self.dataArray addObjectsFromArray:datas];
        
        self.requestCompeleteType = RequestCompeleteSuccess;
        
        self.hasMore = [response.data[@"more"] boolValue];
        if (self.hasMore) {
            self.moreParams = response.data[@"more_params"];
        }
        
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        if (![Global global].networkReachable) {
            self.requestCompeleteType = RequestCompeleteNoWifi;
        }
        else {
            self.requestCompeleteType = RequestCompeleteError;
        }
        
        failure();
    }];
}

@end
