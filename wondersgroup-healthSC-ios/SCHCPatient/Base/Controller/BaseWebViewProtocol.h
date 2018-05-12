//
//  BaseWebViewProtocol.h
//  SCHCPatient
//
//  Created by Jam on 2016/11/25.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface BaseWebViewProtocol : NSURLProtocol

@property (nonatomic, strong) NSURLConnection *connection;

@end
