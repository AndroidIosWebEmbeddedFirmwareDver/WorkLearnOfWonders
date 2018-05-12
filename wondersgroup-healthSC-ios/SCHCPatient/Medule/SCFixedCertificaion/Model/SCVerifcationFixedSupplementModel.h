//
//  SCVerifcationFixedSupplementModel.h
//  SCHCPatient
//
//  Created by wanda on 16/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"
typedef NS_ENUM(NSInteger ,VerifcationFixedType){
    EmptyInfo,//空白
    NumberInfo,//账号
    NameInfo,//姓名
    IDcardInfo,//身份证
    VerifcationFixedInfo,//实名认证
    TestInfo,//描述
};
@interface SCVerifcationFixedSupplementModel : BaseModel
@property (nonatomic,copy  ) NSString       * title;
@property (nonatomic,assign) VerifcationFixedType type;

- (id)initWithtitle:(NSString *)title type:(VerifcationFixedType )type;
@end
