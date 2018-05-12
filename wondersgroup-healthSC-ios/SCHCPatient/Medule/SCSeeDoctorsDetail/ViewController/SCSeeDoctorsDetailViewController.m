//
//  SCVisitDetailViewController.m
//  HCPatient
//
//  Created by wuhao on 2016/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "SCSeeDoctorsDetailViewController.h"
#import "BaseTableViewCell.h"


/*
 这个类已经弃用了。。。。
 */

@interface SCSeeDoctorsDetailViewController ()

@property(nonatomic,strong)UITableView *table;//展示表格
@property(nonatomic,strong)NSArray *dataArray;//数据数组
@property(nonatomic,strong)NSArray *titleArary;//标题数组
@property(nonatomic,strong)UIView *bottomWhiteView;//底下的白色背景
@property(nonatomic,strong)UILabel *orderStateLabel;//订单状态标签
@property(nonatomic,strong)UILabel *orderStateDetailLabel;//订单状态详细标签

@end

@implementation SCSeeDoctorsDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    

    self.navigationItem.title=@"就诊详情";
    
    _titleArary=[NSArray new];
    _titleArary=@[@"医院",@"科室",@"医生",@"门诊类型",@"挂号费",@"就诊时间",@"就诊地点"];
    
    
    [self requestData];
    [self createUI];
    
    
}


    //请求数据
-(void)requestData{
    
    
    
}


    //搭建UI
-(void)createUI{
    

        //创建Tabel
    self.table=[UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStyleGrouped withDelegateAndDataSource:self];
    self.table.scrollEnabled=NO;
    self.table.rowHeight=44.f;
    self.table.backgroundColor=[UIColor clearColor];
    [self.table mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view.mas_top).offset(45);
        make.left.equalTo(self.view.mas_left);
        make.size.mas_equalTo(CGSizeMake(self.view.self.width,self.view.height-200));
    }];
    
    
        //白色背景 [UIColor colorWithHexString:@"#FFFFFF"]
//    self.bottomWhiteView=[UISetupView setupViewWithSuperView:self.view withBackGroundColor:[UIColor redColor]];
//    [self.bottomWhiteView mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(self.view.mas_left);
//        make.top.equalTo(self.view.mas_top);
//        make.right.equalTo(self.view.mas_right);
//        make.bottom.equalTo(self.table.mas_top);
//
//    }];

    
                //订单状态标签
    self.orderStateLabel=[UISetupView setupLabelWithSuperView:self.view withText:@"订单状态:" withTextColor:[UIColor tc2Color] withFontSize:12];
    self.orderStateLabel.backgroundColor=[UIColor greenColor];
    [self.orderStateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.view.mas_left).offset(101);
        make.top.equalTo(self.view.mas_top).offset(15);
        make.right.equalTo(self.view.mas_right).offset(-254);
        make.bottom.equalTo(self.table.mas_top).offset(-15);
        make.size.mas_equalTo(CGSizeMake(60, 12));
        
        
            }];
        
        
                //订单状态详细标签
    self.orderStateDetailLabel=[UISetupView setupLabelWithSuperView:self.view withText:@"test" withTextColor:[UIColor colorWithHexString:@"#FFA217"] withFontSize:12];
    [self.orderStateDetailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.orderStateLabel.mas_right);
        make.top.equalTo(self.view.mas_top).offset(15);
        make.right.equalTo(self.view.mas_right).offset(-101);
        make.bottom.equalTo(self.table.mas_top).offset(-15);
            }];
            
  
    
}


- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    if (section==2) {
        return 0.0000001f;
    }
    return 10.f;

}



- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 0.00000001f;
    
}


    //创建组数
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    
    return 3;
    
    
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    return nil;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    return nil;
}


    //组数里面的行数
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (section==1) {
        return 7;
    }
    return 1;
}




-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    static NSString *cellID=@"cellID";
    BaseTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:cellID];
    if (cell==nil) {
        cell=[[BaseTableViewCell alloc]initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:cellID];
    }
    
    if (indexPath.section==0) {
        
        UILabel *titleLabel = [UISetupView setupLabelWithSuperView:cell.contentView withText:@"就诊人" withTextColor:[UIColor tc2Color] withFontSize:16.0];
            //   [cell addSubview:titleLabel];
        [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(cell.contentView).offset(15.0);
            make.top.equalTo(cell.contentView).offset(11.0);
            make.bottom.equalTo(cell.contentView).offset(-11.0);
        }];
        
            //        UIImageView *arrowIcon = [UISetupView setupImageViewWithSuperView:cell.contentView withImageName:@"ic_personal_more"];
            //            //[cell addSubview:arrowIcon];
            //        [arrowIcon mas_makeConstraints:^(MASConstraintMaker *make) {
            //            make.right.equalTo(cell.contentView).offset(-15.0);
            //            make.centerY.equalTo(cell.contentView);
            //        }];
        
        
        
        NSString *patientName=@"张小亮";
        
        UILabel *contentLabel = [UISetupView setupLabelWithSuperView:cell.contentView withText:patientName withTextColor:[UIColor tc3Color] withFontSize:16.0];
        contentLabel.backgroundColor=[UIColor redColor];
            // [cell addSubview:contentLabel];
        [contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.right.equalTo(cell.contentView).offset(-32);
            make.centerY.equalTo(cell.contentView);
        }];
        
        cell.lineTopHidden=NO;
        cell.lineBottomHidden=NO;
    }
    if (indexPath.section==1) {
        
        
        UILabel *titleLabel = [UISetupView setupLabelWithSuperView:cell.contentView withText:self.titleArary[indexPath.row] withTextColor:[UIColor tc2Color] withFontSize:16.0];
            //   [cell addSubview:titleLabel];
        [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(cell.contentView).offset(15.0);
            make.top.equalTo(cell.contentView).offset(11.0);
            make.bottom.equalTo(cell.contentView).offset(-11.0);
        }];
        
            //indexPath 0,1,2有右边指引箭头
        if (indexPath.row<=2) {
            UIImageView *arrowIcon = [UISetupView setupImageViewWithSuperView:cell.contentView withImageName:@"systemRightArrow"];
                //[cell addSubview:arrowIcon];
            [arrowIcon mas_makeConstraints:^(MASConstraintMaker *make) {
                make.right.equalTo(cell.contentView).offset(-15.0);
                make.centerY.equalTo(cell.contentView);
            }];
            
            
        }
        
        
//#warning 测试详细信息,仅做测试
        NSArray *testDetailArray=@[@"成都市第一人民医院",@"皮肤科",@"刘医生",@"主治",@"5元",@"2016-09-29 周六 上午",@"门诊1号楼一楼挂号窗口"];
        
        
//        NSString *patientName=@"test";
        
        UILabel *contentLabel = [UISetupView setupLabelWithSuperView:cell.contentView withText:testDetailArray[indexPath.row] withTextColor:[UIColor tc3Color] withFontSize:16.0];
            // [cell addSubview:contentLabel];
        [contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.right.equalTo(cell.contentView).offset(-32);
            make.centerY.equalTo(cell.contentView);
        }];
        
            //第一个row有上下线条,其他的都是只有下线条
        if (indexPath.row==0) {
            cell.lineTopHidden=NO;
            cell.lineBottomHidden=NO;
        }
        cell.lineTopHidden=YES;
        cell.lineBottomHidden=NO;
        
        
        
    }
    if (indexPath.section==2) {
        
        UILabel *titleLabel = [UISetupView setupLabelWithSuperView:cell.contentView withText:@"订单号" withTextColor:[UIColor tc2Color] withFontSize:16.0];
            //   [cell addSubview:titleLabel];
        [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(cell.contentView).offset(15.0);
            make.top.equalTo(cell.contentView).offset(11.0);
            make.bottom.equalTo(cell.contentView).offset(-11.0);
        }];
        
            //        UIImageView *arrowIcon = [UISetupView setupImageViewWithSuperView:cell.contentView withImageName:@"ic_personal_more"];
            //            //[cell addSubview:arrowIcon];
            //        [arrowIcon mas_makeConstraints:^(MASConstraintMaker *make) {
            //            make.right.equalTo(cell.contentView).offset(-15.0);
            //            make.centerY.equalTo(cell.contentView);
            //        }];
        
        
        
        NSString *orderNumberString=@"1232142142142142";
        
        UILabel *contentLabel = [UISetupView setupLabelWithSuperView:cell.contentView withText:orderNumberString withTextColor:[UIColor tc3Color] withFontSize:16.0];
            // [cell addSubview:contentLabel];
        [contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.right.equalTo(cell.contentView).offset(-32);
            make.centerY.equalTo(cell.contentView);
        }];
        
        cell.lineTopHidden=NO;
        cell.lineBottomHidden=NO;
        
        
        
    }
    

    return cell;
}



#pragma mark 完成按钮的点击事件
-(void)pressDoneButton{
    
    
    
    
}



@end
