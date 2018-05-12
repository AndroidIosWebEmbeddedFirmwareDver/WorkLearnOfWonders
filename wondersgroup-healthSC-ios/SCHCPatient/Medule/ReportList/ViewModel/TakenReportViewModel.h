//
//  TakenReportViewModel.h
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"

@interface TakenReportViewModel : BaseViewModel

@property (strong, nonatomic) NSArray *dataArray;
@property (copy,   nonatomic) NSString *keyWord;//模糊搜索的关键字
- (void)getHostptialNameSuccess:(void(^)(void))success failed:(void(^)(NSError *error))failed;
@end
