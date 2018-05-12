//
//  PatientsListViewModel.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/15.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientsListViewModel.h"

@implementation PatientsListViewModel

- (void)getPatients:(void(^)(void))success failed:(void(^)(NSError *error))failed {
/*
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    NSDictionary *params = @{@"":@"",@"":@""};
    NSMutableDictionary * parameters = [[NSMutableDictionary alloc] initWithDictionary:params];
    if (self.moreParams && [self.moreParams objectForKey:@"flag"]) {
        [parameters setValue:[self.moreParams objectForKey:@"flag"] forKey:@"flag"];
    }
    
    NSString *urlStr = nil;
    if (_tag == New_PATIENT) {
        urlStr = Patient_New_List;
    } else {
        urlStr = Patient_List;
    }
    
    [self.adapter request:urlStr params:parameters class:[PatientListModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        ListModel *listModel = response.data;
        self.moreParams = listModel.more_params;
        self.hasMore = [listModel.more boolValue];
        
        NSMutableArray *temp = [[NSMutableArray alloc] initWithArray:self.dataArray];
        [temp addObjectsFromArray:listModel.content];
        self.dataArray = temp;
        
        if (_dataArray.count == 0) {
            self.requestCompeleteType = RequestCompeleteEmpty;
        } else {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }
        
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        self.requestCompeleteType = RequestCompeleteError;
        
        failed(error);
    }];
*/
    
    
    NSMutableArray *temp = [[NSMutableArray alloc] init];
    for (NSInteger i = 0; i<5; i++) {
        PatientListModel *m = [[PatientListModel alloc] init];
        m.name = [NSString stringWithFormat:@"name_%zd",i];
        m.mobile = @"1234567890";
        [temp addObject:m];
    }
    self.dataArray = temp;
}

@end
