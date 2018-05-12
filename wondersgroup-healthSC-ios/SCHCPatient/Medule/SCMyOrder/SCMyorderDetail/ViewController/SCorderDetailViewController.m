//
//  SCorderDetailViewController.m
//  HCPatient
//
//  Created by wanda on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "SCorderDetailViewController.h"

@interface SCorderDetailViewController ()

@end

@implementation SCorderDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.title = @"订单详情";
    [self initDataArray];
    [self setupview];
    
}

- (void)initDataArray
{
     self.titleArray =  @[@"医院",@"科室",@"医生",@"门诊类型",@"挂号费",@"就诊时间",@"就诊地点"];
}

- (void)setupview
{
    _tableview = [[UITableView alloc] initWithFrame: CGRectZero];
    [_tableview setBackgroundColor: [UIColor bc2Color]];
    [_tableview setSeparatorColor: [UIColor clearColor]];
    _tableview.delegate = self;
    _tableview.dataSource = self;
    [_tableview setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    [self.view addSubview: _tableview];
    _tableview.frame = CGRectMake(0, 0,SCREEN_WIDTH, SCREEN_HEIGHT-64);
    [self setupHeadView];
    [self setupfootView];

}


- (void)setupHeadView
{
    self.headView = [[UIView alloc] init];
    self.headView.backgroundColor = [UIColor whiteColor];
    self.headView.frame = CGRectMake(0,0,SCREEN_WIDTH,102);
    _tableview.tableHeaderView = self.headView;
    
    UIView *orderStateView = [[UIView alloc] init];
    [self.headView addSubview:orderStateView];
    orderStateView.backgroundColor = [UIColor bc2Color];
    [orderStateView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(self.headView);
        make.height.mas_equalTo(44);
    }];
    
    UILabel *orderStateLabel = [[UILabel alloc] init];
    orderStateLabel.backgroundColor = [UIColor clearColor];
    [orderStateView addSubview:orderStateLabel];
    //orderStateLabel.text = @"订单状态: 待就诊(1天以后就诊)";
    orderStateLabel.text = @"订单状态:";
    orderStateLabel.textColor = [UIColor blackColor];
    orderStateLabel.font = [UIFont systemFontOfSize:12.0];
    [orderStateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.centerY.equalTo(orderStateView);
    }];
 
    NSMutableAttributedString *noteStr = [[NSMutableAttributedString alloc] initWithString:orderStateLabel.text];
    NSRange redRange = NSMakeRange(0, [[noteStr string] rangeOfString:@":"].location+1);
    [noteStr addAttribute:NSForegroundColorAttributeName value:[UIColor redColor] range:redRange];
    [orderStateLabel setAttributedText:noteStr] ;
    [orderStateLabel sizeToFit];
    
    UIView *bottomView = [[UIView alloc] init];
    bottomView.backgroundColor = [UIColor bc2Color];
    [self.headView addSubview:bottomView];
    [bottomView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.headView);
        make.top.equalTo(orderStateView.mas_bottom);
    }];
    
    UIView *peopleView = [[UIView alloc] init];
    [bottomView addSubview:peopleView];
    peopleView.backgroundColor = [UIColor whiteColor];
    [peopleView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(bottomView);
        make.height.mas_equalTo(44);
    }];
    
    UILabel *peopleNameLabel = [[UILabel alloc] init];
    peopleNameLabel.text = @"就诊人";
    peopleNameLabel.textColor = [UIColor tc2Color];
    peopleNameLabel.backgroundColor = [UIColor clearColor];
    peopleNameLabel.font = [UIFont systemFontOfSize:16.0];
    [peopleView addSubview:peopleNameLabel];
    [peopleNameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(peopleView).offset(15);
        make.centerY.equalTo(peopleView);
    }];
    
    UILabel *textLabel = [[UILabel alloc] init];
    textLabel.text = @"张小亮";
    textLabel.textColor = [UIColor tc2Color];
    textLabel.backgroundColor = [UIColor clearColor];
    textLabel.font = [UIFont systemFontOfSize:16.0];
    [peopleView addSubview:textLabel];
    [textLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(peopleView).offset(-32);
        make.centerY.equalTo(peopleView);
    }];
}

- (void)setupfootView
{
    self.footView = [[UIView alloc] init];
    self.footView.backgroundColor = [UIColor bc2Color];
    self.footView.frame = CGRectMake(0,0,SCREEN_WIDTH,193);
    _tableview.tableFooterView = self.footView;
    
    UIView *orderNumView = [[UIView alloc] init];
    orderNumView.backgroundColor = [UIColor whiteColor];
    [self.footView addSubview:orderNumView];
    [orderNumView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.footView);
        make.top.mas_equalTo(10);
        make.height.mas_equalTo(44);
    }];
    
    UILabel *orderNumLabel = [[UILabel alloc] init];
    orderNumLabel.text = @"订单号";
    orderNumLabel.textColor = [UIColor tc2Color];
    orderNumLabel.backgroundColor = [UIColor clearColor];
    orderNumLabel.font = [UIFont systemFontOfSize:16.0];
    [orderNumView addSubview:orderNumLabel];
    [orderNumLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(orderNumView).offset(15);
        make.centerY.equalTo(orderNumView);
    }];
    
    UILabel *orderNumtextLabel = [[UILabel alloc] init];
    orderNumtextLabel.text = @"1237897321231242";
    orderNumtextLabel.textColor = [UIColor tc2Color];
    orderNumtextLabel.backgroundColor = [UIColor clearColor];
    orderNumtextLabel.font = [UIFont systemFontOfSize:16.0];
    [orderNumView addSubview:orderNumtextLabel];
    [orderNumtextLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(orderNumView).offset(-32);
        make.centerY.equalTo(orderNumView);
    }];
   
    UIView *bottomView = [[UIView alloc] init];
    bottomView.backgroundColor = [UIColor whiteColor];
    [self.footView addSubview:bottomView];
    [bottomView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.footView);
        make.bottom.equalTo(self.footView);
        make.height.mas_equalTo(64);
    }];
    _tableview.tableFooterView.userInteractionEnabled = YES;
    UIButton *handlebutton = [UIButton buttonWithType:UIButtonTypeCustom];
    handlebutton.userInteractionEnabled = YES;
    handlebutton.layer.cornerRadius = 5;
    handlebutton.layer.borderColor = [[UIColor tc5Color] CGColor];
    handlebutton.layer.borderWidth = 1.0;
    [handlebutton setTitle:@"医生评价" forState:UIControlStateNormal];
    [handlebutton setBackgroundColor:[UIColor bc7Color]];
    handlebutton.titleLabel.font = [UIFont systemFontOfSize:16.0];
    [handlebutton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
    [[handlebutton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x){
        NSLog(@"cccccbbbbb");
    }];
    [bottomView addSubview:handlebutton];
    [handlebutton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.footView).offset(15);
        make.right.equalTo(self.footView).offset(-15);
        make.height.mas_equalTo(44);
        make.centerY.equalTo(bottomView);
    }];
    
    
}

- (void)viewClicked
{
    NSLog(@"123");
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 7;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString *identifier = @"WDResidentIndefier";
         SCOrderDetailCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[SCOrderDetailCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
                }
    cell.titleLabel.text = self.titleArray[indexPath.row];
    if (indexPath.row>2) {
        cell.arrowsView.hidden = YES;
    }
    return  cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return 44;
}

@end
