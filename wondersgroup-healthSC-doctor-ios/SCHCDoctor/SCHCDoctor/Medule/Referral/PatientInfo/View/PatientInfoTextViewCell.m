//
//  PatientInfoTextViewCell.m
//  SCHCDoctor
//
//  Created by Po on 2017/10/23.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientInfoTextViewCell.h"

@implementation PatientInfoTextViewCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        
        [self configTopBlankLine];
        [self configImportantLabel];
        [self configTitleLabel];
        [self configTextView];
        [self configBottomLine];
        [self buildConstraints];
    }
    return self;
}

- (void)buildConstraints {
    [self.topBlankLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.contentView);
        make.height.mas_equalTo(0);
    }];
    
    [self.bottomLineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.mas_equalTo(15);
        make.bottom.right.equalTo(self.contentView);
        make.height.mas_equalTo(1);
    }];
    
    [self.importantLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView).offset(15);
        make.top.bottom.equalTo(self.titleLabel);
        make.width.mas_equalTo(0);
    }];
    
    [self.titleLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.topBlankLabel.mas_bottom);
        make.left.equalTo(self.importantLabel.mas_right).offset(5);
        make.height.mas_equalTo(45);
        make.width.mas_equalTo(100);
    }];
    
    [self.textView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.topBlankLabel.mas_bottom);
        make.bottom.equalTo(self.contentView);
        make.left.equalTo(self.titleLabel.mas_right).offset(5);
        make.right.equalTo(self.contentView).offset(-27);
        make.height.mas_greaterThanOrEqualTo(45);
    }];

}

@end
