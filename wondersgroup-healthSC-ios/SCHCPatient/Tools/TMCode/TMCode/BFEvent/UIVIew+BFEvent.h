//
//  UIVIew+BFEvent.h
//  MakeFun
//
//  Created by maorenchao on 16/6/28.
//  Copyright © 2016年 boreanfrog. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UIResponder+BFEvent.h"

@interface UIView(UIVIew_BFEvent)
- (void)addBFTapEventName:(NSString *)eventname;
- (void)removeBFTapEvent;
@end
