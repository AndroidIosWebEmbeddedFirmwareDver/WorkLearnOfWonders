//
//  MyAttentionSegmentViewController.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>


typedef NS_ENUM(NSUInteger, SegmentIndicateStyle) {
    
    SegmentIndicateStyleDefault,    //指示条和按钮的标题齐平
    SegmentIndicateStyleFlush,      //指示条和按钮宽度齐平
    SegmentIndicateStyle70,         //指示条占70%
};

typedef NS_ENUM(NSUInteger, SegmentItemWidthStyle) {

    SegmentItemWidthStyleAuto,          //按钮自适应宽度
    SegmentItemWidthStyleAbsulote,      //按钮绝对宽度
};

@interface MyAttentionSegmentViewController : UIViewController

@property (nonatomic, strong) UIColor *segementTintColor;       //选中时的字体颜色, 默认是黑色
@property (nonatomic, strong) UIScrollView *segmentView;
@property (nonatomic, assign) SegmentIndicateStyle indicateStyle;
@property (nonatomic, assign) SegmentItemWidthStyle itemWidthStyle;
@property (nonatomic, strong) NSArray *viewControllers;


- (instancetype)initWithFrame:(CGRect)frame titles:(NSArray *)titles itemStyle:(SegmentItemWidthStyle)style;

- (void)setSelectedItemAtIndex:(NSInteger)index;

- (void)setSegementViewControllers:(NSArray <UIViewController *>*)viewControllers;

- (void)selectedAtIndex:(void(^)(NSInteger index))indexBlock;

@end
