//
//  MyAttentionSegmentViewController.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "MyAttentionSegmentViewController.h"


typedef void(^SegmentIndexBlock)(NSInteger index);

@interface MyAttentionSegmentViewController () <UIScrollViewDelegate>

@property (nonatomic, strong) UIScrollView *containerView;

@property (nonatomic, assign) CGSize size;
@property (nonatomic, strong) NSArray *titles;

@property (nonatomic, strong) UIView *indicateView;             //指示条

@property (nonatomic, strong) UIColor *normalColor;             //标题未被选中时的颜色
@property (nonatomic, strong) UIFont *font;
@property (nonatomic, assign) CGFloat segmentHeight;            //segmentControl的高度
@property (nonatomic, assign) CGFloat minItemSpace;             //item的最小间距
@property (nonatomic, assign) CGFloat buttonSpace;              //按钮title到边的距离
@property (nonatomic, strong) NSMutableArray *widthArray;       //存放按钮的宽度

@property (nonatomic, assign) CGFloat indicateHeight;           //指示条高度
@property (nonatomic, assign) NSTimeInterval duration;          //滑动时间

@property (nonatomic, strong) UIButton *selectedButton;

@property (nonatomic, copy) SegmentIndexBlock resultBlock;

@end

@implementation MyAttentionSegmentViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}


- (instancetype)initWithFrame:(CGRect)frame titles:(NSArray *)titles itemStyle:(SegmentItemWidthStyle)style {
    
    if (self == [super init]) {
        self.size = frame.size;
        self.titles = titles;
        self.itemWidthStyle = style;
        [self prepareBasic];
        [self preparePage];
        [self prepareContainerView];
    }
    return self;
}

- (void)prepareBasic {
    
    self.view.backgroundColor = [UIColor clearColor];
    self.widthArray = [NSMutableArray array];
    self.segmentHeight = 44.;
    self.minItemSpace = 20.;
    self.segementTintColor = [UIColor blackColor];
    self.normalColor = [UIColor tc1Color];
    self.font = [UIFont systemFontOfSize:16];
    self.buttonSpace = [self calculateSpace];
    self.indicateHeight = 2.;
    self.duration = .3;
}

- (void)preparePage {
    
    self.segmentView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 0, self.size.width, self.segmentHeight)];
    self.segmentView.backgroundColor = [UIColor colorWithWhite:1. alpha:.5];
    self.segmentView.showsHorizontalScrollIndicator = NO;
    self.segmentView.showsVerticalScrollIndicator = NO;
    [self.view addSubview:self.segmentView];
    
    UIView *bottomLineView = [[UIView alloc] initWithFrame:CGRectMake(0, self.segmentHeight-0.5, self.size.width, 0.5)];
    bottomLineView.backgroundColor = [UIColor dc1Color];
    [self.segmentView addSubview:bottomLineView];
    
    self.indicateView = [[UIView alloc] init];
    self.indicateView.backgroundColor = self.segementTintColor;
    
    CGFloat itemX = 0;
    for (int i = 0; i < self.titles.count; i++) {
        NSString *title = self.titles[i];
        
        UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
        
        CGSize titleSize;
        
        if (self.itemWidthStyle == SegmentItemWidthStyleAuto) {
            titleSize = [title sizeWithAttributes:@{NSFontAttributeName: self.font}];
            button.frame = CGRectMake(itemX, 0, self.buttonSpace * 2 + titleSize.width, self.segmentHeight);
            self.segmentView.contentSize = CGSizeMake(itemX, self.segmentHeight);
            itemX += self.buttonSpace*2 + titleSize.width;
        }
        else if (self.itemWidthStyle == SegmentItemWidthStyleAbsulote) {
            button.frame = CGRectMake(itemX, 0, SCREEN_WIDTH/self.titles.count, self.segmentHeight);
            self.segmentView.contentSize = CGSizeMake(itemX, self.segmentHeight);
            itemX += SCREEN_WIDTH/self.titles.count * (i+1);
        }
        
        button.titleLabel.font = [UIFont systemFontOfSize:14.];
        [button setTag:i];
        [button setTitle:title forState:UIControlStateNormal];
        [button setTitleColor:self.normalColor forState:UIControlStateNormal];
        [button setTitleColor:self.segementTintColor forState:UIControlStateSelected];
        [button addTarget:self action:@selector(segmentButtonAction:) forControlEvents:UIControlEventTouchUpInside];
        [self.segmentView addSubview:button];
        [self.widthArray addObject:[NSNumber numberWithDouble:CGRectGetWidth(button.frame)]];
        
        if (i == 0) {
            button.selected = YES;
            self.selectedButton = button;
            
            // 添加指示杆
            if (self.indicateStyle == SegmentIndicateStyleDefault) {
                self.indicateView.frame = CGRectMake(self.buttonSpace, self.segmentHeight - self.indicateHeight, titleSize.width, self.indicateHeight);
            }
            else if (self.indicateStyle == SegmentIndicateStyleFlush) {
                self.indicateView.frame = CGRectMake(0, self.segmentHeight - self.indicateHeight, SCREEN_WIDTH/self.titles.count, self.indicateHeight);
            }
            else if (self.indicateStyle == SegmentIndicateStyle70) {
                self.indicateView.frame = CGRectMake((SCREEN_WIDTH/self.titles.count)*0.15,
                                                     self.segmentHeight - self.indicateHeight,
                                                     (SCREEN_WIDTH/self.titles.count)*0.7,
                                                     self.indicateHeight);
            }
            [self.segmentView addSubview:self.indicateView];
        }
    }
}

- (void)prepareContainerView {
    
    self.containerView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, self.segmentHeight, self.size.width, self.size.height - self.segmentHeight)];
    self.containerView.backgroundColor = [UIColor whiteColor];
    self.containerView.showsVerticalScrollIndicator = NO;
    self.containerView.showsHorizontalScrollIndicator = NO;
    self.containerView.delegate = self;
    self.containerView.pagingEnabled = YES;
    self.containerView.bounces = NO;
    [self.view addSubview:self.containerView];
}


#pragma mark -
- (CGFloat)calculateSpace {
    
    CGFloat space = 0.;
    CGFloat totalWidth = 0.;
    
    for (NSString *title in self.titles) {
        CGSize titleSize = [title sizeWithAttributes:@{NSFontAttributeName : self.font}];
        totalWidth += titleSize.width;
    }
    
    space = (self.size.width - totalWidth) / self.titles.count / 2;
    if (space > self.minItemSpace / 2) {
        return space;
    } else {
        return self.minItemSpace / 2;
    }
}


- (void)selectedAtIndex:(void (^)(NSInteger))indexBlock {
    if (indexBlock) {
        self.resultBlock = indexBlock;
        self.resultBlock([self selectedAtIndex]);
    }
}


#pragma mark - segmentButton action 

- (void)segmentButtonAction:(UIButton *)button {
    
    if (button != self.selectedButton) {
        button.selected = YES;
        self.selectedButton.selected = !self.selectedButton.selected;
        self.selectedButton = button;
        
        [self scrollIndicateView];
        [self scrollSegementView];
    }
    
    if (self.resultBlock) {
        self.resultBlock(self.selectedButton.tag);
    }
}

- (void)scrollIndicateView {
    NSInteger index = [self selectedAtIndex];
    CGSize titleSize = [self.selectedButton.titleLabel.text sizeWithAttributes:@{NSFontAttributeName : self.font}];
    [UIView animateWithDuration:self.duration delay:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        if (self.indicateStyle == SegmentIndicateStyleDefault) {
            self.indicateView.frame = CGRectMake(CGRectGetMinX(self.selectedButton.frame) + self.buttonSpace, CGRectGetMinY(self.indicateView.frame), titleSize.width, self.indicateHeight);
        }
        else if (self.indicateStyle == SegmentIndicateStyleFlush) {
            self.indicateView.frame = CGRectMake(CGRectGetMinX(self.selectedButton.frame), CGRectGetMinY(self.indicateView.frame), [self widthAtIndex:index], self.indicateHeight);
        }
        else if (self.indicateStyle == SegmentIndicateStyle70) {
            self.indicateView.frame = CGRectMake(CGRectGetMinX(self.selectedButton.frame) + (SCREEN_WIDTH/self.titles.count)*0.15,
                                                 CGRectGetMinY(self.indicateView.frame),
                                                 [self widthAtIndex:index] * 0.7,
                                                 self.indicateHeight);
        }
        else {
            
        }
        
        [self.containerView setContentOffset:CGPointMake(index * self.size.width, 0)];
    } completion:nil];
}

/**
 根据选中调整segementView的offset
 */
- (void)scrollSegementView {
    CGFloat selectedWidth = self.selectedButton.frame.size.width;
    CGFloat offsetX = (self.size.width - selectedWidth) / 2;
    
    if (self.selectedButton.frame.origin.x <= self.size.width / 2) {
        [self.segmentView setContentOffset:CGPointMake(0, 0) animated:YES];
    } else if (CGRectGetMaxX(self.selectedButton.frame) >= (self.segmentView.contentSize.width - self.size.width / 2)) {
        [self.segmentView setContentOffset:CGPointMake(self.segmentView.contentSize.width - self.size.width, 0) animated:YES];
    } else {
        [self.segmentView setContentOffset:CGPointMake(CGRectGetMinX(self.selectedButton.frame) - offsetX, 0) animated:YES];
    }
}

#pragma mark - index

- (NSInteger)selectedAtIndex {
    return self.selectedButton.tag;
}

- (void)setSelectedItemAtIndex:(NSInteger)index {
    for (UIView *view in self.segmentView.subviews) {
        if ([view isKindOfClass:[UIButton class]] && view.tag == index) {
            UIButton *button = (UIButton *)view;
            [self segmentButtonAction:button];
        }
    }
}

- (CGFloat)widthAtIndex:(NSInteger)index {
    if (index < 0 || index > self.titles.count - 1) {
        return .0;
    }
    return [[self.widthArray objectAtIndex:index] doubleValue];
}

#pragma mark - scrollView delegate

- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView {
    NSInteger index = round(scrollView.contentOffset.x / self.size.width);
    [self setSelectedItemAtIndex:index];
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
    CGFloat offsetX = scrollView.contentOffset.x;
    
    NSInteger currentIndex = [self selectedAtIndex];
    
    // 当当前的偏移量大于被选中index的偏移量的时候，就是在右侧
    CGFloat offset; // 在同一侧的偏移量
    NSInteger buttonIndex = currentIndex;
    if (offsetX >= [self selectedAtIndex] * self.size.width) {
        offset = offsetX - [self selectedAtIndex] * self.size.width;
        buttonIndex += 1;
    } else {
        offset = [self selectedAtIndex] * self.size.width - offsetX;
        buttonIndex -= 1;
        currentIndex -= 1;
    }
    
    CGFloat originMovedX, targetButtonWidth, originButtonWidth, itemWidth;
    
    CGFloat targetMovedWidth = [self widthAtIndex:currentIndex];//需要移动的距离
    
    if (self.indicateStyle == SegmentIndicateStyleDefault) {
        originMovedX = (CGRectGetMinX(self.selectedButton.frame) + self.buttonSpace);
        targetButtonWidth = ([self widthAtIndex:buttonIndex] - 2 * self.buttonSpace);
        originButtonWidth = ([self widthAtIndex:[self selectedAtIndex]] - 2 * self.buttonSpace);
        itemWidth = originButtonWidth + (targetButtonWidth - originButtonWidth) / self.size.width * offset;
    }
    else if (self.indicateStyle == SegmentIndicateStyleFlush) {
        originMovedX = CGRectGetMinX(self.selectedButton.frame);
        targetButtonWidth = [self widthAtIndex:buttonIndex];
        originButtonWidth = [self widthAtIndex:[self selectedAtIndex]];
        itemWidth = originButtonWidth + (targetButtonWidth - originButtonWidth) / self.size.width * offset;
    }
    else if (self.indicateStyle == SegmentIndicateStyle70) {
        originMovedX = CGRectGetMinX(self.selectedButton.frame) + (SCREEN_WIDTH/self.titles.count)*0.15;
        targetButtonWidth = [self widthAtIndex:buttonIndex];
        originButtonWidth = [self widthAtIndex:[self selectedAtIndex]];
        itemWidth = (originButtonWidth + (targetButtonWidth - originButtonWidth) / self.size.width * offset) * 0.7;
    }
    
    CGFloat moved; // 移动的距离
    moved = offsetX - [self selectedAtIndex] * self.size.width;
    self.indicateView.frame = CGRectMake(originMovedX + targetMovedWidth / self.size.width * moved,
                                         self.indicateView.frame.origin.y,
                                         itemWidth,
                                         self.indicateView.frame.size.height);
}


#pragma mark - set方法

- (void)setSegementTintColor:(UIColor *)segementTintColor {
    _segementTintColor = segementTintColor;
    _indicateView.backgroundColor = segementTintColor;
    for (UIView *view in _segmentView.subviews) {
        if ([view isKindOfClass:[UIButton class]]) {
            UIButton *button = (UIButton *)view;
            [button setTitleColor:segementTintColor forState:UIControlStateSelected];
        }
    }
}

- (void)setSegementViewControllers:(NSArray *)viewControllers {
    [self setViewControllers:viewControllers];
}

- (void)setViewControllers:(NSArray *)viewControllers {
    _viewControllers = viewControllers;
    _containerView.contentSize = CGSizeMake(viewControllers.count * _size.width, _size.height - _segmentHeight);
    for (int i = 0; i < viewControllers.count; i++) {
        UIViewController *viewController = viewControllers[i];
        viewController.view.frame = CGRectOffset(_containerView.bounds, i * _size.width, 0);
        [_containerView addSubview:viewController.view];
        [self addChildViewController:viewController];
    }
}

- (void)setIndicateStyle:(SegmentIndicateStyle)indicateStyle {
    
    _indicateStyle = indicateStyle;
    if (indicateStyle == SegmentIndicateStyleDefault) {
        
    }
    else if (indicateStyle == SegmentIndicateStyleFlush) {
        self.indicateView.frame = CGRectMake(_selectedButton.frame.origin.x, _segmentHeight - _indicateHeight, [self widthAtIndex:0], _indicateHeight);
    }
    else if (indicateStyle == SegmentIndicateStyle70) {
        self.indicateView.frame = CGRectMake(_selectedButton.frame.origin.x+(SCREEN_WIDTH/self.titles.count)*0.15,
                                             _segmentHeight - _indicateHeight,
                                             [self widthAtIndex:0] * 0.7,
                                             _indicateHeight);
    }
}



@end
