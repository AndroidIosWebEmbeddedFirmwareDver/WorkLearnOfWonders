//
//  SCExtractOrdersViewModel.h
//  SCHCPatient
//
//  Created by Gu Jiajun on 16/11/16.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SearchHistoryWithHospitalModel.h"
#import "SCHospitalModel.h"
@interface SCExtractOrdersViewModel : BaseViewModel

@property (strong, nonatomic) NSArray *dataArray;
@property (copy,   nonatomic) NSString *keyWord;//模糊搜索的关键字

@property (strong, nonatomic) NSMutableArray * searchHistoryArray;

- (void)getHostptialNameSuccess:(void(^)(void))success failed:(void(^)(void))failed;

/**
 获取用户搜索记录

 @param model <#model description#>
 */
- (void)saveSearchHistoryModel:(SCHospitalModel *)model;
- (void)getSearchHistoryData;
@end
