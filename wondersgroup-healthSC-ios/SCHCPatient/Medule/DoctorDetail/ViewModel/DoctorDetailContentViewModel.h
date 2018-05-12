//
//  DoctorDetailContentViewModel.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"


@interface DoctorDetailContentViewModel : BaseViewModel

@property (nonatomic, copy) NSString *content;

@property (nonatomic, assign) CGFloat cellHeight;

- (instancetype)initWithContent:(NSString *)content;

@end
