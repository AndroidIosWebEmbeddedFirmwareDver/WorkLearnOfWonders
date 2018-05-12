//
//  SearchHistoryWithHospitalModel.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/21.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"
/*
 搜索医院历史存储数据库模型
 */
typedef enum ViewContrllerType {
    SearchTypeContrller_NearBy,//附近就医
    SearchTypeContrller_Appointment,//预约挂号
    SearchTypeController_payOnLine  //在线支付
} ViewContrllerType;
@interface SearchHistoryWithHospitalModel : BaseModel

@property (nonatomic, copy) NSString *cat_id;
// 标题名称
@property (nonatomic, copy) NSString *cat_name;

@property (nonatomic,strong)NSDate * date;

@property (nonatomic, strong) NSString * userID;

@property (nonatomic,assign)ViewContrllerType type;

@end
