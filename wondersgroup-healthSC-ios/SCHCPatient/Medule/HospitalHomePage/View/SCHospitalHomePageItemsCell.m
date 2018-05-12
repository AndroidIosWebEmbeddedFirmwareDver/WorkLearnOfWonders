//
//  SCHospitalHomePageItemsCell.m
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/1.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "SCHospitalHomePageItemsCell.h"

#define SIX_STRING_WIDTH [@"一二三四五六" boundingRectWithSize:CGSizeMake(CGFLOAT_MAX, CGFLOAT_MAX) \
                                        options:NSStringDrawingUsesLineFragmentOrigin| NSStringDrawingUsesFontLeading \
                                        attributes:@{NSFontAttributeName : [UIFont systemFontOfSize:13]} \
                                        context:nil].size.width

@interface ItemView : UIView

@property (nonatomic, strong) UIImageView *iconView;
@property (nonatomic, strong) UILabel *titleLabel;
@property (nonatomic, strong) UILabel *descLabel;
@property (nonatomic, strong) UIImageView *closeSign;

@property (nonatomic, copy) NSString *iconName;
@property (nonatomic, copy) NSString *title;
@property (nonatomic, copy) NSString *desc;
@property (nonatomic, assign, getter=isClosed) BOOL closed;

@property (nonatomic, copy) ItemViewTapHandle tapHandler;


+ (instancetype)viewWithIconName:(NSString *)iconName
                           title:(NSString *)title
                            desc:(NSString *)desc
                             tag:(NSInteger)tag
                          inView:(UIView *)inView;

@end


@implementation ItemView

+ (instancetype)viewWithIconName:(NSString *)iconName
                           title:(NSString *)title
                            desc:(NSString *)desc
                             tag:(NSInteger)tag
                          inView:(UIView *)inView {
    return [[self alloc] initWithIconName:iconName
                                    title:title
                                     desc:desc
                                      tag:tag
                                   inView:inView];
}


- (instancetype)initWithIconName:(NSString *)iconName
                           title:(NSString *)title
                            desc:(NSString *)desc
                             tag:(NSInteger)tag
                          inView:(UIView *)inView {
    if (self = [super init]) {
        _iconName = iconName;
        _title = title;
        _desc = desc;
        self.tag = tag;
        self.backgroundColor = [UIColor whiteColor];
        
        [self setupSubviews];
        [self setupSubviewsLayout];
        [self addTapGesture];
        
        [inView addSubview:self];
    }
    return self;
}


- (void)addTapGesture {
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] init];
    [self addGestureRecognizer:tap];
    
    @weakify(self);
    [[tap rac_gestureSignal] subscribeNext:^(id x) {
        @strongify(self);
        if (self.tapHandler) {
            self.tapHandler(self.tag);
        }
    }];
    
    [RACObserve(self, closed) subscribeNext:^(NSNumber *x) {
        self.closeSign.hidden       = ![x boolValue];
        self.userInteractionEnabled = ![x boolValue];
    }];
    
}


- (void)setupSubviews {
    _iconView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:_iconName]];
    _iconView.userInteractionEnabled = YES;
    [self addSubview:_iconView];
    
    _titleLabel = [[UILabel alloc] init];
    _titleLabel.font = [UIFont systemFontOfSize:16];
    _titleLabel.textColor = [UIColor tc1Color];
    _titleLabel.text = _title;
    [self addSubview:_titleLabel];
    
    
    _descLabel = [[UILabel alloc] init];
    _descLabel.font = [UIFont systemFontOfSize:12];
    _descLabel.textColor = [UIColor tc2Color];
    _descLabel.text = _desc;
    [self addSubview:_descLabel];
    
    _closeSign = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"img暂未开通"]];
    _closeSign.userInteractionEnabled = YES;
    [self addSubview:_closeSign];
}


- (void)setupSubviewsLayout {
    [_iconView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.offset(11);
        make.width.height.mas_equalTo(32);
        make.left.offset(16);
    }];
    
    [_titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_iconView.mas_right).offset(18);
        make.top.offset(12.5);
        make.right.equalTo(self);
    }];
    

    [_descLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_titleLabel);
        make.right.equalTo(self).offset(-10);
        make.top.equalTo(_titleLabel.mas_bottom).offset(8);
//        make.width.mas_equalTo(SIX_STRING_WIDTH);
    }];
    
    UIImage *img = [UIImage imageNamed:@"img暂未开通"];
    [_closeSign mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.right.equalTo(self);
        make.size.mas_equalTo(img.size);
    }];
}



@end


static NSInteger const kBaseTagValue = 888;
static CGFloat const kItemHeight = 65;

@interface SCHospitalHomePageItemsCell()

@property (nonatomic, strong) NSMutableArray *itemsArrayM;

@end


@implementation SCHospitalHomePageItemsCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupSubviews];
        [self setupSubviewsLayout];
        [self bind];
        
        self.lineTopHidden = YES;
        self.lineBottomHidden = YES;
        self.backgroundColor = [UIColor bc2Color];
    }
    return self;
}



#pragma mark - Bind

- (void)bind {
    [self.itemsArrayM enumerateObjectsUsingBlock:^(ItemView * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        obj.tapHandler = ^(NSInteger tag){
            if (self.tapHandler) {
                self.tapHandler(tag - kBaseTagValue);
            }
        };
        
//        if (idx == 3) {
//            obj.closed = YES;
//        }
    }];
    
}


#pragma mark - Setup UI

- (void)setupSubviews {
    [self.itemsArrayM addObject:[ItemView viewWithIconName:@"icon医院"
                                                     title:@"医院概况"
                                                      desc:@"查看医院简介"
                                                       tag:kBaseTagValue + 0
                                                    inView:self.contentView]];
    [self.itemsArrayM addObject:[ItemView viewWithIconName:@"Hospital_HomePage_Register"
                                                     title:@"预约挂号"
                                                      desc:@"提前预约先人一步"
                                                       tag:kBaseTagValue + 1
                                                    inView:self.contentView]];
    [self.itemsArrayM addObject:[ItemView viewWithIconName:@"icon医生"
                                                     title:@"科室医生"
                                                      desc:@"一览医生擅长"
                                                       tag:kBaseTagValue + 2
                                                    inView:self.contentView]];
    [self.itemsArrayM addObject:[ItemView viewWithIconName:@"icon智能导诊"
                                                     title:@"智能导诊"
                                                      desc:@"症状自测找对医生"
                                                       tag:kBaseTagValue + 3
                                                    inView:self.contentView]];
    
}


- (void)setupSubviewsLayout {
    [self.itemsArrayM enumerateObjectsUsingBlock:^(ItemView * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        NSInteger totalCul = 2;
        NSInteger row = idx / totalCul;
        NSInteger cul = idx % totalCul;
        CGFloat w = [UIScreen mainScreen].bounds.size.width * 0.5;
        CGFloat h = kItemHeight;
        CGFloat offsetX = w * cul;
        CGFloat offsetY = h * row + 10;
       [obj mas_makeConstraints:^(MASConstraintMaker *make) {
           make.left.equalTo(self.contentView).offset(offsetX);
           make.top.equalTo(self.contentView).offset(offsetY);
           make.size.mas_equalTo(CGSizeMake(w, h));
       }];
    }];
    
    
    UIView *hLine = [self creatLine];
    [hLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.height.mas_equalTo(0.5);
        make.left.right.offset(0);
        make.bottom.offset(-kItemHeight);
    }];
    
    UIView *vLine = [self creatLine];
    [vLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.width.mas_equalTo(0.5);
        make.top.offset(-10);
        make.bottom.offset(0);
        make.centerX.offset(0);
    }];
    
}



#pragma mark - Other

- (UIView *)creatLine {
    UIView *line = [[UIView alloc] init];
    line.backgroundColor = [UIColor dc4Color];
    [self.contentView addSubview:line];
    
    return line;
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
    
    return label;
}


- (NSMutableArray *)itemsArrayM {
    if (!_itemsArrayM) {
        _itemsArrayM = [NSMutableArray array];
    }
    return _itemsArrayM;
}


- (CGSize)sizeThatFits:(CGSize)size {
    return CGSizeMake(size.width, kItemHeight * 2 + 10);    // 10 : 顶部间距
}
@end






