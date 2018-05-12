//
//  DoctorDetailDateCell.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCDoctorSchedulingDateCell.h"

typedef NS_ENUM(NSInteger, SCDoctorDetailDateViewType) {
    SCDoctorDetailDateViewTypeNormal,
    SCDoctorDetailDateViewTypeSelected,
    SCDoctorDetailDateViewTypeDisabled,
};

@interface SCDoctorSchedulingDateCell ()
{
//    BOOL isSelectedDisable;//是否点击约满按钮
//    NSInteger time;//时间
}
@property (nonatomic, strong) UILabel *titleLabel;
@property (nonatomic, strong) UIScrollView *myScrollView;
//@property (nonatomic, strong) UIButton *showAllBtn;
//@property (nonatomic, strong) NSMutableArray *fullViewArray;//约满了的views
//@property (nonatomic, strong) NSTimer *timer;

@end

@implementation SCDoctorSchedulingDateCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        self.isShowAllView = NO;
        [self setupView];
        [self bindViewModel];
    }
    return self;
}

//-(NSMutableArray *)fullViewArray {
//    if (_fullViewArray == nil) {
//        _fullViewArray = [NSMutableArray array];
//    }
//    return _fullViewArray;
//}

#pragma mark    - setupView

- (void)setupView {
    self.backgroundColor = [UIColor bc1Color];
    self.contentView.backgroundColor = [UIColor bc1Color];
    
    WS(weakSelf)
    self.titleLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc2Color] withFontSize:14];
    self.titleLabel.numberOfLines = 0;
    [self.titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView);
        make.right.equalTo(weakSelf.contentView);
        make.top.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(44);
    }];
    //第1条横线
    UIView *scrollTopLine = [UISetupView setupLineViewWithSuperView:self.contentView];
    [scrollTopLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(weakSelf.contentView);
        make.top.equalTo(weakSelf.titleLabel.mas_bottom);
        make.height.mas_equalTo(0.5);
    }];
    
    UILabel *paibaiTitle = [UISetupView setupLabelWithSuperView:self.contentView withText:@"排\n班" withTextColor:[UIColor tc3Color] withFontSize:12];
    paibaiTitle.textAlignment = NSTextAlignmentCenter;
    paibaiTitle.numberOfLines = 2;
    [paibaiTitle mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView);
        make.top.equalTo(scrollTopLine.mas_bottom);
        make.width.mas_equalTo(30);
        make.height.mas_equalTo(50);
    }];
    
    UILabel *amTitle = [UISetupView setupLabelWithSuperView:self.contentView withText:@"上\n午" withTextColor:[UIColor tc3Color] withFontSize:12];
    amTitle.textAlignment = NSTextAlignmentCenter;
    amTitle.numberOfLines = 2;
    [amTitle mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView);
        make.top.equalTo(paibaiTitle.mas_bottom);
        make.width.mas_equalTo(30);
        make.height.mas_equalTo(65);
    }];
    
    UILabel *pmTitle = [UISetupView setupLabelWithSuperView:self.contentView withText:@"下\n午" withTextColor:[UIColor tc3Color] withFontSize:12];
    pmTitle.textAlignment = NSTextAlignmentCenter;
    pmTitle.numberOfLines = 2;
    [pmTitle mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView);
        make.top.equalTo(amTitle.mas_bottom);
        make.width.mas_equalTo(30);
        make.height.mas_equalTo(65);
    }];
    
    //第1条竖线
    UIView *line1 = [UISetupView setupLineViewWithSuperView:self.contentView];
    [line1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(paibaiTitle.mas_right);
        make.top.equalTo(weakSelf.titleLabel.mas_bottom);
        make.width.mas_equalTo(0.5);
        make.bottom.equalTo(pmTitle);
    }];
    
    self.myScrollView = [UISetupView setupScrollViewWithSuperView:self.contentView withDelegate:self withPagingEnabled:NO];
    [self.myScrollView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView);
        make.left.equalTo(line1.mas_right);
        make.top.equalTo(scrollTopLine.mas_bottom);
        make.height.mas_equalTo(180);
    }];

    //第2条横线
    UIView *line2 = [UISetupView setupLineViewWithSuperView:self.contentView];
    [line2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(paibaiTitle.mas_bottom);
        make.height.mas_equalTo(0.5);
        make.left.right.equalTo(weakSelf.contentView);
    }];
    //第3条横线
    UIView *line3 = [UISetupView setupLineViewWithSuperView:self.contentView];
    [line3 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(amTitle.mas_bottom);
        make.height.mas_equalTo(0.5);
        make.left.right.equalTo(weakSelf.contentView);
    }];
    
    
    //第4条横线
    UIView *scrollBottomLine = [UISetupView setupLineViewWithSuperView:self.contentView];
    [scrollBottomLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(weakSelf.contentView);
        make.top.equalTo(weakSelf.myScrollView.mas_bottom);
        make.height.mas_equalTo(0.5);
    }];

//    self.showAllBtn = [UISetupView setupButtonWithSuperView:self.contentView withTitleToStateNormal:@"显示全部排班" withTitleColorToStateNormal:[UIColor tc1Color] withTitleFontSize:14 withAction:^(UIButton *sender) {
//        
//        if (isSelectedDisable) {
//            if (weakSelf.selectedDisableBlock) {
//                weakSelf.selectedDisableBlock();
//            }
//        }else {
//            sender.selected = !sender.selected;
//            weakSelf.isShowAllView = !weakSelf.isShowAllView;
//            [weakSelf changeShowView];
//        }
//    }];
//    [self.showAllBtn setTitle:@"显示可约排班" forState:UIControlStateSelected];
//    [self.showAllBtn mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.right.equalTo(weakSelf.contentView);
//        make.top.equalTo(weakSelf.myScrollView.mas_bottom);
//        make.height.mas_equalTo(44);
//    }];
    

}

//画日期
-(void)setupDateView {
    
    WS(weakSelf)
    
    for (int i = 0; i < self.viewModel.dates.count; i++) {
        
        UIView *view = [self setupDateViewWithNumber:i withModel:self.viewModel.dates[i] withType:SCDoctorDetailDateViewTypeNormal withAM:NO withScheduleModel:nil withDoctorInfo:nil];
        [self.myScrollView addSubview:view];
        [view mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.mas_equalTo(i*50);
            make.top.equalTo(weakSelf.myScrollView);
            make.width.mas_equalTo(50);
            make.height.mas_equalTo(50);
            if (i == self.viewModel.dates.count-1) {
                make.right.equalTo(weakSelf.myScrollView);
            }
        }];
    }
}

//画上下午
-(void)setupContentView {
    WS(weakSelf)
    
    for (int i = 0; i < self.viewModel.dates.count; i++) {
        UIView *view = nil;
        
        SCAppointmentDoctorDateModel *model = self.viewModel.dates[i];
        
        for (int j = 0; j < self.viewModel.model.schedule.count; j++) {
         
            Schedule *schedule = self.viewModel.model.schedule[j];
            
            if ([model.date isEqualToString:schedule.scheduleDate] && ([schedule.timeRange intValue]==1 || [schedule.timeRange intValue]==2)) {
                SCAppointmentDoctorDateModel *model1 = [SCAppointmentDoctorDateModel new];
                model1.week = schedule.visitLevel;
                model1.showDate = [NSString stringWithFormat:@"%@元",schedule.visitCost];
                
                BOOL isAm = YES;
                if ([schedule.timeRange intValue]==2) {
                    isAm = NO;
                }
                if ([schedule.isFull boolValue] == YES) {
                    view = [self setupDateViewWithNumber:i withModel:model1 withType:SCDoctorDetailDateViewTypeSelected withAM:isAm withScheduleModel:schedule withDoctorInfo:self.viewModel.model.doctorInfo];
                }else {
                    view = [self setupDateViewWithNumber:i withModel:model1 withType:SCDoctorDetailDateViewTypeDisabled withAM:isAm withScheduleModel:schedule withDoctorInfo:self.viewModel.model.doctorInfo];
                }
                
                [self.myScrollView addSubview:view];
                [view mas_makeConstraints:^(MASConstraintMaker *make) {
                    make.left.mas_equalTo(i*50);
                    if (isAm) {
                        make.top.equalTo(weakSelf.myScrollView).offset(50);
                    }else {
                        make.top.equalTo(weakSelf.myScrollView).offset(50+65);
                    }
                    make.width.mas_equalTo(50);
                    make.height.mas_equalTo(65);
                    //            if (i == self.viewModel.dates.count-1) {
                    //                make.right.equalTo(weakSelf.myScrollView);
                    //            }
                }];

            }
        }
        
//        [self.myScrollView addSubview:view];
//        [view mas_makeConstraints:^(MASConstraintMaker *make) {
//            make.left.mas_equalTo(i*50);
//            make.top.equalTo(weakSelf.myScrollView).offset(50);
//            make.width.mas_equalTo(50);
//            make.height.mas_equalTo(65);
////            if (i == self.viewModel.dates.count-1) {
////                make.right.equalTo(weakSelf.myScrollView);
////            }
//        }];
    }

}


//画线
-(void)setupLine {
    
    WS(weakSelf)
    for (int i = 0; i < 7; i++) {
        UIView *line = [UISetupView setupLineViewWithSuperView:self.myScrollView];
        [line mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(weakSelf.myScrollView).offset((i+1)*50);
            make.top.height.equalTo(weakSelf.myScrollView);
            make.width.mas_equalTo(0.5);
        }];
    }

}

#pragma mark    - method
-(UIView *)setupDateViewWithNumber:(int)i withModel:(SCAppointmentDoctorDateModel *)model withType:(SCDoctorDetailDateViewType)type withAM:(BOOL)isAM withScheduleModel:(Schedule *)schedule withDoctorInfo:(DoctorInfo *)doctorInfo{
    UIView *dateView = [[UIView alloc]init];
    CGFloat height = 50;
    if (type != SCDoctorDetailDateViewTypeNormal) {
        height = 65;
    }
    dateView.frame = CGRectMake(0, 0, 50, height);
    dateView.backgroundColor = [UIColor bc1Color];
    
    if (type == SCDoctorDetailDateViewTypeNormal || type == SCDoctorDetailDateViewTypeSelected) {
        
        NSString *text = [NSString stringWithFormat:@"%@\n%@",model.week,model.showDate];
        
        UILabel *dateLabel = [UISetupView setupLabelWithSuperView:dateView withText:text withTextColor:[UIColor tc3Color] withFontSize:12];
        dateLabel.textAlignment = NSTextAlignmentCenter;
        dateLabel.tag = 1001;
        dateLabel.numberOfLines = 0;
//        // 设置段落
//        NSMutableParagraphStyle * paragraphStyle = [[NSMutableParagraphStyle alloc] init];
//        paragraphStyle.lineSpacing = 2;
//        // NSKernAttributeName字体间距
//        NSDictionary *attributes = @{ NSParagraphStyleAttributeName:paragraphStyle,NSKernAttributeName:@1.5f};
//        NSMutableAttributedString * attriStr = [[NSMutableAttributedString alloc] initWithString:text attributes:attributes];
//        // 创建文字属性
//        NSDictionary * attriBute = @{NSForegroundColorAttributeName:[UIColor tc3Color],NSFontAttributeName:[UIFont systemFontOfSize:12]};
//        
//        [attriStr addAttributes:attriBute range:NSMakeRange([text rangeOfString:model.showDate].location, model.showDate.length)];
//        dateLabel.attributedText = attriStr;
//        dateLabel.textAlignment = NSTextAlignmentCenter;

        [dateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerY.centerX.top.equalTo(dateView);
            make.left.equalTo(dateView).offset(3);
        }];
//
//        UILabel *weekLabel = [UISetupView setupLabelWithSuperView:dateView withText:model.week withTextColor:[UIColor tc3Color] withFontSize:12];
//        weekLabel.tag = 1000;
//        weekLabel.numberOfLines = 0;
//        [weekLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//            make.left.right.equalTo(dateView);
//            make.top.equalTo(dateView).offset(11);
//        }];
//        
//        UILabel *dateLabel = [UISetupView setupLabelWithSuperView:dateView withText:model.showDate withTextColor:[UIColor tc3Color] withFontSize:12];
//        dateLabel.tag = 1001;
//        [dateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//            make.left.right.equalTo(dateView);
//            make.top.equalTo(weekLabel.mas_bottom);
//        }];
        
        if (type == SCDoctorDetailDateViewTypeNormal) {
            dateView.backgroundColor = [UIColor bc1Color];
        }else {
            dateView.backgroundColor = [UIColor bc7Color];
//            weekLabel.textColor = [UIColor tc0Color];
            dateLabel.textColor = [UIColor tc0Color];
        }

    }else {
        UILabel *dateLabel = [UISetupView setupLabelWithSuperView:dateView withText:@"约满" withTextColor:[UIColor tc2Color] withFontSize:12];
        dateLabel.textAlignment = NSTextAlignmentCenter;
        dateLabel.tag = 1001;
        [dateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerY.centerX.left.top.equalTo(dateView);
        }];
        dateView.backgroundColor = [UIColor bc2Color];
//        dateLabel.hidden = YES;
//        [self.fullViewArray addObject:dateLabel];
    }
    
    if (type == SCDoctorDetailDateViewTypeSelected) {
        WS(weakSelf)
        @weakify(schedule)
        @weakify(doctorInfo)
        dateView.tappedBlock = ^(UITapGestureRecognizer *tap){
            @strongify(schedule)
            @strongify(doctorInfo)
            
//            [weakSelf stopTime];
            if (weakSelf.selectedBlock) {
                weakSelf.selectedBlock(schedule,doctorInfo);
            }
        };

    }else if (type == SCDoctorDetailDateViewTypeDisabled){
//        WS(weakSelf)
//        dateView.tappedBlock = ^(UITapGestureRecognizer *tap){
//            if (weakSelf.showAllBtn.selected || isSelectedDisable) {
//                isSelectedDisable = YES;
//                
//                [weakSelf stopTime];
//
//                weakSelf.timer = [NSTimer scheduledTimerWithTimeInterval:1 target:self selector:@selector(changeBtnType) userInfo:nil repeats:YES];
//                time = 3;
//                [[NSRunLoop currentRunLoop] addTimer: weakSelf.timer forMode: NSRunLoopCommonModes];
//                [weakSelf.timer fire];
//            }
//        };
        
        
    }
    
    return dateView;
}

#pragma mark    - bindViewModel
-(void)bindViewModel {
    
    WS(weakSelf)
    [RACObserve(self, viewModel)subscribeNext:^(SCDoctorSchedulingViewModel *viewModel) {
        
        
        if (viewModel) {
            if (viewModel.model.doctorInfo.deptName.length) {
                weakSelf.titleLabel.text = [NSString stringWithFormat:@"    %@-%@",viewModel.model.doctorInfo.deptName,viewModel.model.doctorInfo.hosName];
            }
            [weakSelf clearScrollView];
            [weakSelf setupDateView];
            [weakSelf setupContentView];
            [weakSelf setupLine];
        }
    }];

}

- (void)clearScrollView {
    NSArray *views = [self.myScrollView subviews];
    for (UIView *view in views) {
        [view removeFromSuperview];
    }
}

#pragma mark    - method 
-(void)changeBtnType {
//    if (time <= 0) {
//        [self.showAllBtn setTitle:@"显示全部排班" forState:UIControlStateNormal];
//        self.showAllBtn.selected = YES;
//        isSelectedDisable = NO;
//        [self stopTime];
//        return;
//    }
//
//    [self.showAllBtn setTitle:[NSString stringWithFormat:@"查看同科室其他医生(%lds)",time] forState:UIControlStateNormal];
//    self.showAllBtn.selected = NO;
//    time--;
}

-(void)changeShowView {
//    for (UILabel *view in self.fullViewArray) {
//        view.hidden = !self.isShowAllView;
//        if (self.isShowAllView) {
//            view.superview.backgroundColor = [UIColor bc2Color];
//        }else {
//            view.superview.backgroundColor = [UIColor whiteColor];
//        }
//    }
}

-(void)stopTime {
//    [self.timer invalidate];
//    self.timer = nil;
}

+(CGFloat)cellHeight {
//    return 194+44;
    return 224;
}



@end
