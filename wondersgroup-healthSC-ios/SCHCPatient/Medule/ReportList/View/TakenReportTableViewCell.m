//
//  TakenReportTableViewCell.m
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "TakenReportTableViewCell.h"

@interface TakenReportTableViewCell ()

@end

@implementation TakenReportTableViewCell

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
    }
    return self;
}

- (void)createUI {
    WS(weakSelf)
    self.backgroundColor   = [UIColor bc1Color];
    
    self.nameLB            = [[UILabel alloc]init];
    self.nameLB.textColor  = [UIColor tc1Color];
    self.nameLB.text       = @"医院";
    self.nameLB.font       = [UIFont systemFontOfSize:14.0];
    [self.contentView addSubview:self.nameLB];
    [self.nameLB mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15.0);
        make.centerY.equalTo(weakSelf.contentView);
        make.right.equalTo(weakSelf.contentView).offset(-15.0);
    }];
    
     UIView *lineView         = [[UIView alloc]init];
     lineView.backgroundColor = [UIColor dc4Color];
    [self.contentView addSubview:lineView];
    [lineView  mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15.0);
        make.bottom.equalTo(weakSelf.contentView).offset(-0.5);
        make.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(0.5);
    }];
}

- (void)setCellModel:(SCHospitalModel *)cellModel withKeyWord:(NSString *)keyWord {
     NSString *resultStr = cellModel.hospitalName;
     NSMutableAttributedString *attrbutedString = [[NSMutableAttributedString alloc]initWithString:resultStr];
     NSRange range = [resultStr rangeOfString:keyWord];
     [attrbutedString addAttributes:@{NSForegroundColorAttributeName:[UIColor tc5Color]} range:range];
     self.nameLB.attributedText = attrbutedString;

}




@end
