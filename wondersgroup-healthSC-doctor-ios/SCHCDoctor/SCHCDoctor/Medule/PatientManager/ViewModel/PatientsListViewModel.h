//
//  PatientsListViewModel.h
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/15.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"
#import "PatientListModel.h"

typedef enum : NSUInteger {
    ALL_PATIENT,
    POOR_PATIENT,
    KEY_PATIENT,
    New_PATIENT
} PatientTag;

@interface PatientsListViewModel : BaseViewModel

@property (nonatomic, strong) NSArray<PatientListModel *> *dataArray;
@property (nonatomic, assign) PatientTag tag;

- (void)getPatients:(void(^)(void))success failed:(void(^)(NSError *error))failed;

@end
