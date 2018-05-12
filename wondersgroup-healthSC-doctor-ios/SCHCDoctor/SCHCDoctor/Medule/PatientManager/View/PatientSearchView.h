//
//  PatientSearchView.h
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void(^SearchActionBlock)(NSString *keyword);

@interface PatientSearchView : UIView <UITextFieldDelegate>

@property (nonatomic, copy) SearchActionBlock searchActionBlock;
@property (nonatomic, strong) UITextField *textField;

@end
