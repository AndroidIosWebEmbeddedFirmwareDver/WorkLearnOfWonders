//
//  UserManager.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UserModel.h"

@interface UserManager : NSObject

@property (nonatomic, assign)   BOOL    isLogin;     //是否登录状态



//登录用户信息
@property (nonatomic, copy)     NSString *token;
@property (nonatomic, copy)     NSString *key;
@property (nonatomic, copy)     NSString *uid;
@property (nonatomic, copy)     NSString *accountName;   //登录名

@property (nonatomic,copy)      NSString * accountPwd;  //登录的密码
@property (nonatomic, assign)     BOOL first_login;   //第一次登录
@property (nonatomic, assign)     BOOL password_complete;   //该账户是否设置过密码
@property (nonatomic, copy)     NSString *nickname;
@property (nonatomic, copy)     NSString *name;
@property (nonatomic, copy)     NSString *talkid;
@property (nonatomic, copy)     NSString *talkpwd;
@property (nonatomic, copy)     NSString *avatar;           //用户头像
@property (nonatomic, copy)     NSNumber *attentCount;  //关注数
@property (nonatomic, copy)     NSNumber *fansCount;    //粉丝数

@property (nonatomic, copy)     NSString *mobile;
@property (nonatomic, copy)     NSString *age;
@property (nonatomic, copy)     NSString *birthday;
@property (nonatomic, copy)     NSString *idcard;   //身份证号
@property (nonatomic, assign)   int       gender;           //1男  2女
@property (nonatomic, assign)   int verificationStatus;    //实名认证状态 0-未提交,1-认证失败,2-审核中,3-认证成功

@property (nonatomic, assign)   BOOL    isPWDLogin;     //是否是账号密码登录



@property (nonatomic, assign)   NSInteger       messageCount;   //未读消息数量




+ (UserManager *)manager;

//获取本地用户信息
- (void)loadUser;

//存储用户何种方式登录
- (void)userSaveLoginStatus:(NSString *)loginAccount withLoginType:(BOOL)pwdLogin;

//用户登录后更新用户信息
- (void)updateLoginInfo: (UserModel *)user;

//根据获取的用户信息更新本地用户
- (void)updateUserInfo: (UserInfoModel *)info;

//用户修改信息后更新用户信息
- (void)updateEditInfo: (NSDictionary *)editInfo;

//用户注销后更新用户信息
- (void)updateLogOffInfo:(BOOL)backToLogin;
//保存用户密码到本地
- (void)saveUserPwd:(NSString *)pwd;
//获取用户密码
- (NSString *)getUserPwd;

@end
