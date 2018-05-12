//
//  AreaCell.h
//  HCPatient
//
//  Created by luzhongchang on 16/8/22.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "LocationModel.h"
@interface AreaCell : UITableViewCell
@property(nonatomic ,strong) UILabel * titlehead;
@property(nonatomic ,strong) LocationModel * model;
-(id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier;
@end



@interface AreaGpsCell : UITableViewCell
@property(nonatomic ,strong) UILabel * titlehead;
@property(nonatomic ,strong) LocationModel * model;
-(id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier;
@end
