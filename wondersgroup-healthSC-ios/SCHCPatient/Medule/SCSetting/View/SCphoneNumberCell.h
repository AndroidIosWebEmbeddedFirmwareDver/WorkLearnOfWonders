//
//  SCphoneNumberCell.h
//  SCHCPatient
//
//  Created by wanda on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void(^PhoneNumberBlock)(NSString *);
@interface SCphoneNumberCell : UITableViewCell
@property (nonatomic,strong) UITextField      *oldPassWordTextField;
@property (nonatomic,copy  ) PhoneNumberBlock phoneNumberBlock;
@end
