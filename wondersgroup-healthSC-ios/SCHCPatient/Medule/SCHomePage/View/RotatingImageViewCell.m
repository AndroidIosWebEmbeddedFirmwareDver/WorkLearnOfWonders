//
//  RotatingImageViewCell.m
//  SZCirculationImage
//
//  Created by Wonders_iOS on 2016/11/1.
//  Copyright © 2016年 吴三忠. All rights reserved.
//

#import "RotatingImageViewCell.h"
#import "SZCirculationImageView.h"
#define UISCREENW  [UIScreen mainScreen].bounds.size.width
#define UISCREENH  [UIScreen mainScreen].bounds.size.height
@implementation RotatingImageViewCell
-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        self.arrURL = [[NSArray alloc]init];
        
//        [self bindViewModel];
//        [self setupUI];
    }
    return self;

}

- (void)setDataWithArray:(NSArray *)banners {
    NSMutableArray * arrayURL = [[NSMutableArray alloc]init];
    for (BannersModel * models in banners) {
        [arrayURL addObject:models.imgUrl];
    }
    if (arrayURL.count>0) {
        self.arrURL = [arrayURL copy];
    }else{
        self.arrURL = @[@"2.jpg"];
    
    }
    
   self.selectImageView = [[SZCirculationImageView alloc]initWithFrame:CGRectMake(0, 0,UISCREENW, 375/2)  andImageURLsArray:self.arrURL andPlaceImage:[UIImage imageNamed:@"2.jpg"]];
    
    self.selectImageView.pauseTime = 3.0;
    [self.contentView addSubview:self.selectImageView];
    
}

-(void)bindViewModel{
   
    WS(weakSelf);
    NSMutableArray * arrayURL = [[NSMutableArray alloc]init];
    
    [RACObserve(self, model.banners) subscribeNext:^(NSArray * banners) {
        for (BannersModel * models in banners) {
            [arrayURL addObject: models.imgUrl];
        }
        
        
        weakSelf.arrURL = [arrayURL copy];
    }];

}
//-(void)setupUI{
//    SZCirculationImageView *imageView1 = [[SZCirculationImageView alloc] initWithFrame:CGRectMake(0, 0,UISCREENW, 375/2) andImageNamesArray:@[@"1.jpg", @"2.jpg",@"6.jpg",@"5.jpg"]];
//    imageView1.pauseTime = 2.0;
//    
//    NSLog(@"_______________%@",self.arrURL);
//    
//    SZCirculationImageView * imageView2 = [[SZCirculationImageView alloc]initWithFrame:CGRectMake(0, 0,UISCREENW, 375/2)  andImageURLsArray:self.arrURL];
//    imageView2.pauseTime = 2.0;
//    [self.contentView addSubview:imageView2];
//}
- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
