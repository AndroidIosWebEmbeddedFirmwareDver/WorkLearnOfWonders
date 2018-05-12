//
//  HotSearchCell.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseTableViewCell.h"
typedef void(^hotKeyWordBlock)(NSInteger index);
@interface HotSearchCell : BaseTableViewCell
@property (nonatomic,strong)NSArray * keyWordsArray;
@property (nonatomic,strong)hotKeyWordBlock block;
@end
