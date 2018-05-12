//
//  MyAttentionDoctorModel.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/15.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"


@interface MyAttentionDoctorModel : BaseModel

@property (nonatomic, copy) NSString *mid;
@property (nonatomic, copy) NSString *headphoto;
@property (nonatomic, copy) NSString *hosOrgCode;
@property (nonatomic, copy) NSString *hosDeptCode;
@property (nonatomic, copy) NSString *hosDoctCode;
@property (nonatomic, copy) NSString *doctorName;
@property (nonatomic, copy) NSString *doctorTitle;
@property (nonatomic, copy) NSString *expertin;
@property (nonatomic, copy) NSString *orderCount;
@property (nonatomic, copy) NSString *isFull;
@property (nonatomic, copy) NSString *gender;


@end
