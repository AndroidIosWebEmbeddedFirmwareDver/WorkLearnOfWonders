//
//  CheckViewModel.m
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ReportViewModel.h"
#import "ReportModel.h"

@implementation ReportViewModel

- (NSArray *)dataArray {
    if (!_dataArray) {
        _dataArray = [NSArray array];
    }
    return _dataArray;
}

#pragma mark -获取检查报告
- (void)getCheckDataFromeServer:(void(^)(void))success failed:(void(^)(NSError *error))failed {
//    for (int i = 0 ; i < 10 ; i ++) {
//        ReportModel *model = [[ReportModel alloc]init];
//        model.date   = @"2016-10-10";
//        model.department_name = @"122314214321";
//        model.item_name = @"神经科";
//        model.hospital_name   = @"10.00";
//        self.dataArray = [self.dataArray arrayByAddingObject:model];
//    }
//    success();
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    NSDictionary *dic = @{ @"uid" : [UserManager manager].uid , @"medicalOrgId" : self.medicalOrgId , @"timeFlag":self.day };

    [self.adapter request:@"healthOnline/jiancha/list"
                   params:dic class:[ReportModel class]
             responseType:Response_List method:Request_GET
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {
            ListModel *listModel = response.data;
                        if (self.dataArray.count != 0) {
                            self.dataArray = nil;
                        }
                        self.dataArray = [self.dataArray arrayByAddingObjectsFromArray:listModel.content];
                        if (self.dataArray.count == 0) {
                            self.requestCompeleteType = RequestCompeleteEmpty;
                        }else {
                            self.requestCompeleteType = RequestCompeleteSuccess;
                        }
                        success();
                  } failure:^(NSURLSessionDataTask *task, NSError *error) {
                        failed(error);
                        self.requestCompeleteType = RequestCompeleteError;
                      
                  }];
}



#pragma mark -获取检验报告
- (void)getInspectionDataFromeServer:(void(^)(void))success failed:(void(^)(NSError *error))failed {
//    for (int i = 0 ; i < 10 ; i ++) {
//        ReportModel *model = [[ReportModel alloc]init];
//        model.date   = @"2016-10-10";
//        model.department_name = @"122314214321";
//        model.item_name = @"神经科";
//        model.hospital_name   = @"10.00";
//        self.dataArray = [self.dataArray arrayByAddingObject:model];
//    }
//    success();
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return ;
    }
    NSDictionary *dic = @{ @"uid" : [UserManager manager].uid , @"medicalOrgId" : self.medicalOrgId , @"timeFlag":self.day };
    [self.adapter request:@"healthOnline/jianyan/list"
                   params:dic class:[ReportModel class]
             responseType:Response_List method:Request_GET
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {
            ListModel *listModel = response.data;
                        if (self.dataArray.count != 0) {
                            self.dataArray = nil;
                        }
                        self.dataArray = [self.dataArray arrayByAddingObjectsFromArray:listModel.content];
                        if (self.dataArray.count == 0) {
                            self.requestCompeleteType = RequestCompeleteEmpty;
                        }else {
                            self.requestCompeleteType = RequestCompeleteSuccess;
                        }
                        success();
                  } failure:^(NSURLSessionDataTask *task, NSError *error) {
                        failed(error);
                        self.requestCompeleteType = RequestCompeleteError;
                  }];
}

- (void)getChooseTimerData:(void(^)(void))success failed:(void(^)(void))failed{
    NSMutableArray *dataArr = [NSMutableArray array];
    NSArray *titleArr = @[];
    if (self.type == 1) {
        titleArr       = @[ @"今天" , /*@"最近三天" , @"最近一周" ,*/ @"最近一个月", @"最近六个月" ];
    }else {
        titleArr       = @[ @"今天" , @"最近三天" , @"最近一周", @"最近一个月"];
    }
    for (NSInteger i = 0; i< titleArr.count; i++) {
        ChooseTimeModel *model = [[ChooseTimeModel alloc]init];
        model.title            = titleArr[i];
        [dataArr addObject:model];
    }
    self.dataArray = [self.dataArray arrayByAddingObjectsFromArray:dataArr];
    success();
}



@end
