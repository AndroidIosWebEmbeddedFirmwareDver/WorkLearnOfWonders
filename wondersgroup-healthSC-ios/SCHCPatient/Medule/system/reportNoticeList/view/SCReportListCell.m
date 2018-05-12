//
//  SystemDetialCell.m
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCReportListCell.h"

@implementation SCReportListCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

-(id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self =[super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if(self)
    {
        self.backgroundColor = [UIColor clearColor];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        [self setUpView];
        [self bindModel];
    }
    return self;
}
-(void)setUpView
{
    
    
    UIFont * usuallFont =[UIFont systemFontOfSize:14];
    UIColor *usualtextcolor = [UIColor colorWithHexString:@"#666666"];
    NSTextAlignment usualTextAligment = NSTextAlignmentLeft;
    
    self.dateLabel = [self setUpLabel:CGRectZero text:@"" font:[UIFont systemFontOfSize:14] textColor:[UIColor whiteColor] textAlignment:NSTextAlignmentCenter parent:self.contentView];
    self.dateLabel.layer.masksToBounds =YES;
    self.dateLabel.layer.cornerRadius =10;
    self.dateLabel.backgroundColor = [UIColor colorWithHexString:@"#d1d1d1"];
    [self.dateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX .equalTo(self.contentView.mas_centerX);
        make.top.equalTo(self.contentView.mas_top).offset(15.0);
        make.height.equalTo(@20);
        make.width.equalTo(@100);
    }];
    
    self.bgView = [[UIView alloc] initWithFrame:CGRectZero];
    self.bgView.backgroundColor =[UIColor whiteColor];
    self.bgView.layer.masksToBounds=YES;
    self.bgView.layer.cornerRadius=5;
    [self.contentView addSubview:self.bgView];
    [self.bgView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView.mas_left).offset(15);
        make.right.equalTo(self.contentView.mas_right).offset(-15);
        make.top.equalTo(self.dateLabel.mas_bottom).offset(12.5);
        make.height.equalTo(@255);
    }];
    
   
    
    self.titleLabel = [self setUpLabel:CGRectZero text:@"" font:[UIFont systemFontOfSize:16] textColor:[UIColor tc1Color] textAlignment:NSTextAlignmentLeft parent:self.bgView];
    [self.titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.bgView.mas_left).offset(10);
        make.top.equalTo(self.bgView.mas_top).offset(15);
        make.right.equalTo(self.bgView.mas_right).offset(-10);
        make.height.equalTo(@16);
    }];
    
    
    UIView * lineview = [[UIView alloc] initWithFrame:CGRectZero];
    lineview.backgroundColor = [UIColor colorWithHexString:@"#f1f1f1"];
    [self.bgView addSubview:lineview];
    
    [lineview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.bgView.mas_left).offset(10);
        make.right.equalTo(self.bgView.mas_right).offset(-10);
        make.top.equalTo(self.titleLabel.mas_bottom).offset(15);
        make.height.equalTo(@0.5);
        
    }];
    
   
    
    //name
    UILabel * nametitleLabel = [self setUpLabel:CGRectZero text:@"姓名:" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    [nametitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.bgView.mas_left).offset(10);
        make.top.equalTo(lineview.mas_bottom).offset(15);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
    
    self.nameLabel = [self setUpLabel:CGRectZero text:@"" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    
    [self.nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(nametitleLabel.mas_right);
        make.top.equalTo(lineview.mas_bottom).offset(15);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
    
   
    //sex
    
    UILabel * sextitleLabel = [self setUpLabel:CGRectZero text:@"性别:" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    [sextitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.bgView.mas_centerX).offset(10);
        make.top.equalTo(lineview.mas_bottom).offset(15);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
    
    self.sexLabel = [self setUpLabel:CGRectZero text:@"" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    
    [self.sexLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(sextitleLabel.mas_right);
        make.top.equalTo(lineview.mas_bottom).offset(15);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
   
    //age
   
    
    UILabel * agetitleLabel = [self setUpLabel:CGRectZero text:@"年龄:" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    [agetitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.bgView.mas_left).offset(10);
        make.top.equalTo(self.nameLabel.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
    
    self.ageLabel = [self setUpLabel:CGRectZero text:@"" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    
    [self.ageLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(agetitleLabel.mas_right);
        make.top.equalTo(self.nameLabel.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
    
    //hospital
    
    UILabel * hospitaltitleLabel = [self setUpLabel:CGRectZero text:@"医院:" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    [hospitaltitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.bgView.mas_left).offset(10);
        make.top.equalTo(self.ageLabel.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
    
    
    self.hospitalNameLabel = [self setUpLabel:CGRectZero text:@"" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    
    [self.hospitalNameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(agetitleLabel.mas_right);
        make.top.equalTo(self.ageLabel.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.right.equalTo(self.bgView.mas_right).offset(-15);
    }];
    
    

    //checkproject
    UILabel * checkProjicttitleLabel = [self setUpLabel:CGRectZero text:@"检查项目:" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    [checkProjicttitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.bgView.mas_left).offset(10);
        make.top.equalTo(self.hospitalNameLabel.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
    
    self.checkProjectLabel = [self setUpLabel:CGRectZero text:@"" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    
    [self.checkProjectLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(agetitleLabel.mas_right);
        make.top.equalTo(self.hospitalNameLabel.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.right.equalTo(self.bgView.mas_right).offset(-15);
    }];
    
    //checkpart
    UILabel * checkParttitleLabel = [self setUpLabel:CGRectZero text:@"检查部位:" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    [checkParttitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.bgView.mas_left).offset(10);
        make.top.equalTo(self.checkProjectLabel.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
    
    self.checkPartLabel = [self setUpLabel:CGRectZero text:@"" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    
    [self.checkPartLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(agetitleLabel.mas_right);
        make.top.equalTo(self.checkProjectLabel.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
    
    //demartment
    
    UILabel * departmenttitleLabel = [self setUpLabel:CGRectZero text:@"科室:" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    [departmenttitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.bgView.mas_centerX).offset(10);
        make.top.equalTo(self.checkProjectLabel.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
    
    self.departmentLable = [self setUpLabel:CGRectZero text:@"" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    
    [self.departmentLable mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(departmenttitleLabel.mas_right);
        make.top.equalTo(self.checkProjectLabel.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];

    
    
   
    //checkdate
    UILabel * checkdatetitleLabel = [self setUpLabel:CGRectZero text:@"检查日期:" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    [checkdatetitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.bgView.mas_left).offset(10);
        make.top.equalTo(self.checkPartLabel.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
    
    self.checkDateLabel = [self setUpLabel:CGRectZero text:@"" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    
    [self.checkDateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(checkdatetitleLabel.mas_right);
        make.top.equalTo(self.checkPartLabel.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
    
    //demartment
    
    UILabel * reportTitleLabel = [self setUpLabel:CGRectZero text:@"报告日期:" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    [reportTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.bgView.mas_centerX).offset(10);
        make.top.equalTo(self.departmentLable.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.width.equalTo(@70);
    }];
    
    self.reportDateLabel = [self setUpLabel:CGRectZero text:@"" font:usuallFont textColor:usualtextcolor textAlignment:usualTextAligment parent:self.bgView];
    
    [self.reportDateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(departmenttitleLabel.mas_right);
        make.top.equalTo(self.departmentLable.mas_bottom).offset(10);
        make.height.equalTo(@14);
        make.right.equalTo(self.bgView.mas_right).offset(-10);
    }];
    

    UIView * lineend = [[UIView alloc] initWithFrame:CGRectZero];
    lineend.backgroundColor = [UIColor colorWithHexString:@"#f1f1f1"];
    [self.bgView addSubview:lineend];
    
    [lineend mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.bgView.mas_left).offset(10);
        make.right.equalTo(self.bgView.mas_right).offset(-10);
        make.top.equalTo(self.reportDateLabel.mas_bottom).offset(15);
        make.height.equalTo(@0.5);
        
    }];

    

    
    
    UILabel * openLinkLabel = [[UILabel alloc] initWithFrame:CGRectZero];
    openLinkLabel.textAlignment = NSTextAlignmentLeft;
    openLinkLabel.text=@"查看详情";
    openLinkLabel.font = [UIFont systemFontOfSize:14];
    openLinkLabel.textColor = [UIColor colorWithHexString:@"#9b9b9b"];
    [self.contentView addSubview:openLinkLabel];
    
    [openLinkLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(lineend.mas_bottom).offset(15);
        make.height.equalTo(@14);
        make.width.equalTo(@100);
        make.left.equalTo(self.bgView.mas_left).offset(10);
    }];
    
    
    UIImageView  * arroImageview = [[UIImageView alloc] initWithFrame:CGRectZero];
    arroImageview.image = [UIImage imageNamed:@"systemRightArrow"];
    //14/28l
    [self.contentView addSubview:arroImageview];
    
    arroImageview.alpha=0;
    [arroImageview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(openLinkLabel.mas_centerY);
        make.width.equalTo(@7);
        make.height.equalTo(@14);
        make.right.equalTo(self.bgView.mas_right).offset(-10);
        
    }];

    
    [self.bgView mas_remakeConstraints:^(MASConstraintMaker *make) {
        
        make.top.equalTo(self.dateLabel.mas_bottom).offset(10);
        make.left.equalTo(self.contentView.mas_left).offset(15);
        make.right.equalTo(self.contentView.mas_right).offset(-15);
        make.bottom.equalTo(openLinkLabel.mas_bottom).offset(15);
    }];
    
    
    
    [self.contentView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self);
        make.bottom.equalTo(self.bgView.mas_bottom);
    }];
    
    
    

}


-(UILabel *) setUpLabel:(CGRect)frame text:(NSString*)text font:(UIFont*)font textColor:(UIColor*)textcolor textAlignment:(NSTextAlignment) textAlignment parent:(UIView*)parentview
{
    UILabel * label = [[UILabel alloc] initWithFrame:frame];
    label.font = font;
    label.textColor = textcolor;
    label.textAlignment = textAlignment;
    label.text = text;
    [parentview addSubview:label];
    return label;
}


-(void)bindModel
{
    WS(weakSelf);
    [RACObserve(self, model) subscribeNext:^(SCReportListModel * x) {
        
        if(x)
        {
            
            CGSize size = [x.date boundingRectWithSize:CGSizeMake(180.0f, 20000.0f)   options:NSStringDrawingUsesLineFragmentOrigin attributes:nil context:nil].size;
            weakSelf.dateLabel.text=x.date;
            [weakSelf.dateLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
                make.top.equalTo(self.contentView.mas_top).offset(10);
                make.height.equalTo(@20);
                make.centerX.equalTo(self.contentView.mas_centerX);
                make.width.mas_equalTo(size.width+24);
            }];
            
            weakSelf.titleLabel.text = x.title;
            weakSelf.nameLabel.text = x.paitentName;
            weakSelf.sexLabel.text = [x.sex intValue]==1?@"女":@"男" ;
            weakSelf.ageLabel.text = [NSString stringWithFormat:@"%@",x.age];
            weakSelf.hospitalNameLabel.text =x.hospitalName;
            weakSelf.checkProjectLabel.text =x.checkProject;
            weakSelf.checkPartLabel.text = x.checkPart;
            weakSelf.departmentLable.text = x.department;
            weakSelf.checkDateLabel.text = x.checkDate;
            weakSelf.reportDateLabel.text = x. reportDate;
            
            
            
        
        }
    }];
}
@end
