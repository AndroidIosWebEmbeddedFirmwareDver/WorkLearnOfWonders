//
//  SCSettingModel.h
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface SCSettingModel : BaseModel
@property (nonatomic, strong) NSString *content;
@property (nonatomic, assign) BOOL isHaveArrow;
@property (nonatomic, assign) BOOL isOn;

@end
