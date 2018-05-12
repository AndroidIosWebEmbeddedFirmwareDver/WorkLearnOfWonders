//
//  SCInhospitalPayViewController.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCInhospitalPayViewController.h"
#import "SCInhospitalPayTableViewCell.h"

static NSString *const INHOSPITAL_PAY_TABLEVIEWCELL = @"INHOSPITAL_PAY_TABLEVIEWCELL";

@interface SCInhospitalPayViewController () <UITableViewDataSource, UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;

@end

@implementation SCInhospitalPayViewController

- (UITableView *)tableView {
    if (!_tableView) {
        _tableView = [UITableView new];
        _tableView.dataSource = self;
        _tableView.delegate = self;
        _tableView.backgroundColor = [UIColor clearColor];
        _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView registerNib:[UINib nibWithNibName:@"SCInhospitalPayTableViewCell" bundle:[NSBundle mainBundle]] forCellReuseIdentifier:INHOSPITAL_PAY_TABLEVIEWCELL];
    }
    return _tableView;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
}

- (void)setupView {
    WS(ws)
    
    self.title = @"诊间支付";
    
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(ws.view);
    }];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 10;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 214;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    SCInhospitalPayTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:INHOSPITAL_PAY_TABLEVIEWCELL forIndexPath:indexPath];
    return cell;
}



@end
