//
//  SCHospitalHomePageHeaderCell.m
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCHospitalHomePageHeaderCell.h"

@interface SCHospitalHomePageHeaderCell()

@property (nonatomic, strong) UIImageView *bgImageV;
@property (nonatomic, strong) UIView *infoMaskView;
@property (nonatomic, strong) UILabel *nameLabel;
@property (nonatomic, strong) UILabel *levelLabel;
@property (nonatomic, strong) UILabel *bookingTitleLabel;
@property (nonatomic, strong) UILabel *bookingNumbLabel;

@end

@implementation SCHospitalHomePageHeaderCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupSubviews];
        [self setupSubviewsLayout];
        [self bind];
        self.lineTopHidden = YES;
        self.clipsToBounds = YES;
    }
    return self;
}


#pragma mark - Bind

- (void)bind {
    [RACObserve(self, bookHidden) subscribeNext:^(NSNumber *x) {
        _bookingTitleLabel.hidden = [x boolValue];
        _bookingNumbLabel.hidden  = [x boolValue];
    }];
    
    
    [RACObserve(self, model) subscribeNext:^(SCHospitalModel *x) {
        [_bgImageV sd_setImageWithURL:[NSURL URLWithString:x.hospitalPhoto] placeholderImage:[UIImage imageNamed:@"医院大图默认750-440"]];
        
        /// 更改版本: 1.0
        /// 修改人员: Joseph Gao
        /// 修改描述: 暂时别删
//        if (x.hosptialPhoto) {
//            [_bgImageV sd_setImageWithURL:[NSURL URLWithString:x.hosptialPhoto] placeholderImage:[UIImage imageNamed:@"医院大图默认750-440"] completed:^(UIImage *image, NSError *error, SDImageCacheType cacheType, NSURL *imageURL) {
//                if (error) {
//                    _bgImageV.image = [UIImage imageNamed:@"医院加载失败750-440"];
//                }
//            }];
//        }


        
        _nameLabel.text         = x.hospitalName;
        _levelLabel.text        = x.hospitalGrade;
        _bookingNumbLabel.text  = x.receiveCount;
        _bookingTitleLabel.text = @"预约量";
        
        /// 更改版本: 1.0
        /// 修改人员: Joseph Gao
        /// 修改描述: 暂时别删
//        _bookingTitleLabel.text = [NSString stringWithFormat:@"预约: %@", x.receiveCount];
    }];
}



#pragma mark - Setup UI

- (void)setupSubviews {
    //
    _bgImageV = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"医院大图默认750-440"]];
    _bgImageV.contentMode = UIViewContentModeScaleAspectFill;
    [self.contentView addSubview:_bgImageV];
    
    //
    _infoMaskView = [[UIView alloc] init];
    _infoMaskView.backgroundColor = [UIColor colorWithWhite:0.0 alpha:0.4];
    [self.contentView addSubview:_infoMaskView];
    
    //
    _nameLabel = [self creatLabelInView:_infoMaskView fontSize:16 textColor:[UIColor whiteColor] aligntment:NSTextAlignmentLeft];
    _levelLabel = [self creatLabelInView:_infoMaskView fontSize:12 textColor:[UIColor tc4Color] aligntment:NSTextAlignmentLeft];
    _bookingNumbLabel = [self creatLabelInView:_infoMaskView fontSize:14 textColor:[UIColor whiteColor] aligntment:NSTextAlignmentRight];
    _bookingTitleLabel = [self creatLabelInView:_infoMaskView fontSize:12 textColor:[UIColor tc4Color] aligntment:NSTextAlignmentRight];
    
    /// 更改版本: 1.0
    /// 修改人员: Joseph Gao
    /// 修改描述: UI修改, 暂时不删
//    _bookingTitleLabel = [self creatLabelInView:_infoMaskView fontSize:16 textColor:[UIColor whiteColor] aligntment:NSTextAlignmentRight];
//    _bookingTitleLabel.text = @"预约: ";
}


- (void)setupSubviewsLayout {
    //
    [_bgImageV mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.contentView);
    }];
    
    //
    [_infoMaskView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.contentView);
        make.height.mas_equalTo(50);
    }];
    

    
    //
    [_levelLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_nameLabel);
        make.top.equalTo(_infoMaskView).offset(30);
    }];
    

    //
    [_bookingNumbLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(_nameLabel);
        make.right.equalTo(self.contentView).offset(-18);
    }];

    //
    [_nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView).offset(15);
        make.bottom.equalTo(self.contentView).offset(-26);
        make.right.lessThanOrEqualTo(_bookingNumbLabel.mas_left).offset(-10);
    }];
    
    
    //
    [_bookingTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(_bookingNumbLabel);
        make.top.equalTo(_bookingNumbLabel.mas_bottom).offset(5);
    }];
    
    
    /// 更改版本: 1.0
    /// 修改人员: Joseph Gao
    /// 修改描述: UI修改, 暂时不删
//    [_bookingTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.right.equalTo(self.contentView).offset(-15);
//        make.bottom.equalTo(self.contentView).offset(-17);
//    }];
    
}



#pragma mark - Other

- (UILabel *)creatLabelInView:(UIView *)inView
                     fontSize:(CGFloat)fontSize
                    textColor:(UIColor *)textColor
                   aligntment:(NSTextAlignment)alignment {
    
    UILabel *label = [[UILabel alloc] init];
    
    label.textColor = textColor;
    label.font = [UIFont systemFontOfSize:fontSize];
    label.textAlignment = alignment;
    if (inView) {
        [inView addSubview:label];
    }
    
    return label;
}


@end
