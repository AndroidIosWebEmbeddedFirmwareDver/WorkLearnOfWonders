//
//  PatientDetailCell.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatintSuperCell.h"

@interface PatientDetailCell : PatintSuperCell
@property (strong, nonatomic) UIImageView   * importantImageView;       //重点图片
@property (strong, nonatomic) UILabel       * titleLabel;               //标题
@property (strong, nonatomic) UITextView    * textView;                 //文本区
@property (strong, nonatomic) UILabel       * countLabel;               //计数
@end
