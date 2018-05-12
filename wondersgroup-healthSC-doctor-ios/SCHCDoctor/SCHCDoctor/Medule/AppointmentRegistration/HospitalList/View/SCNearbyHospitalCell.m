//
//  WCNearbyHospitalCell.m
//  HCPatient
//
//  Created by Gu Jiajun on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "SCNearbyHospitalCell.h"

@interface SCNearbyHospitalCell ()

@property (nonatomic, strong) UIImageView *photoView;
@property (nonatomic, strong) UILabel *nameLbl;
@property (nonatomic, strong) UILabel *addressLbl;
@property (nonatomic, strong) UILabel *lvlLbl;

@end

@implementation SCNearbyHospitalCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        [self setUpViews];
        [self bindModel];
    }
    return self;
}


- (void)setUpViews
{
    WS(weakSelf)
    _photoView = [UISetupView setupImageViewWithSuperView:self.contentView withImageName:nil];
    [_photoView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView.mas_left).offset(15);
        make.top.equalTo(weakSelf.contentView.mas_top).offset(15);
        make.width.mas_equalTo(110);
        make.bottom.equalTo(weakSelf.contentView.mas_bottom).offset(-15);
    }];
    
    _nameLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc1Color] withFontSize:16];
    [_nameLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.contentView.mas_top).offset(20);
        make.left.equalTo(weakSelf.photoView.mas_right).offset(15);
        make.right.equalTo(weakSelf.contentView.mas_right).offset(-15);
        make.height.mas_equalTo(16);
    }];
    
    _addressLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc2Color] withFontSize:12];
    [_addressLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.nameLbl.mas_bottom).offset(10);
        make.left.equalTo(weakSelf.nameLbl.mas_left);
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.height.mas_equalTo(12);
    }];
    
    _lvlLbl = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor stc1Color] withFontSize:10];
    _lvlLbl.textAlignment = NSTextAlignmentCenter;
    [_lvlLbl setborderWithColor:[UIColor stc1Color] withWidth:0.5];
    [_lvlLbl setCornerRadius:3];
    [_lvlLbl mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.addressLbl.mas_bottom).offset(10);
        make.left.equalTo(weakSelf.addressLbl.mas_left);
        make.width.mas_equalTo(56);
        make.height.mas_equalTo(17);
    }];
    
    UIView *line = [[UIView alloc]init];
    line.backgroundColor = [UIColor dc1Color];
    [self.contentView addSubview:line];
    [line mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(weakSelf.contentView);
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView).offset(0);
        make.height.mas_equalTo(0.5);
    }];
}

- (void)bindModel{
    WS(weakSelf);
    [RACObserve(self, model) subscribeNext:^(SCHospitalModel *x) {
        if (x) {
            [weakSelf.photoView sd_setImageWithURL:[NSURL URLWithString:x.receiveThumb] placeholderImage:[UIImage imageNamed:@"医院列表默认220-140"]];
            weakSelf.nameLbl.text = x.hospitalName;
            weakSelf.lvlLbl.text = x.hospitalGrade;
            if (x.hospitalGrade.length > 0) {
                weakSelf.lvlLbl.alpha = 1.0;
            } else {
                weakSelf.lvlLbl.alpha = 0.0;
            }
            weakSelf.addressLbl.text = x.hospitalAddress;
        }
    }];
    
    
    [RACObserve(self, highLightString) subscribeNext:^(NSString *x) {
        if (x) {
            NSString *name = _model.hospitalName;
            NSMutableAttributedString *attrbutedString = [[NSMutableAttributedString alloc] initWithString:name];
            NSRange range = [name rangeOfString:x];
            [attrbutedString addAttributes:@{NSForegroundColorAttributeName:[UIColor tc5Color]} range:range];
            weakSelf.nameLbl.attributedText = attrbutedString;
        }
    }];
    
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
