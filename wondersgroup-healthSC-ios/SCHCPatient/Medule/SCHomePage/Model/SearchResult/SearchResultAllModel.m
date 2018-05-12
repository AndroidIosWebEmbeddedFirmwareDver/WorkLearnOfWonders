//
//  SearchResultAllModel.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SearchResultAllModel.h"

@implementation SearchResultAllModel
+ (NSDictionary *)mj_replacedKeyFromPropertyName {
    return @{@"HospitalsModel":@"hospitals",
             @"DoctorsModel":@"doctors",
             @"ArticlesModel":@"articles"
             };
}
@end
