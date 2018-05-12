//
//  SCCertificationFixedNameCell.h
//  SCHCPatient
//
//  Created by wanda on 16/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void(^NameBlock)(NSString *name);
@interface SCCertificationFixedNameCell : UITableViewCell
@property (nonatomic,strong) UITextField     *nameTf;
@property (nonatomic,copy  ) NameBlock       nameBlock;
@end
