//
//  ChooseReportTimerCell.m
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ChooseReportTimerCell.h"

@interface ChooseReportTimerCell()

@end

@implementation ChooseReportTimerCell

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
    self.selectionStyle = UITableViewCellSelectionStyleNone;
    WS(weakSelf)
    self.backgroundColor    = [UIColor bc1Color];
    self.titleLB            = [[UILabel alloc]init];
    self.titleLB.textColor  = [UIColor tc1Color];
    self.titleLB.font       = [UIFont systemFontOfSize:14.0];
    self.titleLB.text       = @"今天";
    [self.contentView addSubview:self.titleLB];
    [self.titleLB mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15.0);
        make.top.equalTo(weakSelf.contentView).offset(12.0);
        make.bottom.equalTo(weakSelf.contentView).offset(-12.0);
    }];
    
    self.chooseBtn          = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.chooseBtn setImage:[UIImage imageNamed:@"未选中"] forState:UIControlStateNormal];
    [self.chooseBtn setImage:[UIImage imageNamed:@"选中"] forState:UIControlStateSelected];
    [self.contentView addSubview:self.chooseBtn];
    [self.chooseBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15.0);
        make.top.equalTo(weakSelf.titleLB);
        make.size.mas_equalTo(CGSizeMake(20.0, 20.0));
    }];
    
    [[[self.chooseBtn rac_signalForControlEvents:UIControlEventTouchUpInside] takeUntil:self.rac_prepareForReuseSignal] subscribeNext:^(UIButton *sender) {
        if (self.BtnBlockAction) {
            self.BtnBlockAction(sender);
        }
    }];

    
    self.lineView                   = [[UIView alloc]init];
    self.lineView .backgroundColor  = [UIColor bc3Color];
    [self.contentView addSubview:self.lineView ];
    [self.lineView  mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15.0);
        make.top.equalTo(weakSelf.contentView.mas_bottom);
        make.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(0.5);
    }];
    
}

- (void)RACBind {
    @weakify(self)
    [RACObserve(self, cellModel.title) subscribeNext:^(NSString *title) {
        @strongify(self)
        self.titleLB.text = title;
    }];
}

@end
