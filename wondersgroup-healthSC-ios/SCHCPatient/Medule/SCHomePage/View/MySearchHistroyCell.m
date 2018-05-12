//
//  MySearchHistroyCell.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "MySearchHistroyCell.h"

@implementation MySearchHistroyCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    if (self = [super initWithStyle: style reuseIdentifier:reuseIdentifier]) {
        [self createView];
    }
    return self;

}
//- (void)setFrame:(CGRect)frame {
//    CGFloat LocationCellSpace = 15;
//    frame.origin.x +=
//    LocationCellSpace;
//    frame.size.width -=
//    2 * LocationCellSpace;
//    [super setFrame:frame];
//}
-(void)createView{
    self.titleLab = [UILabel new];
    self.titleLab.font = [UIFont systemFontOfSize:14];
    [self.contentView addSubview:self.titleLab];
    self.defultImageView = [UIImageView new];
     [self.contentView addSubview:self.defultImageView];
    self.defultImageView.image = [UIImage imageNamed:@""];
    WS(weakSelf)
    [self.defultImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf).offset(-13);
        make.top.equalTo(weakSelf).offset(15);
        make.width.mas_equalTo(7);
        make.centerY.equalTo(weakSelf);
        make.bottom.equalTo(weakSelf).offset(-15);
    }];
    [self.titleLab mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(weakSelf);
        make.left.equalTo(weakSelf).offset(15);
        make.right.equalTo(self.defultImageView.mas_left).offset(-10);
    }];


}

@end
