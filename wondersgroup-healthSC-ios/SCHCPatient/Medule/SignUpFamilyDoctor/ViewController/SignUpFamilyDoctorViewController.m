//
//  SignUpFamilyDoctorViewController.m
//  SCHCPatient
//
//  Created by ZJW on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "SignUpFamilyDoctorViewController.h"
#import "SignUpFamilyDoctorViewModel.h"
#import <YYText/YYText.h>
#import "FamilyDoctorTeamViewController.h"

@interface SignUpFamilyDoctorViewController ()
@property (nonatomic, strong) SignUpFamilyDoctorViewModel *viewModel;
@property (nonatomic, strong) UIImageView *topImageView;

@end

@implementation SignUpFamilyDoctorViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {
        
        self.viewModel = [SignUpFamilyDoctorViewModel new];
        
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    [self setupView];
    [self bindViewModel];
    
    [LoadingView showLoadingInView:self.view];
    [self.viewModel getSignUpNumber];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [self.navigationController setNavigationBarHidden:YES animated:animated];

}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    [self.navigationController setNavigationBarHidden:NO animated:animated];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc {
    
}


#pragma mark    - setupView
-(void)setupView {
    self.view.backgroundColor = [UIColor bc2Color];
    
    WS(weakSelf)
    UIImage *image = [UIImage imageNamed:@"Bitmap"];
    self.topImageView = [UISetupView setupImageViewWithSuperView:self.view withImageName:@"Bitmap"];
    [self.topImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(self.view);
        make.height.equalTo(self.topImageView.mas_width).multipliedBy(image.size.height/image.size.width);
    }];
    
    UIButton *_navBackBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [_navBackBtn setImage:[UIImage imageNamed:@"back_white"] forState:UIControlStateNormal];
    [_navBackBtn setImageEdgeInsets:UIEdgeInsetsMake(-10, -10, 10, 10)];
    _navBackBtn.frame = CGRectMake(0, 20, 64, 64);
    [self.view addSubview:_navBackBtn];
    [[_navBackBtn rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x) {
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];

    UIImage *image1 = [UIImage imageNamed:@"签约家庭医生"];
    UIImageView *centerImageView = [UISetupView setupImageViewWithSuperView:self.view withImageName:@"签约家庭医生"];
    [centerImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.topImageView.mas_bottom).offset(20);
        make.height.equalTo(centerImageView.mas_width).multipliedBy(image1.size.height/image1.size.width);
    }];

    
    UIView *btnView = [UISetupView setupViewWithSuperView:self.view withBackGroundColor:[UIColor bc1Color]];
    [btnView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.left.right.equalTo(self.view);
        make.height.mas_equalTo(63);
    }];
    
    UIButton *submitBtn = [UISetupView setupButtonWithSuperView:btnView withTitleToStateNormal:@"我要签约" withTitleColorToStateNormal:[UIColor tc0Color] withTitleFontSize:16 withAction:^(UIButton *sender) {
        [weakSelf signup];
    }];
    submitBtn.backgroundColor = [UIColor bc7Color];
    [submitBtn setCornerRadius:5];
    [submitBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.center.equalTo(btnView);
        make.left.equalTo(btnView).offset(15);
        make.height.mas_equalTo(44);
    }];
    
}

#pragma mark    - bindViewModel
-(void)bindViewModel {
    
    WS(weakSelf)
    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *type) {
        if ([type intValue] == 0) {
            return ;
        }
        [LoadingView hideLoadinForView:self.view];
        
        FailViewType failType = FailViewUnknow;
        switch ([type intValue]) {
            case RequestCompeleteEmpty:{
            }
                break;
            case RequestCompeleteNoWifi:
                failType = FailViewNoWifi;
                break;
            case RequestCompeleteError:
                failType = FailViewError;
                break;
            case RequestCompeleteSuccess: {
                [weakSelf.view hiddenFailView];
                
                [weakSelf reloadView];
                
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow && failType != FailViewEmpty) {
            
        }
    }];
    
}

#pragma mark    - method
-(void)reloadView {
    
    YYLabel *yylabel = [self getYYLabelwithString:[NSString stringWithFormat:@"已有 %@ 个家庭医生团队入驻该平台",self.viewModel.doctorNumber] withTextRange:NSMakeRange(3, [self.viewModel.doctorNumber stringValue].length)];
    [yylabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.topImageView).offset(AdaptiveFrameHeight(75));
        make.centerX.equalTo(self.topImageView);
    }];

    YYLabel *yylabel1 = [self getYYLabelwithString:[NSString stringWithFormat:@"为 %@ 个家庭提供家庭医生服务",self.viewModel.familyNumber] withTextRange:NSMakeRange(2, [self.viewModel.familyNumber stringValue].length)];
    [yylabel1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(yylabel.mas_bottom).offset(AdaptiveFrameHeight(15));
        make.centerX.equalTo(self.topImageView);
    }];
    
}


-(void)signup {
    FamilyDoctorTeamViewController *vc = [FamilyDoctorTeamViewController new];
    
    [self.navigationController pushViewController:vc animated:YES];
}

-(YYLabel *)getYYLabelwithString:(NSString *)string withTextRange:(NSRange)textRange{
    NSMutableAttributedString *text = [[NSMutableAttributedString alloc] initWithString:string];
    
    text.yy_font = [UIFont boldSystemFontOfSize:16];
    text.yy_color = [UIColor tc0Color];
    [text yy_setFont:[UIFont fontWithName:@"Helvetica-BoldOblique" size:32] range:textRange];
    [text addAttribute:NSUnderlineStyleAttributeName value:[NSNumber numberWithInteger:NSUnderlineStyleSingle] range:textRange];
    
    YYLabel *yylabel = [YYLabel new];
    yylabel.attributedText = text;
    [self.topImageView addSubview:yylabel];

    return yylabel;
}

@end
