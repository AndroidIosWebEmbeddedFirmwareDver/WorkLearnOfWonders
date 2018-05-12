//
//  OnLinePayCell.m
//  SZCirculationImage
//
//  Created by Wonders_iOS on 2016/11/1.
//  Copyright © 2016年 吴三忠. All rights reserved.
//

#import "OnLinePayCell.h"
//#import "View+MASAdditions.h"
#import "YSButton.h"
#import "cuzZButton.h"
#define UISCREENW  [UIScreen mainScreen].bounds.size.width
#define UISCREENH  [UIScreen mainScreen].bounds.size.height
//Masnory  弱引用
#define WS(weakSelf)  __weak __typeof(&*self)weakSelf = self;

@implementation OnLinePayCell
-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupView];
        [self bindModel];
//        [self removeFromSuperview];
    }
    return self;

}

-(NSArray<FunctionModel *> * )buttonArr{
    if (_dataArray) {
        _dataArray =[NSArray array];
    }
    return _dataArray;
}
-(void)bindModel{


//    NSLog(@"*********8%@",self.dataArray);
    WS(weakSelf)
[RACObserve(weakSelf,dataArray) subscribeNext:^(NSArray <FunctionModel *> * x) {
    if (x.count>2) {
        FunctionModel * model  =  x[0];
        FunctionModel * model1 =  x[1];
        FunctionModel * model2 =  x[2];
        
        weakSelf.leftButton.titleStr = model.mainTitle;
        weakSelf.leftButton.imageName = model.imgUrl;
        
        weakSelf.midButton.titleStr = model1.mainTitle;
        weakSelf.midButton.imageName = model1.imgUrl;
        
        weakSelf.rightButton.titleStr = model2.mainTitle;
        weakSelf.rightButton.imageName = model2.imgUrl;
    }else{
        NSString * imageName = @"家庭医生默认";
        weakSelf.leftButton.imageName = imageName;
        weakSelf.midButton.imageName = imageName;
        weakSelf.rightButton.imageName = imageName;
    }
    
}];

}
-(void)setupView{
    
    self.leftButton = [[cuzZButton alloc]init];
    self.leftButton.tag = 100;
    [self.contentView addSubview:self.leftButton];
    self.midButton = [[cuzZButton alloc]init];
    self.midButton.tag = 101;
    [self.contentView addSubview:self.midButton];
    self.rightButton = [[cuzZButton alloc]init];
    self.rightButton.tag = 102;
    [self.contentView addSubview:self.rightButton];
WS(weakSelf)
    [self.leftButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(weakSelf);
        make.width.mas_equalTo(UISCREENW/3);
        make.left.equalTo(weakSelf).offset(0*UISCREENW/3);
    }];
    [self.midButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(weakSelf);
        make.width.mas_equalTo(UISCREENW/3);
        make.left.equalTo(weakSelf).offset(1*UISCREENW/3);
    }];
    [self.rightButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(weakSelf);
        make.width.mas_equalTo(UISCREENW/3);
        make.left.equalTo(weakSelf).offset(2*UISCREENW/3);
    }];

    
    [self.leftButton addTarget:self action:@selector(clickForButton:) forControlEvents:UIControlEventTouchUpInside];
     [self.midButton addTarget:self action:@selector(clickForButton:) forControlEvents:UIControlEventTouchUpInside];
     [self.rightButton addTarget:self action:@selector(clickForButton:) forControlEvents:UIControlEventTouchUpInside];
}

-(void)clickForButton:(UIButton *)btn{
    switch (btn.tag) {
        case 100:
            //在线支付
            if (self.onLinePayBlock) {
                self.onLinePayBlock();
            }
            break;
        case 101:
            //提取报告
            if (self.drawReportBlock) {
             self.drawReportBlock();
            }
            break;
        case 102:
            //电子处方
            if (self.electronPrescriptionBlock) {
                self.electronPrescriptionBlock();
            }
            break;
        default:
            break;
    }

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
