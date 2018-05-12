//
//  HomeFunctionTableViewCell.m
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "HomeFunctionTableViewCell.h"
#import "HomeFunctionButton.h"

@interface HomeFunctionTableViewCell ()

@end

@implementation HomeFunctionTableViewCell
- (void)awakeFromNib {
    // Initialization code
    [super awakeFromNib];
}

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier  {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        self.selectionStyle = UITableViewCellSelectionStyleNone;

        [self prepareUI];
        [self bindRac];
    }
    return self;
}

- (void)prepareUI {
    
    self.contentView.backgroundColor = [UIColor bc1Color];
    
    UIView *backView = [UIView new];
    backView.backgroundColor = [UIColor whiteColor];
    backView.tag = 200;
    [self.contentView addSubview:backView];
    [backView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.width.height.equalTo(self.contentView);
    }];
    
    CGFloat left = 44, offsetX = (SCREEN_WIDTH-61*3-44*2)/2.0, top = 29.5, offsetY = 29.5, sizeX = 61,sizeY = 61+15+15;
    
    for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 3; j++) {
            
            HomeFunctionButton *button = [HomeFunctionButton new];
            button.tag = 300+j+i*3;
            [button addTarget:self action:@selector(buttonSelected:) forControlEvents:UIControlEventTouchUpInside];
            [backView addSubview:button];
            [button mas_makeConstraints:^(MASConstraintMaker *make) {
                make.left.equalTo(backView).offset(left+j*(offsetX+sizeX));
                make.top.equalTo(backView).offset(top+i*(offsetY+sizeY));
                make.size.mas_equalTo(CGSizeMake(sizeX, sizeY));
            }];
        }
    }
}

- (void)bindRac {
    
    WS(weakSelf)
    [RACObserve(self, datas) subscribeNext:^(NSArray *x) {
        if (x) {
            UIView *view = [weakSelf.contentView viewWithTag:200];
            for (int i = 0; i < x.count; i++) {
                HomeFunctionModel *model = x[i];
                
                HomeFunctionButton *button = [view viewWithTag:300+i];
                button.imageString = model.image;
                button.titleName = model.content;
                button.invalid = [model.invalid boolValue];
                button.isNeedRedPoint = [model.isNeedRedPoint boolValue];
            }
        }
    }];
}

-(void)buttonSelected:(UIButton *)sender {
    if (self.functionButtonBlock) {
        self.functionButtonBlock(sender.tag);
    }
}

@end
