//
//  MyAttentionHospitalModel.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/15.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"


@interface MyAttentionHospitalModel : BaseModel

@property (nonatomic, copy) NSString *hospitalId;
@property (nonatomic, copy) NSString *hospitalCode;
@property (nonatomic, copy) NSString *hospitalName;
@property (nonatomic, copy) NSString *hospitalAddress;
@property (nonatomic, copy) NSString *hospitalDesc;
@property (nonatomic, copy) NSString *hospitalPhoto;
@property (nonatomic, copy) NSString *hospitalTel;
@property (nonatomic, copy) NSString *hospitalGrade;
@property (nonatomic, copy) NSString *receiveCount;

@end
