//
//  ReferralActionSheetView.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ReferralActionSheetView.h"

typedef void(^ReferralSheetClickBlock)(NSInteger index, BOOL isCancel);

@interface ReferralActionSheetView ()

@property (strong, nonatomic) UIView * contentView;

@property (strong, nonatomic) NSArray * titlesArray;
@property (strong, nonatomic) NSArray * buttonsArray;
@property (strong, nonatomic) UIButton * cancelButton;
@property (copy, nonatomic) ReferralSheetClickBlock clickBlock;
@end

@implementation ReferralActionSheetView

- (instancetype)initWithFrame:(CGRect)frame titles:(NSArray *)titles{
    self = [super initWithFrame:SCREEN_BOUNDS];
    if (self) {
        
        _titlesArray = [NSArray arrayWithArray:titles];
        
        _contentView = [[UIView alloc] initWithFrame:frame];
        [_contentView setBackgroundColor:[UIColor whiteColor]];
        [self addSubview:_contentView];
        [self setupView];
    }
    return self;
}

- (void)setupView {
    [self setBackgroundColor:RGBA_COLOR(46, 122, 240, 0.0)];
    CGFloat width = _contentView.frame.size.width;
    CGFloat height = _contentView.frame.size.height / (_titlesArray.count + 1);
    NSMutableArray * temp = [NSMutableArray array];
    for (NSInteger index = 0; index < _titlesArray.count; index ++) {
        UIButton * button = [[UIButton alloc] initWithFrame:CGRectMake(0, height * index, width, height)];
        [button setTitle:_titlesArray[index] forState:UIControlStateNormal];
        [button setTitleColor:RGB_COLOR(102, 102, 102) forState:UIControlStateNormal];
        [button.titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
        [button addTarget:self action:@selector(pressButton:) forControlEvents:UIControlEventTouchUpInside];
        [_contentView addSubview:button];
        [temp addObject:button];
        UILabel * label = [[UILabel alloc] initWithFrame:CGRectMake(0, height * (index + 1), width, 1)];
        [label setBackgroundColor:RGB_COLOR(241, 241, 241)];
        [_contentView addSubview:label];
    }
    _buttonsArray = [NSArray arrayWithArray:temp];
    
    _cancelButton = [[UIButton alloc] initWithFrame:CGRectMake(0, _contentView.frame.size.height - height, width, height)];
    [_cancelButton addTarget:self action:@selector(pressCancelButton:) forControlEvents:UIControlEventTouchUpInside];
    
    [_cancelButton setTitle:@"取消" forState:UIControlStateNormal];
    [_cancelButton setTitleColor:RGB_COLOR(153, 153, 153) forState:UIControlStateNormal];
    [_cancelButton.titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
    [_contentView addSubview:_cancelButton];
}

#pragma mark - event
- (void)pressButton:(UIButton *)sender {
    if (_clickBlock) {
        NSInteger index = [_buttonsArray indexOfObject:sender];
        
        _clickBlock(index, NO);
        [self hideAnim];
    }
}

- (void)pressCancelButton:(UIButton *)sender {
    if (_clickBlock) {
        
        _clickBlock(-1, YES);
        [self hideAnim];
    }
}

#pragma mark - function
- (void)showAnim {
    MJWeakSelf
    CGFloat y = SCREEN_HEIGHT - _contentView.frame.size.height;
    
    CGRect rect = _contentView.frame;
    rect.origin.y = y;
    [UIView animateWithDuration:0.2 delay:0 options:UIViewAnimationOptionCurveEaseOut animations:^{
        [weakSelf.contentView setFrame:rect];
        [weakSelf setBackgroundColor:RGBA_COLOR(46, 122, 240, 0.1)];
    } completion:^(BOOL finished) {
        
    }];
}

- (void)hideAnim {
    CGRect rect = _contentView.frame;
    rect.origin.y = SCREEN_HEIGHT;
    
    MJWeakSelf
    [UIView animateWithDuration:0.2 delay:0 options:UIViewAnimationOptionCurveEaseOut animations:^{
        [weakSelf.contentView setFrame:rect];
        [weakSelf setBackgroundColor:RGBA_COLOR(46, 122, 240, 0)];
    } completion:^(BOOL finished) {
        if (finished) {
            [weakSelf removeFromSuperview];
        }
    }];
}

#pragma mark - setter
- (void)setClickBlock:(void(^)(NSInteger index, BOOL isCancel))block {
    _clickBlock = block;
}



-(void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event {
    [self hideAnim];
}
@end
