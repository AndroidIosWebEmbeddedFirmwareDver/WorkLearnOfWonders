//
//  SearchResultDoctorCell.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SearchResultDoctorCell.h"

@implementation SearchResultDoctorCell
-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self createUI];
        [self getDatas];
    }
    return self;
}
-(void)getDatas{
//    self.doctorsNameLab.text = @"略屌";
//    self.doctorsProfessional.text = @"副主任医师";
//    self.doctorsHeadImage.backgroundColor =[UIColor redColor];
//    self.registerLab.text = @"挂号";
//    self.hospitalsNameLab.text = @"四川大学华西第二医院";
//    self.doctorsOfficeLab.text = @"儿科";
//    self.receivePatientNumLab.text = @"520";e
//    self.doctorDetailLab.text = @"打算带回家啊哈哈就是大家啊哈酒阿迪达斯大大的";
        WS(weakSelf);
//    RAC(self.doctorsNameLab,text) = RACObserve(self, model.doctorName);
        [RACObserve(self, model) subscribeNext:^(DoctorssModel *x) {
            if (x) {
                
                    NSString * imageName = @"默认医生女96";
                    if ([x.gender intValue]==2) {
                        imageName = @"默认医生女96";
                    }else{
                       
                        imageName = @"默认医生男96";
                    }
                    [weakSelf.doctorsHeadImage sd_setImageWithURL:[NSURL URLWithString:x.headphoto] placeholderImage:[UIImage imageNamed:imageName]];
                
                
                if (x.doctorName) {
                    NSInteger lengthStr = x.doctorName.length;
                    if (lengthStr>6) {
                        lengthStr = 6;
                    }
                    weakSelf.doctorsNameLab.text = x.doctorName;
                    [self.doctorsNameLab mas_updateConstraints:^(MASConstraintMaker *make) {
                        make.top.equalTo(weakSelf).offset(15);
                        make.left.equalTo(weakSelf.doctorsHeadImage.mas_right).offset(17);
                        make.height.mas_offset(@16);
                        make.width.mas_lessThanOrEqualTo(16 * lengthStr);
                    }];
                }

                if (x.doctorTitle) {
                    weakSelf.doctorsProfessional.text = x.doctorTitle;
                }
                
                if (x.hosName) {
                     weakSelf.hospitalsNameLab.text = [NSString stringWithFormat:@"%@",x.hosName];
                    NSInteger lengthStr = x.hosName.length;
                    if (lengthStr>10) {
                        lengthStr = 10;
                    }
                    
                    [self.hospitalsNameLab mas_updateConstraints:^(MASConstraintMaker *make) {
                        make.top.equalTo(weakSelf.doctorsNameLab.mas_bottom).offset(10);
                        make.left.equalTo(weakSelf.doctorsHeadImage.mas_right).offset(17);
                        make.height.mas_equalTo(@13);
                        make.width.mas_equalTo(13*lengthStr);
                    }];
                    
                }
                
                if (x.deptName) {
                    weakSelf.doctorsOfficeLab.text = x.deptName;
                }
               
                if (x.orderCount) {
                    weakSelf.receivePatientNumLab.text = x.orderCount;
                }
                
                if (x.expertin) {
                    weakSelf.doctorDetailLab.text = [NSString stringWithFormat:@"擅长: %@",x.expertin];
                }
                else {
                    weakSelf.doctorDetailLab.text = @"擅长: ";
                }
                
            }
        }];
        
        
        [RACObserve(self, highLightString) subscribeNext:^(NSString *x) {
            if (x) {
                NSString *doctorName = _model.doctorName;
                NSMutableAttributedString *attrbutedStr = [[NSMutableAttributedString alloc] initWithString:doctorName];
                NSRange range1 = [doctorName rangeOfString:x];
                [attrbutedStr addAttributes:@{NSForegroundColorAttributeName:[UIColor tc5Color]} range:range1];
                weakSelf.doctorsNameLab.attributedText = attrbutedStr;
            }
        }];
        
 

}
-(void)createUI{
    self.doctorsHeadImage = [UIImageView new];
    self.doctorsNameLab  = [UILabel new];
    self.doctorDetailLab = [UILabel new];
    self.doctorsProfessional = [UILabel new];
    self.hospitalsNameLab = [UILabel new];
    self.doctorsOfficeLab = [UILabel new];
    self.receiveLab = [UILabel new];
    self.receivePatientNumLab = [UILabel new];
    self.doctorDetailLab = [UILabel new];
    self.registerLab = [UILabel new];
    [self.contentView addSubview:self.registerLab];
    [self.contentView addSubview:self.doctorsHeadImage];
    [self.contentView addSubview:self.doctorsNameLab];
    [self.contentView addSubview:self.doctorsOfficeLab];
    [self.contentView addSubview:self.doctorsProfessional];
    [self.contentView addSubview:self.hospitalsNameLab];
    [self.contentView addSubview:self.receiveLab];
    [self.contentView addSubview:self.receivePatientNumLab];
    [self.contentView addSubview:self.doctorDetailLab];
    
    WS(weakSelf)
    [self.doctorsHeadImage mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf).offset(15);
        make.left.equalTo(weakSelf).offset(15);
        make.width.height.mas_offset(48);
    }];
    self.doctorsHeadImage.layer.cornerRadius = 24;
    self.doctorsHeadImage.clipsToBounds = YES;
    
    [self.doctorsNameLab mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf).offset(15);
        make.left.equalTo(weakSelf.doctorsHeadImage.mas_right).offset(17);
        make.height.mas_offset(@16);
        make.width.mas_lessThanOrEqualTo(16 * 2);
    }];
    self.doctorsNameLab.font = [UIFont systemFontOfSize:15];
    self.doctorsNameLab.textColor = [UIColor tc2Color];
    
//    NSString * str = @"仪式仪式是多大好几岁";
//    CGRect rect = [str boundingRectWithSize:CGSizeMake(CGFLOAT_MAX,12) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName:[UIFont systemFontOfSize:12.0]} context:nil];
    self.registerLab.textColor = [UIColor stc3Color];
    self.registerLab.font = [UIFont systemFontOfSize:10];
    self.registerLab.textAlignment = NSTextAlignmentCenter;
    self.registerLab.layer.cornerRadius = 8.0f;
    self.registerLab.layer.borderWidth = 0.5;
    self.registerLab.layer.borderColor = self.registerLab.textColor.CGColor;
    self.registerLab.text = @"挂号";

    [self.registerLab mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf).offset(16);
        make.height.mas_equalTo(@17);
        make.right.equalTo(weakSelf).offset(-15);
        make.width.mas_equalTo(@35);
    }];
    
    [self.doctorsProfessional mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.doctorsNameLab.mas_right).offset(10);
        make.centerY.equalTo(self.doctorsNameLab);
        make.height.mas_equalTo(@12);
        make.right.equalTo(self.registerLab.mas_left).offset(-10);
    }];
    self.doctorsProfessional.textColor = [UIColor tc3Color];
    self.doctorsProfessional.font = [UIFont systemFontOfSize:12];
    
    


    self.hospitalsNameLab.font = [UIFont systemFontOfSize:12];
//    self.hospitalsNameLab.numberOfLines = 2;
    [self.hospitalsNameLab mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.doctorsNameLab.mas_bottom).offset(10);
        make.left.equalTo(weakSelf.doctorsHeadImage.mas_right).offset(17);
        make.height.mas_equalTo(@13);
        make.width.mas_equalTo(13*10);
    }];

    self.hospitalsNameLab.textColor = [UIColor tc3Color];

    [self.doctorsOfficeLab mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.hospitalsNameLab.mas_right).offset(6);
        make.top.equalTo(weakSelf.doctorsNameLab.mas_bottom).offset(10);
        make.height.mas_equalTo(@12);
        make.right.equalTo(weakSelf).offset(-15);
    }];
    self.doctorsOfficeLab.font = [UIFont systemFontOfSize:12];
    self.doctorsOfficeLab.textColor = [UIColor tc3Color];

    
    [self.receiveLab mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.height.equalTo(weakSelf.hospitalsNameLab);
        make.top.equalTo(weakSelf.hospitalsNameLab.mas_bottom).offset(10);
        make.width.mas_equalTo(13 * 3);
    }];
    self.receiveLab.font = [UIFont systemFontOfSize:12];
    self.receiveLab.textColor = [UIColor stc1Color];
    self.receiveLab.text = @"接诊量";

    [self.receivePatientNumLab mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.height.equalTo(weakSelf.receiveLab);
        make.left.equalTo(weakSelf.receiveLab.mas_right).offset(10);
        make.right.equalTo(weakSelf);
    }];
    self.receivePatientNumLab.textColor = [UIColor stc1Color];
    self.receivePatientNumLab.font = [UIFont systemFontOfSize:12];
    
    [self.doctorDetailLab mas_makeConstraints:^(MASConstraintMaker *make) {
        make.height.left.equalTo(weakSelf.receiveLab);
        make.right.equalTo(weakSelf).offset(-(43/2));
        make.top.equalTo(weakSelf.receiveLab.mas_bottom).offset(10);
    }];
    self.doctorDetailLab.textColor = [UIColor tc4Color];
    self.doctorDetailLab.font = [UIFont systemFontOfSize:12];
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
