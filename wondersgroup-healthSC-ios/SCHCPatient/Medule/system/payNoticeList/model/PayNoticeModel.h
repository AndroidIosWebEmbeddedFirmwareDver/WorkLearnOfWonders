//
//  PayNoticeModel.h
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface PayNoticeModel : BaseModel
@property(nonatomic ,strong) NSString * ID;
@property(nonatomic,assign) NSNumber * type;//项目类型
@property(nonatomic,strong) NSString * orderId;

@property(nonatomic,strong) NSString * hospitalName;
@property(nonatomic,strong) NSString * patientName;
@property(nonatomic,strong) NSString * department;

@property(nonatomic,strong) NSString * doctorName;
@property(nonatomic,strong) NSString  * price;
@property(nonatomic,assign) NSNumber * payStatus;
@property(nonatomic,assign) NSNumber * payType;
@property(nonatomic,strong) NSString * payTypeName;
@property(nonatomic,strong) NSString * clinicType;



@property(nonatomic,strong) NSString * prescriptionCode;
@property(nonatomic,strong) NSString * prescriptionTime;
@property(nonatomic,strong) NSString * orderTime;





@property(nonatomic ,strong) NSString * createDate;
@property(nonatomic ,strong) NSString * registerId;//订单号
@property(nonatomic ,strong) NSString * messageId;
@property(nonatomic ,strong) NSString * linkUp;
@property(nonatomic ,assign) NSNumber * state;
@end
