//
//  PatientSearchViewController.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientSearchViewController.h"
#import "PatientManagerCell.h"
#import "PatientSearchView.h"
#import "PatientSeachViewModel.h"
#import "NewAddViewController.h"
#import "PatientDetailViewController.h"

@interface PatientSearchViewController () <UITableViewDelegate,UITableViewDataSource>

@property (nonatomic, strong) PatientSearchView *patientSearchView;
@property (nonatomic, strong) UITableView *myTableView;
@property (nonatomic, strong) PatientSeachViewModel *viewModel;

@end

@implementation PatientSearchViewController

-(instancetype)init{
    self = [super init];
    if (self) {
        self.viewModel = [PatientSeachViewModel new];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"签约患者";
    
    [self setupView];
    [self bindViewModel];

    [self searchWithKeyword:_keyword];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)setupView {
    
    self.view.backgroundColor = [UIColor bc2Color];
    
    UIButton *addButton = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 50, 50)];
    [addButton setImage:[UIImage imageNamed:@"添加患者"] forState:UIControlStateNormal];
    [addButton addTarget:self action:@selector(addButtonAction) forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:addButton];
    
    WS(weakSelf)
    
    _patientSearchView = [[PatientSearchView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 62)];
    _patientSearchView.textField.text = _keyword;
    _patientSearchView.searchActionBlock = ^(NSString *keyword) {
        NSLog(@"keyword:%@",keyword);
        [weakSelf searchWithKeyword:keyword];
    };
    [self.view addSubview:_patientSearchView];
    
    self.myTableView = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStylePlain withDelegateAndDataSource:self];
    self.myTableView.backgroundColor = [UIColor clearColor];
    [self.myTableView setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    
    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.patientSearchView.mas_bottom);
        make.left.right.equalTo(weakSelf.view);
        make.bottom.equalTo(weakSelf.view);
    }];
    
    [self.myTableView registerClass:[PatientManagerCell class] forCellReuseIdentifier:@"PatientManagerCell"];
}

-(void)bindViewModel {
    @weakify(self)
    [RACObserve(self.viewModel, patientsArray) subscribeNext:^(NSArray *x) {
        @strongify(self)
        [self.myTableView reloadData];
    }];
}

- (void)searchWithKeyword:(NSString *)keyword {
    [self.viewModel searchPatientsWithKeyword:keyword Success:^{
        
    } Failed:^(NSError *error) {
        
    }];
}

#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.viewModel.patientsArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    PatientManagerCell * cell = [tableView dequeueReusableCellWithIdentifier:@"PatientManagerCell"];
    
    cell.model = self.viewModel.patientsArray[indexPath.row];
    
    return cell;
    
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return 71;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
    
    PatientDetailViewController *vc = [[PatientDetailViewController alloc] init];
    vc.model = self.viewModel.patientsArray[indexPath.row];
    [self.navigationController pushViewController:vc animated:YES];
}

#pragma mark addButtonAction
- (void)addButtonAction {
    NewAddViewController *vc = [[NewAddViewController alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
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
