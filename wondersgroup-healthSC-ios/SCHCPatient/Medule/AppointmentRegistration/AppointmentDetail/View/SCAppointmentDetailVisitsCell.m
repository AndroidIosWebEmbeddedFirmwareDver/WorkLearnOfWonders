//
//  SCAppointmentDetailVisitsCell.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDetailVisitsCell.h"

@interface SCAppointmentDetailVisitsCell ()

@property (nonatomic, strong) UILabel *titleLabel1;
@property (nonatomic, strong) UILabel *contentLabel1;

@end

@implementation SCAppointmentDetailVisitsCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupView];
        [self bindViewModel];
    }
    return self;
}

- (void)setupView {
    self.backgroundColor = [UIColor bc1Color];
    self.contentView.backgroundColor = [UIColor bc1Color];
    
    WS(weakSelf)
    self.titleLabel1 = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc2Color] withFontSize:16];
    [self.titleLabel1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(100);
    }];
    
    self.contentLabel1 = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc1Color] withFontSize:16];
    self.contentLabel1.textAlignment = NSTextAlignmentRight;
    [self.contentLabel1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.centerY.equalTo(weakSelf.contentView);
        make.left.equalTo(weakSelf.titleLabel1.mas_right).offset(5);
    }];

    [bottomLine mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(weakSelf.contentView);
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(0.5);
    }];

    [topLine mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.contentView);
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(0.5);
    }];

}

-(void)bindViewModel {
    
    RAC(self.titleLabel1, text) = RACObserve(self, title);
    RAC(self.contentLabel1, text) = RACObserve(self, content);
}


+(CGFloat)cellHeight {
    return 44;
}


@end
