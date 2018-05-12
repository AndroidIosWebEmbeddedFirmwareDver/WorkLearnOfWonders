//
//  SCNearbyHospitalSearchBarView.m
//  SCHCPatient
//
//  Created by Gu Jiajun on 16/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCNearbyHospitalSearchBarView.h"

@interface SCNearbyHospitalSearchBarView () <UITextFieldDelegate>

@property (nonatomic, strong) UITextField *searchField;
@property (nonatomic, strong) UIImageView *magnifierIcon;

@end

@implementation SCNearbyHospitalSearchBarView

- (id)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor bc1Color];
        [self setCornerRadius:5];
        
        [self setupView];
    }
    return self;
}

- (void)setupView{
    WS(weakSelf)
    _magnifierIcon = [UISetupView setupImageViewWithSuperView:self withImageName:@"ic_nav_gray_search"];
    [_magnifierIcon mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(weakSelf.mas_centerY);
        make.right.equalTo(weakSelf.mas_centerX).offset(-50);
        make.width.mas_equalTo(18);
        make.height.mas_equalTo(18);
    }];
    
    _searchField = [UISetupView setupTextFieldWithSuperView:self withText:nil withTextColor:[UIColor tc2Color] withFontSize:14 withPlaceholder:@"搜索医院" withDelegate:self withReturnKeyType:UIReturnKeySearch withKeyboardType:UIKeyboardTypeDefault];
    _searchField.textAlignment = NSTextAlignmentCenter;
    
    [_searchField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.mas_top);
        make.left.equalTo(weakSelf.mas_left);
        make.right.equalTo(weakSelf.mas_right);
        make.bottom.equalTo(weakSelf.mas_bottom);
    }];
    
}

#pragma mark textField Delegate
- (BOOL)textFieldShouldBeginEditing:(UITextField *)textField{
    if (_searchBlock) {
        _searchBlock(@"");
    }
    return NO;
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    if (_searchBlock) {
        _searchBlock(textField.text);
    }
    return YES;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
