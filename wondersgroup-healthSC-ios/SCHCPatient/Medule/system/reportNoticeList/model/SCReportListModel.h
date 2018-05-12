//
//  SystemDeitalModel.h
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface SCReportListModel : BaseModel
@property(nonatomic ,strong) NSString * title;
@property(nonatomic ,strong) NSString * paitentName;
@property(nonatomic ,strong) NSString * PaitentId;
@property(nonatomic ,strong) NSNumber * sex;
@property(nonatomic ,strong) NSNumber * age;
@property(nonatomic ,strong) NSString * hospitalName;
@property(nonatomic ,strong) NSString * hospitalId;
@property(nonatomic ,strong) NSString * checkProject;
@property(nonatomic ,strong) NSString * checkPart;
@property(nonatomic ,strong) NSString * department;
@property(nonatomic ,strong) NSString * checkDate;
@property(nonatomic ,strong) NSString * reportDate;
@property(nonatomic ,strong) NSString * date;
@property(nonatomic ,strong) NSString * linkUp;
@end
