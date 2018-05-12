//
//  ChooseReportTimerCell.h
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ReportModel.h"

typedef void(^BtnBlock)(UIButton *sender);
@interface ChooseReportTimerCell : UITableViewCell

@property (strong, nonatomic) ChooseTimeModel *cellModel;
@property (strong, nonatomic) UILabel *titleLB;
@property (strong, nonatomic) UIButton *chooseBtn;
@property (strong, nonatomic) BtnBlock BtnBlockAction;
@property (strong, nonatomic) UIView  *lineView;
@end
