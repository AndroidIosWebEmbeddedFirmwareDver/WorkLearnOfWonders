//
//  SCOrderDetailCell.m
//  HCPatient
//
//  Created by wanda on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "SCOrderDetailCell.h"

@implementation SCOrderDetailCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupSubview];
         [self bindRac];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    return self;
}

- (void)setupSubview
{
    self.titleLabel = [[UILabel alloc] init];
    [self.contentView addSubview:self.titleLabel];
    self.titleLabel.font = [UIFont systemFontOfSize:16.0];
    self.titleLabel.textColor = [UIColor tc2Color];
    self.titleLabel.backgroundColor = [UIColor clearColor];
    [self.titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView).offset(15);
        make.centerY.equalTo(self.contentView);
    }];
    
    UIView *bottomLineView = [UIView new];
    bottomLineView.backgroundColor = [UIColor bc3Color];
    [self.contentView addSubview:bottomLineView];
    [bottomLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView).offset(15);
        make.right.equalTo(self.contentView);
        make.height.mas_equalTo(0.5);
        make.bottom.equalTo(self.contentView);
    }];
    
    self.arrowsView = [[UIImageView alloc] init];
    [self.contentView addSubview:self.arrowsView];
    self.arrowsView.image = [UIImage imageNamed:@"link_right"];
    [self.arrowsView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self.contentView.mas_right).offset(-15);
        make.centerY.equalTo(self.contentView);
        make.size.mas_equalTo(self.arrowsView.image.size);
    }];
}

- (void)bindRac {
    
//    [RACObserve(self, cellModel.gender) subscribeNext:^(id x) {
//        self.sexLabel.text = [x integerValue] == 1 ? @"男" : @"女";
//    }];
}
@end
