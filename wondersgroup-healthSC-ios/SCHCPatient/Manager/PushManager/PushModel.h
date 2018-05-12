//
//  PushModel.h
//  VaccinePatient
//
//  Created by maorenchao on 16/6/21.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface PushModel : BaseModel
@property(nonatomic, strong)NSString *msgId;
@property(nonatomic, strong)NSString *forType;
@property(nonatomic, strong)NSString *title;
@property(nonatomic, strong)NSString *content;
@property(nonatomic, strong)NSString *page;

@end
