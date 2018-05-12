//
//  SCAppointmentDoctorDateViewController.m
//  VaccinePatient
//
//  Created by ZJW on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDoctorDateViewController.h"
#import "SCAppointmentDoctorDateCell.h"
#import "SCAppointmentDetailViewController.h"
#import "SCDoctorSchedulingViewController.h"

@interface SCAppointmentDoctorDateTypeModel : NSObject

@property (nonatomic, strong) NSString *date;//日期
@property (nonatomic, strong) NSString *week;//周
@property (nonatomic, assign) NSInteger number;//位置

-(instancetype)initWithDate:(NSString *)date withNumber:(NSInteger)number withWeek:(NSString *)week;

@end

@implementation SCAppointmentDoctorDateTypeModel

-(instancetype)initWithDate:(NSString *)date withNumber:(NSInteger)number withWeek:(NSString *)week{
    self = [super init];
    if (self) {
        self.date = date;
        self.number = number;
        self.week = week;
    }
    return self;
}

@end


@interface SCAppointmentDoctorDateViewController ()<UITableViewDelegate,UITableViewDataSource>

@property (nonatomic, assign) BOOL isLoading;//是否正在加载中,不可点击日期
@property (nonatomic, strong) UITableView *myTableView;
@property (nonatomic, strong) UIView *myTableBackView;//用来显示无数据页面
@property (nonatomic, strong) UIScrollView *topScrollView;
@property (nonatomic, strong) SCAppointmentDoctorDateTypeModel *dateModel;

@end

@implementation SCAppointmentDoctorDateViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {
        self.viewModel = [SCAppointmentDoctorDateViewModel new];
        
    }
    return self;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.isLoading = NO;
    
    [self setupView];
    [self bindViewModel];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark    - setupView
-(void)setupView {
    
    self.view.backgroundColor = [UIColor bc2Color];
    WS(weakSelf)
    
    self.topScrollView = [UISetupView setupScrollViewWithSuperView:self.view withDelegate:self withPagingEnabled:NO];
    [self.topScrollView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(weakSelf.view);
        make.height.mas_equalTo(50);
    }];
    
    self.myTableView = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStylePlain withDelegateAndDataSource:self];
    self.myTableView.backgroundColor = [UIColor clearColor];
    self.myTableView.frame = CGRectMake(0, 50, self.view.width, self.view.height - 64 - 44 - 50);
    
    self.myTableBackView = [UISetupView setupViewWithSuperView:self.view withBackGroundColor:[UIColor clearColor]];
    [self.myTableBackView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(weakSelf.myTableView);
    }];
    self.myTableBackView.hidden = YES;
    
    MJRefreshGifHeader * header = [UIUtility headerRefreshTarget:self action:@selector(reloadData)];
    self.myTableView.mj_header = header;
    
    [self reloadDateData];
    
    [LoadingView showLoadingInView:self.view];
    
}

-(void)setupScrollContentView {
    
    if (self.viewModel.dates.count == 0) {
        return;
    }
    WS(weakSelf)
    for (int i = 0; i < self.viewModel.dates.count; i++) {
        
        UIView *view = [self setupDateViewWithNumber:i withModel:self.viewModel.dates[i]];
        [self.topScrollView addSubview:view];
        [view mas_makeConstraints:^(MASConstraintMaker *make) {
            CGFloat width = 50;
            if (IS_IPHONE_6 || IS_IPHONE_6P) {
                width = 60;
            }
            make.left.mas_equalTo(i*width);
            make.top.bottom.equalTo(weakSelf.topScrollView);
            make.width.mas_equalTo(width);
            make.height.mas_equalTo(50);
            if (i == self.viewModel.dates.count-1) {
                make.right.equalTo(weakSelf.topScrollView);
            }
        }];
    }
    
    self.dateModel = [[SCAppointmentDoctorDateTypeModel alloc]initWithDate:((SCAppointmentDoctorDateModel *)self.viewModel.dates[0]).date withNumber:0 withWeek:((SCAppointmentDoctorDateModel *)self.viewModel.dates[0]).week];
}

-(UIView *)setupDateViewWithNumber:(int)i  withModel:(SCAppointmentDoctorDateModel *)model{
    CGFloat width = 50;
    if (IS_IPHONE_6 || IS_IPHONE_6P) {
        width = 60;
    }

    UIView *dateView = [[UIView alloc]init];
    dateView.frame = CGRectMake(0, 0, width, 50);
    dateView.backgroundColor = [UIColor bc1Color];
    dateView.tag = 100+i;
    
    UIView *bottomLine = [UISetupView setupLineViewWithSuperView:dateView];
    [bottomLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(dateView);
        make.height.mas_equalTo(0.5);
    }];

    if (i == 0) {
        UIView *leftLine = [UISetupView setupLineViewWithSuperView:dateView];
        [leftLine mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.top.bottom.equalTo(dateView);
            make.width.mas_equalTo(0.5);
        }];
    }
    UIView *rightLine = [UISetupView setupLineViewWithSuperView:dateView];
    [rightLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.top.bottom.equalTo(dateView);
        make.width.mas_equalTo(0.5);
    }];

    UILabel *weekLabel = [UISetupView setupLabelWithSuperView:dateView withText:model.week withTextColor:[UIColor tc4Color] withFontSize:12];
    weekLabel.tag = 1000;
    [weekLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(dateView);
        make.top.equalTo(dateView).offset(11);
    }];
    
    UILabel *dateLabel = [UISetupView setupLabelWithSuperView:dateView withText:model.showDate withTextColor:[UIColor tc4Color] withFontSize:12];
    dateLabel.tag = 1001;
    [dateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(dateView);
        make.top.equalTo(weekLabel.mas_bottom);
    }];
    
    if (i == 0) {
        dateView.backgroundColor = [UIColor bc7Color];
        ((UILabel *)[dateView viewWithTag:1000]).textColor = [UIColor tc6Color];
        ((UILabel *)[dateView viewWithTag:1001]).textColor = [UIColor tc6Color];
    }
    
    WS(weakSelf)
    @weakify(dateView)
    dateView.tappedBlock = ^(UITapGestureRecognizer *tap){
        @strongify(dateView)
        if (weakSelf.isLoading) {
            return ;
        }
        UIView *lastView = [weakSelf.topScrollView viewWithTag:100+weakSelf.dateModel.number];
        
        lastView.backgroundColor = [UIColor bc1Color];
        dateView.backgroundColor = [UIColor bc7Color];
        
        ((UILabel *)[lastView viewWithTag:1000]).textColor = [UIColor tc3Color];
        ((UILabel *)[lastView viewWithTag:1001]).textColor = [UIColor tc3Color];
        ((UILabel *)[dateView viewWithTag:1000]).textColor = [UIColor tc6Color];
        ((UILabel *)[dateView viewWithTag:1001]).textColor = [UIColor tc6Color];
        
        weakSelf.dateModel = [[SCAppointmentDoctorDateTypeModel alloc]initWithDate:((SCAppointmentDoctorDateModel *)self.viewModel.dates[i]).date withNumber:i withWeek:((SCAppointmentDoctorDateModel *)self.viewModel.dates[i]).week];
        weakSelf.viewModel.time = weakSelf.dateModel.date;
        [LoadingView showLoadingInView:weakSelf.view];
        [weakSelf reloadData];
    };
    
    return dateView;
}

#pragma mark    - bindViewModel
-(void)bindViewModel {
    WS(weakSelf)
    [RACObserve(self.viewModel, dateRequestCompeleteType) subscribeNext:^(NSNumber *type) {
        if ([type intValue] == 0) {
            return ;
        }
        [LoadingView hideLoadinForView:self.view];
        [weakSelf endRefreshing];
        
        FailViewType failType = FailViewUnknow;
        switch ([type intValue]) {
            case RequestCompeleteEmpty:{
                weakSelf.myTableBackView.hidden = NO;
                [weakSelf.myTableBackView showFailView:FailViewEmpty withAction:^{
                    [weakSelf reloadDateData];
                }];
            }
                break;
            case RequestCompeleteNoWifi:
                failType = FailViewNoWifi;
                break;
            case RequestCompeleteError:
                failType = FailViewError;
                break;
            case RequestCompeleteSuccess: {
                weakSelf.myTableBackView.hidden = YES;
                [weakSelf.view hiddenFailView];
                weakSelf.viewModel.time = ((SCAppointmentDoctorDateModel *)(self.viewModel.dates[0])).date;
                [weakSelf setupScrollContentView];
                [LoadingView showLoadingInView:weakSelf.view];
                [weakSelf reloadData];
                
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow && failType != FailViewEmpty) {
            weakSelf.myTableBackView.hidden = NO;
            [weakSelf.myTableBackView showFailView:failType withAction:^{
                [weakSelf reloadData];
            }];
        }
    }];
    
    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *type) {
        if ([type intValue] == 0) {
            return ;
        }
        [LoadingView hideLoadinForView:self.view];
        [weakSelf endRefreshing];
        
        FailViewType failType = FailViewUnknow;
        switch ([type intValue]) {
            case RequestCompeleteEmpty:{
                weakSelf.myTableBackView.hidden = NO;
                [weakSelf.myTableBackView showFailView:FailViewEmpty withAction:^{
                    [LoadingView showLoadingInView:weakSelf.view];
                    [weakSelf reloadData];
                }];
            }
                break;
            case RequestCompeleteNoWifi:
                failType = FailViewNoWifi;
                break;
            case RequestCompeleteError:
                failType = FailViewError;
                break;
            case RequestCompeleteSuccess: {
                weakSelf.myTableBackView.hidden = YES;
                [weakSelf.view hiddenFailView];
                [weakSelf.myTableView reloadData];
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow && failType != FailViewEmpty) {
            weakSelf.myTableBackView.hidden = NO;
            [weakSelf.myTableBackView showFailView:failType withAction:^{
                [LoadingView showLoadingInView:weakSelf.view];
                [weakSelf reloadData];
            }];
        }
        weakSelf.isLoading = NO;
    }];

    [RACObserve(self.viewModel, hasMore) subscribeNext:^(NSNumber *hasMore) {
        if ([hasMore boolValue]) {
            MJRefreshAutoNormalFooter * footer =[UIUtility footerMoreTarget:self action:@selector(reloadMoreData)];
            weakSelf.myTableView.mj_footer =footer;
        }
        else {
            weakSelf.myTableView.mj_footer =nil;
        }
    }];
}

#pragma mark    - reloadData
-(void)reloadDateData {
    [self.viewModel getAppointmentDate];
}

-(void)reloadData {
    self.isLoading = YES;
    [self.viewModel getAppointmentDoctorWithDate];
}

-(void)reloadMoreData {
    self.isLoading = YES;
    [self.viewModel getAppointmentDoctorMoreWithDate];
}

#pragma mark    - method
-(void)endRefreshing {
    [self.myTableView.mj_header endRefreshing];
    [self.myTableView.mj_footer endRefreshing];
}


#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 1;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return self.viewModel.datas.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"SCAppointmentDoctorDateCell";
    
    SCAppointmentDoctorDateCell * cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if(!cell)
    {
        cell = [[SCAppointmentDoctorDateCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.lineTopHidden = YES;
        cell.lineBottomHidden = NO;
    }
    
    if (self.viewModel.datas.count > indexPath.section) {
        cell.model = self.viewModel.datas[indexPath.section];
    }
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return [SCAppointmentDoctorDateCell cellHeight];
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (self.viewModel.datas.count <= indexPath.section) return;
    
    SCAppointmentDetailModel *model = self.viewModel.datas[indexPath.section];
    
    if ([model.isFull boolValue] == NO) {
        return;
    }
    
    SCAppointmentDetailViewController *vc = [SCAppointmentDetailViewController new];
    model.schedule.weekDay = self.dateModel.week;
    vc.viewModel.model = model;
    
    [self.navigationController pushViewController:vc animated:YES];
}



@end
