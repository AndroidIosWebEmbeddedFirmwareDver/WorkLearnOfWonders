//
//  ReferralCell.h
//  SCHCDoctor
//
//  Created by Po on 2017/6/5.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseTableViewCell.h"

@interface ReferralCell : BaseTableViewCell

@property (strong, nonatomic) UIImageView   * userImageView;            //头像
@property (strong, nonatomic) UILabel       * userNameLabel;            //姓名
@property (strong, nonatomic) UILabel       * typeLabel;                //优先级类型
@property (strong, nonatomic) UILabel       * timeLabel;                //时间
@property (strong, nonatomic) UILabel       * inHospitalTitleLabel;     //转入标题
@property (strong, nonatomic) UILabel       * inHospitalLabel;          //转入医院名
@property (strong, nonatomic) UILabel       * outHospitalTitleLabel;    //转出标题
@property (strong, nonatomic) UILabel       * outHospitalLabel;         //转出医院名
@property (strong, nonatomic) UILabel       * progressLabel;            //进度状态

- (void)showProgress:(ReferralProgress)type;
- (void)setPriority:(ReferralPriority)type;

@end
