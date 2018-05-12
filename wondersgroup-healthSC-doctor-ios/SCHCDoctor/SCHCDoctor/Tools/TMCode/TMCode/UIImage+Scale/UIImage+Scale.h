//
//  UIImage+Scale.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIImage (Scale)
//图片压缩
- (NSData *)compressImage;

- (UIImage *)scaleToSize:(CGSize)size;

- (UIImage *)imageScale:(CGFloat)scale;

@end
