//
//  HomePageModel.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"
#import "BannersModel.h"
#import "FunctionModel.h"
#import "ArticlessModel.h"
@interface HomePageModel : BaseModel
@property (nonatomic,strong) NSArray <BannersModel *> * banners;
@property (nonatomic,strong) NSArray <FunctionModel *> * functionIcons;
@property (nonatomic,strong) NSArray <ArticlessModel *> * news;

@end
