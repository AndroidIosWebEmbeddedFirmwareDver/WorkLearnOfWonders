//
//  cuzZButton.m
//  SZCirculationImage
//
//  Created by Wonders_iOS on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "cuzZButton.h"
#import "View+MASAdditions.h"
//Masnory  弱引用
#define WS(weakSelf)  __weak __typeof(&*self)weakSelf = self;
@implementation cuzZButton
-(id)init{
    if (self = [super init]) {
        self.buttonImage = [UIImageView new];
        WS(weakSelf)
        [self addSubview:self.buttonImage];
        [self.buttonImage mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerX.equalTo(weakSelf);
            make.top.equalTo(weakSelf).offset(12);
            make.height.mas_equalTo(41);
            make.width.mas_equalTo(36);
        }];
        self.buttonTitle = [UILabel new];
        [self addSubview:self.buttonTitle];
        self.buttonTitle.font = [UIFont systemFontOfSize:16];
        self.buttonTitle.textAlignment = NSTextAlignmentCenter;
        self.buttonTitle.textColor = [UIColor tc2Color];
        [self.buttonTitle mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(self.buttonImage.mas_bottom).offset(8);
            make.centerX.equalTo(weakSelf);
            make.bottom.equalTo(weakSelf).offset(-11);
            make.width.equalTo(weakSelf);
        }];
        [self bindModel];
    }
    return self;

}
-(void)bindModel{
WS(weakSelf)
    
    [RACObserve(weakSelf,imageName) subscribeNext:^(NSString  * x) {
            if (x) {
                if([x rangeOfString:@"http"].location !=NSNotFound){
                    [self.buttonImage sd_setImageWithURL:[NSURL URLWithString:x] placeholderImage:[UIImage imageNamed:@"家庭医生默认"]];

                }else{
                    self.buttonImage.image = [UIImage imageNamed:x];
                }

            }
        }];
    [RACObserve(weakSelf,titleStr) subscribeNext:^(NSString  * x) {
        if (x) {
            self.buttonTitle.text = x;
        }
    }];

}
//-(void)initWithButtonImageName:(NSString *)imageName andButtonTitle:(NSString *)titleStr{
//    [self.buttonImage sd_setImageWithURL:[NSURL URLWithString:imageName]];
////    self.buttonImage.image = [UIImage imageNamed:imageName];
//    self.buttonTitle.text = titleStr;
//}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
