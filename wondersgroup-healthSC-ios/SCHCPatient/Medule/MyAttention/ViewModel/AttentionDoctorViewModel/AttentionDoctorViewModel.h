//
//  AttentionDoctorViewModel.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"


@interface AttentionDoctorViewModel : BaseViewModel <MyAttentionIMPL>

@property (nonatomic, strong) NSMutableArray *dataArray;

@end
