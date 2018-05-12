//
//  CustomSearchBar.h
//  GZHealthCloudPatient
//
//  Created by Li,Huanan on 16/8/2.
//  Copyright © 2016年 WondersGroup. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CustomSearchBar : UIView
@property (nonatomic, strong, readonly) UITextField *textField;
+ (CustomSearchBar *)customSearbarWithFrame:(CGRect)frame text:(NSString *)text placeholder:(NSString *)placeholder textFieldDelegate:(id<UITextFieldDelegate>)delegate needFoucus:(BOOL)need;
+ (CustomSearchBar *)customSearbarWithFrame:(CGRect)frame text:(NSString *)text placeholder:(NSString *)placeholder textFieldDelegate:(id<UITextFieldDelegate>)delegate;

@end
