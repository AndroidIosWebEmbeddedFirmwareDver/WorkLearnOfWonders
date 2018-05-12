//
//  SCNearbyHospitalSearchBarView.h
//  SCHCPatient
//
//  Created by Gu Jiajun on 16/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void(^SearchBlock)(NSString *word);

@interface SCNearbyHospitalSearchBarView : UIView

@property (nonatomic, copy) SearchBlock searchBlock;

@end
