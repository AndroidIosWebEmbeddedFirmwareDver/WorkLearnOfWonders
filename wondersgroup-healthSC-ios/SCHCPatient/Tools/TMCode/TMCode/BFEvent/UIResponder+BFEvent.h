//
//  UIResponder+BFEvent.h
//  MakeFun
//
//  Created by maorenchao on 16/6/28.
//  Copyright © 2016年 boreanfrog. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIResponder(UIResponder_BFEvent)
- (void)bfEventWithEventName:(NSString *)eventname userInfo:(id)userInfo;
@end
