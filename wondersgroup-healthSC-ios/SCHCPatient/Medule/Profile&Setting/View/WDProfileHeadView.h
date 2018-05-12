//
//  WDProfileHeadView.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void(^goInformation)();

@interface WDProfileHeadView : UIView

@property (nonatomic, strong) goInformation goBlock;

@end
