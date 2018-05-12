//
//  HHInformationListModel.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface HHInformationListModel : BaseModel

@property (nonatomic, copy) NSString *cat_id;
@property (nonatomic, copy) NSString *cat_name;
@property (nonatomic, assign) BOOL more;
@property (nonatomic, strong) NSDictionary *more_params;

@property (nonatomic, strong) NSMutableArray *list;

@end
