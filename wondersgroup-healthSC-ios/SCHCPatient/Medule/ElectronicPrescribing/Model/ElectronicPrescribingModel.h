//
//  ElectronicPrescribingModel.h
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface ElectronicPrescribingModel : BaseModel
@property (copy, nonatomic) NSString *cfhm;//处方号码
@property (copy, nonatomic) NSString *cflx;//处方类型
@property (copy, nonatomic) NSString *kfksdm;//开方科室代码
@property (copy, nonatomic) NSString *kfksmc;//开方科室名称
@property (copy, nonatomic) NSString *kfysbh;//开方医生编号
@property (copy, nonatomic) NSString *kfysxm;//开方医生姓名
@property (copy, nonatomic) NSString *kfsj;//开方时间
@property (copy, nonatomic) NSString *cfje;//处方金额 单位:分
@property (copy, nonatomic) NSString *zfzt;//支付状态
@property (copy, nonatomic) NSString *yljgmc;//医院名称
@property (copy, nonatomic) NSString *url;//h5 条转链接
@end


@interface ElectronicPrescribingListModel : BaseModel
@property (copy  , nonatomic) NSString *yljgdm;//医疗机构代码
@property (strong, nonatomic) NSArray  *prescription;
@end
