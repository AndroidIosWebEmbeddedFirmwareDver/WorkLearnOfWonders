//
//  NewAddViewController.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "NewAddViewController.h"
#import "PatientManagerCell.h"
#import "PatientDetailViewController.h"
#import "PatientsListViewModel.h"

@interface NewAddViewController () <UITableViewDelegate,UITableViewDataSource>

@property (nonatomic, strong) UITableView *myTableView;
@property (nonatomic, strong) PatientsListViewModel *viewModel;

@end

@implementation NewAddViewController

- (instancetype)init {
    if (self = [super init]) {
        self.viewModel = [PatientsListViewModel new];
        self.viewModel.tag = New_PATIENT;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"新增患者";
    
    [self setupView];
    [self bindViewModel];
    
    [self requestData];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)setupView {
    
    self.view.backgroundColor = [UIColor bc2Color];
    
    WS(weakSelf)
    
    self.myTableView = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStylePlain withDelegateAndDataSource:self];
    self.myTableView.backgroundColor = [UIColor clearColor];
    [self.myTableView setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    
    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(weakSelf.view);
        make.bottom.equalTo(weakSelf.view);
    }];
    
    [self.myTableView registerClass:[PatientManagerCell class] forCellReuseIdentifier:@"PatientManagerCell"];
    
}

#pragma mark    - bindViewModel
-(void)bindViewModel {
    @weakify(self)
    [RACObserve(self.viewModel, dataArray) subscribeNext:^(NSArray *x) {
        @strongify(self)
        [self.myTableView reloadData];
    }];
}

- (void)requestData {
    [self.viewModel getPatients:^{
        
    } failed:^(NSError *error) {
        
    }];
}

#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.viewModel.dataArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    PatientManagerCell * cell = [tableView dequeueReusableCellWithIdentifier:@"PatientManagerCell"];
    
    cell.model = self.viewModel.dataArray[indexPath.row];
    
    return cell;
    
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return 71;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
    
    PatientDetailViewController *vc = [[PatientDetailViewController alloc] init];
    vc.model = self.viewModel.dataArray[indexPath.row];
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
