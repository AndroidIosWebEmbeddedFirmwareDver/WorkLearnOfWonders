//
//  WDAlertView.m
//  EyeProtection
//
//  Created by ZJW on 16/3/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDAlertView.h"

@interface WDAlertView ()
{
    WDAlertViewType viewType;
}
@property (nonatomic, strong) UINavigationController *nav;
@property (nonatomic, strong) UIView *bgView;
@property (nonatomic, strong) UIView *lineView;

@end

@implementation WDAlertView

-(instancetype)initWithNavigationController:(UINavigationController *)nav withType:(WDAlertViewType)type{
    self = [super init];
    if (self) {
        viewType = type;
        self.nav = nav;
        [self setupView];
        
    }
    return self;
}

-(instancetype)init{
    self = [super init];
    if (self) {
        [self setupView];
    }
    return self;
}

-(void)setupView {
    
    UIView *bgView = [[UIView alloc]init];
    bgView.backgroundColor = [UIColor bc1Color];
    bgView.layer.cornerRadius = 5;
    bgView.layer.masksToBounds = YES;
    [self addSubview:bgView];
    [bgView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self).offset(30);
        make.right.equalTo(self).offset(-30);
        make.top.bottom.equalTo(self);
    }];
    _bgView = bgView;
    
    self.titleLabel = [[UILabel alloc]init];
    self.titleLabel.textColor = [UIColor tc1Color];
    self.titleLabel.numberOfLines = 0;
    self.titleLabel.textAlignment = NSTextAlignmentCenter;
    self.titleLabel.font = [UIFont systemFontOfSize:16];
    [bgView addSubview:self.titleLabel];
    [self.titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bgView).offset(15);
        make.right.equalTo(bgView).offset(-15);
        make.top.equalTo(bgView).offset(25);
    }];
    
    self.contentLabel = [[UILabel alloc]init];
    self.contentLabel.textColor = [UIColor tc2Color];
    self.contentLabel.numberOfLines = 0;
    self.contentLabel.textAlignment = NSTextAlignmentCenter;
    self.contentLabel.font = [UIFont systemFontOfSize:14];
    [bgView addSubview:self.contentLabel];
    [self.contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.titleLabel);
        make.top.equalTo(self.titleLabel.mas_bottom).offset(15);
    }];

    UIView *line = [[UIView alloc]init];
    line.backgroundColor = [UIColor dc1Color];
    [bgView addSubview:line];
    _lineView = line;
    [line mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bgView).offset(15);
        make.right.equalTo(bgView).offset(-15);
        make.top.equalTo(self.contentLabel.mas_bottom).offset(25);
        make.height.mas_equalTo(0.5);
    }];
    
    self.cancelBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.cancelBtn setTitleColor:[UIColor tc4Color] forState:UIControlStateNormal];
    self.cancelBtn.titleLabel.font = [UIFont systemFontOfSize:14];
    [self.cancelBtn setTitle:@"取消" forState:UIControlStateNormal];
    [self.cancelBtn setBackgroundColor:[UIColor bc1Color]];
//    self.cancelBtn.layer.borderColor = [UIColor dc1Color].CGColor;
//    self.cancelBtn.layer.borderWidth = 0.5;
//    self.cancelBtn.layer.cornerRadius = 5;
//    self.cancelBtn.layer.masksToBounds = YES;
    [self.cancelBtn addTarget:self action:@selector(cancelClick:) forControlEvents:UIControlEventTouchUpInside];
    [bgView addSubview:self.cancelBtn];

    if (viewType == WDAlertViewTypeTwo) {
        self.submitBtn = [UIButton buttonWithType:UIButtonTypeCustom];
        [self.submitBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
        self.submitBtn.titleLabel.font = [UIFont systemFontOfSize:14];
        [self.submitBtn setTitle:@"确定" forState:UIControlStateNormal];
//        [self.submitBtn setBackgroundColor:[UIColor tc5Color]];
//        self.submitBtn.layer.cornerRadius = 5;
//        self.submitBtn.layer.masksToBounds = YES;
        [self.submitBtn addTarget:self action:@selector(submitClick:) forControlEvents:UIControlEventTouchUpInside];
        [bgView addSubview:self.submitBtn];
        
        [self.cancelBtn mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(bgView);
            make.top.equalTo(line.mas_bottom);
            make.width.equalTo(bgView.mas_width).multipliedBy(0.5);
            make.height.mas_equalTo(44);
        }];
        
        [self.submitBtn mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(self.cancelBtn.mas_right);
            make.top.equalTo(self.cancelBtn);
            make.width.equalTo(self.cancelBtn);
            make.height.equalTo(self.cancelBtn);
        }];
    }else {
        [self.cancelBtn mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerX.equalTo(bgView);
            make.top.equalTo(line.mas_bottom);
            make.width.equalTo(bgView.mas_width);
            make.height.mas_equalTo(44);
        }];
    }

    
}

- (void)submitClick:(id)sender {
    if (self.submitBlock) {
        self.submitBlock(self);
    }
}

- (void)cancelClick:(id)sender {
    if (self.cancelBlock) {
        self.cancelBlock(self);
    }
}

-(void)reloadTitle:(NSString *)title content:(NSString *)content {
    self.titleLabel.text = title;
    self.contentLabel.text = content;
    
    [self changeFrame];
    
    CGFloat height = 25;
    
    NSDictionary *attribute = @{NSFontAttributeName: self.titleLabel.font};
    
    CGSize size = CGSizeMake(SCREEN_WIDTH-30-30-15-15, MAXFLOAT);
    size =[title boundingRectWithSize: size
                              options: NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading | NSStringDrawingTruncatesLastVisibleLine
                           attributes: attribute context:nil].size;
    if (self.titleLabel.text.length) {
        height += size.height;
        height += 15;
    }
    
    NSDictionary *attribute1 = @{NSFontAttributeName: self.contentLabel.font};
    
    CGSize size1 = CGSizeMake(SCREEN_WIDTH-30-30-15-15, MAXFLOAT);
    size1 =[content boundingRectWithSize: size1
                                 options: NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading | NSStringDrawingTruncatesLastVisibleLine
                              attributes: attribute1 context:nil].size;
    if (self.contentLabel.text.length) {
        height += size1.height;
        height += 25;
    }else {
        height += 10;
    }
    height += 0.5+44;

    self.frame = CGRectMake(0, 0, SCREEN_WIDTH, height);
}

-(void)changeFrame {
    if (self.titleLabel.text.length && !self.contentLabel.text.length) {
        [self.contentLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
            make.left.right.equalTo(self.titleLabel);
            make.top.equalTo(self.titleLabel).offset(15);
        }];
    }else if (!self.titleLabel.text.length && self.contentLabel.text.length) {
        [self.contentLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
            make.left.right.equalTo(self.titleLabel);
            make.top.equalTo(self.titleLabel);
        }];
    }

    
    
}

-(void)showViewWithHaveBackAction:(BOOL)isHaveBackAction withHaveBackView:(BOOL)isHaveBackView {

    [self.nav presentPopupView:self animationType:MJPopupViewAnimationFade isHaveBack:isHaveBackAction isHaveBackView:isHaveBackView];
}

-(void)dismiss {
    //导航栏不一样 无法dismiss
     [[BFRouter router].navi dismissPopupViewControllerWithanimationType:MJPopupViewAnimationFade];
}

-(void)dealloc {
    self.nav = nil;
}

@end

