//
//  BaseTableViewCell.h
//  VaccinePatient
//
//  Created by ZJW on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BaseTableViewCell : UITableViewCell
{
    UIView      *topLine;
    UIView      *bottomLine;
}

@property (nonatomic, assign) BOOL    lineTopHidden;
@property (nonatomic, assign) BOOL    lineBottomHidden;

+(CGFloat)cellHeight;

@end
