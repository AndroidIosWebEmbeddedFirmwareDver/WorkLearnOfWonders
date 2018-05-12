//
//  OnLinePayCell.h
//  SZCirculationImage
//
//  Created by Wonders_iOS on 2016/11/1.
//  Copyright © 2016年 吴三忠. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "cuzZButton.h"
#import "FunctionModel.h"
@interface OnLinePayCell : UITableViewCell
typedef void(^onLinePayBlock)();
typedef void(^drawReportBlock)();
typedef void(^electronPrescriptionBlock)();

@property (nonatomic,strong)NSArray <FunctionModel *> * dataArray;
@property(nonatomic,copy)onLinePayBlock onLinePayBlock;
@property(nonatomic,copy)drawReportBlock drawReportBlock;
@property(nonatomic,copy)electronPrescriptionBlock electronPrescriptionBlock;

@property (nonatomic,strong) cuzZButton * leftButton;
@property (nonatomic,strong) cuzZButton * midButton;
@property (nonatomic,strong) cuzZButton * rightButton;

-(void)setupView;
@end
