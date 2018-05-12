//
//  ReportNoticeCell.m
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSystemListCell.h"


@implementation SCSystemListCell

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
        self.selectionStyle =UITableViewCellSelectionStyleNone;
        
        self.backgroundColor = [UIColor clearColor];
        [self setUpView];
        [self bindModel];
    }
    return self;
    
}
-(void)setUpView
{
    WS(weakSelf);
    self.dateLabel = [[UILabel alloc] initWithFrame:CGRectZero];
    self.dateLabel.textAlignment = NSTextAlignmentCenter;
    [self.dateLabel.layer setMasksToBounds:YES];
    [self.dateLabel setCornerRadius:10];
    self.dateLabel.font = [UIFont systemFontOfSize:12];
    self.dateLabel.textColor = [UIColor whiteColor];
    self.dateLabel.backgroundColor = [UIColor colorWithHexString:@"#d1d1d1"];
    [self.contentView addSubview:self.dateLabel];
    [self.dateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.contentView.mas_top).offset(10);
        make.height.equalTo(@20);
        make.centerX.equalTo(weakSelf.contentView.mas_centerX);
        make.width.equalTo(@100);
    }];
    
  
    self.bgView =[[UILabel alloc] initWithFrame:CGRectZero];
    self.bgView.backgroundColor = [UIColor whiteColor];
    self.bgView.layer.masksToBounds =YES;
    self.bgView.layer.cornerRadius=5;
    [self.contentView addSubview:self.bgView];
    
    [self.bgView mas_remakeConstraints:^(MASConstraintMaker *make) {
        
        make.top.equalTo(weakSelf.dateLabel.mas_bottom).offset(10);
        make.left.equalTo(weakSelf.contentView.mas_left).offset(15);
        make.right.equalTo(weakSelf.contentView.mas_right).offset(-15);
        make.bottom.equalTo(weakSelf.contentView.mas_bottom);
    }];
    self.titleLabel = [[UILabel alloc] initWithFrame:CGRectZero];
    self.titleLabel.textAlignment = NSTextAlignmentLeft;
    self.titleLabel.font = [UIFont systemFontOfSize:16];
    self.titleLabel.textColor = [UIColor tc1Color];
    self.titleLabel.numberOfLines=1;
    [self.contentView addSubview:self.titleLabel];
    
    [self.titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.bgView.mas_left).offset(10);
        make.right.equalTo(weakSelf.bgView.mas_right).offset(-10);
        make.top.equalTo(weakSelf.bgView.mas_top).offset(15);
        make.height.equalTo(@16);
    }];
    
    
    
    self.lineView = [[UIView alloc] initWithFrame:CGRectZero];
    self.lineView.backgroundColor = [UIColor dc4Color];
    [self.bgView addSubview:self.lineView];
    
    
    [self.lineView mas_makeConstraints:^(MASConstraintMaker *make) {
       
        make.left.equalTo(weakSelf.bgView.mas_left).offset(10);
        make.right.equalTo(weakSelf.bgView.mas_right).offset(-10);
        make.top.equalTo(weakSelf.titleLabel.mas_bottom).offset(10);
        make.height.equalTo(@0.5);
    }];
    
    

    
    self.picView = [[UIImageView alloc]initWithFrame:CGRectZero];
    self.picView.image = [UIImage imageNamed:@""];
    [self.contentView addSubview:self.picView];
    [self.picView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.bgView.mas_left).offset(10);
        make.right.equalTo(weakSelf.bgView.mas_right).offset(-10);
        make.top.equalTo(weakSelf.lineView.mas_bottom).offset(15);
//        make.height.mas_equalTo((SCREEN_WIDTH-50)/scale);
        make.height.mas_equalTo(@0);
    }];
    
    self.desLabel  = [[UILabel alloc] initWithFrame:CGRectZero];
    self.desLabel.textAlignment = NSTextAlignmentLeft;
    self.desLabel.font = [UIFont systemFontOfSize:14];
    self.desLabel.textColor = [UIColor tc2Color];
    self.desLabel.numberOfLines=0;
    [self.bgView addSubview:self.desLabel];
    
    [self.desLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.bgView.mas_left).offset(10);
        make.right.equalTo(weakSelf.bgView.mas_right).offset(-10);
        make.top.equalTo(weakSelf.picView.mas_bottom).offset(15);
        
        //make.bottom.equalTo(weakSelf.bgView.mas_bottom).offset(-15);
        
    }];
    
   
    self.lineviewbottom=[[UIView alloc] initWithFrame:CGRectZero];
    self.lineviewbottom.backgroundColor = [UIColor dc4Color];
    [self.bgView addSubview:self.lineviewbottom];
    
    [self.lineviewbottom mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.bgView.mas_left).offset(10);
        make.right.equalTo(weakSelf.bgView.mas_right).offset(-10);
        make.top.equalTo(weakSelf.desLabel.mas_bottom).offset(10);
        make.height.equalTo(@0.5);
        
    }];
    
   
    
    
    
    self.openLinkLabel = [[UILabel alloc] initWithFrame:CGRectZero];
    self.openLinkLabel.textAlignment = NSTextAlignmentLeft;
    self.openLinkLabel.text=@"查看详情";
    self.openLinkLabel.font = [UIFont systemFontOfSize:14];
    self.openLinkLabel.textColor = [UIColor tc3Color];
    [self.bgView addSubview:self.openLinkLabel];
    
    [self.openLinkLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.lineviewbottom.mas_bottom);
        make.height.mas_equalTo(44.0);
        make.width.equalTo(@100);
        make.left.equalTo(weakSelf.bgView.mas_left).offset(10);
        make.bottom.equalTo(weakSelf.bgView.mas_bottom);
    }];
    
    
   
    
    
    
    
//    self.arroImageview = [[UIImageView alloc] initWithFrame:CGRectZero];
//    self.arroImageview.image = [UIImage imageNamed:@"systemRightArrow"];
//    //14/28l
//    [self.bgView addSubview:self.arroImageview];
//    
//    self.arroImageview.alpha=0;
//    [self.arroImageview mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.centerY.equalTo(weakSelf.openLinkLabel.mas_centerY);
//        make.width.equalTo(@7);
//        make.height.equalTo(@14);
//        make.right.equalTo(weakSelf.bgView.mas_right).offset(-10);
//       
//    }];
//    
    
    [self.bgView mas_remakeConstraints:^(MASConstraintMaker *make) {
        
        make.top.equalTo(weakSelf.dateLabel.mas_bottom).offset(10);
        make.left.equalTo(weakSelf.contentView.mas_left).offset(15);
        make.right.equalTo(weakSelf.contentView.mas_right).offset(-15);
        make.bottom.equalTo(weakSelf.contentView.mas_bottom);
    }];
    

    
    
    
    
    
}
-(void)bindModel
{
  
    
    WS(weakSelf);
    [RACObserve(self, model) subscribeNext:^(SCSystemListModel * x) {
        if(x)
        {
            
            
            CGSize size = [x.date boundingRectWithSize:CGSizeMake(180.0f, 20000.0f)   options:NSStringDrawingUsesLineFragmentOrigin attributes:nil context:nil].size;
//            CGSize size = [x.date sizeWithFont:weakSelf.dateLabel.font constrainedToSize:CGSizeMake(180.0f, 20000.0f) lineBreakMode:NSLineBreakByWordWrapping];
            weakSelf.dateLabel.text=x.date;
            [weakSelf.dateLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
                make.top.equalTo(self.contentView.mas_top).offset(10);
                make.height.equalTo(@20);
                make.centerX.equalTo(self.contentView.mas_centerX);
                make.width.mas_equalTo(size.width+24);
            }];
            
            weakSelf.titleLabel.text = x.title;
           
            if(x.picurl.length==0)
            {
                weakSelf.picView.alpha=0;
                [weakSelf.picView mas_updateConstraints:^(MASConstraintMaker *make) {
                    make.height.mas_equalTo(@0);
                }];
                [weakSelf.desLabel mas_updateConstraints:^(MASConstraintMaker *make) {
                    make.top.equalTo(weakSelf.picView.mas_bottom).offset(0);
                }];
                

                
            }
            else
            {
                self.picView.alpha=1;
                [self.picView sd_setImageWithURL:[NSURL URLWithString:x.picurl] placeholderImage:[UIImage imageNamed:@""]];
                
                
                float scale = 375/320.0;
                
              
                [self.picView mas_updateConstraints:^(MASConstraintMaker *make) {
                    
                    make.height.mas_equalTo((SCREEN_WIDTH-50)/scale);
                    
                }];
                [weakSelf.desLabel mas_updateConstraints:^(MASConstraintMaker *make) {
                    make.top.equalTo(weakSelf.picView.mas_bottom).offset(10);
                }];
                

            }
             weakSelf.desLabel.text = x.des;
//            weakSelf.desLabel.backgroundColor =[UIColor greenColor];
            
            
            if(x.linkUp.length==0)
            {
                weakSelf.lineviewbottom.alpha=0;
                weakSelf.openLinkLabel.alpha=0;
                weakSelf.arroImageview.alpha=0;
                
                [weakSelf.openLinkLabel mas_updateConstraints:^(MASConstraintMaker *make) {
                    make.height.mas_equalTo(0);
                } ];
                
                
                
            }
            else
            {
                weakSelf.lineviewbottom.alpha=1;
                weakSelf.openLinkLabel.alpha=1;
                weakSelf.arroImageview.alpha=0;
                
                
                
                [weakSelf.openLinkLabel mas_updateConstraints:^(MASConstraintMaker *make) {
                    make.height.mas_equalTo(44);
                } ];
                
                
             
            }
            
        }
        
        
        
    }];
    
    
    
    
    
}
@end
