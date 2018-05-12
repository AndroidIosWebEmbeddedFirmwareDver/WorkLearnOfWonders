//
//  PatientsListViewController.h
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewController.h"
#import "PatientListModel.h"



typedef void(^SelectBlock)(PatientListModel *model);

@interface PatientsListViewController : BaseViewController

//@property (nonatomic, strong) NSArray<PatientListModel *> *patientsArray;
@property (nonatomic, copy) SelectBlock selectBlock;

- (instancetype)initWithPatientTag:(NSUInteger)tag;

@end
