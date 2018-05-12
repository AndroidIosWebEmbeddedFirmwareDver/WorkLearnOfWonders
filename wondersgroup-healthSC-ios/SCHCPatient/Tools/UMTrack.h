//
//  UMTrack.h
//  EyeProtectionDoctor
//
//  Created by Jam on 16/5/14.
//  Copyright © 2016年 陈刚. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UMTrack : NSObject

/*
 * 友盟分析集成
 */
+ (void)startUMTrackWithAppkey:(NSString *)appkey;


/*
 * 友盟集成
 */
+ (void)requestTrackWithAppkey:(NSString *)appkey;


@end
