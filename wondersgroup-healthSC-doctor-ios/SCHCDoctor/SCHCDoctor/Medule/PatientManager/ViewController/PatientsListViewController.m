//
//  PatientsListViewController.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientsListViewController.h"
#import "PatientManagerCell.h"
#import "PatientsListViewModel.h"

@interface PatientsListViewController () <UITableViewDelegate,UITableViewDataSource>

@property (nonatomic, strong) UITableView *myTableView;
@property (nonatomic, strong) PatientsListViewModel *viewModel;

@end

@implementation PatientsListViewController

- (instancetype)initWithPatientTag:(NSUInteger)tag {
    self = [super init];
    if (self) {
        self.viewModel = [PatientsListViewModel new];
        switch (tag) {
            case 0:
                self.viewModel.tag = ALL_PATIENT;
                break;
            case 1:
                self.viewModel.tag = POOR_PATIENT;
                break;
            case 2:
                self.viewModel.tag = KEY_PATIENT;
                break;
                
            default:
                break;
        }
        
    }
    return self;
}


-(instancetype)init{
    self = [super init];
    if (self) {
        self.viewModel = [PatientsListViewModel new];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupView];
    [self bindViewModel];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
    if (self.viewModel.dataArray.count == 0) {
        [self requestData];
    }
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
    
    if (_selectBlock) {
        _selectBlock(self.viewModel.dataArray[indexPath.row]);
    }
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
