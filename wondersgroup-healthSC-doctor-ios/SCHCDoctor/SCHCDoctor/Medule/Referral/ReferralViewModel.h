//
//  ReferralViewModel.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/5.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"
#import "ReferralModel.h"
#import "ReferralStateModel.h"
@interface ReferralViewModel : BaseViewModel
@property (assign, nonatomic) NSInteger currentSegmentNum;                       //当前列表
@property (strong, nonatomic) NSArray * segmentTitles;

@property (assign, nonatomic) NSInteger currentPage;                             //当前页码
@property (assign, nonatomic) NSInteger pageSize;                                //每页数据量

@property (strong, nonatomic) NSMutableArray * allModels;                        //所有数据数组
@property (strong, nonatomic) ReferralStateModel * stateModel;                   //状态


/**
 请求数据状态
 */
- (void)requestStateWithSuccess:(void(^)(id content))successBlock
                           fail:(void(^)(NSError * error, NSString * errorString))failBlock;

/**
 请求数据
 */
- (void)requestDataWithPage:(NSInteger)page
                    Success:(void(^)(id content))successBlock
                       fail:(void(^)(NSError * error, NSString * errorString))failBlock;

@end
