//
//  TabbarItemModel.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface TabbarItemModel : BaseModel

@property (nonatomic, strong) NSArray *tabImages;
@property (nonatomic, assign) BOOL     isBundleImage;
@property (nonatomic, assign) BOOL     downloadComplete;


@end
