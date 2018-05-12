//
//  SignUpFamilyDoctorViewModel.h
//  SCHCPatient
//
//  Created by ZJW on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "BaseViewModel.h"

@interface SignUpFamilyDoctorViewModel : BaseViewModel<SignUpFamilyDoctorIMPL>

@property (nonatomic, strong) NSNumber *doctorNumber;
@property (nonatomic, strong) NSNumber *familyNumber;

@end
