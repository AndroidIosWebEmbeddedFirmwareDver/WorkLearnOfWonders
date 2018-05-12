//
//  HotSearchCell.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HotSearchCell.h"
#import "HotSearchCollectionViewCell.h"
#import "HotSearchKeyWordModel.h"
#import "HotSearchViewModel.h"
#define HOTCOLLECCELL @"HotSearchCollectionViewCell"
@interface HotSearchCell()<UICollectionViewDelegate,UICollectionViewDataSource>
@property(nonatomic,strong)HotSearchViewModel * viewModel;
@end
@implementation HotSearchCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    if (self = [super initWithStyle: style reuseIdentifier:reuseIdentifier]) {

        [self createView];
    }
    return self;

}

-(HotSearchViewModel *)viewModel{
    if (!_viewModel) {
        _viewModel = [HotSearchViewModel new];
    }
    return _viewModel;
}

-(void)createView{
    UICollectionViewFlowLayout * flow = [[UICollectionViewFlowLayout alloc]init];
    
    flow.minimumInteritemSpacing = 0;
    flow.minimumLineSpacing = 0;
    flow.sectionInset = UIEdgeInsetsZero;
    flow.scrollDirection = UICollectionViewScrollDirectionVertical;
    
     UICollectionView * collecView = [[UICollectionView alloc]initWithFrame:CGRectMake(0,0,SCREEN_WIDTH, self.frame.size.height) collectionViewLayout:flow];
    collecView.backgroundColor = [UIColor bc2Color];
    [self.contentView addSubview:collecView];
    WS(weakSelf)
    [collecView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.bottom.equalTo(weakSelf);
    }];
    collecView.delegate = self;
    collecView.dataSource = self;
    collecView.showsHorizontalScrollIndicator = NO;
    collecView.showsVerticalScrollIndicator = NO;
    collecView.pagingEnabled = NO;
    [collecView registerClass:[HotSearchCollectionViewCell class] forCellWithReuseIdentifier:HOTCOLLECCELL];
    

}
- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section{
    return 8;
    return self.keyWordsArray.count;
}
- (__kindof UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
    HotSearchCollectionViewCell * cell = [collectionView dequeueReusableCellWithReuseIdentifier:HOTCOLLECCELL forIndexPath:indexPath];
    NSString * keyWord =  self.viewModel.array[indexPath.row];
    cell.titleLab.text = keyWord;
//    cell.titleLab.text = @"神经系统..";
    cell.layer.cornerRadius = 6.0f;
    return cell;
}
- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView{
    return 1;
}
-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath{
    if (self.block) {
        self.block(indexPath.row);
    }
}
-(CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath{
    
    
    return CGSizeMake((SCREEN_WIDTH-126/2)/3,30);
    
}
- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout minimumLineSpacingForSectionAtIndex:(NSInteger)section{
    return 8;

}
- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout minimumInteritemSpacingForSectionAtIndex:(NSInteger)section{
    return 33/4;

}
//-(CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout referenceSizeForHeaderInSection:(NSInteger)section{
//    
//    
//    return CGSizeMake([UIScreen mainScreen].bounds.size.width,618* kScale_750 + 14* kScale_750-20);
//}
@end
