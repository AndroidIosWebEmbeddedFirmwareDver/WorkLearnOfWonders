//
//  WDBaseWebViewController.h
//  EyeProtection
//
//  Created by Jam on 15/11/19.
//  Copyright © 2015年 Jam. All rights reserved.
//

#import "BaseViewController.h"

@interface WDBaseWebViewController : BaseViewController<UIWebViewDelegate>

@property (nonatomic,copy)NSString * loadUrl;
@property (nonatomic, strong) UIWebView *baseWebView;
@property(nonatomic, assign)BOOL    loadSuccess;

//正常初始化
- (instancetype)initWithURL:(NSString *)url;

//需要清空指定URL的初始化
- (instancetype)initWithURL:(NSURL *)url clearCookie:(NSURL *)clearURL;

- (void) loadWebView;

@end
