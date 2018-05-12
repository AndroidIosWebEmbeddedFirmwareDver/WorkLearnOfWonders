//
//  WDProfileTableViewCell.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDProfileTableViewCell.h"

@interface WDProfileTableViewCell()

@property (nonatomic, strong) UIImageView *headImageView;
@property (nonatomic, strong) UILabel *titleLabel;
@property (nonatomic, strong) UIView *lineView;

@end

@implementation WDProfileTableViewCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        [self setupUI];
        [self bindData];
    }
    return self;
}

- (void)setupUI {
    WS(ws)
    
    self.backgroundColor = [UIColor whiteColor];
    
    _headImageView = [UIImageView new];
    [self.contentView addSubview:_headImageView];
    [_headImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(ws.contentView);
        make.left.equalTo(ws).offset(15);
        make.width.height.mas_equalTo(24);
    }];
    
    _titleLabel = [UILabel new];
    _titleLabel.textColor = [UIColor tc1Color];
    _titleLabel.font = [UIFont systemFontOfSize:14];
    [self.contentView addSubview:_titleLabel];
    [_titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(_headImageView);
        make.left.equalTo(_headImageView.mas_right).offset(15);
    }];
    
    _lineView = [UIView new];
    _lineView.backgroundColor = [UIColor bc3Color];
    [self.contentView addSubview:_lineView];
    [_lineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.right.equalTo(ws.contentView);
        make.left.equalTo(ws).offset(15);
        make.height.mas_equalTo(0.5);
    }];
}

- (void)bindData {
    [RACObserve(self, imageName) subscribeNext:^(NSString *x) {
        _headImageView.image = [UIImage imageNamed:x];
    }];
    
    [RACObserve(self, title) subscribeNext:^(NSString *x) {
        _titleLabel.text = x;
    }];
    
    [RACObserve(self, isLast) subscribeNext:^(id x) {
        if (_isLast) {
            _lineView.hidden = YES;
        } else {
            _lineView.hidden = NO;
        }
    }];
}

@end
