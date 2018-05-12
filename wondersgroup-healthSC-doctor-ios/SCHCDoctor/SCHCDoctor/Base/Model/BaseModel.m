//
//  BaseModel.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@implementation BaseModel

/* 获取对象的所有属性 */
+ (NSArray *)getAllProperties
{
    u_int count;
    objc_property_t *properties  =class_copyPropertyList([self class], &count);
    NSMutableArray *propertiesArray = [NSMutableArray arrayWithCapacity:count];
    NSMutableArray *propertiesAttrArray = [NSMutableArray arrayWithCapacity:count];
    for (int i = 0; i < count ; i++)
    {
        const char* propertyName =property_getName(properties[i]);
        
        [propertiesArray addObject: [NSString stringWithUTF8String: propertyName]];
        [propertiesAttrArray addObject: [NSString stringWithUTF8String: propertyName]];
        
//        NSLog(@"attributes:%s",propertyAttributes);
        
        //        NSLog(@"\r\n%@\r\n",propertiesAttrArray[i]);
    }
    free(properties);
    
    return propertiesArray;
}

/* 获取字典 key:属性-value:属性 */
+ (NSDictionary*)getDicPropertiesToSameValue{
    NSMutableDictionary *returnDic = [[NSMutableDictionary alloc] init];
    NSArray *allProperties = [self getAllProperties];
    for (int i = 0; i < allProperties.count ; i++)
    {
        [returnDic setObject:allProperties[i] forKey:allProperties[i]];
    }
    return returnDic;
}
@end
