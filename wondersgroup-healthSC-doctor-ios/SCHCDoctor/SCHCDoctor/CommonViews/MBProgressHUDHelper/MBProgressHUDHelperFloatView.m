//
//  MBProgressHUDHelper＋FloatView.m
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "MBProgressHUDHelperFloatView.h"



@implementation MBProgressHUDHelperFloatView

+ (MBProgressHUDHelperFloatView *)defaultHelper
{
    static MBProgressHUDHelperFloatView *_helper = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _helper = [[MBProgressHUDHelperFloatView allocWithZone:NULL] init];
    });
    return _helper;
}
#pragma 创建部分
+(void) showFloatView:(UIView*)view  content:(NSString * )text type:(FloatViewType)type
{
    [[MBProgressHUDHelperFloatView defaultHelper] showFloatView:view content: text type:type];
    
}

-(void ) showFloatView:(UIView*)view  content:(NSString * )text type:(FloatViewType)type
{
    
    
    if(self.BgMaskview)
    {

        [self.BgMaskview removeFromSuperview];
        self.BgMaskview=nil;
        
    }
    [view addSubview:self.BgMaskview];
    
    [self.BgMaskview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(view);
    }];
    
    UIView * maskview  = [[UIView alloc] initWithFrame:CGRectZero];
    maskview.backgroundColor = [UIColor colorWithHexString:@"#333333"];
    [maskview.layer setMasksToBounds:YES];
    [maskview.layer setCornerRadius:5];
    [self.BgMaskview addSubview:maskview];
    
    [maskview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.BgMaskview.mas_centerX);
        make.centerY.equalTo(self.BgMaskview.mas_centerY);
        make.height.equalTo(@100);
        make.width.equalTo(@100);
        
    }];
    
    switch (type) {
        case FloatViewTypeNone:
            [[MBProgressHUDHelperFloatView defaultHelper] HudHide];
            break;
        case FloatViewTypeSucccess:
        {
            [self opensuccess:text maskview:maskview];
            [[MBProgressHUDHelperFloatView defaultHelper] HudHide];
        }
            break;
        case FloatViewTypeFailed:
        {
            [self openfailed:text maskview:maskview];
             [[MBProgressHUDHelperFloatView defaultHelper] HudHide];
        }
            break;
        case FloatViewTypeIng:
            [self opening:text maskview:maskview];
            break;
        default:
            break;
    }
    
}


- (void)showNOdevelopView:(UIView*)view  content:(NSString * )text
{
    if(self.BgMaskview)
    {
        
        [self.BgMaskview removeFromSuperview];
        self.BgMaskview = nil;
        
    }
    [view addSubview:self.BgMaskview];
    
    [self.BgMaskview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(view);
    }];
    
    UIImageView *maskview  = [[UIImageView alloc] initWithFrame:CGRectZero];
    maskview.backgroundColor = [UIColor colorWithHexString:@"#333333"];
    [maskview.layer setMasksToBounds:YES];
    maskview.image = [UIImage imageNamed:@"功能开发中"];
    [maskview.layer setCornerRadius:5];
    [self.BgMaskview addSubview:maskview];
    
    [maskview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.BgMaskview.mas_centerX);
        make.centerY.equalTo(self.BgMaskview.mas_centerY);
        make.size.mas_equalTo(maskview.image.size);
    }];
    [[MBProgressHUDHelperFloatView defaultHelper] HudHide];
}



-(UIView*)BgMaskview
{
    if(!_BgMaskview)
    {
       _BgMaskview = [[UIView alloc] initWithFrame:CGRectZero];
        _BgMaskview.backgroundColor = [UIColor colorWithHex:0x2e7af0 alpha:0.1];
      
    }
    return _BgMaskview;
}
/***
    创建 成功弹层
 **/
-(void)opensuccess:(NSString *)text maskview:(UIView *)maskview
{
    
    UIImageView *imageView =[[UIImageView alloc] initWithFrame:CGRectZero];
    imageView.image =[UIImage imageNamed:@"uploadSuccess"];
    [maskview addSubview:imageView];
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(maskview.mas_top).offset(20);
        make.left.equalTo(maskview.mas_left).offset(28);
        make.right.equalTo(maskview.mas_right).offset(-28);
        make.bottom.equalTo(maskview.mas_bottom).offset(-40);
        
    }];
    
    UILabel * label  = [[UILabel alloc] initWithFrame:CGRectZero];
    label.text= text;
    label.font = [UIFont systemFontOfSize:14];
    label.textAlignment=NSTextAlignmentCenter;
    label.textColor = [UIColor tc0Color];
    [maskview addSubview:label];
    
    [label mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(maskview.mas_bottom).offset(-20);
        make.left.equalTo(maskview.mas_left);
        make.right.equalTo(maskview.mas_right);
        make.height.equalTo(@14);
        
    }];
    
    
    
    
}



/***
 创建 失败弹层
 **/
-(void)openfailed:(NSString *)text maskview:(UIView *)maskview
{
    
    UIImageView *imageView =[[UIImageView alloc] initWithFrame:CGRectZero];
    imageView.image =[UIImage imageNamed:@"uploadFailed"];
    [maskview addSubview:imageView];
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(maskview.mas_top).offset(20);
        make.left.equalTo(maskview.mas_left).offset(28);
        make.right.equalTo(maskview.mas_right).offset(-28);
        make.bottom.equalTo(maskview.mas_bottom).offset(-40);
        
    }];
    
    UILabel * label  = [[UILabel alloc] initWithFrame:CGRectZero];
    label.text= text;
    label.font = [UIFont systemFontOfSize:14];
    label.textAlignment=NSTextAlignmentCenter;
    label.textColor = [UIColor tc0Color];
    [maskview addSubview:label];
    
    [label mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(maskview.mas_bottom).offset(-20);
        make.left.equalTo(maskview.mas_left);
        make.right.equalTo(maskview.mas_right);
        make.height.equalTo(@14);
        
    }];


}

/***
 创建 正在上🚢
 **/
-(void)opening:(NSString *)text maskview:(UIView *)maskview
{

    
    [maskview addSubview:self.ActivityIndicatorView];
    [self.ActivityIndicatorView startAnimating];
    
    [self.ActivityIndicatorView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(maskview.mas_centerX);
        make.centerY.equalTo(maskview.mas_centerY).offset(-10);
    }];
    
    
    UILabel * label  = [[UILabel alloc] initWithFrame:CGRectZero];
    label.text= text;
    label.font = [UIFont systemFontOfSize:14];
    label.textAlignment=NSTextAlignmentCenter;
    label.textColor = [UIColor tc0Color];
    [maskview addSubview:label];
    
    [label mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(maskview.mas_bottom).offset(-20);
        make.left.equalTo(maskview.mas_left);
        make.right.equalTo(maskview.mas_right);
        make.height.equalTo(@14);
        
    }];

    
}



-(UIActivityIndicatorView *)ActivityIndicatorView
{
    if(!_ActivityIndicatorView)
    {
        _ActivityIndicatorView = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
    }
    return _ActivityIndicatorView;
}


#pragma mark 隐藏部分
-(void)HudHide
{
    
    [self performSelector:@selector(delayMethod) withObject:nil afterDelay:2.0f];
   
}
-(void)delayMethod
{
   
    if(self.BgMaskview){
        self.BgMaskview.hidden=YES;
        [self.BgMaskview removeFromSuperview];
        self.BgMaskview=nil;
    }
   
}

+(void)hide
{
    [[MBProgressHUDHelperFloatView defaultHelper] HudHide];
}

- (void)hideNow
{
    [self delayMethod];
}


@end
