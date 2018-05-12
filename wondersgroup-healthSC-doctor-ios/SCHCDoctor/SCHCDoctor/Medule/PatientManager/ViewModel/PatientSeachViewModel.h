//
//  PatientSeachViewModel.h
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"
#import "PatientListModel.h"

@interface PatientSeachViewModel : BaseViewModel

@property (nonatomic, strong) NSArray<PatientListModel *> *patientsArray;

- (void)searchPatientsWithKeyword:(NSString *)keyword Success:(void(^)(void))success Failed:(void(^)(NSError *error))failed;


@end
