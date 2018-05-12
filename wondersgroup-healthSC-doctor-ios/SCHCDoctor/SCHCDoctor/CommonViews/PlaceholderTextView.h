//
//  PlaceholderTextView.h
//  HCPatient
//
//  Created by ZJW on 15/7/14.
//  Copyright (c) 2015年 陈刚. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface PlaceholderTextView : UITextView <UITextViewDelegate>
{
    NSString *placeholder;
    UIColor *placeholderColor;
//    UIColor *limitColor;
@private
    UILabel *placeHolderLabel;
//    UILabel *limitLabel;
}

@property (nonatomic, retain) UILabel  *placeHolderLabel;
@property (nonatomic, retain) NSString *placeholder;
@property (nonatomic, retain) UIColor  *placeholderColor;
//@property (nonatomic, retain) UIColor  *limitColor;
@property (nonatomic, assign) CGPoint  placeHolderLabelOrigion;

@property (nonatomic, assign) NSInteger limitLength;
-(void)textChanged:(NSNotification*)notification;

@property (nonatomic, copy) NSString *contentText;

@end
