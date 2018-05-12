//
//  ReferralStateModel.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/19.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseModel.h"

@interface ReferralStateModel : BaseModel

@property (assign, nonatomic) BOOL hasRequesting;       //是否有申请中的数据
@property (assign, nonatomic) BOOL hasRejected;         //是否有已驳回的数据
@property (assign, nonatomic) BOOL hasReferred;         //是否有已转诊的数据
@property (assign, nonatomic) BOOL hasCanceled;         //是否有已取消的数据

@end
