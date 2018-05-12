//
//  SCInHospitalVC.m
//  SCHCPatient
//
//  Created by Po on 2017/5/8.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "SCInHospitalVC.h"

@interface SCInHospitalVC ()

@property (strong, nonatomic) UIImageView * blackImageView;
@property (strong, nonatomic) UILabel * blackLabel;

@end

@implementation SCInHospitalVC

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initInterface];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark - user-define initialization

- (void)initInterface {
    [self getBlackImageView];
    [self getBlackLabel];
    
    [self buildConstraints];
}

- (void)buildConstraints {
    __weak typeof(self) weakSelf = self;
    [_blackImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(weakSelf.view);
        make.top.mas_equalTo(weakSelf.view).offset(50);
        make.left.equalTo(weakSelf.view).offset(30);
        make.right.equalTo(weakSelf.view).offset(-30);
        
    }];
    
    [_blackLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(weakSelf.blackImageView);
        make.top.mas_equalTo(weakSelf.blackImageView.mas_bottom).offset(20);
        make.height.mas_equalTo(50);
    }];
}


#pragma mark - event

#pragma mark - function

#pragma mark - delegate

#pragma mark - notification

#pragma mark - setter

#pragma mark - getter
- (UIImageView *)getBlackImageView {
    if(!_blackImageView) {
        _blackImageView = [[UIImageView alloc] init];
        [_blackImageView setImage:[UIImage imageNamed:@"期待img"]];
        _blackImageView.contentMode = UIViewContentModeScaleAspectFill;
        [self.view addSubview:_blackImageView];
    }
    return _blackImageView;
}

- (UILabel *)getBlackLabel {
    if(!_blackLabel) {
        _blackLabel = [[UILabel alloc] init];
        [_blackLabel setNumberOfLines:2];
        [_blackLabel setFont:[UIFont systemFontOfSize:18]];
        
        [_blackLabel setTextAlignment:NSTextAlignmentCenter];
        [self.view addSubview:_blackLabel];
        
        NSMutableParagraphStyle *paraStyle = [[NSMutableParagraphStyle alloc] init];
        paraStyle.lineBreakMode = NSLineBreakByCharWrapping;
        paraStyle.alignment = NSTextAlignmentCenter;
        paraStyle.lineSpacing = 6;
        paraStyle.hyphenationFactor = 1.0;
        paraStyle.firstLineHeadIndent = 0.0;
        paraStyle.paragraphSpacingBefore = 0.0;
        paraStyle.headIndent = 0;
        paraStyle.tailIndent = 0;
        NSDictionary *dic = @{NSFontAttributeName:[UIFont systemFontOfSize:16],
                              NSParagraphStyleAttributeName:paraStyle,
                              NSKernAttributeName:@1.0f,
                              NSForegroundColorAttributeName:RGB_COLOR(72, 72, 72)
                              };
        
        NSAttributedString *attributeStr = [[NSAttributedString alloc] initWithString:@"新的服务，敬请期待\n程序员搬砖建设中" attributes:dic];
        [_blackLabel setAttributedText:attributeStr];
    }
    return _blackLabel;
}

@end
