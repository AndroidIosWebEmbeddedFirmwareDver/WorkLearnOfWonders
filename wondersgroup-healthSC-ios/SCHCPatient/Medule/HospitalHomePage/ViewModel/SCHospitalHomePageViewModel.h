//
//  SCHospitalHomePageViewModel.h
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/1.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCHospitalHomePageModel.h"

@interface SCHospitalHomePageViewModel : BaseViewModel

@property (nonatomic, strong) SCHospitalHomePageModel *hospitalModel;
@property (nonatomic, assign, getter=isCollected) BOOL collected;

- (void)requsetHospitalHomePageData:(NSString *)hospitalID
                  withSuccessHandle:(void(^)(SCHospitalHomePageModel *hospitalModel))successHandler
                      failureHandle:(void(^)(NSError *error))failureHandler;

- (void)collectHospitalWithHospitalID:(NSString *)hospitalID
                        successHandle:(void(^)())successHandler
                        failureHandle:(void(^)(NSError *error))failureHandler;

- (void)publishEvaluation:(NSString *)evaluation
           withHospitalID:(NSString *)hospitalID
            successHandle:(void(^)())successHandler
            failureHandle:(void(^)(NSError *error))failureHandler;

@end
