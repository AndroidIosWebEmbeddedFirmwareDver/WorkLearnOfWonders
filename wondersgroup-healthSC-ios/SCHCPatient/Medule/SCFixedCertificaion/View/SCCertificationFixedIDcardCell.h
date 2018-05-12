//
//  SCCertificationFixedIDcardCell.h
//  SCHCPatient
//
//  Created by wanda on 16/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void(^IDcardBlock)(NSString *idcard);
@interface SCCertificationFixedIDcardCell : UITableViewCell
@property (nonatomic,strong) UITextField     *idcardTf;
@property (nonatomic,copy  ) IDcardBlock     idcardBlock;
@end
