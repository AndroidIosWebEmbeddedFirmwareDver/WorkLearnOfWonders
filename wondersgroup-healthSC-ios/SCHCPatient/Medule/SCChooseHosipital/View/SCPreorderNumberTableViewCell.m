//
//  ChooseHospitalTableViewCell.m
//  SCHCPatient
//
//  Created by wuhao on 2016/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCPreorderNumberTableViewCell.h"


@implementation SCPreorderNumberTableViewCell
{
    UIImageView *hospitalImage;//医院整体外观图像
    UILabel *hospitalNameLabel;//医院名称标签
    UILabel *hospitalRankingLabel;//医院定级标签
    UILabel *preOrderNumberLabel;//预约量

}



-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    if (self==[super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupUI];
        [self bindRac];
    }
    return self;

}

-(void)setupUI{

   
        //医院整体外观图像
    hospitalImage=[UISetupView setupImageViewWithSuperView:self.contentView withImageName:@""];
    
    
        //医院名字标签
    hospitalNameLabel=[UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor redColor] withFontSize:12];
    
    
        //预约量标签
    preOrderNumberLabel=[UISetupView setupLabelWithSuperView:self.contentView withText:@"预约量" withTextColor:[UIColor redColor] withFontSize:12];
    
    
    
        //医院定级标签
    hospitalRankingLabel=[UISetupView setupLabelWithSuperView:self.contentView withText:@"二级甲等" withTextColor:[UIColor redColor] withFontSize:12];
    hospitalRankingLabel.textColor=[UIColor blackColor];
    hospitalRankingLabel.layer.borderColor=[UIColor cyanColor].CGColor;
    hospitalRankingLabel.layer.borderWidth=4.f;
    
    

}


-(void)bindRac{


}










@end
