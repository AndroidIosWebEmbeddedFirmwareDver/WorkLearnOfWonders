//
//  AppointmentResultViewModel.h
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/9.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"

@interface AppointmentResultViewModel : BaseViewModel

@property (nonatomic, assign) BOOL isSuccess;
@property (nonatomic, strong) NSString *errorString;

@end
