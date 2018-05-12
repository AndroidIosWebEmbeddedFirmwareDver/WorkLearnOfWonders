//
//  SignatureManager.h
//  VaccinePatient
//
//  Created by Jam on 16/8/30.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SignatureManager : NSObject

#pragma mark - Request的Header、签名类
/**
 *  为接口请求、WebView的Request添加Header
 *
 *  @param  request : 所要添加Header的Request
 *  @param  params  : request传参，生成签名的时候需要用到
 *  @param  method  : request方法，如果是POST方法，生成签名的时候需要把Params转成JSONString，反之，签名的时候直接拼接
 *  @param  url     : 请求路径
 *  @param  isAPI   : 是否是后台接口请求
 */
+ (void)addHeader: (id)request
           params: (NSDictionary *)params
           method: (NSString *)method
      requestPath: (NSString *)url
          withAPI: (BOOL)isAPI;

@end
