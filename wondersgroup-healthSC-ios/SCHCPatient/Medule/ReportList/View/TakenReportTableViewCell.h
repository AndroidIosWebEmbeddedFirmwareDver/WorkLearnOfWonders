//
//  TakenReportTableViewCell.h
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SCHospitalModel.h"
@interface TakenReportTableViewCell : UITableViewCell

@property (strong, nonatomic) SCHospitalModel *cellModel;
@property (strong, nonatomic) UILabel *nameLB;
//@property (strong, nonatomic) UIView  *lineView;

- (void)setCellModel:(SCHospitalModel *)cellModel withKeyWord:(NSString *)keyWord;

@end
