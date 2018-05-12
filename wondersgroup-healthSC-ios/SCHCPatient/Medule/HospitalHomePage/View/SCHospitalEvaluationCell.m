//
//  SCHospitalEvaluationCell.m
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/1.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "SCHospitalEvaluationCell.h"
#import "SCEvaluationModel.h"

@interface SCHospitalEvaluationCell()

@property (nonatomic, strong) UILabel *contentLabel;
@property (nonatomic, strong) UILabel *nameLabel;
@property (nonatomic, strong) UILabel *timeLabel;

@property (nonatomic, strong) NSMutableArray *starArrayM;

@end

@implementation SCHospitalEvaluationCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupSubviews];
        [self setupSubviewsLayout];
        [self bind];
        self.lineTopHidden = YES;
    }
    return self;
}



#pragma mark - Bind

- (void)bind {
    
    NSInteger starCount = 3;
    if (starCount != 0) {
        [self.starArrayM enumerateObjectsUsingBlock:^(UIButton * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
            if (idx == starCount - 1) {
                *stop = YES;
            }
            obj.selected = YES;
        }];
    }

    
    [RACObserve(self, model) subscribeNext:^(SCEvaluationModel *x) {
        if (x == nil) return;
        
        _contentLabel.text = x.content;
        _nameLabel.text = x.nickName;
        _timeLabel.text = x.createTime;
    }];
}


#pragma mark - Setup UI

- (void)setupSubviews {
    _contentLabel = [self creatLabelInView:self.contentView fontSize:14 textColor:[UIColor tc2Color] aligntment:NSTextAlignmentLeft];
    _nameLabel = [self creatLabelInView:self.contentView fontSize:12 textColor:[UIColor tc3Color] aligntment:NSTextAlignmentRight];
    _timeLabel = [self creatLabelInView:self.contentView fontSize:12 textColor:[UIColor tc3Color] aligntment:NSTextAlignmentRight];
}


- (void)setupSubviewsLayout {
    CGFloat margin = 15;
    [_contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.offset(margin);
        make.left.offset(margin);
        make.right.offset(-margin);
    }];
    
    
    [_nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_contentLabel);
        make.top.equalTo(_contentLabel.mas_bottom).offset(10);
        make.bottom.offset(-margin);
    }];
    
    [_timeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(_nameLabel);
        make.right.offset(-margin);
    }];
    
    
    
    __block UIView *preBtn = nil;
    [self.starArrayM enumerateObjectsUsingBlock:^(id  _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        CGFloat offsetX = preBtn == nil ? 15 : 6;
        if (preBtn == nil) {
            preBtn = _nameLabel;
        }
        
        [obj mas_makeConstraints:^(MASConstraintMaker *make) {
            make.size.mas_equalTo(CGSizeMake(10, 10));
            make.centerY.equalTo(_nameLabel);
            make.left.equalTo(preBtn.mas_right).offset(offsetX);
        }];
        
        preBtn = obj;
    }];
}


- (UILabel *)creatLabelInView:(UIView *)inView
                     fontSize:(CGFloat)fontSize
                    textColor:(UIColor *)textColor
                   aligntment:(NSTextAlignment)alignment {
    
    UILabel *label = [[UILabel alloc] init];
    
    label.textColor = textColor;
    label.font = [UIFont systemFontOfSize:fontSize];
    label.textAlignment = alignment;
    label.userInteractionEnabled = YES;
    label.numberOfLines = 0;
    if (inView) {
        [inView addSubview:label];
    }

    return label;
}


- (NSMutableArray *)starArrayM {
    if (!_starArrayM) {
        _starArrayM = [NSMutableArray arrayWithCapacity:5];
        
        for (int i = 0; i < 5; i++) {
            UIButton *starBtn = [UIButton buttonWithType:UIButtonTypeCustom];
            starBtn.tag = i;
            [starBtn setImage:[UIImage imageNamed:@"评分镂空"] forState:UIControlStateNormal];
            [starBtn setImage:[UIImage imageNamed:@"评分"] forState:UIControlStateSelected];
            [self.contentView addSubview:starBtn];
            
            [_starArrayM addObject:starBtn];
        }
    }
    return _starArrayM;
}

@end
