//
//  ReferralInfoViewModel.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ReferralInfoViewModel.h"
#import "PatientInfoModel.h"
#import "ReferralInfoModel.h"
@implementation ReferralInfoViewModel
- (instancetype)initWithType:(ReferralType)type model:(PatientInfoModel *)model{
    self = [super init];
    if (self) {
        _type = type;
        _model = model;
        [self initDataWithType:type];
    }
    return self;
}

- (void)initDataWithType:(ReferralType)type {
    NSMutableArray * temp = [NSMutableArray array];
    ReferralInfoModel * model1 = [[ReferralInfoModel alloc] init];
    model1.title = @"转入医院名称";
    model1.placeHolder = @"请选择医院";
    model1.isImportant = YES;
    model1.topBlank = YES;
    model1.type = PatientInfoBaseCellTypeNormal;
    [temp addObject:model1];
    
    if (type == ReferralTypeOutpatient) {
        ReferralInfoModel * model2 = [[ReferralInfoModel alloc] init];
        model2.title = @"发起医院名称";
        model2.placeHolder = @"";
        model2.isImportant = YES;
        model2.topBlank = YES;
        model2.type = PatientInfoBaseCellTypeNormal;
        [temp addObject:model2];
        
        for (NSString * title in @[@"发起科室名称",@"发起医生姓名",@"发起医生手机",@"转诊办手机"]) {
            ReferralInfoModel * model = [[ReferralInfoModel alloc] init];
            model.title = title;
            model.placeHolder = @"选填";
            model.isImportant = NO;
            model.topBlank = NO;
            model.type = PatientInfoBaseCellTypeTextFiled;
            [temp addObject:model];
        }
    } else {
        ReferralInfoModel * model2 = [[ReferralInfoModel alloc] init];
        model2.title = @"科室名称";
        model2.placeHolder = @"";
        model2.isImportant = NO;
        model2.topBlank = NO;
        model2.type = PatientInfoBaseCellTypeNormal;
        [temp addObject:model2];
        
        ReferralInfoModel * model3 = [[ReferralInfoModel alloc] init];
        model3.title = @"申请日期";
        model3.placeHolder = @"";
        model3.isImportant = YES;
        model3.topBlank = NO;
        model3.type = PatientInfoBaseCellTypeNormal;
        [temp addObject:model3];
    }
    
    _modelArray = [NSArray arrayWithArray:temp];
}

- (PatientInfoModel *)configModel {
    //转入医院名称与编码单独设置,发起医院编码，名称为获取医生信息
    if (_type == ReferralTypeOutpatient) {
        _model.fromDeptName = ((ReferralInfoModel *)_modelArray[2]).detail;
        _model.fromDocName = ((ReferralInfoModel *)_modelArray[3]).detail;
        _model.fromDocPhone = ((ReferralInfoModel *)_modelArray[4]).detail;
    } else {
        _model.dateOfApplication = ((ReferralInfoModel *)_modelArray[2]).detail;
    }
    return _model;
}

- (NSString *)checkModelImportantFullWithErrorString {
    [self configModel];
    NSString * errorString = @"";
    if (_type == ReferralTypeOutpatient) {
        //此处并未检测转入医院编号，应当在选择时检测，若缺失，则不可选
        if (!(_model.toOrgName) || [_model.toOrgName isEqualToString:@""]) {
            errorString = @"请选择转入医院";
        } else if (!(_model.fromOrgName) || [_model.fromOrgName isEqualToString:@""] ) {
            errorString = @"转出医院信息异常，请检查";
        }
    } else {
        if (!(_model.toOrgName) || [_model.toOrgName isEqualToString:@""]) {
            errorString = @"请选择转入医院";
        } else if (!(_model.fromOrgName) || [_model.fromOrgName isEqualToString:@""] ) {
            errorString = @"请选择申请日期";
        }
    }
    
    return errorString;
}

- (void)requestReferralWithSuccess:(void(^)(id content))successBlock
                              fail:(void(^)(NSError * error, NSString * errorString))failBloc {
    NSDictionary * params = [self getParams];
    
    NSString * url = ReferralRequestOutpatient;
    if (_type == ReferralTypeHospitalized) {
        url = ReferralRequestInPatient;
    }
    [self.adapter request:url params:params class:nil responseType:Response_Object method:Request_POST needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
    }];
    successBlock(nil);
}

- (NSDictionary *)getParams {
    NSDictionary * temp = nil;
    if (_type == ReferralTypeOutpatient) {
        temp = @{   @"name":_model.name,                                //患者姓名
                    @"gender":_model.gender,                            //性别
                    @"age":_model.age,                                  //年龄
                    @"VisitCardType":_model.VisitCardType,              //就诊卡类型
                    @"VisitCardNum":_model.VisitCardNum,                //就诊卡号
                    @"idCard":_model.idCard,                            //身份证号
                    @"mobile":_model.mobile,                            //手机号
                    @"address":_model.address,                          //家庭地址
                    @"urgency":_model.urgency,                          //紧急程度（1:一般，2:紧急）
                    @"tentativeDiagnosis":_model.tentativeDiagnosis,    //初步诊断
                    @"medicalHistory":_model.medicalHistory,            //病史摘要
                    @"pastHistory":_model.pastHistory,                  //主要既往史
                    @"treatment":_model.treatment,                      //治疗情况
                    @"toOrgCode":_model.toOrgCode,                      //转入医院编码
                    @"toOrgName":_model.toOrgName,                      //转入医院名称
                    @"toDeptName":_model.toDeptName,                    //转入科室名称
                    @"fromOrgCode":_model.fromOrgCode,                  //发起医院编码
                    @"fromOrgName":_model.fromOrgName,                  //发起医院名称
                    @"fromDeptName":_model.fromDeptName,                //发起科室名称
                    @"fromDocName":_model.fromDocName,                  //发起医生姓名
                    @"fromDocPhone":_model.fromDocPhone,                //发起医生电话
                    @"rferralOfficePhone":_model.rferralOfficePhone,    //转诊办电话
                    @"dateOfApplication":_model.dateOfApplication       //申请日期
                    };
    } else {
        temp = @{
                    @"name":_model.name,                                //患者姓名
                    @"gender":_model.gender,                            //性别
                    @"age":_model.age,                                  //年龄
                    @"idCard":_model.idCard,                            //身份证号
                    @"birthday":_model.birthday,                        //出生日期
                    @"mobile":_model.mobile,                            //手机号
                    @"address":_model.address,                          //家庭地址
                    @"urgency":_model.urgency,                          //紧急程度（1:一般，2:紧急）
                    @"tentativeDiagnosis":_model.tentativeDiagnosis,    //初步诊断
                    @"medicalHistory":_model.medicalHistory,            //病史摘要
                    @"pastHistory":_model.pastHistory,                  //主要既往史
                    @"treatment":_model.treatment,                      //治疗情况
                    @"toOrgCode":_model.toOrgCode,                      //转入医院编码
                    @"toOrgName":_model.toOrgName,                      //转入医院名称
                    @"toDeptName":_model.toDeptName,                    //转入科室名称
                    @"fromOrgCode":_model.fromOrgCode,                  //发起医院编码
                    @"fromOrgName":_model.fromOrgName,                  //发起医院名称
                    @"fromDeptName":_model.fromDeptName,                //发起科室名称
                    @"fromDocName":_model.fromDocName,                  //发起医生姓名
                    @"dateOfApplication":_model.dateOfApplication       //申请日期
                };
    }
    return temp;
}
@end
