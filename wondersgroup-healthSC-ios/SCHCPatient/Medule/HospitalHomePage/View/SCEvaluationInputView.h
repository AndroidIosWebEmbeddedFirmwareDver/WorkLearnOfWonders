//
//  SCEvaluationInputView.h
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/2.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import <UIKit/UIKit.h>

//typedef void(^ShowHandle)(void);
//typedef void(^DismissHandle)(void);
/// typedef void(^MaskViewClickHandle)(void);

typedef void(^SureHandle)(NSString *text);

@interface SCEvaluationInputView : UIView

@property (nonatomic, copy) SureHandle sureHandler;

- (void)dismiss;

/// @property (nonatomic, copy) void (^maskViewClickHandler)(void);

//- (instancetype)initWithFrame:(CGRect)frame
//               bottomShowView:(UIView *)bottomView
//                    inputView:(UIView *)inputView
//                   showHandle:(ShowHandle)showHandler
//                dismissHandle:(DismissHandle)dismissHandler;

@end
