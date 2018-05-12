//
//  BFRouter.h
//  BFRouter
//
//  Created by maorenchao on 16/4/22.
//  Copyright © 2016年 dudua. All rights reserved.
//

#import <UIKit/UIKit.h>

#define WDScheme @"com.wondersgroup.healthcloud.sichuan"


typedef NS_ENUM(NSInteger, BFActionBehavior)
{
    BFActionDynamicUndefineBehavior = 0,
    BFActionPopBehavior,
    BFActionPopToRootBehavior,
    BFActionPushBehavior
};

@interface BFRouterPath : NSObject
@property(nonatomic, strong)NSString *path;
@property(nonatomic, assign)BFActionBehavior behavior;
@property(nonatomic, assign)NSInteger popCount;       //not use
@property(nonatomic, strong)NSDictionary *parameter;

@end



typedef NSString* (^BFClassWithH5UrlBlock)(NSString *url);
typedef BOOL (^BFRouterCanopenURlBlock)(NSString *url);

@protocol BFRouterPassParameterProtocal <NSObject>
- (instancetype)initWithUrlParameter:(NSDictionary *)parameter;
@end

@protocol BFRouterPassUrlProtocal <NSObject>
- (instancetype)initWithURL:(NSString *)url;
@end

@interface BFRouter : NSObject
@property(nonatomic, weak)UINavigationController *navi;
@property(nonatomic, strong)BFClassWithH5UrlBlock classWithH5Block;
@property(nonatomic, strong)BFRouterCanopenURlBlock canOpenURLBlock;

+ (instancetype)router;
+ (void)setupCoifigPath:(NSString *)path;

- (void)open:(NSString *)url;

- (BFRouterPath *)getRouterPathFromInnerURL:(NSString *)url;
@end
