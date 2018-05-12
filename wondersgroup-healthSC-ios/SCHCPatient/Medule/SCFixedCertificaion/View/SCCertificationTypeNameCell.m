//
//  SCCertificationTypeNameCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCCertificationTypeNameCell.h"

@implementation SCCertificationTypeNameCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupSubview];
        [self bindRac];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    return self;
}

- (void)setupSubview
{
     WS(weakSelf)
    UILabel *label = [[UILabel alloc] init];
    label.textColor = [UIColor tc2Color];
    label.text = @"姓名";
    label.backgroundColor = [UIColor clearColor];
    label.font = [UIFont systemFontOfSize:14];
    [weakSelf.contentView addSubview:label];
    [label mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(125);
        make.height.mas_equalTo(25);
    }];
    
    _nameLabel = [[UILabel alloc] init];
    _nameLabel.textColor = [UIColor tc2Color];
    //_nameLabel.text = @"张全蛋";
    _nameLabel.textAlignment = NSTextAlignmentRight;
    _nameLabel.backgroundColor = [UIColor clearColor];
    _nameLabel.font = [UIFont systemFontOfSize:14];
    [weakSelf.contentView addSubview:_nameLabel];
    [_nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(125);
        make.height.mas_equalTo(25);
    }];
    
    UILabel *topLine = [[UILabel alloc] init];
    topLine.backgroundColor = [UIColor bc3Color];
    [weakSelf.contentView addSubview:topLine];
    [topLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView);
        make.right.equalTo(weakSelf.contentView);
        make.top.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(0.5);
    }];
    
    UILabel *lineLabel = [[UILabel alloc] init];
    lineLabel.backgroundColor = [UIColor bc3Color];
    [self.contentView addSubview:lineLabel];
    [lineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView);
        make.bottom.equalTo(weakSelf.contentView.mas_bottom);
        make.height.mas_equalTo(0.5);
    }];
}

- (void)bindRac
{
    WS(weakSelf)
    [RACObserve(self, model.name) subscribeNext:^(id x) {
        weakSelf.nameLabel.text = x;
    }];
}


@end
