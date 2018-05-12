//
//  SCDepartmentViewModel.h
//  VaccinePatient
//
//  Created by ZJW on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCDepartmentModel.h"

@interface SCDepartmentViewModel : BaseViewModel<AppointmetIMPL>

@property (nonatomic, strong) NSString  *hospitalID;
@property (nonatomic, strong) NSString  *hospitalCode;
@property (nonatomic, strong) NSString  *hosDeptCode;//上级科室代码
@property (nonatomic, assign) NSInteger selectedIndex;
@property (nonatomic, strong) NSArray   *noodleList;
@property (nonatomic, strong) NSMutableArray *childrenList;


@end
