//
//  SCTrueNameModel.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface SCTrueNameModel : BaseModel

@property (nonatomic, strong) NSString *uid;
@property (nonatomic, strong) NSString *success;    //实名认证是否成功
@property (nonatomic, strong) NSString *status;     //实名认证状态 0-未提交,1-认证失败,2-审核中,3-认证成功
@property (nonatomic, strong) NSString *statusSpec; //实名认证状态文字
@property (nonatomic, strong) NSString *name;       //姓名
@property (nonatomic, strong) NSString *msg;        //审核失败信息
@property (nonatomic, strong) NSString *idcard;     //身份证号
@property (nonatomic, strong) NSString *can_submit; //是否可以提交实名认证信息

@end
