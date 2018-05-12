//
//  LocationModel.m
//  HCPatient
//
//  Created by luzhongchang on 16/8/22.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "LocationModel.h"
#import "ManagerAreaDataDao.h"

@implementation LocationModel


+(LocationModel * )Instance
{
    static dispatch_once_t onceToken;
    static LocationModel *shared = nil;
    dispatch_once(&onceToken, ^{
        shared = [[LocationModel alloc] init];
    });
    return shared;
}



-(instancetype) init
{
    self = [super init];
    if(self)
    {
    
    self.showAreaCode =@"";
    self.latitude=@"";
    self.longitude=@"";
        [self bindModel];
    }
    
    return self;
}

-(void)bindModel
{
    RAC(self,showAreaName) = [RACSignal combineLatest:@[RACObserve(self, longitude),RACObserve(self,latitude),RACObserve(self, areaCode),RACObserve(self, areaName) ] reduce:^(NSString * longitude, NSString *latitude, NSString *  areaCode, NSString * areaName  ){
       
            return areaName;
    }];
    
    RAC(self, showAreaCode) = [RACSignal combineLatest:@[RACObserve(self, showAreaName)] reduce:^(NSString * showAreaName){
        
        if(showAreaName.length >0){
            
            if([showAreaName isEqualToString:@"成都"]){
                return @"510100000000";
            }
        
        
            NSArray *array =  [[Context context] getAreaNameAndID];
        
            for (int i =0; i<array.count; i++){
                BaseAreaModel * mod = [array objectAtIndex:i];
                if([showAreaName hasPrefix:mod.name])
                {
                    return mod.code;
                }
            }
            BaseAreaModel * mod = [array objectAtIndex:0];
            return mod.code;
        }else{
            
            
            if([showAreaName isEqualToString:@"成都"] || showAreaName==nil){
                return @"510100000000";
            }
        
            
                NSArray *array =  [[Context context] getAreaNameAndID];
                BaseAreaModel * mod = [array objectAtIndex:0];
                return mod.code;
            
            }
        
        
    }];
    
    
    RAC(self, areaCode) = [RACSignal combineLatest:@[RACObserve(self, areaName)] reduce:^(NSString  * areaName){
        if(areaName.length >0){
            
            if([areaName isEqualToString:@"成都"]){
                return @"510100000000";
            }
            
        NSArray *array =  [[Context context] getAreaNameAndID];
            
            for (int i =0; i<array.count; i++){
                BaseAreaModel * mod = [array objectAtIndex:i];
                if([areaName hasPrefix:mod.name]){
                    return mod.code;
                }
            }
            
            BaseAreaModel * mod = [array objectAtIndex:0];
            return mod.code;
            
        }else{
        
            if([areaName isEqualToString:@"成都"] ||areaName ==nil){
                return @"510100000000";
            }
            
            
            NSArray *array =  [[Context context] getAreaNameAndID];
            BaseAreaModel * mod = [array objectAtIndex:0];
            return mod.code;
            
      }
        
        
    }];
    
}












@end
