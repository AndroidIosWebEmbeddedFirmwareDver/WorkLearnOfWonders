//
//  HealthHomeViewModel.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HealthHomeViewModel.h"
#import "DBManager.h"
#import "HealthHomeDBModel.h"


@interface HealthHomeViewModel ()

@property (nonatomic, strong) HealthHomeLayoutModel *informationLayoutModel;

@end


@implementation HealthHomeViewModel

- (instancetype)init {
    
    if (self == [super init]) {
        [self prepareData];
    }
    return self;
}

- (void)prepareData {
    
    self.layoutArray = [NSMutableArray array];
    
    HealthHomeLayoutModel *rotatingModel = [[HealthHomeLayoutModel alloc] init];
    rotatingModel.type = HealthHomeLayoutTypeRotatingImage;
    rotatingModel.cellHeight = AdaptiveFrameHeight(375/2.0);
    [self.layoutArray addObject:rotatingModel];
    
    HealthHomeLayoutModel *functionLayoutModel = [[HealthHomeLayoutModel alloc] init];
    functionLayoutModel.type = HealthHomeLayoutTypeFunction;
    functionLayoutModel.cellHeight = 150.;
    [self.layoutArray addObject:functionLayoutModel];
    
    self.informationLayoutModel = [[HealthHomeLayoutModel alloc] init];
    self.informationLayoutModel.type = HealthHomeLayoutTypeInformation;
    self.informationLayoutModel.cellHeight = 97.;
    [self.layoutArray addObject:self.informationLayoutModel];
}


- (void)getHealthHomeFunction:(void (^)(void))success failure:(void (^)(void))failure {
    
    [self.adapter request:HealthHome_Function_URL params:nil class:nil responseType:Response_Object method:Request_GET needLogin:NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        NSLog(@"%@", response);
        self.bannerArray = [BannersModel mj_objectArrayWithKeyValuesArray:response.data[@"banners"]];
        self.functionArray = [FunctionModel mj_objectArrayWithKeyValuesArray:response.data[@"functionIcons"]];
        
        HealthHomeDBModel *dbModel = [[DBManager manager] getHealthHomeData];
        
        if (!dbModel) {
            dbModel = [[HealthHomeDBModel alloc] init];
        }
        dbModel.bannerArray = self.bannerArray;
        dbModel.functionArray = self.functionArray;
        
        [[DBManager manager] saveHealthHomeDataWithModel:dbModel];
        
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        HealthHomeDBModel *dbModel = [[DBManager manager] getHealthHomeData];
        if (dbModel) {
            self.bannerArray = dbModel.bannerArray;
            self.functionArray = dbModel.functionArray;
        }
        
        failure();
    }];
}

- (void)getHealthHomeInformationList:(void (^)(void))success failure:(void (^)(void))failure {
    
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    
    [self.adapter request:HealthHome_Information_URL params:params class:nil responseType:Response_Object method:Request_GET needLogin:NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        [HHInformationListModel mj_setupObjectClassInArray:^NSDictionary *{
            return @{@"list" : @"HHInformationModel"};
        }];
        
        self.dataArray = [HHInformationListModel mj_objectArrayWithKeyValuesArray:response.data];
        
        if (self.dataArray.count == 0) {
            success();
            return ;
        }
        
        HealthHomeDBModel *dbModel = [[DBManager manager] getHealthHomeData];
        
        if (!dbModel) {
            dbModel = [[HealthHomeDBModel alloc] init];
        }

        dbModel.infoDataArray = self.dataArray;
        [[DBManager manager] saveHealthHomeDataWithModel:dbModel];
        
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        HealthHomeDBModel *dbModel = [[DBManager manager] getHealthHomeData];
        if (dbModel) {

            self.dataArray = dbModel.infoDataArray;
        }
        
        failure();
    }];
}

- (void)getDBData {
    
    HealthHomeDBModel *dbModel = [[DBManager manager] getHealthHomeData];
    if (dbModel) {
        
        self.bannerArray = dbModel.bannerArray;
        self.functionArray = dbModel.functionArray;
        self.dataArray = dbModel.infoDataArray;
    }
}

@end


@implementation HealthHomeLayoutModel

@end
