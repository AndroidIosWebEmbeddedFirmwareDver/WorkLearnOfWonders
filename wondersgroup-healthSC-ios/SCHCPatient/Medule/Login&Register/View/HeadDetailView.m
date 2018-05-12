//
//  HeadDetailView.m
//  VaccinePatient
//
//  Created by Jam on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HeadDetailView.h"

@interface HeadDetailView ()

@property (nonatomic,strong)UIButton * fastLoginButton;

@property (nonatomic,strong)UIButton * pwdLoginButton;

@property (nonatomic,strong)UIButton * cancelButton;

@property (nonatomic,strong)UIImageView * smallImageView;


@property (nonatomic,strong)UIImageView * backImageView;

@property (nonatomic,strong)UIButton * delButton;
@end


@implementation HeadDetailView
- (UIImageView *)smallImageView{
    if (!_smallImageView) {
        _smallImageView  = [UIImageView new];
        _smallImageView.image = [UIImage imageNamed:@"icon箭头上"];
    }
    return _smallImageView;
}
- (UIImageView *)backImageView{
    if (!_backImageView) {
        _backImageView  = [UIImageView new];
        _backImageView.image = [UIImage imageNamed:@"登录背景色"];
        _backImageView.userInteractionEnabled = YES;
    }
    return _backImageView;
}

- (UIButton *)delButton{

    if (!_delButton) {
        _delButton = [UIButton buttonWithType:UIButtonTypeCustom];

        [_delButton.titleLabel setFont: [UIFont systemFontOfSize:16.]];
        
        [_delButton setImage:[UIImage imageNamed:@"icon关闭24灰"] forState:UIControlStateNormal];
        [_delButton addTarget:self action:@selector(delFunction:) forControlEvents:UIControlEventTouchUpInside];
       
    }
    return _delButton;


}

- (void)delFunction:(id)sender{
    if(self.delBlock){
        self.delBlock();
    }

}
#pragma mark - 用户相关
- (instancetype)initWithLogo:(NSString *)image withSlogan:(NSString *)slogan {
    self = [super init];
    if (self) {
        self.tipString = slogan;
        self.imageName = image;
        [self setupLoginUIViews];
    }
    return self;
}
- (instancetype)initWithLogo:(NSString *)image withSlogan:(NSString *)slogan
           withfastLoginName:(NSString * )fastLoginString
            withPwdLoginName:(NSString *)pwdLoginString{
    self = [super init];
    if (self) {
        self.tipString = slogan;
        self.imageName = image;
        self.fastLoginName = fastLoginString;
        self.pwdLoginName = pwdLoginString;
        [self setupLoginUIViews];
    }
    return self;
   

}
- (void)setupLoginUIViews {
    [self addSubview:self.backImageView];
    [self.backImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self);
    }];
    [self addSubview:self.delButton];
    [self.delButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.mas_left);
        make.top.equalTo(self.mas_top).offset(15.);
        make.size.mas_equalTo(CGSizeMake(40, 40));
    }];
    UIImageView *imageView = [UISetupView setupImageViewWithSuperView: self withImageName: self.imageName];
    
    [imageView.layer setCornerRadius: 10.0];
    [imageView.layer setMasksToBounds: YES];
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.size.mas_equalTo(CGSizeMake(74.5, 60.5));
        make.top.equalTo(self).offset(60.0);
        make.centerX.equalTo(self);
    }];
    
    UIImageView *sloganImage = [UISetupView setupImageViewWithSuperView: self withImageName: self.tipString];
    [sloganImage mas_makeConstraints:^(MASConstraintMaker *make) {
        make.size.mas_equalTo(CGSizeMake(110.0, 24.0));
        make.top.equalTo(imageView.mas_bottom).offset(10.0);
        make.centerX.equalTo(self);
    }];
   
    if([self.fastLoginName length]){
        self.fastLoginButton = [UISetupView setupButtonWithSuperView:self withTitleToStateNormal:self.fastLoginName withTitleColorToStateNormal:[UIColor whiteColor] withTitleFontSize:18. withAction:^(UIButton *sender) {
            NSLog(@"快捷登录");
            if (self.changeBlock) {
                self.changeBlock(1);
                [self.smallImageView mas_updateConstraints:^(MASConstraintMaker *make) {
                      make.left.equalTo(self.mas_left).offset(SCREEN_WIDTH/4);
                }];
                [UIView animateWithDuration:0.3 animations:^{
                    [self layoutIfNeeded];
                }];
            }
            self.pwdLoginButton.alpha =0.5;
            self.fastLoginButton.alpha =1.0;
        }];
        [self.fastLoginButton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.mas_equalTo(self.mas_bottom).offset(-40.);
            make.left.equalTo(self.mas_left);
            make.width.mas_equalTo(SCREEN_WIDTH/2.);
        }];
        
        
        self.pwdLoginButton = [UISetupView setupButtonWithSuperView:self withTitleToStateNormal:self.pwdLoginName withTitleColorToStateNormal:[UIColor whiteColor] withTitleFontSize:18. withAction:^(UIButton *sender) {
            NSLog(@"密码登录");
            
            if (self.changeBlock) {
                self.changeBlock(0);
                [self.smallImageView mas_updateConstraints:^(MASConstraintMaker *make) {
                   
                  make.left.equalTo(self.mas_left).offset(SCREEN_WIDTH/4*3);
                }];
                [UIView animateWithDuration:0.3 animations:^{
                    [self layoutIfNeeded];
                }];
            }
            self.pwdLoginButton.alpha = 1.0;
            self.fastLoginButton.alpha = 0.5;
        }];
        
        [self.pwdLoginButton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.mas_equalTo(self.mas_bottom).offset(-40.);
            make.right.equalTo(self.mas_right);
            make.width.mas_equalTo(SCREEN_WIDTH/2.);
        }];
        
        self.pwdLoginButton.alpha = 0.5;
        self.fastLoginButton.alpha = 1.0;
        [self addSubview:self.smallImageView];
        [self.smallImageView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.size.mas_equalTo(self.smallImageView.image.size);
            make.bottom.equalTo(self.mas_bottom);
             make.left.equalTo(self.mas_left).offset(SCREEN_WIDTH/4);
        }];
    }
    
   
}



- (instancetype)initWithImage:(NSString *)image withTip:(NSString *)tip {

    self = [super init];
    if (self) {
        self.tipString = tip;
        self.imageName = image;
        [self setupUIViews];
    }
    return self;
}


- (void)setupUIViews {
    
    UIImageView *imageView = [UISetupView setupImageViewWithSuperView: self withImageName: self.imageName];
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.size.mas_equalTo(CGSizeMake(75.0, 75.0));
        make.top.equalTo(self).offset(40.0);
        make.centerX.equalTo(self);
    }];
    
    
    UILabel *tipLabel = [UISetupView setupLabelWithSuperView: self
                                                    withText: self.tipString
                                               withTextColor: [UIColor tc1Color]
                                                withFontSize: 16.0];
    [tipLabel setTextAlignment: NSTextAlignmentCenter];
    [tipLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(imageView.mas_bottom).offset(15.0);
        make.centerX.equalTo(self);
        make.left.equalTo(self).offset(15.0);
        make.right.equalTo(self).offset(-15);
    }];
}

#pragma mark - 宝贝相关
- (instancetype)initWithBabyImage:(NSString *)image withTip:(NSString *)tip {
    self = [super init];
    if (self) {
        self.tipString = tip;
        self.imageName = image;
        [self setupBabyUIViews];
    }
    return self;
    
}


- (void)setupBabyUIViews {
    
    UIImageView *imageView = [UISetupView setupImageViewWithSuperView: self withImageName: self.imageName];
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.size.mas_equalTo(CGSizeMake(132.0, 132.0));
        make.top.equalTo(self).offset(29.0);
        make.centerX.equalTo(self);
    }];
    
    
    UILabel *tipLabel = [UISetupView setupLabelWithSuperView: self
                                                    withText: self.tipString
                                               withTextColor: [UIColor tc1Color]
                                                withFontSize: 14.0];
    [tipLabel setTextAlignment: NSTextAlignmentCenter];
    [tipLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(imageView.mas_bottom).offset(9.0);
        make.centerX.equalTo(self);
        make.left.equalTo(self).offset(15.0);
        make.right.equalTo(self).offset(-15);
    }];
}




@end
