//
//  MyOrderModel.h
//  HCPatient
//
//  Copyright © 2016年 非丶白. All rights reserved.
//

#import "BaseModel.h"

@interface payorderModel : BaseModel
@property (nonatomic, strong) NSString * oid;//
@property (nonatomic, strong) NSString * amount;//
@property (nonatomic, strong) NSString * subject;//微健康预约挂号订单-名称
@property (nonatomic, strong) NSString * body;//微健康预约挂号订单-详情
@property (nonatomic, strong) NSString * descrip;//微健康预约挂号订单-描述
@property (nonatomic, strong) NSString * status;//微健康预约挂号订单-名称
@property (nonatomic, strong) NSString * time_left; //已过时间
@property (nonatomic, strong) NSString * submerno; //子商户号
@property (nonatomic, strong) NSString * appid; //

@end

@interface businessModel : BaseModel
@property (nonatomic, strong) NSString * prescriptionNum;//处方号
@property (nonatomic, strong) NSString * hospitalName;// 医院名称
@property (nonatomic, strong) NSString * deptName;//科室名称
@property (nonatomic, strong) NSString * hospitalCode;
@property (nonatomic, strong) NSString * time;//开方时间
@property (nonatomic, strong) NSString * receiptNo;//这个收据编号只有支付成功之后才有
@property (nonatomic, strong) NSString * outDoctorLevel;//出诊级别
@property (nonatomic, strong) NSString * doctorName;//医生名字
@property (nonatomic, strong) NSString * patientName;//患者名字
@property (nonatomic, strong) NSString * appointPeriod;//预约时间段
@property (nonatomic, strong) NSString * jzlsh;
@property (nonatomic, assign) int  state;//预约状态(1,2--->待就诊,3--->已就诊,4---->已就诊)

@end

@interface SCMyOrderModel : BaseModel

@property (nonatomic, strong) NSString      *orderType;//订单类型（ 诊间支付 住院金预交 挂号费支）
@property (nonatomic, strong) NSString      *orderId;//订单id
@property (nonatomic, strong) NSString      *price;//订单价格
@property (nonatomic, strong) businessModel *business;
@property (nonatomic, strong) payorderModel *pay_order;
@property (nonatomic, strong) NSString      *payStatus;//订单状态（待支付 1已支付 3已退款 4已超时）
@property (nonatomic,assign ) int           isEvaluated;//是否已评价 0-->未评价 1--->已评价
@property (nonatomic,copy   ) NSString      *showOrderId;//显示出来的orderID;


@end
