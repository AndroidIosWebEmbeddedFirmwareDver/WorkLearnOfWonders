//
//  CheckViewModel.h
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"

@interface ReportViewModel : BaseViewModel

@property (strong, nonatomic) NSArray   *dataArray;
@property (copy  , nonatomic) NSString  *medicalOrgId;//医疗机构代码
@property (strong, nonatomic) NSNumber  *day;//查询时间区间
@property (assign, nonatomic) int  type;//电子处方，提取报告

- (void)getCheckDataFromeServer:(void(^)(void))success failed:(void(^)(NSError *error))failed;//检查报告接口
//- (void)getInspectionDataFromeServer:(void(^)(void))success failed:(void(^)(void))failed;//检验报告
- (void)getInspectionDataFromeServer:(void(^)(void))success failed:(void(^)(NSError *error))failed;//检验报告
- (void)getChooseTimerData:(void(^)(void))success failed:(void(^)(void))failed;
@end
