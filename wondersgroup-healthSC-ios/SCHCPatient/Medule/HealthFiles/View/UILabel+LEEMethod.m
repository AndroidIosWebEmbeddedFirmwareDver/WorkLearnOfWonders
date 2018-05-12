//
//  UILabel+LEEMethod.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UILabel+LEEMethod.h"
#import <CoreText/CoreText.h>


@implementation UILabel (LEEMethod)

- (void)changeAlignmentLeftAndRight {
    
    CGFloat width = self.frame.size.width - 5.;
    
    CGRect textRect = [self.text boundingRectWithSize:CGSizeMake(width, MAXFLOAT)
                                              options:NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingTruncatesLastVisibleLine | NSStringDrawingUsesFontLeading
                                           attributes:@{NSFontAttributeName : self.font}
                                              context:nil];
    
    CGFloat margin = (width - textRect.size.width) / (self.text.length - 1);
    
    NSMutableAttributedString *attributeString = [[NSMutableAttributedString alloc] initWithString:self.text];
    [attributeString addAttribute:(id)kCTKernAttributeName value:@(margin) range:NSMakeRange(0, self.text.length - 1)];
    
    NSMutableAttributedString *aString = [[NSMutableAttributedString alloc] initWithString:@":"];
    
    [attributeString appendAttributedString:aString];
    
    self.attributedText = attributeString;
}

@end
