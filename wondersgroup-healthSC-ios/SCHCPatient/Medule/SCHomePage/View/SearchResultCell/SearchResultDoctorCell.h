//
//  SearchResultDoctorCell.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseTableViewCell.h"
#import "DoctorsModel.h"

@interface SearchResultDoctorCell : BaseTableViewCell
@property(nonatomic,strong)UIImageView * doctorsHeadImage;
@property (nonatomic,strong) UILabel * doctorsNameLab;
@property (nonatomic,strong) UILabel * doctorsProfessional;//职称
@property (nonatomic,strong) UILabel * hospitalsNameLab;//医院
@property (nonatomic,strong) UILabel * doctorsOfficeLab;//科室
@property (nonatomic,strong) UILabel * receiveLab;//接诊量（文字title）
@property (nonatomic,strong) UILabel * receivePatientNumLab;//接诊量
@property (nonatomic,strong) UILabel * doctorDetailLab;//医生简介

@property (nonatomic,strong) UILabel * registerLab;//挂号

@property (nonatomic,strong) DoctorssModel * model;

@property (nonatomic, strong) NSString *highLightString;
@end
