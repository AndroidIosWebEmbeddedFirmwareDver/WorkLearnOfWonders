//
//  SCMyPreOrderListsFinalCell.m
//  SCHCPatient
//
//  Created by wuhao on 2016/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCMyPreOrderListsFinalCell.h"
#import "ResponseAdapter.h"

#import "EvaluateView.h"


@interface SCMyPreOrderListsFinalCell ()<UIAlertViewDelegate>
{
    SCCommitButtonType commitButtonType;

}
    //上部
@property(nonatomic,strong)UILabel *preOrderLabel;//预约挂号标签
@property(nonatomic,strong)UILabel *preOrderStateLabel;//预约状态标签

    //中部
@property(nonatomic,strong)UILabel *hospitalNameLabel;//医院名称标签
@property(nonatomic,strong)UILabel *departMentNameLabel;//医院科室标签
//@property(nonatomic,strong)UILabel *doctorNameLabel;//医生姓名标签
//@property(nonatomic,strong)UILabel *doctorLevelNameLabel;//门症等级标签
@property(nonatomic,strong)UILabel *timeDateLabel;//就诊时间标签
@property(nonatomic,strong)UILabel *timeWorkDayLabel;//就诊时间工作日标签
@property(nonatomic,strong)UILabel *timeHalfDayLabel;//就诊时间是上午还是下午
@property(nonatomic,strong)UILabel *patientNameLabel;//患者姓名标签
@property(nonatomic,strong)UILabel *priceLabel;//价格标签
@property(nonatomic,strong)UIImageView *rightArrow;//指示右箭头

    //下部
@property(nonatomic,strong)UILabel *preOrderNumberLabel;//预约号标签
@property(nonatomic,strong)UIButton *commitmentButton;//预约按钮
@property(nonatomic,strong)UIButton *evaluateButton;//评价按钮



@property(nonatomic,strong)NSDictionary *cancelPreorderDic;//取消预约的相关参数
@property(nonatomic,strong)NSDictionary *evaluateDoctorDic;//评价医生的相关参数f


@end





@implementation SCMyPreOrderListsFinalCell


-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    if (self==[super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self createUI];
            //UI数据的填充
        [self bindRac];
        self.backgroundColor = [UIColor bc2Color];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        
    }

    return self;
}

-(void)createUI{
    
         WS(weakSelf);
    
        //上部白色背景
    UIView *upWhiteView=[UISetupView setupViewWithSuperView:self.contentView withBackGroundColor:[UIColor whiteColor]];
    [upWhiteView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView.mas_left);
        make.top.equalTo(weakSelf.contentView.mas_top);
        make.right.equalTo(weakSelf.contentView.mas_right);
            //make.size.mas_equalTo(CGSizeMake(self.contentView.width, 44));
        make.height.mas_equalTo(@44);
        
    }];
    
    
    
        //预约挂号
    self.preOrderLabel=[UISetupView setupLabelWithSuperView:upWhiteView withText:@"预约挂号" withTextColor:[UIColor tc2Color] withFontSize:14];
    [self.preOrderLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(upWhiteView.mas_left).offset(15);
        make.centerY.equalTo(upWhiteView);
    }];
    
    
        //预约挂号状态
    self.preOrderStateLabel=[UISetupView setupLabelWithSuperView:upWhiteView withText:@"待就诊" withTextColor:[UIColor stc1Color] withFontSize:14];
    [self.preOrderStateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(upWhiteView.mas_right).offset(-15);
        make.centerY.equalTo(upWhiteView);
    }];
    
    
        //上面的灰色线条
    UIView *upGrayView=[UISetupView setupViewWithSuperView:self.contentView withBackGroundColor:[UIColor dc4Color]];
    
    [upGrayView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView.mas_left).offset(15);
        make.top.equalTo(upWhiteView.mas_bottom).offset(0);
        make.right.equalTo(weakSelf.contentView.mas_right).offset(-15);
        make.height.mas_equalTo(@1);
    }];
    
    
    
        //中部白色背景
    UIView *middleWhiteView=[UISetupView setupViewWithSuperView:self.contentView withBackGroundColor:[UIColor whiteColor]];
    [middleWhiteView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView.mas_left);
        make.top.equalTo(upWhiteView.mas_bottom).offset(1);
        make.right.equalTo(weakSelf.contentView.mas_right);
            //make.size.mas_equalTo(CGSizeMake(self.contentView.width, 44));
        make.height.mas_equalTo(@150);
    }];

    self.rightArrow = [UIImageView new];
    self.rightArrow.image = [UIImage imageNamed:@"link_right"];
    [middleWhiteView addSubview:self.rightArrow];
    
    [self.rightArrow mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(middleWhiteView.mas_right).offset(-13);
        make.centerY.equalTo(middleWhiteView);
        make.size.mas_equalTo(_rightArrow.image.size);
    }];
    


        //医院标签
    self.hospitalNameLabel=[UISetupView setupLabelWithSuperView:middleWhiteView withText:@"成都市第一人民医院" withTextColor:[UIColor tc1Color] withFontSize:16];
    [self.hospitalNameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(middleWhiteView.mas_left).offset(15);
        make.top.equalTo(middleWhiteView.mas_top).offset(15);
//        make.bottom.equalTo(middleWhiteView.mas_bottom).offset(-120);
        make.height.mas_equalTo(@16);
    }];
    
        //科室名称
    self.departMentNameLabel=[UISetupView setupLabelWithSuperView:middleWhiteView withText:@"皮肤科" withTextColor:[UIColor tc1Color] withFontSize:16];
    [self.departMentNameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(middleWhiteView.mas_left).offset(15);
        make.top.equalTo(middleWhiteView.mas_top).offset(41);
        make.right.equalTo(middleWhiteView).offset(-15);
        make.height.mas_equalTo(@16);
    }];
    
    
    
//        //医生名称
//    self.doctorNameLabel=[UISetupView setupLabelWithSuperView:middleWhiteView withText:@"张医生" withTextColor:[UIColor tc1Color] withFontSize:16];
//    [self.doctorNameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(_departMentNameLabel.mas_right).offset(10);
//        make.top.equalTo(middleWhiteView.mas_top).offset(41);
////        make.bottom.equalTo(middleWhiteView.mas_bottom).offset(-93);
//        make.height.mas_equalTo(@16);
//    }];
//    
//    
//        //医生等级
//    self.doctorLevelNameLabel=[UISetupView setupLabelWithSuperView:middleWhiteView withText:@"专家门诊" withTextColor:[UIColor tc1Color] withFontSize:16];
//    [self.doctorLevelNameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(weakSelf.doctorNameLabel.mas_right).offset(10);
//        make.top.equalTo(middleWhiteView.mas_top).offset(41);
////        make.bottom.equalTo(middleWhiteView.mas_bottom).offset(-93);
//            // make.size.mas_equalTo(CGSizeMake(48, 16));
//        make.height.mas_equalTo(@16);
//    }];
    
    
        //就诊人
    self.patientNameLabel=[UISetupView setupLabelWithSuperView:middleWhiteView withText:@"就诊人: 张小亮" withTextColor:[UIColor tc1Color] withFontSize:16];
    [self.patientNameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(middleWhiteView.mas_left).offset(15);
        make.top.equalTo(weakSelf.departMentNameLabel.mas_bottom).offset(10);
//        make.bottom.equalTo(middleWhiteView.mas_bottom).offset(-67);
            // make.size.mas_equalTo(CGSizeMake(48, 16));
        make.height.mas_equalTo(@16);
        make.width.mas_equalTo(@174);
    }];
    
    
        //挂号金额
   self.priceLabel=[UISetupView setupLabelWithSuperView:middleWhiteView withText:@"挂号金额: 50元" withTextColor:[UIColor tc1Color] withFontSize:16];
    [self.priceLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(middleWhiteView.mas_left).offset(15);
        make.top.equalTo(weakSelf.patientNameLabel.mas_bottom).offset(10);
//        make.bottom.equalTo(middleWhiteView.mas_bottom).offset(-42);
//      make.size.mas_equalTo(CGSizeMake(48, 16));
        make.height.mas_equalTo(@16);
    }];
    
    
        //时间标签(类似2016-12-1这样)
    self.timeDateLabel=[UISetupView setupLabelWithSuperView:middleWhiteView withText:@"2016-07-20" withTextColor:[UIColor tc2Color] withFontSize:16];
    [self.timeDateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(middleWhiteView.mas_left).offset(15);
        make.top.equalTo(weakSelf.priceLabel.mas_bottom).offset(10);
//        make.bottom.equalTo(middleWhiteView.mas_bottom).offset(-15);
            // make.size.mas_equalTo(CGSizeMake(48, 16));
        make.height.mas_equalTo(@16);
    }];
    
    
        //时间标签(类似周一)
//    self.timeWorkDayLabel=[UISetupView setupLabelWithSuperView:middleWhiteView withText:@"2016-07-20" withTextColor:[UIColor tc2Color] withFontSize:16];
//    [self.timeWorkDayLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(weakSelf.timeDateLabel.mas_right).offset(5);
//        make.top.equalTo(weakSelf.priceLabel.mas_bottom).offset(10);
////        make.bottom.equalTo(middleWhiteView.mas_bottom).offset(-15);
//        make.height.mas_equalTo(@16);
//    }];
//    
//        //时间标签(类似上午 下午)
//    self.timeHalfDayLabel=[UISetupView setupLabelWithSuperView:self.contentView withText:@"上午" withTextColor:[UIColor tc2Color] withFontSize:16];
//    [self.timeHalfDayLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(weakSelf.timeWorkDayLabel.mas_right).offset(5);
//        make.top.equalTo(weakSelf.priceLabel.mas_bottom).offset(10);
////        make.bottom.equalTo(middleWhiteView.mas_bottom).offset(-15);
//            // make.size.mas_equalTo(CGSizeMake(48, 16));
//        make.height.mas_equalTo(@16);
//    }];
    
    
        //下面的灰色线条
    UIView *downGrayView=[UISetupView setupViewWithSuperView:middleWhiteView withBackGroundColor:[UIColor dc4Color]];
    [downGrayView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(middleWhiteView.mas_left).offset(15);
        make.top.equalTo(weakSelf.timeDateLabel.mas_bottom).offset(15);
        make.right.equalTo(middleWhiteView.mas_right).offset(-15);
        make.height.mas_equalTo(@1);
    }];
    
    
        //下部白色背景
    UIView *downWhiteView=[UISetupView setupViewWithSuperView:self.contentView withBackGroundColor:[UIColor whiteColor]];
    [downWhiteView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView.mas_left);
        make.top.equalTo(downGrayView.mas_bottom);
        make.right.equalTo(weakSelf.contentView.mas_right);
            //make.size.mas_equalTo(CGSizeMake(self.contentView.width, 44));
        make.height.mas_equalTo(@44);
    }];

    
    self.commitmentButton = [UISetupView setupButtonWithSuperView:downWhiteView withTitleToStateNormal:@"" withTitleColorToStateNormal: [UIColor blackColor] withTitleFontSize:14 withAction:^(UIButton *sender) {
        
        //取消预约
        if (weakSelf.cellModel.business.state==1 || weakSelf.cellModel.business.state==2) {
            weakSelf.cancelPreorderDic=[[NSDictionary alloc]init];
            NSDictionary *dic=@{@"orderId":self.cellModel.orderId,@"cancelObj":@1,@"cancelReason":@1};
            weakSelf.cancelPreorderDic=dic;
            
            UIAlertView *alert=[[UIAlertView alloc]initWithTitle:nil message:@"是否取消本次预约" delegate:self cancelButtonTitle:@"暂不取消" otherButtonTitles:@"立即取消", nil];
            alert.delegate=weakSelf;
            [alert show];
            
        }
        //评价医生
        if (weakSelf.cellModel.business.state==4 && weakSelf.cellModel.isEvaluated==0) {
            weakSelf.evaluateDoctorDic=[[NSDictionary alloc]init];
            NSDictionary *dic=@{@"orderId":self.cellModel.orderId,@"uid":[UserManager manager].uid,};
            weakSelf.evaluateDoctorDic=dic;
            
            [weakSelf evaluateDoctorMethodWithDic: weakSelf.evaluateDoctorDic];
        }
    }];
    
    [self.commitmentButton.layer masksToBounds];
    self.commitmentButton.layer.cornerRadius=3.f;
    self.commitmentButton.layer.borderWidth=1.f;
    self.commitmentButton.layer.borderColor=[UIColor bc4Color].CGColor;
    
    [self.commitmentButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.size.mas_equalTo(CGSizeMake(72, 30));
        make.centerY.mas_equalTo(downWhiteView);
        make.right.equalTo(downWhiteView.mas_right).offset(-15);
        
    }];
    
    //订单号标签
    self.preOrderNumberLabel=[UISetupView setupLabelWithSuperView:downWhiteView withText:@"订单号: 132132142132132" withTextColor:[UIColor tc2Color] withFontSize:12];
    [self.preOrderNumberLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(downWhiteView).offset(15);
        make.height.mas_equalTo(@12);
        make.centerY.mas_equalTo(downWhiteView);
        make.right.lessThanOrEqualTo(weakSelf.commitmentButton.mas_left);
    }];

             //提交按钮
    
    self.lineBottomHidden=YES;

}
    //绑定数据源
-(void)bindRac{
    
    WS(weakSelf)

        //订单状态
    [RACObserve(self, cellModel.business.state)subscribeNext:^(NSNumber *x) {
        if (x) {
            [weakSelf.preOrderNumberLabel mas_updateConstraints:^(MASConstraintMaker *make) {
                make.right.lessThanOrEqualTo(weakSelf.commitmentButton.mas_left);
            }];
            
            
            NSString *commitString=@"";
            UIColor *commitBackGroundColor=[UIColor blackColor];
            [weakSelf.commitmentButton setHidden:NO];
            
            
            switch ([x intValue]) {
                case 1:{
                    commitString=@"取消预约";
                    commitBackGroundColor=[UIColor bc3Color];
                    weakSelf.preOrderStateLabel.text=@"待就诊";
                    weakSelf.preOrderStateLabel.textColor=[UIColor stc1Color];
                    weakSelf.commitmentButton.backgroundColor =[UIColor whiteColor];

                    
                }
                    break;
                case 2:{
                    commitString=@"取消预约";
                    commitBackGroundColor=[UIColor bc3Color];
                    weakSelf.preOrderStateLabel.text=@"待就诊";
                    weakSelf.preOrderStateLabel.textColor=[UIColor stc1Color];
                    weakSelf.commitmentButton.backgroundColor =[UIColor whiteColor];

                }
                    
                    break;
                case 3:{
                    [weakSelf.commitmentButton setHidden: YES];
                    weakSelf.preOrderStateLabel.text=@"已取消";
                    weakSelf.preOrderStateLabel.textColor=[UIColor tc2Color];
                    [weakSelf.preOrderNumberLabel mas_updateConstraints:^(MASConstraintMaker *make) {
                        make.right.lessThanOrEqualTo(weakSelf.commitmentButton.mas_left).offset(72.0);
                    }];
                    
                }
                    break;
                case 4:{
                    //已就诊未评价
                    if (weakSelf.cellModel.isEvaluated==0) {
                        commitString=@"评价医生";
                        commitBackGroundColor=[UIColor whiteColor];
                        self.commitmentButton.backgroundColor =[UIColor bc7Color];
                        
                    }
                    //已就诊已评价
                    else{
                        commitString=@"已评价";
                        commitBackGroundColor=[UIColor whiteColor];
                        self.commitmentButton.backgroundColor =[UIColor bc3Color];
                        
                        
                    }
                    weakSelf.preOrderStateLabel.text=@"已就诊";
                    weakSelf.preOrderStateLabel.textColor=[UIColor stc4Color];
                    
                    
                }
                    break;
                    
                    
                case 6:{
                    [weakSelf.commitmentButton setHidden: YES];
                    weakSelf.preOrderStateLabel.text=@"已爽约";
                    weakSelf.preOrderStateLabel.textColor=[UIColor redColor];
                    [weakSelf.preOrderNumberLabel mas_updateConstraints:^(MASConstraintMaker *make) {
                        make.right.lessThanOrEqualTo(weakSelf.commitmentButton.mas_left).offset(72.0);
                    }];
                
                
                
                }
                    break;
                    
            }
            [self.commitmentButton setTitleColor: commitBackGroundColor forState: UIControlStateNormal];
            [self.commitmentButton setTitle: commitString forState: UIControlStateNormal];
        }
        
    }];
    
        //医院名称
    [RACObserve(self, cellModel.business.hospitalName)subscribeNext:^(NSString *x) {
        weakSelf.hospitalNameLabel.text=x;
    }];
        //科室名称
    [RACObserve(self, cellModel.business.deptName)subscribeNext:^(NSString *x) {
        weakSelf.departMentNameLabel.text= [NSString stringWithFormat: @"%@　%@　%@", weakSelf.cellModel.business.deptName, self.cellModel.business.doctorName, self.cellModel.business.outDoctorLevel];
    }];
//        //医生姓名
//    [RACObserve(self, cellModel.business.doctorName)subscribeNext:^(NSString *x) {
//        weakSelf.doctorNameLabel.text=x;
//    }];
//    
//        //门症等级
//    [RACObserve(self, cellModel.business.outDoctorLevel)subscribeNext:^(NSString *x) {
//        weakSelf.doctorLevelNameLabel.text=x;
//    }];
    
        //患者姓名
    [RACObserve(self, cellModel.business.patientName)subscribeNext:^(NSString *x) {
        weakSelf.patientNameLabel.text=[NSString stringWithFormat:@"就诊人: %@",x];
    }];
    
        //价格

    [RACObserve(self, cellModel.price)subscribeNext:^(NSString *x) {
        weakSelf.priceLabel.text=[NSString stringWithFormat:@"挂号金额: %@元",x];
    }];
    
    
    [RACObserve(self, cellModel.business.time)subscribeNext:^(NSString *x) {
        
        weakSelf.timeDateLabel.text=x;
    }];
    
    
//    [RACObserve(self, cellModel.week)subscribeNext:^(NSString *x) {
//        weakSelf.timeWorkDayLabel.text=x;
//    }];
//    
//    [RACObserve(self,cellModel.timeRnge )subscribeNext:^(id x) {
//        if ([x intValue]==0) {
//            weakSelf.timeHalfDayLabel.text=@"上午";
//
//        }else{
//            weakSelf.timeHalfDayLabel.text=@"下午";
//
//        }
//        
//    }];
    
    
    [RACObserve(self, cellModel.showOrderId)subscribeNext:^(NSString *x) {
        weakSelf.preOrderNumberLabel.text=[NSString stringWithFormat:@"预约单号: %@",x];
    }];

}


#pragma mark 取消预约
-(void)cancelPreOrderMethodWithDic:(NSDictionary *)dic{
    if (dic==nil) {
        return;
    }
    
        //通知取消预约
    if (self.cancelDelegate) {
        [self.cancelDelegate cancelPreOrderSuccess:dic];
        
    }
    
}

#pragma mark 评价医生
-(void)evaluateDoctorMethodWithDic:(NSDictionary *)dic{
    
    if (self.evaluateDelegate) {
        [self.evaluateDelegate evaluateDoctorSuccess:self.evaluateDoctorDic];
    }
    

    
}


#pragma mark alertView Delegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex{
    if (buttonIndex==0) {
        return;
    }else{
        [self cancelPreOrderMethodWithDic:self.cancelPreorderDic];
    
    }


}








@end
