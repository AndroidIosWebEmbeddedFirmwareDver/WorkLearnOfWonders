//
//  ReferralDetailViewModel.h
//  SCHCPatient
//
//  Created by Po on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "ReferralDetailModel.h"
@interface ReferralDetailViewModel : BaseViewModel

@property (strong, nonatomic) NSIndexPath * currentDetailShowCount;     //当前详情展示编号
@property (strong, nonatomic) NSArray * baseDataArray;                             //基础信息
@property (strong, nonatomic) NSArray<ReferralDetailModel *> * referralDataArray;  //转诊信息

@end
