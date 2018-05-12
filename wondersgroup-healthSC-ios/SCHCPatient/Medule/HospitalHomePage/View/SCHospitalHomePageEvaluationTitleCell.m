//
//  SCHospitalHomePageEvaluationTitleCell.m
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/1.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "SCHospitalHomePageEvaluationTitleCell.h"

@interface SCHospitalHomePageEvaluationTitleCell()

@property (nonatomic, strong) UIView *topGrayView;
@property (nonatomic, strong) UILabel *titleLabel;
@property (nonatomic, strong) UILabel *moreLabel;

@end

@implementation SCHospitalHomePageEvaluationTitleCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupSubviews];
        [self setupSubviewsLayout];
        [self bind];
        self.lineTopHidden = YES;
        self.lineBottomHidden = YES;
    }
    return self;
}



#pragma mark - Bind

- (void)bind {
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] init];
    [_moreLabel addGestureRecognizer:tap];
    
    @weakify(self);
    [[tap rac_gestureSignal] subscribeNext:^(id x) {
        @strongify(self);
        if (self.moreTapHandler) {
            self.moreTapHandler();
        }
    }];
    
    
    [RACObserve(self, model) subscribeNext:^(SCHospitalHomePageModel *x) {
        _titleLabel.text = [NSString stringWithFormat:@"医院评价(%@)", x.evaluateCount];
        _moreLabel.hidden = !([x.evaluateCount integerValue] > 5);

        
        /// 更改版本: 1.0
        /// 修改人员: Joseph Gao
        /// 修改描述: 暂时别删
//        _moreLabel.attributedText = [self moreLabelAttributedStrByHasMoreString:([x.evaluateCount integerValue] > 5)];
    }];
}


#pragma mark - Setup UI

- (void)setupSubviews {
    _topGrayView = [[UIView alloc] init];
    _topGrayView.backgroundColor = [UIColor bc2Color];
    [self.contentView addSubview:_topGrayView];
    
    _titleLabel = [self creatLabelInView:self.contentView fontSize:15 textColor:[UIColor tc1Color] aligntment:NSTextAlignmentLeft];
    _moreLabel = [self creatLabelInView:self.contentView fontSize:14 textColor:[UIColor tc3Color] aligntment:NSTextAlignmentRight];
 
    _titleLabel.text = @"医院评价(0)";
    _moreLabel.attributedText = [self moreLabelAttributedStrByHasMoreString:NO];
}


- (NSAttributedString *)moreLabelAttributedStrByHasMoreString:(BOOL)hasMore {
    NSMutableAttributedString *attrStrM = nil;
    attrStrM = [[NSMutableAttributedString alloc] initWithString:@"更多  "];
    
    /// 更改版本: 1.0
    /// 修改人员: Joseph Gao
    /// 修改描述: 暂时别删
    
//    if (hasMore) {
//        attrStrM = [[NSMutableAttributedString alloc] initWithString:@"更多  "];
//    }
//    else {
//        attrStrM = [[NSMutableAttributedString alloc] initWithString:@"     "];
//    }
    
    NSTextAttachment *attach = [[NSTextAttachment alloc] init];
    UIImage *img = [UIImage imageNamed:@"back"];
    attach.image = img;
    attach.bounds = CGRectMake(0, -1, 7, 14);
    NSAttributedString *attachStr = [NSMutableAttributedString attributedStringWithAttachment:attach];
    
    [attrStrM appendAttributedString:attachStr];
    
    return attrStrM;
}


- (void)setupSubviewsLayout {
    [_topGrayView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.contentView);
        make.height.mas_equalTo(10);
    }];
    
    [_titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.offset(25);
        make.left.offset(15);
        make.bottom.offset(-5);
    }];
    
    [_moreLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(_titleLabel);
        make.right.offset(-15);
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
    if (inView) {
        [inView addSubview:label];
    }
    label.userInteractionEnabled = YES;
    
    return label;
}


@end
