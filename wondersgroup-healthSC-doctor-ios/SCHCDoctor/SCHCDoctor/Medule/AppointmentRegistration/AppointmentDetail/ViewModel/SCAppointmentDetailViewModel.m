//
//  AppointmentDetailViewModel.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDetailViewModel.h"

@implementation SCAppointmentDetailViewModel


//提交预约信息详情
-(void)submitAppointmentDetail: (void(^)(WDRegistrationPayModel *))success
                       failure: (void(^)(void))failure; {
    
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
    }
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithDictionary:
  @{
    @"payMode":@"4",
    @"payState":@"2",
    @"scheduleId":self.model.schedule.scheduleId,
    @"hosOrgCode":self.model.hosOrgCode,
    @"hosName":self.model.hosName,
    @"hosDeptCode":self.model.hosDeptCode,
    @"hosDoctCode":self.model.hosDoctCode,
    @"doctName":self.model.doctorName,
    @"deptName":self.model.deptName,
    @"visitLevel":self.model.schedule.visitLevel,
    @"visitCost":self.model.schedule.visitCost,
    @"timeRange":self.model.schedule.timeRange,
    @"orderTime":[NSString stringWithFormat:@"%@ %@",self.model.schedule.scheduleDate,self.model.schedule.startTime],
    @"uid":[UserManager manager].uid,
    @"userCardType":@"01",
//        @"userCardId":@"510122195903291571",
//        @"userName":@"陈建仁",
//        @"mediCardId":@"000000060033311",
    @"userCardId":[UserManager manager].idcard?[UserManager manager].idcard:@"",
    @"userName":[UserManager manager].name?[UserManager manager].name:@"",
    @"userPhone":[UserManager manager].mobile?[UserManager manager].mobile:@"",
    @"userSex":[NSString stringWithFormat:@"%d",[UserManager manager].gender]?[NSString stringWithFormat:@"%d",[UserManager manager].gender]:@"",
    @"userBD":[UserManager manager].birthday?[UserManager manager].birthday:@"",
    @"mediCardId":self.card
    }];

    [self.adapter request:ORDER_SUBMITORDER params:params class:[WDRegistrationPayModel class] responseType:Response_Object method:Request_POST needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        [MBProgressHUDHelper showHudWithText: response.message];
        NSLog(@"%@",response.data);
        WDRegistrationPayModel *model = response.data;
        model.amount = [NSString stringWithFormat:@"%.0f",[self.model.schedule.visitCost doubleValue]*100];
        model.price = self.model.schedule.visitCost;
        success(model);

    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
        failure();
    }];
}


@end
