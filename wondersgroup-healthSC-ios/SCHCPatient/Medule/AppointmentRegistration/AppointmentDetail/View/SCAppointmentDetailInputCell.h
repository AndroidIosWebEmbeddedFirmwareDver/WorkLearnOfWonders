//
//  SCAppointmentDetailInputCell.h
//  SCHCPatient
//
//  Created by ZJW on 2016/11/21.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseTableViewCell.h"
#import "SCAppointmentDetailViewModel.h"

@interface SCAppointmentDetailInputCell : BaseTableViewCell

@property (nonatomic, strong) NSString *title;
@property (nonatomic, strong) SCAppointmentDetailViewModel *viewModel;

@end
