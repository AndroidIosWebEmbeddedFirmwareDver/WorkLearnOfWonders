//
//  DoctorDetailInfoCell.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCDoctorSchedulingInfoCell.h"

@interface SCDoctorSchedulingInfoCell ()

@property (nonatomic, strong) UIImageView *iconImageView;
@property (nonatomic, strong) UILabel *nameLabel;
@property (nonatomic, strong) UILabel *dutyLabel;
@property (nonatomic, strong) UILabel *hospitalLabel;

@end

@implementation SCDoctorSchedulingInfoCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupView];
        [self bindViewModel];
    }
    return self;
}

- (void)setupView {
    self.backgroundColor = [UIColor clearColor];
    self.contentView.backgroundColor = [UIColor clearColor];
    
    WS(weakSelf)

    UIImageView *imageView = [UISetupView setupImageViewWithSuperView:self.contentView withImageName:@"医生背景"];
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(weakSelf.contentView);
    }];
    
    self.iconImageView = [UISetupView setupImageViewWithSuperView:self.contentView withImageName:@"默认医生男96"];

    [self.iconImageView setCornerRadius:26];
    [self.iconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(weakSelf.contentView);
        make.top.equalTo(weakSelf.contentView).offset(60);
        make.size.mas_equalTo(CGSizeMake(55, 55));
    }];
    
    self.nameLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc0Color] withFontSize:16];
    self.nameLabel.numberOfLines = 0;
    self.nameLabel.textAlignment = NSTextAlignmentCenter;
    [self.nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.top.equalTo(weakSelf.iconImageView.mas_bottom).offset(5);
    }];
    
    self.dutyLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc0Color] withFontSize:12];
    self.dutyLabel.numberOfLines = 0;
    self.dutyLabel.textAlignment = NSTextAlignmentCenter;
    [self.dutyLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(weakSelf.nameLabel);
        make.top.equalTo(weakSelf.nameLabel.mas_bottom).offset(5);
    }];
    
    self.hospitalLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc0Color] withFontSize:13];
    self.hospitalLabel.textAlignment = NSTextAlignmentCenter;
    self.hospitalLabel.numberOfLines = 0;
    [self.hospitalLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.dutyLabel.mas_bottom).offset(5);
        make.centerX.equalTo(weakSelf.contentView);
        make.left.equalTo(weakSelf.contentView).offset(15);
    }];
   
}

-(void)bindViewModel {
    WS(weakSelf)
    
    RAC(self.nameLabel, text) = RACObserve(self, model.doctorInfo.doctorName);
    RAC(self.hospitalLabel, text) = RACObserve(self, model.doctorInfo.hosName);
    RAC(self.dutyLabel, text) = RACObserve(self, model.doctorInfo.doctorTitle);
    
    [RACObserve(self, model.doctorInfo.headphoto)subscribeNext:^(NSString *icon) {
        
        NSString *imageName = @"默认医生男96";
        if (self.model.doctorInfo.gender) {
            imageName = [self.model.doctorInfo.gender integerValue] == 2 ? @"默认医生女96" : @"默认医生男96";
        }

        if (icon.length) {
            [weakSelf.iconImageView sd_setImageWithURL:[NSURL URLWithString:icon] placeholderImage:[UIImage imageNamed:imageName]];
        }else {
            [weakSelf.iconImageView setImage:[UIImage imageNamed:imageName]];
        }
    }];
}


+(CGFloat)cellHeightWithModel:(SCDoctorSchedulingModel *)model {
    CGFloat height = 60+55;
    
    CGFloat width = SCREEN_WIDTH-15-15;
    CGSize size = [Utility heightWithAttributesWithFont:[UIFont systemFontOfSize:16] withText:model.doctorInfo.doctorName withWidth:width withHeight:MAXFLOAT];
    height += size.height+1;
    height += 5;
    
    width = SCREEN_WIDTH-15-15;
    size = [Utility heightWithAttributesWithFont:[UIFont systemFontOfSize:12] withText:model.doctorInfo.doctorTitle withWidth:width withHeight:MAXFLOAT];
    height +=size.height+1;
    height += 5;

    width = SCREEN_WIDTH-15-15;
    size = [Utility heightWithAttributesWithFont:[UIFont systemFontOfSize:13] withText:model.doctorInfo.hosName withWidth:width withHeight:MAXFLOAT];
    height +=size.height+1;
    height += 25;

    
    return height;
}

@end
