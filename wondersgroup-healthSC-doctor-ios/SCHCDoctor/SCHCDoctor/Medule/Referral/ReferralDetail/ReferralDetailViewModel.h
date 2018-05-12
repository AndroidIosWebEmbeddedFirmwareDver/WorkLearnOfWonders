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

@property (assign, nonatomic) BOOL isJoinReferr;        //是接入转诊

@property (strong, nonatomic) NSIndexPath * currentDetailShowCount;                 //当前详情展示编号
@property (strong, nonatomic) NSArray * baseDataTitles;                             //基础信息标题
@property (strong, nonatomic) ReferralDetailModel * model;                          //数据

@end
