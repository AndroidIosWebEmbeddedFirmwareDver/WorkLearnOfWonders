//
//  PatientSelectCell.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientSelectCell.h"

typedef void(^SelectBlock)(NSInteger index, NSString * title);

@interface PatientSelectCell ()


@property (strong, nonatomic) UIView * selectPanel;             //选择面板
@property (strong, nonatomic) NSArray * buttonsArray;           //选项

@property (strong, nonatomic) UIButton * currentButton;         //当前选中按钮
@property (copy, nonatomic)   SelectBlock selectBlock;
@end

@implementation PatientSelectCell
- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        [self initInterface];
    }
    return self;
}

- (void)initInterface {
    [self setBackgroundColor:RGB_COLOR(246, 246, 246)];
    
    _importantImageView = [[UIImageView alloc] init];
    [self.contentView addSubview:_importantImageView];
    
    _titleLabel = [[UILabel alloc] init];
    [_titleLabel setText:@"紧急程度"];
    [_titleLabel setTextColor:RGB_COLOR(102, 102, 102)];
    [_titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
    [self.contentView addSubview:_titleLabel];

    _selectPanel = [[UIView alloc] init];
    [_selectPanel setBackgroundColor:[UIColor whiteColor]];
    [self.contentView addSubview:_selectPanel];
    [self buildConstraint];
}

- (void)buildConstraint {
    [_importantImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView).offset(15);
        make.top.equalTo(self.contentView).offset(20);
        make.height.mas_equalTo(14);
        make.width.mas_equalTo(0);
    }];
    
    [_titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(_importantImageView);
        make.left.equalTo(_importantImageView.mas_right);
        make.right.equalTo(self.contentView).offset(-15);
    }];

    [_selectPanel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.contentView);
        make.top.equalTo(_titleLabel.mas_bottom).offset(10);
        make.height.mas_equalTo(40);
    }];
}


- (void)setTitles:(NSArray *)titles {
    if (_buttonsArray.count != 0) {
        for(UIButton *button in _buttonsArray) {
            [button removeTarget:self action:@selector(pressButton:) forControlEvents:UIControlEventTouchUpInside];
            [button removeFromSuperview];
        }
    }
    NSMutableArray * temp = [NSMutableArray array];
    for (NSInteger index = 0; index < titles.count; index ++) {
        NSString * title = titles[index];
        UIButton * button = [[UIButton alloc] init];
        [button.titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:13]];
        [button setTitleColor:RGB_COLOR(153, 153, 153) forState:UIControlStateNormal];
        [button setTitleColor:RGB_COLOR(46, 122, 240) forState:UIControlStateSelected];
        [button setTitle:title forState:UIControlStateNormal];
        [button.layer setCornerRadius:12];
        [button setborderWithColor:RGB_COLOR(153, 153, 153) withWidth:1];
        [button addTarget:self action:@selector(pressButton:) forControlEvents:UIControlEventTouchUpInside];
        [_selectPanel addSubview:button];
        [temp addObject:button];
        
        [button mas_makeConstraints:^(MASConstraintMaker *make) {
            make.width.mas_equalTo(54);
            make.height.mas_equalTo(24);
            if (index == 0) {
                make.left.equalTo(_selectPanel).offset(22);
                make.top.equalTo(_selectPanel).offset(9);
            } else {
                UIButton * lastButton = temp[index - 1];
                make.top.equalTo(lastButton);
                make.left.equalTo(lastButton.mas_right).offset(30);
            }
        }];
    }

    _buttonsArray = [NSArray arrayWithArray:temp];
    
    if (_buttonsArray.count > 0) {
        UIButton * button = _buttonsArray[0];
        [self pressButton:button];
    }
}

- (void)pressButton:(UIButton *)sender {
    if (_currentButton) {
        if (_currentButton == sender) {
            return;
        }
        [_currentButton setSelected:NO];
        [_currentButton setborderWithColor:RGB_COLOR(153, 153, 153) withWidth:1];
    }
    
    _currentButton = sender;
    [_currentButton setSelected:YES];
    [_currentButton setborderWithColor:RGB_COLOR(46, 122, 240) withWidth:1];
    _currentCount = [_buttonsArray indexOfObject:_currentButton];
    
    if (self.editBlock) {
        MJWeakSelf
        self.editBlock(YES,sender.titleLabel.text,weakSelf);
    }
}


- (void)setSelectedBlock:(void(^)(NSInteger index, NSString * title))block {
    _selectBlock = block;
}

@end
