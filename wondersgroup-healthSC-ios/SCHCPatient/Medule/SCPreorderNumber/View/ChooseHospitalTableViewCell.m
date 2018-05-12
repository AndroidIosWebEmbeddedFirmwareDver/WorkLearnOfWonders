//
//  ChooseHospitalTableViewCell.m
//  SCHCPatient
//
//  Created by wuhao on 2016/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ChooseHospitalTableViewCell.h"

@implementation ChooseHospitalTableViewCell
{
    UIImageView *hospitalImage;//医院整体外观图像
    UILabel *hospitalNameLabel;//医院名称标签
    UILabel *hospitalRankingLabel;//医院定级标签

}



-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    if (self==[super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupUI];
    }
    return self;

}

-(void)setupUI{



}


@end
