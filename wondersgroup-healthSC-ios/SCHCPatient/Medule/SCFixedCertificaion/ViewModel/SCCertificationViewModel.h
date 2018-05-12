//
//  WDCertificationViewModel.h
//  JAHealthCloudPatient
//
//  Created by luzhongchang on 16/6/15.
//  Copyright © 2016年 WondersGroup. All rights reserved.
//

#import "BaseViewModel.h"
#import "WDVerificationInfoModel.h"
@interface SCCertificationViewModel : BaseViewModel

@property (nonatomic, strong) NSString *userName;
@property (nonatomic, strong) NSString *idCard;
@property (nonatomic, strong) UIImage  *photo;
@property (nonatomic, strong) NSString *photoUrl;
@property (nonatomic, strong) NSString *behindphotoUrl;
@property (nonatomic,strong ) NSString * uid;
@property (nonatomic, assign) BOOL     canUploard;

+ (instancetype)shareInstance;

- (void)verifyCertification:(void(^)(BOOL isSuccess))sucess;
- (void)getCertification:(void(^)(WDVerificationInfoModel *verificationModel))sucess;

- (void)doctorInvitationCodeSwitchSuccess: (void (^)(id response))success;

@end
