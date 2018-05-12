//
//  SCExtractOrdersViewModel.m
//  SCHCPatient
//
//  Created by Gu Jiajun on 16/11/16.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCExtractOrdersViewModel.h"
#import "SCHospitalModel.h"
#import "DBManager.h"
@implementation SCExtractOrdersViewModel

- (NSArray *)dataArray {
    if (!_dataArray) {
        _dataArray = [NSArray array];
    }
    return _dataArray;
}

- (void)getHostptialNameSuccess:(void(^)(void))success failed:(void(^)(void))failed {
    
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
    }
    [self.adapter request:@"search/recommend" params:@{@"wd":self.keyWord} class:[SCHospitalModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        if (self.dataArray.count != 0) {
            self.dataArray = nil;
        }
        //        self.dataArray = [self.dataArray arrayByAddingObjectsFromArray:response.data];
        self.dataArray = response.data;
        if (self.dataArray.count == 0) {
            self.requestCompeleteType = RequestCompeleteEmpty;
        }else {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }
        
        success();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        self.requestCompeleteType = RequestCompeleteError;
        failed();
    }];
}

- (void)saveSearchHistoryModel:(SCHospitalModel *)model {
    SearchHistoryWithHospitalModel * temp = [[SearchHistoryWithHospitalModel alloc] init];
    temp.type = SearchTypeController_payOnLine;
    temp.cat_name = model.hospitalName;
    temp.cat_id = model.hospitalCode;
    temp.userID = [[UserManager manager] uid];
    temp.date = [NSDate date];

    //检测是否存在
    NSInteger num = [self checkSameDataWithHospitalName:temp.cat_name];
    if (num != -1) {
        [_searchHistoryArray removeObjectAtIndex:num];
        [_searchHistoryArray insertObject:temp atIndex:0];
    } else {
        [_searchHistoryArray insertObject:temp atIndex:0];
        if (_searchHistoryArray.count > 5) {
            _searchHistoryArray = [NSMutableArray arrayWithArray:[_searchHistoryArray subarrayWithRange:NSMakeRange(0, 5)]];
        }
    }
    
    [self saveSearchDataToDB];
}

- (NSInteger)checkSameDataWithHospitalName:(NSString *)name {
    for (NSInteger i = 0; i < _searchHistoryArray.count; i ++) {
        SearchHistoryWithHospitalModel * temp = _searchHistoryArray[i];
        if ([name isEqualToString:temp.cat_name]) {
            return i;
        }
    }
    return -1;
}

- (void)getSearchHistoryData {
    NSArray * array = [[DBManager manager] getSearchHistoryWithHospitalWithType:SearchTypeController_payOnLine];
    _searchHistoryArray = [[NSMutableArray alloc] initWithArray:array];
}

- (void)saveSearchDataToDB {
    __weak typeof(self) weakSelf = self;
    dispatch_async(dispatch_get_global_queue(0, 0), ^{
        [[DBManager manager] clearHospitalSearchHistoryWithType:SearchTypeController_payOnLine];
        for (SearchHistoryWithHospitalModel * model in weakSelf.searchHistoryArray) {
            [[DBManager manager] saveSearchHistoryWithHospital:model];
        }
    });
}

@end
