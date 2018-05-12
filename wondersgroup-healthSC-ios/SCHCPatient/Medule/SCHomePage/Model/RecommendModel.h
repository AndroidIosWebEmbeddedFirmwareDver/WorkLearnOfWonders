//
//  RecommendModel.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"
/*
 搜索关联词汇 此版本没有
 */
@interface RecommendModel : BaseModel
@property (nonatomic,strong)NSString * code;
@property (nonatomic,strong)NSString * name;
@property (nonatomic,strong)NSString * recommendID;
@end
