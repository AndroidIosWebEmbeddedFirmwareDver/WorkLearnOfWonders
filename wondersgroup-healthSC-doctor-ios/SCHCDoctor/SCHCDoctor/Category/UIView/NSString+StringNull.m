//
//  NSString+StringNull.m
//  CNHealthCloudDoctor
//
//  Created by 杜凯 on 16/5/29.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "NSString+StringNull.h"

@implementation NSString (StringNull)

+ (NSString *)StringNull:(NSString *)str{
    
    if ([str isKindOfClass:[NSNull class]]|| str == nil) {
        return  @"";
        
    }else if ([str isKindOfClass:[NSNumber class]]) {
        return  [NSString stringWithFormat:@"%@",[str description]];
    }else if([str isKindOfClass:[NSString class]]){
        
        return (NSString *)str;
        
    }else if ([str isEqual:[NSNull null]]) {
        return  @"";
    } else if ([str isEqualToString:@"(null)"]) {
        return @"";
    }
    
    return (str==nil?@"":str);
    
}

@end
