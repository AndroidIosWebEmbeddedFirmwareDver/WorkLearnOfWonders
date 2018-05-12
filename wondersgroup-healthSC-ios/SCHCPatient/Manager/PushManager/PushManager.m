//
//  PushManager.m
//  VaccinePatient
//
//  Created by maorenchao on 16/6/22.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "PushManager.h"

@interface PushManager()
@property(nonatomic, strong)PushModel *pushModel;
@property(nonatomic, strong)PushModel *pushTempModel;

@property(nonatomic, strong)NSMutableArray *historyPushModels;

@end

@implementation PushManager
+ (instancetype)manager
{
    static PushManager *instance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[PushManager alloc] init];
        instance.historyPushModels = [[NSMutableArray alloc] init];
    });
    return instance;
}

- (void)addPushModel:(PushModel *)pushModel
{
    if ([self checkRepeatPushModel:pushModel]) {
        return;
    }
    
    self.pushModel = pushModel;
    [self.historyPushModels addObject:pushModel];
}

- (BOOL)checkRepeatPushModel:(PushModel *)pushModel
{
    for (int i = 0; i<self.historyPushModels.count; i++) {
        PushModel *oldPushModel = [self.historyPushModels objectAtIndex:i];
        if (oldPushModel.msgId.length > 0 && pushModel.msgId.length > 0 && [oldPushModel.msgId isEqualToString:pushModel.msgId]) {
            return YES;
        }
    }
    return NO;
}

- (void)handelPushFromBackGround:(BOOL)isFromBackGround
{
    if (!self.pushModel) {
        return;
    }
    if (self.pushModel.forType && [self.pushModel.forType isEqualToString:@"openPage"]) {
        
        //app收到大开页面的推送通知
        if (isFromBackGround) {
            if ([BFRouter router].navi) {
                [[BFRouter router] open:self.pushModel.page];
            }
            self.pushModel = nil;
        }
        else
        {
            if (![BFRouter router].navi) {
                return;
            }
            self.pushTempModel = self.pushModel;
            self.pushModel = nil;
            WS(weakSelf);
            UIAlertController * alertController = [UIAlertController alertControllerWithTitle:self.pushTempModel.title message:self.pushTempModel.content preferredStyle:UIAlertControllerStyleAlert];
            UIAlertAction *okAction = [UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
                [[BFRouter router] open:self.pushTempModel.page];
                weakSelf.pushTempModel = nil;
            }];
            UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
                weakSelf.pushTempModel = nil;
            }];
            [alertController addAction:okAction];
            [alertController addAction:cancelAction];
            [[BFRouter router].navi presentViewController:alertController animated:YES completion:^{
                
            }];
        }
    }
    else if (self.pushModel.forType && [self.pushModel.forType isEqualToString:@"signUpSuccess"])
    {
        if ([BFRouter router].navi) {
            [[BFRouter router].navi popToRootViewControllerAnimated:NO];
            [[VCManager manager] showTabbarControllerAtIndex: 0];
        }
        
        self.pushModel = nil;
    }
    
    
}

@end
