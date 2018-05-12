//
//  BlockedUIs.m
//  haowan
//
//  Created by wupeijing on 3/29/15.
//  Copyright (c) 2015 iyaya. All rights reserved.
//

#import "UIKitBlockAdditions.h"
#import <objc/runtime.h>

@implementation UIKitBlockAdditions

@end

@implementation UIButton (Block)

- (ButtonBlockAction)tappedBlock {
    return objc_getAssociatedObject(self, @selector(tappedBlock));
}

- (void)setTappedBlock:(ButtonBlockAction)tappedBlock {
    [self addTarget:self action:@selector(buttonTapped:) forControlEvents:UIControlEventTouchUpInside];
    objc_setAssociatedObject(self, @selector(tappedBlock), tappedBlock, OBJC_ASSOCIATION_COPY_NONATOMIC);
}

- (void)buttonTapped:(UIButton *)button {
    if (self.tappedBlock) {
        self.tappedBlock(button);
    }
}

@end

@implementation UIAlertView (Block)

- (AlertBlockAction)tappedBlock {
    return objc_getAssociatedObject(self, @selector(tappedBlock));
}

- (void)setTappedBlock:(AlertBlockAction)tappedBlock {
    self.delegate = self;
    objc_setAssociatedObject(self, @selector(tappedBlock), tappedBlock, OBJC_ASSOCIATION_COPY_NONATOMIC);
}

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex{
    if (self.tappedBlock) {
        self.tappedBlock(buttonIndex);
    }
}
@end


@implementation UIView (Block)

- (TapBlockAction)tappedBlock {
    return objc_getAssociatedObject(self, @selector(tappedBlock));
}

- (void)setTappedBlock:(TapBlockAction)tappedBlock {
    [self addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapped:)]];
    objc_setAssociatedObject(self, @selector(tappedBlock), tappedBlock, OBJC_ASSOCIATION_COPY_NONATOMIC);
}

- (void)tapped:(UITapGestureRecognizer *)tap {
    if (self.tappedBlock) {
        self.tappedBlock(tap);
    }
}

@end
