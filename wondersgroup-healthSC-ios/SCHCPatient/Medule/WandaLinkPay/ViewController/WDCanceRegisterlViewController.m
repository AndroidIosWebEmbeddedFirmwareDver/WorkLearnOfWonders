//
//  WDCanceRegisterlViewController.m
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/15.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDCanceRegisterlViewController.h"

#define IMG_NAME @"icon取消成功"
#define CANCEL_REGISTER_TEL @"400-900-9957"


@interface WDCanceRegisterlViewController ()<UIGestureRecognizerDelegate>

@property (nonatomic, strong) UIView *contentView;

@property (nonatomic, strong) UIImageView *iconV;
@property (nonatomic, strong) UILabel *titleLabel;
@property (nonatomic, strong) UILabel *tipLabel;
@property (nonatomic, strong) UILabel *otherInfoLabel;
@property (nonatomic, strong) UILabel *telLabel;

@property (nonatomic, strong) UIButton *finishBtn;

@property (nonatomic, strong) UILabel *bottomTipLabel;

@end

@implementation WDCanceRegisterlViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupSubviews];
    [self setupSubviewsLayout];
}


- (void)finishAction {
    [self popBack];
}


- (void)callAction {
    NSURL *telURL = [NSURL URLWithString:[NSString stringWithFormat:@"tel://%@", CANCEL_REGISTER_TEL]];
    UIAlertController *alertC = [UIAlertController alertControllerWithTitle:[NSString stringWithFormat:@"拨打%@", CANCEL_REGISTER_TEL]
                                                                    message:nil
                                                             preferredStyle:UIAlertControllerStyleAlert];
    [alertC addAction:[UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
    }]];
    
    [alertC addAction:[UIAlertAction actionWithTitle:@"拨打" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        if (![[UIApplication sharedApplication] canOpenURL:telURL]) {
            NSLog(@"=====>> 无法拨打号码: %@", CANCEL_REGISTER_TEL);
            return;
        }
        
        [[UIApplication sharedApplication] openURL:telURL];
    }]];
    
    
    [self.navigationController presentViewController:alertC animated:YES completion:^{
        
    }];
    
}


#pragma mark - Setup UI

- (void)setupSubviews {
    
    self.title = @"取消预约";
    
    self.navigationItem.hidesBackButton = YES;
    self.navigationItem.leftBarButtonItem = nil;
    
    _iconV = [[UIImageView alloc] initWithImage:[UIImage imageNamed:IMG_NAME]];
    [self.contentView addSubview:_iconV];
    
    _titleLabel = [self creatLabelWithTitle:@"取消预约成功"
                                 titleColor:[UIColor tc1Color]
                                   fontSize:18
                                     action:nil];
    
    _tipLabel = [self creatLabelWithTitle:@"您所支付的挂号费退款将于二十四小时内返还至您付款时所使用的账户，请您注意查收"
                               titleColor:[UIColor tc2Color]
                                 fontSize:12
                                   action:nil];
    _otherInfoLabel = [self creatLabelWithTitle:@"如有疑问，请咨询客服"
                                     titleColor:[UIColor tc1Color]
                                       fontSize:12
                                         action:nil];
    _telLabel = [self creatLabelWithTitle:CANCEL_REGISTER_TEL
                               titleColor:[UIColor tc5Color]
                                 fontSize:12
                                   action:nil];
    
    [_telLabel addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(callAction)]];
    _telLabel.userInteractionEnabled = YES;
    
    
    _finishBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    _finishBtn.layer.cornerRadius = 4;
    [_finishBtn setBackgroundColor:[UIColor sbc4Color]];
    [_finishBtn setTitle:@"完成" forState:UIControlStateNormal];
    _finishBtn.titleLabel.font = [UIFont systemFontOfSize:16];
    [_finishBtn addTarget:self action:@selector(finishAction) forControlEvents:UIControlEventTouchUpInside];
    [_contentView addSubview:_finishBtn];
    
    
    
    _bottomTipLabel = [self creatLabelWithTitle:@"多次取消预约将受到平台惩罚"
                                     titleColor:[UIColor tc3Color]
                                       fontSize:14
                                         action:nil];
    
}


- (void)setupSubviewsLayout {
    UIImage *img = [UIImage imageNamed:IMG_NAME];
    [_iconV mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(_contentView);
        make.top.offset(35);
        make.size.mas_equalTo(img.size);
    }];
    
    
    [_titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(_contentView);
        make.top.equalTo(_iconV.mas_bottom).offset(15);
    }];
    
    
    [_tipLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_titleLabel.mas_bottom).offset(25);
        make.left.equalTo(_contentView).offset(43.5);
        make.right.equalTo(_contentView).offset(-43.5);
    }];
    
    
    [_otherInfoLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_tipLabel.mas_bottom).offset(10);
        make.right.equalTo(_contentView.mas_centerX).offset(20);
    }];
    
    
    [_telLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(_otherInfoLabel);
        make.left.equalTo(_otherInfoLabel.mas_right);
    }];
    
    //
    CGFloat btnMargin = 16;
    [_finishBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.offset(btnMargin);
        make.right.offset(-btnMargin);
        make.top.equalTo(_otherInfoLabel.mas_bottom).offset(50);
        make.height.mas_equalTo(44);
    }];
    
    
    //
    [_bottomTipLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.offset(-30);
        make.centerX.offset(0);
    }];
}



- (UILabel *)creatLabelWithTitle:(NSString *)title titleColor:(UIColor *)titleColor fontSize:(CGFloat)fontSize action:(SEL)action {
    UILabel *label = [[UILabel alloc] init];
    label.text          = title;
    label.font          = [UIFont systemFontOfSize:fontSize];
    label.textColor     = titleColor;
    label.textAlignment = NSTextAlignmentCenter;
    label.numberOfLines = 0;
    
    if (action) {
        UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:action];
        [label addGestureRecognizer:tap];
    }
    
    [self.contentView addSubview:label];
    
    return label;
}


#pragma mark - Lazy Loading

- (UIView *)contentView {
    if (!_contentView) {
        _contentView = [[UIView alloc] init];
        _contentView.backgroundColor = [UIColor whiteColor];
        [self.view addSubview:_contentView];
        
        [_contentView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.offset(10);
            make.left.right.bottom.equalTo(self.view);
        }];
    }
    return _contentView;
}

@end
