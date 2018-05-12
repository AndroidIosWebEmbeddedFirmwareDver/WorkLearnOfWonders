//
//  FamilyDoctorTeamViewModel.h
//  SCHCPatient
//
//  Created by ZJW on 2017/6/5.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "FamilyDoctorTeamModel.h"

@interface FamilyDoctorTeamViewModel : BaseViewModel<SignUpFamilyDoctorIMPL>

@property (nonatomic, strong) NSArray *teamArray;

@end
