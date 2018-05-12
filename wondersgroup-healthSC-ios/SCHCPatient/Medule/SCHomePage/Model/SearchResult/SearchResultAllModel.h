//
//  SearchResultAllModel.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"
#import "HospitalsModel.h"
#import "DoctorsModel.h"
#import "ArticlesModel.h"

@interface SearchResultAllModel : BaseModel
@property (nonatomic, strong) HospitalsModel  * hospitals;//医院数据
@property (nonatomic, strong) DoctorsModel  * doctors;//医生数据
@property (nonatomic, strong) ArticlesModel * articles;//医院数据

@end
