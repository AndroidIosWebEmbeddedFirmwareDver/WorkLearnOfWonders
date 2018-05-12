//
//  PatientInfoViewModel.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientInfoViewModel.h"
@implementation PatientInfoCellTypeModel
@end

@implementation PatientInfoViewModel
- (instancetype)initWithType:(ReferralType)type model:(PatientInfoModel *)model{
    self = [super init];
    if (self) {
        _type = type;
        _urgencyArray = @[@"一般",@"紧急"];
        _data = model;
        NSMutableArray * temp = [NSMutableArray array];
        PatientInfoCellTypeModel * sex = [self getCellCommonModelWithTitle:@"性别"
                                                               placeHolder:@"请选择性别"
                                                                      type:PatientInfoCellNormal
                                                               isImportant:NO];
        PatientInfoCellTypeModel * age = [self getCellCommonModelWithTitle:@"年龄"
                                                               placeHolder:@""
                                                                      type:PatientInfoCellNormal
                                                               isImportant:NO];
        PatientInfoCellTypeModel * cardType = [self getCellCommonModelWithTitle:@"就诊卡类型"
                                                               placeHolder:@"请选择就诊卡类型"
                                                                      type:PatientInfoCellNormal
                                                               isImportant:YES];
        PatientInfoCellTypeModel * cardNum = [self getCellCommonModelWithTitle:@"就诊卡卡号"
                                                               placeHolder:@"请输入就诊卡卡号"
                                                                      type:PatientInfoCellTextField
                                                               isImportant:YES];
        PatientInfoCellTypeModel * phone = [self getCellCommonModelWithTitle:@"联系电话"
                                                                 placeHolder:@"请输入联系电话"
                                                                        type:PatientInfoCellTextField
                                                                 isImportant:YES];
        PatientInfoCellTypeModel * address = [self getCellCommonModelWithTitle:@"家庭住址"
                                                                   placeHolder:@"选填"
                                                                          type:PatientInfoCellTextView
                                                                   isImportant:NO];
        PatientInfoCellTypeModel * urgency = [self getCellCommonModelWithTitle:@"紧急程度"
                                                               placeHolder:@""
                                                                      type:PatientInfoCellSelected
                                                               isImportant:NO];
        PatientInfoCellTypeModel * firstCheck = [self getCellCommonModelWithTitle:@"初次诊断"
                                                                      placeHolder:@"请输入文字"
                                                                             type:PatientInfoCellTextPanel
                                                                      isImportant:YES];
        PatientInfoCellTypeModel * history = [self getCellCommonModelWithTitle:@"病史摘要"
                                                                      placeHolder:@"请输入文字"
                                                                             type:PatientInfoCellTextPanel
                                                                      isImportant:YES];
        PatientInfoCellTypeModel * mainHistory = [self getCellCommonModelWithTitle:@"主要既往史"
                                                                      placeHolder:@"请输入文字"
                                                                             type:PatientInfoCellTextPanel
                                                                      isImportant:YES];
        PatientInfoCellTypeModel * cure = [self getCellCommonModelWithTitle:@"治疗情况"
                                                                      placeHolder:@"请输入文字"
                                                                             type:PatientInfoCellTextPanel
                                                                      isImportant:YES];
        if (type == ReferralTypeOutpatient) {
            _modelsArray = @[sex, age, cardType, cardNum, phone, address, urgency, firstCheck, history, mainHistory, cure];
        } else {
            _modelsArray = @[sex, age, phone, address, urgency, firstCheck, history, mainHistory, cure];
        }
    }
    return self;
}

- (NSString *)checkModelImportantFullWithErrorString {
    [self getPostData];
    NSString * errorMsg = @"";
    if (_type == ReferralTypeOutpatient) {
        if (!_data.VisitCardType || [_data.VisitCardType isEqualToString:@""]) {
            errorMsg = @"就诊卡类型不能为空";
        }else if (!_data.VisitCardNum || [_data.VisitCardNum isEqualToString:@""]) {
            errorMsg = @"就诊卡卡号不能为空";
        }else if (!_data.mobile || [_data.mobile isEqualToString:@""]) {
            errorMsg = @"联系电话不能为空";
        }else if(![RegexKit validateMobile:_data.mobile]) {
            errorMsg = @"联系电话格式不正确";
        }
    } else {
        if(![RegexKit validateMobile:_data.mobile]) {
            errorMsg = @"联系电话格式不正确";
        }
    }
    return errorMsg;
}

- (PatientInfoCellTypeModel *)getCellCommonModelWithTitle:(NSString *)title
                                              placeHolder:(NSString *)placeHolder
                                                     type:(PatientInfoCellType)type
                                              isImportant:(BOOL)isImportant {
    PatientInfoCellTypeModel * model = [[PatientInfoCellTypeModel alloc] init];
    model.title = title;
    model.placeHolder = placeHolder;
    model.detail = @"";
    model.type = type;
    model.isImportant = isImportant;
    return model;
}

- (PatientInfoModel *)getPostData {
    if (_type == ReferralTypeOutpatient) {
        
        _data.gender                = [self getDataWithModelCount:0];
        _data.age                   = [self getDataWithModelCount:1];
        _data.VisitCardType         = [self getDataWithModelCount:2];
        _data.VisitCardNum          = [self getDataWithModelCount:3];
        _data.mobile                = [self getDataWithModelCount:4];
        _data.address               = [self getDataWithModelCount:5];
        _data.urgency               = [self getDataWithModelCount:6];
        _data.tentativeDiagnosis    = [self getDataWithModelCount:7];
        _data.medicalHistory        = [self getDataWithModelCount:8];
        _data.pastHistory           = [self getDataWithModelCount:9];
        _data.treatment             = [self getDataWithModelCount:10];
        
    } else {
        _data.gender                = [self getDataWithModelCount:0];
        _data.age                   = [self getDataWithModelCount:1];
        _data.mobile                = [self getDataWithModelCount:2];
        _data.address               = [self getDataWithModelCount:3];
        _data.urgency               = [self getDataWithModelCount:4];
        _data.tentativeDiagnosis    = [self getDataWithModelCount:5];
        _data.medicalHistory        = [self getDataWithModelCount:6];
        _data.pastHistory           = [self getDataWithModelCount:7];
        _data.treatment             = [self getDataWithModelCount:8];
    }
    return _data;
}

- (NSString *)getDataWithModelCount:(NSInteger)count {
    PatientInfoCellTypeModel * model = _modelsArray[count];
    return model.detail;
}
@end


