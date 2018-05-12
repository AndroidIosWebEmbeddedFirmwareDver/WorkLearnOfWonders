//
//  ArticleInfoModel.h
//  VaccinePatient
//
//  Created by maorenchao on 16/6/15.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"
#import "ShareModel.h"

@interface ArticleInfoModel : BaseModel
@property(nonatomic, strong)ShareModel *share;
@property(nonatomic, strong)NSNumber *can_favorite;//是否可以收藏
@property(nonatomic, strong)NSNumber *is_favorite;//用户是否已经收藏, 通过这个字段来控制收藏按钮是否点亮

@end
