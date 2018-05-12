//
//  SCHospitalEvaluationListViewModel.h
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/2.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "BaseViewModel.h"

typedef void(^SuccessHandle)(NSArray *evaluationList);
typedef void(^FailureHandle)(NSError *error);

@interface SCHospitalEvaluationListViewModel : BaseViewModel

@property (nonatomic, strong) NSMutableArray *evaluationList;

- (void)requstEvaluationListWithHospitalID:(NSString *)hospitalID
                             isMoreRequest:(BOOL)isMoreRequest
                              successBlock:(SuccessHandle)successHandler
                              failureBlock:(FailureHandle)failureHandler;


/// 更改版本: 1.0
/// 修改人员: Joseph Gao
/// 修改描述: UI变更,暂时别删
- (void)publishEvaluation:(NSString *)evaluation
           withHospitalID:(NSString *)hospitalID
            successHandle:(void(^)())successHandler
            failureHandle:(void(^)(NSError *error))failureHandler;

@end
