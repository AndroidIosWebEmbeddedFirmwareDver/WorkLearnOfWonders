//
//  SCDepartmentViewController.m
//  VaccinePatient
//
//  Created by ZJW on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCDepartmentViewController.h"
#import "SCAppointmentDoctorTabViewController.h"

#import "SCMyPreorderListsViewController.h"
#import "SCMyPreorderDetailViewController.h"
#import "SCHospitalHomePageViewController.h"

@interface SCDepartmentViewController ()<UITableViewDelegate,UITableViewDataSource>
{
    UITableView *_myLeftTabel;
    UITableView *_myRightTabel;
}

@end

@implementation SCDepartmentViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {
        self.viewModel = [SCDepartmentViewModel new];

    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    [self setupView];
    [self bindViewModel];
    [self getNoodleDepartment];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc {

}


#pragma mark    - setupView
-(void)setupView {

    self.navigationItem.title = @"选择科室";
    self.view.backgroundColor = [UIColor bc2Color];
    
    if (![self isHaveViewControllerWithClassString:@"SCHospitalHomePageViewController"]) {
        [self setRightItemWithString:@"医院微站" withAction:@selector(gotoDoctorHome)];
    }
    
    UITableView *tableViewL = [[UITableView alloc] initWithFrame: CGRectMake(0, 0, SCREEN_WIDTH/3, self.view.frame.size.height)
                                                           style: UITableViewStyleGrouped];
    tableViewL.delegate      = self;
    tableViewL.dataSource    = self;
    [tableViewL setBackgroundColor: [UIColor bc10Color]];
    [tableViewL setSeparatorColor: [UIColor clearColor]];
    [tableViewL setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    [self.view addSubview: tableViewL];
    _myLeftTabel = tableViewL;
    
    [_myLeftTabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view);
        make.bottom.equalTo(self.view);
        make.left.equalTo(self.view);
        make.width.mas_equalTo(SCREEN_WIDTH/3);
    }];
    
    UITableView *tableViewR = [[UITableView alloc] initWithFrame: CGRectMake(0, 0, SCREEN_WIDTH/3, self.view.frame.size.height)
                                                           style: UITableViewStyleGrouped];
    tableViewR.delegate      = self;
    tableViewR.dataSource    = self;
    [tableViewR setBackgroundColor: [UIColor clearColor]];
    [tableViewR setSeparatorColor: [UIColor clearColor]];
    [tableViewR setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    [self.view insertSubview: tableViewR aboveSubview: tableViewL];
    _myRightTabel = tableViewR;
    
    [_myRightTabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view);
        make.bottom.equalTo(self.view);
        make.left.equalTo(tableViewL.mas_right);
        make.right.equalTo(self.view);
    }];
    
    UIView *rightBG = [[UIView alloc] init];
    [rightBG setBackgroundColor: [UIColor bc1Color]];
    [self.view insertSubview: rightBG belowSubview: _myRightTabel];
    [rightBG mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_myRightTabel);
        make.bottom.equalTo(_myRightTabel);
        make.left.equalTo(_myRightTabel);
        make.right.equalTo(_myRightTabel);
    }];

}

- (UIView *)loadSelectedView {
    UIView *view        = [[UIView alloc] init];
    UIView *lineBottom  = [[UIView alloc] init];
    
    [lineBottom setBackgroundColor: [UIColor dc1Color]];
    [view       setBackgroundColor: [UIColor bc1Color]];
    
    [view addSubview: lineBottom];
    [lineBottom mas_makeConstraints:^(MASConstraintMaker *make) {
        make.height.mas_equalTo(0.5);
        make.left.equalTo(view);
        make.right.equalTo(view);
        make.bottom.equalTo(view);
    }];
    
    return view;
}

#pragma mark    - bindViewModel
-(void)bindViewModel {
    
}

#pragma mark    - reloadData
- (void)getNoodleDepartment {
    [LoadingView showLoadingInView:self.view];
    
    __weak typeof(self) weakSelf = self;
    [self.viewModel getNoodleDepartmentlList:^{
        [_myLeftTabel reloadData];
        [weakSelf.view hiddenFailView];
        if (weakSelf.viewModel.noodleList.count > 0) {
            [_myLeftTabel selectRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0] animated:NO scrollPosition: UITableViewScrollPositionTop];
            SCDepartmentModel *deparment = self.viewModel.noodleList[0];
            self.viewModel.hosDeptCode = deparment.hosDeptCode;

            [weakSelf.viewModel getChildreDepartmentlList:^{
                [LoadingView hideLoadinForView:self.view];
                [_myRightTabel reloadData];
            } failure:^{
                [_myRightTabel reloadData];
                [LoadingView hideLoadinForView:self.view];
            }];
        }
        else {
            [LoadingView hideLoadinForView:self.view];
            [weakSelf.view showFailView:FailViewEmpty withAction:^{
                [weakSelf getNoodleDepartment];
            }];
        }
        
    } failure:^{
        [LoadingView hideLoadinForView:self.view];
        [self.view showFailView:FailViewEmpty withAction:^{
            [weakSelf getNoodleDepartment];
        }];
        
        [_myLeftTabel reloadData];
        [_myRightTabel reloadData];
    }];
    
}

- (void)getChildDepartment {
    [LoadingView showLoadingInView:self.view];
    
    [self.viewModel getChildreDepartmentlList:^{
        [self.view hiddenFailView];
        [_myLeftTabel reloadData];
        [_myRightTabel reloadData];
        [LoadingView hideLoadinForView:self.view];
        
        if ([self.viewModel.childrenList[self.viewModel.selectedIndex] count] > 0) {
            [_myRightTabel scrollToRowAtIndexPath: [NSIndexPath indexPathForRow:0 inSection:0] atScrollPosition: UITableViewScrollPositionTop animated: NO];
        }
        
    } failure:^{
        [_myLeftTabel reloadData];
        [_myRightTabel reloadData];
        [LoadingView hideLoadinForView:self.view];
    }];
}

#pragma mark    - method
-(void)gotoDoctorHome {
    SCHospitalHomePageViewController *vc = [SCHospitalHomePageViewController new];
    vc.hospitalID = self.viewModel.hospitalID;
    [self.navigationController pushViewController:vc animated:YES];
}

#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    if (self.viewModel.noodleList.count == 0) {
        return 0;
    }
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (tableView == _myLeftTabel) {
        return self.viewModel.noodleList.count;
    }
    return [self.viewModel.childrenList[self.viewModel.selectedIndex] count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (tableView == _myLeftTabel) {
        static NSString *cellIdentifier = @"LeftTableViewCell";
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier: cellIdentifier];
        if (!cell) {
            cell = [[UITableViewCell alloc] initWithStyle: UITableViewCellStyleDefault reuseIdentifier: cellIdentifier];
            [cell setSelectionStyle: UITableViewCellSelectionStyleDefault];
            [cell setSelectedBackgroundView:[self loadSelectedView]];
            
            [cell.textLabel setTextColor: [UIColor tc1Color]];
            [cell.textLabel setFont: [UIFont systemFontOfSize: 16.0]];
            [cell setBackgroundColor: [UIColor bc10Color]];

            UIView *line = [[UIView alloc] init];
            [cell addSubview:line];
            line.backgroundColor = [UIColor dc1Color];
            [line mas_makeConstraints:^(MASConstraintMaker *make) {
                make.width.equalTo(cell);
                make.height.mas_equalTo(0.5);
                make.left.bottom.equalTo(cell);
            }];
        }
        SCDepartmentModel *deparment = self.viewModel.noodleList[indexPath.row];

        if (indexPath.row == self.viewModel.selectedIndex) {
            [cell setBackgroundView:[self loadSelectedView]];
            cell.textLabel.textColor = [UIColor tc5Color];
            if ([deparment.hosDeptCode isEqualToString:@"-9"]) {
                cell.textLabel.textColor = [UIColor redColor];
            }

        }else {
            [cell setBackgroundView:nil];
            [cell.textLabel setTextColor: [UIColor tc1Color]];
            if ([deparment.hosDeptCode isEqualToString:@"-9"]) {
                cell.textLabel.textColor = [UIColor redColor];
            }
        }
        cell.textLabel.text = deparment.deptName;
        return cell;
    }
    
    static NSString *cellIdentifier = @"RightTableViewCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier: cellIdentifier];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle: UITableViewCellStyleDefault reuseIdentifier: cellIdentifier];
        [cell setSelectionStyle: UITableViewCellSelectionStyleNone];
        [cell.textLabel setTextColor:[UIColor tc1Color]];
        [cell.textLabel setFont: [UIFont systemFontOfSize: 16.0]];
        [cell setBackgroundColor: [UIColor clearColor]];
    }
    SCDepartmentModel *deparment = self.viewModel.childrenList[self.viewModel.selectedIndex][indexPath.row];
    cell.textLabel.text = deparment.deptName;
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if(tableView == _myLeftTabel) {
        self.viewModel.selectedIndex = indexPath.row;
        NSMutableArray *list = self.viewModel.childrenList[indexPath.row];
        [_myLeftTabel scrollToNearestSelectedRowAtScrollPosition:UITableViewScrollPositionTop animated: YES];
        SCDepartmentModel *deparment = self.viewModel.noodleList[indexPath.row];

        if(list.count == 0) {
            self.viewModel.hosDeptCode = deparment.hosDeptCode;
            [self getChildDepartment];
        }
        else {
            [_myLeftTabel reloadData];
            [_myRightTabel reloadData];
            [_myRightTabel scrollToRowAtIndexPath: [NSIndexPath indexPathForRow:0 inSection:0] atScrollPosition: UITableViewScrollPositionTop animated: NO];
        }
    }
    else {
        
        SCAppointmentDoctorTabViewController *vc = [[SCAppointmentDoctorTabViewController alloc] init];
        vc.model = self.viewModel.childrenList[self.viewModel.selectedIndex][indexPath.row];
        [self.navigationController pushViewController:vc animated: YES];
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 48.0;
}


- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 0.5;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section {
    return 0.5;
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    return nil;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    return nil;
}



@end
