//
//  FailView.h
//  VaccinePatient
//
//  Created by Jam on 16/5/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void(^TouchViewBlock)(void);

@interface FailView : UIView


@property (nonatomic, strong) UIImageView       *imageFail;
@property (nonatomic, strong) UILabel           *labelFail;
@property (nonatomic, copy)   TouchViewBlock    touchFailBlock;


/**
 *  显示WDFailView
 *
 *  @param  dataImage : 失败图片
 *  @param  tip       : 失败描述
 */

- (void)showFail:(UIImage *)dataImage withTip:(NSString *)tip;

@end
