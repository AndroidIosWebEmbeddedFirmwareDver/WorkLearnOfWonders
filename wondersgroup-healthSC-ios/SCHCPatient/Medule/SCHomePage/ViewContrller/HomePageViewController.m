//
//  HomePageViewController.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HomePageViewController.h"
#import "YSButton.h"
#import "OnLinePayCell.h"
#import "HotTopicConsultCell.h"
#import "HealthHomeFunctionCell.h"
#import "SCDepartmentViewController.h"
#import "SCorderDetailViewController.h"
#import "SCCertificationFixedViewController.h"
#import "HomeAreaSearchViewController.h"
#import "HomeSearchViewController.h"
#import "ReportListViewController.h"
#import "SCNearbyHospitalViewController.h"
#import "ElectronicPrescribingVC.h"
#import "Context.h"
#import "LocationModel.h"
#import "TakeReportViewController.h"
#import "SCCertificationResultVC.h"
#import "WDHospitalListRootViewController.h"
#import "SCOnlinePayRootViewController.h"
#import "SCHomePageViewModel.h"
#import "UserService.h"
#import "SCCertificationFixedViewController.h"

#import "WDBannerCell.h"
#import "WDUpdateView.h"
#import "LocationManager.h"
#import "ReferralVC.h"
#define UISCREENW  [UIScreen mainScreen].bounds.size.width
#define UISCREENH  [UIScreen mainScreen].bounds.size.height

#define ROTATINGCELL @"WDBannerCell"
#define ONLINEPAYCELL @"OnLinePayCell"
#define HOTTOPICCONSULTCELL @"HotTopicConsultCell"
#define FUNCTIONCELL @"HealthHomeFunctionCell"
/*
目前首页定位逻辑：
1.第一次进入首页判断是否开启定位
  1>开启定位
    首页显示定位城市，当用户选择城市后，则首页显示选择的城市（不存储用户选择的城市）
  2>关闭定位或定位失败
    默认显示成都，当用户选择城市后，则首页显示选择的城市（不存储用户选择的城市）
2. 杀掉程序在进入首页(第二次)，和第一次一样，判断是否开启定位，开启就显示定位城市；
 关闭定位或定位失败，则显示成都
 */
@interface HomePageViewController ()<UITableViewDelegate,UITableViewDataSource>{
    YSButton * leftbutton;//城市按钮
    UILabel * labelWithSearch;//搜索里面的文字
    UIView * clearView;//搜索框背景
    UIView * searchView;//搜索框
}
@property (nonatomic,strong)UITableView * myTableView;
@property (nonatomic,strong)SCHomePageViewModel * viewModel;
@property (nonatomic,strong) UIView * blueView;
@property (nonatomic,strong) UIView * titleView;
@end

@implementation HomePageViewController
-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
  self.navigationController.navigationBar.hidden = YES;
    if ([UserManager manager].isLogin ) {
        [[UserService service]updateMessageCount:@{@"uid": [UserManager manager].uid} complete:^{
            
        } failure:^{
            
        }];
    }
}

- (void)viewDidLoad {
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(allowLocation) name:LOCATION_WARING_SETTING object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(reloadReFresh)
                                                 name:UIApplicationDidBecomeActiveNotification object:nil];
    [super viewDidLoad];
    [self getDatas];
    [self setBindModel];
    [self setMyTableView];
    [self SimilateNaviBar];
  
   
}
-(void)getDatas{
    
    self.viewModel = [[SCHomePageViewModel alloc]init];
    
    [LoadingView showLoadingInView:self.view];
    
    [self.viewModel requestHomeDatasuccess:^{
        [LoadingView hideLoadinForView:self.view];
        
        [self.myTableView reloadData];
        
    } failure:^(NSError *error) {
        [LoadingView hideLoadinForView:self.view];
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        
    }];
}

-(void)setMyTableView{
    WS(ws)
    self.myTableView = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStyleGrouped withDelegateAndDataSource:self];
    self.myTableView.backgroundColor = [UIColor clearColor];
    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(ws.view);
        make.bottom.equalTo(ws.view);
    }];

    [self.myTableView registerClass:[WDBannerCell class] forCellReuseIdentifier:ROTATINGCELL];
    [self.myTableView registerClass:[OnLinePayCell class] forCellReuseIdentifier:ONLINEPAYCELL];
    [self.myTableView registerClass:[HotTopicConsultCell class] forCellReuseIdentifier:HOTTOPICCONSULTCELL];
    [self.myTableView registerClass:[HealthHomeFunctionCell class] forCellReuseIdentifier:FUNCTIONCELL];
    self.myTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.myTableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(reloadReFresh)];
    

}

-(void)reloadReFresh{
    
    if (![[Global global] networkReachable]) {
        [self.myTableView.mj_header endRefreshing];
        [MBProgressHUDHelper showHudWithText:@"无网络..请检查网络设置"];
    }

    
    [self.viewModel requestHomeDatasuccess:^{
        [self.myTableView.mj_header endRefreshing];
        [self.myTableView reloadData];
    } failure:^(NSError *error) {
        
        [self.myTableView.mj_header endRefreshing];
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
    }];
    
}
- (void)alertAllowLocation{
    WDAlertView *alert = [[WDAlertView alloc]initWithNavigationController:self.navigationController withType:WDAlertViewTypeTwo];
    [alert reloadTitle:@"" content:@"打开“定位服务”来允许“微健康”确定您的位置。"];
    [alert.submitBtn setTitle:@"设置" forState:UIControlStateNormal];
    [alert.cancelBtn setTitle:@"取消" forState:UIControlStateNormal];
    alert.submitBlock = ^(WDAlertView *view) {
        [view dismiss];
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:UIApplicationOpenSettingsURLString]];
    };
    
    alert.cancelBlock = ^(WDAlertView *view) {
        [view dismiss];
    };
    
    [alert showViewWithHaveBackAction:YES withHaveBackView:YES];
}

-(void)allowLocation{
    
    if ([LocationManager isEnableLocation]==NO) {
            [self alertAllowLocation];
    }
    
}

-(void)setBindModel{
    
    WS(weakSelf);
    
    [RACObserve(weakSelf,viewModel.model) subscribeNext:^(HomePageModel * x) {

        if (x.banners.count==0&&x.functionIcons.count==0&&x.news.count == 0) {
            if ([[DBManager manager]getHomePageDatas]) {
                
                self.viewModel.model = [[DBManager manager]getHomePageDatas];
            }
            [self.myTableView reloadData];

        }
        
    }];
    
    
//    [RACObserve([Global global], networkReachable) subscribeNext:^(id x) {
//        
//        BOOL net = [x boolValue];
//        if (net == YES) {
//            [self reloadReFresh];
//        }else{
//            [self.myTableView.mj_header endRefreshing];
//        }
//    }];
    
    [RACObserve([LocationModel Instance], areaName) subscribeNext:^(NSString *  x) {
        if(x){
            leftbutton.buttonTitle.text = x;
        }
    }];
    
//提示升级------------------------
    [RACObserve([TaskManager manager], appConfig)subscribeNext:^(AppConfigModel * model) {
        if (model.appUpdate) {
            
            if ([Global global].needUpdateVerson==0) {
                if ([model.appUpdate.hasUpdate boolValue]) {
                    if ([model.appUpdate.forceUpdate boolValue]) {
                        WDUpdateView * alertView = [[WDUpdateView alloc]initWithNavigationController:self.navigationController withType:WDAlertViewTypeOne];
                        NSString * title =  [NSString stringWithFormat:@"您的当前版本v%@，发现新版本v%@",APP_VERSION,model.appUpdate.lastVersion];
                        NSString * content = model.appUpdate.updateMsg;
                        [alertView.submitBtn setTitle:@"立即更新" forState:UIControlStateNormal];
                        [alertView.submitBtn setTitle:@"立即更新" forState:UIControlStateHighlighted];
                        alertView.submitBlock = ^(WDUpdateView *alert){
                            
                            [[UIApplication sharedApplication]openURL:[NSURL URLWithString:model.appUpdate.downloadUrl]];
                        };
                        
                        [alertView reloadTitle:title content:content];
                        [alertView showViewWithHaveBackAction:NO withHaveBackView:YES];
                        [Global global].needUpdateVerson =1;
                    }else{
                        if ([Global global].needUpdateVerson == 0){
                            WDUpdateView * alertView = [[WDUpdateView alloc]initWithNavigationController:self.navigationController withType:WDAlertViewTypeTwo];
                            [alertView.submitBtn setTitle:@"立即更新" forState:UIControlStateNormal];
                            [alertView.submitBtn setTitle:@"立即更新" forState:UIControlStateHighlighted];
                            
                            [alertView.cancelBtn setTitle:@"稍后再说" forState:UIControlStateNormal];
                            [alertView.cancelBtn setTitle:@"稍后再说" forState:UIControlStateHighlighted];
                            NSString * title =  [NSString stringWithFormat:@"您的当前版本v%@，发现新版本v%@",APP_VERSION,model.appUpdate.lastVersion];
                            NSString * content = model.appUpdate.updateMsg;
                            @weakify(alertView)
                            alertView.submitBlock = ^(WDUpdateView *alert){
                                @strongify(alertView)
                                [alertView dismiss];
                                [[UIApplication sharedApplication]openURL:[NSURL URLWithString:model.appUpdate.downloadUrl]];
                               
                            };
                            
                            
                            alertView.cancelBlock = ^(WDUpdateView *alert){
                                @strongify(alertView)
                                [alertView dismiss];
                               
                            };
                            [alertView reloadTitle:title content:content];
                            [alertView showViewWithHaveBackAction:YES withHaveBackView:YES];
                            [Global global].needUpdateVerson =1;
                        }
                    }
                }
            }
        }
    }];

}

#pragma CustomNavigationBar 自定义-NavigationBar------
-(void)SimilateNaviBar{
    self.blueView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, UISCREENW, 64)];
    self.blueView.backgroundColor = [UIColor bc1Color];
    self.blueView.alpha=0;
    [self.view addSubview:self.blueView];
    
    self.titleView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, UISCREENW, 64)];
    [self.view addSubview:self.titleView];
    [self.titleView.layer insertSublayer:[self createGradientLayer] atIndex:0];
    
    clearView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, UISCREENW, 64)];
    clearView.backgroundColor = [UIColor clearColor];
    [self.view addSubview:clearView];
/*
  储存城市-------------------------------------------
    leftbutton.backgroundColor = [UIColor yellowColor];
    leftbutton.buttonTitle.backgroundColor = [UIColor redColor];
    leftbutton.buttonImage.backgroundColor = [UIColor greenColor];
 
    if ([[TaskManager manager] getCityName]) {
        leftbutton.buttonTitle.text = [[TaskManager manager] getCityName];
        leftbutton.buttonImage.image =[UIImage imageNamed:@"icon下箭头白"];
    }else{
        leftbutton.buttonTitle.text = @"成都";
        leftbutton.buttonImage.image =[UIImage imageNamed:@"icon下箭头白"];
    }
*/
    
//城市按钮—-------------------------------------
    leftbutton = [[YSButton alloc] init];
    leftbutton.buttonTitle.text = @"成都";
    leftbutton.buttonImage.image =[UIImage imageNamed:@"icon下箭头白"];
    leftbutton.buttonTitle.textColor = [UIColor whiteColor];
    leftbutton.buttonTitle.font =[UIFont systemFontOfSize:12];
    [leftbutton.buttonTitle setTextAlignment: NSTextAlignmentCenter];
    [[leftbutton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x){
        [self SelectArea:nil];
    }];
    [clearView addSubview:leftbutton];
    WS(weakSelf)
    searchView = [[UIView alloc] initWithFrame:CGRectZero];
    searchView.backgroundColor =[[UIColor alloc] initWithRed:1 green:1 blue:1 alpha:0.8];
    [searchView.layer setMasksToBounds:YES];
    searchView.layer.cornerRadius =5;
    [clearView addSubview:searchView];
    [searchView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.width.mas_equalTo((SCREEN_WIDTH/750) * 582);
        make.right.equalTo(weakSelf.view).offset(-15);
        make.bottom.equalTo(clearView).offset(-6);
        make.top.equalTo(clearView).offset(21);
    }];
    [leftbutton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view);
        make.centerY.equalTo(searchView);
        make.right.equalTo(searchView.mas_left).offset(-6);
        make.height.equalTo(@30);
    }];
//搜索🔍图片-----------------------------------------------
    UIImageView * icon =[[UIImageView alloc] initWithFrame:CGRectZero];
    icon.size = CGSizeMake(12, 13);
    icon.image = [ UIImage imageNamed:@"ic_nav_gray_search"];
    [searchView addSubview:icon];
    [icon mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(searchView.mas_left).offset(10);
        make.centerY.equalTo(searchView.mas_centerY);
        make.width.mas_equalTo(12);
        make.height.mas_equalTo(13);
    }];
//搜索框的文字----------------------------------------------
    labelWithSearch = [[UILabel alloc] initWithFrame:CGRectZero];
    labelWithSearch.textAlignment = NSTextAlignmentLeft;
    labelWithSearch.textColor = [UIColor whiteColor];
    labelWithSearch.font = [UIFont systemFontOfSize:13];
    labelWithSearch.text=@"搜索医院、医生、文章";
    [clearView addSubview:labelWithSearch];
    [labelWithSearch mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(icon.mas_right).offset(6);
        make.width.mas_equalTo(200);
        make.centerY.equalTo(searchView.mas_centerY);
        make.height.equalTo(searchView.mas_height);
    }];
    
// 搜索按钮  -----------------------------------------------
    UIButton * button = [[UIButton alloc] initWithFrame:CGRectZero];
    [[button rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x) {
        [self OpenSearch:nil];
    }];
    [searchView addSubview:button];
    [button mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(icon.mas_right);
        make.right.equalTo(searchView);
        make.height.equalTo(searchView.mas_height);
        make.top.equalTo(searchView.mas_top);
    }];
    
}

#pragma  createGradientLayer 渐变颜色 ---------------------------
-(CAGradientLayer *) createGradientLayer{
    CAGradientLayer *  _gradientLayer = [CAGradientLayer layer];
    _gradientLayer.colors = [NSArray arrayWithObjects:
                             (id)[[UIColor clearColor] CGColor],
                             (id)[[UIColor tc2Color] CGColor], nil, nil];
    _gradientLayer.frame = CGRectMake(0, 0, UISCREENW, 64);
    _gradientLayer.startPoint = CGPointMake(0.5,1.0);
    _gradientLayer.endPoint = CGPointMake(0.5, 0.0);
    return _gradientLayer;
}

#pragma OpenSearch 搜索界面入口
-(void)OpenSearch:(id)sender{
    HomeSearchViewController * con = [[HomeSearchViewController alloc] init];
    con.hidesBottomBarWhenPushed =YES;
    con.type = HomeSearchType_All;
    [self.navigationController pushViewController:con animated:YES];
}

#pragma mark    - UITableViewDelegate,UITableViewDataSource -------------
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (section == 3){
        return self.viewModel.model.news.count;
    }else{
        return 1;
    }
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 4;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    /* section:
               0->banners,
               1,2->functions,
               3->news
     */
    switch (indexPath.section){
        case 0:
        {
            WDBannerCell *cell = [tableView dequeueReusableCellWithIdentifier:ROTATINGCELL];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            cell.bannerDataArray = self.viewModel.model.banners;
            // 轮播图点击
            cell.bannerClickedHandler = ^(BannersModel * bannerData){
                if (bannerData.hoplink == nil || [bannerData.hoplink isEqualToString:@""]) {
                    return;
                }
                [[BFRouter router]open:bannerData.hoplink];
            };
            return cell;
        }
            break;
        case 1:
        {
        HealthHomeFunctionCell * cell = [tableView dequeueReusableCellWithIdentifier:FUNCTIONCELL];
            if (self.viewModel.model.functionIcons.count > 2) {
                
                [cell setCellWithArray:self.viewModel.model.functionIcons];
                FunctionModel * leftModel =  self.viewModel.model.functionIcons[0];
                FunctionModel * rightTopModel =  self.viewModel.model.functionIcons[1];
                FunctionModel * rightBottomModel =  self.viewModel.model.functionIcons[2];
                cell.rightTopButtonBlock = ^() {
                    if (rightTopModel.hoplink == nil || [rightTopModel.hoplink isEqualToString:@""]) {
                        return;
                    }
                    [[BFRouter router] open:rightTopModel.hoplink];
                    
                };
                cell.leftButtonBlock = ^{
                    if (leftModel.hoplink == nil || [leftModel.hoplink isEqualToString:@""]) {
                        return;
                    }
                    [[BFRouter router] open:leftModel.hoplink];
                    
                };
                cell.rightBottomButtonBlock=^{
                    if (rightBottomModel.hoplink == nil || [rightBottomModel.hoplink isEqualToString:@""]) {
                        return;
                    }
                    [[BFRouter router] open:rightBottomModel.hoplink];
                };
            }
            return cell;
        }
            break;
        case 2:
        {
    
            OnLinePayCell * cell = [tableView dequeueReusableCellWithIdentifier:ONLINEPAYCELL];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            if (self.viewModel.model.functionIcons.count > 5) {
                FunctionModel * model1 = self.viewModel.model.functionIcons[3];
                FunctionModel * model2 = self.viewModel.model.functionIcons[4];
                FunctionModel * model3 = self.viewModel.model.functionIcons[5];
                if (model1!=nil&&model2!=nil&&model3!=nil) {
                    cell.dataArray = @[model1,model2,model3];
                }
                
                cell.onLinePayBlock = ^(){
                    //跳转在线支付
                    [[BFRouter router]open:model1.hoplink];
                };
                cell.drawReportBlock = ^(){
                    //跳转提取报告
                    [[BFRouter router]open:model2.hoplink];
                };
                cell.electronPrescriptionBlock = ^(){
                    //跳转电子处方
                    [[BFRouter router]open:model3.hoplink];
                };
                
            }
            return cell;
        
        }
            break;
        case 3:
        {
            HotTopicConsultCell * cell = [tableView dequeueReusableCellWithIdentifier:HOTTOPICCONSULTCELL];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
           ArticlessModel * model = self.viewModel.model.news[indexPath.row];
            if (model) {
                cell.model = model;
            }
            return cell;
        }
            break;
        default:
            return nil;
            break;
    }
}

-(CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    switch (section) {
        case 0:
            return 2.5f;
            break;
        case 1:
            return 2.5f;
            break;
        case 2:
            return 10.0f;
            break;
        case 3:
            return 0.1f;
            break;
        default:
            return 0.1f;
            break;
    }
}
-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    switch (section) {
        case 0:
            return 0.1f;
            break;
        case 1:
            return 2.5f;
            break;
        case 2:
            return 2.5f;
            break;
        case 3:
            return 32.0f;
            break;
        default:
            return 2.0f;
            break;
    }
}
-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    if (section == 3) {
        UIView * bgView = [UIView new];
        bgView.backgroundColor = [UIColor whiteColor];
        UILabel * textLab = [UILabel new];
        textLab.text = @"热门资讯";
        textLab.font = [UIFont systemFontOfSize:12];
        textLab.textColor = [UIColor blackColor];
        [bgView addSubview:textLab];
        UILabel * lineLab = [UILabel new];
        lineLab.backgroundColor = [UIColor bc3Color];
        [textLab addSubview:lineLab];
        
        [textLab mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(bgView);
            make.left.equalTo(bgView).offset(15);
            make.height.mas_equalTo(bgView);
            make.width.mas_equalTo(@50);
        }];
        [lineLab mas_makeConstraints:^(MASConstraintMaker *make) {
            make.bottom.equalTo(bgView);
            make.height.equalTo(@0.5);
            make.left.width.equalTo(bgView);
        }];
        
        if (self.viewModel.model.news.count>0) {
            return bgView;
        }
        
        return nil;
    }else{
        return nil;
    }
    
}



- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    return nil;
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    switch (indexPath.section) {
        case 0:
            return AdaptiveFrameHeight(375/2.0);
            break;
        case 1:
            return 300/2.0;
            break;
        case 2:
            return 176/2.0;
            break;
        case 3:
            return 176/2.0;
            break;
        default:
            return 40;
            break;
    }
    
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if (indexPath.section==3) {
        ArticlessModel  * model = self.viewModel.model.news[indexPath.row];
        [[BFRouter router]open:model.url];
    }
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
    CGFloat offsetY = scrollView.contentOffset.y ;
    clearView.alpha =1+offsetY / (64.0);
    self.titleView.alpha=1-offsetY / (64.0);
    if(offsetY<0){
        self.titleView.alpha=1+offsetY / (64.0);
        searchView.backgroundColor = [UIColor whiteColor];
        searchView.alpha = 0.3;
        labelWithSearch.textColor = [UIColor whiteColor];
        leftbutton.buttonTitle.textColor = [UIColor whiteColor];
        leftbutton.buttonImage.image = [UIImage imageNamed:@"icon下箭头白"];
    }
    if(offsetY>64){
        self.blueView.alpha = offsetY/(64.0);
        searchView.backgroundColor = [UIColor bc2Color];
        searchView.alpha = 1;
        labelWithSearch.textColor = [UIColor tc2Color];
        leftbutton.buttonTitle.textColor = [UIColor tc2Color];
        leftbutton.buttonImage.image = [UIImage imageNamed:@"icon下箭头灰"];
    }else{
        self.blueView.alpha = offsetY/(64.0);
        searchView.backgroundColor = [UIColor whiteColor];
        searchView.alpha = 0.3;
         labelWithSearch.textColor = [UIColor whiteColor];
        leftbutton.buttonTitle.textColor = [UIColor whiteColor];
        leftbutton.buttonImage.image = [UIImage imageNamed:@"icon下箭头白"];
    }
}

#pragma goToSelectArea 选择城市-------------
-(void)SelectArea:(id)sender{
    HomeAreaSearchViewController * con = [[HomeAreaSearchViewController alloc] init];
    con.hidesBottomBarWhenPushed =true;
    [self.navigationController pushViewController:con animated:true];
    con.block = ^(NSString * cityName,NSString * cicyCode){
    /*
     把选择的城市2 name 和 code 存到模型里--(用户选择的)--为了用户选择非定位城市，下次进入首页时默认
     是用户选择的城市而非定位城市--
     */
//        [LocationModel Instance].showAreaCode = cicyCode;
//        [LocationModel Instance].showAreaName =cityName;
        leftbutton.buttonTitle.text = cityName;
//      [[TaskManager manager] setMyCityCode:cicyCode andCityName:cityName];
    };
}

-(void)viewDidAppear:(BOOL)animated{
    [super viewDidAppear:animated];
    [self.navigationController setNavigationBarHidden:true animated:NO];
}

-(void)viewWillDisappear:(BOOL)animated{
    //self.hidesBottomBarWhenPushed =FALSE;
    [self.navigationController setNavigationBarHidden:false animated:NO];
    [[NSNotificationCenter defaultCenter] removeObserver:self];
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
