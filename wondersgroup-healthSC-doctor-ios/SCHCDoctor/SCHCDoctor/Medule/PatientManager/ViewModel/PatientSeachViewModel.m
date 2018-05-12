//
//  PatientSeachViewModel.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientSeachViewModel.h"

@implementation PatientSeachViewModel

- (void)searchPatientsWithKeyword:(NSString *)keyword Success:(void(^)(void))success Failed:(void(^)(NSError *error))failed {
    
    NSMutableArray *temp = [[NSMutableArray alloc] init];
    for (NSInteger i = 0; i<5; i++) {
        PatientListModel *m = [[PatientListModel alloc] init];
        m.name = [NSString stringWithFormat:@"name_%zd",i];
        m.mobile = @"1234567890";
        [temp addObject:m];
    }
    self.patientsArray = temp;
    
}


@end
