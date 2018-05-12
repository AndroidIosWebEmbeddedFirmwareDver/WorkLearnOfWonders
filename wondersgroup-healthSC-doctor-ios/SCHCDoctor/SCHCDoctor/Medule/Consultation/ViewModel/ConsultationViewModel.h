//
//  ConsultationViewModel.h
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"
#import "ConsultationModel.h"

@interface ConsultationViewModel : BaseViewModel

@property (nonatomic, strong) NSArray<ConsultationModel *> *dataArray;

- (void)getConsultationConversations:(void(^)(void))success failed:(void(^)(NSError *error))failed;

@end
