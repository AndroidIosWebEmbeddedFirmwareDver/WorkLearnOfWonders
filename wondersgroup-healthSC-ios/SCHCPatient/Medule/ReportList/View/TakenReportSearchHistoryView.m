//
//  TakenReportSearchHistoryView.m
//  SCHCPatient
//
//  Created by Gu Jiajun on 2017/5/8.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "TakenReportSearchHistoryView.h"

@interface TakenReportSearchHistoryView () <UITableViewDelegate,UITableViewDataSource>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSMutableArray *showArray;

@end

@implementation TakenReportSearchHistoryView

- (id)init{
    self = [super init];
    if (self) {
        self.showArray = [[NSMutableArray alloc] init];
        
        [self createUI];
        [self bindRAC];
    }
    
    return self;
}

- (void)createUI {
    WS(weakSelf)
    UILabel *titleLbl = [UISetupView setupLabelWithSuperView:self withText:@"查询历史" withTextColor:[UIColor tc1Color] withFontSize:14];
    titleLbl.textAlignment = NSTextAlignmentCenter;
    [titleLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.mas_top).offset(5);
        make.centerX.equalTo(weakSelf.mas_centerX);
        make.width.mas_equalTo(100);
        make.height.mas_equalTo(20);
    }];
    
    UIView *leftLine = [UISetupView setupLineViewWithSuperView:self color:[UIColor dc1Color]];
    [leftLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(titleLbl.mas_centerY);
        make.left.equalTo(weakSelf.mas_left).offset(15);
        make.right.equalTo(titleLbl.mas_left).offset(0);
        make.height.mas_equalTo(1);
    }];
    
    UIView *rightLine = [UISetupView setupLineViewWithSuperView:self color:[UIColor dc1Color]];
    [rightLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(titleLbl.mas_centerY);
        make.left.equalTo(titleLbl.mas_right).offset(15);
        make.right.equalTo(weakSelf.mas_right).offset(0);
        make.height.mas_equalTo(1);
    }];
    
    _tableView                    = [[UITableView alloc]initWithFrame:CGRectMake(0, titleLbl.height+10, self.width, self.height-10-titleLbl.bottom) style:UITableViewStylePlain];
    _tableView.separatorStyle     = UITableViewCellSeparatorStyleNone;
    _tableView.delegate           = self;
    _tableView.dataSource         = self;
    _tableView.backgroundColor    = [UIColor colorWithHex:0xF6F6F6];
    _tableView.rowHeight          = 44.0;
    _tableView.showsVerticalScrollIndicator = NO;
    [self addSubview:_tableView];
    //[_tableView registerClass:[TakenReportTableViewCell class] forCellReuseIdentifier:TABLECELL];
    [_tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(titleLbl.mas_bottom).offset(10);
        make.left.right.equalTo(weakSelf);
        make.bottom.equalTo(weakSelf.mas_bottom);
    }];
}

- (void)bindRAC {
    @weakify(self)
    [RACObserve(self, historyArray) subscribeNext:^(NSArray *x) {
        @strongify(self)
        [self.showArray removeAllObjects];
        if (x.count > 0) {
            NSInteger i = x.count-1;
            while ((self.showArray.count <= 4) && (i >= 0)) {
                [self.showArray addObject:x[i]];
                i--;
            }
            [self.tableView reloadData];
        }
        
    }];
}

#pragma mark tableView
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.showArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView
                             dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc]
                initWithStyle:UITableViewCellStyleDefault
                reuseIdentifier:CellIdentifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell.backgroundColor = [UIColor clearColor];
        
    }
    SCHospitalModel *model = self.showArray[indexPath.row];
    cell.textLabel.text = model.hospitalName;
    cell.textLabel.textAlignment = NSTextAlignmentCenter;
    cell.textLabel.textColor = RGB_COLOR(177, 194, 208);
    cell.textLabel.font = [UIFont systemFontOfSize:18];
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (_historySelectBlock != nil) {
        _historySelectBlock(_showArray[indexPath.row]);
    }
}


@end
