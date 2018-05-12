//
//  RJDatePicker.h
//  GuestManager
//
//  Created by Po on 16/4/15.
//  Copyright © 2016年 pretang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RJDatePicker : UIControl

@property (strong, nonatomic) UIDatePicker * picker;


//建立
- (void)build;

/**
 *  @brief 选中回调
 */
- (void)setSelectedBlock:(void(^)(NSDate * date))selectedBlock;
@end
