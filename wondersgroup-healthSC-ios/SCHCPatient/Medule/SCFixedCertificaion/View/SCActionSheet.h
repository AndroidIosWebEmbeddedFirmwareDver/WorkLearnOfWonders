//
//  SCActionSheet
//  
//
//  Created by wanda on 16/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//
#import <UIKit/UIKit.h>

@interface SCActionSheet : UIView

@property (nonatomic, copy) void (^Click) (NSInteger clickIndex);
- (instancetype)initWithFrame:(CGRect)frame titleArr:(NSArray *)titleArr;
- (void)hiddenSheet;

@end
