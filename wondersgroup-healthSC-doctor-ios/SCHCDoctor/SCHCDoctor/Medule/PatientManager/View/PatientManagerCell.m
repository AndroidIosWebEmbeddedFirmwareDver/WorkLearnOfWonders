//
//  PatientManagerCell.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientManagerCell.h"

@interface PatientManagerCell ()

@property (nonatomic, strong) UIImageView *avatarView;
@property (nonatomic, strong) UILabel *nameLbl;
@property (nonatomic, strong) UILabel *genderLbl;
@property (nonatomic, strong) UILabel *addressLbl;
@property (nonatomic, strong) UILabel *ageLbl;

@property (nonatomic, strong) UILabel *keyLbl;  //重点
@property (nonatomic, strong) UILabel *poorLbl; //贫困

@end

@implementation PatientManagerCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier  {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        
        [self prepareUI];
        [self bindRac];
    }
    return self;
}

- (void)prepareUI {
    WS(weakSelf)
    
    _avatarView = [UISetupView setupImageViewWithSuperView:self.contentView withImageName:nil];
    [_avatarView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.contentView.mas_top).offset(19);
        make.left.equalTo(weakSelf.contentView.mas_left).offset(15);
        make.size.mas_equalTo(CGSizeMake(33, 33));
    }];
    
    _nameLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:nil withTextColor:[UIColor tc1Color] withFontSize:16];
    [_nameLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.contentView.mas_top).offset(16);
        make.left.equalTo(weakSelf.avatarView.mas_right).offset(10);
        make.height.mas_equalTo(16);
    }];
    
    _genderLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:nil withTextColor:[UIColor tc3Color] withFontSize:16];
    [_genderLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.contentView.mas_top).offset(16);
        make.left.equalTo(weakSelf.nameLbl.mas_right).offset(15);
        make.height.mas_equalTo(16);
    }];
    
    _ageLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:nil withTextColor:[UIColor tc3Color] withFontSize:16];
    [_ageLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.contentView.mas_top).offset(16);
        make.left.equalTo(weakSelf.genderLbl.mas_right).offset(15);
        make.height.mas_equalTo(16);
    }];
    
    _addressLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:nil withTextColor:[UIColor tc3Color] withFontSize:13];
    [_addressLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(weakSelf.contentView.mas_bottom).offset(-15);
        make.left.equalTo(weakSelf.nameLbl.mas_left).offset(0);
        make.height.mas_equalTo(13);
    }];
    
    _keyLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:@"重点" withTextColor:[UIColor stc2Color] withFontSize:11];
    _keyLbl.textAlignment = NSTextAlignmentCenter;
    [_keyLbl setCornerRadius:8.5];
    [_keyLbl setborderWithColor:[UIColor stc2Color] withWidth:0.5];
    [_keyLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(weakSelf.nameLbl.mas_centerY);
        make.left.equalTo(weakSelf.ageLbl.mas_right).offset(20);
        make.size.mas_equalTo(CGSizeMake(31, 17));
    }];
    _poorLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:@"贫困" withTextColor:[UIColor stc2Color] withFontSize:11];
    _poorLbl.textAlignment = NSTextAlignmentCenter;
    [_poorLbl setCornerRadius:8.5];
    [_poorLbl setborderWithColor:[UIColor stc2Color] withWidth:0.5];
    [_poorLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(weakSelf.nameLbl.mas_centerY);
        make.left.equalTo(weakSelf.keyLbl.mas_right).offset(20);
        make.size.mas_equalTo(CGSizeMake(31, 17));
    }];
    
    UIView *bottomLine = [UISetupView setupBottomLineViewWithSuperView:self.contentView withSpace:0];
}

- (void)bindRac {
    @weakify(self)
    [RACObserve(self, model) subscribeNext:^(PatientListModel *x) {
        @strongify(self)
        self.avatarView.image = [UIImage imageNamed:@"电话"];
        self.nameLbl.text = x.name;
        self.genderLbl.text = @"男";
        self.ageLbl.text = @"100";
        self.addressLbl.text = @"rdtfyguhijokctfvyghuij";
        
        BOOL isKey = YES;
        BOOL isPoor = YES;
        [self relayoutTagIsKey:isKey isPoor:isPoor];
    }];
}

//重新调整 重点和贫困 标签
- (void)relayoutTagIsKey:(BOOL)isKey isPoor:(BOOL)isPoor {
    WS(weakSelf)
    if (isKey && isPoor) {
        //两个都显示
        self.keyLbl.hidden = NO;
        self.poorLbl.hidden = NO;
        [_keyLbl mas_updateConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(weakSelf.ageLbl.mas_right).offset(20);
        }];
        [_poorLbl mas_updateConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(weakSelf.keyLbl.mas_right).offset(8);
        }];
        
    } else if (isKey && !isPoor) {
        //只显示重点
        self.keyLbl.hidden = NO;
        self.poorLbl.hidden = YES;
        [_keyLbl mas_updateConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(weakSelf.ageLbl.mas_right).offset(20);
        }];
    } else if (!isKey && isPoor) {
        //只显示贫困
        self.keyLbl.hidden = YES;
        self.poorLbl.hidden = NO;
        [_poorLbl mas_updateConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(weakSelf.ageLbl.mas_right).offset(20);
        }];
    } else {
        //两个都不显示
        self.keyLbl.hidden = YES;
        self.poorLbl.hidden = YES;
    }
}

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
