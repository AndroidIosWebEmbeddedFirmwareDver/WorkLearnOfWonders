//
//  RJDatePicker.m
//  GuestManager
//
//  Created by Po on 16/4/15.
//  Copyright © 2016年 pretang. All rights reserved.
//

#import "RJDatePicker.h"

typedef void(^SelectedPicker)(NSDate * date);

@interface RJDatePicker ()

@property (strong, nonatomic) UIView * contentView;
@property (strong, nonatomic) UIView * pickerContentView;
@property (copy, nonatomic) SelectedPicker selectedBlock;                 //有效选中回调
@property (strong, nonatomic) UIButton * confirmButton;
@property (strong, nonatomic) UIButton * cancelButton;

@end

@implementation RJDatePicker
- (instancetype)init
{
    self = [super initWithFrame:SCREEN_BOUNDS];
    if (self) {
        [self getContentView];
        [self getPicker];
    }
    return self;
}

#pragma mark - event
//- (void)dateUpdate:(UIDatePicker *)picker {
//    if (_selectedBlock) {
//        _selectedBlock(picker.date);
//    }
//}

- (void)tapContentView {
    MJWeakSelf
    [UIView animateWithDuration:0.2 animations:^{
        [weakSelf setBackgroundColor:[UIColor colorWithWhite:0 alpha:0]];
        [_pickerContentView setFrame:CGRectMake(0, SCREEN_HEIGHT, SCREEN_WIDTH, 280)];
    } completion:^(BOOL finished) {
        [_pickerContentView removeFromSuperview];
        _picker = nil;
        _pickerContentView = nil;
        [weakSelf removeFromSuperview];
    }];
}

- (void)pressConfirmButton:(UIButton *)sender {
    if (_selectedBlock) {
        _selectedBlock(_picker.date);
    }
    [self tapContentView];
}

- (void)pressCancelButton:(UIButton *)sender {
    [self tapContentView];
}

#pragma mark - function
- (void)build {
    [self getPicker];
    [self playAnimation];
}

- (void)playAnimation {
    MJWeakSelf
    [UIView animateWithDuration:0.3 animations:^{
        [weakSelf setBackgroundColor:[UIColor colorWithWhite:0 alpha:0.3]];
        [_pickerContentView setFrame:CGRectMake(0, SCREEN_HEIGHT - 280, SCREEN_WIDTH, 280)];
    }];
}

#pragma mark - setter
- (void)setSelectedBlock:(void(^)(NSDate * date))selectedBlock {
    if (_selectedBlock) {
        _selectedBlock = nil;
    }
    _selectedBlock = selectedBlock;
}

#pragma mark - getter
- (UIDatePicker *)getPicker {
    if (!_picker) {
        //178
        _pickerContentView = [[UIView alloc] initWithFrame:CGRectMake(0, SCREEN_HEIGHT, SCREEN_WIDTH, 280)];
        [_pickerContentView setBackgroundColor:[UIColor whiteColor]];
        _picker = [[UIDatePicker alloc] initWithFrame:CGRectMake(0, 40, SCREEN_WIDTH, 240)];
        [_picker setBackgroundColor:[UIColor whiteColor]];
//        [_picker addTarget:self action:@selector(dateUpdate:) forControlEvents:UIControlEventValueChanged];
        [_pickerContentView addSubview:_picker];
        
        //按钮
        _confirmButton = [[UIButton alloc] initWithFrame:CGRectMake(SCREEN_WIDTH - 80, 0, 80, 40)];
        [_confirmButton setTitle:@"确定" forState:UIControlStateNormal];
        [_confirmButton setTitleColor:RGB_COLOR(0, 122, 255) forState:UIControlStateNormal];
        [_confirmButton addTarget:self action:@selector(pressConfirmButton:) forControlEvents:UIControlEventTouchUpInside];
        [_confirmButton.titleLabel setTextColor:[UIColor blackColor]];
        
        _cancelButton = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 80, 40)];
        [_cancelButton setTitle:@"取消" forState:UIControlStateNormal];
        [_cancelButton addTarget:self action:@selector(pressCancelButton:) forControlEvents:UIControlEventTouchUpInside];
        [_cancelButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
        
        [_pickerContentView addSubview:_confirmButton];
        [_pickerContentView addSubview:_cancelButton];
        
        [self addSubview:_pickerContentView];
    }
    return _picker;
}

- (UIView *)getContentView {
    if (!_contentView) {
        _contentView = [[UIView alloc] initWithFrame:SCREEN_BOUNDS];
        
        UITapGestureRecognizer * tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapContentView)];
        [tapGesture setNumberOfTapsRequired:1];
        [tapGesture setNumberOfTouchesRequired:1];
        [_contentView addGestureRecognizer:tapGesture];
        
        [self addSubview:_contentView];
    }
    return _contentView;
}





























@end
