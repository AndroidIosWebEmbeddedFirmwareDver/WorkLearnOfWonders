//
//  AritcleModel.h
//  VaccinePatient
//
//  Created by maorenchao on 16/6/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface AritcleModel : BaseModel
//"id": "71a35a600b1842df86100625c6edc4d8",
//"url": "http://10.1.92.195/vaccine-h5/article?id=71a35a600b1842df86100625c6edc4d8",
//"title": "爱好和平",
//"thumb": "www.baidu.com",
//"brief": "今天天气比较好",
//"pv": 100,
//"useplace": "2",
//"collect": 0,
//"share": 0
@property(nonatomic, strong)NSString *articleId;
@property(nonatomic, strong)NSString *thumb;
@property(nonatomic, strong)NSString *title;
@property(nonatomic, strong)NSString *brief;
@property(nonatomic, strong)NSString *url;

@property(nonatomic, strong)NSNumber *useplace;

@property(nonatomic, strong)NSNumber *pv;
@property(nonatomic, strong)NSNumber *collect;
@property(nonatomic, strong)NSNumber *share;
@end
