//
//  FamilyDoctorWebViewController.m
//  SCHCPatient
//
//  Created by ZJW on 2017/6/15.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "FamilyDoctorWebViewController.h"

@interface FamilyDoctorWebViewController ()<UIScrollViewDelegate>
@property (nonatomic, strong) UIView *customNavBar;
@property (nonatomic, strong) UIView *navBarBgView;
@property (nonatomic, strong) UIButton *navBackBtn;
@property (nonatomic, strong) UILabel *navTitleLabel;
@property (nonatomic, strong) CAGradientLayer *gradientLayer;

@end

@implementation FamilyDoctorWebViewController

#pragma mark    - lifecycle

- (void)viewDidLoad {
    [super viewDidLoad];
    
    if (!self.isHaveHead) {
        [self setupCustomNavigationBar];
    }
    
    self.baseWebView.scrollView.delegate = self;

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    if (!self.isHaveHead) {
        self.navigationController.navigationBarHidden = YES;
    }
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    if (!self.isHaveHead) {
        self.navigationController.navigationBarHidden = NO;
    }
}

#pragma mark    - method
- (void)setupCustomNavigationBar {
    _customNavBar = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 64)];
    [self.view addSubview:_customNavBar];
    
    //
    _navBarBgView = [[UIView alloc] initWithFrame:_customNavBar.bounds];
    _navBarBgView.backgroundColor = [UIColor whiteColor];
    _navBarBgView.alpha = 0.0;
    [_customNavBar addSubview:_navBarBgView];
    //
    
    _gradientLayer = [CAGradientLayer layer];
    [_customNavBar.layer addSublayer:_gradientLayer];
    _gradientLayer.frame = _customNavBar.bounds;
    
    
    _gradientLayer.startPoint = CGPointMake(0, 0);
    _gradientLayer.endPoint = CGPointMake(0, 1);
    
    _gradientLayer.colors = @[(__bridge id)[UIColor gard1Color].CGColor,
                              (__bridge id)[UIColor gard2Color].CGColor];
    
    _navBackBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [_navBackBtn setImage:[UIImage imageNamed:@"back_white"] forState:UIControlStateNormal];
    [_navBackBtn setImageEdgeInsets:UIEdgeInsetsMake(-10, -10, 10, 10)];
    _navBackBtn.frame = CGRectMake(0, 20, 64, 64);
    [_customNavBar addSubview:_navBackBtn];
    @weakify(self);
    [[_navBackBtn rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x) {
        @strongify(self);
        [self popBack];
    }];
    
    _navTitleLabel = [[UILabel alloc] initWithFrame:CGRectMake(64, 20, SCREEN_WIDTH - 64 * 2, 44)];
    _navTitleLabel.textColor = [UIColor whiteColor];
    _navTitleLabel.font = [UIFont systemFontOfSize:18];
    _navTitleLabel.textAlignment = NSTextAlignmentCenter;
    [_customNavBar addSubview:_navTitleLabel];
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
    CGFloat offsetY = scrollView.contentOffset.y;
    
    CGFloat h = 100.0;
    if (offsetY <= 0) {
        [self setupNavTopState];
        
    } else {
        _navBarBgView.alpha = (offsetY <= h) ? offsetY/h : 1;
        [self setupNavScrollingState];
    }
}

- (void)setupNavTopState {
    _navBarBgView.alpha = 0.0;
    _gradientLayer.opacity = 1.0;
    _navTitleLabel.textColor = [UIColor whiteColor];
    [_navBackBtn setImage:[UIImage imageNamed:@"back_white"] forState:UIControlStateNormal];
}


- (void)setupNavScrollingState {
    _gradientLayer.opacity = 0.0;
    _navTitleLabel.textColor = [UIColor tc1Color];
    [_navBackBtn setImage:[UIImage imageNamed:@"icon_back"] forState:UIControlStateNormal];
}

- (void)webViewDidFinishLoad:(UIWebView *)webView{
    [super webViewDidFinishLoad:webView];
    if (!self.isHaveHead) {
        _navTitleLabel.text = [webView stringByEvaluatingJavaScriptFromString:@"document.title"];
    }
}


@end
