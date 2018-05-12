//
//  WDUpdateView.m
//  VaccinePatient
//
//  Created by ZJW on 16/9/21.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDUpdateView.h"

@implementation WDUpdateView

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
    
    
    WS(weakSelf)
    self.backgroundColor =[UIColor clearColor];
    UIView *bgView = [[UIView alloc]init];
    //bgView.image= [UIImage imageNamed:@"版本升级"];
    bgView.backgroundColor = [UIColor whiteColor];
    bgView.layer.cornerRadius = 5;
    bgView.layer.masksToBounds = YES;
    [self addSubview:bgView];
    [bgView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.width.equalTo(@285);
        make.centerX.equalTo(weakSelf.mas_centerX);
        make.centerY.equalTo(weakSelf.mas_centerY).offset(-10);
        make.bottom.equalTo(weakSelf);
    }];
    UIImageView * headView = [UIImageView new];
    headView.image = [UIImage imageNamed:@"版本升级"];
    [bgView addSubview:headView];
    [headView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(bgView.mas_top);
        make.left.equalTo(bgView.mas_left);
        make.right.equalTo(bgView.mas_right);
        make.height.mas_equalTo(@65.);
    }];

    
    
    self.titleLabel = [[UILabel alloc]init];
    self.titleLabel.textColor = [UIColor tc1Color];
    self.titleLabel.numberOfLines = 0;
    //self.titleLabel.textAlignment = NSTextAlignmentCenter;
    self.titleLabel.font = [UIFont systemFontOfSize:14.];
    [bgView addSubview:self.titleLabel];
    [self.titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bgView).offset(15);
        make.right.equalTo(bgView).offset(-15);
        make.top.equalTo(bgView).offset(75);
        make.height.mas_greaterThanOrEqualTo(@15);
    }];
    UILabel * subTitleLabel = [UILabel new];
    subTitleLabel = [[UILabel alloc]init];
    subTitleLabel.textColor = [UIColor tc1Color];
    subTitleLabel.numberOfLines = 0;
    subTitleLabel.textAlignment = NSTextAlignmentLeft;
    subTitleLabel.font = [UIFont systemFontOfSize:14.0];
    [bgView addSubview:subTitleLabel];
    subTitleLabel.text  = @"新版本更新内容:";
    [subTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.titleLabel);
        make.top.equalTo(self.titleLabel.mas_bottom).offset(15);
    }];
    self.contentLabel = [[UILabel alloc]init];
    self.contentLabel.textColor = [UIColor tc1Color];
    self.contentLabel.numberOfLines = 0;
    self.contentLabel.textAlignment = NSTextAlignmentLeft;
    self.contentLabel.font = [UIFont systemFontOfSize:14.0];
    [bgView addSubview:self.contentLabel];
    [self.contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(weakSelf.titleLabel);
        make.top.equalTo(subTitleLabel.mas_bottom).offset(3);
    }];
    
    UIView *line = [[UIView alloc]init];
    line.backgroundColor = [UIColor dc1Color];
    [bgView addSubview:line];
    [line mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bgView).offset(0);
        make.right.equalTo(bgView).offset(0);
        make.top.equalTo(bgView.mas_bottom).offset(-45);
        make.height.mas_equalTo(0.5);
    }];
    
    self.cancelBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.cancelBtn setTitleColor:[UIColor tc3Color] forState:UIControlStateNormal];
    self.cancelBtn.titleLabel.font = [UIFont systemFontOfSize:16];
    [self.cancelBtn setTitle:@"取消" forState:UIControlStateNormal];
    [self.cancelBtn setBackgroundColor:[UIColor clearColor]];
    self.cancelBtn.layer.borderColor = [UIColor clearColor].CGColor;
    [self.cancelBtn setTitleColor:[UIColor tc3Color] forState:UIControlStateNormal];
    self.cancelBtn.layer.borderWidth = 0.5;
    self.cancelBtn.layer.cornerRadius = 5;
    self.cancelBtn.layer.masksToBounds = YES;
    [self.cancelBtn addTarget:self action:@selector(cancelClick:) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:self.cancelBtn];
    
    
    self.submitBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.submitBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
    self.submitBtn.titleLabel.font = [UIFont systemFontOfSize:16];
    [self.submitBtn setTitle:@"确定" forState:UIControlStateNormal];
    [self.submitBtn setBackgroundColor:[UIColor clearColor]];
    self.submitBtn.layer.cornerRadius = 5;
    self.submitBtn.layer.masksToBounds = YES;
    [self.submitBtn addTarget:self action:@selector(submitClick:) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:self.submitBtn];
    
    [self.cancelBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bgView).offset(15);
        make.top.equalTo(line.mas_bottom).offset(0);
        make.right.equalTo(self.submitBtn.mas_left).offset(-15);
        make.height.mas_equalTo(45);
    }];
    
    self.submitBtn.backgroundColor =[UIColor clearColor];
    [self.submitBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.cancelBtn.mas_right).offset(15);
        make.top.equalTo(self.cancelBtn);
        make.right.equalTo(bgView).offset(-15);
        make.width.equalTo(self.cancelBtn);
        make.height.equalTo(self.cancelBtn);
    }];
    self.submitBtn.userInteractionEnabled=YES;
    
    
    UIView * stick = [[UIView alloc] init];
    stick.backgroundColor = [UIColor dc1Color];
    [bgView addSubview:stick];
    [stick mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(line.mas_bottom);
        make.bottom.equalTo(bgView.mas_bottom);
        make.width.equalTo(@0.5);
        make.centerX.equalTo(bgView.mas_centerX);
        
    }];
    
    
    if(viewType == WDAlertViewTypeOne)
    {
        self.cancelBtn.alpha=0;
        stick.alpha=0;
        [self.submitBtn mas_remakeConstraints:^(MASConstraintMaker *make) {
            
            make.top.equalTo(self.cancelBtn).offset(0);
            make.centerX.equalTo(self.mas_centerX);
            make.left.equalTo(bgView.mas_left).offset(40);
            make.right.equalTo(bgView.mas_right).offset(-40);
            make.height.equalTo(@45);
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
    
    NSDictionary *attribute = @{NSFontAttributeName: self.titleLabel.font};
    
    CGSize size = CGSizeMake(285-15-15, 29999);
    size =[title boundingRectWithSize: size
                              options: NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading | NSStringDrawingTruncatesLastVisibleLine
                           attributes: attribute context:nil].size;
    if (!self.titleLabel.text.length) {
        size = CGSizeMake(0, 0);
    }
    NSDictionary *attribute1 = @{NSFontAttributeName: self.contentLabel.font};
    
    CGSize size1 = CGSizeMake(285-15-15, 29999);
    size1 =[content boundingRectWithSize: size1
                                 options: NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading | NSStringDrawingTruncatesLastVisibleLine
                              attributes: attribute1 context:nil].size;
    if (!self.contentLabel.text.length) {
        size1 = CGSizeMake(0, 0);
    }
    if (size1.height + size.height + 75 + 55+16. > 245.) {
       
        self.frame = CGRectMake(0, 0, 285, size1.height + size.height + 75 + 55+16. );
    }else{
     self.frame = CGRectMake(0, 0, 285, 245.5);
    }
   
}

- (void)loadTitle:(NSString *)title {
    
}

-(void)showViewWithHaveBackAction:(BOOL)isHaveBackAction withHaveBackView:(BOOL)isHaveBackView {
    [self.nav presentPopupView:self animationType:MJPopupViewAnimationFade isHaveBack:isHaveBackAction isHaveBackView:isHaveBackView];
}
-(instancetype)initWithNavigationController:(UINavigationController *)nav{
    self = [super init];
    if (self) {
        self.nav = nav;
        [self setupView];
        
    }
    return self;
    
}
-(void)dismiss {
    [[BFRouter router].navi dismissPopupViewControllerWithanimationType:MJPopupViewAnimationFade];
}

-(void)dealloc {
    self.nav = nil;
}



@end

