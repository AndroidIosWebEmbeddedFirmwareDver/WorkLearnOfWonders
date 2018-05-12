//
//  WCNearbyHospitalCell.h
//  HCPatient
//
//  Created by Gu Jiajun on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SCHospitalModel.h"

@interface SCNearbyHospitalCell : UITableViewCell

@property (nonatomic, strong) SCHospitalModel *model;
@property (nonatomic, strong) NSString *highLightString;

@end
