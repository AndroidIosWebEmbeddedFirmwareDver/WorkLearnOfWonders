//
//  SCNearbyHospitalNoDataNetView.h
//  SCHCPatient
//
//  Created by Gu Jiajun on 16/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger, NearbyHospitalDataType) {
    NearbyNoData,        //展示空页面
    NearbyNoWIFI,       //展示没有网络
    
};

//重新加载
typedef void(^NearbyHospitalBlock)(void);

@interface SCNearbyHospitalNoDataNetView : UIView

@property (nonatomic, assign) NearbyHospitalDataType type;
@property (nonatomic, copy) NearbyHospitalBlock block;

@end
