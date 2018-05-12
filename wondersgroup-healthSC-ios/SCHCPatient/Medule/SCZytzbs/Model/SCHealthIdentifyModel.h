//
//  WDHealthIdentifyModel.h
//  SHHealthCloudNormal
//
//  Created by wanda on 16/8/10.
//  Copyright © 2016年 WondersGroup. All rights reserved.
//

#import "BaseModel.h"
@interface SCadviceModel : BaseModel
@property (nonatomic, strong)NSString * dailyLife;
@property (nonatomic, strong)NSString * care;
@property (nonatomic, strong)NSString * emotion;
@property (nonatomic, strong)NSString * exercise;
@property (nonatomic, strong)NSString * diet;
@end



@interface SCHealthIdentifyModel : BaseModel

@property (nonatomic,strong ) SCadviceModel *advice;
@property (nonatomic, strong) NSString      *physical;//体质

@end
