//
//  LocationManager.m
//  HCPatient
//
//  Created by luzhongchang on 16/8/22.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "LocationManager.h"
#import "LocationModel.h"
#import "ManagerAreaDataDao.h"
@implementation LocationManager


+ (LocationManager *)manager
{
    static dispatch_once_t onceToken;
    static LocationManager *shared = nil;
    dispatch_once(&onceToken, ^{
        shared = [[LocationManager alloc] init];
    });
    return shared;
}

-(instancetype)init {
    self = [super init];
    if (self) {
        
        self.gpsComplete=false;
            // 要使用百度地图，请先启动BaiduMapManager
        self.mapManager = [[BMKMapManager alloc]init];
        self.reOpen=false;
      
        BOOL ret = true;
        NSString *identifier = [[NSBundle mainBundle] bundleIdentifier];
        if ([identifier rangeOfString:@"open.user"].location == NSNotFound) {
                //appstroe版本
            ret = [_mapManager start:BAIDU_MAP_APPSTORE_KEY generalDelegate:self];
                //线上版 测试百度地图key
        }else{
                //企业版
            ret = [_mapManager start:BAIDU_MAP_KEY generalDelegate:self];
                //企业版 测试百度地图key
        }
        
        
        if(ret==false)
        {
        NSLog(@"error");
        }
        else
        {
            NSLog(@"ok");
        }
        
        _geocodesearch = [[BMKGeoCodeSearch alloc]init];
        _geocodesearch.delegate = self;
        //初始化BMKLocationService
        _locService = [[BMKLocationService alloc]init];
        //启动LocationService
        _locService.distanceFilter =1000.0;
        _locService. desiredAccuracy = kCLLocationAccuracyBest;
        [_locService startUserLocationService];
        _locService.delegate = self;
    }
    return self;
}

+ (BOOL)isEnableLocation{
    if ([CLLocationManager authorizationStatus] == kCLAuthorizationStatusDenied) {
        NSLog(@"定位服务不可用!");
        return NO;
    } else if ([CLLocationManager locationServicesEnabled] &&
               ([CLLocationManager authorizationStatus] == kCLAuthorizationStatusAuthorizedWhenInUse || [CLLocationManager authorizationStatus] == kCLAuthorizationStatusAuthorizedAlways
                )) {
                   NSLog(@"定位服务可以用!");
                   return YES;
               }
    return NO;
}

/**
 start 开始定位
 **/
-(void) startLocation
{
    if(self.locService)
    {
        return;
    }
    
}


-(void)onGetReverseGeoCodeResult:(BMKGeoCodeSearch *)searcher result:(BMKReverseGeoCodeResult *)result errorCode:(BMKSearchErrorCode)error
{
    
    if(error == BMK_SEARCH_NO_ERROR)
    {
    
        if(self.reOpen)
        {
            LocationModel * model = [[LocationModel alloc] init];
            model.longitude = [NSString stringWithFormat:@"%f", result.location.longitude ];
            model.latitude = [NSString stringWithFormat:@"%f", result.location.latitude ];
        
            model.areaName = [self dealAreaName:result];//result.addressDetail.city;
            [self judgeIsInSiChuan:result];
            if(self.gpsSuccess)
            {
                self.gpsSuccess(model);
            }
            return;
        }
    
        [LocationModel Instance].longitude =[NSString stringWithFormat:@"%f", result.location.longitude ];
        [LocationModel Instance].latitude =[NSString stringWithFormat:@"%f", result.location.latitude ];
        [LocationModel Instance].areaName = [self dealAreaName:result];
        [self judgeIsInSiChuan:result];
        //[LocationModel Instance].areaName = result.addressDetail.district.length>2?[result.addressDetail.district substringToIndex:2]: result.addressDetail.district ;
    
        [LocationModel Instance].blishName = [self dealAreaName:result];
        //[LocationModel Instance].blishName = result.addressDetail.district.length>2?[result.addressDetail.district substringToIndex:2]: result.addressDetail.district ;
    
    
    }
    else
    {
    
    
        if(self.reOpen)
        {
        
        
            if(self.gpsfailed)
            {
                self.gpsfailed();
            }
            self.reOpen =FALSE;
            return;
        }
    
        [LocationModel Instance].longitude =@"";
        [LocationModel Instance].latitude =@"";
        [LocationModel Instance].areaName =@"成都";
        
    }
    
    self.gpsComplete=YES;
    

}

//接收正向编码结果
- (void)onGetGeoCodeResult:(BMKGeoCodeSearch *)searcher result:(BMKGeoCodeResult *)result errorCode:(BMKSearchErrorCode)error{
    if (error == BMK_SEARCH_NO_ERROR) {
        //在此处理正常结果
        if (_cityCoordinateBlock) {
            _cityCoordinateBlock(result.location);
        }
    }
    else {
        NSLog(@"抱歉，未找到结果");
    }
}


- (void)didUpdateBMKUserLocation:(BMKUserLocation *)userLocation
{
    self.userLocation = userLocation;
    [_locService stopUserLocationService];
    NSLog(@"didUpdateUserLocation lat %f,long %f",userLocation.location.coordinate.latitude,userLocation.location.coordinate.longitude);
    BMKReverseGeoCodeOption * re =[[BMKReverseGeoCodeOption alloc] init];
    CLLocationCoordinate2D coords = CLLocationCoordinate2DMake(/* 31.41,121.48*/
    userLocation.location.coordinate.latitude,userLocation.location.coordinate.longitude);//纬度，经度
    [re setReverseGeoPoint:coords];
    
    BOOL io = [_geocodesearch reverseGeoCode:re ];
    if(io)
        {
        
        }
    else
        {
        
        
        if(self.reOpen)
        {
            if(self.gpsfailed)
                {
                self.gpsfailed();
                }
            self.reOpen =FALSE;
            return;
        }
        
        NSLog(@"检索发送失败");
            [LocationModel Instance].longitude =@"";
            [LocationModel Instance].latitude =@"";
            [LocationModel Instance].areaName =@"成都";
        
        
        
         self.gpsComplete=YES;
        }
    

}

- (void)didFailToLocateUserWithError:(NSError *)error
{
    
    if(self.reOpen)
    {
        if(self.gpsfailed)
            {
            self.gpsfailed();
            }
        self.reOpen =FALSE;
        return;
    }
    
    [LocationModel Instance].longitude =@"";
    [LocationModel Instance].latitude =@"";
    [LocationModel Instance].areaName =@"成都";
    

    
     self.gpsComplete=YES;
}


-(void) restartLocation
{

   
    self.reOpen=YES;
     [_locService startUserLocationService];
}



#pragma mark 通过地址获取坐标
- (void)getCoordinateWithCity:(NSString *)city{
    BMKGeoCodeSearchOption *geoCodeSearchOption = [[BMKGeoCodeSearchOption alloc]init];
    geoCodeSearchOption.city= city;
    geoCodeSearchOption.address = city;
    BOOL flag = [_geocodesearch geoCode:geoCodeSearchOption];
    
    if(flag)
    {
        NSLog(@"geo检索发送成功");
    }
    else
    {
        NSLog(@"geo检索发送失败");
    }
}

#pragma mark 处理地名，四川之外的都显示成成都市
- (NSString *)dealAreaName:(BMKReverseGeoCodeResult *)result{
    if ([result.addressDetail.province containsString:@"四川"]) {
        
        if ([result.addressDetail.city containsString:@"市"]) {
            NSString * cityName =result.addressDetail.city;
        NSMutableString * city = [NSMutableString stringWithString:cityName];
            [city deleteCharactersInRange:NSMakeRange(city.length-1, 1)];
            return city;
        }
//        return result.addressDetail.city;
    }
    return @"成都";
}

#pragma mark 判断是否在四川
- (void)judgeIsInSiChuan:(BMKReverseGeoCodeResult *)result{
    if ([result.addressDetail.province containsString:@"四川"]) {
        self.isInSiChuan = YES;
    } else {
        self.isInSiChuan = NO;
    }
}

@end
