//
//  SCCertificationType.m
//  SCHCPatient
//
//  Created by wanda on 16/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCCertificationResultVC.h"
#import "SCCertificationTypeIDCardCell.h"
#import "SCCertificationTypeNameCell.h"
#import "SCCertificationTypeFailCell.h"
#import "SCCertificationFixedViewController.h"
@interface SCCertificationResultVC ()

@end

@implementation SCCertificationResultVC

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setupView];
}

- (void)setupView
{
    WS(weakSelf);
    self.title = @"实名认证";
    self.view.backgroundColor = [UIColor bc2Color];
    self.tableView = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStylePlain];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    self.tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tableView.backgroundColor = [UIColor bc2Color];
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.left.right.equalTo(weakSelf.view);
    }];
    [self setupHeadView];
    if (self.type == CertificationFail) {
        [self setupFootView];
    }
}

- (void)setupFootView
{
    WS(weakSelf)
    UIView *footView = [[UIView alloc] init];
    footView.backgroundColor = [UIColor bc2Color];
    footView.frame = CGRectMake(0,0,SCREEN_WIDTH,150);
    _tableView.tableFooterView = footView;
    
    UIButton *handlebutton = [UIButton buttonWithType:UIButtonTypeCustom];
    handlebutton.userInteractionEnabled = YES;
    handlebutton.layer.cornerRadius = 5;
    handlebutton.layer.borderColor = [[UIColor bc3Color] CGColor];
    handlebutton.layer.borderWidth = 0.5;
    [footView addSubview:handlebutton];
    [handlebutton setTitle:@"重新申请实名认证" forState:UIControlStateNormal];
    [handlebutton setBackgroundColor:[UIColor bc7Color]];
    handlebutton.titleLabel.font = [UIFont systemFontOfSize:16.0];
    [handlebutton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [[handlebutton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x){
        SCCertificationFixedViewController *certificationFixed = [[SCCertificationFixedViewController alloc] init];
        certificationFixed.formFail = YES;
        certificationFixed.hidesBottomBarWhenPushed = YES;
        [weakSelf.navigationController pushViewController:certificationFixed animated:YES];
        certificationFixed.navigationController.navigationBar.hidden = NO;
    }];
    [handlebutton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(15);
        make.right.equalTo(weakSelf.view).offset(-15);
        make.centerY.equalTo(footView);
        make.height.mas_equalTo(44);
    }];
}

- (void)setupHeadView
{
    UIView *headView = [[UIView alloc] init];
    headView.backgroundColor = [UIColor bc2Color];
    headView.frame = CGRectMake(0,0,SCREEN_WIDTH,220);
    _tableView.tableHeaderView = headView;
    
    UIImageView *imageView = [[UIImageView alloc] init];
    [headView addSubview:imageView];
    imageView.backgroundColor = [UIColor clearColor];
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(headView);
        make.centerY.equalTo(headView).offset(-15);
        make.width.equalTo(@140);
        make.height.equalTo(@120);
    }];
    
    UILabel *textLabel = [[UILabel alloc] init];
    textLabel.textAlignment = NSTextAlignmentCenter;
    textLabel.textColor = [UIColor tc2Color];
    textLabel.font = [UIFont systemFontOfSize:14.0];
    [headView addSubview:textLabel];
    [textLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(headView);
        make.top.equalTo(imageView.mas_bottom).offset(15);
    }];
    if (self.type == CertificationIng) {
        imageView.image = [UIImage imageNamed:@"img正在审核中"];
        textLabel.text = @"正在审核中";
    } else if (self.type == CertificationPass) {
        imageView.image = [UIImage imageNamed:@"img审核已通过"];
        textLabel.text = @"审核已通过";
    } else if(self.type == CertificationFail) {
        imageView.image = [UIImage imageNamed:@"img审核未通过"];
        textLabel.text = @"审核未通过";
    }
}

#pragma mark - tableView delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (self.type == CertificationFail) {
        return 1;
    }
    return 2;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (self.type == CertificationFail) {
        return 80;
    }
    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"CertificationTypeCellIdentifier";
    if (self.type == CertificationFail) {
        SCCertificationTypeFailCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if (!cell) {
            cell = [[SCCertificationTypeFailCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        cell.model = self.model;
        cell.backgroundColor = [UIColor whiteColor];
        return cell;
    } else {
        if (indexPath.row == 0) {
            SCCertificationTypeNameCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
            if (!cell) {
                cell = [[SCCertificationTypeNameCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
                cell.selectionStyle = UITableViewCellSelectionStyleNone;
            }
            cell.model = self.model;
            cell.backgroundColor = [UIColor whiteColor];
            return cell;
        } else if (indexPath.row == 1) {
            SCCertificationTypeIDCardCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
            if (!cell) {
                cell = [[SCCertificationTypeIDCardCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
                cell.selectionStyle = UITableViewCellSelectionStyleNone;
            }
            cell.model = self.model;
            cell.backgroundColor = [UIColor whiteColor];
            return cell;
        }
    }
        return nil;
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}



@end
