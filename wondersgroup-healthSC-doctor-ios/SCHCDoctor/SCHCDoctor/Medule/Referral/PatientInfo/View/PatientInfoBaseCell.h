//
//  PatientInfoBaseCell.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatintSuperCell.h"
#import "InfoBaseTextView.h"
typedef NS_ENUM(NSUInteger, PatientInfoBaseCellType) {
    PatientInfoBaseCellTypeNormal = 0,
    PatientInfoBaseCellTypeTextFiled,
    PatientInfoBaseCellTypeTextView
};

@interface PatientInfoBaseCell : PatintSuperCell

@property (strong, nonatomic) UILabel * topBlankLabel;
@property (strong, nonatomic) UILabel * importantLabel;
@property (strong, nonatomic) UILabel * titleLabel;
@property (strong, nonatomic) UILabel * detailLabel;
@property (strong, nonatomic) UITextField * textField;
@property (strong, nonatomic) InfoBaseTextView * textView;
@property (strong, nonatomic) UIImageView * arrowImageView;
@property (strong, nonatomic) UILabel * bottomLineLabel;

- (UILabel *)configTopBlankLine;
- (UILabel *)configTitleLabel;
- (UILabel *)configImportantLabel;
- (UILabel *)configDetailLabel;
- (UIImageView *)configArrowImageView;
- (UITextField *)configTextField;
- (InfoBaseTextView *)configTextView;
- (UILabel *)configBottomLine;

- (void)setImportant:(BOOL)isImportant;
- (void)showTopBlank:(BOOL)isShow;
- (void)showBottomLine:(BOOL)isShow;


- (void)reSetTextView;


@end
