//
//  WDCertificationViewModel.m
//  JAHealthCloudPatient
//
//  Created by luzhongchang on 16/6/15.
//  Copyright © 2016年 WondersGroup. All rights reserved.
//

#import "SCCertificationViewModel.h"

@implementation SCCertificationViewModel

+ (instancetype)shareInstance{
    static SCCertificationViewModel *instance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance =  [SCCertificationViewModel new];
    });
    return instance;
}

- (instancetype)init
{
    self = [super init];
    if (self) {
        RAC(self,canUploard) = [RACSignal combineLatest:@[RACObserve(self, userName), RACObserve(self, idCard),RACObserve(self, photo)]reduce:^id(NSString *userName, NSString *idcard,UIImage *photo){
            return @([userName length] && [RegexKit validateIdentityCard:idcard] && photo != nil);
        }];
    }
    return self;
}

- (void)verifyCertification:(void(^)(BOOL isSuccess))sucess
{
    self.uid = [UserManager manager].uid;
    UIImage *image = self.photo;
     NSData *imageData = UIImagePNGRepresentation(image);
    NSString *fileName = [NSString stringWithFormat:@"%@.png", [[NSUUID UUID] UUIDString]];
     UploadModel *fileModel = [[UploadModel alloc] init:FileType_Image fileData: imageData fileName: fileName];
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
    }
    [[ResponseAdapter sharedInstance] upload: fileModel token: [TaskManager manager].qnToken success:^(ResponseModel *response) {
        [MBProgressHUDHelper hideHud];
        self.photoUrl = response.data;
        NSString * cardId = [self.idCard stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
        NSDictionary *params = @{@"uid" : self.uid ? :@"",
                                 @"name": self.userName,
                                 @"idcard":cardId,
                                 @"photo":self.photoUrl
                                 };
        [self.adapter request:USER_VERIFICATION_SUBMIT params:params class:nil responseType:Response_Message method:Request_POST needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
            NSLog(@"+++++%@",response);
             sucess(YES);
        } failure:^(NSURLSessionDataTask *task, NSError *error) {
            sucess(NO);
            [MBProgressHUDHelper showHudWithText:error.localizedDescription]; //提交认证信息失败
        }];
        
    } failure:^(NSError *error) {
         sucess(NO);
        [MBProgressHUDHelper hideHud];
        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
    }];
}

- (void)setPhoto:(UIImage *)photo
{
    _photo = photo;
    self.photoUrl = nil;
}




- (void)getCertification:(void(^)(WDVerificationInfoModel *verificationModel))sucess
{    

//    NSString * uid = [UserManager manager].uid;
//    if (![uid length]) {
//        sucess(nil);
//        return;
//    }
//    NSDictionary * params = @{@"uid":uid};
//    [self.adapter request:USER_VERIFICATION_GET params:params class:[WDVerificationInfoModel class] responseType:Response_Object  method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
//        if ([response.data isKindOfClass:[WDVerificationInfoModel class]]) {
//
//            [UserManager manager].idcard =  ((WDVerificationInfoModel *)(response.data)).idcard;
//            sucess(response.data);
//        }
//        else
//        {
//            
//            sucess(nil);
//        }
//
//    } failure:^(NSURLSessionDataTask *task, NSError *error) {
//        sucess(nil);
//        [MBProgressHUDHelper showHudWithText:error.localizedDescription]; //提交认证信息失败
//    }];
   }

//实名认证医生邀请开关
- (void)doctorInvitationCodeSwitchSuccess: (void (^)(id response))success{
    
    [self.adapter request:@"switch/getInvitationCode" params:nil class:nil responseType:Response_Object  method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        success(response.data);
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        success(nil);
//        [MBProgressHUDHelper showHudWithText:error.localizedDescription]; //提交认证信息失败
    }];

}



@end
