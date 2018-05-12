//
//  YSButton.m
//  图文上下的按钮
//
//  Created by Joseph on 15/10/8.
//  Copyright © 2015年 Joseph. All rights reserved.
//

#import "YSButton.h"

@implementation YSButton

-(id)init{
    if (self = [super init]) {
         WS(weakSelf)
        
        self.buttonTitle = [UILabel new];
        [self addSubview:self.buttonTitle];
        self.buttonTitle.font = [UIFont systemFontOfSize:14];
        [self.buttonTitle mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(weakSelf);
            make.bottom.equalTo(weakSelf);
            make.left.equalTo(weakSelf).offset(5);
            make.right.equalTo(weakSelf).offset(-7);
        }];
        self.buttonImage = [UIImageView new];
    
        
        [self addSubview:self.buttonImage];
        [self.buttonImage mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerY.equalTo(weakSelf);
            make.height.mas_equalTo(2);
            make.width.mas_equalTo(4);
            make.left.equalTo(weakSelf.buttonTitle.mas_right).offset(3);
        }];
        
        
    }
    return self;
    
//    [self bindText];
    
}
-(void)bindText{
    WS(weakSelf)
[RACObserve(weakSelf, buttonTitle.text) subscribeNext:^(NSString* x) {
    
    NSInteger length = x.length;
    if (length>3) {
        length=3;
    }
    self.buttonTitle.text = x;
    
    [self.buttonTitle mas_updateConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf);
        make.bottom.equalTo(weakSelf);
        make.left.equalTo(weakSelf).offset(5);
        //        make.right.equalTo(weakSelf).offset(-7);
        make.width.mas_equalTo(15 * length);
    }];
    
}];



}
-(void)initWithButtonImageName:(NSString *)imageName andButtonTitle:(NSString *)titleStr{
    NSInteger length = titleStr.length;
    if (length>3) {
        length=3;
    }
    self.buttonImage.image = [UIImage imageNamed:imageName];
    self.buttonTitle.text = titleStr;
    WS(weakSelf)
    [self.buttonTitle mas_updateConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf);
        make.bottom.equalTo(weakSelf);
        make.left.equalTo(weakSelf).offset(5);
//        make.right.equalTo(weakSelf).offset(-7);
        make.width.mas_equalTo(15 * length);
    }];
}


//-(void)layoutSubviews {
//    [super layoutSubviews];
//    //Center text
//    CGRect newFrame = [self titleLabel].frame;
//    newFrame.origin.x = 0;
//    newFrame.origin.y = 0;
//    newFrame.size.height = self.frame.size.height;
//    newFrame.size.width = self.frame.size.width - 9;
//    
//    self.titleLabel.frame = newFrame;
//    self.titleLabel.textAlignment = NSTextAlignmentCenter;
//    
//    // Center image
//    self.imageView.frame = CGRectMake(0,0, 4, 2);
//    CGPoint center = self.imageView.center;
//    center.x = self.titleLabel.frame.size.width + 2 + 10;
//    center.y = self.frame.size.height/2;
//    self.imageView.center = center;
//    
//
//}
//
//- (void)setTitle:(nullable NSString *)title forState:(UIControlState)state {
//    [super setTitle:title forState:state];
//    [self.titleLabel sizeToFit];
//}
//
//
//- (void)setImage:(nullable UIImage *)image forState:(UIControlState)state {
//    [super setImage:image forState:state];
//    [self.imageView sizeToFit];
//}

@end
