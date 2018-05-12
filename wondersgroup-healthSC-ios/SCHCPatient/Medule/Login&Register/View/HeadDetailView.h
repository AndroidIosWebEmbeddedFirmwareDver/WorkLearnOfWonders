//
//  HeadDetailView.h
//  VaccinePatient
//
//  Created by Jam on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>


typedef void(^HeadDetailViewChangeBlock)(NSInteger changeIndex);
@interface HeadDetailView : UIView


@property (nonatomic, copy) NSString *imageName;
@property (nonatomic, copy) NSString *tipString;

@property (nonatomic,copy) NSString * fastLoginName;


@property (nonatomic,copy) NSString * pwdLoginName;

@property (nonatomic,copy)HeadDetailViewChangeBlock  changeBlock;

@property (nonatomic,copy)dispatch_block_t delBlock;

- (instancetype)initWithLogo:(NSString *)image withSlogan:(NSString *)slogan;

- (instancetype)initWithLogo:(NSString *)image withSlogan:(NSString *)slogan
           withfastLoginName:(NSString * )fastLoginString
            withPwdLoginName:(NSString *)pwdLoginString;

- (instancetype)initWithImage:(NSString *)image withTip:(NSString *)tip;


- (instancetype)initWithBabyImage:(NSString *)image withTip:(NSString *)tip;

@end
