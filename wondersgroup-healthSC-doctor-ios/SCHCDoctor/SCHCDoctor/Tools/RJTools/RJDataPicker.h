//
//  RJDataPicker.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/21.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RJDataPicker : UIView
@property (strong, nonatomic) UIPickerView * picker;

- (instancetype)initWithData:(NSArray *)data;
- (instancetype)initWithMinNum:(NSInteger)minNum maxNum:(NSInteger)maxNum;

//建立
- (void)build;

/**
 *  @brief 选中回调
 */
- (void)setSelectedBlock:(void(^)(NSString * data))selectedBlock;
@end
