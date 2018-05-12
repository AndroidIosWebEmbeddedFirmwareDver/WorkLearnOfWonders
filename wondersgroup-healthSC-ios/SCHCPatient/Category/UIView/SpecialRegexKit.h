//
//  SpecialRegexKit.h
//  GZHealthCloudPatient
//
//  Created by 杜凯 on 16/8/4.
//  Copyright © 2016年 WondersGroup. All rights reserved.
//

@interface SpecialRegexKit : RegexKit


//验证密码
+ (BOOL) validateSpecialPassword:(NSString *)passWord;
#pragma mark - 验证真实姓名
+ (BOOL) validateRealname: (NSString *)realname;
@end
