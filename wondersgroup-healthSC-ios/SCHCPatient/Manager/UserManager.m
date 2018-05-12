//
//  UserManager.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UserManager.h"
#import "DBManager.h"
#import "UserService.h"

@interface UserManager()

@end



@implementation UserManager

+ (UserManager *)manager {
    
    static dispatch_once_t onceToken;
    static UserManager *manager = nil;
    dispatch_once(&onceToken, ^{
        manager = [[UserManager alloc] init];
    });
    return manager;
}

- (instancetype)init {
    self = [super init];
    if (self) {
        
        
    }
    return self;
}

#pragma mark - 获取本地用户信息
- (void)loadUser {
    UserModel *model = [[DBManager manager] getUser];
    if (model) {
        if (![model.isLogin boolValue]||model.uid.length == 0) {
            //用户未登录，启用游客登录
            [self updateLogOffInfo: NO];
            [[UserService service]userGestLoginComplete:^{
                
            }];
        }
        else { 
            //登录用户信息赋值
            [self updateLoginInfo: model];
            //获取最新用户信息
            [[UserService service] refreshLastUserInfo];
        }
    }
    else {
        //未登录用户信息赋值
        [self updateLogOffInfo: NO];
    }
    
    //更新用户登录模式：账号密码登录、动态码登录
    [self updateLoginStatusType];
    
}

#pragma mark - 用户登录后更新用户信息
- (void)updateLoginInfo: (UserModel *)user {
    
    if (![self.token isEqualToString: user.token]) {
        self.token  = user.token;
    }
    if (![self.key isEqualToString: user.key]) {
        self.key    = user.key;
    }
    if (![self.uid isEqualToString: user.uid]) {
        self.uid  = user.uid;
    }
    if (self.first_login  != [user.first_login boolValue]) {
        self.first_login = [user.first_login boolValue];
    }
    if (self.password_complete  != [user.info.password_complete boolValue]) {
        self.password_complete = [user.info.password_complete boolValue];
    }
    if (self.verificationStatus  != [user.info.verificationStatus intValue ]) {
        self.verificationStatus = [user.info.verificationStatus intValue];
    }
    if (![self.accountName isEqualToString: user.loginName]) {
        self.accountName = user.loginName;
    }
    if (![self.nickname isEqualToString: user.info.nickname]) {
        self.nickname = user.info.nickname;
    }
    if (![self.name isEqualToString: user.info.name]) {
        self.name = user.info.name;
    }
    if (![self.talkId isEqualToString: user.info.talkId]) {
        self.talkId = user.info.talkId;
    }
    if (![self.talkPwd isEqualToString: user.info.talkPwd]) {
        self.talkPwd = user.info.talkPwd;
    }
    if (![self.signFamilyTeamId isEqualToString: user.info.signFamilyTeamId]) {
        self.signFamilyTeamId = user.info.signFamilyTeamId;
    }
    if (![self.avatar isEqualToString: user.info.avatar]) {
        self.avatar = user.info.avatar;
    }
    if (![self.age isEqualToString: user.info.age]) {
        self.age = user.info.age;
    }
    if (![self.mobile isEqualToString: user.info.mobile]) {
        self.mobile = user.info.mobile;
    }
    if (![self.birthday isEqualToString: user.info.birthday]) {
        self.birthday = user.info.birthday;
    }
    if (![self.idcard isEqualToString: user.info.idcard]) {
        self.idcard = user.info.idcard;
    }
    if (self.gender != [user.info.gender intValue]) {
        self.gender = [user.info.gender intValue];
    }
    if ([self.attentCount intValue] != [user.info.attentCount intValue]) {
        self.attentCount = user.info.attentCount;
    }
    if ([self.fansCount intValue] != [user.info.fansCount intValue]) {
        self.fansCount = user.info.fansCount;
    }
    
    [[DBManager manager] upDateUser: user];
    
    if (self.isLogin != [user.isLogin boolValue]) {
        self.isLogin = [user.isLogin boolValue];
    }
}

#pragma mark - 根据获取的用户信息更新本地用户
- (void)updateUserInfo: (UserInfoModel *)info {
    
    UserModel *user = [[DBManager manager] getUser];
    
    if (![info.nickname isEqualToString: self.nickname]) {
        user.info.nickname  = info.nickname;
        self.nickname       = info.nickname;
    }
    
    if (![info.name isEqualToString: self.name]) {
        user.info.name  = info.name;
        self.name       = info.name;
    }
    
    if (![info.talkId isEqualToString: self.talkId]) {
        user.info.talkId    = info.talkId;
        self.talkId         = info.talkId;
    }
    
    if (![info.talkPwd isEqualToString: self.talkPwd]) {
        user.info.talkPwd   = info.talkPwd;
        self.talkPwd        = info.talkPwd;
    }
    if (![info.signFamilyTeamId isEqualToString: self.signFamilyTeamId]) {
        user.info.signFamilyTeamId    = info.signFamilyTeamId;
        self.signFamilyTeamId         = info.signFamilyTeamId;
    }
    if (![info.avatar isEqualToString: self.avatar]) {
        user.info.avatar   = info.avatar;
        self.avatar        = info.avatar;
    }
    
    if (![info.mobile isEqualToString: self.mobile]) {
        user.info.mobile   = info.mobile;
        user.loginName     = info.mobile;
        self.mobile        = info.mobile;
        self.accountName   = info.mobile;
    }

    if (![info.birthday isEqualToString: self.birthday]) {
        user.info.birthday  = info.birthday;
        self.birthday       = info.birthday;
    }
    if (![info.idcard isEqualToString: self.idcard]) {
        user.info.idcard  = info.idcard;
        self.idcard       = info.idcard;
    }
    if (![info.age isEqualToString: self.age]) {
        user.info.age  = info.age;
        self.age       = info.age;
    }
    if ([info.gender intValue] != self.gender) {
        user.info.gender    = info.gender;
        self.gender         = [info.gender intValue];
    }

    if ([info.attentCount intValue] != [self.attentCount intValue]) {
        user.info.attentCount = info.attentCount;
        self.attentCount      = info.attentCount;
    }
    if ([info.fansCount intValue] != [self.fansCount intValue]) {
        user.info.fansCount = info.fansCount;
        self.fansCount      = info.fansCount;
    }
    if (self.password_complete  != [info.password_complete boolValue]) {
         user.info.password_complete = info.password_complete;
        self.password_complete = [user.info.password_complete boolValue];
    }
    
    if (self.verificationStatus  != [info.verificationStatus intValue]) {
         user.info.verificationStatus = info.verificationStatus;
        self.verificationStatus = [user.info.verificationStatus intValue];
    }
    [[DBManager manager] upDateUser: user];

}


#pragma mark - 用户修改信息后更新用户信息
- (void)updateEditInfo: (NSDictionary *)editInfo {

    UserModel *user = [[DBManager manager] getUser];

    for (NSString *key in [editInfo allKeys]) {
        if ([key isEqualToString: @"avatar"] && ![user.info.avatar isEqualToString: editInfo[key]]) {
            user.info.avatar    = editInfo[key];
            self.avatar         = user.info.avatar;
        }
        else if ([key isEqualToString: @"gender"] && [user.info.gender intValue] != [editInfo[key] intValue]) {
            user.info.gender    = editInfo[key];
            self.gender         = [user.info.gender intValue];
        }
        else if ([key isEqualToString: @"birthday"] && ![user.info.birthday isEqualToString: editInfo[key]]) {
            user.info.birthday  = editInfo[key];
            self.birthday       = user.info.birthday;
        }
        else if ([key isEqualToString: @"nickname"] && ![user.info.nickname isEqualToString: editInfo[key]]) {
            user.info.nickname  = editInfo[key];
            self.nickname       = user.info.nickname;
        }
        else if ([key isEqualToString: @"name"] && ![user.info.name isEqualToString: editInfo[key]]) {
            user.info.name  = editInfo[key];
            self.name       = user.info.name;
        }
        else if ([key isEqualToString: @"mobile"] && ![user.info.mobile isEqualToString: editInfo[key]]) {
            user.info.mobile  = editInfo[key];
            self.mobile       = user.info.mobile;
        }
        else if ([key isEqualToString: @"age"] && ![user.info.age isEqualToString: editInfo[key]]) {
            user.info.age  = editInfo[key];
            self.age       = user.info.age;
        }
    }
    [[DBManager manager] upDateUser: user];
}



#pragma mark - 用户注销后更新用户信息
- (void)updateLogOffInfo:(BOOL)backToLogin {
    //更新游客信息
//    [self updateGuestUser];
    
    //重置所有的倒计时
    [[CountDownManager manager] resetAllCountDownTimer];
    
    UserModel *user = [[DBManager manager] getUser];
    //登录用户状态改为NO
    if (user) {
        user.isLogin = [NSNumber numberWithBool: NO];
        [[DBManager manager] upDateUser: user];
        self.accountName = user.loginName;
    }
    if (self.isLogin) {
        self.isLogin = NO;
    }
    
    self.token    = [[UserDefaults shareDefaults] objectForKey: GuestUserToken];
    self.key      = [[UserDefaults shareDefaults] objectForKey: GuestUserKey];
    self.uid = @"";
    self.nickname = @"";
    self.name = @"";
    self.talkId   = nil;
    self.talkPwd  = nil;
    self.signFamilyTeamId = nil;
    self.avatar   = @"";
    self.mobile   = @"";
    self.birthday = @"";
    self.gender   = 0;
    self.attentCount = @0;
    self.fansCount = @0;
    self.age = @"",
    self.password_complete = 0;
    self.verificationStatus = 0;
    self.messageCount = 0;
    if(backToLogin) {
        [[VCManager manager]showHomeViewController:NO];
        [[VCManager manager] presentLoginViewController: YES];
    }
}


#pragma mark - 游客登录更新信息
- (void)guestLoginUpdateInfo:(UserModel *)user{
    [[UserDefaults shareDefaults]setObject:user.token forKey:GuestUserToken];
    [[UserDefaults shareDefaults]setObject:user.key forKey:GuestUserKey];
    
}
//- (void)updateGuestUser {
//    
//    self.guestToken = [[UserDefaults shareDefaults] objectForKey: GuestUserToken];
//    self.guestKey   = [[UserDefaults shareDefaults] objectForKey: GuestUserKey];
//
//}



#pragma mark - 存储用户何种方式登录
- (void)userSaveLoginStatus:(NSString *)loginAccount withLoginType:(BOOL)pwdLogin {
    
    UserDefaults *defaults = [UserDefaults shareDefaults];
    if (pwdLogin) {
        //账号密码登录
        self.isPWDLogin = YES;
    }
    else if (![loginAccount isEqualToString: self.accountName]){
        //动态码登录下，账号与前一个不一样
        self.isPWDLogin = NO;
    }
    [defaults setObject: [NSNumber numberWithBool: self.isPWDLogin] forKey: LoginStatusType];
}

#pragma mark - 获取何种方式登录
- (void)updateLoginStatusType {
    
    UserDefaults *defaults = [UserDefaults shareDefaults];
    if(![defaults objectForKey: LoginStatusType]) {
        self.isPWDLogin = NO;
        [defaults setObject: [NSNumber numberWithBool: self.isPWDLogin] forKey: LoginStatusType];
    }
    else {
        self.isPWDLogin = [[defaults objectForKey: LoginStatusType] boolValue];
    }
}
#pragma mark - 保存用户密码到本地
//保存用户密码到本地
- (void)saveUserPwd:(NSString *)pwd{
    UserModel *user = [[DBManager manager] getUser];
    user.pwd = pwd;
    [[DBManager manager] upDateUser: user];
   //[[UserDefaults shareDefaults]setObject:pwd forKey:UserOldPwd];
    
}
#pragma mark - 获取用户密码
//获取用户密码
- (NSString *)getUserPwd{

    UserModel *user = [[DBManager manager] getUser];
    //return [[UserDefaults shareDefaults]objectForKey:UserOldPwd];
    return user.pwd;

}




@end
