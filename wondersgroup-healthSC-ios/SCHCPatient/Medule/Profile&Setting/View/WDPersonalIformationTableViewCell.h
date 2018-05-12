//
//  WDPersonalIformationTableViewCell.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger, WDInformationCellType) {
    WDInformationCellDefault,
    WDInformationCellHead,
    WDInformationCellPhone,
    WDInformationCellStyle
};

@interface WDPersonalIformationTableViewCell : UITableViewCell

@property (nonatomic, assign) WDInformationCellType cellType;
@property (nonatomic, strong) NSString *title;
@property (nonatomic, strong) NSString *content;
@property (nonatomic, strong) NSString *head;
@property (nonatomic, strong) NSString *phone;
@property (nonatomic, assign) int style;
@property (nonatomic, assign) BOOL isLast;

@end
