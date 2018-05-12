//
//  RJHeaderConst.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#ifndef RJHeaderConst_h
#define RJHeaderConst_h

typedef NS_ENUM(NSUInteger, ReferralType) {
    ReferralTypeOutpatient = 0,         //门诊
    ReferralTypeHospitalized            //住院
};

typedef NS_ENUM(NSUInteger, ReferralPriority) {
    ReferralPriorityHigh = 0,           //紧急
    ReferralPriorityMedieam,            //中等
    ReferralPriorityNone                //无
};

typedef NS_ENUM(NSUInteger, ReferralProgress) {
    ReferralProgressRequest = 0,        //申请中
    ReferralProgressFail,               //已驳回
    ReferralProgressSuccess,            //已转诊
    ReferralProgressCancel,             //已取消
    ReferralProgressNone                //无
};

#endif /* RJHeaderConst_h */
