//
//  PayNoticeCell.m
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "PayNoticeCell.h"

@implementation PayNoticeCell

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
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if(self)
    {
        self.backgroundColor = [UIColor clearColor];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        [self SetUpview];
        [self bindModel];
        
    }
    return self;
}
-(void)SetUpview
{
    
    WS(weakSelf);
    
    self.noticeDateLabel = [self setUpLabel:CGRectZero text:@"" font:[UIFont systemFontOfSize:14] textColor:[UIColor whiteColor] textAlignment:NSTextAlignmentCenter parent:self.contentView];
    self.noticeDateLabel.layer.masksToBounds =YES;
    self.noticeDateLabel.layer.cornerRadius =10;
    self.noticeDateLabel.backgroundColor = [UIColor colorWithHexString:@"#d1d1d1"];
    [self.noticeDateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX .equalTo(weakSelf.contentView.mas_centerX);
        make.top.equalTo(weakSelf.contentView.mas_top).offset(15.0);
        make.height.equalTo(@20);
        make.width.equalTo(@100);
    }];


    
    self.bgview = [[UIView alloc] initWithFrame:CGRectZero];
    self.bgview.backgroundColor =[UIColor whiteColor];
    self.bgview.layer.masksToBounds=YES;
    self.bgview.layer.cornerRadius=5;
    [self.contentView addSubview:self.bgview];
    [self.bgview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView.mas_left).offset(15);
        make.right.equalTo(weakSelf.contentView.mas_right).offset(-15);
        make.top.equalTo(weakSelf.noticeDateLabel.mas_bottom).offset(12.5);
        make.height.equalTo(@255);
    }];
    
    
 
    
    self.title = [self setUpLabel:CGRectZero text:@"" font:[UIFont systemFontOfSize:14] textColor:[UIColor tc1Color] textAlignment:NSTextAlignmentLeft parent:self.bgview];
    [self.title mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.bgview.mas_left).offset(10);
        make.right.equalTo(weakSelf.bgview.mas_centerX).offset(-10);
        make.top.equalTo(weakSelf.bgview.mas_top).offset(15);
        make.height.equalTo(@14);
    }];
    
    
    
    self.payStatusLabel= [self setUpLabel:CGRectZero text:@"" font:[UIFont systemFontOfSize:14] textColor:[UIColor tc2Color] textAlignment:NSTextAlignmentRight parent:self.bgview];
    [self.payStatusLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.bgview.mas_centerX).offset(10);
        make.right.equalTo(weakSelf.bgview.mas_right).offset(-10);
        make.top.equalTo(weakSelf.bgview.mas_top).offset(15);
        make.height.equalTo(@14);
    }];
    
    
    UIView * lineview  =[[UIView alloc] initWithFrame:CGRectZero];
    lineview.backgroundColor =  [UIColor dc4Color];;
    [self.bgview addSubview:lineview];
    [lineview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.bgview.mas_left).offset(10);
        make.right.equalTo(weakSelf.bgview.mas_right).offset(-10);
        make.top.equalTo(weakSelf.title.mas_bottom).offset(15);
        make.height.equalTo(@0.5);
    }];
    
    self.hospitalNameLabel = [self setUpLabel:CGRectZero text:@"" font:[UIFont systemFontOfSize:16] textColor:[UIColor tc1Color] textAlignment:NSTextAlignmentLeft parent:self.bgview];
    
    [self.hospitalNameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.bgview.mas_left).offset(10);
        make.right.equalTo(weakSelf.bgview.mas_right).offset(-10);
        make.top.equalTo(lineview.mas_bottom).offset(15);
        make.height.equalTo(@14);
    }];
    
 
    
    self.departLabel = [self setUpLabel:CGRectZero text:@"" font:[UIFont systemFontOfSize:16] textColor:[UIColor tc1Color] textAlignment:NSTextAlignmentLeft parent:self.bgview];
    [self.departLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.bgview.mas_left).offset(10);
        make.right.equalTo(weakSelf.bgview.mas_right).offset(-10);
        make.top.equalTo(weakSelf.hospitalNameLabel.mas_bottom).offset(10);
        make.height.equalTo(@16);
    }];
    
    
    
    
    
    
    self.patientNameLabel = [self setUpLabel:CGRectZero text:@"就诊人:" font:[UIFont systemFontOfSize:16]  textColor:[UIColor tc1Color] textAlignment:NSTextAlignmentLeft parent:self.bgview];
    [self.patientNameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.bgview.mas_right).offset(-10);
        make.left.equalTo(weakSelf.bgview.mas_left).offset(10);
        make.top.equalTo(weakSelf.departLabel.mas_bottom).offset(10);
        make.height.equalTo(@16);
    }];
    
    
    
   
    

    
    
    self.payLabel = [self setUpLabel:CGRectZero text:@"" font:[UIFont systemFontOfSize:16] textColor:[UIColor tc1Color] textAlignment:NSTextAlignmentLeft parent:self.bgview];
    [self.payLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.patientNameLabel.mas_left);
        make.right.equalTo(weakSelf.bgview.mas_right).offset(-10);
        make.top.equalTo(weakSelf.patientNameLabel.mas_bottom).offset(10);
        make.height.equalTo(@16);
        
    }];
    
    
    
    self.dateLabel = [self setUpLabel:CGRectZero text:@"" font:[UIFont systemFontOfSize:16] textColor:[UIColor tc1Color] textAlignment:NSTextAlignmentLeft parent:self.bgview];
    [self.dateLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.patientNameLabel.mas_left);
        make.right.equalTo(weakSelf.bgview.mas_right).offset(-10);
        make.top.equalTo(weakSelf.payLabel.mas_bottom).offset(10);
        make.height.equalTo(@16);

    }];
    
    
    
    

    
  
    
    self.endline   =[[UIView alloc] initWithFrame:CGRectZero];
    self.endline.backgroundColor =  [UIColor dc4Color];;
    [self.bgview addSubview:self.endline];
    [self.endline mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.bgview.mas_left).offset(10);
        make.right.equalTo(weakSelf.bgview.mas_right).offset(-10);
        make.top.equalTo(weakSelf.dateLabel.mas_bottom).offset(15);
        make.height.equalTo(@0.5);
    }];
    
    self.oprationLabel = [self setUpLabel:CGRectZero text:@"" font:[UIFont systemFontOfSize:12] textColor:[UIColor tc2Color] textAlignment:NSTextAlignmentLeft parent:self.bgview];
    [self.oprationLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.bgview.mas_left).offset(10);
        make.right.equalTo(weakSelf.bgview.mas_right).offset(-15);
        make.top.equalTo(weakSelf.endline.mas_bottom).offset(15);
        make.height.equalTo(@12);
    }];
    

    
//    self.arroImageview = [[UIImageView alloc] initWithFrame:CGRectZero];
//    self.arroImageview.image = [UIImage imageNamed:@"systemRightArrow"];
//    //14/28l
//    [self.contentView addSubview:self.arroImageview];
//    
//    
//    [self.arroImageview mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.centerY.equalTo(self.oprationLabel.mas_centerY);
//        make.width.equalTo(@7);
//        make.height.equalTo(@14);
//        make.right.equalTo(self.bgview.mas_right).offset(-10);
//        
//    }];

    
    
    [self.bgview mas_remakeConstraints:^(MASConstraintMaker *make) {
        
        make.left.equalTo(weakSelf.contentView.mas_left).offset(15);
        make.right.equalTo(weakSelf.contentView.mas_right).offset(-15);
        make.top.equalTo(weakSelf.noticeDateLabel.mas_bottom).offset(12.5);
        make.bottom.equalTo(weakSelf.oprationLabel.mas_bottom).offset(15);
    }];
    

    
    [self.contentView mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(weakSelf);
        make.bottom.equalTo(weakSelf.bgview.mas_bottom);
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
-(void )bindModel
{
    
    WS(weakSelf);
    [RACObserve(self, model) subscribeNext:^(PayNoticeModel * x) {
        if(x)
        {
            
            CGSize size = [x.createDate boundingRectWithSize:CGSizeMake(180.0f, 20000.0f)   options:NSStringDrawingUsesLineFragmentOrigin attributes:nil context:nil].size;
            weakSelf.noticeDateLabel.text=x.createDate;
            [weakSelf.noticeDateLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
                make.top.equalTo(weakSelf.contentView.mas_top).offset(10);
                make.height.equalTo(@20);
                make.centerX.equalTo(weakSelf.contentView.mas_centerX);
                make.width.mas_equalTo(size.width+24+10);
            }];
            weakSelf.title.text  = x.payTypeName;
            weakSelf.hospitalNameLabel.text =x.hospitalName;
            
            self.oprationLabel.text =[NSString stringWithFormat:@"订单号：%@",x.orderId];
            //支付通知只有支付成功消息
            if([x.payStatus intValue]==0)
            {
                weakSelf.payStatusLabel.text=@"支付失败";
//                weakSelf.payStatusLabel.textColor = [UIColor tc2Color];
            }
            else
            {
                weakSelf.payStatusLabel.text=@"支付成功";
//                weakSelf.payStatusLabel.textColor = [UIColor tc2Color];
            }
            
            
         
            
            
            
            weakSelf.dateLabel.alpha=1;
            if([x.payType intValue]==2)
            {
              
                weakSelf.patientNameLabel.text =[NSString stringWithFormat:@"就诊人: %@",x.patientName];
                weakSelf.departLabel.text =[NSString stringWithFormat:@"%@ %@ %@",x.department ,x.doctorName,x.clinicType];
                weakSelf.payLabel.text = [NSString stringWithFormat:@"挂号金额: %@元",x.price];
                weakSelf.dateLabel.text =x.orderTime;
                
                [weakSelf.endline mas_remakeConstraints:^(MASConstraintMaker *make) {
                    make.left.equalTo(weakSelf.bgview.mas_left).offset(10);
                    make.right.equalTo(weakSelf.bgview.mas_right).offset(-10);
                    make.top.equalTo(weakSelf.dateLabel.mas_bottom).offset(15);
                    make.height.equalTo(@0.5);
                }];
                weakSelf.dateLabel.textColor = [UIColor tc2Color];
                weakSelf.payLabel.textColor  =[UIColor tc1Color];
                
            }
            else
            {
            
                 weakSelf.departLabel.text =[NSString stringWithFormat:@"支付金额: %@",x.price];
                weakSelf.patientNameLabel.text = [NSString stringWithFormat:@"开方时间: %@",x.prescriptionTime];
                weakSelf.payLabel.text = [NSString stringWithFormat:@"处方号码: %@",x.prescriptionCode];
                
                weakSelf.dateLabel.alpha=0;
                
                
                weakSelf.payLabel.textColor =  [UIColor tc2Color];
                
                [weakSelf.endline mas_remakeConstraints:^(MASConstraintMaker *make) {
                    make.left.equalTo(weakSelf.bgview.mas_left).offset(10);
                    make.right.equalTo(weakSelf.bgview.mas_right).offset(-10);
                    make.top.equalTo(weakSelf.payLabel.mas_bottom).offset(15);
                    make.height.equalTo(@0.5);
                }];
                
            }
            
            
            
        }
    }];
    
}

@end
