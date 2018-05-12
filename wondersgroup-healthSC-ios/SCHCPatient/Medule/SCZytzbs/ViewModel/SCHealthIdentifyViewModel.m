//
//  WDHealthIdentifyViewModel.m
//  SHHealthCloudNormal
//
//  Created by wanda on 16/8/10.
//  Copyright © 2016年 WondersGroup. All rights reserved.
//

#import "SCHealthIdentifyViewModel.h"

@implementation SCHealthIdentifyViewModel

- (void)postHealthIdentifyWithParams:(NSString*)resultStr :(void(^)(void))success
                             failure: (void(^)(void))failure
{
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
    }
    NSMutableDictionary * dic = [NSMutableDictionary dictionary];
    if([UserManager manager].uid.length > 0) {
    [dic setObject:[UserManager manager].uid forKey:@"registerid"];
    }
    
    [dic setObject:self.resultStr forKey:@"content"];
    
    //@"http://127.0.0.1/temp9090.json" ZYTZBSURL
    [self.adapter request:ZYTZBSURL params:dic class:[SCHealthIdentifyModel class] responseType:Response_List method:Request_POST needLogin:NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
         self.dataArray = response.data;
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        self.requestCompeleteType = RequestCompeleteError;
        failure();
        
    }];
  
    
}
@end
