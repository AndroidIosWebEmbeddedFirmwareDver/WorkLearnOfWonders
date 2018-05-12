//
//  SCSurePassWordCell.h
//  SCHCPatient
//
//  Created by wanda on 16/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void(^suretextFieldBlock)(NSString *);
@interface SCSurePassWordCell : UITableViewCell
@property (nonatomic,strong) UITextField        *surePassWordTextField;
@property (nonatomic, copy ) suretextFieldBlock suretextfieldblocK;
@property (nonatomic,strong) UIButton           *surepwdeyeButton;
@end
