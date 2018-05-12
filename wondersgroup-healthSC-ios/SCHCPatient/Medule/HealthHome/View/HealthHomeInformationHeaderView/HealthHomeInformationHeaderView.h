//
//  HealthHomeInformationHeaderView.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>


typedef void(^SegmentControlSelectedBlock)(NSUInteger);

@interface HealthHomeInformationHeaderView : UITableViewHeaderFooterView

@property (nonatomic, assign) NSUInteger selectedIndex;

@property (nonatomic, copy) SegmentControlSelectedBlock segmentControlSelectedBlock;

- (void)setDataWithItems:(NSArray *)items;

- (void)testFunction;

@end
