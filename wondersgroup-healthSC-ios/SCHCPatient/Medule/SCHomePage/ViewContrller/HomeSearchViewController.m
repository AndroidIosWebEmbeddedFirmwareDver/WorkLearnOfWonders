//
//  HomeSearchViewController.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HomeSearchViewController.h"
#import "CustomSearchBar.h"
#import "MySearchHistroyCell.h"
#import "HotSearchCell.h"
#import "HotSearchViewModel.h"
#import "SearchResultViewController.h"
#import "RecommendModel.h"
#import "SearchRecommendCell.h"
#import "SCSearchModel.h"
#import "DBManager.h"
#import "WDAlertView.h"
#import "SCHospitalModel.h"
#import "SearchHistoryWithHospitalModel.h"
#define HOTSEARCHCELL @"HotSearchCell"
#define MyHISTROYCELL @"MySearchHistroyCell"
#define RECOMMENDCELL @"SearchRecommendCell"
@interface HomeSearchViewController ()<UITableViewDelegate,UITableViewDataSource,UITextFieldDelegate>

@property (nonatomic,strong)UITableView * tableView;
@property (nonatomic,strong)UIView * searchView;
@property (nonatomic,strong)CustomSearchBar *searchBar;
@property (nonatomic,strong)HotSearchViewModel * viewModel;
@end

@implementation HomeSearchViewController
- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self creatSearchView];
    self.navigationController.navigationBar.hidden = NO;
    [self.navigationController.navigationBar addSubview:self.searchView];
    [self.searchBar.textField becomeFirstResponder];
    [self.tableView reloadData];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationController.title  = @"搜索";
    self.viewModel = [[HotSearchViewModel alloc]init];
//  热门搜索此版本去掉的------------
//  [self getHotKeyWord];
    [self setupTableView];
    [self getSearchHistory];

}

#pragma 获取搜索历史 ------------
-(void)getSearchHistory{
    switch (self.type) {
        case HomeSearchType_All:
            [self.viewModel reloadSection];
            [self.tableView reloadData];
            break;
        case HomeSearchType_Hospital:
            [self.viewModel refreshDateWithType:self.viewType];
            [self.tableView reloadData];
            break;
        default:
            break;
    }
    
}
/*
 热门搜索请求 此版本不加这个功能额。。
 -(void)getHotKeyWord{
 [LoadingView showLoadingInView:self.view];
 [self.viewModel getHotSearchKeyWord:^{
 [LoadingView hideLoadinForView:self.view];
 [self.tableView reloadData];
 } failure:^{
 [LoadingView hideLoadinForView:self.view];
 }];
 }
 */
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

- (void)setupTableView {
    WS(ws)
    self.view.backgroundColor = HEX_COLOR(0xf7f9fa);
    self.tableView = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStyleGrouped withDelegateAndDataSource:self];
    self.tableView.backgroundColor = [UIColor clearColor];
    [self.tableView registerClass:[MySearchHistroyCell class] forCellReuseIdentifier:MyHISTROYCELL];
    [self.tableView registerClass:[HotSearchCell class] forCellReuseIdentifier:HOTSEARCHCELL];
    [self.tableView registerClass:[SearchRecommendCell class] forCellReuseIdentifier:RECOMMENDCELL];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(ws.view);
        make.left.equalTo(ws.view);
        make.right.equalTo(ws.view);
    }];
  
}

#pragma mark    - UITableViewDelegate,UITableViewDataSource ---------
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    /*
     搜索提示关联词功能此版本不做 -
     if (self.viewModel.recommendArr.count>0) {
         return self.viewModel.recommendArr.count;
     }else{
     
     }
     */
        switch (self.type) {
            case HomeSearchType_All:
                if (self.viewModel.historyArray.count >=  5) {
                    return 5;
                }
                return self.viewModel.historyArray.count;
                break;
            case HomeSearchType_Hospital:
                if (self.viewModel.historyArrayWithHospital.count >=  5) {
                    return 5;
                }
                return self.viewModel.historyArrayWithHospital.count;
                break;
            default:
                return 1;
                break;
        }
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    /*
     搜索关联词汇
     if (self.viewModel.recommendArr.count>0) {
     return 1;
     }
     */
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
/*
 搜索提示关联词功能没有了
 if (self.viewModel.recommendArr.count>0) {
 SearchRecommendCell * cellRe = [tableView dequeueReusableCellWithIdentifier:RECOMMENDCELL];
 SCHospitalModel * model   = self.viewModel.recommendArr[indexPath.row];
 cellRe.titleLab.text = model.hospitalName;
 NSString *resultStr = model.hospitalName;
 NSMutableAttributedString *attrbutedString = [[NSMutableAttributedString alloc]initWithString:resultStr];
 NSRange range = [resultStr rangeOfString:self.searchBar.textField.text];
 [attrbutedString addAttributes:@{NSForegroundColorAttributeName:[UIColor tc5Color]} range:range];
 cellRe.titleLab.text           = resultStr;
 cellRe.titleLab.attributedText = attrbutedString;
 return cellRe;
 }else{
 
 }
 */
    
        //搜索历史
        MySearchHistroyCell * cell = [tableView dequeueReusableCellWithIdentifier:MyHISTROYCELL];
        
        switch (self.type) {
            case HomeSearchType_All:
            {
                SCSearchModel * model = self.viewModel.historyArray[indexPath.row];
                cell.titleLab.text = model.cat_name;
                
            }
                break;
            case HomeSearchType_Hospital:
            {
                SearchHistoryWithHospitalModel * model = self.viewModel.historyArrayWithHospital[indexPath.row];
                cell.titleLab.text = model.cat_name;
            }
                break;
            default:
                break;
        }
        
        return cell;
        
        /*
         热门搜索功能此版本不加
         HotSearchCell * cell = [tableView dequeueReusableCellWithIdentifier:HOTSEARCHCELL];
         if (self.viewModel.array.count>0) {
         cell.keyWordsArray = self.viewModel.array;
         cell.block = ^(NSInteger index){
         //热门搜索的关键字
         NSString * text = self.viewModel.array[index];
         self.searchBar.textField.text = text;
         [self saveHistoryWithText:text];
         SearchResultViewController * resultVC = [SearchResultViewController new];
         resultVC.searchBarText = text;
         resultVC.type = self.type;
         [self.navigationController pushViewController:resultVC animated:YES];
         NSLog(@"666666666%@",text);
         };
         
         }
         return cell;
         */
    
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    /*
     判断热门搜索 功能 - 此版本没有
     if (indexPath.section == 0) {
     CGFloat height = 121;
     if (self.viewModel.array.count<=3) {
     height = 40;
     }
     if (self.viewModel.array.count>3&&self.viewModel.array.count<=6) {
     height = 80;
     }
     if (self.viewModel.array.count>6) {
     height = 121;
     }
     if (self.viewModel.array.count==0) {
     height = 0;
     }
     
     return height;
     }
     */
    
    return 40;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, 36 + 15)];
    view.backgroundColor = [UIColor clearColor];
    UILabel * titleLab = [UILabel new];
    [view addSubview:titleLab];
    [titleLab mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(view).offset(20);
        make.left.equalTo(view).offset(15);
        make.bottom.equalTo(view).offset(-15);
        make.width.mas_equalTo(100);
    }];
    titleLab.font = [UIFont systemFontOfSize:16];
    titleLab.text = @"我的搜索历史";
    /*
     此版本没有热门搜索啊 - 
     if (section == 0) {
     if (self.viewModel.array.count>0) {
     titleLab.text = @"热门搜索";//此版本没有
     }
     
     }else{
     
     }
     */
    
    switch (self.type) {
        case HomeSearchType_Hospital:
            if (self.viewModel.historyArrayWithHospital.count>0) {
                return view;
            }
            return nil;
            break;
        case HomeSearchType_All:
            if (self.viewModel.historyArray.count>0) {
                return view;
            }
            return nil;
            break;
        default:
            return nil;
            break;
    }
    
}
-(UIView * )tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section{
    WS(ws)
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, 36 + 15)];
    view.backgroundColor = [UIColor whiteColor];
    UIButton * button = [UISetupView setupButtonWithSuperView:view withTitleToStateNormal:@"清除全部历史记录" withTitleColorToStateNormal:[UIColor tc2Color] withTitleFontSize:16 withAction:^(UIButton *sender) {
        
        [self.navigationController.view endEditing:YES];
        WDAlertView *alert = [[WDAlertView alloc]initWithNavigationController:self.navigationController withType:WDAlertViewTypeTwo];
        alert.submitBlock = ^(WDAlertView *alert) {
            NSLog(@"删除历史记录");
            switch (self.type) {
                case HomeSearchType_All:
                    [[DBManager manager]clearAllSearchHistory];
                    [ws.viewModel reloadSection];
                    break;
                case HomeSearchType_Hospital:
                    [[DBManager manager]clearHospitalSearchHistoryWithType:self.viewType];
                    [ws.viewModel refreshDateWithType:self.viewType];
                    break;
                    
                default:
                    break;
            }
            [ws.tableView reloadData];
            [alert dismiss];
        };
        alert.cancelBlock = ^(WDAlertView *alert) {
            [alert dismiss];
        };
        [alert reloadTitle:@"确定清空历史搜索吗？" content:nil];
        [alert showViewWithHaveBackAction:YES withHaveBackView:YES];
    
    }];
    [view addSubview:button];
    [button mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.bottom.equalTo(view);
    }];
    
    switch (self.type) {
        case HomeSearchType_Hospital:
            if (self.viewModel.historyArrayWithHospital.count>0) {
                return view;
            }
            return nil;
            break;
        case HomeSearchType_All:
            if (self.viewModel.historyArray.count>0) {
                return view;
            }
            return nil;
            break;
        default:
            return nil;
            break;
    }
}
- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    
    switch (self.type) {
        case HomeSearchType_All:
            if (self.viewModel.historyArray.count==0) {
                return 0.1;
            }
            return 36 + 15;
            break;
        case HomeSearchType_Hospital:
            if (self.viewModel.historyArrayWithHospital.count==0) {
                return 0.1;
            }
            return 36 + 15;
            break;
            
        default:
            return 36 + 15;
            break;
    }
    /*
     热门搜索的判断 此版本无 -
     switch (section) {
     case 0:
     if (self.viewModel.array.count==0) {
     return 0.1;
     }
     return 36 + 15;
     break;
     case 1:
     
     break;
     
     default:
     return 0.1;
     break;
     }
     */

}
-(CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    
    switch (self.type) {
        case HomeSearchType_All:
            if (self.viewModel.historyArray.count==0) {
                return 0.1;
            }
            return 40;
            break;
        case HomeSearchType_Hospital:
            if (self.viewModel.historyArrayWithHospital.count==0) {
                return 0.1;
            }
            return 40;
            break;
            
        default:
            return 40;
            break;
    }
    
    /*
     热门搜索的判断 此版本无-
     switch (section) {
     case 0:
     
     return 0.1;
     break;
     case 1:
     
     break;
     
     default:
     return 0.1;
     break;
     }
     */
    
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    /*
     搜索 提示关联词 功能此版本无 -
     if (self.viewModel.recommendArr.count>0) {
     SCHospitalModel * model = self.viewModel.recommendArr[indexPath.row];
     self.searchBar.textField.text = model.hospitalName;
     [self saveHistoryWithText:model.hospitalName];
     SearchResultViewController * resultVC = [SearchResultViewController new];
     resultVC.searchBarText = model.hospitalName;
     resultVC.type = HomeSearchType_All;
     [self.navigationController pushViewController:resultVC animated:YES];
     }
     */
    
//我的搜索历史
    switch (self.type) {
        case HomeSearchType_All:
        {
            
            SCSearchModel * model = self.viewModel.historyArray[indexPath.row];
            [self saveHistoryModel:model];
            
            
            self.searchBar.textField.text = model.cat_name;
            SearchResultViewController * resultVC = [SearchResultViewController new];
            resultVC.searchBarText = model.cat_name;
            resultVC.type = self.type;
            [self.navigationController pushViewController:resultVC animated:YES];
            NSLog(@"^^^^^^^^^^^^^^^^%@",model.cat_name);
            
        }
            break;
        case HomeSearchType_Hospital:
        {
            SearchHistoryWithHospitalModel * model = self.viewModel.historyArrayWithHospital[indexPath.row];
            [self saveHistoryHospitalModel:model];
            
            
            self.searchBar.textField.text = model.cat_name;
            SearchResultViewController * resultVC = [SearchResultViewController new];
            resultVC.searchBarText = model.cat_name;
            resultVC.type = self.type;
            [self.navigationController pushViewController:resultVC animated:YES];
            NSLog(@"^^^^^^^^^^^^^^^^%@",model.cat_name);
        }
            break;
            
        default:
            break;
    }
    
}
#pragma TextFieldDelegate -----------
-(BOOL)textFieldShouldReturn:(UITextField *)textField{
    BOOL  isNull = [self checkIsAllEmptyCharacterString:textField.text];
    if (isNull) {
        [MBProgressHUDHelper showHudWithText:@"请输入内容"];
        return NO;
    }
    if (self.searchBar.textField.text.length > 0&&![self.searchBar.textField.text isEqualToString:@" "]) {
        switch (self.type) {
            case HomeSearchType_Hospital:
                [self saveHistoryHospitalWithText:textField.text];
                break;
            case HomeSearchType_All:
                [self saveHistoryWithText:textField.text];
                break;
            default:
                break;
        }
        SearchResultViewController * resultVC = [SearchResultViewController new];
        resultVC.searchBarText = self.searchBar.textField.text;
        resultVC.type = self.type;
        [self.navigationController pushViewController:resultVC animated:YES];
        return YES;
    }else{
       
        [MBProgressHUDHelper showHudWithText:@"请输入内容"];
        
         return YES;
    }
    return NO;
}

//-(BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string{
//    NSLog(@"*************%@",textField.text);
//    NSLog(@"*************%@",string);
//    NSLog(@"*************%ld,%ld",range.location,range.length);
//    NSString * textStr;
//    if ([string isEqualToString:@""]) {
//        textStr = [textField.text substringToIndex:textField.text.length-1];
//    }else{
//        textStr = [NSString stringWithFormat:@"%@%@",textField.text,string];
//    }
//    NSLog(@"*******444******%@",textStr);
//    [LoadingView showLoadingInView:self.view];
//    [self.viewModel getSearchRecommendList:textStr success:^{
//         [LoadingView hideLoadinForView:self.view];
//        [self.tableView reloadData];
//        WS(ws)
//        if (self.viewModel.recommendArr.count>0) {
//            [self.tableView mas_updateConstraints:^(MASConstraintMaker *make) {
//                make.top.bottom.equalTo(ws.view);
//                make.left.equalTo(ws.view);
//                make.right.equalTo(ws.view);
//            }];
//        }
//    } failure:^{
//         [LoadingView hideLoadinForView:self.view];
//    }];
//
//    return YES;
//}
#pragma SaveHistory - 保存搜索医院的历史数据(model) ---------
- (void)saveHistoryHospitalModel:(SearchHistoryWithHospitalModel *)model{
    
    BOOL  isNull = [self checkIsAllEmptyCharacterString:model.cat_name];
    if (isNull) {
        return;
    }
    SearchHistoryWithHospitalModel * searchword = [SearchHistoryWithHospitalModel new];
    searchword.cat_name = model.cat_name;
    searchword.cat_id = model.cat_id;
    searchword.date = [NSDate date];
    searchword.type = self.viewType;
    [[DBManager manager]saveSearchHistoryWithHospital:searchword];
    [self.viewModel refreshDateWithType:self.viewType];
    [self.tableView reloadData];
    
    
    
}
#pragma SaveHospitalHistory - 保存搜索医院的历史数据(Text) ---------
- (void)saveHistoryHospitalWithText:(NSString * )text{
    BOOL  isNull = [self checkIsAllEmptyCharacterString:text];
    if (isNull) {
        return;
    }
    SearchHistoryWithHospitalModel * searchword = [SearchHistoryWithHospitalModel new];
    searchword.cat_name = text;
    searchword.date = [NSDate date];
    searchword.type = self.viewType;
    [[DBManager manager]saveSearchHistoryWithHospital:searchword];
    [self.viewModel refreshDateWithType:self.viewType];
//    [self.tableView reloadData];
    
    
}
#pragma SaveAllHistory - 保存搜索全部的历史数据(model) ---------
- (void)saveHistoryModel:(SCSearchModel *)model{
    
    BOOL  isNull = [self checkIsAllEmptyCharacterString:model.cat_name];
    if (isNull) {
        return;
    }
    SCSearchModel * searchword = [SCSearchModel new];
    searchword.cat_name = model.cat_name;
    searchword.cat_id = model.cat_id;
    searchword.date = [NSDate date];
    [[DBManager manager]setMySearchHistory:searchword];
    [self.viewModel reloadSection];
    [self.tableView reloadData];
}
#pragma SaveAllHistory - 保存搜索全部的历史数据(Text) ---------
- (void)saveHistoryWithText:(NSString * )text{
    BOOL  isNull = [self checkIsAllEmptyCharacterString:text];
    if (isNull) {
        return;
    }
    SCSearchModel * searchword = [SCSearchModel new];
    searchword.cat_name = text;
    searchword.date = [NSDate date];
    [[DBManager manager]setMySearchHistory:searchword];
    [self.viewModel reloadSection];
//    [self.tableView reloadData];
}
#pragma CheckString 检查输入的东西---------
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

- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
    [self.searchBar.textField resignFirstResponder];
    
}
- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    [self.searchBar.textField resignFirstResponder];
    [self.searchView removeFromSuperview];
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
