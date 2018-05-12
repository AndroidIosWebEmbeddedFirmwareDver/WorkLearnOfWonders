//
//  NearbyHospitalMapView.h
//  HCPatient
//
//  Created by Gu Jiajun on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "BaiduMapHead.h"
#import "LocationModel.h"

@interface NearbyHospitalMapView : BMKMapView

@property (nonatomic, strong) NSArray *dataArray;
@property (nonatomic, strong) NSDictionary *centerCoordinateDict;
@property (nonatomic, strong) LocationModel *currentModel;

- (void)setMapViewDelegate;

@end
