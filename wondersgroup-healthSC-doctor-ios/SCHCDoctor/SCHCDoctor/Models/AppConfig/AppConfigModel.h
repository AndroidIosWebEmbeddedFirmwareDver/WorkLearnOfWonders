//
//  AppConfigModel.h
//  VaccinePatient
//
//  Created by maorenchao on 16/6/16.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"
#import "ConfigAdModel.h"
#import "UpdateInfoModel.h"
#import "CommonModel.h"
@interface AppConfigModel : BaseModel

//"userAgreement":"http://10.1.92.195:3003/vaccine-h5-de/agreement",// 用户协议
//"reservationProcess":"http://10.1.92.195:3003/vaccine-h5-de/booking/process",// 预约流程
//"reservationRules":"http://10.1.92.195:3003/vaccine-h5-de/booking/rules",// 预

@property (nonatomic, strong) ConfigAdModel   *advertisement;
@property (nonatomic, strong) UpdateInfoModel *appUpdate;
@property (nonatomic ,strong) CommonModel     * common;
@property (nonatomic, strong) NSString        *userAgreement;// 用户协议
@property (nonatomic, strong) NSNumber        *scrollRotationTime;////首页滚动栏轮巡时间间隔(秒)

@end
