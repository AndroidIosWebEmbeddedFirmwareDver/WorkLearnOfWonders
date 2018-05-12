//
//  HealthHomeFunctionCell.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HealthHomeFunctionCell.h"
#import "HHFunctionLeftButton.h"
#import "HHFunctionRightButton.h"


@interface HealthHomeFunctionCell ()

@property (nonatomic, strong) HHFunctionLeftButton *leftButton;
@property (nonatomic, strong) HHFunctionRightButton *rightTopButton;
@property (nonatomic, strong) HHFunctionRightButton *rightBottomButton;

@end

@implementation HealthHomeFunctionCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier  {
    
    return [self initWithStyle:style reuseIdentifier:reuseIdentifier noSubTitle:NO];
}

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier noSubTitle:(BOOL)noSub {
    
    if (self == [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self prepareUIWithNoSub:noSub];
        [self bindRac];
    }
    return self;
}

- (void)prepareUIWithNoSub:(BOOL)noSub {
    
    self.contentView.backgroundColor = [UIColor bc2Color];
    
    UIView *backView = [UIView new];
    backView.backgroundColor = [UIColor whiteColor];
    [self.contentView addSubview:backView];
    
    self.leftButton = [[HHFunctionLeftButton alloc] initWithNoSubTitle:noSub];
    [self.leftButton addTarget:self action:@selector(leftButtonAction:) forControlEvents:UIControlEventTouchUpInside];
    [backView addSubview:self.leftButton];
    
    UIView *verticalLine = [UIView new];
    verticalLine.backgroundColor = [UIColor dc4Color];
    [backView addSubview:verticalLine];
    
    self.rightTopButton = [[HHFunctionRightButton alloc] init];
    [self.rightTopButton addTarget:self action:@selector(rightTopButtonAction:) forControlEvents:UIControlEventTouchUpInside];
    
    [backView addSubview:self.rightTopButton];
    
    UIView *horizontalLine = [UIView new];
    horizontalLine.backgroundColor = [UIColor dc4Color];
    [backView addSubview:horizontalLine];
    
    self.rightBottomButton = [[HHFunctionRightButton alloc] init];
    [self.rightBottomButton addTarget:self action:@selector(rightBottomButtonAction:) forControlEvents:UIControlEventTouchUpInside];
    [backView addSubview:self.rightBottomButton];
    
    WS(weakSelf)
    
    if (noSub) {
        [backView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.left.right.equalTo(weakSelf.contentView);
            make.bottom.equalTo(weakSelf.contentView).offset(-10);
        }];
    }
    else {
        [backView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.right.equalTo(weakSelf.contentView);
            make.top.equalTo(weakSelf.contentView).offset(5);
            make.bottom.equalTo(weakSelf.contentView).offset(-5);
        }];
    }
    
    [self.leftButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.left.equalTo(backView);
        make.right.equalTo(verticalLine.mas_left);
    }];
    
    [verticalLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.width.mas_equalTo(@0.5);
        make.top.bottom.equalTo(backView);
        make.centerX.equalTo(backView);
    }];
    
    [self.rightTopButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.right.equalTo(backView);
        make.left.equalTo(verticalLine.mas_right);
        make.bottom.equalTo(horizontalLine.mas_top);
    }];
    
    [horizontalLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.height.mas_equalTo(@0.5);
        make.right.equalTo(backView);
        make.left.equalTo(verticalLine.mas_right);
        make.centerY.equalTo(backView);
    }];
    
    [self.rightBottomButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.bottom.equalTo(backView);
        make.top.equalTo(horizontalLine.mas_bottom);
        make.left.equalTo(weakSelf.rightTopButton);
    }];
}

- (void)bindRac {
    
    WS(weakSelf)
    [RACObserve(self, rightBottomFuncitonInvalid) subscribeNext:^(NSNumber *x) {
        
        weakSelf.rightBottomButton.invalid = [x boolValue];
    }];
}

- (void)setCellWithArray:(NSArray<FunctionModel *> *)array {
    
    if (array.count > 2) {
        FunctionModel *leftModel = [array objectAtIndex:0];
        self.leftButton.imageString = leftModel.imgUrl;
        self.leftButton.titleString = leftModel.mainTitle;
        self.leftButton.subTitleString = leftModel.subTitle;
        
        FunctionModel *rightTopModel = [array objectAtIndex:1];
        self.rightTopButton.imageString = rightTopModel.imgUrl;
        self.rightTopButton.titleName = rightTopModel.mainTitle;

        FunctionModel *rightBottomModel = [array objectAtIndex:2];
        self.rightBottomButton.imageString = rightBottomModel.imgUrl;
        self.rightBottomButton.titleName = rightBottomModel.mainTitle;
    }
}

#pragma mark - button action

- (void)leftButtonAction:(id)sender {
    
    if (self.leftButtonBlock) {
        self.leftButtonBlock();
    }
}

- (void)rightTopButtonAction:(id)sender {
    
    if (self.rightTopButtonBlock) {
        self.rightTopButtonBlock();
    }
}

- (void)rightBottomButtonAction:(id)sender {
    
    if (self.rightBottomButtonBlock) {
        self.rightBottomButtonBlock();
    }
}


@end
