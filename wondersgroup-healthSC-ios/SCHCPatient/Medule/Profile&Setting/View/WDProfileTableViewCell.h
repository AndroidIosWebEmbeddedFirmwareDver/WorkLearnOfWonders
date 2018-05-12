//
//  WDProfileTableViewCell.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface WDProfileTableViewCell : UITableViewCell

@property (nonatomic, strong) NSString *imageName;
@property (nonatomic, strong) NSString *title;
@property (nonatomic, assign) BOOL isLast;

@end
