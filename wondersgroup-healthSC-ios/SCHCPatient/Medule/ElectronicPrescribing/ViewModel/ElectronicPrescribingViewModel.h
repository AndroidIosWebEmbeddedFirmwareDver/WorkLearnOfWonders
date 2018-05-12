//
//  ElectronicPrescribingViewModel.h
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "ElectronicPrescribingModel.h"
@interface ElectronicPrescribingViewModel : BaseViewModel
@property (strong, nonatomic) ElectronicPrescribingListModel *dataModel;
@property (strong, nonatomic) NSArray *dataArray;
@property (copy  , nonatomic) NSString  *medicalOrgId;//医疗机构代码
@property (strong, nonatomic) NSNumber  *day;//查询时间区间

- (void)getElectronicPrescribingDataFromeServer:(void(^)(void))success failed:(void(^)(NSError *error))failed;
//- (void)getElectronicPrescribingDataFromeServer:(void(^)(void))success failed:(void(^)(NSString *message))failed;
@end
