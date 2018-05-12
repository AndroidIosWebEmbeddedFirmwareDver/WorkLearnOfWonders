//
//  ManagerAreaDataDao.h
//  HCPatient
//
//  Created by luzhongchang on 16/8/28.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LocationModel.h"


@interface BaseAreaModel : BaseModel
@property(nonatomic,strong) NSString * name;
@property(nonatomic ,strong) NSString * code;
@end



@interface ManagerAreaDataDao : NSObject
+(instancetype)shareInstance;
-(NSMutableArray * ) getNearyThreeeData;
-(void)insertdata:(LocationModel *)model;


@end
