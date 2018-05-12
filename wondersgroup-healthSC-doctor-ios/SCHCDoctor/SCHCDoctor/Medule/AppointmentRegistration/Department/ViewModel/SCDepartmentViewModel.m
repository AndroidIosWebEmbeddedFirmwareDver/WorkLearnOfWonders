//
//  SCDepartmentViewModel.m
//  VaccinePatient
//
//  Created by ZJW on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCDepartmentViewModel.h"

@implementation SCDepartmentViewModel


//获取预约医院一级科室列表
- (void)getNoodleDepartmentlList: (void(^)(void))success
                         failure: (void(^)(void))failure {
    
    
    NSDictionary *params = @{@"hospitalCode" : self.hospitalCode ? self.hospitalCode : @""};
    
    [self.adapter request:QUERYDEPS params: params class: [SCDepartmentModel class] responseType: Response_List method: Request_GET needLogin: YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        self.noodleList = response.data;
        
        [self.childrenList removeAllObjects];
        for (int i = 0; i < [self.noodleList count]; i++) {
            [self.childrenList addObject: [NSMutableArray array]];
        }
        
        
        if (success) {
            success();
        }
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        
        self.noodleList = [NSArray array];
        
        if (failure) {
            failure();
        }
    }];
    
}

//获取预约医院二级科室列表
- (void)getChildreDepartmentlList: (void(^)(void))success
                          failure: (void(^)(void))failure {
    
    
    NSDictionary *params = @{
                             @"hospitalCode"      : self.hospitalCode ? self.hospitalCode : @"",
                             @"hosDeptCode" : self.hosDeptCode ? self.hosDeptCode : @""
                             };
    [self.adapter request:QUERYDEPS params: params class: [SCDepartmentModel class] responseType: Response_List method: Request_GET needLogin: YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        NSArray *list = response.data;
        [self.childrenList removeObjectAtIndex: self.selectedIndex];
        [self.childrenList insertObject: list atIndex: self.selectedIndex];
        
        if (success) {
            success();
        }
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        
        [self.childrenList removeObjectAtIndex: self.selectedIndex];
        [self.childrenList insertObject: [NSMutableArray array] atIndex: self.selectedIndex];
        
        if (failure) {
            failure();
        }
    }];
    
}


#pragma mark - Lazy Loading

- (NSArray *)noodleList {
    if (!_noodleList) {
        _noodleList = [NSArray array];
    }
    return _noodleList;
}

- (NSMutableArray *)childrenList {
    if (!_childrenList) {
        _childrenList = [NSMutableArray array];
    }
    return _childrenList;
}



@end
