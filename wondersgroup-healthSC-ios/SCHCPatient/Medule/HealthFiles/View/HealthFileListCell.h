//
//  HealthFileListCell.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HealthFileListViewModel.h"
#import "HealthFileModel.h"



@interface HealthFileListCell : UITableViewCell

@property (nonatomic, strong) HealthFileModel *cellModel;

@property (nonatomic, assign) HealthFileListCellType type;

@end
