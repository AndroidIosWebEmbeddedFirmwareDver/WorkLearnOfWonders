//
//  WDHospitalListRootViewModel.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCHospitalModel.h"

@interface WDHospitalListRootViewModel : BaseViewModel

@property (nonatomic, strong) NSString *cityCode;
@property (nonatomic, strong) NSString *flag;
@property (nonatomic, strong) NSArray<SCHospitalModel *> *hospitalArr;

- (void)requestHospitalList:(void(^)(void))success failure:(void (^)(void))failure;
- (void)requestMoreHospitalList:(void(^)(void))success failure:(void (^)(void))failure;

@end
