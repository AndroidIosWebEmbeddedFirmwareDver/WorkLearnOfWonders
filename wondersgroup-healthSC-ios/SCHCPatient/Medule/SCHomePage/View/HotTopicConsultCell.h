//
//  HotTopicConsultCell.h
//  SZCirculationImage
//
//  Created by Wonders_iOS on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ArticlessModel.h"
@interface HotTopicConsultCell : UITableViewCell
@property (nonatomic,strong)UIImageView * titleImageView;
@property (nonatomic,strong)UILabel * titleLabel;
@property (nonatomic,strong)UILabel * detailLabel;

@property (nonatomic,strong)ArticlessModel  * model;

@property (nonatomic, strong) NSString *highLightString;
@end
