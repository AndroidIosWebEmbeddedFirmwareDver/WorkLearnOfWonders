//
//  SearchResultViewController.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SearchResultViewController.h"
#import "CustomSearchBar.h"
#import "SearchResultDoctorCell.h"
#import "HotTopicConsultCell.h"
#import "SearchResultViewModel.h"
#import "HospitalsModel.h"
#import "DoctorsModel.h"
#import "ArticlesModel.h"
#import "SCNearbyHospitalCell.h"

#import "AllHospitalsListViewController.h"
#import "AllDoctorsListViewController.h"
#import "AllArticlesViewController.h"
#import "SCHospitalHomePageViewController.h"
#import "DoctorDetailViewController.h"
#import "SCSearchModel.h"
#import "SearchHistoryWithHospitalModel.h"
#import "AllDoctorsListViewController.h"
#import "InspectMoreViewModel.h"
#define DOCTORCELL @"SearchResultDoctorCell.h"
#define HOSPITALCELL @"SCNearbyHospitalCell"
#define HOTTOPICCONSULTCELL @"HotTopicConsultCell"
@interface SearchResultViewController ()<UITextFieldDelegate,UITableViewDelegate,UITableViewDataSource>

@property (nonatomic,strong)UIView * searchView;
@property (nonatomic,strong)CustomSearchBar *searchBar;
@property (nonatomic,strong)UITableView * mySearchTableView;
@property (nonatomic,strong)SearchResultViewModel * viewModel;
@property (nonatomic,strong)InspectMoreViewModel * viewModelList;
@end

@implementation SearchResultViewController

- (void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self creatSearchView];
    self.navigationController.navigationBar.hidden = NO;
    [self.navigationController.navigationBar addSubview:self.searchView];
    [self.searchBar.textField resignFirstResponder];
    [self.mySearchTableView reloadData];
}

- (void)viewWillDisappear:(BOOL)animated{
    [super viewWillDisappear:animated];
    [self.searchBar.textField resignFirstResponder];
    [self.searchView removeFromSuperview];
    
}

#pragma 搜索框UI ------------
- (void)creatSearchView{
    if (self.searchView) return;
    self.searchView  = [[UIView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH , 44.)];
    self.searchView.backgroundColor = [UIColor clearColor];
    switch (self.type) {
        case HomeSearchType_All:
            self.searchBar = [CustomSearchBar customSearbarWithFrame:CGRectMake(0, 0, SCREEN_WIDTH-50, 44.) text:@"" placeholder:@"搜索医院、医生、文章" textFieldDelegate:self];
            break;
        case HomeSearchType_Hospital:
            self.searchBar = [CustomSearchBar customSearbarWithFrame:CGRectMake(0, 0, SCREEN_WIDTH-50, 44.) text:@"" placeholder:@"搜索医院" textFieldDelegate:self];
            break;
            
        default:
            break;
    }
    
    self.searchBar.backgroundColor = [UIColor bc2Color];
    if (self.searchBarText.length>0) {
        self.searchBar.textField.text = self.searchBarText;
    }
    self.searchBar.backgroundColor = [UIColor clearColor];
    [self.searchView addSubview:self.searchBar];
    UIButton *cannelButton = [[UIButton alloc] initWithFrame:CGRectMake(SCREEN_WIDTH-55, 0, 55, 44.)];
    cannelButton.titleLabel.font = [UIFont systemFontOfSize:15.];
    [cannelButton setTitle:@"取消" forState:UIControlStateNormal];
    [cannelButton setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
    cannelButton.backgroundColor = [UIColor clearColor];
    [[cannelButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x) {
        [self.navigationController popViewControllerAnimated:YES];
    }];
    [self.searchView addSubview:cannelButton];
    
}
- (void)viewDidLoad {
    [super viewDidLoad];
    self.viewModel = [[SearchResultViewModel alloc]init];
    self.viewModelList = [[InspectMoreViewModel alloc]init];
    [self getDatas];
    [self setTableView];
    [self bindViewModel];
}
-(void)refreshAllSearchDatas{
    [LoadingView showLoadingInView:self.view];
    [self.viewModel getSearchResultList:self.searchBar.textField.text success:^{
        [LoadingView hideLoadinForView:self.view];
        [self.mySearchTableView reloadData];
    } failure:^(NSError * error){
        [LoadingView hideLoadinForView:self.view];
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
    }];

}
-(void)bindViewModel{
    @weakify(self)
    RACSignal *requestCompeleteTypeSignal = RACObserve(self.viewModel, requestCompeleteType);
    [requestCompeleteTypeSignal subscribeNext:^(NSNumber *type) {
        @strongify(self)
        if ([type intValue] == 0) {
            return ;
        }
//        [LoadingView hideLoadinForView:self.view];
//        [self endRefreshing];
        
        FailViewType failType = FailViewUnknow;
        switch ([type intValue]) {
            case RequestCompeleteEmpty:
                failType = FailViewEmpty;
                break;
            case RequestCompeleteNoWifi:
                failType = FailViewNoWifi;
                break;
            case RequestCompeleteError:
                failType = FailViewError;
                break;
            case RequestCompeleteSuccess: {
                [self.view hiddenFailView];
                [self.mySearchTableView reloadData];
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow) {
            [self.view showFailView:failType withAction:^{
                [self refreshAllSearchDatas];
            }];
            
        }
        
    }];
    
    RACSignal *requestCompeleteTypeSignalList = RACObserve(self.viewModelList, requestCompeleteType);
    [requestCompeleteTypeSignalList subscribeNext:^(NSNumber *type) {
        @strongify(self)
        if ([type intValue] == 0) {
            return ;
        }
        FailViewType failType = FailViewUnknow;
        switch ([type intValue]) {
            case RequestCompeleteEmpty:
                failType = FailViewEmpty;
                break;
            case RequestCompeleteNoWifi:
                failType = FailViewNoWifi;
                break;
            case RequestCompeleteError:
                failType = FailViewError;
                break;
            case RequestCompeleteSuccess: {
                [self.view hiddenFailView];
                [self.mySearchTableView reloadData];
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow) {
            [self.view showFailView:failType withAction:^{
                [self reloadReFresh];
            }];
            
        }
        
    }];
    
    [RACObserve(self.viewModelList, hasMore) subscribeNext:^(NSNumber *x) {
        @strongify(self)
        BOOL more = x.boolValue;
        if (more) {
            self.mySearchTableView.mj_footer = [UIUtility footerMoreTarget:self action:@selector(requestMore)];
        }else {
            self.mySearchTableView.mj_footer = nil;
        }
    }];
  
}
-(void)requestMore{
    [self.viewModelList getMoreHospitalsList:self.searchBarText success:^{
        WS(weakSelf)
        [_mySearchTableView.mj_header endRefreshing];
        [weakSelf.view hiddenFailView];
    } failure:^(NSError * error){
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
    }];


}
-(void)getDatas{

    if (self.type) {
        NSLog(@"%d",self.type);
    }
    switch (self.type) {
        case HomeSearchType_Hospital:
        {
            [LoadingView showLoadingInView:self.view];
            [self.viewModelList getAllHospitalsList:self.searchBarText success:^{
                [LoadingView hideLoadinForView:self.view];
                [self.mySearchTableView reloadData];
            } failure:^(NSError *error){
                [LoadingView hideLoadinForView:self.view];
            }];
            
        }
            break;
        case HomeSearchType_Doctor:
        {
            [LoadingView showLoadingInView:self.view];
            [self.viewModelList getAllDoctorsList:self.searchBarText success:^{
                [LoadingView hideLoadinForView:self.view];
                [self.mySearchTableView reloadData];
            } failure:^(NSError * error){
                [LoadingView hideLoadinForView:self.view];
                [MBProgressHUDHelper showHudWithText:error.localizedDescription];
            }];
            
        }
            break;
        case HomeSearchType_Article:
        {
            [LoadingView showLoadingInView:self.view];
            [self.viewModelList getAllAritlesList:self.searchBarText success:^{
                [LoadingView hideLoadinForView:self.view];
                [self.mySearchTableView reloadData];
            } failure:^(NSError * error){
                [LoadingView hideLoadinForView:self.view];
                [MBProgressHUDHelper showHudWithText:error.localizedDescription];
            }];
        
        
        
        }
            break;
        case HomeSearchType_All:
        {
        
            [LoadingView showLoadingInView:self.view];
            [self.viewModel getSearchResultList:self.searchBarText success:^{
                [LoadingView hideLoadinForView:self.view];
                [self.mySearchTableView reloadData];
            } failure:^(NSError * error){
                [LoadingView hideLoadinForView:self.view];
                [MBProgressHUDHelper showHudWithText:error.localizedDescription];
            }];
        
        
        
        }
            
            break;
            
        default:
            break;
    }
   
}
-(void)reloadReFresh{
    [LoadingView showLoadingInView:self.view];
    [self.viewModelList getAllHospitalsList:self.searchBar.textField.text success:^{
        [LoadingView hideLoadinForView:self.view];
        [self.mySearchTableView.mj_header endRefreshing];
        [self.mySearchTableView reloadData];
    } failure:^(NSError *error){
        [LoadingView hideLoadinForView:self.view];
        [self.mySearchTableView.mj_header endRefreshing];
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
    }];
}
-(void)setTableView{
    self.mySearchTableView = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStyleGrouped withDelegateAndDataSource:self];
    WS(ws)
    [self.mySearchTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.left.right.equalTo(ws.view);
    }];
    [self.mySearchTableView registerClass:[SearchResultDoctorCell class] forCellReuseIdentifier:DOCTORCELL];
    [self.mySearchTableView registerClass:[SCNearbyHospitalCell class] forCellReuseIdentifier:HOSPITALCELL];
    [self.mySearchTableView registerClass:[HotTopicConsultCell class] forCellReuseIdentifier:HOTTOPICCONSULTCELL];
    switch (self.type) {
        case HomeSearchType_All:
            self.mySearchTableView.mj_header = nil;
            break;
        case HomeSearchType_Hospital:
            self.mySearchTableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(reloadReFresh)];
            break;
            
        default:
            break;
      }
}
#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    switch (self.type) {
        case HomeSearchType_Hospital:
        {
        
            return self.viewModelList.dataArray.count;
        
        
        }
            break;
        case HomeSearchType_Doctor:
        {
        
        
            return self.viewModelList.dataDoctorArray.count;
        
        
        }
            
            break;
        case HomeSearchType_Article:
        {
        
            return self.viewModelList.dataArticleArray.count;
        
        
        }
            
            break;
        case HomeSearchType_All:
        {
        
            switch (section) {
                case 0:
                    return self.viewModel.allModel.hospitals.content.count;
                    break;
                case 1:
                    return self.viewModel.allModel.doctors.content.count;
                    break;
                case 2:
                    return self.viewModel.allModel.articles.content.count;
                    
                    break;
                default:
                    return 1;
                    break;
            }
            return 2;
        
        
        
        }
            break;
            
        default:
            return 1;
            break;
    }
    
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    switch (self.type) {
        case HomeSearchType_Hospital:
            return 1;
            break;
        case HomeSearchType_Doctor:
            return 1;
            break;
        case HomeSearchType_Article:
            return 1;
            break;
        case HomeSearchType_All:
            return 3;
            break;
            
        default:
            return 1;
            break;
    }

}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    switch (self.type) {
        case HomeSearchType_Hospital:
        {
        
            return [self setHospitalTableViewCell:tableView withIndex:indexPath];
        
        }
            break;
        case HomeSearchType_Doctor:
        {
            [self setDoctorsTableViewCell:tableView withIndex:indexPath];
        
        
        }

            break;
        case HomeSearchType_Article:
        {
        
            return [self setArticlesTableViewCell:tableView withIndex:indexPath];
        
        
        }
 
            break;
        case HomeSearchType_All:
        {
        
            return [self setAllTableViewCell:tableView withIndex:indexPath];
        
        }
    
            break;
            
        default:
            return nil;
            break;
    }
    
    return nil;
}
-(UITableViewCell * )setArticlesTableViewCell:(UITableView *)tableView withIndex:(NSIndexPath *)indexPath{
    HotTopicConsultCell * cell = [tableView dequeueReusableCellWithIdentifier:HOTTOPICCONSULTCELL];
    cell.model = self.viewModelList.dataArticleArray[indexPath.row];
    cell.highLightString = self.searchBar.textField.text;
    return cell;

}
-(UITableViewCell * )setDoctorsTableViewCell:(UITableView *)tableView withIndex:(NSIndexPath *)indexPath{
    SearchResultDoctorCell * cell = [tableView dequeueReusableCellWithIdentifier:DOCTORCELL];
    cell.model = self.viewModelList.dataDoctorArray[indexPath.row];
    cell.highLightString = self.searchBar.textField.text;
    return cell;

}
-(UITableViewCell * )setHospitalTableViewCell:(UITableView *)tableView withIndex:(NSIndexPath *)indexPath{
    SCNearbyHospitalCell * cell = [tableView dequeueReusableCellWithIdentifier:HOSPITALCELL];
    SCHospitalModel * model = self.viewModelList.dataArray[indexPath.row];
    cell.model = model;
    cell.highLightString = self.searchBar.textField.text;
    return cell;

}
-(UITableViewCell * )setAllTableViewCell:(UITableView *)tableView withIndex:(NSIndexPath *)indexPath{
    switch (indexPath.section) {
        case 0:
        {
            SCNearbyHospitalCell * cell = [tableView dequeueReusableCellWithIdentifier:HOSPITALCELL];
            cell.model =  self.viewModel.allModel.hospitals.content[indexPath.row];
            cell.highLightString = self.searchBar.textField.text;
            
            return cell;
        }
            break;
        case 1:
        {
            SearchResultDoctorCell * cell = [tableView dequeueReusableCellWithIdentifier:DOCTORCELL];
            cell.model = self.viewModel.allModel.doctors.content[indexPath.row];
            cell.highLightString = self.searchBar.textField.text;
            return cell;
        }
            break;
        case 2:
        {
            HotTopicConsultCell * cell = [tableView dequeueReusableCellWithIdentifier:HOTTOPICCONSULTCELL];
            cell.model = self.viewModel.allModel.articles.content[indexPath.row];
            cell.highLightString = self.searchBar.textField.text;
            return cell;
        }
            break;
        default:
            return nil;
            break;
    }


}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return 111;
    
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    
    UIView * bgView = [UIView new];
    UIView * view = [[UIView alloc]init];
    view.backgroundColor = [UIColor whiteColor];
    [bgView addSubview:view];
    [view mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(bgView);
        make.top.equalTo(bgView).offset(8);
        make.bottom.equalTo(bgView).offset(-1);
    }];
    
    UILabel * titleLab = [UILabel new];
    titleLab.textColor  = [UIColor tc2Color];
    [view addSubview:titleLab];
    titleLab.font = [UIFont systemFontOfSize:12];
    [titleLab mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(view).offset(15);
        make.top.equalTo(view);
        make.bottom.equalTo(view);
        make.width.mas_equalTo(@50);
    }];
    UILabel * areaLabel = [UILabel new];
    [view addSubview:areaLabel];
    areaLabel.font = [UIFont systemFontOfSize:12];
    [areaLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(view).offset(-15);
        make.top.bottom.equalTo(titleLab);
        make.width.mas_equalTo(SCREEN_WIDTH/2-15);
    }];
    areaLabel.textAlignment = NSTextAlignmentRight;
    if (self.type == HomeSearchType_All) {
        switch (section) {
            case 0:
                if (self.viewModel.allModel.hospitals.content.count>0) {
                    titleLab.text = @"相关医院";
                    return bgView;
                }
                return nil;
                break;
            case 1:
                if (self.viewModel.allModel.doctors.content.count>0) {
                    titleLab.text = @"相关医生";
                    return bgView;
                }
                return nil;
                break;
            case 2:
                if (self.viewModel.allModel.articles.content.count>0) {
                    titleLab.text = @"相关文章";
                    return bgView;
                }
                return nil;
                break;
                
            default:
                return nil;
                break;
        }
    }
    
    return nil;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {

    switch (section) {
        case 0:
            //                相关医院
            if (self.viewModel.allModel.hospitals.content.count>0 && self.type == HomeSearchType_All) {
               return 32 + 8;
            }
            return 0.1f;
            break;
        case 1:
            //                相关医生
            if (self.viewModel.allModel.doctors.content.count>0 && self.type == HomeSearchType_All) {
               return 32 + 8;
            }
            return 0.1f;
            break;
        case 2:
            //                相关文章
            if (self.viewModel.allModel.articles.content.count>0 && self.type == HomeSearchType_All) {
               return 32 + 8;
            }
            return 0.1f;
            break;
            
        default:
            return 0.1;
            break;
    }

}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    
    switch (self.type) {
        case HomeSearchType_Hospital:
            // 进入医院详情
        {
            SCHospitalModel * hosModel =  self.viewModelList.dataArray[indexPath.row];
            SCHospitalHomePageViewController * hospital = [SCHospitalHomePageViewController new];
            hospital.hospitalID = hosModel.hospitalId;
            [self.navigationController pushViewController:hospital animated:YES];
        
        
        }
            
            break;
        case HomeSearchType_Doctor:
            //进入医生详情
        {
            
        DoctorssModel *  docModel = self.viewModelList.dataDoctorArray[indexPath.row];
            DoctorDetailViewController * doctorVC = [DoctorDetailViewController new];
            doctorVC.showRegister = YES;
            doctorVC.hospitalCode = docModel.hosOrgCode;
            doctorVC.hosDeptCode = docModel.hosDeptCode;
            doctorVC.hosDoctCode = docModel.hosDoctCode;
            [self.navigationController pushViewController:doctorVC animated:YES];
            
        }
            
            break;
        case HomeSearchType_Article:
        {
            //进入文章
        ArticlessModel * model  = self.viewModelList.dataArticleArray[indexPath.row];
            [[BFRouter router]open:model.url];
        }
            break;
        case HomeSearchType_All:
            if (indexPath.section == 0) {
            //医院详情
                SCHospitalModel * hosModel =  self.viewModel.allModel.hospitals.content[indexPath.row];
                SCHospitalHomePageViewController * hospital = [SCHospitalHomePageViewController new];
                hospital.hospitalID = hosModel.hospitalId;
                [self.navigationController pushViewController:hospital animated:YES];
                
            }else if(indexPath.section == 1){
            //医生详情
                DoctorssModel *  docModel = self.viewModel.allModel.doctors.content[indexPath.row];
                DoctorDetailViewController * doctorVC = [DoctorDetailViewController new];
                doctorVC.showRegister = YES;
                doctorVC.hospitalCode = docModel.hosOrgCode;
                doctorVC.hosDeptCode = docModel.hosDeptCode;
                doctorVC.hosDoctCode = docModel.hosDoctCode;
                [self.navigationController pushViewController:doctorVC animated:YES];
                
            }else{
             //文章
            ArticlessModel * model  = self.viewModel.allModel.articles.content[indexPath.row];
            [[BFRouter router]open:model.url];

            }
            
            break;
            
        default:
            
            break;
    }
    
}
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    switch (section) {
        case 0:
            //                更多相关医院
            if ([self.viewModel.allModel.hospitals.more intValue]==1 && self.type == HomeSearchType_All) {
                return 44 + 8;
            }
            return 0.1f;
            break;
        case 1:
            //                更多相关医生原
            if ([self.viewModel.allModel.doctors.more intValue]==1 && self.type == HomeSearchType_All) {
                return 44 + 8;
            }
            return 0.1f;
            break;
        case 2:
            //                更多相关文章
            if ([self.viewModel.allModel.articles.more intValue]==1 && self.type == HomeSearchType_All) {
                return 44 + 8;
            }
            return 0.1f;
            break;
            
        default:
            return 0.1;
            break;
    }
}
- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section{
    UIView * bgView = [[UIView alloc]init];
    UIView * view = [UIView new];
    view.backgroundColor = [UIColor whiteColor];
    [bgView addSubview:view];
    
    [view mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(bgView);
        make.bottom.equalTo(bgView).offset(-8);
        
    }];
    UIButton * inspectBtn = [UISetupView setupButtonWithSuperView:view withTitleToStateNormal:@"" withTitleColorToStateNormal:[UIColor tc3Color] withTitleFontSize:14 withAction:^(UIButton *sender) {
        switch (section) {
            case 0:
//                查看更多相关医院
            {
            
                AllHospitalsListViewController * hosVC = [AllHospitalsListViewController new];
                hosVC.keyWord = self.searchBarText;
                [self.navigationController pushViewController:hosVC animated:YES];
            
            }
                
                break;
            case 1:
//                查看更多相关医生
            {
                AllDoctorsListViewController * docVC = [AllDoctorsListViewController new];
                docVC.keyWord = self.searchBarText;
                [self.navigationController pushViewController:docVC animated:YES];
            
            }
                break;
            case 2:
//                查看更多相关文章
            {
                AllArticlesViewController * artVC = [AllArticlesViewController new];
                artVC.keyWord = self.searchBarText;
                [self.navigationController pushViewController:artVC animated:YES];
            }
                break;
                
            default:
                break;
        }
        
    }];
    [inspectBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.left.right.equalTo(view);
    }];
    
    UILabel * titleLab = [UILabel new];
    titleLab.textColor = [UIColor tc2Color];
    titleLab.font = [UIFont systemFontOfSize:14];
    [inspectBtn addSubview:titleLab];
    [titleLab mas_makeConstraints:^(MASConstraintMaker *make) {
        make.center.equalTo(inspectBtn);
        make.height.mas_equalTo(@14);
        make.width.equalTo(inspectBtn);
    }];
    titleLab.textAlignment = NSTextAlignmentCenter;
    
    if (self.type == HomeSearchType_All) {
        switch (section) {
            case 0:
                if (self.viewModel.allModel.hospitals.content.count>0&&[self.viewModel.allModel.hospitals.more intValue] == 1) {
                    titleLab.text = @"查看更多相关医院";
                    return bgView;
                }
                return nil;
                break;
            case 1:
                if (self.viewModel.allModel.doctors.content.count>0&&[self.viewModel.allModel.doctors.more intValue] == 1) {
                    titleLab.text = @"查看更多相关医生";
                    return bgView;
                }
                return nil;
                break;
            case 2:
                if (self.viewModel.allModel.articles.content.count>0&&[self.viewModel.allModel.articles.more intValue] == 1) {
                    titleLab.text = @"查看更多相关文章";
                    return bgView;
                }
                return nil;
                break;
                
            default:
                return nil;
                break;
        }
    }
    
    return nil;
}
- (BOOL)checkIsAllEmptyCharacterString:(NSString *)checkString;
{
    NSString *checkContentMsg = checkString;
    checkContentMsg = [checkContentMsg stringByReplacingOccurrencesOfString:@" " withString:@""];
    checkContentMsg = [checkContentMsg stringByReplacingOccurrencesOfString:@"\n" withString:@""];
    checkContentMsg = [checkContentMsg stringByReplacingOccurrencesOfString:@"\t" withString:@""];
    
    if (checkContentMsg == nil || [checkContentMsg isEqualToString:@""]) {
        return YES;
    }
    return NO;
}
-(BOOL)textFieldShouldReturn:(UITextField *)textField{
    BOOL  isNull = [self checkIsAllEmptyCharacterString:textField.text];
    if (isNull) {
        [MBProgressHUDHelper showHudWithText:@"请输入内容"];
        return NO;
    }
    if (self.searchBar.textField.text.length > 0&&![self.searchBar.textField.text isEqualToString:@" "]) {
        [self.searchBar.textField resignFirstResponder];
        switch (self.type) {
            case HomeSearchType_Hospital:
            {
                [LoadingView showLoadingInView:self.view];
                [self.viewModelList getAllHospitalsList:textField.text success:^{
                    [LoadingView hideLoadinForView:self.view];
                    [self.mySearchTableView reloadData];
                } failure:^(NSError *error){
                    [LoadingView hideLoadinForView:self.view];
                    [MBProgressHUDHelper showHudWithText:error.localizedDescription];
                }];
            
            
            }
                break;
            case HomeSearchType_All:
            {
            
                [LoadingView showLoadingInView:self.view];
                [self.viewModel getSearchResultList:textField.text success:^{
                    [LoadingView hideLoadinForView:self.view];
                    
                    [self.mySearchTableView reloadData];
                    self.searchBarText = textField.text;
                } failure:^(NSError * error){
                    [LoadingView hideLoadinForView:self.view];
                    [MBProgressHUDHelper showHudWithText:error.localizedDescription];
                }];
            
            }
                break;
                
            default:
                break;
        }
    }else{
    
        [MBProgressHUDHelper showHudWithText:@"请输入内容"];
    
    }
    return NO;
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
    
    [self.searchBar.textField resignFirstResponder];
    
    
}
//-(BOOL)textFieldShouldBeginEditing:(UITextField *)textField{
//
//    [self.searchBar.textField becomeFirstResponder];
//    return YES;
//    
//}
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
