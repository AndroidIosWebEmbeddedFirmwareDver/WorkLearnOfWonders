//
//  ReportTableViewCell.m
//  sichuan_cell
//
//  Created by 刘松洪 on 2016/11/1.
//  Copyright © 2016年 刘松洪. All rights reserved.
//

#import "ReportTableViewCell.h"

#define TEXTFONT [UIFont systemFontOfSize:16.0]

@interface ReportTableViewCell (){
     double _totalHeight;
}

@property (strong, nonatomic) UILabel *checkTimeLB;
@property (strong, nonatomic) UILabel *subjectNameLB;
@property (strong, nonatomic) UILabel *checkCatergory;
@property (strong, nonatomic) UILabel *hosptialLB;
@end

@implementation ReportTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        [self createUI];
        [self RACBind];
    }
    return self;
}

- (void)createUI {
    self.selectionStyle = UITableViewCellSelectionStyleNone;
    self.contentView.backgroundColor = [UIColor bc1Color];
    WS(weakSelf)
    //开方日期
    UILabel *checkTime           = [[UILabel alloc]init];
    checkTime.textColor          = [UIColor tc2Color];
    checkTime.text               = @"检查时间: ";
    checkTime.font               = TEXTFONT;
    CGSize checkTimeDateThatFit  = [checkTime sizeThatFits:CGSizeZero];
    [self.contentView addSubview:checkTime];
    [checkTime mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15.5);
        make.top.equalTo(weakSelf.contentView).offset(15.0);
        make.size.mas_equalTo(checkTimeDateThatFit);
    }];
    
    self.checkTimeLB           = [[UILabel alloc]init];
    self.checkTimeLB.textColor = [UIColor tc1Color];
    self.checkTimeLB.text      = @"";
    self.checkTimeLB.font      = TEXTFONT;
    [self.contentView addSubview:self.checkTimeLB];
    [self.checkTimeLB mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(101.0);
        make.top.equalTo(checkTime);
        make.height.equalTo(checkTime);
        make.right.equalTo(weakSelf.contentView).offset(-15.0);
    }];
    
    //医院
    UILabel *hosptial                 = [[UILabel alloc]init];
    hosptial.textColor                = [UIColor tc2Color];
    hosptial.text                     = @"医        院: ";
    hosptial.font                     = TEXTFONT;
    CGSize   hosptialThatFit          = [hosptial sizeThatFits:CGSizeZero];
    [self.contentView addSubview:hosptial];
    [hosptial mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(checkTime);
        make.top.equalTo(checkTime.mas_bottom).offset(10.0);
        make.size.mas_equalTo(hosptialThatFit);
    }];
    
    self.hosptialLB                   = [[UILabel alloc]init];
    self.hosptialLB.textColor         = [UIColor tc1Color];
    self.hosptialLB.text              = @"";
    self.hosptialLB.font              = TEXTFONT;
    [self.contentView addSubview:self.hosptialLB];
    [self.hosptialLB mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.checkTimeLB);
        make.top.equalTo(hosptial);
        make.height.equalTo(hosptial);
        make.right.equalTo(weakSelf.contentView).offset(-26.0);
    }];
    
    
    UILabel *subjectName             = [[UILabel alloc]init];
    subjectName.textColor            = [UIColor tc2Color];
    subjectName.text                 = @"科        室: ";
    subjectName.font                 = TEXTFONT;
    CGSize subjectNameThatFit        = [subjectName sizeThatFits:CGSizeZero];
    [self.contentView addSubview:subjectName];
    [subjectName mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(hosptial);
        make.top.equalTo(hosptial.mas_bottom).offset(10.0);
        make.size.mas_equalTo(subjectNameThatFit);
    }];
    
    self.subjectNameLB                = [[UILabel alloc]init];
    self.subjectNameLB.textColor      = [UIColor tc1Color];
    self.subjectNameLB.text           = @"";
    self.subjectNameLB.font           = TEXTFONT;
    [self.contentView addSubview:self.subjectNameLB];
    [self.subjectNameLB mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.checkTimeLB);
        make.top.equalTo(subjectName);
        make.height.equalTo(subjectName);
        make.right.equalTo(weakSelf.contentView).offset(-26.0);
    }];
    
    //检查类别
    UILabel *checkCatergory                  = [[UILabel alloc]init];
    checkCatergory.textColor                 = [UIColor tc2Color];
    checkCatergory.text                      = @"检查类别: ";
    checkCatergory.font                      = TEXTFONT;
    CGSize  checkCatergoryThatFit            = [checkCatergory sizeThatFits:CGSizeZero];
    [self.contentView addSubview:checkCatergory];
    [checkCatergory mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(subjectName);
        make.top.equalTo(subjectName.mas_bottom).offset(10.0);
        make.width.mas_equalTo(checkCatergoryThatFit.width);
        make.bottom.equalTo(weakSelf.contentView).offset(-15.0);
    }];
    
    self.checkCatergory                    = [[UILabel alloc]init];
    self.checkCatergory.textColor          = [UIColor tc1Color];
    self.checkCatergory.text               = @"";
    self.checkCatergory.font               = TEXTFONT;
    [self.contentView addSubview:self.checkCatergory];
    [self.checkCatergory mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.hosptialLB);
        make.top.equalTo(checkCatergory);
        make.height.equalTo(checkCatergory);
        make.right.equalTo(weakSelf.hosptialLB);
    }];
    
    UIImageView *imageView             = [[UIImageView alloc]init];
    imageView.image                    = [UIImage imageNamed:@"link_right"];
    [self.contentView addSubview:imageView];
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-16.0);
        make.centerY.equalTo(weakSelf);
        make.size.mas_equalTo(CGSizeMake(7.0, 14.0));
    }];

    UIView *lineView         = [[UIView alloc]init];
    lineView.backgroundColor = [UIColor dc4Color];
    [self.contentView addSubview:lineView];
    [lineView  mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15.0);
        make.bottom.equalTo(weakSelf.contentView).offset(-0.5);
        make.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(0.5);
    }];
}

- (void)RACBind {
    @weakify(self)
    
    [RACObserve(self, cellModel) subscribeNext:^(ReportModel *model) {
         @strongify(self)
        self.checkTimeLB.text    = model.date;
        self.hosptialLB.text     = model.hospital_name;
        self.subjectNameLB.text  = model.department_name;
        self.checkCatergory.text = model.item_name;
    }];
    
//    [RACObserve(self, cellModel.date) subscribeNext:^(NSString *date) {
//        @strongify(self)
//        self.checkTimeLB.text = date;
//    }];
//    
//    [RACObserve(self, cellModel.hospital_name) subscribeNext:^(NSString *hosptialName) {
//        @strongify(self)
//        self.hosptialLB.text = hosptialName;
//    }];
//    
//    [RACObserve(self, cellModel.department_name) subscribeNext:^(NSString *subjectName) {
//        @strongify(self)
//        
//    }];
//    
//    [RACObserve(self, cellModel.item_name) subscribeNext:^(NSString *checkCatergory) {
//        @strongify(self)
//        self.checkCatergory.text = checkCatergory;
//    }];
}

@end
