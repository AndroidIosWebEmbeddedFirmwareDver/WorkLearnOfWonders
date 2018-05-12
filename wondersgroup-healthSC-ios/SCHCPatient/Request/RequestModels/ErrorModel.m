//
//  ErrorModel.m
//  HCPatient
//
//  Created by Jam on 15/6/15.
//  Copyright (c) 2015年 陈刚. All rights reserved.
//

#import "ErrorModel.h"

@implementation ErrorModel

- (void)setCode:(NSNumber *)code {
    _code = code;
    switch ([code intValue]) {
        case 1: {
            _status = Request_Model_Success;
//            _msg    = @"成功";
        }
            break;
        case -1: {
            _status = Request_Model_Failure;
//            _msg    = @"失败";
        }
            break;
        case 10: {
            _status = Request_Model_NoneToken;
        }
            break;
        case 12: {
            _status = Request_Model_InvalidToken;
            _msg    = @"用户登录失效，请重新登录。";
        }
            break;
        case 13: {
            _status = Request_Model_InvalidToken;
            _msg    = @"用户登录失效，请重新登录。";
        }
            break;
        case -3: {
            _status = Request_Model_Invalid;
            _msg    = @"数据异常。请稍后重试。";
        }
        case NSURLErrorTimedOut: {
            _status = Request_Model_Timeout;
            _msg    = @"网络连接超时.请检查网络。";
        }
            break;
        case 404: {
            _status = Request_Model_Failure;
            _msg    = @"服务器连接失败。";
        }
            break;
        default: {
            _status = Request_Model_Failure;
//            _msg    = @"失败";
        }
            break;
    }
}

@end
