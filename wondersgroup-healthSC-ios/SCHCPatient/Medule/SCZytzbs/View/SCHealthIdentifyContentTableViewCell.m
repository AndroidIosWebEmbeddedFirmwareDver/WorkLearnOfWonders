//
//  HealthIdentifyContentTableViewCell.m
//  HCPatient
//
//  Created by guowenke1 on 15/3/10.
//  Copyright (c) 2015年 wonders. All rights reserved.
//

#import "SCHealthIdentifyContentTableViewCell.h"
#import <Masonry/Masonry.h>

@implementation SCHealthIdentifyContentTableViewCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    
    if (self == [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupview];
    }
    return self;
}
- (void)setupview
{
    UIView *topline = [[UIView alloc]initWithFrame:CGRectMake(0, 0.5, SCREEN_WIDTH, 0.5)];
    topline.backgroundColor = [UIColor bc3Color];
    [self.contentView addSubview:topline];
    [topline mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self);
        make.width.equalTo(self);
        make.top.equalTo(self);
        make.height.mas_equalTo(0.5);
    }];

    
    UIView *bottomline = [[UIView alloc] init];
       [self.contentView addSubview:bottomline];
    [bottomline mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self);
        make.width.equalTo(self);
        make.top.equalTo(self.mas_bottom);
        make.height.mas_equalTo(0.5);
    }];
    
    bottomline.backgroundColor = [UIColor bc3Color];
 
    
}

- (void)creatMessageOnViewMessage:(NSString *)str
{
    if (!_baseView) {
        [self setBackgroundColor: [UIColor bc1Color]];
        
        UIView *baseView = [[UIView alloc] initWithFrame: self.frame];
        [baseView setBackgroundColor: self.backgroundColor];
        [self.contentView addSubview: baseView];
        _baseView = baseView;
 
        [_baseView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(self.contentView.mas_top).with.offset(0);
            make.left.equalTo(self.contentView.mas_left).with.offset(0);
            make.right.equalTo(self.contentView.mas_right).with.offset(0);
        }];
        
        UIImage *image = [UIImage imageNamed:@"未选中"];
        UIImageView *imageSelect = [[UIImageView alloc] initWithImage: image];
        [self.contentView addSubview: imageSelect];
        [imageSelect mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.mas_equalTo(35);
            make.centerY.equalTo(self);
//            make.size.mas_equalTo(imageSelect.image.size);
            make.size.mas_equalTo(CGSizeMake(20, 20));
        }];
        
        _imageSelect = imageSelect;
        _imageSelect.highlightedImage = [UIImage imageNamed:@"选中蓝色"];
        [_imageSelect mas_makeConstraints:^(MASConstraintMaker *make) {
          
            make.centerY.equalTo(self);
            make.left.equalTo(self.contentView).offset(35);
//             make.size.mas_equalTo(imageSelect.image.size);
            make.size.mas_equalTo(CGSizeMake(20, 20));
//            make.right.equalTo(self.contentView.mas_right).with.offset(0);
        }];
        
        _contentLabel = [[UILabel alloc] init];
        [_contentLabel setTextColor: RGB_COLOR(102, 102, 102)];
        _contentLabel.font = [UIFont systemFontOfSize:17.0];
        _contentLabel.textAlignment = NSTextAlignmentLeft;
        [self.contentView addSubview:_contentLabel];
        [_contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerY.equalTo(self);
            make.left.equalTo(_imageSelect.mas_right).offset(18);
            make.width.mas_equalTo(220);
            make.height.mas_equalTo(25);
        }];

    }
    //ZYCOLOR
     self.contentLabel.textColor = [UIColor tc2Color];
   // self.contentLabel.backgroundColor = [UIColor redColor];
    self.contentLabel.text = str;
    //self.backgroundColor = [UIColor redColor];
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
