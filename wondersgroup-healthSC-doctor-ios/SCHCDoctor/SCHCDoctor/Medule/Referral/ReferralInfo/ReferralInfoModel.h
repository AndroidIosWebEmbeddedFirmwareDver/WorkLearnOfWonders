//
//  ReferralInfoModel.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseModel.h"
#import "PatientInfoBaseCell.h"
@interface ReferralInfoModel : BaseModel

@property (strong, nonatomic) NSString * title;             //标题
@property (strong, nonatomic) NSString * placeHolder;       //空闲文案
@property (strong, nonatomic) NSString * detail;
@property (assign, nonatomic) PatientInfoBaseCellType type; //类型

@property (assign, nonatomic) BOOL isImportant;             //是否重要
@property (assign, nonatomic) BOOL topBlank;                //上方是否留白
@property (strong, nonatomic) NSString * keyString;         //key值

@end
