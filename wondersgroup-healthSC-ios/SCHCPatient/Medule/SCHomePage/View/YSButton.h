//
//  YSButton.h
//  图文上下的按钮
//
//  Created by Joseph on 15/10/8.
//  Copyright © 2015年 Joseph. All rights reserved.
//
/**
 *  图文上下排布的按钮
 */

#import <UIKit/UIKit.h>

@interface YSButton : UIButton
@property (nonatomic,strong)UIImageView * buttonImage;
@property (nonatomic,strong)UILabel * buttonTitle;

//-(void)initWithButtonImageName:(NSString *)imageName andButtonTitle:(NSString *)titleStr;
@end
