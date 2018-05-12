//
//  SCCertificationFixedIDcardCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCCertificationFixedIDcardCell.h"

@implementation SCCertificationFixedIDcardCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupSubview];
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
    
    _idcardTf = [[UITextField alloc] init];
    _idcardTf.textAlignment = NSTextAlignmentRight;
    _idcardTf.font = [UIFont systemFontOfSize:14];
    _idcardTf.borderStyle = UITextBorderStyleNone;
    _idcardTf.placeholder = @"请输入真实身份证号";
    [self.contentView addSubview:_idcardTf];
    [_idcardTf mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.centerY.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(@25);
        make.width.equalTo(@190);
    }];
    [_idcardTf.rac_textSignal subscribeNext:^(id x){
         if(weakSelf.idcardBlock) {
            weakSelf.idcardBlock(x);
        }
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


@end
