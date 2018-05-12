//
//  DoctorDetailJudgeHeader.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/22.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>


typedef void(^MoreButtonActionBlock)();

@interface DoctorDetailJudgeHeader : UITableViewHeaderFooterView

@property (nonatomic, copy) NSString *judgeCount;

@property (nonatomic, copy) MoreButtonActionBlock moreBlock;

@end
