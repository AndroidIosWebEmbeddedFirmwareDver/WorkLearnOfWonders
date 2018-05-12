//
//  HealthIdentifyTitleTableViewCell.m
//  HCPatient
//
//  Created by guowenke1 on 15/3/10.
//  Copyright (c) 2015年 wonders. All rights reserved.
//

#import "SCHealthIdentifyTitleTableViewCell.h"
#import <Masonry/Masonry.h>


@interface SCHealthIdentifyTitleTableViewCell ()
{
    UILabel *progressLabel;
}

@end

@implementation SCHealthIdentifyTitleTableViewCell



- (void)addView
{
    if (!_contentLabel) {
        _contentLabel = [[UILabel alloc] initWithFrame:CGRectMake(65,0, SCREEN_WIDTH - 10*2, 20)];
        [_contentLabel setTextColor: RGB_COLOR(102, 102, 102)];
        _contentLabel.font = [UIFont systemFontOfSize:18.0];
        _contentLabel.textAlignment = NSTextAlignmentLeft;
        _contentLabel.numberOfLines = 0;
        [self.contentView addSubview:_contentLabel];
        
        progressLabel = [[UILabel alloc] init];
        //[progressLabel setTextColor:[UIColor blackColor]];
        progressLabel.textColor = [UIColor tc5Color];
        progressLabel.font = [UIFont systemFontOfSize:17.0];
        progressLabel.textAlignment = NSTextAlignmentLeft;
        progressLabel.numberOfLines = 0;
        progressLabel.backgroundColor = [UIColor clearColor];
        [self.contentView addSubview:progressLabel];
        [progressLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(self).offset(15);
            make.centerY.equalTo(self);
            make.width.mas_equalTo(50);
            make.height.mas_equalTo(25);
        }];
    }
      self.backgroundColor = [UIColor bc1Color];
}

- (void)creatMessageOnView:(NSInteger)inter message:(NSString *)str total:(NSInteger)total
{
  
    [_labelPrompt removeFromSuperview];
    if (inter == 1) {

        [self setBackgroundColor: [UIColor clearColor]];
        [self addView];
 
        [self.contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(self.contentView);
            make.left.equalTo(self.contentView.mas_left).with.offset(65);
            make.right.equalTo(self.contentView).offset(-25);
            make.height.equalTo(self.contentView);
        }];

    }else{
        
        [self.contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(self.contentView);
            make.left.equalTo(self.contentView.mas_left).with.offset(65);
            make.right.equalTo(self.contentView).offset(-25);
            //            make.height.mas_equalTo(21);
        }];
    }
    self.contentLabel.text = str;

    NSMutableAttributedString *atristring = [[NSMutableAttributedString alloc] initWithString:[NSString stringWithFormat:@"%ld╱%ld",(long)inter, (long)total]];
    
    if (atristring.length < 5) {
        [atristring addAttribute:NSKernAttributeName value:[NSNumber numberWithInt:-5] range:NSMakeRange(1, 1)];
        
        [atristring addAttribute:NSBaselineOffsetAttributeName value:[NSNumber numberWithInt:-5] range:NSMakeRange(2, 2)];
        
        [atristring addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:12.0f] range:NSMakeRange(2, 2)];
    } else {
        [atristring addAttribute:NSKernAttributeName value:[NSNumber numberWithInt:-5] range:NSMakeRange(2, 1)];
        
        [atristring addAttribute:NSBaselineOffsetAttributeName value:[NSNumber numberWithInt:-5] range:NSMakeRange(3, 2)];
        
        [atristring addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:12.0f] range:NSMakeRange(3,2 )];
    }
    progressLabel.attributedText = atristring;
    if (inter == 1) {
   
    }

}


- (void)creatMessage:(NSInteger)inter message:(NSString *)str total:(NSInteger)total;
{
    [_labelPrompt removeFromSuperview];
    
    if (inter == 1) {
        [self setBackgroundColor: [UIColor bc1Color]];
        [self addView];
        [self.contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(self.contentView.mas_top).with.offset(0);
            make.left.equalTo(self.contentView.mas_left).with.offset(45);
            make.right.equalTo(self.contentView.mas_right).with.offset(-25);
        }];
        
    }else{
        [self.contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(self.contentView.mas_top).with.offset(0);
            make.left.equalTo(self.contentView.mas_left).with.offset(45);
            make.right.equalTo(self.contentView.mas_right).with.offset(-25);
        }];
    }
//    self.titleLable.text = [NSString stringWithFormat:@"第 %ld 题", (long)inter];
    self.contentLabel.text = str;
}

#pragma mark - label的高度
- (CGFloat)calculateCellHeightWithMessage:(NSString *)str
{
    CGFloat contentLabelWide = [UIScreen mainScreen].bounds.size.width - 20;
    CGSize maximumContentLabelSize = CGSizeMake(contentLabelWide, FLT_MAX);
    CGFloat totalHeight = 0;
    NSDictionary *attribute = @{NSFontAttributeName: [UIFont systemFontOfSize:16.0]};
    CGSize contentSize = [str boundingRectWithSize:maximumContentLabelSize options:NSStringDrawingUsesFontLeading|NSStringDrawingUsesLineFragmentOrigin attributes:attribute context:nil].size;
    if (str.length > 0) {
        totalHeight += contentSize.height;
    }
    return totalHeight +1;
}




- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
