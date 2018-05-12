//
//  ListModel.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ListModel : NSObject

@property (nonatomic, strong) NSNumber      *more;
@property (nonatomic, strong) NSDictionary  *more_params;
@property (nonatomic, strong) NSDictionary  *extras;
@property (nonatomic, strong) NSArray       *content;

@end
