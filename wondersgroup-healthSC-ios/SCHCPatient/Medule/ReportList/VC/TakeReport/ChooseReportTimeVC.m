//
//  ChooseReportTimeVC.m
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ChooseReportTimeVC.h"
#import "ChooseReportTimerCell.h"
#import "ReportViewModel.h"

#define TABLECELL @"ChooseReportTimerCell"
@interface ChooseReportTimeVC ()<UITableViewDelegate,UITableViewDataSource> {
    NSString *_chooseTime;
    NSInteger _type;
}
@property (strong, nonatomic) ReportViewModel *viewModel;
@property (strong, nonatomic) UITableView *myTableView;
@end

@implementation ChooseReportTimeVC

- (instancetype)init {
    self = [super init];
    if (self) {
        self.viewModel = [[ReportViewModel alloc]init];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"选择时间段";
    
    self.viewModel.type = self.reportCategoryType;

    [self createUI];
    [self RACBind];
    [self requestData];
}

- (void)RACBind {
//    @weakify(self)
    
}


- (void)requestMore {
    
}

- (void)requestData {
    
    [[RACSignal createSignal:^RACDisposable *(id<RACSubscriber> subscriber) {
        [self.viewModel getChooseTimerData:^{
        [self.myTableView reloadData];
            [subscriber sendNext:@(1)];//传到下面订阅信号的block
            [subscriber sendCompleted];
        } failed:^{
            
        }];
         return nil;
    }] subscribeNext:^(id x) {
        [self.myTableView reloadData];
//        [self.myTableView.delegate tableView:self.myTableView didSelectRowAtIndexPath:[NSIndexPath indexPathForItem:self.defalutChoose.integerValue inSection:0]];
    }];
}

- (void)createUI {
    self.myTableView = [[UITableView alloc]initWithFrame:CGRectMake(0, 10, self.view.width, self.view.height) style:UITableViewStylePlain];
    self.myTableView.separatorStyle     = UITableViewCellSeparatorStyleNone;
    self.myTableView.backgroundColor    = [UIColor colorWithHex:0xF6F6F6];
    self.myTableView.delegate           = self;
    self.myTableView.dataSource         = self;
    self.myTableView.scrollEnabled      = NO;
    self.myTableView.estimatedRowHeight = 68.5;
    [self.view addSubview:self.myTableView];
    [self.myTableView registerClass:[ChooseReportTimerCell class] forCellReuseIdentifier:TABLECELL];
    
}

#pragma mark - UITableViewDelegate And dataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.viewModel.dataArray.count==0?3:self.viewModel.dataArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    ChooseReportTimerCell *cell = [tableView dequeueReusableCellWithIdentifier:TABLECELL];
    cell.cellModel            = [self.viewModel.dataArray objectAtIndex:indexPath.row];
    __weak ChooseReportTimerCell *reCell = cell;
    cell.BtnBlockAction  = ^(UIButton *btn){
        for (ChooseReportTimerCell *everyCell in  [tableView visibleCells]) {
            everyCell.chooseBtn.selected = NO;
            everyCell.titleLB.textColor  = [UIColor tc1Color];
        }
        reCell.chooseBtn.selected = YES;
        reCell.titleLB.textColor  = [UIColor bc7Color];
        _chooseTime = reCell.titleLB.text;
        _type = indexPath.row;
        [self back];
    };
    if (indexPath.row == self.defalutChoose.integerValue) {
        cell.chooseBtn.selected = YES;
        reCell.titleLB.textColor  = [UIColor bc7Color];
    }
    if (indexPath.row == self.viewModel.dataArray.count - 1) {
        cell.lineView.hidden = YES;
    }else {
        cell.lineView.hidden = NO;
    }
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    ChooseReportTimerCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    for (ChooseReportTimerCell *everyCell in  [tableView visibleCells]) {
        everyCell.chooseBtn.selected = NO;
        everyCell.titleLB.textColor  = [UIColor tc1Color];
    }
    cell.chooseBtn.selected = YES;
    cell.titleLB.textColor  = [UIColor bc7Color];
    _chooseTime = cell.titleLB.text;
    _type = indexPath.row;
    [self back];
}

- (void)back {
    if (self.chooseTimerBlockAction) {
        self.chooseTimerBlockAction(_chooseTime,_type);
    }
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
//    [self.navigationController.navigationBar setBottomBorderColor:[UIColor whiteColor] height:0.5];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
//    [self.navigationController.navigationBar setBottomBorderColor:[UIColor redColor] height:0.5];
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
