//
//  HHFLeftButton.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface HHFunctionLeftButton : UIButton

@property (nonatomic, copy) NSString *imageString;
@property (nonatomic, copy) NSString *titleString;
@property (nonatomic, copy) NSString *subTitleString;


- (instancetype)initWithNoSubTitle:(BOOL)noSubTitle;

@end
