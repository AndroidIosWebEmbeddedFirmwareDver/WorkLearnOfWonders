//
//  SCSettingAccountCell.m
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSettingAccountCell.h"
#import "NKColorSwitch.h"

@interface SCSettingAccountCell ()

@property (nonatomic, strong) UILabel *titleLabel;
@property (nonatomic, strong) NKColorSwitch *tipSwitch;
@property (nonatomic, strong) UIView *lineView;
@property (nonatomic, strong) UIImageView *arrowImageView;

@end

@implementation SCSettingAccountCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupUI];
        [self bindData];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    return self;
}

- (void)setupUI {
    WS(ws)
    
    self.backgroundColor = [UIColor whiteColor];
    
    _titleLabel = [UILabel new];
    _titleLabel.textColor = [UIColor tc1Color];
    _titleLabel.font = [UIFont systemFontOfSize:14];
    [self.contentView addSubview:_titleLabel];
    [_titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(self.contentView);
        make.left.equalTo(self.contentView).offset(15);
    }];
    
    _tipSwitch = [[NKColorSwitch alloc]initWithFrame:CGRectMake(SCREEN_WIDTH-15-44, (44-24)/2, 44, 24)];
    [_tipSwitch setOnTintBorderColor:UIColorByRGB(0x2E7AF0)];
    [_tipSwitch setTintBorderColor:UIColorByRGB(0x2E7AF0)];
    _tipSwitch.thumbTintColor = UIColorByRGB(0x2E7AF0);
    _tipSwitch.onTintColor = UIColorByRGB(0xffffff);
    
    [_tipSwitch addTarget:self action:@selector(switchAction:) forControlEvents:UIControlEventValueChanged];   // 开关事件切换通知

    [self.contentView addSubview:_tipSwitch];
//    [_tipSwitch mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.centerY.equalTo(self.contentView);
//        make.right.equalTo(self.contentView).offset(-15);
//        make.size.mas_equalTo(CGSizeMake(44, 24));
//    }];
    
    _arrowImageView = [UISetupView setupImageViewWithSuperView:self.contentView withImageName:@"link_right"];
    [_arrowImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(self.contentView);
        make.right.equalTo(self.contentView).offset(-15);
        make.size.mas_equalTo(CGSizeMake(7, 14));
    }];
    
    _lineView = [UIView new];
    _lineView.backgroundColor = [UIColor dc1Color];
    [self.contentView addSubview:_lineView];
    [_lineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.right.equalTo(ws.contentView);
        make.left.equalTo(ws.contentView);
        make.height.mas_equalTo(0.5);
    }];
}

- (void)bindData {
    WS(weakSelf)
    [RACObserve(self, model) subscribeNext:^(SCSettingModel *x) {
        if (x) {
            weakSelf.titleLabel.text = x.content;
            weakSelf.tipSwitch.on = x.isOn;
            weakSelf.tipSwitch.hidden = x.isHaveArrow;
            weakSelf.arrowImageView.hidden = !x.isHaveArrow;
        }
    }];
    
    [RACObserve(self, isLast) subscribeNext:^(NSNumber *x) {
        weakSelf.lineView.hidden = [x boolValue];
    }];
}

-(void)switchAction:(NKColorSwitch *)sender {
    
    NSLog(@"%@",[NSNumber numberWithBool:sender.on]);
    [self changeSwitchColor:sender];
}

-(void)changeSwitchColor:(NKColorSwitch *)sender  {
    if (sender.on) {
        sender.thumbTintColor = UIColorByRGB(0x2E7AF0);
        [sender setOnTintBorderColor:UIColorByRGB(0x2E7AF0)];
        [sender setTintBorderColor:UIColorByRGB(0x2E7AF0)];
    }else {
        sender.thumbTintColor = UIColorByRGB(0xcccccc);
        [sender setOnTintBorderColor:UIColorByRGB(0xcccccc)];
        [sender setTintBorderColor:UIColorByRGB(0xcccccc)];
    }
}

@end
