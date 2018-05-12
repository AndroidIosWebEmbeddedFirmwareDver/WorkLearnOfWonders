//
//  SCMyPreorderListsViewModel.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCMyPreorderListsViewModel.h"
#import "SCMyPreorderListsModel.h"

//最新的Model
#import "SCMyOrderModel.h"


@implementation SCMyPreorderListsViewModel


-(NSArray *)datas{
    if (!_datas) {
        _datas=[NSArray new];
    }
    return _datas;

}


    //获取数据
- (void)getPreOrderDataFromeServer:(int)preOrderState  success:(void(^)(void))successBlock failed:(void(^)(void))failedBlock{
//    if (self.datas.count != 0) {
//        self.datas = nil;
//    }

    if(![[Global global] networkReachable])
        {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
        }
    
      int stateInt=0;
    
    
        //#warning 吴昊Warning  state字段暂时这样传值
    switch (preOrderState) {
        
                //全部预约----->state字段:0
        case 0:
            stateInt=0;
            break;
                //待就诊------->state字段:2
        case 1:
            stateInt=2;
            break;
                //已就诊-------->state字段:4
         case 2:
            stateInt=4;
            break;
                //已取消--------->state字段:3
         case 3:
            stateInt=3;
            break;
    }
    

    NSString *uidString=[UserManager manager].uid;
    
        //测试Uid
        //uidString=@"846fa8695a9b401b8b9d44dff8980b98";
    
    NSDictionary *dic=@{@"uid":uidString,@"state":@(stateInt)};
    if (stateInt==0) {
        dic=@{@"uid":uidString};
    }

    [self.adapter request:QUERYORDERLIST params:dic class:[SCMyOrderModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        ListModel *model=response.data;

            //self.datas=[NSMutableArray arrayWithArray:model.content];
        self.datas = [NSArray arrayWithArray:  model.content];
//        self.datas = [SCMyOrderModel mj_objectArrayWithKeyValuesArray:response.data[@"content"]];
        
        if (self.datas.count == 0) {
            self.requestCompeleteType = RequestCompeleteEmpty;
            successBlock();
            return ;
        }
        else {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }
        
        
        self.hasMore = [model.more boolValue];
        if (self.hasMore) {
            self.moreParams = model.more_params;
        }else {
            self.moreParams = nil;
        }

        
        successBlock();

        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        if (error.code == -1011) {
                //连接失败
            self.requestCompeleteType = RequestCompeleteError;
        }
        failedBlock();
        
    }];
    

}

#pragma mark 获取更多数据
- (void)getMorePreOrderDataFromeServer:(int)preOrderState  success:(void(^)(void))successBlock failed:(void(^)(void))failedBlock{
    
//    if (self.datas.count != 0) {
//        self.datas = nil;
//    }
    
    if(![[Global global] networkReachable])
        {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
        }
    
    int stateInt=0;
    
    
        //#warning 吴昊Warning  state字段暂时这样传值
    switch (preOrderState) {
            
                //全部预约----->state字段:0
        case 0:
            stateInt=0;
            break;
                //待就诊------->state字段:2
        case 1:
            stateInt=2;
            break;
                //已就诊-------->state字段:4
        case 2:
            stateInt=4;
            break;
                //已取消--------->state字段:3
        case 3:
            stateInt=3;
            break;
    }
    
    
    NSString *uidString=[UserManager manager].uid;
        //测试uidString,写死
        //uidString=@"846fa8695a9b401b8b9d44dff8980b98";


    
//    NSMutableDictionary *dic=@{@"uid":uidString,@"state":@(stateInt)};
//    if (stateInt==0) {
//        dic=@{@"uid":uidString};
//    }

    NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithDictionary:@{@"uid":uidString,@"state":@(stateInt)}];
    if (stateInt==0) {
        dic=[NSMutableDictionary dictionaryWithDictionary:@{@"uid":uidString}];
    }

    [dic setValuesForKeysWithDictionary:self.moreParams];
    

    
    [self.adapter request:QUERYORDERLIST params:dic class:[SCMyOrderModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
    
        ListModel *model=response.data;
        NSArray *arr=model.content;
        self.datas=[self.datas arrayByAddingObjectsFromArray:arr];
        
    
        if (self.datas.count == 0) {
            self.requestCompeleteType = RequestCompeleteEmpty;
            successBlock();
            return ;
        }
        else {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }
        
        
        self.hasMore = [model.more boolValue];
        if (self.hasMore) {
            self.moreParams = model.more_params;
        }else {
            self.moreParams = nil;
        }
    
        successBlock();

        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        if (error.code == -1011) {
                //连接失败
            self.requestCompeleteType = RequestCompeleteError;
        }
        failedBlock();
        
    }];

}




- (void)cancelPreOrderWithParams:(NSDictionary *)dic  success:(void(^)(void))successBlock failed:(void(^)(void))failedBlock{
    if (!dic) {
        return;
    }
    
    [MBProgressHUDHelper showHudIndeterminate];

    [self.adapter request:ORDER_CANCEL params:dic class:nil responseType:Response_Object method:Request_POST needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        if (successBlock) {
            NSString *msg = response.message ? response.message : @"取消预约成功";
            [MBProgressHUDHelper showHudWithText: msg];
            successBlock();
        }
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        if (failedBlock) {
            [MBProgressHUDHelper showHudWithText: error.localizedDescription];
            failedBlock();
        }
        
    }];
    
    
}



-(void)evaluateDoctorWithParams:(NSDictionary *)dic success:(void (^)(void))successBlock failed:(void (^)(void))failedBlock{

    if (!dic) {
        return;
    }
    
    [MBProgressHUDHelper showHudIndeterminate];
    [self.adapter request:DOCTOR_EVALUATE params:dic class:nil responseType:Response_Object method:Request_POST needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        if (successBlock) {
            NSString *msg = response.message ? response.message : @"评价医生成功";
            [MBProgressHUDHelper showHudWithText: msg];
            successBlock();
        }
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        if (failedBlock) {
            [MBProgressHUDHelper showHudWithText: error.localizedDescription];
            failedBlock();
        }
        
    }];





}




@end
