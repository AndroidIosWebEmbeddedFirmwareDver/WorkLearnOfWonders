//
//  PatintSuperCell.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseTableViewCell.h"
typedef void(^PatientEndEditing)(BOOL isEdit, NSString * string, UITableViewCell * cell);
@interface PatintSuperCell : BaseTableViewCell
@property (copy, nonatomic) PatientEndEditing editBlock;        //编辑完成
- (void)setEditBlock:(void(^)(BOOL isEdit, NSString * string, UITableViewCell * cell))editBlock;
@end
