//
//  BFRouter.m
//  BFRouter
//
//  Created by maorenchao on 16/4/22.
//  Copyright © 2016年 dudua. All rights reserved.
//

#import "BFRouter.h"
#import "AppDelegate.h"
#import "PerfectInformationView.h"

static NSString *configPath = @"router";

@implementation BFRouterPath



@end



@interface BFRouter()
@property (nonatomic, strong) NSDictionary *gotoMap;
@property (nonatomic, strong) id<BFRouterPassParameterProtocal> saveObject;

@end
@implementation BFRouter
+ (void)setupCoifigPath:(NSString *)path
{
    configPath = path;
}

- (instancetype)init
{
    self = [super init];
    if (self) {
        [self initGotoMap];
    }
    return self;
}

+ (instancetype)router{
    static BFRouter *instance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance =  [ BFRouter new];
//        [instance initGotoMap];
    });
    return instance;
}

- (void)initGotoMap
{
    NSString *plistPath = [[NSBundle mainBundle] pathForResource:configPath ofType:@"plist"];
    NSMutableDictionary *maplist = [[[NSMutableDictionary alloc] initWithContentsOfFile:plistPath] objectForKey:@"maplist"];
    NSMutableDictionary *mapDic = [[NSMutableDictionary alloc] init];
    
    for (NSDictionary *dic in maplist) {
        if ([dic objectForKey:@"urlPage"]) {
            [mapDic setObject:dic forKey:[dic objectForKey:@"urlPage"]];
        }
    }
    self.gotoMap = mapDic;
}

- (void)open:(NSString *)url
{
    if (self.canOpenURLBlock && !self.canOpenURLBlock(url)) {
        return;
    }
    if (!self.navi) {
        return;
    }
    
    NSLog(@"%@",[[NSURL URLWithString:url] scheme]);
    if ([[[NSURL URLWithString:url] scheme] isEqualToString:WDScheme]) {
      
            [self openPageWithAppInnerUrl:url];
    }
    else if([[[NSURL URLWithString:url] scheme] isEqualToString:@"http"] ||
            [[[NSURL URLWithString:url] scheme] isEqualToString:@"https"])
    {
        [self openH5PageWithUrl:url];
    }
    else
    {
        if(![UserManager manager].isLogin) {
            [[VCManager manager] presentLoginViewController: YES];
        }
        else {
            [self openPageWithAppPathURL:url];
        }
    }
}

- (void)openH5PageWithUrl:(NSString *)url
{
    //open h5 view
    NSString *targetClassString;
    UIViewController<BFRouterPassUrlProtocal> *viewController;
    if (self.classWithH5Block) {
        targetClassString = self.classWithH5Block(url);
        viewController = [[NSClassFromString(targetClassString) alloc] initWithURL:url];
        viewController.hidesBottomBarWhenPushed = YES;
        [self.navi pushViewController:viewController animated:YES];
        return;
    }
    NSDictionary *dic = [self.gotoMap objectForKey:@"webview"];
    if (dic) {
        targetClassString = [dic objectForKey:@"object"];
        if (targetClassString) {
            viewController = [[NSClassFromString(targetClassString) alloc] initWithURL:url];
            viewController.hidesBottomBarWhenPushed = YES;
            [self.navi pushViewController:viewController animated:YES];
        }
    }
}

- (void)openPageWithAppInnerUrl:(NSString *)url
{
    BFRouterPath *routerPath = [self getRouterPathFromInnerURL:url];
    if (routerPath != nil) {
        
        NSDictionary *dic = routerPath.parameter;
        
        if ([dic objectForKey:@"loginOrVerify"]) {//0:不需要登录，1:需登录,2:需实名制
            int type = [[dic objectForKey:@"loginOrVerify"] intValue];
            switch (type) {
                case 0:
                {
                    [self openPageWithRouterPath:routerPath];
                }
                    break;
                case 1:
                {
                    if(![UserManager manager].isLogin) {
                        [[VCManager manager] presentLoginViewController: YES];
                        return;
                    }
                    [self openPageWithRouterPath:routerPath];
                }
                    break;
                case 2:
                {
                    if(![UserManager manager].isLogin) {
                        [[VCManager manager] presentLoginViewController: YES];
                        return;
                    }

                    [PerfectInformationView showPerfectInformationAlertIsSuccess:^(BOOL success) {
                        if (!success) {
                            [self openPageWithRouterPath:routerPath];
                        }
                        
                    }];
                
                }
                    break;
                    
                default:
                {
                    [self openPageWithRouterPath:routerPath];
                }
                    break;
            }
            
        }else {
            [self openPageWithRouterPath:routerPath];
        }
        
//            BOOL  needlogin = [[dic objectForKey:@"needLogin"] boolValue];
//            if(needlogin) {
//
//                if(![UserManager manager].isLogin) {
//                    [[VCManager manager] presentLoginViewController: YES];
//                    return;
//                }
//            }
//            
//            BOOL needVerification = [[dic objectForKey:@"needVerification"] boolValue];
//            if (needVerification) {
//                if ([PerfectInformationView showPerfectInformationAlert]) {
//                    return;
//                }
//            }
        
//        [self openPageWithRouterPath:routerPath];
    }
}

- (void)openPageWithAppPathURL:(NSString *)url
{
    BFRouterPath *routerPath = [self getRouterPathAppPathURL:url];
    if (routerPath != nil) {
        [self openPageWithRouterPath:routerPath];
    }
}

- (void)openPageWithRouterPath:(BFRouterPath *)routerPath
{
    id<BFRouterPassParameterProtocal> viewController = [self createViewControllerWithRouterPath:routerPath];    
    

    if (routerPath.behavior == BFActionPopBehavior) {
        for (int i = 0; i<routerPath.popCount; i++) {
            if (!viewController && i == routerPath.popCount-1) {
                [self.navi popViewControllerAnimated:YES];
            }
            else
            {
                [self.navi popViewControllerAnimated:NO];
            }
        }
    }
    else if (routerPath.behavior == BFActionPopToRootBehavior) {
        [self.navi popToRootViewControllerAnimated:viewController?NO:YES];
    }
    
    if (![viewController isKindOfClass:[UIViewController class]]) {
        return;
    }

    if (viewController) {
        if ([self exist:(UIViewController *)viewController]) {
            NSMutableArray *array = [NSMutableArray arrayWithArray:self.navi.viewControllers];
            
            [array replaceObjectAtIndex:array.count-1 withObject:viewController];
            [self.navi setViewControllers:[array copy]];
            return;
        }
        [self.navi pushViewController:(UIViewController *)viewController animated:YES];
    }
}

- (id<BFRouterPassParameterProtocal>)createViewControllerWithRouterPath:(BFRouterPath *)routerPath
{
    if (routerPath.path.length == 0) {
        return nil;
    }
//    UIViewController<BFRouterPassParameterProtocal> *viewController;
    self.saveObject = nil;

    NSDictionary *dic = [self.gotoMap objectForKey:routerPath.path];
    if (dic) {
        NSString *targetClassString = [dic objectForKey:@"object"];
        if (targetClassString) {
            NSDictionary *parameterReflectDic = [dic objectForKey:@"parameter"];
            NSArray *keys = [parameterReflectDic allKeys];
            NSMutableDictionary *params = [NSMutableDictionary new];
            for (NSString *key in keys) {
                if ([routerPath.parameter objectForKey:key] && [parameterReflectDic objectForKey:key]) {
                    [params setObject:[routerPath.parameter objectForKey:key] forKey:[parameterReflectDic objectForKey:key]];
                }
            }
            if (params.allKeys.count > 0) {
                self.saveObject = [[NSClassFromString(targetClassString) alloc] initWithUrlParameter:params];
            }
            else
            {
                self.saveObject = [[NSClassFromString(targetClassString) alloc] init];
            }
            if ([self.saveObject isKindOfClass:[UIViewController class]]) {
                ((UIViewController *)self.saveObject).hidesBottomBarWhenPushed = YES;
            }
        }
    }
    return self.saveObject;
}

#pragma mark - 根据path获取pageclass
- (NSString *)classWithPath:(NSString *)path
{
    NSDictionary *dic = [self.gotoMap objectForKey:path];
    if (dic) {
        NSString *targetClassString = [dic objectForKey:@"object"];
        return targetClassString;
    }
    return nil;
}

#pragma mark -获取class所在navi栈中的index
- (NSInteger)indexOfClassInNavi:(Class)class
{
    if (class == nil) {
        return 0;
    }
    for (int i = 0;i<self.navi.viewControllers.count;i++) {
        UIViewController *vc = [self.navi.viewControllers objectAtIndex:i];
        if ([vc isKindOfClass:class]) {
            return self.navi.viewControllers.count - 1 - i;
        }
    }
    return 0;
}

#pragma mark - TOOL Method
#pragma mark 解析app WDScheme 跳转url
- (BFRouterPath *)getRouterPathFromInnerURL:(NSString *)url
{
    if (url.length == 0) {
        return nil;
    }
    BFRouterPath *routerPath = [[BFRouterPath alloc] init];
    // 不能用 [url path] 是因为会先decode
    NSArray *array = [url componentsSeparatedByString:@"/"];
    NSMutableArray *pathComponents = [array mutableCopy];
    
    if (pathComponents.count >= 4) {
        // 删除 前面的 com.wondersgroup.healthcloud://patient
        [pathComponents removeObjectsAtIndexes:[NSIndexSet indexSetWithIndexesInRange:NSMakeRange(0, 3)]];
        // 删除最后的 空字符串
        if ([pathComponents.lastObject isKindOfClass:[NSString class]] &&
            [pathComponents.lastObject isEqualToString:@""]) {
            [pathComponents removeLastObject];
        }
    }
    
    if (pathComponents.count <1) {
        return nil;
    }
    
    // 去掉 编码
    for (int i = 0; i < pathComponents.count; i++) {
        id content = pathComponents[i];
        if ([content isKindOfClass:[NSString class]]) {
            NSString *newContent = [content stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
            [pathComponents replaceObjectAtIndex:i withObject:newContent];
        }
    }
    
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    NSString *target = pathComponents[0];
    if (pathComponents.count > 1) {
        target = [pathComponents lastObject];
    }
    
    // 分离 参数
    NSMutableArray *targetAndParameters = [[target componentsSeparatedByString:@"?"] mutableCopy];
    if (targetAndParameters.count <= 0) {
        return nil;
    }
    target = targetAndParameters[0];
    [targetAndParameters removeObjectAtIndex:0];
    for (NSString *paramString in targetAndParameters) {
        NSArray * tempParams_1 = [paramString componentsSeparatedByString:@"&"];
        for (NSString *paramString_1 in tempParams_1) {
            NSArray * tempParams = [paramString_1 componentsSeparatedByString:@"="];
            
            if (tempParams.count != 2) {
                continue;
            }
            params[tempParams[0]] = tempParams[1];
        }
        
    }
    
    routerPath.path = target;
    if (params.allKeys.count > 0) {
        routerPath.parameter = params;
    }
    
    return routerPath;
}

#pragma mark 解析app 内部跳转url  如./、../ 详见Readme.md
- (BFRouterPath *)getRouterPathAppPathURL:(NSString *)url
{
    if (url.length == 0) {
        return nil;
    }
    BFRouterPath *routerPath = [[BFRouterPath alloc] init];
    // 分离 参数
    NSMutableArray *targetAndParameters = [[url componentsSeparatedByString:@"?"] mutableCopy];
    if (targetAndParameters.count <= 0) {
        return nil;
    }
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    NSString  * target = targetAndParameters[0];
    [targetAndParameters removeObjectAtIndex:0];
    for (NSString *paramString in targetAndParameters) {
        NSArray * tempParams_1 = [paramString componentsSeparatedByString:@"&"];
        for (NSString *paramString_1 in tempParams_1) {
            NSArray * tempParams = [paramString_1 componentsSeparatedByString:@"="];
            
            if (tempParams.count != 2) {
                continue;
            }
            params[tempParams[0]] = tempParams[1];
        }
        
    }
    
    if (params.allKeys.count > 0) {
        routerPath.parameter = params;
    }
    
    routerPath.behavior = BFActionDynamicUndefineBehavior;

    if ([target hasPrefix:@"./"])
    {
        routerPath.behavior = BFActionPushBehavior;
        routerPath.path = [target stringByReplacingOccurrencesOfString:@"./" withString:@""];
    }
    else if ([target hasPrefix:@"../"])
    {
        routerPath.behavior = BFActionPopBehavior;
        NSString *targetPath = [target stringByReplacingOccurrencesOfString:@"../" withString:@""];
        routerPath.popCount = (target.length - targetPath.length)/3;
        routerPath.path = targetPath;
    }
    else if ([target hasPrefix:@"/"])
    {
        routerPath.behavior = BFActionPopToRootBehavior;
        routerPath.path = [target stringByReplacingOccurrencesOfString:@"/" withString:@""];
    }
    else if([target hasPrefix:@"-"])
    {
        routerPath.behavior = BFActionPopToRootBehavior;
        routerPath.path = [target stringByReplacingOccurrencesOfString:@"-" withString:@""];
        NSString *classString = [self classWithPath:routerPath.path];
        Class class;
        if (classString.length) {
            class = NSClassFromString(classString);
        }
    
        routerPath.popCount = [self indexOfClassInNavi:class];
        if (routerPath.popCount <= 0) {
            routerPath.behavior = BFActionPopToRootBehavior;
        }
        else
        {
            routerPath.behavior = BFActionPopBehavior;
        }
        routerPath.path = nil;
    }
    else
    {
        routerPath.behavior = BFActionPushBehavior;
        routerPath.path = [target stringByReplacingOccurrencesOfString:@"." withString:@""];
    }
    
    return routerPath;
}

-(BOOL)exist:(UIViewController*)vc
{
    
    UIViewController * lastview = [self.navi.viewControllers lastObject];
    
    if([NSStringFromClass(lastview.class) isEqualToString:NSStringFromClass(vc.class)])
    {
        return TRUE;
    }
    
    return FALSE;
    
}

@end
