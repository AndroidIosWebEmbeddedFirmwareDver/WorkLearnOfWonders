//
//  ArticlessModel.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface ArticlessModel : BaseModel
@property (nonatomic,strong) NSString * articleID;
@property (nonatomic,strong) NSString * thumb;//title 图片URL
@property (nonatomic,strong) NSString * title;
@property (nonatomic,strong) NSString * desc;
@property (nonatomic,strong) NSString * pv;//预览量啊
@property (nonatomic,strong) NSString * url;//h5 Url
@end
