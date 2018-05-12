//
//  DoctorDetailJudgeModel.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface DoctorDetailJudgeModel : BaseModel

@property (nonatomic, copy) NSString *mid;
@property (nonatomic, copy) NSString *uid;
@property (nonatomic, copy) NSString *nickName;
@property (nonatomic, copy) NSString *content;
@property (nonatomic, copy) NSString *createTime;

@property (nonatomic, assign) CGFloat cellHeight;

@end
