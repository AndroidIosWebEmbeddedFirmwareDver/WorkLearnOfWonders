//
//  HomeAreaSearchViewController.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"
#import "HomeAreaViewModel.h"
typedef void(^selectedCityBlock)(NSString * cityName,NSString * cityCode);

@interface HomeAreaSearchViewController : BaseViewController

@property (nonatomic, strong) HomeAreaViewModel *viewModel;
@property (nonatomic,strong)selectedCityBlock block;

@end
