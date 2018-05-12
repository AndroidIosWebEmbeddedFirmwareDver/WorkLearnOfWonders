//
//  UIButton+BFEvent.h
//  MakeFun
//
//  Created by maorenchao on 16/6/28.
//  Copyright © 2016年 boreanfrog. All rights reserved.
//

#import "UIResponder+BFEvent.h"

@interface  UIButton(UIButton_BFEvent)
- (void)addBFEventName:(NSString *)eventname forControlEvents:(UIControlEvents)controlEvent;
- (void)removeBFEventName:(NSString *)eventname forControlEvents:(UIControlEvents)controlEvent;
@end
