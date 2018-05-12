//
//  OrderDetailViewController.h
//  HCPatient
//
//  Created by wuhao on 2016/10/31.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "BaseViewController.h"
#import "SCMyPreorderListsModel.h"
#import "SCMyPreorderDetailModel.h"



/**
  四川健康云的预约详情界面(demo.方便后期修改)
 */
@interface SCMyPreorderDetailViewController: BaseViewController

@property(nonatomic,strong)SCMyPreorderListsModel *model;
//@property(nonatomic,strong)SCMyPreorderDetailModel *detailModel;


@property(nonatomic,copy)NSString *OrderIdString;//订单列表中的"scOrderId"字段

@property(nonatomic,assign)int state;//1,2-->待就诊   3--->已取消 4->已就诊

@end



