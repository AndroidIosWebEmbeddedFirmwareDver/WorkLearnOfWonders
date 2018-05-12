//
//  ShareAcionSheet.m
//  VaccinePatient
//
//  Created by ZJW on 16/7/27.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ShareAcionSheet.h"
//#import <TencentOpenAPI/QQApiInterface.h>
#import "WXApi.h"

@interface ShareAcionSheet ()
{
    NSArray *iconDatas;
    NSArray *titlesDatas;
    CGFloat space;
    BOOL _isHaveCustom;
}
@property (nonatomic, strong) UIWindow *keyWindow;
@property (nonatomic, strong) UIWindow *shareWindow;

@property (nonatomic, strong) UIButton *backBtn;
@property (nonatomic, strong) UIView *showView;

@property (nonatomic, strong) NSMutableArray *topViews;//上层view
@property (nonatomic, strong) NSMutableArray *bottomViews;//下层view
@end

@implementation ShareAcionSheet

-(instancetype)init {
    self = [super init];
    if (self) {
        [self setupView];
    }
    return self;
}

-(instancetype)initWithCustomIcons:(NSArray *)icons withCustomTitles:(NSArray *)titles{
    self = [super init];
    if (self) {
        iconDatas = icons;
        titlesDatas = titles;
        [self setupView];
    }
    return self;
}

-(NSMutableArray *)topViews {
    if (_topViews == nil) {
        _topViews = [NSMutableArray array];
    }
    return _topViews;
}

-(NSMutableArray *)bottomViews {
    if (_bottomViews == nil) {
        _bottomViews = [NSMutableArray array];
    }
    return _bottomViews;
}

-(void)setupView {

    _isHaveCustom = NO;
    if (iconDatas.count>0 && titlesDatas.count>0 && iconDatas.count == titlesDatas.count) {
        _isHaveCustom = YES;
    }
    
    self.keyWindow = [[UIApplication sharedApplication].delegate window];
    
    self.shareWindow = [[UIWindow alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)];
    self.shareWindow.backgroundColor = [UIColor clearColor];
    self.frame = self.shareWindow.frame;
    [self.shareWindow addSubview:self];
    [self.shareWindow makeKeyAndVisible];

    WS(weakSelf)
    self.backBtn = [UISetupView setupButtonWithSuperView:self withTitleToStateNormal:@"" withTitleColorToStateNormal:nil withTitleFontSize:0 withAction:^(UIButton *sender) {
    
        [weakSelf dissmiss];
        
    }];
    self.backBtn.backgroundColor = RGBA_COLOR(0, 0, 0, 0.5);
    [self.backBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(weakSelf);
    }];
    
    self.showView = [UISetupView setupViewWithSuperView:self withBackGroundColor:[UIColor clearColor]];
    self.showView.frame = CGRectMake(0, SCREEN_HEIGHT, SCREEN_WIDTH, 180);

//    [self.showView mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.right.equalTo(weakSelf);
//        make.top.equalTo(weakSelf.mas_bottom).offset(0);
//        if (_isHaveCustom) {
//            make.height.mas_equalTo(280);
//        }else {
//            make.height.mas_equalTo(211);
//        }
//    }];
    
    UIView *topView = [UISetupView setupViewWithSuperView:self.showView withBackGroundColor:[UIColor whiteColor]];
    [topView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.showView);
        make.right.equalTo(weakSelf.showView);
        make.top.equalTo(weakSelf.showView);
        if (_isHaveCustom) {
            make.height.mas_equalTo(215);
        }else {
            make.height.mas_equalTo(132);
        }
    }];
    
    UIView *line = [UISetupView setupLineViewWithSuperView:self.showView];
    [line mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(weakSelf.showView);
        make.height.mas_equalTo(0.5);
        make.top.equalTo(topView.mas_bottom);
    }];
    
    UIButton *cancelBtn = [UISetupView setupButtonWithSuperView:self.showView withTitleToStateNormal:@"取消" withTitleColorToStateNormal:[UIColor tc1Color] withTitleFontSize:16 withAction:^(UIButton *sender) {
        
        [weakSelf dissmiss];
        
        if (weakSelf.cancelBlock) {
            weakSelf.cancelBlock();
        }
        
    }];
    cancelBtn.backgroundColor = [UIColor whiteColor];
    [cancelBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(topView);
        make.top.equalTo(topView.mas_bottom);
        make.height.mas_equalTo(48);
    }];
    
    NSMutableArray *pictures = [NSMutableArray array];
    NSMutableArray *titles = [NSMutableArray array];
    
//    [pictures addObject:@"新浪微博"];
//    [titles addObject:@"新浪微博"];
//    
//    if ([WXApi isWXAppInstalled]) {
//        [pictures addObject:@"微信好友"];
//        [titles addObject:@"微信好友"];
//
//        [pictures addObject:@"微信朋友圈"];
//        [titles addObject:@"微信朋友圈"];
//    }
//
//    if ([QQApiInterface isQQInstalled]) {
//        [pictures addObject:@"QQ"];
//        [titles addObject:@"QQ"];
//    }

    
//    if ([WXApi isWXAppInstalled]) {
    [pictures addObject:@"微信好友"];
    [titles addObject:@"微信好友"];

        [pictures addObject:@"微信朋友圈"];
        [titles addObject:@"微信朋友圈"];
        
//    }
    
//    if ([QQApiInterface isQQInstalled]) {
//        [pictures addObject:@"QQ"];
//        [titles addObject:@"QQ"];
//    }

//    [pictures addObject:@"新浪微博"];
//    [titles addObject:@"新浪微博"];
    
    
    space = ((SCREEN_WIDTH-50*pictures.count)/(pictures.count+1));

    CGFloat height = 0;
    
    UIScrollView *shareScrollView = [UISetupView setupScrollViewWithSuperView:topView withDelegate:nil withPagingEnabled:YES];
    shareScrollView.backgroundColor = [UIColor clearColor];
    [shareScrollView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(topView);
        make.right.equalTo(topView);
        make.top.equalTo(topView);
        make.bottom.equalTo(topView);
    }];

    for (int i = 0; i < pictures.count; i++) {

        [self setupShareBtnWithSuperView:shareScrollView withTopView:shareScrollView withNumber:i withButtonTag:100+i withIsFirstRow:YES withIcon:pictures[i] withTitle:titles[i] withLast:(i == pictures.count-1) withDelay:i*0.1 withAction:^(NSString *title,UIButton *sender) {
            ShareType type = ShareTypeOther;

            if ([title isEqualToString:@"新浪微博"]) {
                type = ShareTypeSina;
            }
            else if ([title isEqualToString:@"微信朋友圈"]) {
                type = ShareTypeWeiXinFriendCircle;
                if (![WXApi isWXAppInstalled]) {
                    [MBProgressHUDHelper showHudWithText: @"微信客户端未安装"];
                    return;
                }
            }
            else if ([title isEqualToString:@"微信好友"]) {
                type = ShareTypeWeiXinFriend;
                if (![WXApi isWXAppInstalled]) {
                    [MBProgressHUDHelper showHudWithText: @"微信客户端未安装"];
                    return;
                }
            }
            else if ([title isEqualToString:@"QQ"]) {
                type = ShareTypeQQ;
            }
            
            if (weakSelf.shareBlock) {
                weakSelf.shareBlock(type);
            }
            [weakSelf dissmiss];
        }];
        
        height = 20+50+5+14+20;
    }
    
    if (_isHaveCustom) {
        UIView *line = [UISetupView setupLineViewWithSuperView:topView];
        [line mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(topView).offset(15);
            make.right.equalTo(topView).offset(-15);
            make.top.mas_equalTo(height);
            make.height.mas_equalTo(0.5);
        }];
        
        UIScrollView *customScrollView = [UISetupView setupScrollViewWithSuperView:topView withDelegate:nil withPagingEnabled:YES];
        customScrollView.backgroundColor = [UIColor clearColor];
        [customScrollView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(topView);
            make.right.equalTo(topView);
            make.top.equalTo(line.mas_bottom);
            make.bottom.equalTo(topView);
        }];
        
        for (int i = 0; i < titlesDatas.count; i++) {
            [self setupShareBtnWithSuperView:customScrollView withTopView:customScrollView withNumber:i withButtonTag:1000+i withIsFirstRow:NO withIcon:iconDatas[i] withTitle:titlesDatas[i] withLast:(i == titlesDatas.count-1) withDelay:i*0.1 withAction:^(NSString *title,UIButton *sender) {
                if (weakSelf.customBlock) {
                    weakSelf.customBlock(sender.tag);
                }
                [weakSelf dissmiss];
            }];
        }
    }
}

-(void)show{
    [self performSelector:@selector(performShowView) withObject:nil afterDelay:0.2];
}

-(void)performShowView {
    WS(weakSelf)
    
    [UIView animateWithDuration:0.2 animations:^{
        
        if (_isHaveCustom) {
            weakSelf.showView.frame = CGRectMake(0, SCREEN_HEIGHT-280, SCREEN_WIDTH, 280);
        }else {
            weakSelf.showView.frame = CGRectMake(0, SCREEN_HEIGHT-180, SCREEN_WIDTH, 180);
        }

        
//        [self.showView mas_updateConstraints:^(MASConstraintMaker *make) {
//            if (_isHaveCustom) {
//                make.top.equalTo(weakSelf.mas_bottom).offset(-280);
//            }else {
//                make.top.equalTo(weakSelf.mas_bottom).offset(-211);
//            }
//        }];
//        [weakSelf.showView layoutIfNeeded];
        
    } completion:^(BOOL finished) {

    }];
    
//    [self startAnimation];
}

     
-(void)dissmiss {
    WS(weakSelf)

    [UIView animateWithDuration:0.1 animations:^{
        
//        [self.showView mas_updateConstraints:^(MASConstraintMaker *make) {
//            make.top.equalTo(weakSelf.mas_bottom).offset(0);
//        }];
//        [self.showView layoutIfNeeded];
        weakSelf.showView.frame = CGRectMake(0, SCREEN_HEIGHT, SCREEN_WIDTH, 180);

        
    } completion:^(BOOL finished) {
        [weakSelf removeFromSuperview];
        [weakSelf.shareWindow resignKeyWindow];
        weakSelf.shareWindow = nil;
        
        [weakSelf.keyWindow makeKeyAndVisible];
    }];
}

-(void)setupShareBtnWithSuperView:(UIView *)superView withTopView:(UIView *)topView withNumber:(NSInteger)number withButtonTag:(NSInteger)tag withIsFirstRow:(BOOL)isFirstRow withIcon:(NSString *)icon withTitle:(NSString *)title withLast:(BOOL)isLast withDelay:(CGFloat)delay withAction:(void(^)(NSString *title,UIButton *sender))action{
    
    UIView *bgView = [UISetupView setupViewWithSuperView:superView withBackGroundColor:[UIColor clearColor]];
    CGFloat top = 0;
    if (_isHaveCustom) {
        if (isFirstRow) {
            top = 32;
        }else {
            top = 32;
        }
    }else {
        top = 32;
    }
    
    bgView.frame = CGRectMake(space+number*(space+50), top, 50, 72);
    bgView.alpha = 1;

    if (isFirstRow) {
        [self.topViews addObject:bgView];
    }else {
        [self.bottomViews addObject:bgView];
    }
//    [bgView mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(superView).offset(space+number*(space+50));
//        if (_isHaveCustom) {
//            if (isFirstRow) {
//                make.top.equalTo(topView).offset(20-20);
//            }else {
//                make.top.equalTo(topView.mas_bottom).offset(20-20);
//            }
//        }else {
//            make.top.equalTo(topView).offset(35-20);
//        }
//        make.height.mas_equalTo(72);
//        make.width.mas_equalTo(50);
//        if (isLast) {
//            make.right.equalTo(superView).offset(-space);
//        }
//    }];
    
    UIImageView *iconImageView = [UISetupView setupImageViewWithSuperView:bgView withImageName:icon];
    [iconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bgView);
        make.top.equalTo(bgView);
        make.size.mas_equalTo(CGSizeMake(50, 50));
    }];
    
    UILabel *titleLabel = [UISetupView setupLabelWithSuperView:bgView withText:title withTextColor:[UIColor tc1Color] withFontSize:12];
    [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(iconImageView);
        make.top.equalTo(iconImageView.mas_bottom).offset(15);
    }];
    
    UIButton *btn = [UISetupView setupButtonWithSuperView:bgView withTitleToStateNormal:@"" withTitleColorToStateNormal:nil withTitleFontSize:0 withAction:^(UIButton *sender) {
        action(title,sender);
    }];
    btn.tag = tag;
    [btn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.width.top.height.equalTo(bgView);
    }];

}

-(void)startAnimation {
    
    for (int i = 0; i < self.topViews.count; i++) {
        UIView *bgView = self.topViews[i];
        
        [UIView animateWithDuration:1 delay:i*0.1 usingSpringWithDamping:0.5 initialSpringVelocity:0 options:UIViewAnimationOptionAllowUserInteraction animations:^{
            bgView.alpha = 1;
            CGFloat top = 0;
            if (_isHaveCustom) {
                top = 20;
            }else {
                top = 35;
            }
            bgView.frame = CGRectMake(space+i*(space+50), top, 50, 72);
        } completion:^(BOOL finished) {
            
        }];
    }

    for (int i = 0; i < self.bottomViews.count; i++) {
        UIView *bgView = self.bottomViews[i];
        
        [UIView animateWithDuration:1 delay:i*0.1 usingSpringWithDamping:0.5 initialSpringVelocity:0 options:UIViewAnimationOptionAllowUserInteraction animations:^{
            bgView.alpha = 1;
            CGFloat top = 0;
            if (_isHaveCustom) {
                top = 20;
            }else {
                top = 35;
            }
            bgView.frame = CGRectMake(space+i*(space+50), top, 50, 72);
        } completion:^(BOOL finished) {
            
        }];
    }

}


@end
