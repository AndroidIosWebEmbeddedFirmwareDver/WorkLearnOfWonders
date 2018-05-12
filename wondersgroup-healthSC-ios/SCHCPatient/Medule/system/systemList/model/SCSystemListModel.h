//
//  ReportNoticeModel.h
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface SCSystemListModel : BaseModel
@property(nonatomic ,strong) NSString* messageId;
@property(nonatomic ,strong) NSString * date;
@property(nonatomic ,strong) NSString * title;
@property(nonatomic ,strong) NSString * picurl;
@property(nonatomic ,strong) NSString * des;
@property(nonatomic,strong) NSString * linkUp;
@property(nonatomic ,assign) NSNumber  * state;

@end
