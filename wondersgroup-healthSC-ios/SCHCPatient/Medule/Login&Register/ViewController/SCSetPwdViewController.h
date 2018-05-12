//
//  SCSetPwdViewController.h
//  SCHCPatient
//
//  Created by 杜凯 on 2016/11/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"

typedef NS_ENUM(NSUInteger, SetReturnType) {
    SetPopType,
    SetPushType,

};

@interface SCSetPwdViewController : BaseViewController


@property (nonatomic,assign)SetReturnType viewType;

@property (nonatomic,copy)NSString * code;

@property (nonatomic,copy)NSString * phone;
@end
