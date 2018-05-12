//
//  SystemModel.h
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface SCSystemModel : BaseModel
@property(nonatomic,assign) NSNumber * messageCount;
@property(nonatomic ,assign) NSNumber * type;
@property(nonatomic ,strong) NSString * content;
@property(nonatomic ,strong) NSString * title;
@property(nonatomic ,strong) NSString * time;
@end
