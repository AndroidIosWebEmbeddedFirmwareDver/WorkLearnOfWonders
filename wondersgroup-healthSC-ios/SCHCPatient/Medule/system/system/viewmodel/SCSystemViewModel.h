//
//  SystemViewModel.h
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"





@interface SCSystemViewModel : BaseViewModel
@property(nonatomic ,strong) NSArray * dataArray;

-(void)getSystemMessageRequest;

-(void)getSystemMessageRequest:(NSDictionary *)dic  success:(IMPLCompleteBlock) success falied:(IMPLFailuredBlock) failed;


-(void)updateMessageIdStatus:(NSDictionary *)dic success:(IMPLCompleteBlock) success falied:(IMPLFailuredBlock) failed;
@end
