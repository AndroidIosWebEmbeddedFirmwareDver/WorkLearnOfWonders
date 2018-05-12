//
//  HealthHomeFunctionCell.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FunctionModel.h"


typedef void(^LeftButtonBlock)(void);
typedef void(^RightTopButtonBlock)(void);
typedef void(^RightBottomButtonBlock)(void);


@interface HealthHomeFunctionCell : UITableViewCell

@property (nonatomic, copy) NSString *leftImageName;
@property (nonatomic, copy) NSString *rightTopImageName;
@property (nonatomic, copy) NSString *rightBottomImageName;

@property (nonatomic, copy) NSString *leftTitle;
@property (nonatomic, copy) NSString *leftSubTitle;
@property (nonatomic, copy) NSString *rightTopTitle;
@property (nonatomic, copy) NSString *rightBottomTitle;

@property (nonatomic, assign) BOOL rightBottomFuncitonInvalid;

@property (nonatomic, copy) LeftButtonBlock leftButtonBlock;
@property (nonatomic, copy) RightTopButtonBlock rightTopButtonBlock;
@property (nonatomic, copy) RightBottomButtonBlock rightBottomButtonBlock;

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier noSubTitle:(BOOL)noSub;

- (void)setCellWithArray:(NSArray<FunctionModel *> *)array;

@end
