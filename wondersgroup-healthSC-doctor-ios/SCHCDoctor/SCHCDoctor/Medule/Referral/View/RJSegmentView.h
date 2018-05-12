//
//  RJSegmentView.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/5.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RJRedButton.h"
@interface RJSegmentView : UIView

@property (assign, nonatomic) BOOL isScrollView;            //是否可以滚动(默认：NO)
@property (assign, nonatomic) CGFloat itemWidth;            //item的宽度（默认：80px）
@property (assign, nonatomic) UIEdgeInsets userInsets;      //内边距
@property (strong, nonatomic) NSArray * titlesArray;        //标题数组
@property (strong, nonatomic) UIScrollView * scrollView;
@property (strong, nonatomic) NSArray * buttonsArray;

- (void)build;

- (void)setSelectedCount:(NSInteger)count;

/**
 选取回调
 */
- (void)setSelectBlock:(void(^)(NSInteger count, UIButton * currentButton))block;
@end
