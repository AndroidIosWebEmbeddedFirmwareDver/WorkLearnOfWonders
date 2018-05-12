//
//  SCAppointmentDetailInfoCell.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDetailInfoCell.h"

@interface SCAppointmentDetailInfoCell ()

@property (nonatomic, strong) UIImageView *iconImageView;
@property (nonatomic, strong) UILabel *nameLabel;
@property (nonatomic, strong) UILabel *dutyLabel;
@property (nonatomic, strong) UILabel *hospitalLabel;
@property (nonatomic, strong) UILabel *departmentLabel;
@property (nonatomic, strong) UILabel *timeLabel;
@property (nonatomic, strong) UILabel *typeLabel;
//@property (nonatomic, strong) UILabel *moneyLabel;

@end

@implementation SCAppointmentDetailInfoCell

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
        make.centerX.equalTo(weakSelf.contentView);
        make.top.equalTo(weakSelf.contentView).offset(15);
        make.size.mas_equalTo(CGSizeMake(48, 48));
    }];
    
    self.nameLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc2Color] withFontSize:16];
    self.nameLabel.textAlignment = NSTextAlignmentCenter;
    [self.nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(weakSelf.contentView);
        make.top.equalTo(weakSelf.iconImageView.mas_bottom).offset(10);
        make.left.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(15);
    }];
    
    self.dutyLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc3Color] withFontSize:12];
    self.dutyLabel.textAlignment = NSTextAlignmentCenter;
    [self.dutyLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(weakSelf.contentView);
        make.top.equalTo(weakSelf.nameLabel.mas_bottom).offset(10);
        make.left.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(12);
    }];

    UILabel *hospitalTitleLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"就诊医院" withTextColor:[UIColor tc2Color] withFontSize:14];
    [hospitalTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.top.equalTo(weakSelf.dutyLabel.mas_bottom).offset(15);
        make.height.mas_equalTo(14);
        make.width.mas_equalTo(60);
    }];
    
    self.hospitalLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc1Color] withFontSize:14];
    [self.hospitalLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(hospitalTitleLabel);
        make.left.equalTo(hospitalTitleLabel.mas_right).offset(35);
        make.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(14);
    }];

    UILabel *departmentTitleLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"就诊科室" withTextColor:[UIColor tc2Color] withFontSize:14];
    [departmentTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.top.equalTo(hospitalTitleLabel.mas_bottom).offset(10);
        make.width.mas_equalTo(60);
        make.height.mas_equalTo(14);
    }];
    
    self.departmentLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc1Color] withFontSize:14];
    [self.departmentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(departmentTitleLabel);
        make.left.equalTo(departmentTitleLabel.mas_right).offset(35);
        make.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(14);
    }];

    UILabel *timeTitleLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"门诊时间" withTextColor:[UIColor tc2Color] withFontSize:14];
    [timeTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.top.equalTo(departmentTitleLabel.mas_bottom).offset(10);
        make.height.mas_equalTo(14);
        make.width.mas_equalTo(60);
    }];
    
    self.timeLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor stc1Color] withFontSize:14];
    [self.timeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(timeTitleLabel);
        make.left.equalTo(timeTitleLabel.mas_right).offset(35);
        make.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(14);
    }];
    
    UILabel *typeTitleLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"门诊类型" withTextColor:[UIColor tc2Color] withFontSize:14];
    [typeTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.top.equalTo(timeTitleLabel.mas_bottom).offset(10);
        make.height.mas_equalTo(14);
        make.width.mas_equalTo(60);
    }];
    
    self.typeLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc1Color] withFontSize:14];
    [self.typeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(typeTitleLabel);
        make.left.equalTo(typeTitleLabel.mas_right).offset(35);
        make.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(14);
    }];

//    UILabel *moneyTitleLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"挂号费用" withTextColor:[UIColor tc2Color] withFontSize:14];
//    [moneyTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(weakSelf.contentView).offset(15);
//        make.top.equalTo(typeTitleLabel.mas_bottom).offset(10);
//        make.height.mas_equalTo(14);
//        make.width.mas_equalTo(60);
//    }];
//    
//    self.moneyLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc1Color] withFontSize:14];
//    [self.moneyLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.top.equalTo(moneyTitleLabel);
//        make.left.equalTo(moneyTitleLabel.mas_right).offset(35);
//        make.right.equalTo(weakSelf.contentView);
//        make.height.mas_equalTo(14);
//    }];

}

-(void)bindViewModel {
    WS(weakSelf)
    
    RAC(self.nameLabel, text) = RACObserve(self, model.doctorName);
    RAC(self.hospitalLabel, text) = RACObserve(self, model.hosName);
    RAC(self.dutyLabel, text) = RACObserve(self, model.doctorTitle);
    RAC(self.departmentLabel, text) = RACObserve(self, model.deptName);
    RAC(self.typeLabel, text) = RACObserve(self, model.schedule.visitLevel);
    
    [RACObserve(self, model.schedule.scheduleDate)subscribeNext:^(NSString *scheduleDate) {
        if (scheduleDate.length) {
            self.timeLabel.text = [NSString stringWithFormat:@"%@ %@ %@",
                                   weakSelf.model.schedule.scheduleDate.length?weakSelf.model.schedule.scheduleDate:@"",
                                   weakSelf.model.schedule.weekDay.length?weakSelf.model.schedule.weekDay:@"",
                                   weakSelf.model.schedule.timeRangeStr.length?weakSelf.model.schedule.timeRangeStr:@""];
        }
    }];

//    [RACObserve(self, model.schedule.visitCost)subscribeNext:^(NSString *money) {
//        if (money) {
//            weakSelf.moneyLabel.text = [NSString stringWithFormat:@"%@元",money];
//        }
//    }];

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
    return 250-14-14;
}



@end
