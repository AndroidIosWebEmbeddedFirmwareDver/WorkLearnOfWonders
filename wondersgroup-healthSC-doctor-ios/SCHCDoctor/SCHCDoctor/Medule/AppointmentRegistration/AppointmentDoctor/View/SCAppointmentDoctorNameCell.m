//
//  SCAppointmentDoctorCell.m
//  VaccinePatient
//
//  Created by ZJW on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDoctorNameCell.h"

@interface SCAppointmentDoctorNameCell ()

@property (nonatomic, strong) UIImageView *iconImageView;
@property (nonatomic, strong) UILabel *nameLabel;
@property (nonatomic, strong) UILabel *dutyLabel;
@property (nonatomic, strong) UILabel *adeptLabel;
@property (nonatomic, strong) UILabel *numberLabel;
@property (nonatomic, strong) UILabel *appointmentNumberLabel;

@end

@implementation SCAppointmentDoctorNameCell

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
    
    self.iconImageView = [UISetupView setupImageViewWithSuperView:self.contentView withImageName:@"默认医生男96"];
    [self.iconImageView setCornerRadius:24];
    [self.iconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.top.equalTo(weakSelf.contentView).offset(15);
        make.size.mas_equalTo(CGSizeMake(48, 48));
    }];
    
    self.nameLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc1Color] withFontSize:16];
    [self.nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.iconImageView.mas_right).offset(15);
        make.top.equalTo(weakSelf.contentView).offset(15);
        make.width.mas_lessThanOrEqualTo(95);
    }];

    self.numberLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc0Color] withFontSize:12];
    [self.numberLabel setCornerRadius:7];
    self.numberLabel.textAlignment = NSTextAlignmentCenter;
    [self.numberLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.centerY.equalTo(weakSelf.nameLabel);
        make.width.mas_equalTo(75/2.0);
    }];    
    
    self.dutyLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc2Color] withFontSize:12];
    [self.dutyLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.nameLabel.mas_right).offset(15);
        make.centerY.equalTo(weakSelf.nameLabel);
        make.right.lessThanOrEqualTo(weakSelf.numberLabel.mas_left).offset(-15);
    }];
    
    self.appointmentNumberLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor stc1Color] withFontSize:12];
    [self.appointmentNumberLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.nameLabel);
        make.top.equalTo(weakSelf.nameLabel.mas_bottom).offset(10);
        make.right.equalTo(weakSelf.contentView).offset(-15);
    }];
    
    self.adeptLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"擅长: " withTextColor:[UIColor tc3Color] withFontSize:12];
    [self.adeptLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.appointmentNumberLabel);
        make.top.equalTo(weakSelf.appointmentNumberLabel.mas_bottom).offset(10);
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.height.mas_equalTo(14);
    }];
    
    [bottomLine mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(weakSelf.contentView);
        make.left.equalTo(weakSelf.nameLabel);
        make.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(0.5);
    }];
    
}

-(void)bindViewModel {
    WS(weakSelf)
    
    RAC(self.dutyLabel, text) = RACObserve(self, model.doctorTitle);
    
    [RACObserve(self, model.doctorName) subscribeNext:^(NSString *text) {
        if (text.length <= 5) {
            weakSelf.nameLabel.text = text;
        } else {
            NSString *subStr = [text substringToIndex:5];
            subStr = [subStr stringByAppendingString:@"..."];
            weakSelf.nameLabel.text = subStr;
        }
    }];
    
    
    [RACObserve(self, model.expertin) subscribeNext:^(NSString *x) {
        if (x.length) {
            self.adeptLabel.text = [NSString stringWithFormat:@"擅长: %@",x];
        }
        else {
            self.adeptLabel.text = @"擅长: ";
        }
    }];
    
    [RACObserve(self, model.orderCount)subscribeNext:^(NSNumber *number) {
        self.appointmentNumberLabel.text = [NSString stringWithFormat:@"接诊量   %@",number];
    }];

    [RACObserve(self, model.isFull)subscribeNext:^(NSNumber *number) {
        if ([number boolValue] == NO) {
            self.numberLabel.text = @"约满";
            self.numberLabel.backgroundColor = [UIColor bc4Color];
            self.numberLabel.textColor = [UIColor tc3Color];
        }else {
            self.numberLabel.text = @"预约";
            self.numberLabel.backgroundColor = [UIColor sbc3Color];
            self.numberLabel.textColor = [UIColor tc0Color];
        }
    }];
    
    [RACObserve(self, model.headphoto)subscribeNext:^(NSString *icon) {
        NSString *imageName = @"默认医生男96";
        if (self.model.gender) {
            imageName = [self.model.gender integerValue] == 2 ? @"默认医生女96" : @"默认医生男96";
        }
        
        if (icon.length) {
            [weakSelf.iconImageView sd_setImageWithURL:[NSURL URLWithString:icon] placeholderImage:[UIImage imageNamed:imageName]];
        }else {
            [weakSelf.iconImageView setImage:[UIImage imageNamed:imageName]];
        }
    }];
    
}


+(CGFloat)cellHeight {
    return 95;
}

@end
