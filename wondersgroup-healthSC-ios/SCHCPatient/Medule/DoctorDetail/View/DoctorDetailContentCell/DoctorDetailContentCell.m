//
//  DoctorDetailContentCell.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DoctorDetailContentCell.h"


@interface DoctorDetailContentCell ()

@property (nonatomic, strong) UILabel *contentLabel;

@end

@implementation DoctorDetailContentCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    
    if (self == [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self prepareUI];
        [self bindRac];
    }
    return self;
}

- (void)prepareUI {
    
    self.contentView.backgroundColor = [UIColor bc2Color];
    
    UIView *backView = [UIView new];
    backView.backgroundColor = [UIColor whiteColor];
    [self.contentView addSubview:backView];
    
    self.contentLabel = [UILabel new];
    self.contentLabel.numberOfLines = 0;
    self.contentLabel.font = [UIFont systemFontOfSize:14.];
    self.contentLabel.textColor = [UIColor tc2Color];
    [backView addSubview:self.contentLabel];
    
    WS(weakSelf)
    
    [backView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.contentView).offset(10);
        make.left.right.bottom.equalTo(weakSelf.contentView);
    }];
    
    [self.contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.equalTo(backView).offset(15);
        make.right.equalTo(backView).offset(-15);
    }];
}

- (void)bindRac {
    
    WS(weakSelf)
    [RACObserve(self, content) subscribeNext:^(NSString *x) {
        
        if (x.length) {
            NSMutableAttributedString *attributeString = [[NSMutableAttributedString alloc] initWithString:x];
            NSMutableParagraphStyle *style = [[NSMutableParagraphStyle alloc] init];
            [style setLineSpacing:5.];
            [attributeString addAttribute:NSParagraphStyleAttributeName value:style range:NSMakeRange(0, x.length)];
            weakSelf.contentLabel.attributedText = attributeString;
        }
    }];
}


@end
