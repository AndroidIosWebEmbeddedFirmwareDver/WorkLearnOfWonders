//
//  ChooseReportTimeVC.h
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"

typedef void(^ChooseTimerBlock)(NSString *text,NSInteger type);

@interface ChooseReportTimeVC : BaseViewController

@property (strong, nonatomic) ChooseTimerBlock chooseTimerBlockAction;
@property (strong, nonatomic) NSNumber *defalutChoose;
@property (assign, nonatomic) int reportCategoryType;
@end
