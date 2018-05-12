//
//  SCOldpasswordCell.h
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void(^PasswordBlock)(NSString *);
@interface SCOldpasswordCell : UITableViewCell
@property (nonatomic, copy ) PasswordBlock passwordBlock;
@property (nonatomic,strong) UITextField   *oldPassWordTextField;
@property (nonatomic,strong) UIButton      * oldpwdeyeButton;

@end
