//
//  FamilyDoctorTeamModel.h
//  SCHCPatient
//
//  Created by ZJW on 2017/6/5.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface FamilyDoctorTeamModel : BaseModel

@property (nonatomic, strong) NSString *teamId;
@property (nonatomic, strong) NSString *teamName;
@property (nonatomic, strong) NSString *orgName;
@property (nonatomic, strong) NSString *orgCode;
@property (nonatomic, strong) NSString *leader;
@property (nonatomic, strong) NSString *signedCount;
@property (nonatomic, strong) NSString *thumb;

@end
