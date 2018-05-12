//
//  HotSearchViewModel.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "HotSearchKeyWordModel.h"
#import "SCSearchModel.h"
#import "HomeSearchViewController.h"
#import "SearchHistoryWithHospitalModel.h"
@interface HotSearchViewModel : BaseViewModel
@property (nonatomic,strong)NSArray * array;
@property (nonatomic,strong)NSArray * recommendArr;//搜索关联数据

@property (nonatomic,strong)NSMutableArray < SCSearchModel * > * historyArray;
@property (nonatomic,strong)NSMutableArray < SearchHistoryWithHospitalModel * > * historyArrayWithHospital;
@property(nonatomic,strong)HotSearchKeyWordModel * model;
- (void)getHotSearchKeyWord: (void(^)(void))success
                         failure: (void(^)(void))failure;
- (void)getSearchRecommendList:(NSString * )keyword  success:(void(^)(void))success
                    failure: (void(^)(void))failure;

-(void)reloadSection;
-(void)refreshDateWithType:(ViewContrllerType)type;
@end
