//
//  HomeFunctionButtonView.h
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HomeFunctionButton : UIButton
@property (nonatomic, copy) NSString *imageString;
@property (nonatomic, copy) NSString *titleName;
@property (nonatomic, assign) BOOL isNeedRedPoint;

@property (nonatomic, assign) BOOL invalid;

@end
