//
//  HotSearchCollectionViewCell.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HotSearchCollectionViewCell.h"

@implementation HotSearchCollectionViewCell
-(id)initWithFrame:(CGRect)frame{
    if (self = [super initWithFrame:frame]) {
        [self createView];
    }
    return self;

}

-(void)createView{
    self.titleLab = [UILabel new];
    self.backgroundColor =[UIColor whiteColor];
    [self.contentView addSubview:self.titleLab];
    WS(weakSelf)
    [self.titleLab mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf).offset(8);
        make.left.equalTo(weakSelf).offset(27/2);
        make.right.equalTo(weakSelf).offset(-(26/2));
        make.bottom.equalTo(weakSelf).offset(-8);
    }];
    self.titleLab.textAlignment = NSTextAlignmentCenter;
    self.titleLab.font = [UIFont systemFontOfSize:16];
}
@end
