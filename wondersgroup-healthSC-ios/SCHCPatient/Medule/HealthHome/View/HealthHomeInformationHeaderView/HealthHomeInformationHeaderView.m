//
//  HealthHomeInformationHeaderView.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HealthHomeInformationHeaderView.h"
#import "HHInformationListModel.h"

@interface HealthHomeInformationHeaderView ()

@property (nonatomic, strong) UIScrollView *backView;

@property (nonatomic, strong) UISegmentedControl *segmentedControl;
@property (nonatomic, strong) UIView *tagView;

//@property (nonatomic, strong) NSArray *items;

@property (nonatomic, assign) CGFloat space;
@property (nonatomic, assign) CGFloat itemWidth;
@property (nonatomic, assign) CGFloat itemSpace;

@end


@implementation HealthHomeInformationHeaderView

- (instancetype)initWithReuseIdentifier:(NSString *)reuseIdentifier items:(NSArray *)items {
    
    if (self == [super initWithReuseIdentifier:reuseIdentifier]) {
        
        [self prepareUI];
    }
    return self;
}



- (void)prepareUI {
    
}

- (void)setDataWithItems:(NSArray *)items {
    
    self.contentView.backgroundColor = [UIColor bc1Color];
    
    for (UIView *subView in self.contentView.subviews) {
        [subView removeFromSuperview];
    }
    UIView *bottomLineView = [UIView new];
    bottomLineView.backgroundColor = [UIColor dc4Color];
    [self.contentView addSubview:bottomLineView];
    
    WS(weakSelf)
    [bottomLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(weakSelf);
        make.height.mas_equalTo(@0.5
                                );
    }];
    
    self.backView = [[UIScrollView alloc] initWithFrame:CGRectMake(0., 0., SCREEN_WIDTH, 40.)];
    [self.contentView addSubview:self.backView];
    self.backView.contentSize = CGSizeMake(SCREEN_WIDTH/4*items.count, 40.);
    self.backView.showsHorizontalScrollIndicator = NO;
    
    
    NSMutableArray *titles = [NSMutableArray array];
    for (int i = 0; i < items.count; i++) {
        HHInformationListModel *listModel = items[i];
        [titles addObject:listModel.cat_name];
    }
    
    self.segmentedControl = [[UISegmentedControl alloc] initWithItems:titles];
    self.segmentedControl.tintColor = [UIColor clearColor];
    self.segmentedControl.frame = CGRectMake(0, 0, SCREEN_WIDTH/4*titles.count, 40.);
    self.segmentedControl.selectedSegmentIndex = self.selectedIndex;
    [self.segmentedControl addTarget:self action:@selector(segmentAction:) forControlEvents:UIControlEventValueChanged];
    
    //设定宽度
    for (int i = 0; i < titles.count; i ++) {
        [self.segmentedControl setWidth:SCREEN_WIDTH/4 forSegmentAtIndex:i];
    }
    
    // 正常状态下
    NSDictionary *normalTextAttributes = @{NSFontAttributeName : [UIFont systemFontOfSize:14.0f],NSForegroundColorAttributeName : [UIColor tc1Color]};
    [self.segmentedControl setTitleTextAttributes:normalTextAttributes forState:UIControlStateNormal];
    // 选中状态下
    NSDictionary *selctedTextAttributes = @{NSFontAttributeName : [UIFont boldSystemFontOfSize:14.0f],NSForegroundColorAttributeName : [UIColor tc5Color]};
    [self.segmentedControl setTitleTextAttributes:selctedTextAttributes forState:UIControlStateSelected];
    [self.backView addSubview:self.segmentedControl];
    
//    self.space = SCREEN_WIDTH/items.count;
    self.space = SCREEN_WIDTH / 4;
    self.itemWidth = self.space * 0.7;
    self.itemSpace = self.space * 0.15;
    
    self.tagView = [[UIView alloc] initWithFrame:CGRectMake(self.space*self.selectedIndex+self.itemSpace, 38., self.itemWidth, 2.)];
    self.tagView.backgroundColor = [UIColor tc5Color];
    [self.backView addSubview:self.tagView];
}

- (void)segmentAction:(UISegmentedControl *)segment {
    
    [UIView animateWithDuration:0.3 animations:^{
        self.tagView.frame = CGRectMake(self.space*segment.selectedSegmentIndex+self.itemSpace, 38., self.itemWidth, 2.);
    } completion:^(BOOL finished) {
        if (self.segmentControlSelectedBlock) {
            self.segmentControlSelectedBlock(segment.selectedSegmentIndex);
        }
    }];
}


@end
