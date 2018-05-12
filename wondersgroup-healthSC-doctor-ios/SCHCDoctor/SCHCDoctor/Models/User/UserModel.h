//
//  UserModel.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

//用户详情
@interface UserInfoModel : BaseModel


@property (nonatomic, copy)     NSString *nickname;         //用户昵称
@property (nonatomic, copy)     NSString *name;             //用户姓名
@property (nonatomic, copy)     NSString *avatar;           //用户头像
@property (nonatomic, copy)     NSString *mobile;           //用户手机号
@property (nonatomic, copy)     NSString *age;               //用户年龄
@property (nonatomic, copy)     NSNumber *attentCount;      //关注数
@property (nonatomic, copy)     NSNumber *fansCount;        //粉丝数

@property (nonatomic, copy)     NSString *talkid;
@property (nonatomic, copy)     NSString *talkpwd;

@property (nonatomic, copy)     NSNumber *isBBsAdmin;
@property (nonatomic, copy)     NSNumber *nicknameEnable;   //是否可以修改昵称
@property (nonatomic, copy)     NSNumber *isChanged;        //是否修改过昵称 用于第一次进入圈子判断

@property (nonatomic, copy)     NSString *birthday;
@property (nonatomic, copy)     NSString *idcard;
@property (nonatomic, strong)   NSNumber *gender;
@property (nonatomic, strong)   NSNumber *password_complete;
@property (nonatomic, strong)   NSNumber *verificationStatus;



@end


//用户基本信息
@interface UserModel : BaseModel

@property (nonatomic, copy)     NSString    *uid;
@property (nonatomic, copy)     NSString    *token;
@property (nonatomic, copy)     NSString    *key;
@property (nonatomic, strong)    NSNumber    *first_login;

@property (nonatomic,copy)   NSString * pwd;

@property (nonatomic, strong)   UserInfoModel *info;    //用户信息
@property (nonatomic, copy)     NSString    *loginName;     //登录名
@property (nonatomic, assign)   NSNumber    *isLogin;       //是否登录状态


@end

