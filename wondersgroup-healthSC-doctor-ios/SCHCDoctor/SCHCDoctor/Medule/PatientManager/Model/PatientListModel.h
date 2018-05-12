//
//  PatientListModel.h
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseModel.h"

@interface PatientListModel : BaseModel

@property (nonatomic, strong) NSString *avatarUrl;
@property (nonatomic, strong) NSString *name;
@property (nonatomic, strong) NSString *address;
@property (nonatomic, strong) NSString *gender;
@property (nonatomic, strong) NSString *age;
@property (nonatomic, strong) NSString *mobile;
@property (nonatomic, strong) NSString *patientTag;


@property (nonatomic, strong) NSString *isKey;
@property (nonatomic, strong) NSString *isPoor;

@end
