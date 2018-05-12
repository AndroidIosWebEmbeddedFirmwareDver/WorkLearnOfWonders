//
//  ProfileTableViewCell.h
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseTableViewCell.h"
#import "ProfileViewModel.h"

@interface ProfileTableViewCell : UITableViewCell

@property (nonatomic, strong) NSString *imageName;
@property (nonatomic, strong) NSString *title;
@property (nonatomic, assign) BOOL isLast;

@end
