//
//  ProfileViewModel.h
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"
#import "ProfileCellModel.h"
#import "ProfileModel.h"

@interface ProfileViewModel : BaseViewModel<ProfileIMPL>

@property (nonatomic, strong) NSArray *datas;
@property (nonatomic, strong) UserInfoModel *headModel;
@property (nonatomic, strong) ProfileModel *functionModel;

-(void)reloadDatas;

@end
