//
//  HospitalsModel.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"
#import "SCHospitalModel.h"

@interface HospitalsModel : BaseModel

@property (nonatomic,strong)NSArray <SCHospitalModel *> * content;
@property (nonatomic,strong)NSNumber  * more;

@end
