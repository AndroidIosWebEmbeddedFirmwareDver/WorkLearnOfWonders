//
//  FamilyDoctorTeamCell.m
//  SCHCPatient
//
//  Created by ZJW on 2017/6/5.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "FamilyDoctorTeamCell.h"

@interface FamilyDoctorTeamCell ()

@property (nonatomic, strong) UIImageView *photoView;
@property (nonatomic, strong) UILabel *nameLbl;
@property (nonatomic, strong) UILabel *addressLbl;
@property (nonatomic, strong) UIView *line;

@end

@implementation FamilyDoctorTeamCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        [self setUpViews];
        [self bindModel];
    }
    return self;
}


- (void)setUpViews
{
    _photoView = [UISetupView setupImageViewWithSuperView:self.contentView withImageName:@"默认团队"];
    [_photoView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView).offset(15);
        make.top.equalTo(self.contentView).offset(15);
        make.width.height.mas_equalTo(49);
    }];
    
    _nameLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc1Color] withFontSize:16];
    [_nameLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.contentView).offset(19);
        make.left.equalTo(self.photoView.mas_right).offset(15);
        make.right.equalTo(self.contentView).offset(15);
        make.height.mas_equalTo(18);
    }];
    
    _addressLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc3Color] withFontSize:14];
    [_addressLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.nameLbl.mas_bottom).offset(10);
        make.left.equalTo(self.nameLbl);
        make.width.equalTo(self.nameLbl);
    }];
    
    self.line = [[UIView alloc]init];
    self.line.backgroundColor = [UIColor dc1Color];
    [self.contentView addSubview:self.line];
    [self.line mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self.contentView);
        make.left.equalTo(self.photoView);
        make.right.equalTo(self.contentView);
        make.height.mas_equalTo(0.5);
    }];
}

- (void)bindModel{
    WS(weakSelf);
    [RACObserve(self, model) subscribeNext:^(FamilyDoctorTeamModel *x) {
        if (x) {
            weakSelf.nameLbl.text = x.teamName;
            weakSelf.addressLbl.text = x.teamAddress;
        }
    }];
    [RACObserve(self, isLast) subscribeNext:^(NSNumber *x) {
        self.line.hidden = [x boolValue];
    }];
}


+(CGFloat)cellHeight {
    return 78;
}

@end
