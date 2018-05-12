//
//  ErrorModel.h
//  HCPatient
//
//  Created by Jam on 15/6/15.
//  Copyright (c) 2015年 陈刚. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef enum {
    Request_Model_Success = 0,
    Request_Model_InvalidToken = 1,
    Request_Model_Failure = 2,
    Request_Model_Invalid,
    Request_Model_Timeout,
    Request_Model_NoneToken
} RequestStatus;

@interface ErrorModel :  NSObject


//code -- 1:成功; -1:一般性错误; -2:token失效
@property (nonatomic, strong) NSNumber *code;
@property (nonatomic, strong) NSString *msg;
@property (nonatomic, assign) RequestStatus status;


@end
