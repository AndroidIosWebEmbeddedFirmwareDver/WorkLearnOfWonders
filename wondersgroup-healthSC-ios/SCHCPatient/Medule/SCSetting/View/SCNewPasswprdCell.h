//
//  SCNewPasswprdCell.h
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void(^textFieldBlock)(NSString *);
@interface SCNewPasswprdCell : UITableViewCell
@property (nonatomic, copy ) textFieldBlock textfieldblocK;
@property (nonatomic,strong) UIButton       *newpwdeyeButton;
@property (nonatomic,strong) UITextField    *myNewTf;
@end
