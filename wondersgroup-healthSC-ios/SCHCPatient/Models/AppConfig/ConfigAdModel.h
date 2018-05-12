//
//  ConfigAdModel.h
//  VaccinePatient
//
//  Created by maorenchao on 16/6/16.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface ConfigAdModel : BaseModel
//    //        "advertisement":{// 启动页广告
//    //            "display":0,// 是否显示,0-不显示,1-显示
//    //            "skip":0,// 是否允许跳过,0-不允许,1-允许
//    //            "duration":3000,// 持续时间(毫秒/ms)
//    //            "imgUrl":"http://www.vaccine.com/",// 图片地址
//    //            "hoplinks":"http://www.vaccine.com/"// 跳转链接
//    //        }
@property(nonatomic, strong)NSString *imgUrl;
@property(nonatomic, strong)NSString *hoplinks;

@property(nonatomic, strong)NSNumber *display;
@property(nonatomic, strong)NSNumber *skip;
@property(nonatomic, strong)NSNumber *duration;



@end
