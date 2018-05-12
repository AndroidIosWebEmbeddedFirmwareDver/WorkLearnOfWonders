//
//  DoctorDetailViewController.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"


@interface DoctorDetailViewController : BaseViewController

/*
 | hospitalCode | 是    | 医院代码 | String |      |
 | hosDeptCode | 是    | 科室代码 | String |      |
 | hosDoctCode | 是    | 医生代码 | String |      |
 */

@property (nonatomic, copy) NSString *hospitalCode;
@property (nonatomic, copy) NSString *hosDeptCode;
@property (nonatomic, copy) NSString *hosDoctCode;

@property (nonatomic, assign) BOOL showRegister;

@end
