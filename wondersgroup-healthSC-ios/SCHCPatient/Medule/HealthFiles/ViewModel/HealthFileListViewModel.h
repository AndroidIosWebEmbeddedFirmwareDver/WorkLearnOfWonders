//
//  HealthFileListViewModel.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"


typedef NS_ENUM(NSUInteger, HealthFileListCellType) {
    
    HealthFileListCellTypeRecord,               //就诊记录
    HealthFileListCellTypeReport,               //查验报告
    HealthFileListCellTypePrescribe,            //电子处方
    HealthFileListCellTypeHospitalize,          //住院史
};

@interface HealthFileListViewModel : BaseViewModel

@end
