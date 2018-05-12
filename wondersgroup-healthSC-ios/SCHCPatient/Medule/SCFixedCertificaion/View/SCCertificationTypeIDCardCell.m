//
//  SCCertificationTypeIDCardCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCCertificationTypeIDCardCell.h"

@implementation SCCertificationTypeIDCardCell

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
    label.text = @"身份证";
    label.backgroundColor = [UIColor clearColor];
    label.font = [UIFont systemFontOfSize:14];
    [self.contentView addSubview:label];
    [label mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(125);
        make.height.mas_equalTo(25);
    }];
    
    _idcardLabel = [[UILabel alloc] init];
    _idcardLabel.textColor = [UIColor tc2Color];
   // _idcardLabel.text = @"150426188111283322";
    _idcardLabel.textAlignment = NSTextAlignmentRight;
    _idcardLabel.backgroundColor = [UIColor clearColor];
    _idcardLabel.font = [UIFont systemFontOfSize:14];
    [self.contentView addSubview:_idcardLabel];
    [_idcardLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(225);
        make.height.mas_equalTo(25);
    }];
    
    UILabel *lineLabel = [[UILabel alloc] init];
    lineLabel.backgroundColor = [UIColor bc3Color];
    [self.contentView addSubview:lineLabel];
    [lineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView);
        make.right.equalTo(weakSelf.contentView);
        make.bottom.equalTo(weakSelf.contentView.mas_bottom);
        make.height.mas_equalTo(0.5);
    }];
}

- (void)bindRac
{
    WS(weakSelf)
    [RACObserve(self, model.idcard) subscribeNext:^(id x) {
        weakSelf.idcardLabel.text = x;
    }];
}


@end
