//
//  WDProfileViewController.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDProfileViewController.h"
#import "WDProfileHeadView.h"
#import "WDProfileTableViewCell.h"
#import "SCSettingViewController.h"
#import "WDLinkPayViewController.h"
#import "SCMyOrderViewController.h"
#import "MyPreOrderListsFinalViewController.h"
#import "MyAttentionViewController.h"
#import "WDPersonalIformationViewController.h"
#import "WDProfileViewModel.h"
#import "UserService.h"
#import "PerfectInformationView.h"
#import "SCMessageViewController.h"
#import "HealthCardViewController.h"

static NSString *const PROFILE_LIST_CELL = @"PROFILE_LIST_CELL";

@interface WDProfileViewController ()<UITableViewDataSource, UITableViewDelegate>

@property (nonatomic, strong) WDProfileHeadView *headView;
@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSArray *cellArr;

@end

@implementation WDProfileViewController

- (WDProfileHeadView *)headView {
    if (!_headView) {
        _headView = [WDProfileHeadView new];
        WS(ws)
        _headView.goBlock = ^(){
            if([UserManager manager].isLogin == NO){
                [[VCManager manager]presentLoginViewController:YES];
                return;
            }else{
                WDPersonalIformationViewController *vc = [WDPersonalIformationViewController new];
                vc.hidesBottomBarWhenPushed = YES;
                [ws.navigationController pushViewController:vc animated:YES];
                vc.navigationController.navigationBar.hidden = NO;;
            }
        };
    }
    return _headView;
}

- (UITableView *)tableView {
    if (!_tableView) {
        _tableView = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStylePlain];
        _tableView.dataSource = self;
        _tableView.delegate = self;
        _tableView.scrollEnabled = NO;
        _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        _tableView.backgroundColor = [UIColor clearColor];
        
        [_tableView registerClass:[WDProfileTableViewCell class] forCellReuseIdentifier:PROFILE_LIST_CELL];
    }
    return _tableView;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
    [self requestData];
}


- (void)viewWillAppear:(BOOL)animated {
//    if (self.needHiddenBar) {
        [self.navigationController setNavigationBarHidden:YES animated:animated];
//    }
//    self.needHiddenBar = YES;
    [super viewWillAppear:animated];
    if ([UserManager manager].isLogin ) {
        [[UserService service] refreshLastUserInfoComplete:^(UserInfoModel *model) {
            [self requestData];
        }];
        [[UserService service]updateMessageCount:@{@"uid": [UserManager manager].uid} complete:^{
            
        } failure:^{
            
        }];
    }
    
}

- (void)viewWillDisappear:(BOOL)animated {
//    if (self.needHiddenBar) {
        [self.navigationController setNavigationBarHidden:NO animated:animated];
//    }
//    self.needHiddenBar = YES;

    [super viewWillDisappear:animated];
}


- (void)setupView {
    self.view.backgroundColor = [UIColor bc2Color];
    
    _cellArr = @[
                 @[@{@"title":@"我的关注", @"image":@"icon我的关注"}],
                 @[@{@"title":@"居民健康卡", @"image":@"icon居民健康卡"}, @{@"title":@"我的订单", @"image":@"icon我的订单"},@{@"title": @"我的预约", @"image":@"icon我的预约"}],
                 @[@{@"title":@"帮助与反馈", @"image":@"icon帮助与反馈"}, @{@"title":@"设置", @"image":@"icon设置"}]
                 ];
    
    WS(ws)
    [self.view addSubview:self.headView];
    [self.headView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(ws.view);
        make.height.mas_equalTo(200);
    }];
    
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(ws.headView.mas_bottom);
        make.left.right.bottom.equalTo(ws.view);
    }];
}

- (void)requestData {
    if ([UserManager manager].isLogin) {
        [[UserService service] requestTureNameType:^(SCTrueNameModel *tureNameModel) {
            
        } failure:^(NSError *error) {
            
        }];
    }
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return _cellArr.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 10;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    UIView *headView = [UIView new];
    headView.backgroundColor = [UIColor clearColor];
    return headView;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    NSArray *sectionArr = _cellArr[section];
    return sectionArr.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    WDProfileTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:PROFILE_LIST_CELL forIndexPath:indexPath];
    NSArray *sectionArr = _cellArr[indexPath.section];
    NSDictionary *cellDic = sectionArr[indexPath.row];
    cell.title = [cellDic objectForKey:@"title"];
    cell.imageName = [cellDic objectForKey:@"image"];
    
    if (indexPath.row == (sectionArr.count-1)) {
        cell.isLast = YES;
    } else {
        cell.isLast = NO;
    }
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        //我的关注跳转
        if([UserManager manager].isLogin == YES){
            MyAttentionViewController *vc = [MyAttentionViewController new];
            vc.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:vc animated:YES];
            vc.navigationController.navigationBar.hidden = NO;
            return;
        } else {
            [[VCManager manager]presentLoginViewController:YES];
            return;
        }
    }
    
    if (indexPath.section == 1) {
        switch (indexPath.row) {
            case 0: {
                //居民健身卡跳转
                if([UserManager manager].isLogin == NO){
                    [[VCManager manager]presentLoginViewController:YES];
                    return;
                } else {
                    [PerfectInformationView showPerfectInformationAlertIsSuccess:^(BOOL success) {
                        if (!success) {
                            NSString *idcard = [UserManager manager].idcard;
                            [[UserService service] refreshLastUserInfoComplete:^(UserInfoModel *model) {
                                HealthCardViewController *vc = [HealthCardViewController new];
                                vc.idcard = idcard;
                                vc.name = model.name;
                                vc.hidesBottomBarWhenPushed = YES;
                                [self.navigationController pushViewController:vc animated:YES];
                            }];
                        }
                    }];
                }
            }
                break;
                
            case 1: {
                //我的订单跳转
                if([UserManager manager].isLogin == NO){
                    [[VCManager manager]presentLoginViewController:YES];
                    return;
                } else {
                    SCMyOrderViewController *orderVC = [[SCMyOrderViewController alloc] init];
                    orderVC.hidesBottomBarWhenPushed = YES;
                    [self.navigationController pushViewController:orderVC animated:YES];
                    orderVC.navigationController.navigationBar.hidden = NO;
                }
                break;
            }
            case 2: {
                
                if([UserManager manager].isLogin == NO){
                    [[VCManager manager]presentLoginViewController:YES];
                    return;
                }else{
                    
                    MyPreOrderListsFinalViewController *preOrderVC=[[MyPreOrderListsFinalViewController alloc]init];
                    preOrderVC.navigationController.navigationBar.hidden = NO;
                    preOrderVC.hidesBottomBarWhenPushed=YES;
                    
                    [self.navigationController  pushViewController:preOrderVC animated:YES];

                }
               
            }
                break;
                
            default:
                break;
        }
    }
    
    if (indexPath.section == 2) {
        switch (indexPath.row) {
            case 0: {
                //帮助与反馈跳转
                //appConfig.CommonModel.helpCenter
                NSString *urlStr = [NSString stringWithFormat:@"%@?userId=%@&tel=%@", [TaskManager manager].appConfig.common.helpCenter, [UserManager manager].uid, [UserManager manager].mobile];
                [[BFRouter router] open:urlStr];
                
            }
                break;
                
            case 1: {
                //设置跳转
                    SCSettingViewController *settingVC = [[SCSettingViewController alloc] init];
                    settingVC.hidesBottomBarWhenPushed = YES;
                    [self.navigationController pushViewController:settingVC animated:YES];
                    settingVC.navigationController.navigationBar.hidden = NO;
                //sctest001
                
//                SCMessageViewController *chatController = [[SCMessageViewController alloc] initWithConversationChatter:@"sctest001" conversationType:EMConversationTypeChat];
//                chatController.title = @"团队名称组长姓名";
//                chatController.hidesBottomBarWhenPushed = YES;
//                [self.navigationController pushViewController:chatController animated:YES];
//                chatController.navigationController.navigationBar.hidden = NO;

            }
                break;
                
            default:
                break;
        }
    }
}

@end
