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
@property (nonatomic, strong) UILabel *teamNameLbl;
@property (nonatomic, strong) UILabel *signCountLbl;
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
        make.width.height.mas_equalTo(48);
    }];
    
    _signCountLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor stc1Color] withFontSize:14];
    _signCountLbl.textAlignment = NSTextAlignmentRight;
    [_signCountLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self.contentView).offset(-15);
        make.top.equalTo(self.contentView).offset(15);
        make.width.mas_equalTo(50);
    }];
    
    _nameLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc1Color] withFontSize:16];
    [_nameLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.contentView).offset(15);
        make.left.equalTo(self.photoView.mas_right).offset(15);
        make.right.equalTo(self.signCountLbl.mas_left).offset(15);
        make.height.mas_equalTo(18);
    }];
    
    _addressLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc2Color] withFontSize:14];
    [_addressLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.nameLbl.mas_bottom).offset(10);
        make.left.equalTo(self.nameLbl);
        make.width.equalTo(self.nameLbl);
    }];
    
    _teamNameLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc3Color] withFontSize:14];
    [_teamNameLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.addressLbl.mas_bottom).offset(10);
        make.left.equalTo(self.nameLbl);
    }];
    
    self.line = [[UIView alloc]init];
    self.line.backgroundColor = [UIColor dc1Color];
    [self.contentView addSubview:self.line];
    [self.line mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self.contentView);
        make.left.equalTo(self.nameLbl);
        make.right.equalTo(self.contentView);
        make.height.mas_equalTo(0.5);
    }];
}

- (void)bindModel{
    WS(weakSelf);
    [RACObserve(self, model) subscribeNext:^(FamilyDoctorTeamModel *x) {
        if (x) {
            weakSelf.nameLbl.text = x.teamName;
            weakSelf.addressLbl.text = x.orgName;
            if ([x.signedCount intValue] > 10000) {
                weakSelf.signCountLbl.text = @"10000+";
            }else {
                weakSelf.signCountLbl.text = [NSString stringWithFormat:@"%@",x.signedCount];
            }
            if (x.leader.length > 5) {
                weakSelf.teamNameLbl.text = [NSString stringWithFormat:@"组长:%@...",[x.leader substringToIndex:5]];
            }else {
                weakSelf.teamNameLbl.text = [NSString stringWithFormat:@"组长:%@",x.leader];
            }
        }
    }];
    
    [RACObserve(self, isLast) subscribeNext:^(NSNumber *x) {
        self.line.hidden = [x boolValue];
    }];

}


+(CGFloat)cellHeight {
    return 95;
}

@end
