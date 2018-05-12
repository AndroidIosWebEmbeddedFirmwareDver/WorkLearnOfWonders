//
//  SCHospitalOverviewIntroduceCell.m
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCHospitalOverviewIntroduceCell.h"

#define MARGIN 15.0

@interface SCHospitalOverviewIntroduceCell()

@property (nonatomic, strong) UILabel *contentLabel;

@end

@implementation SCHospitalOverviewIntroduceCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        self.lineTopHidden = YES;
        self.lineBottomHidden = YES;
        
        
        _contentLabel = [[UILabel alloc] init];
        _contentLabel.font          = [UIFont systemFontOfSize:14];
        _contentLabel.textColor     = [UIColor tc2Color];
        _contentLabel.numberOfLines = 0;
        [self.contentView addSubview:_contentLabel];
        
        
        [_contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.bottom.equalTo(self.contentView);
            make.left.offset(MARGIN);
            make.right.offset(-MARGIN);
        }];
        
        [self bind];
    }
    return self;
}


- (void)bind {
    [RACObserve(self, content) subscribeNext:^(NSString *x) {
        if (!x) return;
        

        NSMutableAttributedString *attrStr = [[NSMutableAttributedString alloc]
                                              initWithString:x
                                              attributes:[self attributes]
                                              ];
        _contentLabel.attributedText = attrStr;
    }];
}


- (NSDictionary *)attributes {
    NSMutableParagraphStyle *style = [[NSMutableParagraphStyle alloc] init];
    style.lineSpacing = 5.0;
    return @{NSFontAttributeName : [UIFont systemFontOfSize:14],
             NSForegroundColorAttributeName: [UIColor tc2Color],
             NSParagraphStyleAttributeName : style,
             };
}


- (CGFloat)caculateAttributeStringHeight:(NSString *)string withWidth:(CGFloat)width {
    return [string boundingRectWithSize:CGSizeMake(width, CGFLOAT_MAX)
                                      options:NSStringDrawingUsesLineFragmentOrigin|NSStringDrawingUsesFontLeading
                                   attributes:[self attributes]
                                      context:nil].size.height;
}


- (CGSize)sizeThatFits:(CGSize)size {
    CGFloat height = [self caculateAttributeStringHeight:self.content withWidth:(SCREEN_WIDTH - MARGIN * 2)];
    return CGSizeMake(size.width, height);
}

@end
