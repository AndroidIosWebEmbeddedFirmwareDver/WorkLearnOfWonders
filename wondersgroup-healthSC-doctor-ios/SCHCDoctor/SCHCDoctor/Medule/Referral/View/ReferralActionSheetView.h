//
//  ReferralActionSheetView.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ReferralActionSheetView : UIView
- (instancetype)initWithFrame:(CGRect)frame titles:(NSArray *)titles;

- (void)showAnim;
- (void)hideAnim;
- (void)setClickBlock:(void(^)(NSInteger index, BOOL isCancel))block;
@end
