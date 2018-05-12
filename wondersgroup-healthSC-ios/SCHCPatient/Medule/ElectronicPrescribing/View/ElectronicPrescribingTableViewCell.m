//
//  ElectronicPrescribingTableViewCell.m
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ElectronicPrescribingTableViewCell.h"

#define TEXTFONT [UIFont systemFontOfSize:14.0]

@interface ElectronicPrescribingTableViewCell ()

@property (strong, nonatomic) UILabel *prescribeDateLB;
@property (strong, nonatomic) UILabel *subjectNameLB;
@property (strong, nonatomic) UILabel *expenseLB;
@property (strong, nonatomic) UILabel *hosptialLB;

@end

@implementation ElectronicPrescribingTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}


- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        [self createUI];
        [self RACBind];
    }
    return self;
}

- (void)createUI {
    WS(weakSelf)
    self.selectionStyle  = UITableViewCellSelectionStyleNone;
    self.backgroundColor = [UIColor bc1Color];
    //开方日期
    UILabel *prescribeDate         = [[UILabel alloc]init];
    prescribeDate.textColor        = [UIColor tc2Color];
    prescribeDate.text             = @"开方时间: ";
    prescribeDate.font             = TEXTFONT;
    CGSize prescribeDateThatFit  = [prescribeDate sizeThatFits:CGSizeZero];
    [self.contentView addSubview:prescribeDate];
    [prescribeDate mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15.0);
        make.top.equalTo(weakSelf.contentView).offset(15.0);
        make.size.mas_equalTo(prescribeDateThatFit);
    }];
    
    self.prescribeDateLB           = [[UILabel alloc]init];
    self.prescribeDateLB.textColor = [UIColor tc1Color];
    self.prescribeDateLB.text      = @"";
    self.prescribeDateLB.font      = TEXTFONT;
    [self.contentView addSubview:self.prescribeDateLB];
    [self.prescribeDateLB mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(101.0);
        make.top.equalTo(prescribeDate);
        make.height.equalTo(prescribeDate);
        make.right.equalTo(weakSelf.contentView).offset(-15.0);
    }];
    
    //医院
    UILabel *hosptial                 = [[UILabel alloc]init];
    hosptial.textColor                = [UIColor tc2Color];
    hosptial.text                     = @"医        院: ";
    hosptial.font                     = TEXTFONT;
    CGSize   hosptialThatFit          = [hosptial sizeThatFits:CGSizeZero];
    [self.contentView addSubview:hosptial];
    [hosptial mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(prescribeDate);
        make.top.equalTo(prescribeDate.mas_bottom).offset(10.0);
        make.size.mas_equalTo(hosptialThatFit);
    }];
    
    self.hosptialLB                   = [[UILabel alloc]init];
    self.hosptialLB.textColor         = [UIColor tc1Color];
    self.hosptialLB.text              = @"";
    self.hosptialLB.font              = TEXTFONT;
    [self.contentView addSubview:self.hosptialLB];
    [self.hosptialLB mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.prescribeDateLB);
        make.top.equalTo(hosptial);
        make.height.equalTo(hosptial);
        make.right.equalTo(weakSelf.contentView).offset(-45.0);
    }];

    
    UILabel *subjectName             = [[UILabel alloc]init];
    subjectName.textColor            = [UIColor tc2Color];
    subjectName.text                 = @"科        室: ";
    subjectName.font                 = TEXTFONT;
        CGSize subjectNameThatFit        = [subjectName sizeThatFits:CGSizeZero];
    [self.contentView addSubview:subjectName];
    [subjectName mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(hosptial);
        make.top.equalTo(hosptial.mas_bottom).offset(10.0);
        make.size.mas_equalTo(subjectNameThatFit);
    }];
    
    self.subjectNameLB                = [[UILabel alloc]init];
    self.subjectNameLB.textColor      = [UIColor tc1Color];
    self.subjectNameLB.text           = @"";
    self.subjectNameLB.font           = TEXTFONT;
    [self.contentView addSubview:self.subjectNameLB];
    [self.subjectNameLB mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.prescribeDateLB);
        make.top.equalTo(subjectName);
        make.height.equalTo(subjectName);
        make.right.equalTo(weakSelf.hosptialLB);
    }];
    
    //费别
    UILabel *expense                  = [[UILabel alloc]init];
    expense.textColor                 = [UIColor tc2Color];
    expense.text                      = @"金        额: ";
    expense.font                      = TEXTFONT;
    CGSize  expenseThatFit            = [expense sizeThatFits:CGSizeZero];
    [self.contentView addSubview:expense];
    [expense mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(subjectName);
        make.top.equalTo(subjectName.mas_bottom).offset(10.0);
        make.width.mas_equalTo(expenseThatFit.width);
        make.bottom.equalTo(weakSelf.contentView).offset(-15.0);
    }];
    
    self.expenseLB                    = [[UILabel alloc]init];
    self.expenseLB.textColor          = [UIColor tc1Color];
    self.expenseLB.text               = @"";
    self.expenseLB.font               = TEXTFONT;
    [self.contentView addSubview:self.expenseLB];
    [self.expenseLB mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.hosptialLB);
        make.top.equalTo(expense);
        make.height.equalTo(expense);
        make.right.equalTo(weakSelf.hosptialLB);
    }];

    UIImageView *imageView             = [[UIImageView alloc]init];
    imageView.image                    = [UIImage imageNamed:@"link_right"];
    [self.contentView addSubview:imageView];
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15.0);
        make.centerY.equalTo(weakSelf);
        make.size.mas_equalTo(CGSizeMake(7.0, 14.0));
    }];
}
- (void)RACBind {
    @weakify(self)
    [RACObserve(self, cellModel.kfsj) subscribeNext:^(NSString *date) {
        @strongify(self)
        self.prescribeDateLB.text = date;
    }];
    
    [RACObserve(self, cellModel.yljgmc) subscribeNext:^(NSString *hosptialName) {
        @strongify(self)
        self.hosptialLB.text = hosptialName;
    }];
    
    [RACObserve(self, cellModel.kfksmc) subscribeNext:^(NSString *subjectName) {
        @strongify(self)
        self.subjectNameLB.text = subjectName;
    }];
    
    [RACObserve(self, cellModel.cfje) subscribeNext:^(NSString *expense) {
        @strongify(self)
        self.expenseLB.text = [NSString stringWithFormat:@"￥%@",expense];
    }];
    
}

@end
