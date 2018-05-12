//
//  PatientManagerViewModel.h
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"
#import "PatientManagerModel.h"

@interface PatientManagerViewModel : BaseViewModel

//@property (nonatomic, strong) NSArray<PatientManagerModel *> *dataArray;
@property (nonatomic, strong) NSNumber *hasNewlyAdded;

- (void)getNewPatientsPrompt:(void(^)(void))success failed:(void(^)(NSError *error))failed;


@end
