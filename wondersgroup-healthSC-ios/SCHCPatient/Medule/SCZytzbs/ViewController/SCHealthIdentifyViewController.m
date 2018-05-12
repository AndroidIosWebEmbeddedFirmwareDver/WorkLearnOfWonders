//
//  HealthIdentifyViewController.m
//  HCPatient
//
//  Created by guowenke1 on 15/3/10.
//  Copyright (c) 2015年 wonders. All rights reserved.
//

#import "SCHealthIdentifyViewController.h"
#import "SCHealthIdentifyTitleTableViewCell.h"
#import "SCHealthIdentifyContentTableViewCell.h"
#import "SCHealthResultViewController.h"
#import "WDAlertView.h"
//#import "UIViewController+CommonNav.h"

@interface SCHealthIdentifyViewController ()
{
    NSInteger score; //计算分数
    UIButton *nextButton;
}

@property (nonatomic, assign) NSInteger       inter;
@property (nonatomic, strong) NSArray         *rootMessageArray;
@property (nonatomic, strong) NSMutableString *selectString;//选中的数组
@property (nonatomic, strong) NSMutableArray  *arrayScore;//分数数组

@end

@implementation SCHealthIdentifyViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.title = @"中医体质辨识";
    self.tableview = [[UITableView alloc] initWithFrame: CGRectZero
                                                          style: UITableViewStyleGrouped];
    [self.tableview setSeparatorColor: [UIColor bc1Color]];
      self.tableview.backgroundColor = [UIColor bc2Color];
    self.tableview.delegate = self;
     self.tableview.dataSource = self;
    [self.tableview setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    [self.view addSubview: self.tableview];
    [self.tableview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view).offset(-40);
        make.bottom.equalTo(self.view);
        make.left.equalTo(self.view);
        make.right.equalTo(self.view);
    }];
    
    self.edgesForExtendedLayout = UIRectEdgeNone;
    
    self.view.backgroundColor = [UIColor bc1Color];//UIColorFromRGB(0XECECEC);
    
    self.inter = 1;
    self.rootMessageArray = [[NSArray alloc] initWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"Property List" ofType:@"plist"]];
    self.selectString = [NSMutableString stringWithCapacity:1];
    
    //根据bug : HCPIOS-212 ,去掉滚动
    _tableview.scrollEnabled = NO;
    
    nextButton = [UIButton buttonWithType:UIButtonTypeCustom];
    nextButton.layer.cornerRadius = 5;
    nextButton.layer.borderColor = [[UIColor bc3Color] CGColor];
    nextButton.layer.borderWidth = 0.5;
    nextButton.backgroundColor = [UIColor bc7Color];
    nextButton.titleLabel.font = [UIFont systemFontOfSize:16.0];
    [nextButton setTitle: @"完成" forState: UIControlStateNormal];
    [nextButton setTitleColor: [UIColor tc0Color] forState: UIControlStateNormal];
    [nextButton addTarget: self action: @selector(pushresultVC) forControlEvents: UIControlEventTouchUpInside];
    [self.view addSubview: nextButton];
    [nextButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.view).offset(15);
        make.right.equalTo(self.view).offset(-15);
        make.height.mas_equalTo(44);
        make.bottom.equalTo(self.view).offset(-20);
    }];
    nextButton.adjustsImageWhenHighlighted = NO;
    nextButton.hidden = YES;
    
    
}

- (void)popBack
{
    WDAlertView *alert = [[WDAlertView alloc]initWithNavigationController:self.navigationController withType:WDAlertViewTypeTwo];
    [alert reloadTitle:@"" content:@"是否放弃实名认证"];
    [alert.cancelBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
    [alert.submitBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
    [alert.submitBtn setTitle:@"确定" forState:UIControlStateNormal];
    [alert.cancelBtn setTitle:@"取消" forState:UIControlStateNormal];
    alert.submitBlock = ^(WDAlertView *view) {
        [view dismiss];
    };
    
    alert.cancelBlock = ^(WDAlertView *view) {
        [view dismiss];
        [self.navigationController popViewControllerAnimated:YES];
    };
    
    [alert showViewWithHaveBackAction:YES withHaveBackView:YES];

}

- (void)returnPopLastView
{
    [self.navigationController popViewControllerAnimated:YES];
}
#pragma mark - UITableViewDataSource , UITableViewDelegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 6;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == 0) {
        if (self.inter == 1) {
         //   return 40;
            return  80;
        }else
       return  60+ [self calculateCellHeightWithMessage:[[self.rootMessageArray objectAtIndex:self.inter - 1] objectForKey:@"title"]];
    }else
        return 60;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == 0) {
        static NSString *cellIdentifier = @"HealthIdentifyTitleTableViewCell";
        SCHealthIdentifyTitleTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
        if (!cell) {
            cell = [[SCHealthIdentifyTitleTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
        }
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        [cell creatMessageOnView:self.inter message:[[self.rootMessageArray objectAtIndex:self.inter - 1] objectForKey:@"title"] total:[self.rootMessageArray count]];
        return cell;
    }else{
        static NSString *cellIdentifier = @"HealthIdentifyContentTableViewCell";
        SCHealthIdentifyContentTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
        if (!cell) {
            cell = [[SCHealthIdentifyContentTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
        }
        cell.selectionStyle = UITableViewCellSelectionStyleDefault;
        [cell creatMessageOnViewMessage:[[[self.rootMessageArray objectAtIndex:self.inter - 1] objectForKey:@"answer"] objectAtIndex:indexPath.row-1]];
        return cell;
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
//    [self pushresultVC];
//    return;
    if (indexPath.row > 0&&self.selectString.length < 66) {
            [self.selectString appendString:[NSString stringWithFormat:@",%ld", (long)indexPath.row]];
        }
        if (indexPath.row > 0) {
           // [HCBudtrinity recordEvent:@"page" segmentation:@{@"page":[NSString stringWithFormat:@"measure_tcm_%ld",self.inter]} count:1];

            self.inter++;
            if (self.inter > 33) {
                [self.selectString deleteCharactersInRange:NSMakeRange(_selectString.length-1,1)];
   
                [self.selectString appendString:[NSString stringWithFormat:@"%ld", (long)indexPath.row]];
                
                self.inter = 33;
                nextButton.hidden = NO;
                return;
                
            }else{
                [self performSelector:@selector(reloadDataTableView) withObject:self afterDelay:0.3];
            }
        }
    
}
- (void)reloadDataTableView{
     [self.tableview reloadData];
}

- (void)pushresultVC {
    SCHealthResultViewController *healthResult = [[SCHealthResultViewController alloc] init];
    healthResult.isMine = self.isMine;
    healthResult.title = @"体质辨识结果";
    healthResult.isLifeStyle = NO;
    //healthResult.stringMessage = @"3,3,4,1,1,2,3,3,1,1,2,2,1,2,3,1,1,1,2,3,2,1,3,1,1,3,3,1,1,3,2,1,2";
    healthResult.stringMessage = [self.selectString substringFromIndex:1];
    [self.navigationController pushViewController:healthResult animated:YES];
}

#pragma mark - label的高度
- (CGFloat)calculateCellHeightWithMessage:(NSString *)str
{
    CGFloat contentLabelWide = [UIScreen mainScreen].bounds.size.width - 20;
    CGSize maximumContentLabelSize = CGSizeMake(contentLabelWide, FLT_MAX);
    CGFloat totalHeight = 0;
    NSDictionary *attribute = @{NSFontAttributeName: [UIFont systemFontOfSize:16.0]};
    CGSize contentSize = [str boundingRectWithSize:maximumContentLabelSize options:NSStringDrawingUsesFontLeading|NSStringDrawingUsesLineFragmentOrigin attributes:attribute context:nil].size;
    if (str.length > 0) {
        totalHeight += contentSize.height;
    }
    return totalHeight +1;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    return nil;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    return nil;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 0.5;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section {
    return 0.5;
}

@end



