//
//  SearchRecommendCell.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SearchRecommendCell.h"

@implementation SearchRecommendCell
-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        self.titleLab = [UILabel new];
        self.titleLab.font = [UIFont systemFontOfSize:14];
        self.titleLab.textColor = [UIColor tc1Color];
        [self.contentView addSubview:self.titleLab];
        WS(weakSelf)
        [self.titleLab mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.bottom.equalTo(weakSelf);
            make.left.equalTo(weakSelf).offset(15);
            make.width.mas_equalTo(SCREEN_WIDTH-15);
        }];
    }
    return self;

}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
