//
//  HomeAreaSearchViewController.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HomeAreaSearchViewController.h"
#import "AreaModel.h"
#import "AreaCell.h"
#import "LocationManager.h"
static NSString *const INDEX_RATIO_CELL_REUSEID = @"AreaCell";
@interface HomeAreaSearchViewController ()<UITableViewDelegate,UITableViewDataSource,UIScrollViewDelegate>{
    UITableView *_myLeftTabel;
    UITableView *_myRightTabel;
}
@end

@implementation HomeAreaSearchViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationController.navigationBar.hidden = NO;
    self.viewModel = [[HomeAreaViewModel alloc]init];
    [self getDatas];
    [self setupView];
    [self bindViewModel];
}
-(void)getDatas{
    
    [LoadingView showLoadingInView:self.view];
    
    [self.viewModel getAllCityDatas:^{
     NSLog(@"_________________%ld",(unsigned long)self.viewModel.rightSelectArray.count);
    [LoadingView hideLoadinForView:self.view];
    [_myRightTabel reloadData];
    } failure:^(NSError * error){
    [LoadingView hideLoadinForView:self.view];
    [MBProgressHUDHelper showHudWithText:error.localizedDescription];
    }];


}
#pragma mark    - setupView-------
-(void)setupView {
    self.navigationItem.title = @"选择您的城市";
    self.view.backgroundColor = [UIColor bc2Color];
    UITableView *tableViewL = [[UITableView alloc] initWithFrame: CGRectMake(0,0, SCREEN_WIDTH/3, self.view.frame.size.height)style: UITableViewStyleGrouped];
    tableViewL.delegate      = self;
    tableViewL.dataSource    = self;
    [tableViewL setBackgroundColor: [UIColor bc2Color]];
    [tableViewL setSeparatorColor: [UIColor clearColor]];
    [tableViewL setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    [self.view addSubview: tableViewL];
    _myLeftTabel = tableViewL;
    WS(weakSelf)
    [_myLeftTabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.view).offset(48);
        make.bottom.equalTo(weakSelf.view);
        make.left.equalTo(weakSelf.view);
        make.width.mas_equalTo(SCREEN_WIDTH/3);
    }];
    UITableView *tableViewR = [[UITableView alloc] initWithFrame: CGRectMake(0, 0, SCREEN_WIDTH/3, self.view.frame.size.height)style: UITableViewStyleGrouped];
    tableViewR.delegate      = self;
    tableViewR.dataSource    = self;
    [tableViewR setBackgroundColor: [UIColor whiteColor]];
    [tableViewR setSeparatorColor: [UIColor clearColor]];
    [tableViewR setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    [self.view insertSubview: tableViewR aboveSubview: tableViewL];
    _myRightTabel = tableViewR;
    
    [_myRightTabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.view).offset(48);
        make.bottom.equalTo(weakSelf.view);
        make.left.equalTo(tableViewL.mas_right);
        make.right.equalTo(weakSelf.view);
    }];
    
    UIView *rightBG = [[UIView alloc] init];
    [rightBG setBackgroundColor: [UIColor bc1Color]];
    [self.view insertSubview: rightBG belowSubview: _myRightTabel];
    [rightBG mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_myRightTabel);
        make.bottom.equalTo(_myRightTabel);
        make.left.equalTo(_myRightTabel);
        make.right.equalTo(_myRightTabel);
    }];
    
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, 40)];
    view.backgroundColor = [UIColor bc2Color];
    [self.view addSubview:view];
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(popWithCityNameAndCityCode)];
    [view addGestureRecognizer:tap];
    
    [view mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.width.equalTo(self.view);
        make.height.mas_equalTo(48);
    }];
    
    UILabel *titleLabel  = [[UILabel alloc] init];
    titleLabel.textColor = [UIColor tc1Color];
    titleLabel.font      = [UIFont systemFontOfSize:12];
    [titleLabel sizeToFit];
    [view addSubview:titleLabel];
    [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(view).offset(15);
        make.top.equalTo(view).offset(15);
    }];
    titleLabel.font = [UIFont systemFontOfSize:16];
    
    if ([LocationManager isEnableLocation]==YES) {
        [RACObserve([LocationModel Instance],areaName) subscribeNext:^(NSString  * name) {
            if (name) {
                titleLabel.text = [NSString stringWithFormat:@"%@   GPS定位",name];
            }
    
        }];
    }else{
        [view removeFromSuperview];
        [_myRightTabel mas_updateConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(weakSelf.view);
            make.bottom.equalTo(weakSelf.view);
            make.left.equalTo(tableViewL.mas_right);
            make.right.equalTo(weakSelf.view);
        }];
        [_myLeftTabel mas_updateConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(weakSelf.view);
            make.bottom.equalTo(weakSelf.view);
            make.left.equalTo(weakSelf.view);
            make.width.mas_equalTo(SCREEN_WIDTH/3);
        }];
        
    
    }
 
}

#pragma pop - 选择GPS定位城市返回 ----------------
-(void)popWithCityNameAndCityCode{
    if ([LocationManager isEnableLocation]==YES) {
        
        LocationModel * model = [LocationModel Instance];

            if (model.areaName) {
                
                if (self.block) {
                    self.block(model.areaName,model.areaCode);
                }
                 [self.navigationController popViewControllerAnimated:YES];
            }
    }

}

#pragma mark    - bindViewModel ----------
-(void)bindViewModel {
    @weakify(self)
    RACSignal *requestCompeleteTypeSignal = RACObserve(self.viewModel, requestCompeleteType);
    [requestCompeleteTypeSignal subscribeNext:^(NSNumber *type) {
        @strongify(self)
        if ([type intValue] == 0) {
            return ;
        }
        [LoadingView hideLoadinForView:self.view];
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
                [_myRightTabel reloadData];
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow) {
            [self.view showFailView:failType withAction:^{
                [self getDatas];
            }];
        }
    }];
    
}

#pragma mark    - UITableViewDelegate,UITableViewDataSource -----------------
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (tableView == _myLeftTabel) {
        return 1;
    }
   
    return self.viewModel.rightSelectArray.count;
    NSLog(@"_________________%ld",(unsigned long)self.viewModel.rightSelectArray.count);
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (tableView == _myLeftTabel) {
        AreaCell * cell =[tableView dequeueReusableCellWithIdentifier:INDEX_RATIO_CELL_REUSEID];
        if(!cell)
        {
            cell = [[AreaCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:INDEX_RATIO_CELL_REUSEID];
            cell.backgroundColor = [UIColor bc2Color];
            [cell setSelectionStyle: UITableViewCellSelectionStyleDefault];
            [cell setSelectedBackgroundView:[self loadSelectedView]];

        }
        cell.titlehead.text = @"四川省";
        if (indexPath.row == 0) {
            cell.titlehead.backgroundColor = [UIColor whiteColor];
            cell.backgroundColor = [UIColor whiteColor];
            cell.titlehead.textColor = [UIColor tc5Color];
            
        }
        return cell;
    }
    
    static NSString *cellIdentifier = @"RightTableViewCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier: cellIdentifier];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle: UITableViewCellStyleDefault reuseIdentifier: cellIdentifier];
        [cell setSelectionStyle: UITableViewCellSelectionStyleNone];
        [cell.textLabel setTextColor:[UIColor tc2Color]];
        [cell.textLabel setFont: [UIFont systemFontOfSize: 14.0]];
        [cell setBackgroundColor: [UIColor clearColor]];
    }
   AreaModel * model = self.viewModel.rightSelectArray[indexPath.row];
    cell.textLabel.text = model.cityName;
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if(tableView == _myLeftTabel) {
//        self.viewModel.selectedIndex = indexPath.row;
        [_myRightTabel reloadData];
    }else {
        //选择城市后返回
         AreaModel * model = self.viewModel.rightSelectArray[indexPath.row];
        if (self.block) {
          
            self.block(model.cityName,model.cityCode);
        }
        
        [self.navigationController popViewControllerAnimated:YES];
        
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 40.0;
}


- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 0.5;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section {
    return 0.5;
}

#pragma SelectedBackgroundView - cell点击后的view-----
- (UIView *)loadSelectedView {
    UIView *view        = [[UIView alloc] init];
    UIView *lineTop     = [[UIView alloc] init];
    UIView *lineBottom  = [[UIView alloc] init];
    
    [lineTop    setBackgroundColor: [UIColor dc1Color]];
    [lineBottom setBackgroundColor: [UIColor dc1Color]];
    [view       setBackgroundColor: [UIColor bc1Color]];
    
    [view addSubview: lineTop];
    [lineTop mas_makeConstraints:^(MASConstraintMaker *make) {
        make.height.mas_equalTo(0.5);
        make.left.equalTo(view);
        make.right.equalTo(view);
        make.top.equalTo(view);
    }];
    
    [view addSubview: lineBottom];
    [lineBottom mas_makeConstraints:^(MASConstraintMaker *make) {
        make.height.mas_equalTo(0.5);
        make.left.equalTo(view);
        make.right.equalTo(view);
        make.bottom.equalTo(view);
    }];
    
    return view;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}
-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    return nil;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    return nil;
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
