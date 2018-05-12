//
//  UIImage+Scale.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UIImage+Scale.h"

@implementation UIImage (Scale)

- (UIImage *)scaleToSize:(CGSize)size
{
    return [self scaleToSize:self size:size];
}

- (UIImage *)imageScale:(CGFloat)scale;
{
    return [self scaleToSize:self size:CGSizeMake(self.size.width*scale, self.size.height*scale)];
}

- (UIImage *)scaleToSize:(UIImage *)img size:(CGSize)size{
    // 创建一个bitmap的context
    // 并把它设置成为当前正在使用的context
    //    UIGraphicsBeginImageContext(size);
    UIGraphicsBeginImageContextWithOptions(size, NO, 1.0);
    // 绘制改变大小的图片
    [img drawInRect:CGRectMake(0,0, size.width, size.height)];
    // 从当前context中创建一个改变大小后的图片
    UIImage* scaledImage =UIGraphicsGetImageFromCurrentImageContext();
    // 使当前的context出堆栈
    UIGraphicsEndImageContext();
    //返回新的改变大小后的图片
    return scaledImage;
}

#pragma mark - //图片压缩   1.5倍屏幕尺寸
- (NSData *)compressImage
{
    CGSize size = [[UIScreen mainScreen] bounds].size;
    CGFloat rateWidth = size.width*1.5/self.size.width;
    CGFloat rateHeight = size.height*1.5/self.size.height;
    
    UIImage *tempImage;
    
    if (rateWidth > 1 && rateHeight > 1) {
        tempImage = self;
    }
    else if(rateWidth > rateHeight)
    {
        tempImage = [self scaleToSize: self size:CGSizeMake(size.width*1.5, rateWidth * self.size.height)];
    }
    else
    {
        tempImage = [self scaleToSize: self size:CGSizeMake(rateHeight * self.size.width, size.height*1.5)];
    }
    
    NSData *imgData = UIImageJPEGRepresentation(tempImage, 1.0);
    if (imgData.length/1024 > 500.0) {
        imgData = UIImageJPEGRepresentation(tempImage, 0.8);
    }
    
    return imgData;
}


@end
