//
//  HealthResultViewController.m
//  HCPatient
//
//  Created by guowenke1 on 15/3/10.
//  Copyright (c) 2015年 wonders. All rights reserved.
//

#import "SCHealthResultViewController.h"
#import "SCHealthResultTableViewCell.h"
@interface SCHealthResultViewController ()

{
    NSArray *arrayTitle;
    NSArray *arrayImage;
    NSArray *arrayAdvise;
    UILabel *noResult;
}

@end

@implementation SCHealthResultViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initViewModel];
    [self bindViewModel];
    [self setupview];
    [self setBackNavgationItem];
     self.edgesForExtendedLayout = UIRectEdgeNone;
    self.view.backgroundColor = [UIColor bc2Color];//UIColorFromRGB(0xececec);
    [self requestData];
 
}


- (void)initViewModel
{
    self.viewModel = [[SCHealthIdentifyViewModel alloc] init];
    self.viewModel.resultStr = self.stringMessage;
    NSLog(@"--传递的参数--%@",self.stringMessage);
    [self.againBtn setCornerRadius:5];
    arrayTitle = [NSArray arrayWithObjects:@"起居",@"饮食",@"保健",@"锻炼",@"情志", nil];
    arrayImage = [NSArray arrayWithObjects:@"icon起居",@"icon饮食",@"icon保健",@"icon锻炼",@"icon情志", nil];
    
}

- (void)setupview
{
    self.tableView = [[UITableView alloc]initWithFrame:CGRectMake(0, 0, 0, 0) style:UITableViewStyleGrouped];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    self.tableView.scrollEnabled = YES;
    self.tableView.backgroundColor = [UIColor clearColor];
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.width.height.equalTo(self.view);
    }];
}

#pragma mark - 获取数据
- (void)requestData {
    [LoadingView showLoadingInView:self.view];
    [self.viewModel postHealthIdentifyWithParams:self.stringMessage:^{
        [self.view hiddenFailView];
        [LoadingView hideLoadinForView:self.view];
        [self.tableView reloadData];
        [self tableReload];
    } failure:^{
        
    }];
}



#pragma mark    - bindViewModel
-(void)bindViewModel {
    WS(weakSelf)
    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *type) {
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
                [weakSelf.view hiddenFailView];
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow) {
            [weakSelf.view showFailView:failType withAction:^{
                [weakSelf requestData];
            }];
        }
    }];

}



-(IBAction)againBtn:(id)sender {
    [self popBack];
}

-(void)setBackNavgationItem{
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"icon_back"] style:UIBarButtonItemStyleDone target:self action:@selector(popBack)];
}

-(void)popBack{
    
    NSUInteger index=[[self.navigationController viewControllers]indexOfObject:self];
    if (index > 2) {
        [self.navigationController popToViewController:[self.navigationController.viewControllers objectAtIndex:index-2]animated:YES];
    }else{
        [self.navigationController popToRootViewControllerAnimated:YES];
    }
}


#pragma mark - UITableViewDataSource, UITableViewDelegate
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{

     SCHealthIdentifyModel *htm = [self.viewModel.dataArray objectAtIndex:indexPath.section];
    if (indexPath.row == 0) {
       
       
        NSString *dailyLife = htm.advice.dailyLife;//htd.dailyLife;
        CGFloat hight = [SCHealthResultTableViewCell calculateCellHeightWithMessage:dailyLife];
        return hight;
 
    }else if (indexPath.row == 1){
      
        NSString *diet = htm.advice.diet;//diet;
        CGFloat hight = [SCHealthResultTableViewCell calculateCellHeightWithMessage:diet];
        return hight;

    }else if (indexPath.row == 2){
       
        NSString *care = htm.advice.care;//care;
        CGFloat hight = [SCHealthResultTableViewCell calculateCellHeightWithMessage:care];
        return hight;

    }else if (indexPath.row == 3){
        
        NSString *exercise = htm.advice.exercise;
        CGFloat hight = [SCHealthResultTableViewCell calculateCellHeightWithMessage:exercise];
        return hight;

    }else{
       
        NSString *emotion = htm.advice.emotion;
        CGFloat hight = [SCHealthResultTableViewCell calculateCellHeightWithMessage:emotion];
        return hight;

    }
}
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return self.viewModel.dataArray.count;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (((SCHealthIdentifyModel *)[self.viewModel.dataArray objectAtIndex:section]).advice == nil || [((SCHealthIdentifyModel *)[self.viewModel.dataArray objectAtIndex:section]).advice isEqual:@""]) {
        return 0;
    }
    return 5;
}
- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    if (((SCHealthIdentifyModel *)[self.viewModel.dataArray objectAtIndex:section]).advice == nil || [((SCHealthIdentifyModel *)[self.viewModel.dataArray objectAtIndex:section]).advice isEqual:@""]) {
        return 115;
    }
    return 170;
}
- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *baseView = [[UIView alloc] init];
    baseView.frame = CGRectMake(0, 0, SCREEN_WIDTH, 170);
    //zytc0
    baseView.backgroundColor = [UIColor tc0Color];
    
//    UIImageView *iconImge = [[UIImageView alloc] init];
//    iconImge.image = [UIImage imageNamed:@"ic_medicine_result"];
//    [baseView addSubview:iconImge];
//    iconImge.backgroundColor = [UIColor clearColor];
//    [iconImge mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(@30);
//        make.top.equalTo(@24);
//        make.width.height.mas_equalTo(25);
//    }];
    
    _healthimageView = [[UIImageView alloc] init];
    _healthimageView.image = [UIImage imageNamed:@"ic_result_bg"];
    [baseView addSubview:_healthimageView];
    _healthimageView.backgroundColor = [UIColor clearColor];
    [_healthimageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(baseView);
        make.height.equalTo(@115);
     }];
    
    UILabel *titleLabel = [[UILabel alloc] init];
    titleLabel.backgroundColor = [UIColor clearColor];
    titleLabel.font = [UIFont systemFontOfSize:14.0];
    titleLabel.text = @"根据中医体质辨识测试题，您的体质类型为:";
    titleLabel.textColor = [UIColor tc2Color];
    [baseView addSubview:titleLabel];
    [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(baseView).offset(15);
        make.top.equalTo(@15);
        make.height.equalTo(@30);
        make.right.equalTo(baseView);
    }];
    
    UILabel *resultLb = [[UILabel alloc] init];
    resultLb.backgroundColor = [UIColor clearColor];
    resultLb.text = ((SCHealthIdentifyModel *)[self.viewModel.dataArray objectAtIndex:section]).physical;
    resultLb.font = [UIFont systemFontOfSize:18.0];
    resultLb.textColor = [UIColor tc5Color];// UIColorFromRGB(0x339966);
    resultLb.textAlignment = NSTextAlignmentCenter;
    [baseView addSubview:resultLb];
    [resultLb mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(baseView);
        make.top.equalTo(titleLabel.mas_bottom).offset(15);
        make.height.equalTo(@35);
        make.right.equalTo(baseView);
    }];
    
//    UILabel *bottomlabel = [[UILabel alloc] init];
//    bottomlabel.text = @"结果仅供参考";
//    bottomlabel.backgroundColor = [UIColor clearColor];
//    bottomlabel.font = [UIFont systemFontOfSize:12.0];
//    bottomlabel.textColor = [UIColor tc3Color];// UIColorFromRGB(0x339966);
//    bottomlabel.textAlignment = NSTextAlignmentCenter;
//    [baseView addSubview:bottomlabel];
//    [bottomlabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(baseView);
//        make.top.equalTo(resultLb.mas_bottom).offset(24);
//        make.height.equalTo(@25);
//        make.right.equalTo(baseView);
//    }];
    
    //下划线
    _lineView = [[UIView alloc] init];
    [baseView addSubview:_lineView];
    _lineView.backgroundColor = [UIColor bc3Color];
    [_lineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(baseView);
        make.top.equalTo(resultLb.mas_bottom).offset(20);
        make.height.equalTo(@0.5);
        make.right.equalTo(baseView);
    }];
    
    if (((SCHealthIdentifyModel *)[self.viewModel.dataArray objectAtIndex:section]).advice == nil || [((SCHealthIdentifyModel *)[self.viewModel.dataArray objectAtIndex:section]).advice isEqual:@""] ) {
        
        
            titleLabel.text = @"您的倾向体质类型为:";
            
            UILabel *bottomlabel = [[UILabel alloc] init];
        if (section == self.viewModel.dataArray.count-1) {
            bottomlabel.text = @"以上结果仅供参考";
        }
            bottomlabel.backgroundColor = [UIColor clearColor];
            bottomlabel.font = [UIFont systemFontOfSize:12.0];
            bottomlabel.textColor = [UIColor tc3Color];// UIColorFromRGB(0x339966);
            bottomlabel.textAlignment = NSTextAlignmentCenter;
            [baseView addSubview:bottomlabel];
            [bottomlabel mas_makeConstraints:^(MASConstraintMaker *make) {
                make.left.equalTo(baseView);
                make.top.equalTo(resultLb.mas_bottom).offset(27);
                make.height.equalTo(@25);
                make.right.equalTo(baseView);
            }];
            return baseView;

    }
    
    UILabel *advicelabel = [[UILabel alloc] init];
    advicelabel.text = @"健康建议";
    advicelabel.backgroundColor = [UIColor clearColor];
    advicelabel.font = [UIFont systemFontOfSize:16.0];
    advicelabel.textColor = [UIColor tc1Color];// UIColorFromRGB(0x339966);
    advicelabel.textAlignment = NSTextAlignmentCenter;
    [baseView addSubview:advicelabel];
    [advicelabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_lineView.mas_bottom).offset(15);
        make.left.right.equalTo(baseView);
        make.height.mas_equalTo(25);
    }];
    
    UILabel *bottomLine = [[UILabel alloc] init];
    bottomLine.backgroundColor = [UIColor bc7Color];
    bottomLine.font = [UIFont systemFontOfSize:16.0];
//    bottomLine.textColor = [UIColor bc6Color];// UIColorFromRGB(0x339966);
   // bottomLine.textAlignment = NSTextAlignmentCenter;
    [baseView addSubview:bottomLine];
    [bottomLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(advicelabel.mas_bottom).offset(2);
        make.centerX.equalTo(advicelabel);
        make.width.mas_equalTo(25);
        make.height.mas_equalTo(2);
    }];
    
    return baseView;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"HealthResultTableViewCell";
    SCHealthResultTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (!cell) {
        cell = [[SCHealthResultTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    }
    
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    
        SCHealthIdentifyModel *htm = [self.viewModel.dataArray objectAtIndex:indexPath.section];
    
        if (indexPath.row == 0) {
            NSString *dailyLife = htm.advice.dailyLife;
            [cell addViewAndMessage:dailyLife title:[arrayTitle objectAtIndex:indexPath.row] image:[arrayImage objectAtIndex:indexPath.row] inter:indexPath.row];
        }else if (indexPath.row == 1){
            NSString *diet = htm.advice.diet;
            [cell addViewAndMessage:diet title:[arrayTitle objectAtIndex:indexPath.row] image:[arrayImage objectAtIndex:indexPath.row] inter:indexPath.row];
        }else if (indexPath.row == 2){
            NSString *care = htm.advice.care;
            [cell addViewAndMessage:care title:[arrayTitle objectAtIndex:indexPath.row] image:[arrayImage objectAtIndex:indexPath.row] inter:indexPath.row];
        }else if (indexPath.row == 3){
            NSString *exercise = htm.advice.exercise;
            [cell addViewAndMessage:exercise title:[arrayTitle objectAtIndex:indexPath.row] image:[arrayImage objectAtIndex:indexPath.row] inter:indexPath.row];
        }else{
            NSString *emotion = htm.advice.emotion;
            [cell addViewAndMessage:emotion title:[arrayTitle objectAtIndex:indexPath.row] image:[arrayImage objectAtIndex:indexPath.row] inter:indexPath.row];
        }
    return cell;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}



- (void)tableReload{
    if (self.viewModel.dataArray == nil || [self.viewModel.dataArray count] == 0) {
        
        UIView *emptyView = [[UIView alloc] init];
        [self.view addSubview:emptyView];
        emptyView.backgroundColor = [UIColor whiteColor];
        [emptyView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.right.top.equalTo(self.view);
            make.height.mas_equalTo(220);
        }];
       
        UILabel *sorryLb = [[UILabel alloc] init];
        [emptyView addSubview:sorryLb];
        sorryLb.backgroundColor = [UIColor clearColor];
        sorryLb.numberOfLines = 0;
        sorryLb.textColor = [UIColor tc2Color];
        sorryLb.font = [UIFont systemFontOfSize:16];
        sorryLb.text = @"对不起,\n您所提交的部分数据不符合医学常理。\n\n\n为保证结果准确性，请您重新测试！";
        [sorryLb mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(emptyView).offset(35);
            make.right.equalTo(emptyView).offset(-35);
            make.top.equalTo(emptyView).offset(35);
        }];
        
        UIButton *startBtn = [UIButton buttonWithType:UIButtonTypeCustom];
        [self.view addSubview:startBtn];
        startBtn.layer.cornerRadius = 5;
        startBtn.layer.borderColor = [[UIColor tc5Color] CGColor];
        startBtn.layer.borderWidth = 0.5;
        startBtn.titleLabel.font = [UIFont systemFontOfSize:16.0];
        startBtn.backgroundColor = [UIColor bc7Color];
        [startBtn mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(emptyView.mas_bottom).offset(60);
            make.left.equalTo(self.view).offset(15);
            make.right.equalTo(self.view).offset(-15);
            make.height.mas_equalTo(44);
        }];
         startBtn.titleLabel.font = [UIFont systemFontOfSize:16.0];
        [startBtn setTitle:@"重新测试" forState:UIControlStateNormal];
        //[startBtn setBackgroundColor:[UIColor bc7Color]];
        [startBtn setTitleColor: [UIColor whiteColor] forState: UIControlStateNormal];
        startBtn.titleLabel.font = [UIFont systemFontOfSize:16.0];
        [[startBtn rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x){
            
            NSUInteger index=[[self.navigationController viewControllers]indexOfObject:self];
            if (index > 2) {
                [self.navigationController popToViewController:[self.navigationController.viewControllers objectAtIndex:index-2]animated:YES];
            }else{
                [self.navigationController popToRootViewControllerAnimated:YES];
            }
            
        }];
        
        return;
    }else{
        if (noResult) {
            [noResult removeFromSuperview];
        }
    }
    
    [_tableView reloadData];
}



- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    return nil;
}


- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section {
    return 0.5;
}

@end
