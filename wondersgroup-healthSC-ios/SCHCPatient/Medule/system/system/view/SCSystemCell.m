//
//  SystemCell.m
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSystemCell.h"

@implementation SCSystemCell

-(id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if(self)
    {
        self.backgroundColor = [UIColor whiteColor];
        self.selectionStyle  = UITableViewCellSelectionStyleNone;
        [self setUpView];
        [self BindModel];
        
    }
    return self;
}
-(void)setUpView
{
    self.iconImageView = [[UIImageView alloc] initWithFrame:CGRectZero];
    [self.iconImageView.layer setMasksToBounds:YES];
    [self.iconImageView.layer setCornerRadius:25];
    [self.contentView addSubview:self.iconImageView];
    
    [self.iconImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.equalTo(self.contentView).offset(15.0);
        make.bottom.equalTo(self.contentView.mas_bottom).offset(-15.0);
        make.width.equalTo(@50);
    }];
    
    self.messageCountLabel = [[UILabel alloc] initWithFrame:CGRectZero];
    self.messageCountLabel.backgroundColor = [UIColor stc2Color];
    self.messageCountLabel.textAlignment = NSTextAlignmentCenter;
    self.messageCountLabel.font = [UIFont systemFontOfSize:10];
    self.messageCountLabel.textColor  =[UIColor whiteColor];
    [self.messageCountLabel.layer setMasksToBounds:YES];
    [self.messageCountLabel.layer setCornerRadius:7.5];
    [self.contentView addSubview:self.messageCountLabel];
    
    [self.messageCountLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.contentView).offset(15.0);
        make.height.equalTo(@15);
        make.left.equalTo(self.contentView.mas_left).offset(55.5);
    }];
    
    
    self.datelabel  = [[UILabel alloc] initWithFrame:CGRectZero];
    self.datelabel.font = [UIFont systemFontOfSize:12];
    self.datelabel.textAlignment = NSTextAlignmentRight;
    self.datelabel.textColor = [UIColor tc3Color];
    [self.contentView addSubview:self.datelabel];
    [self.datelabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(self.contentView.mas_right).offset(-32);
        make.centerY.equalTo(self.messageCountLabel.mas_centerY);
        make.height.equalTo(self.messageCountLabel.mas_height);
    }];
    
    
    self.titleLable = [[UILabel alloc] initWithFrame:CGRectZero];
    self.titleLable.font =[UIFont systemFontOfSize:16];
    self.titleLable.textAlignment = NSTextAlignmentLeft;
    self.titleLable.textColor = [UIColor tc1Color];
    [self.contentView addSubview:self.titleLable];
    
    
    [self.titleLable mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView.mas_left).offset(82.5);
        make.top.equalTo(self.contentView.mas_top).offset(20);
        make.height.equalTo(@16);
    }];
    
    self.contentLable = [[UILabel alloc] initWithFrame:CGRectZero];
    self.contentLable.font =[UIFont systemFontOfSize:14];
    self.contentLable.textAlignment = NSTextAlignmentLeft;
    self.contentLable.textColor = [UIColor tc2Color];
    [self.contentView addSubview:self.contentLable];
    
    
    [self.contentLable mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView.mas_left).offset(82.5);
        make.top.equalTo(self.titleLable.mas_bottom).offset(10);
        make.right.equalTo(self.contentView.mas_right).offset(-15);
        make.height.equalTo(@14);
    }];
    
    self.topLine =[[UIView alloc] initWithFrame:CGRectZero];
    self.topLine.backgroundColor = [UIColor dc4Color];
    [self.contentView addSubview:self.topLine];
    [self.topLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView.mas_left);
        make.right.equalTo(self.contentView.mas_right);
        make.top.equalTo(self.contentView.mas_top);
        make.height.equalTo(@0.5);
    }];
    
    self.endLine =[[UIView alloc] initWithFrame:CGRectZero];
    self.endLine.backgroundColor = [UIColor dc4Color];
    [self.contentView addSubview:self.endLine];
    
    [self.endLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView.mas_left);
        make.right.equalTo(self.contentView.mas_right);
        make.bottom.equalTo(self.contentView.mas_bottom);
        make.height.equalTo(@0.5);
    }];
    
    
    UIImageView * arroImageview = [[UIImageView alloc] initWithFrame:CGRectZero];
    arroImageview.image = [UIImage imageNamed:@"systemRightArrow"];
    //14/28l
    arroImageview.alpha=1;
    [self.contentView addSubview:arroImageview];
    
    
    [arroImageview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(self.contentView.mas_centerY);
        make.width.equalTo(@7);
        make.height.equalTo(@14);
        make.right.equalTo(self.contentView.mas_right).offset(-10);
        
    }];

}
-(void)BindModel
{
    
    WS(weakSelf);
    [RACObserve(self, model) subscribeNext:^(SCSystemModel * x) {
        
//        @property(nonatomic,assign) NSNumber * messageCount;
//        @property(nonatomic ,assign) NSNumber * type;
//        @property(nonatomic ,assign) NSString * content;
        
        if(x)
        {
            
            weakSelf.datelabel.text = x.time;
           
            switch ([x.type intValue])
            {
                case 1:
                {
                     weakSelf.titleLable.text = x.title.length==0?@"系统消息":x.title ;
                    weakSelf.iconImageView.image=[UIImage imageNamed:@"systemicon"];
                    weakSelf.contentLable.text = x.content.length==0?@"暂无消息":x.content ;
                }
                    break;
                case 2:
                {
                    weakSelf.iconImageView.image =[UIImage imageNamed:@"payicon"];
                    weakSelf.titleLable.text = x.title.length==0?@"支付通知":x.title ;
                    weakSelf.contentLable.text = x.content.length==0?@"暂无通知":x.content ;
                }
                    break;
                case 3:
                    weakSelf.iconImageView.image = [UIImage imageNamed:@"reporticon"];
                    
                    break;
                default:
                    break;
            }
            
            weakSelf.messageCountLabel.text=[x.messageCount intValue]>99?@"...":[NSString stringWithFormat:@"%@",x.messageCount];
            
            [weakSelf.messageCountLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
                make.top.equalTo(weakSelf.contentView).offset(15.0);
                make.height.equalTo(@15);
                make.width.equalTo([x.messageCount intValue]<99?@15:@15);
                make.left.equalTo(weakSelf.contentView.mas_left).offset(55.5);
            }];
            if ([x.messageCount intValue]==0)
            {
                weakSelf.messageCountLabel.alpha=0;
            }
            else
            {
                weakSelf.messageCountLabel.alpha=1;
            }
                
           
        }
        
        
        
        
        
    }];
    [RACObserve(self, index) subscribeNext:^(NSNumber * x) {
       
        
        
        if([x intValue]==0)
        {
            weakSelf.topLine.alpha=1;
        }
        else
        {
            weakSelf.topLine.alpha =0;
        }
        
        if([x intValue] == weakSelf.objectCount-1)
        {
            
            [weakSelf.endLine mas_remakeConstraints:^(MASConstraintMaker *make) {
                make.left.equalTo(weakSelf.contentView.mas_left);
                make.right.equalTo(weakSelf.contentView.mas_right);
                make.bottom.equalTo(weakSelf.contentView.mas_bottom);
                make.height.equalTo(@0.5);
            }];
        }
        else
        {
            [weakSelf.endLine mas_remakeConstraints:^(MASConstraintMaker *make) {
                make.left.equalTo(weakSelf.contentView.mas_left).offset(15);
                make.right.equalTo(weakSelf.contentView.mas_right).offset(-15);
                make.bottom.equalTo(weakSelf.contentView.mas_bottom);
                make.height.equalTo(@0.5);
            }];
        }
        
        
        
    }];

}

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
