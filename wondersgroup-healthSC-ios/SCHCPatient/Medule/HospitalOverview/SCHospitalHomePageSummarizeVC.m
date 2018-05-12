//
//  SCHospitalHomePageSummarizeVC.m
//  SCHCPatient
//
//  Created by wuhao on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCHospitalHomePageSummarizeVC.h"
#import "SCHospitalOverviewTitleCell.h"
#import "SCHospitalOverviewIntroduceCell.h"
#import "SCHospitalOverviewCellModel.h"
#import "SCHospitalHomePageHeaderCell.h"

static NSString *const kOverviewHeaderCellReuseID = @"kOverviewHeaderCellReuseID";
static NSString *const kOverviewTitleCellReuseID = @"OverviewTitleCellReuseID";
static NSString *const kOverviewIntroduceReuseID = @"OverviewIntroduceReuseID";


@interface SCHospitalHomePageSummarizeVC ()<UITableViewDataSource, UITableViewDelegate>


@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSMutableArray *dataArray;


@property (nonatomic, strong) UIView *customNavBar;
@property (nonatomic, strong) UIView *navBarBgView;
@property (nonatomic, strong) UIButton *navBackBtn;
@property (nonatomic, strong) UILabel *navTitleLabel;
@property (nonatomic, strong) CAGradientLayer *gradientLayer;


@end

@implementation SCHospitalHomePageSummarizeVC


- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    [self.navigationController setNavigationBarHidden:YES animated:YES];
    
    [self setupTableView];
    [self setupCustomNavigationBar];
    [self recombineDataArray];
}


- (void)recombineDataArray {
    [self.dataArray addObject:[SCHospitalOverviewCellModel modelWithCellType:HospitalOverviewCellTypeHeader model:self.hospitalModel]];
    [self.dataArray addObject:[SCHospitalOverviewCellModel modelWithCellType:HospitalOverviewCellTypeTitle model:nil]];
    if (self.hospitalModel.hospitalDesc) {
            [self.dataArray addObject:[SCHospitalOverviewCellModel modelWithCellType:HospitalOverviewCellTypeIntroduce model:self.hospitalModel]];
    }

    [self.tableView reloadData];
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.dataArray.count;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    SCHospitalOverviewCellModel *cellModel = self.dataArray[indexPath.row];
    switch (cellModel.cellType) {
        case HospitalOverviewCellTypeHeader:{
            SCHospitalHomePageHeaderCell *cell = [tableView dequeueReusableCellWithIdentifier:kOverviewHeaderCellReuseID forIndexPath:indexPath];
            [self configCell:cell withIndexPath:indexPath];
            return cell;
            break;
        }
        case HospitalOverviewCellTypeTitle: {
            SCHospitalOverviewTitleCell *cell = [tableView dequeueReusableCellWithIdentifier:kOverviewTitleCellReuseID forIndexPath:indexPath];
            [self configCell:cell withIndexPath:indexPath];
            return cell;
            break;
        }
        case HospitalOverviewCellTypeIntroduce: {
            SCHospitalOverviewIntroduceCell *cell = [tableView dequeueReusableCellWithIdentifier:kOverviewIntroduceReuseID forIndexPath:indexPath];
            [self configCell:cell withIndexPath:indexPath];
            return cell;
            break;
        }
        default:
            break;
    }
    
    return nil;
}


- (void)configCell:(UITableViewCell *)cell withIndexPath:(NSIndexPath *)indexPath {
    SCHospitalOverviewCellModel *cellModel = self.dataArray[indexPath.row];
    
    switch (cellModel.cellType) {
        case HospitalOverviewCellTypeHeader: {
            SCHospitalHomePageHeaderCell *headerCell = (SCHospitalHomePageHeaderCell *)cell;
            headerCell.model = cellModel.model;
            headerCell.bookHidden = YES;
            break;
        }
        case HospitalOverviewCellTypeTitle: {
            break;
        }
        case HospitalOverviewCellTypeIntroduce: {
            SCHospitalOverviewIntroduceCell *introduceCell = (SCHospitalOverviewIntroduceCell *)cell;
            introduceCell.content = self.hospitalModel.hospitalDesc;
            break;
        }
        default:
            break;
    }
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    NSString *identifier = nil;
    SCHospitalOverviewCellModel *cellModel = self.dataArray[indexPath.row];
    switch (cellModel.cellType) {
        case HospitalOverviewCellTypeHeader: {
            return 220;
            break;
        }
        case HospitalOverviewCellTypeTitle: {
            return 60;
            break;
        }
        case HospitalOverviewCellTypeIntroduce: {
            identifier = kOverviewIntroduceReuseID;
            break;
        }
        default:
            break;
    }
    
    return [tableView fd_heightForCellWithIdentifier:identifier configuration:^(id cell) {
        [self configCell:cell withIndexPath:indexPath];
    }];

}


- (void)setupTableView {
    _tableView = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStylePlain];
    _tableView.backgroundColor = [UIColor clearColor];
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    _tableView.clipsToBounds = NO;
    [self registerTableViewCell];
    [self.view addSubview:_tableView];
    
    [_tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(self.view);
        make.bottom.offset(-10);
    }];
}


- (void)registerTableViewCell {
    [_tableView registerClass:[SCHospitalHomePageHeaderCell class] forCellReuseIdentifier:kOverviewHeaderCellReuseID];
    [_tableView registerClass:[SCHospitalOverviewTitleCell class] forCellReuseIdentifier:kOverviewTitleCellReuseID];
    [_tableView registerClass:[SCHospitalOverviewIntroduceCell class] forCellReuseIdentifier:kOverviewIntroduceReuseID];

}


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
    //    gradientLayer.locations = @[@(0.7)];
    
    
    
    _navBackBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [_navBackBtn setImage:[UIImage imageNamed:@"login_back"] forState:UIControlStateNormal];
    [_navBackBtn setImageEdgeInsets:UIEdgeInsetsMake(-10, -10, 10, 10)];
    _navBackBtn.frame = CGRectMake(0, 20, 64, 64);
    [_customNavBar addSubview:_navBackBtn];
    @weakify(self);
    [[_navBackBtn rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x) {
        @strongify(self);
        [self.navigationController popViewControllerAnimated:YES];
    }];
    
    
    _navTitleLabel = [[UILabel alloc] initWithFrame:CGRectMake(64, 20, SCREEN_WIDTH - 64 * 2, 44)];
    _navTitleLabel.text = @"医院概况";
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
    }
    else {
        _navBarBgView.alpha = (offsetY <= h) ? offsetY/h : 1;
        [self setupNavScrollingState];
    }
}


- (void)setupNavTopState {
    _navBarBgView.alpha = 0.0;
    _gradientLayer.opacity = 1.0;
    _navTitleLabel.textColor = [UIColor whiteColor];
    [_navBackBtn setImage:[UIImage imageNamed:@"login_back"] forState:UIControlStateNormal];
}


- (void)setupNavScrollingState {
    _gradientLayer.opacity = 0.0;
    _navTitleLabel.textColor = [UIColor tc1Color];
    [_navBackBtn setImage:[UIImage imageNamed:@"icon_back"] forState:UIControlStateNormal];
}



- (NSMutableArray *)dataArray {
    if (!_dataArray) {
        _dataArray = [NSMutableArray arrayWithCapacity:3];
    }
    return _dataArray;
}
@end
