//
//  DoctorsModel.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"
@interface DoctorssModel : BaseModel
/*
 "id": 16560,
 "hospitalCode": "450753341",
 "deptCode": "100705",
 "doctorCode": "53",
 "doctorName": "李艳(内三)",
 "doctorTile": "副主任医师",
 "expertin": "心血管疾病，脑血管疾病，小儿脑科",
 "hospitalName": "成都市第七人民医院",
 "deptName": "内科门诊",
 "headphoto": "http://og3xulzx6.bkt.clouddn.com/589d85f6e72a4f978f4d7d7cc05df51b"
 }
 ],
 "more": true
 */
@property (nonatomic,strong) NSString * doctorID;//id
@property (nonatomic,strong) NSString * hosOrgCode;
@property (nonatomic,strong) NSString * hosDeptCode;//科室代码
@property (nonatomic,strong) NSString * hosDoctCode;//医生代码
@property (nonatomic,strong) NSString * doctorName;
@property (nonatomic,strong) NSString * doctorTitle;
@property (nonatomic,strong) NSString * expertin;//心血管疾病，脑血管疾病，小儿脑科
@property (nonatomic,strong) NSString * hosName;
@property (nonatomic,strong) NSString * deptName;//科室
@property (nonatomic,strong) NSString * headphoto;//医生头像
@property (nonatomic,strong) NSString * orderCount;//接诊量
@property (nonatomic,strong) NSNumber * more;//是否有更多
@property (nonatomic,strong) NSNumber * gender;//性别 1 男 2 女
@end


@interface DoctorsModel : BaseModel

@property (nonatomic,strong) NSString * more;
@property (nonatomic,strong) NSArray <DoctorssModel *> * content;
@end
