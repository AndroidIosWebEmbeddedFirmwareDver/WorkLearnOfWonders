//
//  SCAppointmentDetailInputCell.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/21.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDetailInputCell.h"

@interface SCAppointmentDetailInputCell ()<UITextFieldDelegate>

@property (nonatomic, strong) UILabel *titleLabel1;
@property (nonatomic, strong) UITextField *contentLabel1;

@end

@implementation SCAppointmentDetailInputCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        [self setupView];
        [self bindViewModel];
    }
    return self;
}

- (void)setupView {
    self.backgroundColor = [UIColor bc1Color];
    self.contentView.backgroundColor = [UIColor bc1Color];
    
    WS(weakSelf)
    self.titleLabel1 = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc2Color] withFontSize:16];
    [self.titleLabel1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.centerY.equalTo(weakSelf.contentView);
        make.width.mas_equalTo(100);
    }];
    
    self.contentLabel1 = [UISetupView setupTextFieldWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc1Color] withFontSize:16 withPlaceholder:@"" withDelegate:self withReturnKeyType:UIReturnKeyDone withKeyboardType:UIKeyboardTypeDefault];
    self.contentLabel1.textAlignment = NSTextAlignmentRight;
    [self.contentLabel1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.centerY.equalTo(weakSelf.contentView);
        make.left.equalTo(weakSelf.titleLabel1.mas_right).offset(5);
    }];
    
//    [self.contentLabel1 setValue:[UIColor stc2Color] forKeyPath:@"_placeholderLabel.textColor"];

    [bottomLine mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(weakSelf.contentView);
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(0.5);
    }];
    
    [topLine mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.contentView);
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(0.5);
    }];
    
}

-(void)bindViewModel {
    RAC(self.titleLabel1, text) = RACObserve(self, title);
//    RAC(self.contentLabel1, text) = RACObserve(self, content);
    
    
}

-(void)setViewModel:(SCAppointmentDetailViewModel *)viewModel {
    WS(weakSelf)
    _viewModel = viewModel;
    
    [RACObserve(viewModel, isNotInputCard) subscribeNext:^(NSNumber *x) {
        if ([x boolValue]) {
            weakSelf.contentView.backgroundColor = [UIColor sbc7Color];
        }else {
            weakSelf.contentView.backgroundColor = [UIColor bc1Color];
        }
    }];
    
    RAC(viewModel, card) = self.contentLabel1.rac_textSignal;
}


+(CGFloat)cellHeight {
    return 44;
}


#pragma mark    - UITextFieldDelegate
- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    
    return YES;
}

-(BOOL)textFieldShouldBeginEditing:(UITextField *)textField {
    self.viewModel.isNotInputCard = @NO;
    return YES;
}



@end
