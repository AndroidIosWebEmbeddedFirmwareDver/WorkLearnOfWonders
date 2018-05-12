//
//  HomeSearchViewController.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"
#import "SearchHistoryWithHospitalModel.h"
typedef enum HomeSearchType {
    HomeSearchType_Hospital,//医院
    HomeSearchType_Doctor,//医生搜索
    HomeSearchType_Article,//文章
    HomeSearchType_All //全部搜索
} HomeSearchType;
@interface HomeSearchViewController : BaseViewController
/*
 区分---搜索的是医院、文章、医生、全部  -
 */
@property (nonatomic,assign) HomeSearchType type;
/*
 区分---预约挂号、附近就医 搜索跳过来 -
 */
@property (nonatomic,assign) ViewContrllerType viewType;

@end
