//
//  BaseViewController.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"

@interface BaseViewController () <UIGestureRecognizerDelegate> {
    UITapGestureRecognizer  *_tapGesture;
}

@end

@implementation BaseViewController


- (id)init
{
    self = [super init];
    if (self) {
        self.hasBack = YES;
//        self.needHiddenBar = NO;
        self.backType = WDViewControllerBackNomal;
    }
    return self;
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName: nibNameOrNil bundle: nibBundleOrNil];
    if (self) {
        self.hasBack = YES;
//        self.needHiddenBar = NO;
    }
    return self;
}

- (id)initWithCoder:(NSCoder *)aDecoder {
    self = [super initWithCoder: aDecoder];
    if (self) {
        self.hasBack = YES;
//        self.needHiddenBar = NO;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.view setBackgroundColor: [UIColor bc2Color]];
    if ([self respondsToSelector:@selector(setEdgesForExtendedLayout:)]) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        self.extendedLayoutIncludesOpaqueBars = NO;
        self.automaticallyAdjustsScrollViewInsets = NO;
    }
    
    if (@available(iOS 11.0, *)) {
        [[UIScrollView appearance] setContentInsetAdjustmentBehavior:UIScrollViewContentInsetAdjustmentNever];
    }
    
    //返回按钮
    if (self.hasBack) {
        self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"icon_back"] style:UIBarButtonItemStyleDone target:self action:@selector(popBack)];
        
        if (self.isShowTitlePopRoot) {
            UIButton *itemBtn = [[UIButton alloc]initWithFrame:CGRectMake(0, 0, 60, 44)];
            [itemBtn setTitle:@"首页" forState:UIControlStateNormal];
            [itemBtn setImage:[UIImage imageNamed:@"icon_back"] forState:UIControlStateNormal];
            [itemBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
            itemBtn.titleLabel.font = [UIFont systemFontOfSize:14];
            itemBtn.contentHorizontalAlignment = UIControlContentHorizontalAlignmentLeft;
            [itemBtn addTarget:self action:@selector(popBackToRoot) forControlEvents:UIControlEventTouchUpInside];
            [itemBtn setTitleEdgeInsets:UIEdgeInsetsMake(0, 5, 0, 0)];
            
            self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:itemBtn];
        }
        
    }else {
        [self.navigationItem setHidesBackButton:YES];
    }
    
    
    
    _tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapGestureAction:)];
    _tapGesture.cancelsTouchesInView =  NO;
    _tapGesture.delegate = self;
    [self.view addGestureRecognizer:_tapGesture];
    
   
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    //键盘弹出监听
    [[NSNotificationCenter defaultCenter] addObserver: self
                                             selector: @selector(keyboardDidShow:)
                                                 name: UIKeyboardDidShowNotification
                                               object:nil];
    //键盘消失监听
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardDidHide:)
                                                 name:UIKeyboardDidHideNotification
                                               object:nil];
    //键盘即将弹出监听
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillShow:)
                                                 name:UIKeyboardWillShowNotification
                                               object:nil];
    //键盘即将消失监听
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillHide:)
                                                 name:UIKeyboardWillHideNotification
                                               object:nil];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    //移除键盘弹出监听
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillShowNotification object:nil];
    //移除键盘消失监听
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillHideNotification object:nil];
    //移除键盘即将弹出监听
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardDidShowNotification object:nil];
    //移除键盘即将消失监听
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardDidHideNotification object:nil];
    
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - rightItem 

-(void)setRightItemWithString:(NSString *)string withAction:(SEL)sel {
    UIButton *itemBtn = [[UIButton alloc]initWithFrame:CGRectMake(0, 0, 60, 44)];
    [itemBtn setTitle:string forState:UIControlStateNormal];
    [itemBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
    itemBtn.titleLabel.font = [UIFont systemFontOfSize:14];
    itemBtn.contentHorizontalAlignment = UIControlContentHorizontalAlignmentRight;
    [itemBtn addTarget:self action:sel forControlEvents:UIControlEventTouchUpInside];
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:itemBtn];
}

#pragma mark - 判断是否一个类是否加载过
-(BOOL)isHaveViewControllerWithClassString:(NSString *)vcString {
    
    for (UIViewController *vc in self.navigationController.viewControllers) {
        if([NSStringFromClass(vc.class) isEqualToString:vcString] &&
           vc != [self.navigationController.viewControllers lastObject]) {
            return YES;
        }
    }
    return NO;
}

#pragma mark - 点击隐藏键盘操作
- (void)tapGestureAction:(id)sender
{
    [self hideKeyboard];
}

#pragma mark - 关闭编辑界面，键盘消失
- (void)hideKeyboard
{
    [self.view endEditing:YES];
}

#pragma mark - 返回按钮操作
- (void)popBack {
    [self.view endEditing:YES];
    if (self.hasBack == YES) {
        
        [self.navigationController popViewControllerAnimated:YES];
        return;
    }
    
    if (self.presentingViewController) {
        if (self.navigationController &&self.navigationController.viewControllers.count > 1) {
            [self.navigationController popViewControllerAnimated:YES];
        }
        else
        {
            [self.presentingViewController dismissViewControllerAnimated:YES completion:nil];
        }
    }else{
        [self.navigationController popViewControllerAnimated:YES];
    }
}

- (void)popBackToViewController:(NSString *)vcStr {
    [[NSNotificationCenter defaultCenter] removeObserver:self];
    
    UIViewController *popVc = nil;
    for (UIViewController *vc in self.navigationController.viewControllers) {
        if ([vc isKindOfClass: NSClassFromString(vcStr)]) {
            popVc = vc;
            break;
        }
    }
    if (popVc) {
        [self.navigationController popToViewController: popVc animated: YES];
    }
    else {
        [self.navigationController popViewControllerAnimated:YES];
    }
}

-(void)popBackToRoot {
    [self.navigationController popToRootViewControllerAnimated:YES];
}

#pragma mark - 键盘监听事件
- (void)keyboardDidShow:(NSNotification *)notification
{
    
}

- (void)keyboardDidHide:(NSNotification *)notification
{
    
}

- (void)keyboardWillShow:(NSNotification *)notification
{
    
}

- (void)keyboardWillHide:(NSNotification *)notification
{
    
}



@end
