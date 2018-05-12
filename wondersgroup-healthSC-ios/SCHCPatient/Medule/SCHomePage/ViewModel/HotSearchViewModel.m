//
//  HotSearchViewModel.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HotSearchViewModel.h"
#import "HotSearchKeyWordModel.h"
#import "RecommendModel.h"
#import "DBManager.h"
#import "SCHospitalModel.h"
#define HotSearchURL @"home/search/hotwords"
#define CommendSearchURL @"search/recommend"
@implementation HotSearchViewModel
-(void)getHotSearchKeyWord:(void (^)(void))success failure:(void (^)(void))failure{
    if(![[Global global] networkReachable])
    {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
   [self.adapter request:HotSearchURL params:nil class:[HotSearchKeyWordModel class]responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
       
       self.model = response.data;
       
       success();
   } failure:^(NSURLSessionDataTask *task, NSError *error) {
       [MBProgressHUDHelper showHudWithText:error.localizedDescription];
       if (failure) {
           failure();
       }
   }];
    
       // self.array = @[@"华西医院",@"饮食",@"健康数据",@"健康数据",@"饮食",@"十大",@"大时代"];

}

-(void)getSearchRecommendList:(NSString *)keyword success:(void (^)(void))success failure:(void (^)(void))failure{
    if(![[Global global] networkReachable])
    {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    NSDictionary * params = @{@"wd":keyword};
    [self.adapter request:CommendSearchURL params:params class:[SCHospitalModel class]responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        self.recommendArr = response.data;
        [self reloadSection];
        success();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        if (failure) {
            failure();
        }
    }];



}
-(NSMutableArray<SCSearchModel *> *)historyArray{
    if (!_historyArray) {
        _historyArray = [NSMutableArray array];
    }
    return _historyArray;

}
-(NSMutableArray<SearchHistoryWithHospitalModel *> *)historyArrayWithHospital{
    if (!_historyArrayWithHospital) {
        _historyArrayWithHospital = [NSMutableArray array];
    }
    return _historyArrayWithHospital;
}
-(void)refreshDateWithType:(ViewContrllerType)type{

    NSArray <SearchHistoryWithHospitalModel * > *  array =[[DBManager manager]getSearchHistoryWithHospitalWithType:type];
    
    self.historyArrayWithHospital = [array copy];

}
- (void)reloadSection{

    NSArray <SCSearchModel * > *  array =[[DBManager manager]getMySearchHistory];

    self.historyArray = [array copy];
}


   @end
