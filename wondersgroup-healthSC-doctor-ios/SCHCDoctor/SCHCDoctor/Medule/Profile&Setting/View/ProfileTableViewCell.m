//
//  ProfileTableViewCell.m
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ProfileTableViewCell.h"

@interface ProfileTableViewCell ()

@property (nonatomic, strong) UIImageView *headImageView;
@property (nonatomic, strong) UILabel *titleLabel;
@property (nonatomic, strong) UIView *lineView;
@property (nonatomic, strong) UIImageView *arrowImageView;

@end

@implementation ProfileTableViewCell

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
        make.width.height.mas_equalTo(25);
    }];
    
    _titleLabel = [UILabel new];
    _titleLabel.textColor = [UIColor tc1Color];
    _titleLabel.font = [UIFont systemFontOfSize:14];
    [self.contentView addSubview:_titleLabel];
    [_titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(_headImageView);
        make.left.equalTo(_headImageView.mas_right).offset(15);
    }];
    
    _arrowImageView = [UISetupView setupImageViewWithSuperView:self.contentView withImageName:@"link_right"];
    [_arrowImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(self.contentView);
        make.right.equalTo(self.contentView).offset(-15);
        make.size.mas_equalTo(CGSizeMake(7, 14));
    }];
    
    _lineView = [UIView new];
    _lineView.backgroundColor = [UIColor dc1Color];
    [self.contentView addSubview:_lineView];
    [_lineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.right.equalTo(ws.contentView);
        make.left.equalTo(_titleLabel);
        make.height.mas_equalTo(0.5);
    }];
}

- (void)bindData {
    WS(weakSelf)
    [RACObserve(self, imageName) subscribeNext:^(NSString *x) {
        weakSelf.headImageView.image = [UIImage imageNamed:x];
    }];
    
    [RACObserve(self, title) subscribeNext:^(NSString *x) {
        weakSelf.titleLabel.text = x;
    }];
    
    [RACObserve(self, isLast) subscribeNext:^(NSNumber *x) {
        weakSelf.lineView.hidden = [x boolValue];
    }];
}


@end
