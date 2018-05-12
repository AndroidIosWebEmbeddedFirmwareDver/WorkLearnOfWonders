//
//  BlockedUIs.h
//  haowan
//
//  Created by wupeijing on 3/29/15.
//  Copyright (c) 2015 iyaya. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

typedef void (^ButtonBlockAction)(UIButton *btn);
typedef void (^AlertBlockAction)(NSInteger clickIndex);
typedef void (^TapBlockAction)(UITapGestureRecognizer *tap);


@interface UIKitBlockAdditions : NSObject

@end

@interface UIButton (Block)

@property (nonatomic) ButtonBlockAction tappedBlock;

@end

@interface UIAlertView (Block) <UIAlertViewDelegate>

@property (nonatomic) AlertBlockAction tappedBlock;

@end


@interface UIView(Block)

@property (nonatomic) TapBlockAction tappedBlock;

@end