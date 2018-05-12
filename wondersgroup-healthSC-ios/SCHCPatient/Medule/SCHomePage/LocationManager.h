//
//  LocationManager.h
//  HCPatient
//
//  Created by luzhongchang on 16/8/22.
//  Copyright © 2016年 ZJW. All rights reserved.
//


/*
 demo  及时定位需求@刘松宏
 
 LocationManager * ma = [LocationManager manager];
 ma.gpsSuccess=^(LocationModel * model)
 {
 
 };
 ma.gpsfailed = ^()
 {
 
 };
 [ma restartLocation];
 
 */

#import <Foundation/Foundation.h>
#import "BaiduMapHead.h"

//#import "BMKManager.h"
@class LocationModel;
typedef void(^GpsSuccess)(LocationModel * model);
typedef void(^GpsFailed)();
typedef void(^CityCoordinateBlock)(CLLocationCoordinate2D coordinate);

@interface LocationManager : NSObject<BMKLocationServiceDelegate ,BMKGeoCodeSearchDelegate,BMKGeneralDelegate>
@property(nonatomic ,strong) BMKMapManager * mapManager;
@property(nonatomic ,strong) BMKLocationService * locService;
@property(nonatomic ,strong) BMKGeoCodeSearch* geocodesearch;
@property(nonatomic, copy) GpsSuccess gpsSuccess;
@property(nonatomic ,copy) GpsFailed gpsfailed;
@property(nonatomic ,assign)  BOOL gpsComplete;

@property(nonatomic ,assign) BOOL reOpen;
@property(nonatomic, strong) BMKUserLocation *userLocation;
@property(nonatomic, assign) BOOL isInSiChuan;

@property(nonatomic ,copy) CityCoordinateBlock cityCoordinateBlock;

+ (LocationManager *)manager;
-(void) startLocation;
/*重新定位*/
-(void) restartLocation;

- (void)getCoordinateWithCity:(NSString *)city;

//判断定位是否开启
+ (BOOL)isEnableLocation;

@end
