//
//  SCAccountSetViewController.m
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAccountSetViewController.h"
#import "SCSetPwdViewController.h"
#import "UserService.h"
#import "SCUserSetViewController.h"
@interface SCAccountSetViewController ()

@end

@implementation SCAccountSetViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setupView];
}

- (void)setupView
{
    WS(weakSelf);
    self.navigationItem.title = @"账号设置";
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

}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    
    return 10.0;
}

- (void)tableView:(UITableView *)tableView willDisplayHeaderView:(UIView *)view forSection:(NSInteger)section {
    if ([view isKindOfClass: [UITableViewHeaderFooterView class]]) {
        UITableViewHeaderFooterView* castView = (UITableViewHeaderFooterView*) view;
        UIView* content = castView.contentView;
        UIColor* color = [UIColor bc2Color];
        content.backgroundColor = color;
    }
}

#pragma mark - tableView delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return 2;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {

    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *identifier = @"AccountSetCellIdentifier";
    if (indexPath.row == 0) {
        SCAccountSetCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if (!cell) {
            cell = [[SCAccountSetCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        cell.backgroundColor = [UIColor whiteColor];
        return cell;
    } else if (indexPath.row == 1) {
         static NSString *identifier = @"ChangePasswordCell";
        SCChangePasswordCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if (!cell) {
            cell = [[SCChangePasswordCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        [cell changeLabelState];
        cell.backgroundColor = [UIColor whiteColor];
        return cell;
    }
    return nil;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == 0) {
        
    } else if (indexPath.row == 1) {
       if ([UserManager manager].password_complete) {
        SCChangePassWordViewController *changePassWord = [[SCChangePassWordViewController alloc] init];
        [self.navigationController pushViewController:changePassWord animated:YES];
        } else {
             SCUserSetViewController *setPassWord = [[SCUserSetViewController alloc] init];
            [self.navigationController pushViewController:setPassWord animated:YES];
        }
    }
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    if ([UserManager manager].isLogin) {
      [[UserService service] refreshLastUserInfo];
    }
    [self.tableView reloadData];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}


@end
