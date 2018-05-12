//
//  RJSegmentView.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/5.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "RJSegmentView.h"

typedef void(^RJSegmentViewSelectBlock)(NSInteger count, UIButton * currentButton);
@interface RJSegmentView () <UIScrollViewDelegate>


@property (strong, nonatomic) UILabel * selectionBottomLine;

@property (strong, nonatomic) RJRedButton * currentButton;

@property (copy, nonatomic) RJSegmentViewSelectBlock selectBlock;
@end

@implementation RJSegmentView

- (instancetype)init
{
    self = [super init];
    if (self) {
        [self initData];
    }
    return self;
}

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        [self initData];
    }
    return self;
}

#pragma mark - user-define initialization
- (void)initData {
    _isScrollView = NO;
    _itemWidth = 80;
    _titlesArray = @[];
    _userInsets = UIEdgeInsetsZero;
}

- (void)initInterface {
    if (_titlesArray.count == 0) {
        return;
    }
    
    UIView * contentView = nil;
    CGFloat height = self.frame.size.height;
    if (_isScrollView) {
        contentView = [self getScrollView];
        CGFloat width = _itemWidth * _titlesArray.count;
        [_scrollView setContentSize:CGSizeMake(width, height)];
    } else {
        contentView = self;
        _itemWidth = (SCREEN_WIDTH -  _userInsets.left - _userInsets.right) / _titlesArray.count;
    }
    NSMutableArray * temp = [NSMutableArray array];
    for (NSInteger i = 0; i < _titlesArray.count; i ++) {
        RJRedButton * button = [[RJRedButton alloc] init];
        [button setTitle:_titlesArray[i] forState:UIControlStateNormal];
        [button setTitleColor:RGB_COLOR(51, 51, 51) forState:UIControlStateNormal];
        [button setTitleColor:RGB_COLOR(24, 116, 255) forState:UIControlStateSelected];
        [button.titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
        [button addTarget:self action:@selector(pressButton:) forControlEvents:UIControlEventTouchUpInside];
        [temp addObject:button];
        [contentView addSubview:button];
        
        //约束
        [button mas_makeConstraints:^(MASConstraintMaker *make) {
            make.width.mas_equalTo(_itemWidth);
            make.top.bottom.equalTo(contentView);
            
            if (i == 0) {
                make.left.equalTo(contentView).offset(_userInsets.left);
            } else {
                UIButton * lastButton = temp[i - 1];
                make.left.equalTo(lastButton.mas_right);
            }
        }];
    }
    _buttonsArray = [NSArray arrayWithArray:temp];
    _currentButton = _buttonsArray[0];
    [_currentButton setSelected:YES];
    //下划线
    _selectionBottomLine = [[UILabel alloc] init];
    [_selectionBottomLine setBackgroundColor:RGB_COLOR(24, 116, 255)];
    [_selectionBottomLine.layer setCornerRadius:0.8];
    [contentView addSubview:_selectionBottomLine];

    CGRect rect = CGRectMake(_userInsets.left + _itemWidth * 0.1, height - 1.6, _itemWidth * 0.8, 1.6);
    [_selectionBottomLine setFrame:rect];
}


#pragma mark - event
- (void)pressButton:(UIButton *)sender {
    if (_currentButton == sender) {
        return;
    }
    [_currentButton setSelected:NO];
    _currentButton = (RJRedButton *)sender;
    [_currentButton setSelected:YES];
    [self updateBottomLine];
    if (_selectBlock) {
        NSInteger count = [_buttonsArray indexOfObject:_currentButton];
        __weak typeof(_currentButton) weakButton = _currentButton;
        _selectBlock(count, weakButton);
    }
}
#pragma mark - function
- (void)build {
    [self initInterface];
}

- (void)updateBottomLine {
    CGFloat width = _currentButton.frame.size.width * 0.8;
    CGFloat height = 1;
    CGFloat y = _currentButton.frame.size.height - height;
    CGFloat x = _currentButton.frame.origin.x + _currentButton.frame.size.width * 0.1;
    CGRect rect = CGRectMake(x, y, width, height);
    [UIView animateWithDuration:0.3 animations:^{
        [_selectionBottomLine setFrame:rect];
    }];
    
}
#pragma mark - delegate

#pragma mark - notification

#pragma mark - setter
- (void)setSelectedCount:(NSInteger)count {
    if (count >= _buttonsArray.count) {
        return;
    }
    [self pressButton:_buttonsArray[count]];
}

- (void)setSelectBlock:(void(^)(NSInteger count, UIButton * currentButton))block {
    _selectBlock = block;
}
#pragma mark - getter
- (UIScrollView *)getScrollView {
    if(!_scrollView) {
        _scrollView = [[UIScrollView alloc] init];
        [_scrollView setDelegate:self];
        [self addSubview:_scrollView];
        
        MJWeakSelf
        [_scrollView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.left.right.bottom.equalTo(weakSelf);
        }];
    }
    return _scrollView;
}
@end
