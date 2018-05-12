//
//  PatientDetailView.h
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "PatientListModel.h"

typedef void(^CallActionBlock)(NSString *mobile);

@interface PatientDetailView : UIView

@property (nonatomic, strong) PatientListModel *model;
@property (nonatomic, copy) CallActionBlock callActionBlock;

@end
