//
//  ProfileFunctionView.m
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ProfileFunctionView.h"

@interface ProfileFunctionView ()


@end

@implementation ProfileFunctionView

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier  {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        
        [self prepareUI];
        [self bindRac];
    }
    return self;
}

- (void)prepareUI {
    
    self.contentView.backgroundColor = [UIColor clearColor];
    
    UIButton *signNumberView = [self functionView];
    signNumberView.tag = 200;
    [signNumberView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.bottom.equalTo(self.contentView);
        make.width.mas_equalTo(SCREEN_WIDTH/2.0);
    }];
    
    UIButton *teamNumberView = [self functionView];
    teamNumberView.tag = 201;
    [teamNumberView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(self.contentView);
        make.left.equalTo(signNumberView.mas_right);
        make.width.mas_equalTo(SCREEN_WIDTH/2.0);
    }];

    UIView *line = [UISetupView setupLineViewWithSuperView:self.contentView];
    [line mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(signNumberView.mas_right);
        make.top.equalTo(self.contentView).offset(10);
        make.centerY.equalTo(self.contentView);
        make.width.mas_equalTo(0.5);
    }];
    
}

- (void)bindRac {
    
    WS(weakSelf)
    [RACObserve(self, model) subscribeNext:^(ProfileModel *x) {
        if (x) {
            UILabel *titleLabel = [[weakSelf.contentView viewWithTag:200] viewWithTag:300];
            UILabel *numberLabel = [[weakSelf.contentView viewWithTag:200] viewWithTag:301];
            titleLabel.text = @"签约数";
//            numberLabel.text = [NSString stringWithFormat:@"%@",x.signNumber];

            UILabel *titleLabel1 = [[weakSelf.contentView viewWithTag:201] viewWithTag:300];
            UILabel *numberLabel1 = [[weakSelf.contentView viewWithTag:201] viewWithTag:301];
            titleLabel1.text = @"我的团队";
//            numberLabel1.text = [NSString stringWithFormat:@"%@",x.teamNumber];
        }
    }];
}

-(UIButton *)functionView {
    UIButton *view = [UISetupView setupButtonWithSuperView:self.contentView withTitleToStateNormal:@"" withTitleColorToStateNormal:[UIColor clearColor] withTitleFontSize:0 withAction:^(UIButton *sender) {
        if (self.functionButtonBlock) {
            self.functionButtonBlock(sender.tag);
        }
    }];
    
    UILabel *titleLabel = [UISetupView setupLabelWithSuperView:view withText:@"" withTextColor:[UIColor tc2Color] withFontSize:13];
    titleLabel.tag = 300;
    [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(view);
        make.top.equalTo(view).offset(15);
    }];
    
    UILabel *numberLabel = [UISetupView setupLabelWithSuperView:view withText:@"" withTextColor:[UIColor tc1Color] withFontSize:28];
    numberLabel.tag = 301;
    [numberLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(view);
        make.top.equalTo(titleLabel.mas_bottom).offset(8);
    }];
    
    return view;
}

@end
