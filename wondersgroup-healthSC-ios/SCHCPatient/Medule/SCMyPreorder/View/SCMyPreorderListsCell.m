//
//  SCMyPreorderListsCell.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCMyPreorderListsCell.h"

@interface SCMyPreorderListsCell ()

@property (nonatomic, strong) UILabel *hospitalLabel;
@property (nonatomic, strong) UILabel *dutyLabel;
@property (nonatomic, strong) UILabel *nameLabel;
@property (nonatomic, strong) UILabel *departmentLabel;
@property (nonatomic, strong) UILabel *timeLabel;
@property (nonatomic, strong) UILabel *personLabel;
@property (nonatomic, strong) UILabel *moneyLabel;

@end

@implementation SCMyPreorderListsCell

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
    
    self.hospitalLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc1Color] withFontSize:16];
    self.hospitalLabel.numberOfLines = 0;
    [self.hospitalLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView).offset(-15);
    }];
    
    
    
    
}

-(void)bindViewModel {
    //WS(weakSelf)
    
    
}


+(CGFloat)cellHeight {
    return 154;
}



@end
