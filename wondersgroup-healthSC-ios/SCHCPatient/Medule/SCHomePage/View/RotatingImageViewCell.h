//
//  RotatingImageViewCell.h
//  SZCirculationImage
//
//  Created by Wonders_iOS on 2016/11/1.
//  Copyright © 2016年 吴三忠. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HomePageModel.h"
#import "SZCirculationImageView.h"
typedef void(^HomeBannerClickedHandle)(BannersModel * rotaData);
@interface RotatingImageViewCell : UITableViewCell
@property (nonatomic, copy) HomeBannerClickedHandle bannerClickedHandler;
@property (nonatomic,strong) NSArray * arrURL;
@property (nonatomic,strong) HomePageModel * model;
@property (nonatomic,strong) SZCirculationImageView * selectImageView;
- (void)setDataWithArray:(NSArray *)banners;
@end
