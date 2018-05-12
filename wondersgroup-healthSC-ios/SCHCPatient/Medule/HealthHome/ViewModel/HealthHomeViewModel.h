//
//  HealthHomeViewModel.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "HHInformationModel.h"
#import "HHInformationListModel.h"
#import "BannersModel.h"
#import "FunctionModel.h"

typedef NS_ENUM(NSUInteger, HealthHomeLayoutType) {
    
    HealthHomeLayoutTypeRotatingImage,      //顶部轮播图
    HealthHomeLayoutTypeFunction,           //功能按钮入口
//    HealthHomeLayoutTypeInfoHeader,         //
    HealthHomeLayoutTypeInformation,        //下面资讯
};


@interface HealthHomeViewModel : BaseViewModel <HealthHomeIMPL>

@property (nonatomic, strong) NSMutableArray *layoutArray;


//banner数组
@property (nonatomic, strong) NSArray<BannersModel *> *bannerArray;

//function数组
@property (nonatomic, strong) NSArray<FunctionModel *> *functionArray;

//资讯数组
@property (nonatomic, strong) NSArray<HHInformationListModel *> *dataArray;


- (void)getDBData;

@end


@interface HealthHomeLayoutModel : NSObject

@property (nonatomic, assign) HealthHomeLayoutType type;
@property (nonatomic, assign) CGFloat cellHeight;

@end
