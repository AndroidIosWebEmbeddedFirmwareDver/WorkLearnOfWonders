//
//  SCSearchModel.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/13.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"
#import "HomeSearchViewController.h"
/*
 搜索全部历史存储数据库模型
 */
@interface SCSearchModel : BaseModel

@property (nonatomic, copy) NSString *cat_id;
/// 标题名称
@property (nonatomic, copy) NSString *cat_name;

@property (nonatomic,strong)NSDate * date;

@property (nonatomic,assign)HomeSearchType type;

@end
