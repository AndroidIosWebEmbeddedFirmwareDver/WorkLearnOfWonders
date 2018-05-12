//
//  AreaCell.m
//  HCPatient
//
//  Created by luzhongchang on 16/8/22.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "AreaCell.h"

@implementation AreaCell


-(id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{

    self = [super  initWithStyle:style reuseIdentifier:reuseIdentifier];
    if(self)
    {
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        self.backgroundColor = [UIColor whiteColor];
        [self setupview];
//        [self Bindmodel];
    }
    return self;
}

-(void)setupview
{
//    self.backgroundColor = [UIColor bc2Color];
    self.titlehead  = [[UILabel alloc] initWithFrame:CGRectZero];
    self.titlehead.font = [UIFont systemFontOfSize:16];
    self.titlehead.textColor = [UIColor tc2Color];
    self.titlehead.textAlignment = NSTextAlignmentLeft;
    [self.contentView addSubview:self.titlehead];
    [self.titlehead mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView.mas_left).offset(15);
        make.top.equalTo(self.contentView.mas_top).offset(0);
        make.right.equalTo(self.contentView.mas_right).offset(-15);
        make.bottom.equalTo(self.contentView.mas_bottom);
    }];
    
    
//    CALayer * layer = [CALayer layer];
//    layer.frame = CGRectMake(0, 43.5, SCREEN_WIDTH, 0.5);
//    layer.backgroundColor = [UIColor dc2Color].CGColor;
//    [self.contentView.layer addSublayer:layer];
    
    
}

//-(void)Bindmodel
//{
//    @weakify(self);
//    [RACObserve(self, model) subscribeNext:^(LocationModel * x) {
//       
//        @strongify(self);
//        if(x)
//        {
//            self.titlehead.text = x.showAreaName;
//        
//        }
//    }];
//}

@end



@implementation AreaGpsCell


-(id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    
    self = [super  initWithStyle:style reuseIdentifier:reuseIdentifier];
    if(self)
        {
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        self.backgroundColor = [UIColor whiteColor];
        [self setupview];
        [self Bindmodel];
        }
    return self;
}

-(void)setupview
{
    self.titlehead  = [[UILabel alloc] initWithFrame:CGRectZero];
    self.titlehead.font = [UIFont systemFontOfSize:16];
    self.titlehead.textColor = [UIColor tc2Color];
    self.titlehead.textAlignment = NSTextAlignmentLeft;
    [self.contentView addSubview:self.titlehead];
    [self.titlehead mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView.mas_left).offset(15);
        make.top.equalTo(self.contentView.mas_top).offset(0);
        make.right.equalTo(self.contentView.mas_right).offset(-15);
        make.bottom.equalTo(self.contentView.mas_bottom);
    }];
    
    
    CALayer * layer = [CALayer layer];
    layer.frame = CGRectMake(0, 43.5, SCREEN_WIDTH, 0.5);
    layer.backgroundColor = [UIColor dc2Color].CGColor;
    [self.contentView.layer addSublayer:layer];
    
    
}

-(void)Bindmodel
{
    @weakify(self);
    [RACObserve(self, model) subscribeNext:^(LocationModel * x) {
        
        @strongify(self);
        if(x)
            {
            self.titlehead.text = x.blishName;
            
            
            if([[LocationModel Instance].latitude isEqualToString:@""] || [[LocationModel Instance].longitude isEqualToString:@""])
            {
            self.titlehead.text=@"定位服务未开启，请手动选择区域";
            }
            
            
            }
    }];
}

@end

