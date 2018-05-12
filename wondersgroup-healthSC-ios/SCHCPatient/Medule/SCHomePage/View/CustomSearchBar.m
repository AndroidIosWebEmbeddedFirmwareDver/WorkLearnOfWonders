//
//  CustomSearchBar.m
//  GZHealthCloudPatient
//
//  Created by Li,Huanan on 16/8/2.
//  Copyright © 2016年 WondersGroup. All rights reserved.
//

#import "CustomSearchBar.h"

@implementation CustomSearchBar
@synthesize textField = _textField;
+ (CustomSearchBar *)customSearbarWithFrame:(CGRect)frame text:(NSString *)text placeholder:(NSString *)placeholder textFieldDelegate:(id<UITextFieldDelegate>)delegate{

    CustomSearchBar *view = [[CustomSearchBar alloc] initWithFrame:frame];
    view.backgroundColor = HEX_COLOR(0xf7f9fa);
    
    UIView *backgroundView = [UIView new];
    backgroundView.layer.masksToBounds = YES;
    backgroundView.layer.cornerRadius = 5.;
    backgroundView.layer.borderWidth = 0.5;
    backgroundView.layer.borderColor = HEX_COLOR(0xdddddd).CGColor;
    backgroundView.backgroundColor = [UIColor bc2Color];
    [view addSubview:backgroundView];
    
    [backgroundView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(view).with.offset(11);
        make.right.equalTo(view).with.offset(-11);
        make.centerY.equalTo(view.mas_centerY);
        make.height.mas_equalTo(@32);
    }];
    
    UIImageView *searchImageView = [UIImageView new];
    searchImageView.image = [UIImage imageNamed:@"ic_nav_gray_search"];
    [backgroundView addSubview:searchImageView];
    
    [searchImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(backgroundView).with.offset(9);
        make.centerY.equalTo(backgroundView.mas_centerY);
        make.size.mas_equalTo(searchImageView.image.size);
    }];
    
    UITextField *textField = [UITextField new];
    
    if (text) {
        textField.text = text;
    }
    view->_textField = textField;
    textField.placeholder = placeholder;
    /*
     修改光标颜色
     */
    textField.tintColor = [UIColor darkGrayColor];
    
    textField.returnKeyType = UIReturnKeySearch;
    textField.font = [UIFont systemFontOfSize:14.];
    textField.delegate = delegate;
    [backgroundView addSubview:textField];
    textField.clearButtonMode = UITextFieldViewModeWhileEditing;
    
    [textField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(searchImageView.mas_right).with.offset(7);
        make.right.equalTo(backgroundView).with.offset(-7);
        make.centerY.equalTo(backgroundView.mas_centerY);
        make.height.mas_equalTo(@14);
    }];
    
    UIView *bottomLineView = [UIView new];
    bottomLineView.backgroundColor = [UIColor clearColor];
    [view addSubview:bottomLineView];
    
    [bottomLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(view);
        make.height.mas_equalTo(@0.5);
    }];
    
    return view;


}
+ (CustomSearchBar *)customSearbarWithFrame:(CGRect)frame text:(NSString *)text placeholder:(NSString *)placeholder textFieldDelegate:(id<UITextFieldDelegate>)delegate needFoucus:(BOOL)need {
    
    CustomSearchBar *view = [[CustomSearchBar alloc] initWithFrame:frame];
    view.backgroundColor = HEX_COLOR(0xf7f9fa);
    
    UIView *backgroundView = [UIView new];
    backgroundView.layer.masksToBounds = YES;
    backgroundView.layer.cornerRadius = 5.;
    backgroundView.layer.borderWidth = 0.5;
    backgroundView.layer.borderColor = HEX_COLOR(0xdddddd).CGColor;
    backgroundView.backgroundColor = [UIColor whiteColor];
    [view addSubview:backgroundView];
    
    [backgroundView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(view).with.offset(11);
        make.right.equalTo(view).with.offset(-11);
        make.centerY.equalTo(view.mas_centerY);
        make.height.mas_equalTo(@32);
    }];
    
    UIImageView *searchImageView = [UIImageView new];
    searchImageView.image = [UIImage imageNamed:@"ic_rearch_sousuo"];
    [backgroundView addSubview:searchImageView];
    
    [searchImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(backgroundView).with.offset(9);
        make.centerY.equalTo(backgroundView.mas_centerY);
        make.size.mas_equalTo(searchImageView.image.size);
    }];
    
    UITextField *textField = [UITextField new];
    
    if (text) {
        textField.text = text;
    }
    textField.placeholder = placeholder;
    /*
        修改光标颜色
     */
    if (need) {
        [textField becomeFirstResponder];
    }
    textField.tintColor = [UIColor darkGrayColor];

    textField.returnKeyType = UIReturnKeySearch;
    textField.font = [UIFont systemFontOfSize:14.];
    textField.delegate = delegate;
    [backgroundView addSubview:textField];
    textField.clearButtonMode = UITextFieldViewModeWhileEditing;

    [textField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(searchImageView.mas_right).with.offset(7);
        make.right.equalTo(backgroundView).with.offset(-7);
        make.centerY.equalTo(backgroundView.mas_centerY);
        make.height.mas_equalTo(@14);
    }];
    
    UIView *bottomLineView = [UIView new];
    bottomLineView.backgroundColor = [UIColor clearColor];
    [view addSubview:bottomLineView];
    
    [bottomLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(view);
        make.height.mas_equalTo(@0.5);
    }];

    return view;
}


@end
