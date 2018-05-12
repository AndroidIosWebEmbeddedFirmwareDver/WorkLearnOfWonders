//
//  PayNoticeViewModel.m
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "PayNoticeViewModel.h"
#import "PayNoticeModel.h"
@implementation PayNoticeViewModel
-(id)init
{
    self  = [super init];
    if(self)
    {
        self.dataArray = [NSMutableArray new];
        
    }
    return self;
}
-(void)getDataByrequest
{
//    NSMutableArray * a = [NSMutableArray new];
//    
//    
//    {
//        PayNoticeModel * model  = [[PayNoticeModel alloc] init];
//        model.ID=@"13";
//        model.type=@1;
//        model.payType=@1;
//        model.orderId=@"123132";
//        model.hospitalName=@"四川洋山医院";
//        model.patientName=@"张晓亮";
//        model.department=@"皮肤科";
//        model.doctorName=@"张医生";
//        model.price=@50;
//        model.payStatus=@1; //0 支付失败， 1 支付成功
//        model.payType=@2;
//        model.payTypeName=@"挂号支付";
//        model.clinicType=@"专家门诊";
//        model.prescriptionCode=@"1231321231";
//        model.prescriptionTime=@"2016-07-20 周一 上午";
//        model.orderTime = @"123123123";
//        model.createDate=@"2016-07-08 10:10:13";
//        model.registerId=@"1";
//        model.messageId=@"12312313123132";
//        model.linkUp=@"123";
//        model.state=@1;
//        
//        [a addObject:model];
//    }
//    {
//        PayNoticeModel * model  = [[PayNoticeModel alloc] init];
//        model.ID=@"13";
//        model.type=@1;
//        model.payType=@1;
//        model.orderId=@"123132";
//        model.hospitalName=@"四川洋山医院";
//        model.patientName=@"张晓亮";
//        model.department=@"皮肤科";
//        model.doctorName=@"张医生";
//        model.price=@50;
//        model.payStatus=@1; //0 支付失败， 1 支付成功
//        model.payType=@1;
//        model.payTypeName=@"挂号支付";
//        model.clinicType=@"专家门诊";
//        model.prescriptionCode=@"1231321231";
//        model.prescriptionTime=@"2016-07-20 周一 上午";
//        model.orderTime = @"123123123";
//        model.createDate=@"2016-07-08 10:10:13";
//        model.registerId=@"1";
//        model.messageId=@"12312313123132";
//        model.linkUp=@"123";
//        model.state=@1;
//
//        [a addObject:model];
//    }
//    {
//        PayNoticeModel * model  = [[PayNoticeModel alloc] init];
//        model.messageID=@"13";
//        model.projectType=@"1";
//        model.ProjectTitle=@"百度";
//        model.payStats =0;
//        model.orderID=@"12312312313";
//        model.content=@[@"qweqwe",@"qweqw",@"qweqwe",@"qweqwe",@"qweqw",@"qweqwe"];
//        model.linkUp=@"123";
//        model.date=@"2016 -7 -10 10:10";
//        [a addObject:model];
//    }
//    {
//        PayNoticeModel * model  = [[PayNoticeModel alloc] init];
//        model.messageID=@"13";
//        model.projectType=@"1";
//        model.ProjectTitle=@"百度";
//        model.payStats =0;
//        model.orderID=@"12312312313";
//        model.content=@[@"qweqwe",@"qweqw",@"qweqwe",@"qweqwe",@"qweqw",@"qweqwe",@"qweqwe",@"qweqwe",@"qweqw",@"qweqwe",@"qweqwe",@"qweqw",];
//        model.linkUp=@"123";
//        model.date=@"2016 -7 -10 10:10";
//        [a addObject:model];
//    }
    
    
}


-(void)getPayList:(NSDictionary *)dic success:(IMPLCompleteBlock)success failed:(IMPLFailuredBlock) failed
{
        if(![[Global global] networkReachable])
        {
            self.requestCompeleteType = RequestCompeleteNoWifi;
            return;
        }
    NSMutableDictionary *pramas = [NSMutableDictionary new];
    [pramas addEntriesFromDictionary:dic];
    if(self.moreParams)
    {
        [pramas addEntriesFromDictionary:self.moreParams];
    }
    
    WS(weakSelf);
    [self.adapter request:PAY_MESSAGE_LIST_URL params:pramas class:[PayNoticeModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        if(weakSelf.moreParams==nil)
        {
            [weakSelf.dataArray removeAllObjects];
        }
        
        ListModel *list = response.data;
        
        weakSelf.hasMore = [list.more boolValue];
        
        
        weakSelf.moreParams = self.hasMore?list.more_params : nil;
        
        
        [weakSelf.dataArray addObjectsFromArray:list.content];
        if(weakSelf.dataArray.count ==0)
        {
            weakSelf.requestCompeleteType = RequestCompeleteEmpty;
        }
        else
        {
            weakSelf.requestCompeleteType = RequestCompeleteSuccess;
        }
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        weakSelf.requestCompeleteType = RequestCompeleteError;
        failed(error);
    }];
    
}

@end
