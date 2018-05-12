//
//  SCExtractOrdersHistoryView.h
//  SCHCPatient
//
//  Created by Po on 2017/5/8.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SCExtractOrdersHistoryView : UIView
@property (strong, nonatomic) UITableView * tableView;
@property (strong, nonatomic) UIView * titleLine;
@property (strong, nonatomic) UILabel * titleLabel;


/**
 设置回调

 @param countBlock 个数
 @param titleBlock 名称
 @param selectedBlock 点击回调
 */
- (void)setDataWithCount:(NSInteger(^)(NSInteger section))countBlock
                   title:(NSString *(^)(NSIndexPath * indexPath))titleBlock
                selected:(void(^)(NSInteger count))selectedBlock;
@end
