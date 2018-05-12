//
//  SCMyPreOrderFinalAllViewController.h
//  SCHCPatient
//
//  Created by wuhao on 2016/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"


/**
 segment控制的VC
 */

@protocol preOrderCountDelegate <NSObject>

/**
  预约总量

 @param count 总量
 */
-(void)allPreorderCount:(int)count;

@end


@interface SCMyPreOrderFinalAllViewController : BaseViewController

///0--->所有预约 1---->待就诊 2----->已就诊  3---->已取消
@property(nonatomic,assign)int preOrderState;

@property(nonatomic,weak)id <preOrderCountDelegate>delegate;


@end
