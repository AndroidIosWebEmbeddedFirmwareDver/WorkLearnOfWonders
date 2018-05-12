//
//  WDChangeNameViewController.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDChangeNameViewController.h"
#import "UserService.h"

@interface WDChangeNameViewController ()

@property (nonatomic, strong) UITextField *nameField;
@property (nonatomic, strong) UIButton *setButton;

@end

@implementation WDChangeNameViewController

- (UITextField *)nameField {
    if (!_nameField) {
        _nameField = [UITextField new];
        _nameField.textColor = [UIColor tc1Color];
        _nameField.placeholder=@"请输入用户名";
        if ([UserManager manager].nickname) {
            _nameField.text = [UserManager manager].nickname;
        }
        _nameField.font = [UIFont systemFontOfSize:16];
    }
    return _nameField;
}

- (UIButton *)setButton {
    if (!_setButton) {
        _setButton = [UIButton new];
        [_setButton setTitle:@"完成" forState:UIControlStateNormal];
        [_setButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
        _setButton.titleLabel.font = [UIFont systemFontOfSize:16];
        _setButton.backgroundColor = [UIColor bc7Color];
        _setButton.layer.masksToBounds = YES;
        _setButton.layer.cornerRadius = 3;
        [_setButton addTarget:self action:@selector(changeName) forControlEvents:UIControlEventTouchUpInside];
    }
    return _setButton;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
}

- (void)setupView {
    self.title = @"编辑姓名";
    
    WS(ws)
    
    UIView *bgView = [UIView new];
    bgView.backgroundColor = [UIColor bc1Color];
    [self.view addSubview:bgView];
    [bgView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(ws.view).offset(10);
        make.left.right.equalTo(ws.view);
        make.height.mas_equalTo(44);
    }];
    
    [bgView addSubview:self.nameField];
    [self.nameField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bgView).offset(15);
        make.right.equalTo(bgView).offset(-15);
        make.top.bottom.equalTo(bgView);
    }];
    
    if(self.content.length>0)
    {
        self.nameField.placeholder=self.content;
    }
    
    [self.view addSubview:self.setButton];
    [self.setButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(bgView.mas_bottom).offset(45);
        make.left.equalTo(ws.view).offset(15);
        make.right.equalTo(ws.view).offset(-15);
        make.height.mas_equalTo(44);
    }];
}

- (void)changeName {
    
    NSString *string = _nameField.text;
    NSString *trimmedString = [string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
    
    if (trimmedString.length == 0) {
        [MBProgressHUDHelper showHudWithText:@"请输入名字"];
        return;
    }
    
    if (trimmedString.length > 20) {
        [MBProgressHUDHelper showHudWithText:@"名字不得大于20个字符"];
        return;
    }
    NSDictionary *dic = @{
                          @"uid" : [UserManager manager].uid,
                          @"nickname" : trimmedString
                          };
    [[UserService service] userEditInfo:dic complete:^{
        [self.navigationController popViewControllerAnimated:YES];
    }];
}









@end
