//
//  BaseViewModel.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ViewModelIMPL.h"
#import "ResponseAdapter.h"



typedef NS_ENUM(NSInteger, RequestCompeleteType) {
    RequestCompeleteEmpty = 100,    //空数据
    RequestCompeleteNoWifi,         //没有网络
    RequestCompeleteError,          //错误数据
    RequestCompeleteSuccess         //成功数据
};

@interface BaseViewModel : NSObject

/**
 *  失败后服务器提示
 */
@property (nonatomic, strong) NSString *tipErrorString;
/**
 *  数据状态
 */
@property (nonatomic, assign) RequestCompeleteType requestCompeleteType;
/**
 *  是否还有更多数据
 */
@property (nonatomic, assign) BOOL hasMore;
/**
 *  是否还有更多数据传给服务器的字典
 */
@property (nonatomic, strong) NSDictionary *moreParams;
/**
 *  下载适配器
 */
@property (nonatomic, strong) ResponseAdapter *adapter;


@end
