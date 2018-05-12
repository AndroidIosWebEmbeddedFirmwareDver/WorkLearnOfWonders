//
//  SystemViewModel.m
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSystemViewModel.h"
#import "SCSystemModel.h"

@implementation SCSystemViewModel
-(void)getSystemMessageRequest
{
    NSMutableArray * a = [NSMutableArray new];
    SCSystemModel * modelsystem = [[SCSystemModel alloc] init];
    modelsystem.messageCount=@0;
    modelsystem.content=@"";
    modelsystem.type =@1;
    
    SCSystemModel * modelpay = [[SCSystemModel alloc] init];
    modelpay.messageCount=@0;
    modelpay.content=@"";
    modelpay.type =@2;
    SCSystemModel * modelreport = [[SCSystemModel alloc] init];
    modelreport.messageCount=@0;
    modelreport.content=@"";
    modelreport.type =@3;
    
    [a addObject:modelsystem];
    [a addObject:modelpay];
    [a addObject:modelreport];
    self.dataArray = a.copy;
}

-(void)getSystemMessageRequest:(NSDictionary *)dic success:(IMPLCompleteBlock) success falied:(IMPLFailuredBlock) failed
{
    
    if(![[Global global] networkReachable])
    {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return ;
    }
    
    WS(weakSelf);
    
    [self.adapter request:Messages_URL params:dic class:[SCSystemModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        ListModel * list = response.data;
        weakSelf.dataArray = list.content;
        
        if(weakSelf.dataArray.count ==0)
        {
            weakSelf.requestCompeleteType = RequestCompeleteEmpty;
            
        }
        else
        {
            weakSelf.requestCompeleteType = RequestCompeleteSuccess;
        }
        
        int co=0;
        for (SCSystemModel * m  in list.content) {
            co += [ m.messageCount  integerValue];
        }
        
        [UserManager manager].messageCount = co;
        
        
        
        
        success();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        weakSelf.requestCompeleteType = RequestCompeleteError;
        failed(error);
    }];

}

-(void)updateMessageIdStatus:(NSDictionary *)dic success:(IMPLCompleteBlock) success falied:(IMPLFailuredBlock) failed
{
    [self.adapter request:SYSTEM_MESSAGE_UPDATEBYID params:dic class:nil responseType:Response_Message method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        NSLog(@"response data %@",response.message);
        success();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        self.requestCompeleteType = RequestCompeleteError;
        failed(error);
    }];


}
@end
