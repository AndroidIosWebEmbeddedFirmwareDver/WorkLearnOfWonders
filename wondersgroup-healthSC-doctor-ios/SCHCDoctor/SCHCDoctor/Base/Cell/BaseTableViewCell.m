//
//  BaseTableViewCell.m
//  VaccinePatient
//
//  Created by ZJW on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseTableViewCell.h"

@implementation BaseTableViewCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        [self setupBaseView];
        [self bindBaseViewModel];
    }
    
    
    return self;
}

-(void)setupBaseView {
    topLine = [UISetupView setupTopLineViewWithSuperView:self.contentView withSpace:0];
    topLine.hidden = YES;
    
    bottomLine = [UISetupView setupBottomLineViewWithSuperView:self.contentView withSpace:0];
    bottomLine.hidden = YES;
}

-(void)bindBaseViewModel {
    RAC(topLine, hidden)      = RACObserve(self, lineTopHidden);
    RAC(bottomLine, hidden)   = RACObserve(self, lineBottomHidden);
}

+(CGFloat)cellHeight {
    return 0;
}

@end
