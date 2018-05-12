//
//  WDBaseWebViewController.m
//  EyeProtection
//
//  Created by Jam on 15/11/19.
//  Copyright © 2015年 Jam. All rights reserved.
//

#import "WDBaseWebViewController.h"
#import "UrlParamUtil.h"
#import "SignatureManager.h"
#import "BaseWebViewProtocol.h"

#define FINISH_KEY      @"finish"
#define FORBIDDEN_KEY   @"forbidden"

@interface WDBaseWebViewController () <UIWebViewDelegate> {
    
}
@property (nonatomic, strong)UIView  *loadingBar;
@property (nonatomic, copy)  NSURL    *webUrl;


@end

@implementation WDBaseWebViewController

#pragma mark -
#pragma mark - Init

#pragma mark   正常初始化
- (instancetype)initWithUrlParameter:(NSDictionary *)parameter {
    self = [super init];
    if (self) {
    }
    return self;
}

- (instancetype) initWithURL:(NSString *)url {
    self = [super init];
    if (self) {
        self.loadUrl = url;
    }
    return self;
}

#pragma mark   需要清空指定URL的初始化
- (instancetype) initWithURL:(NSString *)url clearCookie:(NSURL *)clearURL {
    self = [super init];
    if (self) {
        self.loadUrl = url;
        [self clearCookieForURL: clearURL];
    }
    return self;
}


#pragma mark -
#pragma mark - LifeCycle
- (void) viewDidLoad {
    [super viewDidLoad];
    
    [self.view setBackgroundColor: [UIColor bc2Color]];
    self.loadSuccess = YES;
    [self setupUIViews];
    [self loadWebView];
    [self bindViewModel];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear: animated];
    //注册WebURL拦截
    [NSURLProtocol registerClass:[BaseWebViewProtocol class]];
}

- (void)viewWillDisappear:(BOOL)animated {
    //解除WebURL拦截
    [super viewWillDisappear:animated];
    [NSURLProtocol unregisterClass:[BaseWebViewProtocol class]];

}


- (void)bindViewModel {
    WS(weakSelf)
    [RACObserve([Global global], networkReachable) subscribeNext:^(NSNumber *networkEnable) {
        if(![networkEnable boolValue]) {
            [weakSelf.view showFailView: FailViewNoWifi withAction:^{
                [weakSelf loadWebView];
            }];
        }
        else {
            [weakSelf.view hiddenFailView];
        }
    }];
    
}

- (void)popBack {
    
    NSDictionary *dic = [UrlParamUtil getParamsFromUrl: self.webUrl];
    if ([dic objectForKey: FINISH_KEY]) {
        BOOL isFinish = [[dic objectForKey: FINISH_KEY] boolValue];
        if (isFinish) {
            [super popBack];
        }
        else{
            if ([self.baseWebView canGoBack]) {
                //
                [self.baseWebView goBack];
            }
            else
            {
                [super popBack];
            }
        }
    }
    else {
        if ([self.baseWebView canGoBack]) {
            //
            [self.baseWebView goBack];
        }
        else
        {
            [super popBack];
        }
    }
    
    
}

#pragma mark -
#pragma mark - Private Methods

#pragma mark   构建视图
- (void) setupUIViews {
    self.baseWebView = [[UIWebView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT - 64)];
    self.baseWebView.scalesPageToFit = YES;
    self.baseWebView.backgroundColor = [UIColor bc1Color];
    [self.view addSubview:self.baseWebView];
    self.baseWebView.delegate = self;
    self.baseWebView.opaque = NO;
    
    self.loadingBar = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 0, 2)];
    self.loadingBar.backgroundColor = [UIColor bc7Color];
    [self.view addSubview:self.loadingBar];
}

#pragma mark   加载Web
- (void) loadWebView {
    [self.view hiddenFailView];
    WS(weakSelf)
    if (![Global global].networkReachable) {
        self.loadSuccess = NO;
        [weakSelf.view showFailView: FailViewNoWifi withAction:^{
            [weakSelf loadWebView];
        }];
        return;
    }

    NSURL * webURL = [NSURL URLWithString:self.loadUrl];
    if(webURL) {
        self.loadSuccess = YES;
//        webURL = [NSURL URLWithString: @"http://www.baidu.com"];
        NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL: webURL];
//        [request setCachePolicy:NSURLRequestReloadIgnoringLocalCacheData];
        [self.baseWebView loadRequest: request];
        self.baseWebView.hidden = YES;
    }
    else {
        //URL为空
        self.loadSuccess = NO;
        [self.view showFailView:FailViewEmpty withAction:^{
            
        }];
    }
}

- (void)loadWebViewRequestHeader:(NSURLRequest *)request {
}

#pragma mark   清空指定URL的Cookie
- (void) clearCookieForURL:(NSURL *)clearURL {
    
    NSHTTPCookieStorage *storage = [NSHTTPCookieStorage sharedHTTPCookieStorage];
    for (NSHTTPCookie *cookie in [storage cookies]) {
        //删除和市民云有关的cookie
        NSString    *host       = clearURL.host;
        NSArray     *array      = [host componentsSeparatedByString:@"."];
        if (array.count >2) {
            NSString    *domain = [NSString stringWithFormat:@"%.@.%@", [array objectAtIndex: array.count - 2], [array lastObject]];
            NSRange      range  = [cookie.domain rangeOfString: domain options: NSCaseInsensitiveSearch];
            if(range.length > 0) {
                [storage deleteCookie: cookie];
            }
        }
    }
}

- (void)webViewDidStartLoad:(UIWebView *)webView
{
    [self showLoadingBar];
}

- (void)webViewDidFinishLoad:(UIWebView *)webView{
    [self finishLoadingBar];
    self.navigationItem.title = [webView stringByEvaluatingJavaScriptFromString:@"document.title"];
    self.baseWebView.hidden = NO;
    self.baseWebView.opaque = YES;
}

- (void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error
{
    [self finishLoadingBar];
}

- (BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType {

    self.webUrl = request.URL;

    NSString *urlStr = [NSString stringWithFormat:@"%@",request.URL];
    //拦截url 做跳转
    //参数中含有goback       则返回
    //参数中含有forbidden    则web橡皮条效果去除
    
    NSDictionary *dic = [UrlParamUtil getParamsFromUrl: [NSURL URLWithString: urlStr]];
    
    BOOL forbidden  = [[dic objectForKey: FORBIDDEN_KEY] boolValue];
    [self.baseWebView.scrollView setBounces: forbidden ? NO : YES];
    
    if ([[[NSURL URLWithString:urlStr] scheme] isEqualToString:WDScheme]) {
        [[BFRouter router] open:urlStr];
    }
    
    return YES;
}

#pragma mark - webview 上边框 加载进度动画
- (void)showLoadingBar
{
    WS(weakSelf);
    self.loadingBar.hidden = NO;
    weakSelf.loadingBar.frame = CGRectMake(0, 0, 0, 2);
    [UIView animateWithDuration:0.8 animations:^{
        weakSelf.loadingBar.frame = CGRectMake(0, 0, SCREEN_WIDTH/2, 2);
    } completion:^(BOOL finished) {
        [UIView animateWithDuration:2 animations:^{
            weakSelf.loadingBar.frame = CGRectMake(0, 0, SCREEN_WIDTH/2 + SCREEN_WIDTH/3, 2);
        } completion:^(BOOL finished) {
//            weakSelf.loadingBar.frame = CGRectMake(0, 0, SCREEN_WIDTH, 2);
        }];
    }];
}

- (void)finishLoadingBar
{
    WS(weakSelf);
    
    [UIView animateWithDuration:0.3 animations:^{
        weakSelf.loadingBar.frame = CGRectMake(0, 0, SCREEN_WIDTH, 2);
    } completion:^(BOOL finished) {
        self.loadingBar.hidden = YES;
    }];
}



@end
