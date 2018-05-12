//
//  SCSettingModel.h
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface SCSettingModel : BaseModel
typedef NS_ENUM(NSInteger ,SettingType){
    SettingAccount,//账号
};
@property (nonatomic,assign) SettingType type;
- (id)initWithtype:(SettingType)type;
@end
