//
//  SCMyPreorderDetailViewModel.h
//  SCHCPatient
//
//  Created by wuhao on 2016/11/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCMyPreorderDetailModel.h"


//@protocol getDetailDelegate <NSObject>
//
//-(void)getData:(SCMyPreorderDetailModel *)model;
//
//@end



@interface SCMyPreorderDetailViewModel : BaseViewModel



@property (nonatomic, strong) NSArray *datas;//数据

@property(nonatomic,strong)SCMyPreorderDetailModel *detailModel;


//@property(nonatomic,weak)id<getDetailDelegate>delegate;


/**
 获取预约详情数据

 @param preOrderID   订单列表中的"scOrderId"字段
 @param successBlock
 @param failedBlock
 */
- (void)getPreOrderDetailDataFromeServer:(NSString *)preOrderID  success:(void(^)(void))successBlock failed:(void(^)(void))failedBlock;








@end
