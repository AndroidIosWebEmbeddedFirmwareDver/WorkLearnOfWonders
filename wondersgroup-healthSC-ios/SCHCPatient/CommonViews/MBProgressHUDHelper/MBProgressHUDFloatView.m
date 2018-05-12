//
//  MBProgressHUDFloatView.m
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "MBProgressHUDFloatView.h"



@implementation MBProgressHUDFloatView


+(void)showFloatView:(NSString *)text type:(FloatViewType) type
{
    [[MBProgressHUDHelper defaultHelper] sho];
}

+(void )showHudWithTextAndType:(NSString *)text withType:(FloatViewType)type
{
    
}

-(void)openSuccess:(NSString * )text
{

}

-(void)openFailed:(NSString *)text
{

}
-(void)showIng:(NSString*) text
{

}
@end
