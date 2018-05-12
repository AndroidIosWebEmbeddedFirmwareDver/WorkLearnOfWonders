//
//  FunctionIntroductionViewController.m
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/12.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "FunctionIntroductionViewController.h"
#import "FunctionIntroductionViewModel.h"

@interface FunctionIntroductionViewController ()

@property (nonatomic, strong) FunctionIntroductionViewModel *viewModel;

@end

@implementation FunctionIntroductionViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {
        self.viewModel = [FunctionIntroductionViewModel new];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self.viewModel reloadDatas];
    
    [self setupView];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc {
    
}


#pragma mark    - setupView
-(void)setupView {
    self.navigationItem.title = @"功能介绍";
    self.view.backgroundColor = [UIColor bc2Color];
    
    UIScrollView *myScrollView = [UISetupView setupScrollViewWithSuperView:self.view withDelegate:nil withPagingEnabled:NO];
    [myScrollView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    
    for (int i = 0; i < self.viewModel.images.count; i++) {
        UIImageView *imageView = [UISetupView setupImageViewWithSuperView:myScrollView withImageName:self.viewModel.images[i]];
        UIImage *image = [UIImage imageNamed:self.viewModel.images[i]];
        [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.right.equalTo(myScrollView);
            make.width.mas_equalTo(SCREEN_WIDTH);
            make.top.equalTo(myScrollView).offset(i*(SCREEN_WIDTH*image.size.width/image.size.height));
            make.height.equalTo(imageView.mas_width).multipliedBy(image.size.width/image.size.height);
            if (i == self.viewModel.images.count-1) {
                make.bottom.equalTo(myScrollView);
            }
        }];
    }
}


@end
