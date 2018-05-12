//
//  TakenReportSearchHistoryView.h
//  SCHCPatient
//
//  Created by Gu Jiajun on 2017/5/8.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SCHospitalModel.h"

typedef void(^HistorySelectBlock)(SCHospitalModel *model);

@interface TakenReportSearchHistoryView : UIView

@property (nonatomic, strong) NSArray *historyArray;
@property (nonatomic, copy) HistorySelectBlock historySelectBlock;

@end
