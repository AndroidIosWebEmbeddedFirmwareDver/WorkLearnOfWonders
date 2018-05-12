//
//  DoctorDetailModel.h
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"
#import "SCAppointmentDetailModel.h"

/*
 "schedule": [
 {
 "scheduleId": "904||385",//排班id
 "scheduleDate": "2016-11-14",//排班日期
 "weekDay": "周一",
 "startTime": "08:00:00",// 开始时间
 "endTime": "11:59:00",// 结束时间
 "visitLevel":""// 出诊级别
 "visitCost": "5",// 诊费
 "timeRange": "1",//1-上午，2-下午，3-晚上
 "isFull": 1//0-约满,1-可预约
 },
 {
 "scheduleId": "904||390",
 "scheduleDate": "2016-11-16",
 "weekDay": "周三",
 "startTime": "08:00:00",
 "endTime": "11:59:00",
 "visitCost": "5",
 "timeRange": "1",
 "isFull": 1
 }
 ],
 "doctorInfo": { 医生信息
 "hosOrgCode": "450755531",
 "hosName": "成都市第一人民医院",
 "hosDeptCode": "29",
 "deptName": "普通外科",
 "hosDoctCode": "188",
 "doctorName": "杨厄",
 "headphoto":"",//头像
 "doctorTitle": "",
 "orderCount": 0,
 },
 "systemTime": "2016-11-10"//当前系统时间
 "week": "周四"// 当前weekday
 */
@interface DoctorInfo : BaseModel

@property (nonatomic, strong) NSString *hosOrgCode;
@property (nonatomic, strong) NSString *hosName;//
@property (nonatomic, strong) NSString *hosDeptCode;//
@property (nonatomic, strong) NSString *deptName;
@property (nonatomic, strong) NSString *hosDoctCode;
@property (nonatomic, strong) NSString *doctorName;
@property (nonatomic, strong) NSString *headphoto;
@property (nonatomic, strong) NSString *doctorTitle;
@property (nonatomic, strong) NSNumber *orderCount;
@property (nonatomic, strong) NSNumber *gender;//医生性别


@end


@interface SCDoctorSchedulingModel : BaseModel

@property (nonatomic, strong) DoctorInfo *doctorInfo;
@property (nonatomic, strong) NSArray *schedule;
@property (nonatomic, strong) NSString *systemTime;
@property (nonatomic, strong) NSString *week;


@end
