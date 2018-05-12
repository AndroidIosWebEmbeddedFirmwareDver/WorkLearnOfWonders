//
//  MBProgressHUDHelper＋FloatView.h
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>


/*
    example 
1    [MBProgressHUDHelperFloatView showFloatView :self.view content:@"上传成功" type:FloatViewTypeNone ];
 
 
 
 2 .[MBProgressHUDHelperFloatView showFloatView :self.view content:@"上传成功" type:FloatViewTypeing ]
 [[MBProgressHUDHelperFloatView defaultHelper] HudHide];
 **/

typedef NS_ENUM(NSInteger,FloatViewType) {
    FloatViewTypeNone = 0,
    FloatViewTypeSucccess =1,
    FloatViewTypeFailed   =2,
    FloatViewTypeIng      =3
};


@interface MBProgressHUDHelperFloatView:NSObject
@property(nonatomic ,strong) UIView * BgMaskview;
@property(nonatomic ,strong) UIActivityIndicatorView *  ActivityIndicatorView;
+ (MBProgressHUDHelperFloatView *)defaultHelper;
- (void)showNOdevelopView:(UIView*)view  content:(NSString * )text;
+(void) showFloatView:(UIView*)view  content:(NSString * )text type:(FloatViewType)type;

+(void)hide;

- (void)hideNow;

@end
