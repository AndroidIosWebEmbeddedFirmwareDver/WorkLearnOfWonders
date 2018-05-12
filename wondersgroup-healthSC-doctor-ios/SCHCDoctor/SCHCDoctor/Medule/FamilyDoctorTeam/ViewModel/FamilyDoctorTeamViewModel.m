//
//  FamilyDoctorTeamViewModel.m
//  SCHCPatient
//
//  Created by ZJW on 2017/6/5.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "FamilyDoctorTeamViewModel.h"


@implementation FamilyDoctorTeamViewModel

- (void)getFamilyDoctorTeamComplete:(void (^)(void))complete failure:(void (^)(NSError *error))failure {
    
    FamilyDoctorTeamModel *model3 = [FamilyDoctorTeamModel new];
    model3.teamName = @"阿斯顿发了空间";
    model3.teamAddress = @"地址地方撒打发";

    FamilyDoctorTeamModel *model1 = [FamilyDoctorTeamModel new];
    model1.teamName = @"阿斯顿发了空间1";
    model1.teamAddress = @"地址地方撒打发1";

    FamilyDoctorTeamModel *model2 = [FamilyDoctorTeamModel new];
    model2.teamName = @"阿斯顿发了空间2";
    model2.teamAddress = @"地址地方撒打发2";

    self.teamArray = [NSArray arrayWithObjects:model3,model1,model2, nil];
    self.requestCompeleteType = RequestCompeleteEmpty;
    return;
    
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    NSDictionary *dic = @{@"orgCode":@"",
                          @"doctorIdCard":@""};
    
    [self.adapter request:MY_TEAM_LIST
                   params:dic
                    class:[FamilyDoctorTeamModel class]
             responseType:Response_List
                   method:Request_GET
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                      ListModel *list = response.data;
                      self.teamArray = list.content;
                      self.hasMore = list.more.boolValue;
                      self.moreParams = list.more_params;
                      if (self.teamArray.count) {
                          self.requestCompeleteType = RequestCompeleteSuccess;
                      }else {
                          self.requestCompeleteType = RequestCompeleteEmpty;
                      }
                      complete();
                  } failure:^(NSURLSessionDataTask *task, NSError *error) {
                      self.requestCompeleteType = RequestCompeleteError;
                      failure(error);
                  }];
}

- (void)getMoreFamilyDoctorTeamComplete:(void (^)(void))complete failure:(void (^)(NSError *error))failure {
    
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithObjectsAndKeys:
                                @"orgCode",@"",
                                @"doctorIdCard",@"", nil];
    [dic addEntriesFromDictionary:self.moreParams];
    
    [self.adapter request:MY_TEAM_LIST
                   params:dic
                    class:[FamilyDoctorTeamModel class]
             responseType:Response_List
                   method:Request_GET
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                      ListModel *list = response.data;
                      self.teamArray = [self.teamArray arrayByAddingObjectsFromArray:list.content];
                      self.hasMore = list.more.boolValue;
                      self.moreParams = list.more_params;
                      if (self.teamArray.count) {
                          self.requestCompeleteType = RequestCompeleteSuccess;
                      }else {
                          self.requestCompeleteType = RequestCompeleteEmpty;
                      }
                      complete();
                  } failure:^(NSURLSessionDataTask *task, NSError *error) {
                      self.requestCompeleteType = RequestCompeleteError;
                      failure(error);
                  }];
}


@end
