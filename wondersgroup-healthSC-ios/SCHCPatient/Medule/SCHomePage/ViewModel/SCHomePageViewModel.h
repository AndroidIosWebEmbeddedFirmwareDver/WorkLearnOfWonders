//
//  SCHomePageViewModel.h
//  SZCirculationImage
//
//  Created by Wonders_iOS on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseViewModel.h"
#import "HomePageModel.h"
#import "DBManager.h"
@interface SCHomePageViewModel : BaseViewModel
@property (nonatomic,strong) HomePageModel * model;//首页数据

- (void)requestHomeDatasuccess:(void (^)(void))success
                              failure:(void (^)(NSError *error))failure;

@end
