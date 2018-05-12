//
//  SCHospitalOverviewTitleCell.m
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCHospitalOverviewTitleCell.h"

@interface SCHospitalOverviewTitleCell()

@property (nonatomic, strong) UILabel *titleLabel;

@end

@implementation SCHospitalOverviewTitleCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        self.lineTopHidden = YES;
        self.lineBottomHidden = YES;
        
        UIView *grayV = [[UIView alloc] init];
        grayV.backgroundColor = [UIColor bc2Color];
        [self.contentView addSubview:grayV];
        
        [grayV mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.top.right.equalTo(self.contentView);
            make.height.mas_equalTo(10);
        }];
        
        
        
        _titleLabel = [[UILabel alloc] init];
        _titleLabel.text = @"医院介绍";
        _titleLabel.font = [UIFont systemFontOfSize:15];
        _titleLabel.textColor = [UIColor tc1Color];
        [self.contentView addSubview:_titleLabel];
        
        
        [_titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.offset(15);
            make.top.equalTo(grayV.mas_bottom).offset(15);
            make.bottom.offset(-15);
        }];
        
    }
    return self;
}

- (CGSize)sizeThatFits:(CGSize)size {
    return CGSizeMake(size.width, 50);
}

@end
