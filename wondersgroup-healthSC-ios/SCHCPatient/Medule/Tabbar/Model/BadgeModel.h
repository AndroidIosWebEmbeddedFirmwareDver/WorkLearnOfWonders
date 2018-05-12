//
//  BadgeModel.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface BadgeModel : BaseModel


//badge下标，暂时用于Tabbar  在 RootTabBarViewModel 中赋值
@property (nonatomic, assign) int badgeIndex;

//未读消息数量
@property (nonatomic, assign) int badgeNum;

@end
