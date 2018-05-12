//
//  SCHospitalHomePageItemsCell.h
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/1.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "BaseTableViewCell.h"

typedef NS_ENUM(NSInteger, HospitalHomePageItemType) {
    HospitalHomePageItemTypeHospitalInfo,   // 医院信息
    HospitalHomePageItemTypeRegister,       // 预约挂号
    HospitalHomePageItemTypeDoctor,         // 科室医生
    HospitalHomePageItemTypeDiagnose,       // 智能导诊
};

typedef void(^ItemViewTapHandle)(HospitalHomePageItemType type);

@interface SCHospitalHomePageItemsCell : BaseTableViewCell

@property (nonatomic, copy) ItemViewTapHandle tapHandler;

@end
