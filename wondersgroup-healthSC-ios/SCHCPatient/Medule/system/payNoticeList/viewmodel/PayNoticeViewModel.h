//
//  PayNoticeViewModel.h
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"

@interface PayNoticeViewModel : BaseViewModel

@property(nonatomic ,strong) NSMutableArray * dataArray;

-(void)getDataByrequest;
-(void)getPayList:(NSDictionary *)dic success:(IMPLCompleteBlock)success failed:(IMPLFailuredBlock) failed;
@end
