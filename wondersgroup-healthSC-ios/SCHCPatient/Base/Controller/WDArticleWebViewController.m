//
//  WDArticleWebViewController.m
//  EyeProtection
//
//  Created by 杜凯 on 16/3/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDArticleWebViewController.h"
//#import "WDShare.h"
#import "ArticleViewModel.h"
#import "ArticleInfoModel.h"
#import "UrlParamUtil.h"
#import "WDShare.h"

@interface WDArticleWebViewController ()
{
}
@property (nonatomic,assign)BOOL isCollect;

@property (nonatomic,strong)UIBarButtonItem * collectButton;

@property (nonatomic,strong)UIBarButtonItem * shareButton;

@property (nonatomic,strong)ArticleViewModel *viewModel;

@property (nonatomic,strong)ArticleInfoModel *articleInfoModel;

@property (nonatomic, strong)UIView *rightNaviCustomView;
@property (nonatomic, strong)UIButton *btnCollect;
@property (nonatomic, strong)UIButton *btnShare;


@end

@implementation WDArticleWebViewController

- (void)setModel:(AritcleModel *)model
{
    _model = model;
    model.pv = [NSNumber numberWithInteger:model.pv.integerValue + 1];
}

- (UIBarButtonItem *)collectButton{
    if (!_collectButton) {
        UIImage * image = [UIImage imageNamed:@"faver_u_icon"];
        _collectButton = [[UIBarButtonItem alloc] initWithImage:[image imageWithRenderingMode:UIImageRenderingModeAutomatic ] style:UIBarButtonItemStylePlain target:self action:@selector(collectAction:)];
        
    }
    return _collectButton;
}
- (UIBarButtonItem *)shareButton{
    if (!_shareButton) {
        UIImage * image = [UIImage imageNamed:@"share_w"];
        _shareButton = [[UIBarButtonItem alloc] initWithImage:[image imageWithRenderingMode:UIImageRenderingModeAutomatic ] style:UIBarButtonItemStylePlain target:self action:@selector(articleShareAction:)];
    }
    return _shareButton;
}
- (ArticleViewModel *)viewModel{
    if (!_viewModel) {
        _viewModel = [[ArticleViewModel alloc] initWithArticleId:self.articleId];
        
    }
    return _viewModel;
}
- (void)viewDidLoad {
    if (!self.loadUrl) {
        self.loadUrl = self.model.url;
        self.articleId = self.model.articleId;
    }
    if (self.articleId.length == 0) {
        NSDictionary *parms = [UrlParamUtil getParamsFromUrl: [NSURL URLWithString:self.loadUrl]];
        self.articleId = [parms valueForKey: @"id"];
    }
    
    [super viewDidLoad];
    //self.navigationItem.title = @"文章详情";
//    [self setupRightNaviItems];
    
    [self checkIsCollect];
    // Do any additional setup after loading the view.
}

#pragma mark - Setup View
- (void)setupRightNaviItems
{
    self.rightNaviCustomView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 100, 44)];
    self.rightNaviCustomView.backgroundColor = [UIColor clearColor];
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:self.rightNaviCustomView];
    
    WS(weakSelf);
    self.btnShare = [[UIButton alloc] init];
    [self.btnShare setImage:[UIImage imageNamed:@"资讯详细"] forState:UIControlStateNormal];
    [self.btnShare addTarget:self action:@selector(articleShareAction:) forControlEvents:UIControlEventTouchUpInside];
    [self.rightNaviCustomView addSubview:self.btnShare];
    
    [self.btnShare mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.rightNaviCustomView);
        make.centerY.equalTo(weakSelf.rightNaviCustomView);
        make.size.mas_equalTo(CGSizeMake(35, 44));
    }];
    
//    self.btnCollect = [[UIButton alloc] init];
//    [self.btnCollect setImage:[UIImage imageNamed:@"faver_u_icon"] forState:UIControlStateNormal];
//    [self.btnCollect addTarget:self action:@selector(collectAction:) forControlEvents:UIControlEventTouchUpInside];
//    [self.rightNaviCustomView addSubview:self.btnCollect];
//    
//    [self.btnCollect mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.right.equalTo(weakSelf.btnShare.mas_left).offset(2);
//        make.centerY.equalTo(weakSelf.rightNaviCustomView);
//        make.size.mas_equalTo(CGSizeMake(35, 44));
//    }];
//    
//    weakSelf.btnCollect.hidden = YES;
}

-(void)loadWebView {
    [super loadWebView];
    if (self.loadSuccess) {
        [self setupRightNaviItems];
    }
    else {
        self.navigationItem.rightBarButtonItem = nil;
    }
}

#pragma mark - 文章收藏、取消收藏
- (void)collectAction:(id)sender{
    
    if (![UserManager manager].isLogin) {
        [[VCManager manager] loadRootViewController];
        return;
    }
    WS(weakSelf);
    self.collectButton.enabled = NO;
    [LoadingView showLoadingInView:self.navigationController.view];
    if (_isCollect) {
         NSLog(@"----按钮点击了---: 收藏");
       
        
        [self.viewModel articleDisCollectArticleWithId:self.articleId complete:^{
            [LoadingView hideLoadinForView:self.navigationController.view];
            weakSelf.isCollect = NO;
            if (weakSelf.model) {
                weakSelf.model.collect = [NSNumber numberWithInteger:weakSelf.model.collect.integerValue -1];
            }
            weakSelf.btnCollect.enabled = YES;
            [weakSelf.btnCollect setImage:[UIImage imageNamed:@"faver_u_icon"] forState:UIControlStateNormal];
            [weakSelf pushNotificationCenter];
        } failure:^(NSError *error) {
            weakSelf.btnCollect.enabled = YES;
            [LoadingView hideLoadinForView:self.navigationController.view];
        }];
    }
    else
    {
         NSLog(@"----按钮点击了---: 取消");
        [self.viewModel articleCollectArticleWithId:self.articleId complete:^{
            weakSelf.isCollect = YES;
            if (weakSelf.model) {
                weakSelf.model.collect = [NSNumber numberWithInteger:weakSelf.model.collect.integerValue + 1];
            }
            weakSelf.btnCollect.enabled = YES;
            [weakSelf.btnCollect setImage:[UIImage imageNamed:@"faver_y_icon"] forState:UIControlStateNormal];
            [LoadingView hideLoadinForView:self.navigationController.view];
            [weakSelf pushNotificationCenter];
        } failure:^(NSError *error) {
            weakSelf.btnCollect.enabled = YES;
            [LoadingView hideLoadinForView:self.navigationController.view];
        }];
    }
    
}

-(void)pushNotificationCenter {
    [[NSNotificationCenter defaultCenter] postNotificationName:COLLECTEDRELOADDATA object:nil];
}

#pragma mark - 检查是否收藏
- (void)checkIsCollect{
    WS(weakSelf);
    _isCollect = NO;
//    self.navigationItem.rightBarButtonItem = self.shareButton;
    [self.viewModel articleCheckCollectWithArticleId:self.articleId complete:^(id response){
        weakSelf.articleInfoModel = response;
        
//        weakSelf.navigationItem.rightBarButtonItems = @[weakSelf.shareButton,weakSelf.collectButton];
//        weakSelf.collectButton.image = self.articleInfoModel.favorite.boolValue?[UIImage imageNamed:@"faver_y_icon"]:[UIImage imageNamed:@"faver_u_icon"];
//        weakSelf.btnCollect.hidden = NO;
//        [weakSelf.btnCollect setImage:self.articleInfoModel.favorite.boolValue?[UIImage imageNamed:@"faver_y_icon"]:[UIImage imageNamed:@"faver_u_icon"] forState:UIControlStateNormal];
        
//        weakSelf.isCollect = self.articleInfoModel.favorite.boolValue;
    } failure:^(NSError *error) {
//        weakSelf.navigationItem.rightBarButtonItems = @[weakSelf.shareButton,weakSelf.collectButton];
//        weakSelf.collectButton.image = [UIImage imageNamed:@"faver_u_icon"];
//        weakSelf.btnCollect.hidden = NO;
//        [weakSelf.btnCollect setImage:[UIImage imageNamed:@"faver_u_icon"] forState:UIControlStateNormal];
//        weakSelf.isCollect = NO;
    }];
    
}

#pragma mark - 文章分享
- (void)articleShareAction:(id)sender{
    
//    [self.viewModel articleAddShareWithArticleId:self.articleId complete:^{
//        
//    } failure:^(NSError *error) {
//        
//    }];

    [[WDShare shareInstance]shareInView:self.view content:self.articleInfoModel.share];
    
   
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
