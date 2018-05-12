//
//  HotTopicConsultCell.m
//  SZCirculationImage
//
//  Created by Wonders_iOS on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HotTopicConsultCell.h"
//#import "View+MASAdditions.h"
//Masnory  弱引用
#define WS(weakSelf)  __weak __typeof(&*self)weakSelf = self;
@implementation HotTopicConsultCell
-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self createUI];
        [self bindViewModel];
    }
    return self;
}
-(void)createUI{
    self.titleImageView = [[UIImageView alloc]initWithImage:[UIImage imageNamed:@""]];
    [self.contentView addSubview:self.titleImageView];
    WS(weakSelf)
    [self.titleImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(weakSelf);
        make.width.mas_equalTo(180/2);
        make.height.mas_equalTo(135/2);
        make.left.equalTo(weakSelf).offset(15);
    }];
    self.titleLabel = [UILabel new];
    self.titleLabel.font = [UIFont systemFontOfSize:16];
    self.titleLabel.textColor = [UIColor tc1Color];
    [self.contentView addSubview:self.titleLabel];
    [self.titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.titleImageView.mas_right).offset(15);
        make.top.equalTo(self.titleImageView);
        make.right.equalTo(weakSelf).offset(-(69/2));
        make.height.mas_equalTo(24);
    }];
    self.detailLabel = [UILabel new];
    self.detailLabel.font = [UIFont systemFontOfSize:14];
    self.detailLabel.textColor = [UIColor tc3Color];
    [self.contentView addSubview:self.detailLabel];
    [self.detailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.titleLabel.mas_bottom).offset(5);
        make.left.equalTo(self.titleLabel);
        make.bottom.equalTo(self.titleImageView);
        make.right.equalTo(weakSelf).offset(-15);
    }];
    self.detailLabel.numberOfLines = 2;
    
    UIView * lineView= [UIView new];
    [self.contentView addSubview:lineView];
    lineView.backgroundColor = [UIColor bc2Color];
    
    [lineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(weakSelf);
        make.height.equalTo(@0.8);
        make.left.equalTo(weakSelf);
        make.right.equalTo(weakSelf);
    }];
}
-(void)bindViewModel{
    WS(weakSelf);
    [RACObserve(self, model) subscribeNext:^(ArticlessModel *x) {
        if (x) {
            
            [weakSelf.titleImageView sd_setImageWithURL:[NSURL URLWithString:x.thumb] placeholderImage:[UIImage imageNamed:@"文章列表加载失败180-135"]];
            
            if (x.title) {
                weakSelf.titleLabel.text = x.title;
            }
            
            if (x.desc) {
                weakSelf.detailLabel.text = x.desc;
            }
            
        }
    }];
    
    
    [RACObserve(self, highLightString) subscribeNext:^(NSString *x) {
        if (x) {
            //                NSString *name = _model.hospitalName;
            NSString *name = _model.title;
            
            //                NSMutableAttributedString *attrbutedString = [[NSMutableAttributedString alloc] initWithString:name];
            //                NSRange range = [name rangeOfString:x];
            //                [attrbutedString addAttributes:@{NSForegroundColorAttributeName:[UIColor tc5Color]} range:range];
            //                weakSelf.hospitalsNameLab.attributedText = attrbutedString;
            
            
            NSMutableAttributedString *attrbutedStr = [[NSMutableAttributedString alloc] initWithString:name];
            NSRange range1 = [name rangeOfString:x];
            [attrbutedStr addAttributes:@{NSForegroundColorAttributeName:[UIColor tc5Color]} range:range1];
            weakSelf.titleLabel.attributedText = attrbutedStr;
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
