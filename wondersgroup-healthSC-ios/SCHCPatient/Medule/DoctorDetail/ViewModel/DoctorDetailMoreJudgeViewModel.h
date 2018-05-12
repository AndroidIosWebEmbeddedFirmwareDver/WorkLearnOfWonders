//
//  DoctorDetailMoreJudgeViewModel.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/22.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"


@interface DoctorDetailMoreJudgeViewModel : BaseViewModel <DoctorIMPL>

@property (nonatomic, copy) NSString *request_doctor_id;

@property (nonatomic, strong) NSMutableArray *dataArray;

@end
