//
//  WCNearbyHospitalModel.m
//  HCPatient
//
//  Created by Gu Jiajun on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "SCHospitalModel.h"

@implementation SCHospitalModel

-(id)mutableCopyWithZone:(NSZone *)zone{
    SCHospitalModel *serializer = [[[self class] allocWithZone:zone] init];
    serializer.hospitalId = [self.hospitalId mutableCopyWithZone:zone];
    serializer.hospitalCode = [self.hospitalCode mutableCopyWithZone:zone];
    serializer.hospitalName = [self.hospitalName mutableCopyWithZone:zone];
    serializer.hospitalGrade = [self.hospitalGrade mutableCopyWithZone:zone];
    serializer.hospitalLatitude = self.hospitalLatitude;
    serializer.hospitalLongitude = self.hospitalLongitude;
    serializer.hospitalAddress = [self.hospitalAddress mutableCopyWithZone:zone];
    serializer.hospitalDesc = [self.hospitalDesc mutableCopyWithZone:zone];
    serializer.hospitalTel = [self.hospitalTel mutableCopyWithZone:zone];
    serializer.receiveThumb = [self.receiveThumb mutableCopyWithZone:zone];
    serializer.hospitalPhoto = [self.hospitalPhoto mutableCopyWithZone:zone];
    serializer.receiveCount = [self.receiveCount mutableCopyWithZone:zone];
    serializer.concern = self.concern;
    serializer.searchType = self.searchType;
    serializer.userId = [self.userId mutableCopyWithZone:zone];
    return serializer;
}

@end
