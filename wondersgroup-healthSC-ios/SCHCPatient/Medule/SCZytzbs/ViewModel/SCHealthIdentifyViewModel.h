//
//  WDHealthIdentifyViewModel.h
//  SHHealthCloudNormal
//
//  Created by wanda on 16/8/10.
//  Copyright © 2016年 WondersGroup. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCHealthIdentifyModel.h"
@interface SCHealthIdentifyViewModel : BaseViewModel
//中医体质辨识结果
- (void)postHealthIdentifyWithParams:(NSString*)resultStr :(void(^)(void))success
                             failure: (void(^)(void))failure;
@property (nonatomic,strong) NSArray               *dataArray;
@property (nonatomic,strong) SCHealthIdentifyModel *model;
@property (nonatomic,strong) NSString              *resultStr;
@end
