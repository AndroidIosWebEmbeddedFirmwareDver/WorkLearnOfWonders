//
//  cuzZButton.h
//  SZCirculationImage
//
//  Created by Wonders_iOS on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface cuzZButton : UIButton
@property (nonatomic,strong)UIImageView * buttonImage;
@property (nonatomic,strong)UILabel * buttonTitle;
//-(void)initWithButtonImageName:(NSString *)imageName andButtonTitle:(NSString *)titleStr;

@property (nonatomic,strong)NSString * imageName;
@property (nonatomic,strong)NSString * titleStr;
@end
