//
//  DoctorDetailViewModel.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "DoctorDetailModel.h"
#import "SCDoctorSchedulingModel.h"

typedef NS_ENUM(NSUInteger, DoctorDetailLayoutType) {

    DoctorDetailLayoutTypeHeaderSection,
    DoctorDetailLayoutTypeTopSection,
//    DoctorDetailLayoutTypeJudgeTitleCell,
    DoctorDetailLayoutTypeJudgeSection,
};

@interface DoctorDetailViewModel : BaseViewModel <DoctorIMPL>

//布局数组
@property (nonatomic, strong) NSMutableArray *layoutArray;

//医生详情请求入参
@property (nonatomic, copy) NSString *hospitalCode;     //医院代码
@property (nonatomic, copy) NSString *hosDeptCode;      //科室代码
@property (nonatomic, copy) NSString *hosDoctCode;      //医生代码

@property (nonatomic, strong) DoctorDetailModel *resultModel;

@property (nonatomic, strong) SCDoctorSchedulingModel *headerModel;

@property (nonatomic, assign) CGFloat topCellHeight;    //


@property (nonatomic, strong) DoctorDetailModel *detailModel;

@end


@interface DoctorDetailLayoutModel : NSObject

@property (nonatomic, assign) DoctorDetailLayoutType type;

@end
