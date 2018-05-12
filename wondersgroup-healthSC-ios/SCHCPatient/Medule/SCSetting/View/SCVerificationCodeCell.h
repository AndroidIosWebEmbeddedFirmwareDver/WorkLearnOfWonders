//
//  SCVerificationCodeCell.h
//  SCHCPatient
//
//  Created by wanda on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void(^getCodeBlock)(NSString *);
typedef void(^inCodeBlock)(NSString *);
@interface SCVerificationCodeCell : UITableViewCell
@property (nonatomic, copy  ) getCodeBlock getCodeAction;
@property (nonatomic, copy  ) inCodeBlock  inCodeAction;
@property (nonatomic,strong ) UIButton     *getVeryCodeButton;
@property (nonatomic, strong) UITextField  *oldPassWordTextField;
@end

