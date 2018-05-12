//
//  ReportNoticeViewModel.m
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSystemListViewModel.h"
#import "SCSystemListModel.h"
@implementation SCSystemListViewModel


-(id) init
{
    self = [super init];
    if(self)
    {
        self.DataArray = [NSMutableArray new];
    }
    return self;
}

-(void)getSystemMessageRequest
{
    NSMutableArray *a = [[NSMutableArray alloc] init];
    
    SCSystemListModel * modelOne = [[SCSystemListModel alloc] init];
    modelOne.messageId=@"1";
    modelOne.date=@"2016-09-26";
    modelOne.title=@"万达健康2.0版本更新通";
    modelOne.picurl=@"http://pic41.nipic.com/20140501/2531170_162158900000_2.jpg";
    modelOne.des =@"万达健康2.0版本更新la";
    modelOne.linkUp = @"123123";
    [a addObject:modelOne];
    
    SCSystemListModel * modeldrc = [[SCSystemListModel alloc] init];
    modeldrc.messageId=@"1";
    modeldrc.date=@"2016-09-26 9:28";
    modeldrc.title=@"万达健康2.0版本更新通";
    modeldrc.picurl=@"";
    modeldrc.des =@"万达健康2.0版本更新la";
    modeldrc.linkUp = nil;
    [a addObject:modeldrc];
    
    self.DataArray = a;
    
}


-(void)getSystemMessage:(NSDictionary*)dic success:(IMPLCompleteBlock)success failure:(IMPLFailuredBlock)failer
{
    
    
    if(![[Global global]networkReachable ])
    {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    NSMutableDictionary * pra = [NSMutableDictionary new];
    [pra addEntriesFromDictionary:dic];
    if(self.moreParams)
    {
        [pra addEntriesFromDictionary:self.moreParams];
    }
    
    WS(weakSelf);

    [self.adapter request:SYSTEM_MESSAGE_LIST_URL  params:pra class:[SCSystemListModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        ListModel * model = response.data;
        if (weakSelf.moreParams==nil)
        {
            [weakSelf.DataArray removeAllObjects];
        }
        
        
        weakSelf.moreParams = model.more?model.more_params : nil;
        weakSelf.hasMore = [model.more boolValue];
        [weakSelf.DataArray addObjectsFromArray: model.content];
        
        if(weakSelf.DataArray.count ==0)
        {
            weakSelf.requestCompeleteType = RequestCompeleteEmpty;
        }
        else
            weakSelf.requestCompeleteType =RequestCompeleteSuccess;
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        weakSelf.requestCompeleteType = RequestCompeleteError;
        failer(error);
    }];
    
}
@end
