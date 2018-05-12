//
//  PickerSelectedView.m
//  VaccinePatient
//
//  Created by Jam on 16/6/15.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "PickerSelectedView.h"

@interface PickerSelectedView ()<UIPickerViewDelegate, UIPickerViewDataSource>

@property (nonatomic, strong) UIWindow *window;
@property (nonatomic, strong) UILabel *titleLabel;
@property (nonatomic, strong) UIView  *selView;

@property (nonatomic, copy) NSString *selString;
@property (nonatomic, strong) NSArray *dataList;
 
@end

@implementation PickerSelectedView


-(instancetype)init {
    self = [super initWithFrame: [UIScreen mainScreen].bounds];
    if (self) {
        self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
        [self.window addSubview:self];
        [self.window makeKeyAndVisible];
        
        [self setBackgroundColor: [UIColor clearColor]];
        [self setupUIView];
    }
    return self;
}


- (void)setupUIView {
    UIView *bgView = [[UIView alloc] initWithFrame: self.bounds];
    [bgView setBackgroundColor: [UIColor blackColor]];
    [bgView setAlpha: 0.5];
    [self addSubview: bgView];
}

#pragma mark - 性别选择
- (void)showGenderPickerWithModel: (NSString *)gender
                        withTitle: (NSString *)showTitle {
    
    self.dataList = @[@"男", @"女"];
    
    UIPickerView *dataPicker = [[UIPickerView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 216)];
    [dataPicker setBackgroundColor: [UIColor bc1Color]];
    dataPicker.dataSource = self;
    dataPicker.delegate   = self;
    [dataPicker reloadAllComponents];

    if (gender && gender.length > 0) {
        self.selString = gender;
        [dataPicker selectRow:[self.dataList indexOfObject: gender] inComponent: 0 animated: NO];
    }
    else {
        gender = @"男";
        self.selString = @"男";
    }

    if (self.selView) {
        [self.selView removeFromSuperview];
        self.selView = nil;
    }
    
    CGRect frame = dataPicker.frame;
    CGFloat viewH = frame.size.height + 44.0;
    
    self.selView = [[UIView alloc] initWithFrame: CGRectMake(0, self.bounds.size.height, SCREEN_WIDTH, viewH)];
    [self addSubview: self.selView];
    
    UIView *topView = [self setupTopView: dataPicker];
    
    self.titleLabel.text = showTitle;
    [self.selView addSubview: topView];
    
    frame.origin.y = 44.0;
    [dataPicker setFrame: frame];
    [self.selView addSubview: dataPicker];
    
    [self showPickerWithAnimation];

}


#pragma mark - UIPickerViewDataSource
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
    return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
    return self.dataList.count;
}


#pragma mark -UIPickerViewDelegate
- (nullable NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component  {
    return self.dataList[row];
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component {
    self.selString = self.dataList[row];
}


#pragma mark - 日期选择
- (void)showDatePickerWithModel: (UIDatePickerMode)model
                       withDate: (NSString *)date
                      withTitle: (NSString *)showTitle
                    withMaxDate: (NSDate *)maxDate
                    withMinDate: (NSDate *)minDate {
    
    UIDatePicker *datePicker = [[UIDatePicker alloc] init];
    [datePicker setBackgroundColor: [UIColor bc1Color]];
    [datePicker setDatePickerMode: model];
    if (maxDate) {
        [datePicker setMaximumDate: maxDate];
    }
    if (minDate) {
        [datePicker setMinimumDate: minDate];
    }
    
    CGRect frame = datePicker.frame;
    frame.size.width = SCREEN_WIDTH;

    if (self.selView) {
        [self.selView removeFromSuperview];
        self.selView = nil;
    }
    
    CGFloat viewH = frame.size.height + 44.0;
    
    self.selView = [[UIView alloc] initWithFrame: CGRectMake(0, self.bounds.size.height, SCREEN_WIDTH, viewH)];
    [self addSubview: self.selView];
    
    UIView *topView = [self setupTopView: datePicker];
    
    self.titleLabel.text = showTitle;
    [self.selView addSubview: topView];
    
    frame.origin.y = 44.0;
    [datePicker setFrame: frame];
    [self.selView addSubview: datePicker];
    
    NSDate *selDate = [NSDate mt_dateFromString: date usingFormat: @"yyyy-MM-dd"];
    if (selDate) {
        [datePicker setDate: selDate];
    }

    [self showPickerWithAnimation];
    
}

#pragma mark - Picker弹出动画
- (void)showPickerWithAnimation {
    WS(weakSelf);
    CGRect frame = self.selView.frame;
    frame.origin.y = frame.origin.y - frame.size.height;
    [UIView animateWithDuration: 0.3 animations:^{
        [weakSelf.selView setFrame: frame];
    }];
}

#pragma mark - Picker消失动画
- (void)dissmiss {
    WS(weakSelf);
    CGRect frame = self.selView.frame;
    frame.origin.y = self.bounds.size.height;
    [UIView animateWithDuration: 0.3 animations:^{
        [weakSelf.selView setFrame: frame];
    } completion:^(BOOL finished) {
        [weakSelf removeFromSuperview];
        weakSelf.window = nil;
        [APP.window makeKeyAndVisible];
    }];
}



#pragma mark - 加载顶部显示、保存、取消的界面
- (UIView *)setupTopView:(UIView *)view {
    WS(weakSelf);
    UIView *topView = [[UIView alloc] initWithFrame: CGRectMake(0, 0, SCREEN_WIDTH, 44.0)];
    [topView setBackgroundColor: [UIColor bc1Color]];
    //保存按钮
    UIButton *save = [UISetupView setupButtonWithSuperView: topView
                                    withTitleToStateNormal: @"确定"
                               withTitleColorToStateNormal: [UIColor tc5Color]
                                         withTitleFontSize:14.0 withAction:^(UIButton *sender) {
                                             //保存操作
                                             if ([view isKindOfClass: [UIDatePicker class]]) {
                                                 UIDatePicker *datePicker = (UIDatePicker *)view;
                                                 weakSelf.saveBlock(datePicker.date);
                                             }
                                             else {
                                                 weakSelf.saveBlock(weakSelf.selString);
                                             }
                                         }];
    [save mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.right.equalTo(topView);
        make.width.mas_equalTo(60.0);
    }];
    
    UIButton *cancel = [UISetupView setupButtonWithSuperView: topView
                                    withTitleToStateNormal: @"取消"
                               withTitleColorToStateNormal: [UIColor tc2Color]
                                         withTitleFontSize:14.0 withAction:^(UIButton *sender) {
                                             [self dissmiss];
                                         }];
    [cancel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.left.equalTo(topView);
        make.width.mas_equalTo(60.0);
    }];
    
    UILabel *showLabel = [UISetupView setupLabelWithSuperView: topView
                                                     withText: @""
                                                withTextColor: [UIColor tc1Color]
                                                 withFontSize: 20.0];
    [showLabel setTextAlignment: NSTextAlignmentCenter];
    [showLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(topView);
        make.left.equalTo(cancel.mas_right).offset(15.0);
        make.right.equalTo(save.mas_left).offset(-15.0);
    }];
    
    self.titleLabel = showLabel;
    
    [UISetupView setupTopLineViewWithSuperView: topView withSpace: 0];
    [UISetupView setupBottomLineViewWithSuperView: topView withSpace: 0];
    
    return topView;
}




@end
