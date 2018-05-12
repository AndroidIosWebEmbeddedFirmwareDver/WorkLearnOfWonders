//
//  DoctorDetailContentViewController.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DoctorDetailContentViewController.h"
#import "DoctorDetailContentCell.h"
#import "DoctorDetailContentViewModel.h"


@interface DoctorDetailContentViewController () <UITableViewDelegate, UITableViewDataSource>

@property (nonatomic, strong) UITableView *tableView;

@property (nonatomic, strong) DoctorDetailContentViewModel *viewModel;

//@property (nonatomic, strong) UILabel *contentLabel;

@end

@implementation DoctorDetailContentViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self prepareData];
    [self prepareUI];
}

- (void)prepareData {
    
    self.viewModel = [[DoctorDetailContentViewModel alloc] initWithContent:self.content];
}

- (void)prepareUI {
    
    self.navigationItem.title = @"医生详情";
    self.view.backgroundColor = [UIColor bc2Color];

    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT-64.) style:UITableViewStylePlain];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tableView.tableFooterView = [UIView new];
    self.tableView.backgroundColor = [UIColor bc2Color];
    [self.view addSubview:self.tableView];
}

- (void)viewWillAppear:(BOOL)animated {
    
    [super viewWillAppear:animated];
    self.navigationController.navigationBar.hidden = NO;
}

#pragma mark - tableView delegate & dataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return 1;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return self.viewModel.cellHeight;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"doctorDetailContentCellIdentifier";
    DoctorDetailContentCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[DoctorDetailContentCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    cell.content = self.viewModel.content;
    return cell;
}



@end
