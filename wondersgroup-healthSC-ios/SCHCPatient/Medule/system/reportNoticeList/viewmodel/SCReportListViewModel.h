//
//  SystemDetailViewModel.h
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"

@interface SCReportListViewModel : BaseViewModel

@property(nonatomic ,strong) NSArray * dataArray;
-(void) getReportRequest;
@end
