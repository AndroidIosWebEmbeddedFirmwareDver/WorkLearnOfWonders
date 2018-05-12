//
//  PatientInfoModel.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/19.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseModel.h"

@interface PatientInfoModel : BaseModel

@property (strong, nonatomic) NSString * name;                  //患者姓名
@property (strong, nonatomic) NSString * gender;                //性别
@property (strong, nonatomic) NSString * age;                   //年龄
@property (strong, nonatomic) NSString * VisitCardType;         //就诊卡类型
@property (strong, nonatomic) NSString * VisitCardNum;          //就诊卡号
@property (strong, nonatomic) NSString * idCard;                //身份证号
@property (strong, nonatomic) NSString * mobile;                //手机号
@property (strong, nonatomic) NSString * address;               //家庭地址
@property (strong, nonatomic) NSString * urgency;               //紧急程度（1:一般，2:紧急）
@property (strong, nonatomic) NSString * tentativeDiagnosis;    //初步诊断
@property (strong, nonatomic) NSString * medicalHistory;        //病史摘要
@property (strong, nonatomic) NSString * pastHistory;           //主要既往史
@property (strong, nonatomic) NSString * treatment;             //治疗情况
@property (strong, nonatomic) NSString * toOrgCode;             //转入医院编码
@property (strong, nonatomic) NSString * toOrgName;             //转入医院名称
@property (strong, nonatomic) NSString * toDeptName;            //转入科室名称
@property (strong, nonatomic) NSString * fromOrgCode;           //发起医院编码
@property (strong, nonatomic) NSString * fromOrgName;           //发起医院名称
@property (strong, nonatomic) NSString * fromDeptName;          //发起科室名称
@property (strong, nonatomic) NSString * fromDocName;           //发起医生姓名
@property (strong, nonatomic) NSString * fromDocPhone;          //发起医生电话
@property (strong, nonatomic) NSString * rferralOfficePhone;    //转诊办电话
@property (strong, nonatomic) NSString * dateOfApplication;     //申请日期

@property (strong, nonatomic) NSString * birthday;              //生日
@end
