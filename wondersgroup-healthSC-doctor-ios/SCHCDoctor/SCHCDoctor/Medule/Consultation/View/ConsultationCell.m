//
//  ConsultationCell.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ConsultationCell.h"

@interface ConsultationCell ()


@end

@implementation ConsultationCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier  {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        
        [self prepareUI];
        [self bindRac];
    }
    return self;
}

- (void)prepareUI {
    
    self.contentView.backgroundColor = [UIColor bc1Color];
    
    _badgeLbl = [[UILabel alloc] init];
    _badgeLbl.translatesAutoresizingMaskIntoConstraints = NO;
    _badgeLbl.backgroundColor = [UIColor sbc2Color];
    _badgeLbl.textColor = [UIColor tc0Color];
    _badgeLbl.font = [UIFont systemFontOfSize:13];
    _badgeLbl.textAlignment = NSTextAlignmentCenter;
    _badgeLbl.layer.cornerRadius = 10;
    _badgeLbl.layer.masksToBounds = YES;
    [self.contentView addSubview:_badgeLbl];
    
    [self addConstraint:[NSLayoutConstraint constraintWithItem:_badgeLbl attribute:NSLayoutAttributeTop relatedBy:NSLayoutRelationEqual toItem:self.avatarView attribute:NSLayoutAttributeTop multiplier:1.0 constant:-5]];
    [self addConstraint:[NSLayoutConstraint constraintWithItem:_badgeLbl attribute:NSLayoutAttributeHeight relatedBy:NSLayoutRelationEqual toItem:nil attribute:NSLayoutAttributeNotAnAttribute multiplier:1 constant:20]];
    [self addConstraint:[NSLayoutConstraint constraintWithItem:_badgeLbl attribute:NSLayoutAttributeLeft relatedBy:NSLayoutRelationEqual toItem:self.avatarView attribute:NSLayoutAttributeRight multiplier:1.0 constant:-13]];
    [self addConstraint:[NSLayoutConstraint constraintWithItem:_badgeLbl attribute:NSLayoutAttributeWidth relatedBy:NSLayoutRelationEqual toItem:nil attribute:NSLayoutAttributeNotAnAttribute multiplier:1.0 constant:20]];
    
}

- (void)bindRac {
   
}

- (void)setBadge:(NSInteger)badge
{
    _badge = badge;
    if (badge > 0) {
        self.badgeLbl.hidden = NO;
    }
    else{
        self.badgeLbl.hidden = YES;
    }
    
    if (badge > 99) {
        self.badgeLbl.text = @"N+";
    }
    else{
        self.badgeLbl.text = [NSString stringWithFormat:@"%ld", (long)_badge];
    }
}

@end
