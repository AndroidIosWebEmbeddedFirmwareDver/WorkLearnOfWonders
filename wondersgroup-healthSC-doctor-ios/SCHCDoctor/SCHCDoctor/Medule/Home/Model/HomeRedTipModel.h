//
//  HomeRedTipModel.h
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/15.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseModel.h"

@interface HomeRedTipModel : BaseModel

@property (nonatomic, strong) NSNumber *hasNewReferral;//是否有新转诊,
@property (nonatomic, strong) NSNumber *hasNewPatient;//是否有新增患者

@end
