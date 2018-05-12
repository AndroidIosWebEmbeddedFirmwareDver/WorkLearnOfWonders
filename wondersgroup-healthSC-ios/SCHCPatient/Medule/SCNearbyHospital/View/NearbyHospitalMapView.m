//
//  NearbyHospitalMapView.m
//  HCPatient
//
//  Created by Gu Jiajun on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "NearbyHospitalMapView.h"
#import "MyBMKPointAnnotation.h"
#import "MapPaopaoView.h"
#import "LocationManager.h"
#import "SCHospitalModel.h"
#import <BaiduMapAPI_Utils/BMKUtilsComponent.h>
#import <BaiduMapAPI_Base/BMKTypes.h>


@interface NearbyHospitalMapView () <BMKMapViewDelegate,BMKLocationServiceDelegate,BMKGeoCodeSearchDelegate,BMKDistrictSearchDelegate>
{
    BMKLocationService *_locService;
    BMKUserLocation *_userLocation;
    BMKGeoCodeSearch*_geoSearch;
    //////地区边界
//    BMKDistrictSearch *_districtSearch;
}

@property (nonatomic, strong) NSMutableArray *annotationsArray;

@end

@implementation NearbyHospitalMapView

- (void)setMapViewDelegate{
    self.delegate = self;
}

- (id)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    if (self) {
        
        self.delegate = self;
        self.showsUserLocation = YES;
        self.zoomLevel = 14;
        self.zoomEnabled = NO;
        self.zoomEnabledWithTap = NO;
        self.scrollEnabled = YES;
        self.showMapScaleBar = YES;
        self.rotateEnabled = NO;
        self.mapScaleBarPosition = CGPointMake(10, frame.size.height-40);
        
        
        if ([LocationManager manager].userLocation) {
            [self updateLocationData:[LocationManager manager].userLocation];
        }

        [self viewWillAppear];
        
        [self bindRAC];
        
        
        
        
        //////地区边界
//        [self districtSearchAction];
        
//        CLLocationCoordinate2D center = CLLocationCoordinate2DMake(39.924257, 116.403263);
//        BMKCoordinateSpan span = BMKCoordinateSpanMake(0.138325, 0.18045);
//        self.limitMapRegion = BMKCoordinateRegionMake(center, span);////限制地图显示范围
//        self.rotateEnabled = NO;//禁用旋转手势
    }
    return self;
}

- (void)bindRAC{
    WS(weakSelf)
    [RACObserve(self, dataArray) subscribeNext:^(NSArray *array) {
        if (array) {
            
            [weakSelf updateAnnotations];
            [weakSelf.annotationsArray removeAllObjects];
            for (NSInteger i = 0; i < array.count; i++) {
                
                SCHospitalModel *info = array[i];
                
                // 添加一个PointAnnotation
                
                MyBMKPointAnnotation* annotation = [[MyBMKPointAnnotation alloc]init];
                CLLocationCoordinate2D coor;
                coor.latitude = [info.hospitalLatitude floatValue];
                coor.longitude = [info.hospitalLongitude floatValue];
                annotation.coordinate = coor;
                annotation.title = info.hospitalName;
                annotation.index = i;
                
                [weakSelf.annotationsArray addObject:annotation];
            }
            
            [weakSelf addAnnotations:weakSelf.annotationsArray];
        }
    }];
    
    
    [RACObserve(self, currentModel) subscribeNext:^(LocationModel *model) {
        if (model) {
            double lon = model.longitude.doubleValue;
            double lat  = model.latitude.doubleValue;
            CLLocationCoordinate2D coordinate = CLLocationCoordinate2DMake(lat, lon);
            [weakSelf setCenterCoordinate:coordinate animated:NO];
            weakSelf.zoomLevel = 14;
        }else {
            
        }
        
    }];
}

- (NSMutableArray *)annotationsArray{
    if (_annotationsArray == nil) {
        _annotationsArray = [[NSMutableArray alloc] init];
    }
    return _annotationsArray;
}

#pragma mark 更新标注点,先删除原有点，然后加上所有点
- (void)updateAnnotations{
    if (_annotationsArray.count > 0) {
        [self removeAnnotations:_annotationsArray];
    }
}

#pragma mark baidu map delegate
- (BMKAnnotationView *)mapView:(BMKMapView *)mapView viewForAnnotation:(id <BMKAnnotation>)annotation
{
    if ([annotation isKindOfClass:[BMKPointAnnotation class]]) {
        static NSString *identifier = @"MKAnnotationView";
        BMKPinAnnotationView *pin = (BMKPinAnnotationView *)[mapView dequeueReusableAnnotationViewWithIdentifier:identifier];
        
        if (pin == nil) {
            pin = [[BMKPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:identifier];
            //在图中我们可以看到图标的上方，有个气泡弹窗里面写着当前用户的位置所在地
            pin.backgroundColor = [UIColor clearColor];
            pin.image = [UIImage imageNamed:@"坐标红"];
        }
        //pin.calloutOffset = CGPointMake(0, 110);
        pin.annotation = annotation;
        pin.canShowCallout = NO;
/*
        MyBMKPointAnnotation *ann = (MyBMKPointAnnotation *)annotation;
        
        NSString *str = ann.title;
        CGSize size = [str boundingRectWithSize:CGSizeMake(200, 30) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName:[UIFont systemFontOfSize:15]} context:nil].size;
        
        MapPaopaoView *ppv = [[MapPaopaoView alloc] initWithFrame:CGRectMake(0, 0, size.width+40, 50)];
        
        ppv.name = str;
        pin.tag = ann.index;
        
        pin.paopaoView = [[BMKActionPaopaoView alloc] initWithCustomView:ppv];
        UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapPaopao:)];
        [pin.paopaoView addGestureRecognizer:tapGesture];

*/
        return pin;
    }
    return nil;
}

-(void)mapView:(BMKMapView *)mapView didSelectAnnotationView:(BMKAnnotationView *)view{
    
    NSLog(@"view.tag:%zd",view.tag);
    
    
}

- (void)mapView:(BMKMapView *)mapView didAddAnnotationViews:(NSArray *)views{
    
}

- (void)mapView:(BMKMapView *)mapView regionDidChangeAnimated:(BOOL)animated{
    double lat = self.centerCoordinate.latitude;
    double lon = self.centerCoordinate.longitude;
    
    self.centerCoordinateDict = @{@"lat":@(lat),@"lon":@(lon)};
    
}

#pragma mark tap
- (void)tapPaopao:(UITapGestureRecognizer *)gesture{
    
    
}

/*
- (BMKDistrictSearch *)districtSearch{
    if (_districtSearch == nil) {
        _districtSearch = [[BMKDistrictSearch alloc] init];
        _districtSearch.delegate = self; // 此处记得不用的时候需要置nil，否则影响内存的释放
    }
    return _districtSearch;
}

#pragma mark 行政区域
- (void)districtSearchAction {
    BMKDistrictSearchOption *option = [[BMKDistrictSearchOption alloc] init];
    option.city = @"四川";
    option.district = @"成都";
    BOOL flag = [self.districtSearch districtSearch:option];
    if (flag) {
        NSLog(@"district检索发送成功");
    } else {
        NSLog(@"district检索发送失败");
    }
}
*/

/**
 *返回行政区域搜索结果
 *@param searcher 搜索对象
 *@param result 搜索结BMKDistrictSearch果
 *@param error 错误号，@see BMKSearchErrorCode
 */
/*
- (void)onGetDistrictResult:(BMKDistrictSearch *)searcher result:(BMKDistrictResult *)result errorCode:(BMKSearchErrorCode)error {
    NSLog(@"onGetDistrictResult error: %d", error);
    if (error == BMK_SEARCH_NO_ERROR) {
        NSLog(@"\nname:%@\ncode:%d\ncenter latlon:%lf,%lf", result.name, (int)result.code, result.center.latitude, result.center.longitude);
        
        BOOL flag = YES;
        for (NSString *path in result.paths) {
            BMKPolygon* polygon = [self transferPathStringToPolygon:path];
            if (polygon) {
                [self addOverlay:polygon]; // 添加overlay
                if (flag) {
//                    [self mapViewFitPolygon:polygon];
                    flag = NO;
                }
            }
        }
    }
}

- (BMKOverlayView*)mapView:(BMKMapView *)map viewForOverlay:(id<BMKOverlay>)overlay
{
    if ([overlay isKindOfClass:[BMKPolygon class]]) {
        BMKPolygonView *polygonView = [[BMKPolygonView alloc] initWithOverlay:overlay];
        polygonView.strokeColor = [UIColor colorWithRed:1 green:0 blue:0 alpha:0.6];
        polygonView.fillColor = [UIColor colorWithRed:1 green:1 blue:0 alpha:0.4];
        polygonView.lineWidth = 1;
        polygonView.lineDash = YES;
        return polygonView;
    }
    return nil;
}

//根据polygone设置地图范围
- (void)mapViewFitPolygon:(BMKPolygon *) polygon {
    CGFloat ltX, ltY, rbX, rbY;
    if (polygon.pointCount < 1) {
        return;
    }
    BMKMapPoint pt = polygon.points[0];
    ltX = pt.x, ltY = pt.y;
    rbX = pt.x, rbY = pt.y;
    for (int i = 1; i < polygon.pointCount; i++) {
        BMKMapPoint pt = polygon.points[i];
        if (pt.x < ltX) {
            ltX = pt.x;
        }
        if (pt.x > rbX) {
            rbX = pt.x;
        }
        if (pt.y > ltY) {
            ltY = pt.y;
        }
        if (pt.y < rbY) {
            rbY = pt.y;
        }
    }
    BMKMapRect rect;
    rect.origin = BMKMapPointMake(ltX , ltY);
    rect.size = BMKMapSizeMake(rbX - ltX, rbY - ltY);
    [self setVisibleMapRect:rect];
//    self.zoomLevel = self.zoomLevel - 0.3;
}

- (BMKPolygon*)transferPathStringToPolygon:(NSString*) path {
    if (path == nil || path.length < 1) {
        return nil;
    }
    NSArray *pts = [path componentsSeparatedByString:@";"];
    if (pts == nil || pts.count < 1) {
        return nil;
    }
    BMKMapPoint *points = new BMKMapPoint[pts.count];
    NSInteger index = 0;
    for (NSString *ptStr in pts) {
        if (ptStr && [ptStr rangeOfString:@","].location != NSNotFound) {
            NSRange range = [ptStr rangeOfString:@","];
            NSString *xStr = [ptStr substringWithRange:NSMakeRange(0, range.location)];
            NSString *yStr = [ptStr substringWithRange:NSMakeRange(range.location + range.length, ptStr.length - range.location - range.length)];
            if (xStr && xStr.length > 0 && [xStr respondsToSelector:@selector(doubleValue)]
                && yStr && yStr.length > 0 && [yStr respondsToSelector:@selector(doubleValue)]) {
                points[index] = BMKMapPointMake(xStr.doubleValue, yStr.doubleValue);
                index++;
            }
        }
    }
    BMKPolygon *polygon = nil;
    if (index > 0) {
        polygon = [BMKPolygon polygonWithPoints:points count:index];
    }
    delete [] points;
    return polygon;
}
*/
/*
#pragma mark - BMKLocationServiceDelegate
//实现相关delegate处理位置信息更新
//处理方向变更信息
- (void)didUpdateUserHeading:(BMKUserLocation *)userLocation
{
    NSLog(@"heading is %@",userLocation.heading);
}
//处理位置坐标更新
- (void)didUpdateBMKUserLocation:(BMKUserLocation *)userLocation
{
    [_locService stopUserLocationService];
    NSLog(@"定位经纬度： lat %f,long %f",userLocation.location.coordinate.latitude,userLocation.location.coordinate.longitude);
    [self setCenterCoordinate:userLocation.location.coordinate animated:YES];
    [self updateLocationData:userLocation];
    _userLocation = userLocation;
    
    
    //百度获取省市
    BMKReverseGeoCodeOption *option = [[BMKReverseGeoCodeOption alloc] init];
    option.reverseGeoPoint = userLocation.location.coordinate;
    [_geoSearch reverseGeoCode:option];

}

- (void)didFailToLocateUserWithError:(NSError *)error{
    NSLog(@"定位失败：%@",error.localizedFailureReason);
}


- (void)onGetReverseGeoCodeResult:(BMKGeoCodeSearch *)searcher result:(BMKReverseGeoCodeResult *)result errorCode:(BMKSearchErrorCode)error{
    NSLog(@"city:::::%@",result.addressDetail.city);
    if (!error) {
 
    }
}
 */



@end
