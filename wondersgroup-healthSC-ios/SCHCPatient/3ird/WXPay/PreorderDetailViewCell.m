//
//  PreorderDetailViewCell.m
//  SCHCPatient
//
//  Created by Jam on 2016/11/24.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "PreorderDetailViewCell.h"

@implementation PreorderDetailViewCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    if (self==[super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupUI];
        [self bindRac];
    }
    return self;
    
}

-(void)setupUI{
    WS(weakSelf)
    UILabel *titleLabel = [UISetupView setupLabelWithSuperView: self.contentView
                                                      withText:@""
                                                 withTextColor: [UIColor tc2Color]
                                                  withFontSize: 16.0];
    [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15.0);
        make.centerY.equalTo(weakSelf.contentView);
    }];
    self.titleLabel = titleLabel;
    
    UILabel *contentLabel = [UISetupView setupLabelWithSuperView: self.contentView
                                                        withText: @""
                                                   withTextColor: [UIColor tc1Color] withFontSize:16.0];
    [contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.greaterThanOrEqualTo(titleLabel.mas_right).offset(5);
        make.right.equalTo(weakSelf.contentView).offset(-16);
        make.centerY.equalTo(weakSelf.contentView);
    }];

    self.contentLabel = contentLabel;
}

-(void)bindRac{
    
    
}

@end
