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
@property (nonatomic, strong) NSString *teamAddress;
@property (nonatomic, strong) NSString *teamLogo;

@end
