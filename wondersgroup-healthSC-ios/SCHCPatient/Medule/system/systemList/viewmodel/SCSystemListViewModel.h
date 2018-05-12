//
//  ReportNoticeViewModel.h
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"

@interface SCSystemListViewModel : BaseViewModel
@property(nonatomic ,strong) NSMutableArray * DataArray;

-(void)getSystemMessageRequest;
-(void)getSystemMessage:(NSDictionary*)dic success:(IMPLCompleteBlock)success failure:(IMPLFailuredBlock)failer;
@end
