//
//  SCMyPreorderDetailViewModel.m
//  SCHCPatient
//
//  Created by wuhao on 2016/11/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCMyPreorderDetailViewModel.h"




@implementation SCMyPreorderDetailViewModel
-(NSArray *)data{
    if (!_datas) {
        _datas=[NSArray array];
    }
    return _datas;
    
}


//-(SCMyPreorderDetailModel *)detailModel{
//    if (!_detailModel) {
//        _detailModel=[[SCMyPreorderDetailModel alloc]init];
//    }
//    return _detailModel;
//
//}




-(void)getPreOrderDetailDataFromeServer:(NSString *)preOrderID success:(void (^)(void))successBlock failed:(void (^)(void))failedBlock{
    
//        if (self.datas.count != 0) {
//            self.datas = nil;
//        }
    
        if(![[Global global] networkReachable])
        {
            self.requestCompeleteType = RequestCompeleteNoWifi;
            return;
        }
        
        NSDictionary *dic=@{@"orderId":preOrderID};
        
        [self.adapter request:QUERYORDERLISTDETAIL params:dic class:[SCMyPreorderDetailModel class] responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
            self.detailModel = response.data;
//            if (self.delegate) {
//                [self.delegate getData:self.detailModel];
//            }
            
            if (successBlock) {
                successBlock();
            }
        
        } failure:^(NSURLSessionDataTask *task, NSError *error) {
            
            [MBProgressHUDHelper showHudWithText:error.localizedDescription];
            if (failedBlock) {
                failedBlock();
            }
            
        }];
}

@end
