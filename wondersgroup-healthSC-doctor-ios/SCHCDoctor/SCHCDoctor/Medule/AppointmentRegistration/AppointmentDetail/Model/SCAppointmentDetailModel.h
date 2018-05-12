//
//  AppointmentDetailModel.h
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface Schedule : BaseModel

@property (nonatomic, strong) NSString *scheduleId;//排班id
@property (nonatomic, strong) NSString *scheduleDate;//排班日期
@property (nonatomic, strong) NSNumber *numSource;//剩余号源数
@property (nonatomic, strong) NSString *visitCost;//诊费
@property (nonatomic, strong) NSString *visitLevel;//出诊级别
@property (nonatomic, strong) NSNumber *timeRange;//1-上午，2-下午，3-晚上
@property (nonatomic, strong) NSString *timeRangeStr;//1-上午，2-下午，3-晚上
@property (nonatomic, strong) NSString *weekDay;//周
@property (nonatomic, strong) NSString *startTime;//
@property (nonatomic, strong) NSString *endTime;//
@property (nonatomic, strong) NSNumber *isFull;//

@end


@interface SCAppointmentDetailModel : BaseModel

@property (nonatomic, strong) NSString *appointmentId;//
@property (nonatomic, strong) NSString *hosOrgCode;//医院代码
@property (nonatomic, strong) NSString *hosDeptCode;//科室代码
@property (nonatomic, strong) NSString *hosDoctCode;//医生代码
@property (nonatomic, strong) NSString *headphoto;//医生头像
@property (nonatomic, strong) NSString *doctorName;//医生姓名
@property (nonatomic, strong) NSNumber *gender;//医生性别
@property (nonatomic, strong) NSString *deptName;//科室名称
@property (nonatomic, strong) NSString *doctorTitle;//职称
@property (nonatomic, strong) NSString *hosName;//医院名字
@property (nonatomic, strong) NSString *expertin;//特长
@property (nonatomic, strong) NSString *department;//部门
@property (nonatomic, strong) NSString *visitCost;//诊费
@property (nonatomic, strong) NSString *patient;//就诊人
@property (nonatomic, strong) NSNumber *patientNumber;//接种数
@property (nonatomic, strong) NSNumber *orderCount;//预约量接诊量
@property (nonatomic, strong) NSNumber *isFull;// 0-约满,1-可预约
@property (nonatomic, strong) Schedule *schedule;//


@end
