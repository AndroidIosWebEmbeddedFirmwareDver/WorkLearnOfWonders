//
//  ReferralInfoViewModel.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"

@class PatientInfoModel;
@class ReferralInfoModel;

@interface ReferralInfoViewModel : BaseViewModel
@property (assign, nonatomic) ReferralType type;                                         //住院， 门诊
@property (strong, nonatomic) NSArray<ReferralInfoModel *> * modelArray;                 //默认文案
@property (strong, nonatomic) PatientInfoModel * model;


- (instancetype)initWithType:(ReferralType)type model:(PatientInfoModel *)model;


/**
 装配数据
 */
- (PatientInfoModel *)configModel;

/**
 检测重要信息是否完整

 @return 错误信息
 */
- (NSString *)checkModelImportantFullWithErrorString;


@end
