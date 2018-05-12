//
//  PerfectInformationView.m
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "PerfectInformationView.h"
#import "WDAlertView.h"
#import "UserService.h"
#import "SCTrueNameModel.h"
#import "SCCertificationResultVC.h"
#import "SCCertificationFixedViewController.h"
@implementation PerfectInformationView

+ (void)showPerfectInformationAlertIsSuccess:(void(^)(BOOL))success{
    
    if ([UserManager manager].verificationStatus == 3) {
        success(NO);
    }else {
        UINavigationController *nav = [BFRouter router].navi;
        [LoadingView showLoadingInView:nav.view];
        [[UserService service] requestTureNameType:^(SCTrueNameModel *tureNameModel) {
            [LoadingView hideLoadinForView:nav.view];
            if ([tureNameModel.status isEqualToString:@"3"]) {
                success(NO);
            }else {
                [[self class] showAlertView];
                success(YES);
            }
        } failure:^(NSError *error) {
            [LoadingView hideLoadinForView:nav.view];
            [MBProgressHUDHelper showHudWithText:error.localizedDescription];
            success(YES);
        }];
    }
}

//+ (UIViewController *)currentViewController {
//    UIViewController *resultVC = nil;
//    UIWindow *window = [[UIApplication sharedApplication] keyWindow];
//    if (window.windowLevel != UIWindowLevelNormal) {
//        NSArray *windows = [[UIApplication sharedApplication] windows];
//        for (UIWindow *visibleWindow in windows) {
//            if (visibleWindow.windowLevel == UIWindowLevelNormal) {
//                window = visibleWindow;
//                break;
//            }
//        }
//    }
//    
//    UIView *frontView = [[window subviews]objectAtIndex:0];
//    id currentResponse = [frontView nextResponder];
//    if ([currentResponse isKindOfClass:[UIViewController class]]) {
//        resultVC = currentResponse;
//    }else {
//        resultVC = window.rootViewController;
//    }
//    return resultVC;
//}
    
+ (void)showAlertView{
    UINavigationController *nav = [BFRouter router].navi;
    WDAlertView *alert = [[WDAlertView alloc]initWithNavigationController:nav withType:WDAlertViewTypeTwo];
    [alert reloadTitle:@"您的账户未实名认证" content:@"实名认证后可获取更多服务"];
    [alert.submitBtn setTitle:@"立即认证" forState:UIControlStateNormal];
    [alert.cancelBtn setTitle:@"暂不认证" forState:UIControlStateNormal];
    alert.submitBlock = ^(WDAlertView *view) {
        [LoadingView showLoadingInView:nav.view];
        [[UserService service] requestTureNameType:^(SCTrueNameModel *tureNameModel) {
            [LoadingView hideLoadinForView:nav.view];
            if ([tureNameModel.status isEqualToString:@"0"]) {
                SCCertificationFixedViewController *vc = [[SCCertificationFixedViewController alloc] init];
                vc.hidesBottomBarWhenPushed = YES;
                [nav pushViewController:vc animated:YES];
                nav.navigationBar.hidden = NO;
            }
            if ([tureNameModel.status isEqualToString:@"1"]) {
                // 认证失败
                SCCertificationResultVC *vc = [[SCCertificationResultVC alloc] init];
                vc.type = CertificationFail;
                vc.model = tureNameModel;
                vc.hidesBottomBarWhenPushed = YES;
                [nav pushViewController:vc animated:NO];
                vc.navigationController.navigationBar.hidden = NO;
            }
            if ([tureNameModel.status isEqualToString:@"2"]) {
                SCCertificationResultVC *vc = [[SCCertificationResultVC alloc] init];
                vc.type = CertificationIng;
                vc.model = tureNameModel;
                vc.hidesBottomBarWhenPushed = YES;
                [nav pushViewController:vc animated:YES];
                vc.navigationController.navigationBar.hidden = NO;
            }
            
        } failure:^(NSError *error) {
            [LoadingView hideLoadinForView:nav.view];

        }];
        [view dismiss];
    };
    
    alert.cancelBlock = ^(WDAlertView *view) {
    
        [view dismiss];
    };
 
    [alert showViewWithHaveBackAction:YES withHaveBackView:YES];
}

@end
