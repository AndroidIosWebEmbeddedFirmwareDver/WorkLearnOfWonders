//
//  WDBannerCell.m
//  SHHealthCloudNormal
//
//  Created by 杜凯 on 16/8/5.
//  Copyright © 2016年 WondersGroup. All rights reserved.
//

#import "WDBannerCell.h"
#import "SDCycleScrollView.h"
@interface WDBannerCell ()<SDCycleScrollViewDelegate>
/** 轮播图 */
@property (nonatomic, strong) SDCycleScrollView *cycleScrollView;
@end

@implementation WDBannerCell

- (void)awakeFromNib {
    // Initialization code
     [super awakeFromNib];
}
- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        _cycleScrollView = [SDCycleScrollView cycleScrollViewWithFrame:CGRectZero
                                                              delegate:self
                                                      placeholderImage:[UIImage imageNamed:@"加载失败750-375"]];
        //_cycleScrollView.bannerImageViewContentMode = UIViewContentModeScaleAspectFill;
        [self.contentView addSubview:_cycleScrollView];
        _cycleScrollView.autoScrollTimeInterval = 3.0;
        _cycleScrollView.frame=CGRectMake(0, 0, SCREEN_WIDTH,AdaptiveFrameHeight(375/2.0));
        _cycleScrollView.autoScrollTimeInterval = 3.0;
        self.selectionStyle = UITableViewCellSelectionStyleNone;
//        [_cycleScrollView mas_makeConstraints:^(MASConstraintMaker *make) {
//            make.edges.equalTo(self.contentView);
//        }];
    }
    
    return self;
}

- (void)setBannerDataArray:(NSArray *)bannerDataArray {
    _bannerDataArray = bannerDataArray;
    
    NSMutableArray *imgUrlArrayM = [NSMutableArray arrayWithCapacity:bannerDataArray.count];
    for (int i = 0; i < bannerDataArray.count; i++) {
        BannersModel *bannerData = bannerDataArray[i];
        if (bannerData.imgUrl == nil) {
            bannerData.imgUrl = @"";
        }
        [imgUrlArrayM addObject:bannerData.imgUrl];
    }
    
    _cycleScrollView.imageURLStringsGroup = imgUrlArrayM.copy;
}


#pragma mark - SDCycleScrollView Delegate

/** 点击图片回调 */
- (void)cycleScrollView:(SDCycleScrollView *)cycleScrollView didSelectItemAtIndex:(NSInteger)index {
    BannersModel *bannerData = self.bannerDataArray[index];
    if (self.bannerClickedHandler) {
        self.bannerClickedHandler(bannerData);
    }
}
- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}




@end
