//
//  SCExtractOrdersHistoryView.m
//  SCHCPatient
//
//  Created by Po on 2017/5/8.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "SCExtractOrdersHistoryView.h"

static NSString * const SCExtractOrdersHistoryCellID = @"SCExtractOrdersHistoryCellID";

typedef NSInteger(^HistoryCountBlock)(NSInteger section);
typedef NSString *(^HistoryTitleBlock)(NSIndexPath * indexPath);
typedef void(^HistorySelectedBlock)(NSInteger count);

@interface SCExtractOrdersHistoryView () <UITableViewDelegate, UITableViewDataSource>

@property (copy, nonatomic) HistoryCountBlock countBlock;
@property (copy, nonatomic) HistoryTitleBlock titleBlock;
@property (copy, nonatomic) HistorySelectedBlock selectedBlock;

@end

@implementation SCExtractOrdersHistoryView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        [self initInterface];
    }
    return self;
}

- (void)initInterface {
    [self getTitleLine];
    [self getTitleLabel];
    [self getTabelView];
    
    [self buildConstraints];
}


- (void)buildConstraints {
    __weak typeof(self) weakSelf = self;
    [_titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf);
        make.height.mas_equalTo(50);
        make.width.mas_equalTo(100);
        make.centerX.mas_equalTo(weakSelf);
        
    }];
    
    [_titleLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.center.equalTo(weakSelf.titleLabel);
        make.height.mas_equalTo(1);
        make.width.equalTo(weakSelf);
    }];
    
    
    [_tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.right.equalTo(weakSelf);
        make.top.equalTo(weakSelf.titleLabel.mas_bottom);
    }];
    
    
}
#pragma mark - event

#pragma mark - function
- (void)setDataWithCount:(NSInteger(^)(NSInteger section))countBlock
                   title:(NSString *(^)(NSIndexPath * indexPath))titleBlock
                selected:(void(^)(NSInteger count))selectedBlock {
    _countBlock = countBlock;
    _titleBlock = titleBlock;
    _selectedBlock = selectedBlock;
}
#pragma mark - delegate
#pragma mark - tableView delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (_selectedBlock) {
        _selectedBlock(indexPath.row);
    }
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (_countBlock) {
        return _countBlock(section);
    }
    return 0;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell * cell = [tableView dequeueReusableCellWithIdentifier:SCExtractOrdersHistoryCellID forIndexPath:indexPath];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    return cell;
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (_titleBlock) {
        NSString * title = _titleBlock(indexPath);
        if (title) {
            UITableViewCell * myCell = (UITableViewCell *)cell;
            [myCell setBackgroundColor:[UIColor bc2Color]];
            [myCell.textLabel setTextAlignment:NSTextAlignmentCenter];
            [myCell.textLabel setText:title];
            [myCell.textLabel setTextColor:RGB_COLOR(177, 194, 208)];
            [myCell.textLabel setFont:[UIFont systemFontOfSize:18]];
        }
    }
}

- (CGFloat)tableView:(UITableView *)tableView estimatedHeightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 40;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 40;
}
#pragma mark - notification

#pragma mark - setter

#pragma mark - getter
- (UITableView *)getTabelView {
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        [_tableView setDelegate:self];
        [_tableView setDataSource:self];
        [_tableView setBackgroundColor:[UIColor bc2Color]];
        _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView registerClass:[UITableViewCell class] forCellReuseIdentifier:SCExtractOrdersHistoryCellID];
        [self addSubview:_tableView];
    }
    return _tableView;
}

- (UIView *)getTitleLine {
    if (!_titleLine) {
        _titleLine = [[UIView alloc] init];
        [_titleLine setBackgroundColor:RGB_COLOR(215, 223, 238)];
        [self addSubview:_titleLine];
    }
    return _titleLine;
}

- (UILabel *)getTitleLabel {
    if (!_titleLabel) {
        _titleLabel = [[UILabel alloc] init];
        [_titleLabel setTextColor:RGB_COLOR(38, 38, 38)];
        _titleLabel.backgroundColor = [UIColor bc2Color];
        [_titleLabel setText:@"查询历史"];
        [_titleLabel setFont:[UIFont systemFontOfSize:14]];
        [_titleLabel setTextAlignment:NSTextAlignmentCenter];
        [self addSubview:_titleLabel];
    }
    return _titleLabel;
}
@end
