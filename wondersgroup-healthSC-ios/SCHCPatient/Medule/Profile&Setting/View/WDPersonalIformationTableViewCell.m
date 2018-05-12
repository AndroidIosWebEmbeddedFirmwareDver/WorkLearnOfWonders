//
//  WDPersonalIformationTableViewCell.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDPersonalIformationTableViewCell.h"

@interface WDPersonalIformationTableViewCell () 

@property (nonatomic, strong) UILabel *titleLabel;
@property (nonatomic, strong) UIImageView *nextImageView;
@property (nonatomic, strong) UIImageView *userHeadImageView;
@property (nonatomic, strong) UILabel *contentLabel;
@property (nonatomic, strong) UILabel *phoneLabel;
@property (nonatomic, strong) UIImageView *styleImageView;
@property (nonatomic, strong) UIView *lineView;

@end

@implementation WDPersonalIformationTableViewCell

- (UILabel *)titleLabel {
    if (!_titleLabel) {
        _titleLabel = [UILabel new];
        _titleLabel.textColor = [UIColor tc2Color];
        _titleLabel.font = [UIFont systemFontOfSize:14];
    }
    return _titleLabel;
}

- (UIImageView *)nextImageView {
    if (!_nextImageView) {
        _nextImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"link_right"]];
    }
    return _nextImageView;
}

- (UIImageView *)userHeadImageView {
    if (!_userHeadImageView) {
        _userHeadImageView = [UIImageView new];
        _userHeadImageView.layer.masksToBounds = YES;
        _userHeadImageView.layer.cornerRadius = 16;
    }
    return _userHeadImageView;
}

- (UILabel *)contentLabel {
    if (!_contentLabel) {
        _contentLabel = [UILabel new];
        _contentLabel.textColor = [UIColor tc1Color];
        _contentLabel.font = [UIFont systemFontOfSize:14];
        _contentLabel.textAlignment = NSTextAlignmentRight;
    }
    return _contentLabel;
}

- (UILabel *)phoneLabel {
    if (!_phoneLabel) {
        _phoneLabel = [UILabel new];
        _phoneLabel.textColor = [UIColor tc1Color];
        _phoneLabel.textAlignment = NSTextAlignmentRight;
        _phoneLabel.font = [UIFont systemFontOfSize:14];
        _phoneLabel.textAlignment = NSTextAlignmentRight;
    }
    return _phoneLabel;
}

- (UIImageView *)styleImageView {
    if (!_styleImageView) {
        _styleImageView = [UIImageView new];
    }
    return _styleImageView;
}

- (UIView *)lineView {
    if (!_lineView) {
        _lineView = [UIView new];
        _lineView.backgroundColor = [UIColor bc3Color];
    }
    return _lineView;
}

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
    
    [self.contentView addSubview:self.titleLabel];
    [self.titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(ws.contentView);
        make.left.equalTo(ws.contentView).offset(15);
    }];
    
    [self.contentView addSubview:self.nextImageView];
    [self.nextImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(ws.contentView);
        make.right.equalTo(ws.contentView).offset(-15);
    }];
    
    [self.contentView addSubview:self.userHeadImageView];
    [self.userHeadImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(ws.contentView);
        make.right.equalTo(ws.nextImageView.mas_left).offset(-10);
        make.width.mas_equalTo(32);
        make.height.mas_equalTo(32);
    }];
    
    [self.contentView addSubview:self.contentLabel];
    [self.contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(ws.nextImageView.mas_left).offset(-10);
        make.centerY.equalTo(ws.contentView);
        make.width.mas_equalTo(110);
    }];
    
    [self.contentView addSubview:self.phoneLabel];
    
    [self.phoneLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(ws.contentView).offset(-15);
        make.centerY.equalTo(ws.contentView);
        make.width.mas_equalTo(110);
    }];
    
    [self.contentView addSubview:self.styleImageView];
    [self.styleImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(ws.contentView);
        make.right.equalTo(ws.nextImageView.mas_left).offset(-10);
    }];
    
    [self.contentView addSubview:self.lineView];
    [self.lineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(ws.contentView).offset(15);
        make.right.equalTo(ws.contentView);
        make.bottom.equalTo(ws.contentView);
        make.height.mas_equalTo(0.5);
    }];
}

- (void)bindData {
    [RACObserve(self, cellType) subscribeNext:^(id x) {
        if (_cellType == WDInformationCellDefault) {
            _nextImageView.hidden = NO;
            _userHeadImageView.hidden = YES;
            _contentLabel.hidden = NO;
            _phoneLabel.hidden = NO;
            _styleImageView.hidden = YES;
        }
        
        else if (_cellType == WDInformationCellHead) {
            _nextImageView.hidden = NO;
            _userHeadImageView.hidden = NO;
            _contentLabel.hidden = YES;
            _phoneLabel.hidden = YES;
            _styleImageView.hidden = YES;
        }
        
        else if (_cellType == WDInformationCellPhone) {
            _nextImageView.hidden = YES;
            _userHeadImageView.hidden = YES;
            _contentLabel.hidden = YES;
            _phoneLabel.hidden = NO;
            _styleImageView.hidden = YES;
        }
        
        else if (_cellType == WDInformationCellStyle) {
            _nextImageView.hidden = NO;
            _userHeadImageView.hidden = YES;
            _contentLabel.hidden = NO;
            _phoneLabel.hidden = YES;
            _styleImageView.hidden = NO;
        }
    }];
    
    [RACObserve(self, title) subscribeNext:^(id x) {
        _titleLabel.text = _title;
    }];
    
    [RACObserve(self, content) subscribeNext:^(id x) {
        if ([UserManager manager].verificationStatus == 0 || [UserManager manager].verificationStatus == 1) {
            _contentLabel.text = _content;
            _contentLabel.hidden = NO;
            _nextImageView.hidden = NO;
            _phoneLabel.hidden = YES;
        } else {
            _phoneLabel.text = _content;
            _contentLabel.hidden = YES;
            _nextImageView.hidden = YES;
            _phoneLabel.hidden = NO;
        }
    }];
    
    [RACObserve(self, head) subscribeNext:^(id x) {
        if ([UserManager manager].gender == 2) {
            [_userHeadImageView sd_setImageWithURL:[NSURL URLWithString:_head] placeholderImage:[UIImage imageNamed:@"默认用户女164"]];
        } else {
            [_userHeadImageView sd_setImageWithURL:[NSURL URLWithString:_head] placeholderImage:[UIImage imageNamed:@"默认用户男164"]];
        }
    }];
    
    [RACObserve(self, phone) subscribeNext:^(id x) {
        _phoneLabel.text = _phone;
    }];
    
    [RACObserve(self, style) subscribeNext:^(id x) {
        if (_style == 0) {
            _contentLabel.hidden = NO;
            _contentLabel.text = @"未认证";
            _styleImageView.hidden = YES;
        }
        
        if (_style == 1) {
            _contentLabel.hidden = YES;
            _styleImageView.image = [UIImage imageNamed:@"img认证失败"];
            _styleImageView.hidden = NO;
        }
        
        if (_style == 2) {
            _contentLabel.hidden = YES;
            _styleImageView.image = [UIImage imageNamed:@"img审核中"];
            _styleImageView.hidden = NO;
        }
        
        if (_style == 3) {
            _contentLabel.hidden = YES;
            _styleImageView.image = [UIImage imageNamed:@"img已认证"];
            _styleImageView.hidden = NO;
        }
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
