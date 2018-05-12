//
//  HomeAreaViewModel.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "AreaModel.h"

@interface HomeAreaViewModel : BaseViewModel

@property(nonatomic ,strong) NSArray * rightSelectArray;//城市数据---
@property (nonatomic, assign) NSInteger selectedIndex;

- (void)getAllCityDatas: (void(^)(void))success
                    failure: (void(^)(NSError * error))failure;
@end
