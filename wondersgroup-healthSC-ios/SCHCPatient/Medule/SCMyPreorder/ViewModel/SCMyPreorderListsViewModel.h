//
//  SCMyPreorderListsViewModel.h
//  SCHCPatient
//
//  Created by ZJW on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCMyPreorderListsModel.h"

@interface SCMyPreorderListsViewModel : BaseViewModel<MyAppointmentIMPL>

@property (nonatomic, strong) NSString *searchString;//搜索字段
@property (nonatomic, strong) NSArray *datas;



    //- (void)getPreOrderDataFromeServer:(void(^)(NSArray*))success failed:(void(^)(void))failed;

//- (void)insertWhenNotExists:(NSObject*)model callback:(void (^)(BOOL result))block;

/**
 获取预约列表数据

 @param preOrderState <#preOrderState description#>
 @param successBlock  <#successBlock description#>
 @param failedBlock   <#failedBlock description#>
 */
- (void)getPreOrderDataFromeServer:(int)preOrderState  success:(void(^)(void))successBlock failed:(void(^)(void))failedBlock;



/**
 获取更多预约列表数据

 @param preOrderState <#preOrderState description#>
 @param successBlock  <#successBlock description#>
 @param failedBlock   <#failedBlock description#>
 */
- (void)getMorePreOrderDataFromeServer:(int)preOrderState  success:(void(^)(void))successBlock failed:(void(^)(void))failedBlock;



/**
 取消预约

 @param dic          dic
 @param successBlock success
 @param failedBlock  failed
 */
- (void)cancelPreOrderWithParams:(NSDictionary *)dic  success:(void(^)(void))successBlock failed:(void(^)(void))failedBlock;




/**
 评价医生
 
 @param dic          dic
 @param successBlock success
 @param failedBlock  failed
 */
- (void)evaluateDoctorWithParams:(NSDictionary *)dic  success:(void(^)(void))successBlock failed:(void(^)(void))failedBlock;







@end
