//
//  SCCertificationType.h
//  SCHCPatient
//
//  Created by wanda on 16/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"
#import "SCTrueNameModel.h"
typedef NS_ENUM(NSUInteger, CertificationType) {
    CertificationIng,            //正在审核中
    CertificationPass,           //已通过
    CertificationFail,           //审核失败
};
@interface SCCertificationResultVC : BaseViewController<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong) SCTrueNameModel   *model;
@property (nonatomic,assign) CertificationType type;
@property (nonatomic,strong) UITableView       *tableView;
@end
