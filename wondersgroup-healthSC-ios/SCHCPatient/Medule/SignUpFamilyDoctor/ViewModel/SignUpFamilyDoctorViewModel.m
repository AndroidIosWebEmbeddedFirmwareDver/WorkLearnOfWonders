//
//  SignUpFamilyDoctorViewModel.m
//  SCHCPatient
//
//  Created by ZJW on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "SignUpFamilyDoctorViewModel.h"

@implementation SignUpFamilyDoctorViewModel

-(void)getSignUpNumber {
    
    self.doctorNumber = @11;
    self.familyNumber = @13;
    self.requestCompeleteType = RequestCompeleteSuccess;
    return;
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithDictionary:
                                   @{
                                     }];
    
    [self.adapter request:ORDER_SUBMITORDER params:params class:[WDRegistrationPayModel class] responseType:Response_Object method:Request_POST needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        NSLog(@"%@",response.data);
//        WDRegistrationPayModel *model = response.data;
//        self.doctorNumber = model.orderId;
//        self.familyNumber = model.orderId;
        self.doctorNumber = @11;
        self.familyNumber = @1213;

        self.requestCompeleteType = RequestCompeleteSuccess;
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        self.requestCompeleteType = RequestCompeleteError;
        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
    }];
}

@end
