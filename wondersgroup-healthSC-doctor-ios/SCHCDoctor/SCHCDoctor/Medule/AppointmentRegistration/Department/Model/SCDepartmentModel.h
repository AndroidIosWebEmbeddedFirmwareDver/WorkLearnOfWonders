//
//  SCDepartmentModel.h
//  VaccinePatient
//
//  Created by ZJW on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface SCDepartmentModel : BaseModel

@property (nonatomic, copy) NSString *hosOrgCode;       //医院代码
@property (nonatomic, copy) NSString *deptName;         //科室名称
@property (nonatomic, copy) NSString *hosDeptCode;      //科室代码

@end
