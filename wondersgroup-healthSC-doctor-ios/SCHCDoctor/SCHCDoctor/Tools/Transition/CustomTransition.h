//
//  CustomTransition.h
//  VaccinePatient
//
//  Created by Jam on 16/7/26.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef enum {
    AnimationTypePresent,
    AnimationTypeDismiss
} AnimationType;


@interface CustomTransition : NSObject <UIViewControllerAnimatedTransitioning>

@property (nonatomic, assign) AnimationType type;


@end
