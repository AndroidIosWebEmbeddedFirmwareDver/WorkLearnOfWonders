//
//  ProfileHeadView.m
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ProfileHeadView.h"
#import <Accelerate/Accelerate.h>

@interface ProfileHeadView ()

@property (nonatomic, strong) UIImageView *backImageView;
@property (nonatomic, strong) UIImageView *photoImage;
@property (nonatomic, strong) UILabel *nameLabel;
@property (nonatomic, strong) UILabel *jobLabel;
@property (nonatomic, strong) UILabel *leaderLabel;

@end

@implementation ProfileHeadView

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
    
    self.contentView.backgroundColor = [UIColor clearColor];
    self.clipsToBounds = YES;

    self.backImageView = [UISetupView setupImageViewWithSuperView:self.contentView withImageName:@""];
    self.backImageView.contentMode = UIViewContentModeScaleAspectFill;
    [self.backImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.top.width.equalTo(self.contentView);
        make.bottom.equalTo(self.contentView).offset(50);
    }];
    
    [self coreBlurImage:[UIImage imageNamed:@"默认用户男164去白边"] withBlurNumber:4 withMaskImageView:self.backImageView];
    
    UIImageView *backLayerImageView = [UISetupView setupImageViewWithSuperView:self.contentView withImageName:@""];
    backLayerImageView.backgroundColor = [UIColor bc7Color];
    backLayerImageView.alpha = 0.5;
    [backLayerImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.width.height.equalTo(self.contentView);
    }];

    self.photoImage = [UISetupView setupImageViewWithSuperView:self.contentView withImageName:@"默认用户男164去白边"];
    [self.photoImage setborderWithColor:[UIColor bc1Color] withWidth:1];
    [self.photoImage setCornerRadius:42];
    [self.photoImage mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.contentView);
        make.top.equalTo(self.contentView).offset(56);
        make.size.mas_equalTo(CGSizeMake(82, 82));
    }];
    
    self.nameLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc0Color] withFontSize:18];
    self.nameLabel.textAlignment = NSTextAlignmentCenter;
    [self.nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.contentView);
        make.left.equalTo(self.contentView).offset(15);
        make.top.equalTo(self.photoImage.mas_bottom).offset(10);
    }];
    
    self.jobLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc0Color] withFontSize:14];
    self.jobLabel.textAlignment = NSTextAlignmentCenter;
    [self.jobLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.contentView);
        make.left.equalTo(self.contentView).offset(15);
        make.top.equalTo(self.nameLabel.mas_bottom).offset(10);
    }];
    
    self.leaderLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"组长" withTextColor:[UIColor tc0Color] withFontSize:12];
    self.leaderLabel.textAlignment = NSTextAlignmentCenter;
    [self.leaderLabel setborderWithColor:[UIColor tc0Color] withWidth:0.5];
    [self.leaderLabel setCornerRadius:7];
    self.leaderLabel.hidden = YES;
    
    UIImageView *imageView = [UISetupView setupImageViewWithSuperView:self.contentView withImageName:@"头部背景波浪形"];
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.bottom.right.equalTo(self.contentView);
        make.height.mas_equalTo(37);
    }];
}

- (void)bindRac {
    
    WS(weakSelf)
    [RACObserve(self, model) subscribeNext:^(UserInfoModel *x) {
        if (x) {
            weakSelf.nameLabel.text = x.name;
            weakSelf.jobLabel.text = x.mobile;
            if (/* DISABLES CODE */ (1)) {
                weakSelf.leaderLabel.hidden = NO;
                weakSelf.nameLabel.textAlignment = NSTextAlignmentRight;
                [weakSelf.nameLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
                    make.right.equalTo(weakSelf.contentView).offset(-SCREEN_WIDTH/2.0-2.5);
                    make.left.equalTo(weakSelf.contentView).offset(15);
                    make.top.equalTo(self.photoImage.mas_bottom).offset(10);
                }];

                [weakSelf.leaderLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
                    make.left.equalTo(weakSelf.nameLabel.mas_right).offset(5);
                    make.centerY.equalTo(weakSelf.nameLabel);
                    make.width.mas_equalTo(34);
                }];
            }else {
                weakSelf.leaderLabel.hidden = YES;
                weakSelf.nameLabel.textAlignment = NSTextAlignmentCenter;
                [weakSelf.nameLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
                    make.centerX.equalTo(self.contentView);
                    make.left.equalTo(self.contentView).offset(15);
                    make.top.equalTo(self.photoImage.mas_bottom).offset(10);
                }];
            }
        }
    }];
}

-(void)coreBlurImage:(UIImage *)image
      withBlurNumber:(CGFloat)blur
   withMaskImageView:(UIImageView *)coreImgv{
    
    dispatch_async(dispatch_get_global_queue(0, 0), ^{
        CIContext *context = [CIContext contextWithOptions:nil];
        CIImage  *inputImage=[CIImage imageWithCGImage:image.CGImage];
        CIFilter *filter = [CIFilter filterWithName:@"CIGaussianBlur"];
        [filter setValue:inputImage forKey:kCIInputImageKey];
        [filter setValue:@(blur) forKey: @"inputRadius"];
        CIImage *result=[filter valueForKey:kCIOutputImageKey];
        CGImageRef outImage=[context createCGImage:result fromRect:[result extent]];
        UIImage *blurImage=[UIImage imageWithCGImage:outImage];
        CGImageRelease(outImage);
        
        dispatch_async(dispatch_get_main_queue(), ^{
            coreImgv.image = blurImage;
        });
    });
}

@end
