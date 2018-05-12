//
//  WDLinkPayViewController.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "WDlnhospitalPayViewModel.h"

@interface WDLinkPayViewController : BaseViewController

@property (nonatomic, strong) WDlnhospitalPayViewModel *viewModel;

@property (nonatomic, assign) BOOL isRegistration;

@end
