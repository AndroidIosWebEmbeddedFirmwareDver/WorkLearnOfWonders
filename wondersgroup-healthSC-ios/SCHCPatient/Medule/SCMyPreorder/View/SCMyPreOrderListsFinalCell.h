//
//  SCMyPreOrderListsFinalCell.h
//  SCHCPatient
//
//  Created by wuhao on 2016/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseTableViewCell.h"
#import "SCMyPreorderListsModel.h"
#import "SCMyOrderModel.h"

/**
 我的预约列表cell
 */


typedef NS_ENUM(NSInteger,SCCommitButtonType){
    SCCommitButtonTypeCancel=0,//取消预约
    SCCommitButtonTypeEvaluate,//评价医生
    SCCommitButtonTypeDone,//已评价

};


@protocol cancelPreorderDelegate <NSObject>

-(void)cancelPreOrderSuccess:(NSDictionary *)dic;

@end

@protocol evaluateDoctorDelegate <NSObject>

-(void)evaluateDoctorSuccess:(NSDictionary *)dic;

@end


@interface SCMyPreOrderListsFinalCell : BaseTableViewCell


@property(nonatomic,strong)SCMyOrderModel *cellModel;

@property(nonatomic,weak)id<cancelPreorderDelegate>cancelDelegate;

@property(nonatomic,weak)id<evaluateDoctorDelegate>evaluateDelegate;


@end
