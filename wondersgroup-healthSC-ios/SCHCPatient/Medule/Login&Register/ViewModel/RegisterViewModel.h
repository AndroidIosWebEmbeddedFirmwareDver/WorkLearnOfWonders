//
//  RegisterViewModel.h
//  SCHCPatient
//
//  Created by Jam on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"

@interface RegisterViewModel : BaseViewModel<UserCenterIMPL>

@property (nonatomic, assign) BOOL enableRegister;
@property (nonatomic, assign) BOOL enableSend;


@property (nonatomic, copy) NSString *mobile;       //手机号
@property (nonatomic, copy) NSString *code;         //验证码



@end
