//
//  SCCertificationTypeFailCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCCertificationTypeFailCell.h"

@implementation SCCertificationTypeFailCell

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
    UILabel *topLine = [[UILabel alloc] init];
    topLine.backgroundColor = [UIColor bc3Color];
    [self.contentView addSubview:topLine];
    [topLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView);
        make.right.equalTo(weakSelf.contentView);
        make.top.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(0.5);
    }];

    UILabel *titleReason = [[UILabel alloc] init];
    titleReason.textColor = [UIColor tc1Color];
    titleReason.text = @"审核失败原因:";
    titleReason.backgroundColor = [UIColor clearColor];
    titleReason.font = [UIFont systemFontOfSize:14];
    [self.contentView addSubview:titleReason];
    [titleReason mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.top.equalTo(weakSelf.contentView).offset(12);
        make.width.mas_equalTo(175);
        make.height.mas_equalTo(20);
    }];
    
    _reasonLabel = [[UILabel alloc] init];
    _reasonLabel.textColor = [UIColor tc2Color];
    _reasonLabel.numberOfLines = 2;
    //_reasonLabel.text = @"您上传的照片无法识别身份信息，请重新上传";
    _reasonLabel.textAlignment = NSTextAlignmentLeft;
    _reasonLabel.backgroundColor = [UIColor clearColor];
    _reasonLabel.font = [UIFont systemFontOfSize:14];
    [self.contentView addSubview:_reasonLabel];
    [_reasonLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.top.equalTo(titleReason.mas_bottom).offset(8);
        make.left.equalTo(weakSelf.contentView).offset(15);
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
    [RACObserve(self, model.msg) subscribeNext:^(id x) {
        weakSelf.reasonLabel.text = x;
    }];
}


@end
