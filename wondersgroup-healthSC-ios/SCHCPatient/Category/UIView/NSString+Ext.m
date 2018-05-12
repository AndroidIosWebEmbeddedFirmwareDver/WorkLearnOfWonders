//
//  NSString+Ext.m
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "NSString+Ext.h"

@implementation NSString (Ext)

- (NSString *)stringByDeletingTerminalSpace {
    return [self stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
}

@end
