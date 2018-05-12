//
//  HealthResultTableViewCell.m
//  HCPatient
//
//  Created by guowenke1 on 15/3/10.
//  Copyright (c) 2015年 wonders. All rights reserved.
//

#import "SCHealthResultTableViewCell.h"
#import <Masonry/Masonry.h>


@interface SCHealthResultTableViewCell ()
{
    UILabel *labelPrompt;
    UIView *lineView;
}

@end

static CGFloat labelFont = 14.0;

@implementation SCHealthResultTableViewCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        [self setupCell];
    }
    return self;
}

- (void)setupCell
{
    UIView *bottoomView = [[UIView alloc] init];
    [self.contentView addSubview:bottoomView];
    bottoomView.backgroundColor = [UIColor clearColor];
    [bottoomView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.mas_equalTo(10);
        make.right.mas_equalTo(self.contentView).offset(-15);
        make.height.equalTo(self.contentView).offset(-20);
    }];
    
    self.imageIcon = [[UIImageView alloc] init];
    [bottoomView addSubview:self.imageIcon];
    self.imageIcon.backgroundColor = [UIColor clearColor];
    [self.imageIcon mas_updateConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(bottoomView);
        make.left.equalTo(bottoomView).offset(10);
        make.width.equalTo(@22);
        make.height.mas_equalTo(22);
     }];
    
    _titleLabel = [[UILabel alloc] init];
    _titleLabel.font = [UIFont systemFontOfSize:labelFont];
    _titleLabel.textColor = [UIColor tc1Color];
    _titleLabel.textAlignment = NSTextAlignmentLeft;
    _titleLabel.backgroundColor = [UIColor clearColor];
    _titleLabel.numberOfLines = 0;
    [bottoomView addSubview:_titleLabel];
    [_titleLabel mas_updateConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_imageIcon.mas_right).offset(5);
        make.width.equalTo(@80);
        make.top.equalTo(_imageIcon);
        make.height.mas_equalTo(22);
    }];
    
    labelPrompt = [[UILabel alloc] init];
    labelPrompt.font = [UIFont systemFontOfSize:labelFont];
    labelPrompt.textColor = [UIColor tc2Color];
    labelPrompt.textAlignment = NSTextAlignmentLeft;
    labelPrompt.backgroundColor = [UIColor clearColor];
    labelPrompt.numberOfLines = 0;
    [bottoomView addSubview:labelPrompt];
    
    lineView = [[UIView alloc] init];
    lineView.backgroundColor = [UIColor bc3Color];
    [bottoomView addSubview:lineView];
    
}

- (void)addgrayView
{
    [_grayView removeFromSuperview];
    _grayView = [[UIView alloc] init];
    _grayView.backgroundColor = [UIColor clearColor];
    [self.contentView addSubview:_grayView];
    [_grayView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.contentView);
        make.height.mas_equalTo(20);
    }];
}
- (void)addViewAndMessage:(NSString *)content title:(NSString *)title image:(NSString *)image inter:(NSInteger)inter
{

    self.imageIcon.image = [UIImage imageNamed:image];
    [self.imageIcon mas_updateConstraints:^(MASConstraintMaker *make) {
       
        make.size.mas_equalTo(self.imageIcon.image.size);
    }];
    CGFloat contentLabelWide = [UIScreen mainScreen].bounds.size.width-50;
    CGSize maximumContentLabelSize = CGSizeMake(contentLabelWide, FLT_MAX);
    CGFloat totalHeight = 0;
    NSDictionary *attribute = @{NSFontAttributeName: [UIFont systemFontOfSize:labelFont]};
    CGSize contentSize = [content boundingRectWithSize:maximumContentLabelSize options:NSStringDrawingUsesFontLeading|NSStringDrawingUsesLineFragmentOrigin attributes:attribute context:nil].size;
    if (content.length > 0) {
        totalHeight += contentSize.height + 1;
    }

    labelPrompt.frame = CGRectMake(10,34, SCREEN_WIDTH -50, totalHeight);
    labelPrompt.text = content;
    
    _titleLabel.text = title;
//      //下划线
//    lineView.frame = CGRectMake(0, totalHeight - 1, SCREEN_WIDTH, 0.5);
//    [self.contentView addSubview:lineView];
}

#pragma mark - label的高度
+ (CGFloat)calculateCellHeightWithMessage:(NSString *)str
{
    CGFloat contentLabelWide = [UIScreen mainScreen].bounds.size.width-50;
    CGSize maximumContentLabelSize = CGSizeMake(contentLabelWide, FLT_MAX);
    CGFloat totalHeight = 0;
    NSDictionary *attribute = @{NSFontAttributeName: [UIFont systemFontOfSize:labelFont]};
    CGSize contentSize = [str boundingRectWithSize:maximumContentLabelSize options:NSStringDrawingUsesFontLeading|NSStringDrawingUsesLineFragmentOrigin attributes:attribute context:nil].size;
    if (str.length > 0) {
        totalHeight += contentSize.height;
    }
 
    return totalHeight + 70;
}
- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
