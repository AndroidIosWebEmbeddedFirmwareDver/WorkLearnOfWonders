//
//  MBProgressHUDFloatView.h
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "MBProgressHUDHelper.h"

typedef NS_ENUM(NSInteger,FloatViewType) {
    FloatViewWithNone          = 0,
    FloatViewWithUploadSuccess = 1,
    FloatViewWithUploadFailed  = 2,
    FloatViewWithIng           = 3
};

@interface MBProgressHUDHelper ( MBProgressHUDHelper)
+(void)showFloatView:(NSString *)text type:(FloatViewType) type;
-(void )showHudWithTextAndType:(NSString *)text withType:(FloatViewType)type;
@end
