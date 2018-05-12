//
//  WDProfileHeadView.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDProfileHeadView.h"

@interface WDProfileHeadView()

@property (nonatomic, strong) UIImageView *backgroundImageView;
@property (nonatomic, strong) UILabel *nameLabel;
@property (nonatomic, strong) UIImageView *headImageView;
@property (nonatomic, strong) UIImageView *styleImageView;

@property(nonatomic, assign) BOOL enableS;

@end

@implementation WDProfileHeadView



- (instancetype)init {
    self = [super init];
    if (self) {
        [self setupView];
        [self bindData];
    }
    return self;
}

- (void)setupView {
        WS(weakSelf);
    _backgroundImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"个人中心背景"]];
    [self addSubview:_backgroundImageView];
    [_backgroundImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(weakSelf);
    }];
    
    _nameLabel = [UILabel new];
    _nameLabel.textColor = [UIColor tc0Color];
    _nameLabel.font = [UIFont systemFontOfSize:18];
    _nameLabel.textAlignment = NSTextAlignmentCenter;
    if ([UserManager manager].verificationStatus == 3) {
        _nameLabel.text = [UserManager manager].name;
    } else {
        _nameLabel.text = [UserManager manager].nickname;
    }
    [self addSubview:_nameLabel];

    [_nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(weakSelf);
        make.top.equalTo(weakSelf).offset(35);
        make.width.mas_equalTo(140);
    }];
    
    _headImageView = [UIImageView new];
    [_headImageView sd_setImageWithURL:[NSURL URLWithString:[UserManager manager].avatar] placeholderImage:[UIImage imageNamed:@"默认用户女164"]];
    _headImageView.layer.masksToBounds = YES;
    _headImageView.layer.cornerRadius = 40;
    [self addSubview:_headImageView];
    [_headImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(weakSelf);
        make.top.equalTo(_nameLabel.mas_bottom).offset(24);
        make.width.height.mas_offset(80);
    }];
    
    _styleImageView = [UIImageView new];
    _styleImageView.image = [UIImage imageNamed:@"img实名认证-未认证"];
    [self addSubview:_styleImageView];
    [_styleImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.bottom.equalTo(_headImageView).offset(-5);
        make.width.height.mas_equalTo(16);
    }];
    
    UIButton *goButton = [UIButton new];
    [goButton addTarget:self action:@selector(doSome) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:goButton];
    [goButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(_headImageView);
    }];
    
}

- (void)bindData {
    
    RACSignal *avatarSignal =  [RACSignal
                                combineLatest:@[RACObserve([UserManager manager], avatar), RACObserve([UserManager manager], gender)]
                                reduce:^(id ava, id gen) {
                                    return @(YES);
                                }];
    
    [avatarSignal subscribeNext:^(id x) {
        if ([UserManager manager].gender == 2) {
            [_headImageView sd_setImageWithURL:[NSURL URLWithString:[UserManager manager].avatar] placeholderImage:[UIImage imageNamed:@"默认用户女164"]];
        } else {
            [_headImageView sd_setImageWithURL:[NSURL URLWithString:[UserManager manager].avatar] placeholderImage:[UIImage imageNamed:@"默认用户男164"]];
        }
    }];
    
//    [RACObserve([UserManager manager], gender) subscribeNext:^(id x) {
//        if ([UserManager manager].gender == 2) {
//            [_headImageView sd_setImageWithURL:[NSURL URLWithString:[UserManager manager].avatar] placeholderImage:[UIImage imageNamed:@"默认用户女164"]];
//        } else {
//            [_headImageView sd_setImageWithURL:[NSURL URLWithString:[UserManager manager].avatar] placeholderImage:[UIImage imageNamed:@"默认用户男164"]];
//        }
//    }];
//    
//    [RACObserve([UserManager manager], avatar) subscribeNext:^(id x) {
//        if ([UserManager manager].gender == 2) {
//            [_headImageView sd_setImageWithURL:[NSURL URLWithString:[UserManager manager].avatar] placeholderImage:[UIImage imageNamed:@"默认用户女164"]];
//        } else {
//            [_headImageView sd_setImageWithURL:[NSURL URLWithString:[UserManager manager].avatar] placeholderImage:[UIImage imageNamed:@"默认用户男164"]];
//        }
//    }];
    
//    [RACObserve([UserManager manager], isLogin) subscribeNext:^(id x) {
//        
//    }];
    
//    WS(weakSelf)
    RACSignal *nameSignal =  [RACSignal
                              combineLatest:@[RACObserve([UserManager manager], isLogin), RACObserve([UserManager manager], name),
                                              RACObserve([UserManager manager], nickname), RACObserve([UserManager manager], verificationStatus)]
                              reduce:^(NSNumber *isLogin, NSString *name, NSString *nickName, NSNumber *stauts) {
                                  NSString *showName = name;
                                  if (![isLogin boolValue]) {
                                      showName = @"";
                                  }
                                  else {
                                      if ([stauts intValue] != 3) {
//                                          NSString *phone = [[UserManager manager].mobile substringFromIndex:7];
                                          showName = nickName;//? nickName : [NSString stringWithFormat:@"微健康用户%@", phone];
                                      }
                                  }
                                  return showName;
                              }];

    RAC(self.nameLabel, text) = nameSignal;
    
//    if ([UserManager manager].verificationStatus == 3) {
//        weakSelf.styleImageView.image = [UIImage imageNamed:@"img实名认证-已认证"];
//    } else {
//        weakSelf.styleImageView.image = [UIImage imageNamed:@"img实名认证-未认证"];
//    }
//
//    [RACObserve([UserManager manager], name) subscribeNext:^(id x) {
//        if ([UserManager manager].verificationStatus == 3) {
//            _nameLabel.text = [UserManager manager].name;
//        } else {
//            if([UserManager manager].isLogin) {
//                if ([UserManager manager].nickname) {
//                    _nameLabel.text = [UserManager manager].nickname;
//                } else {
//                    NSString *phone = [[UserManager manager].mobile substringFromIndex:7];
//                    _nameLabel.text = [NSString stringWithFormat:@"微健康用户%@", phone];
//                }
//            } else {
//                _nameLabel.text=@"";
//            }
//        }
//    }];
//    
//    [RACObserve([UserManager manager], nickname) subscribeNext:^(id x) {
//        if ([UserManager manager].verificationStatus == 3) {
//            _nameLabel.text = [UserManager manager].name;
//        } else {
//            _nameLabel.text = [UserManager manager].nickname;
//        }
//    }];
    
    [RACObserve([UserManager manager], verificationStatus) subscribeNext:^(id x) {
        if ([UserManager manager].verificationStatus == 3 && [UserManager manager].isLogin) {
            _styleImageView.image = [UIImage imageNamed:@"img实名认证-已认证"];
        } else {
            _styleImageView.image = [UIImage imageNamed:@"img实名认证-未认证"];
        }
    }];
    

}

- (void)doSome {
    _goBlock();
}



@end
