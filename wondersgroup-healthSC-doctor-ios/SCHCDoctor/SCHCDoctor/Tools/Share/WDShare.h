//
//  WDShare.h
//  EyeProtection
//
//  Created by 杜凯 on 16/3/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <ShareSDK/ShareSDK.h>
#import "ShareAcionSheet.h"
#import "ShareModel.h"

@interface WDShare : NSObject

@property (nonatomic, strong) ShareAcionSheet *shareActionSheet;

+ (instancetype)shareInstance;
//分享
-(void)shareInView: (UIView *)view
           content: (ShareModel *)model
           success: (void (^)(NSString *success))success
           failure: (void (^)(NSString *failure))failure
            cancel: (void (^)(NSString *cancel))cancel ;


- (void)shareInView:(UIView *)view
            content:(ShareModel *)model;


- (void)shareContent:(NSString *)content
                View:(UIView *)view
                 url:(NSString *)url
                type:(SSDKPlatformType)type;


- (void)shareInView:(UIView *)view
            content:(ShareModel *)model
           topic_id:(NSString *)topic_id
              icons:(NSArray *)icons
             titles:(NSArray *)titles;


@end
