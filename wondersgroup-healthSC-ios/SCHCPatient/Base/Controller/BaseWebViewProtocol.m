//
//  BaseWebViewProtocol.m
//  SCHCPatient
//
//  Created by Jam on 2016/11/25.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseWebViewProtocol.h"
#import "SignatureManager.h"
#import "UrlParamUtil.h"

@implementation BaseWebViewProtocol


//@method:创建NSURLProtocol实例，NSURLProtocol注册之后，所有的NSURLConnection都会通过这个方法检查是否持有该Http请求。
//@return: YES：持有该Http请求NO：不持有该Http请求

+ (BOOL)canInitWithRequest:(NSURLRequest *)request {
    
    static NSUInteger requestCount =0;
    NSLog(@"Request #%lu: URL = %@", (unsigned long)requestCount++, request);
    if ([NSURLProtocol propertyForKey:@"MyURLProtocolHandledKey"inRequest: request]) {
        return NO;
    }
    return YES;
}

- (id)initWithRequest:(NSURLRequest *)request cachedResponse:(NSCachedURLResponse *)cachedResponse client:(id <NSURLProtocolClient>)client {
    return [super initWithRequest:request cachedResponse:cachedResponse client:client];
}

#pragma mark --NSURLProtocol Hold Relevant Method
/**
 @method: NSURLProtocol抽象类必须要实现。通常情况下这里有一个最低的标准：即输入输出请求满足最基本的协议规范一致。因此这里简单的做法可以直接返回。一般情况下我们是不会去更改这个请求的。如果你想更改，比如给这个request添加一个title，组合成一个新的http请求。
 @parma : 本地HttpRequest请求：request
 @return: 直接转发
 */
+ (NSURLRequest *)canonicalRequestForRequest:(NSURLRequest *)request {
    return request;
}
/**
 @method: NSURLProtocol缓存系统设置：如果有两个URL请求，并且他们是相等的，那么这里可以使用相同的缓存空间
 @parma : 本地HttpRequest请求：request
 @return:
 */
+ (BOOL)requestIsCacheEquivalent:(NSURLRequest *)a toRequest:(NSURLRequest *)b {
    return [super requestIsCacheEquivalent:a toRequest:b];
}

//- (void)startLoading {
//    NSURLSession *session = [NSURLSession sessionWithConfiguration:[[NSURLSessionConfiguration alloc] init] delegate:self delegateQueue:nil];
//    NSURLSessionDataTask *task = [session dataTaskWithRequest:self.request];
//    [task resume];
//}

- (void)startLoading {
    NSMutableURLRequest *newRequest = [self.request mutableCopy];
    NSDictionary *params = [UrlParamUtil getParamsFromUrl: newRequest.URL];
    [SignatureManager addHeader: newRequest params: params method: @"GET" requestPath: newRequest.URL.path withAPI: NO];
    [NSURLProtocol setProperty:@YES forKey:@"MyURLProtocolHandledKey" inRequest: newRequest];
    self.connection = [NSURLConnection connectionWithRequest:newRequest delegate:self];
}

/**
 @method: 当前Connection连接取消的时候被调用
 @parma :
 @return:
 */
- (void)stopLoading {
    [self.connection cancel];
    self.connection =nil;
}


- (void)URLSession:(NSURLSession *)session dataTask:(NSURLSessionDataTask *)dataTask didReceiveResponse:(NSURLResponse *)response completionHandler:(void (^)(NSURLSessionResponseDisposition))completionHandler {
    [[self client] URLProtocol:self didReceiveResponse:response cacheStoragePolicy:NSURLCacheStorageAllowed];
    
    completionHandler(NSURLSessionResponseAllow);
}

- (void)URLSession:(NSURLSession *)session dataTask:(NSURLSessionDataTask *)dataTask didReceiveData:(NSData *)data {
    [[self client] URLProtocol:self didLoadData:data];
}

#pragma mark --NSURLProtocol Delegate
- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response {
    
    [self.client URLProtocol:self didReceiveResponse:response cacheStoragePolicy:NSURLCacheStorageNotAllowed];
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data {
    [self.client URLProtocol:self didLoadData:data];
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    [self.client URLProtocolDidFinishLoading:self];
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
    [self.client URLProtocol:self didFailWithError:error];
}


@end
