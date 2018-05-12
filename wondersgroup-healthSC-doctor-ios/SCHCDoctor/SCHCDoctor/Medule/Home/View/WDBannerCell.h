//
//  WDBannerCell.h
//  SHHealthCloudNormal
//
//  Created by 杜凯 on 16/8/5.
//  Copyright © 2016年 WondersGroup. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BannersModel.h"
typedef void(^HomeBannerClickedHandle)(BannersModel *bannerData);
@interface WDBannerCell : UITableViewCell
/** 轮播图WDBannerData数据集合 */
@property (nonatomic, strong) NSArray *bannerDataArray;

@property (nonatomic, copy) HomeBannerClickedHandle bannerClickedHandler;
@end
