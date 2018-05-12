//
//  SCThreeLoginView.m
//  SCHCPatient
//
//  Created by 杜凯 on 2016/11/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCThreeLoginView.h"
#import "ThreeLoginViewModel.h"

@interface SCThreeLoginView ()

@property (nonatomic,strong)ThreeLoginViewModel * viewModel;

@end

@implementation SCThreeLoginView
- (ThreeLoginViewModel *)viewModel {
    if (!_viewModel) {
        _viewModel = [ThreeLoginViewModel new];
        
    }
    return _viewModel;
}
- (instancetype)init {
    self = [super init];
    if (self) {
        [self setupView];
        //[self isInstall];
    }
    return self;
}
- (void)setupView{

    

}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
