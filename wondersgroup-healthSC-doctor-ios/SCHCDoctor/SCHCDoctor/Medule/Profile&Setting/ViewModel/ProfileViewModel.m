//
//  ProfileViewModel.m
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ProfileViewModel.h"

@implementation ProfileViewModel

-(void)reloadDatas {
    self.headModel = [UserInfoModel new];
    self.headModel.name = @"张三岁";
    self.headModel.mobile = @"张三岁公司";
    
    ProfileCellModel *model = [ProfileCellModel new];
    model.image = @"帮助与反馈";
    model.content = @"帮助与反馈";
    
    ProfileCellModel *model1 = [ProfileCellModel new];
    model1.image = @"设置";
    model1.content = @"设置";

    self.datas = @[model,model1];
}

-(void)getProfileData {
    
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
    }
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithDictionary:
                                   @{
                                     @"payMode":@"4",
                                     @"payState":@"2",
                                     }];
    
    [self.adapter request:MY_COUNT params:params class:[ProfileModel class] responseType:Response_Object method:Request_POST needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        NSLog(@"%@",response.data);
        self.functionModel = response.data;

        self.requestCompeleteType = RequestCompeleteSuccess;

    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        self.requestCompeleteType = RequestCompeleteError;

    }];

}

@end
