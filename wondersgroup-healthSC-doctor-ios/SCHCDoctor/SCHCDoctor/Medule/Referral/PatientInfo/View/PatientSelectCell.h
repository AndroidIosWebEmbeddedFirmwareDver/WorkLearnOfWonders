//
//  PatientSelectCell.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatintSuperCell.h"

@interface PatientSelectCell : PatintSuperCell
@property (strong, nonatomic) UIImageView * importantImageView; //重点图片
@property (strong, nonatomic) UILabel * titleLabel;             //标题
@property (assign, nonatomic) NSInteger currentCount;

- (void)setTitles:(NSArray *)titles;

- (void)setSelectedBlock:(void(^)(NSInteger index, NSString * title))block;
@end
