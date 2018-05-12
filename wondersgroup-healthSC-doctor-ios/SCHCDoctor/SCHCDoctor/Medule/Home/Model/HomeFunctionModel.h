//
//  HomeFunctionModel.h
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseModel.h"

@interface HomeFunctionModel : BaseModel

@property (nonatomic, strong) NSString *image;
@property (nonatomic, strong) NSString *content;
@property (nonatomic, assign) NSNumber *invalid;
@property (nonatomic, assign) NSNumber *isNeedRedPoint;

@end
