//
//  ReportModel.h
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface ReportModel : BaseModel

@property (copy, nonatomic) NSString *date;//检查时间c
@property (copy, nonatomic) NSString *reprotID;//id
@property (copy, nonatomic) NSString *department_name;//科别
@property (copy, nonatomic) NSString *item_name;//检查类别
@property (copy, nonatomic) NSString *hospital_name;
@property (copy, nonatomic) NSString *view_url;

@end


@interface ChooseTimeModel : BaseModel

@property (copy, nonatomic) NSString *title;

@end

@interface TakenReportHostptial : BaseModel

/// 医院id
@property (nonatomic, copy) NSString *hospitalId;
/// 医院代码
@property (nonatomic, copy) NSString *hospitalCode;
/// 医院名称
@property (nonatomic, copy) NSString *hospitalName;

@end
