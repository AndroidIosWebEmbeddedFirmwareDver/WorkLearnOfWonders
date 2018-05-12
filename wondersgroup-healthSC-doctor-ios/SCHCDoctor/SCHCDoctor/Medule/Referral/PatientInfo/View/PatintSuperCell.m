//
//  PatintSuperCell.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatintSuperCell.h"

@implementation PatintSuperCell
- (void)setEditBlock:(void(^)(BOOL isEdit, NSString * string, UITableViewCell * cell))editBlock {
    _editBlock = editBlock;
}

@end
