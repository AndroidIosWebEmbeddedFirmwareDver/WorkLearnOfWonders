//
//  MyPreOrderListsFinalViewController.m
//  SCHCPatient
//
//  Created by wuhao on 2016/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "MyPreOrderListsFinalViewController.h"
#import "VTMagic.h"
    //segment引用的VC
#import "SCMyPreOrderFinalAllViewController.h"



@interface MyPreOrderListsFinalViewController ()<VTMagicViewDelegate,VTMagicViewDataSource,preOrderCountDelegate>
@property (strong, nonatomic) VTMagicController *magicController;



@end

@implementation MyPreOrderListsFinalViewController

- (void)viewDidLoad {
    [super viewDidLoad];
        // Do any additional setup after loading the view.
    
        //self.navigationItem.title=@"我的预约(2)";
    
    
//    if(![[Global global] networkReachable]){
//        self.navigationItem.title=@"我的预约";
//    }
    self.navigationItem.title=@"我的预约";

    [self addChildViewController:self.magicController];
    [self prepareMagicController];
}

- (UIViewController<VTMagicProtocol> *)magicController {
    if (!_magicController) {
        _magicController = [[VTMagicController alloc] init];
        _magicController.magicView.navigationColor  = [UIColor whiteColor];
        _magicController.magicView.sliderColor      = [UIColor bc7Color];
        _magicController.magicView.sliderHeight     = 2;
        _magicController.magicView.sliderWidth      = 88;
        _magicController.magicView.layoutStyle      = VTLayoutStyleDivide;
        _magicController.magicView.switchStyle      = VTSwitchStyleDefault;
        _magicController.magicView.navigationHeight = 44;
        _magicController.magicView.dataSource       = self;
        _magicController.magicView.delegate         = self;
        _magicController.magicView.needPreloading   = NO;
        _magicController.magicView.separatorColor = [UIColor dc1Color];
//        [self addChildViewController:self.magicController];
    }
    
    return _magicController;
}



#pragma mark - VTmagicViewDataSource

- (NSArray<__kindof NSString *> *)menuTitlesForMagicView:(VTMagicView *)magicView {
    NSArray *menuArr = @[@"全部",@"待就诊",@"已就诊",@"已取消"];
    return menuArr;
}

- (void)prepareMagicController {
    [self.view addSubview:self.magicController.view];
    WS(weakSelf)
    [_magicController.view mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(weakSelf.view);
    }];
    [_magicController didMoveToParentViewController:self];
    [_magicController.magicView reloadData];
    [_magicController.magicView switchToPage:0 animated:NO];
    
    
}




- (UIButton *)magicView:(VTMagicView *)magicView menuItemAtIndex:(NSUInteger)itemIndex {
    static NSString *identifier = @"itemBtn";
    UIButton *menuBtn = [magicView dequeueReusableItemWithIdentifier:identifier];
    if (!menuBtn) {
        menuBtn = [UIButton buttonWithType:UIButtonTypeCustom];
        [menuBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateSelected];
        [menuBtn setTitleColor:[UIColor tc1Color] forState:UIControlStateNormal];
        menuBtn.titleLabel.font = [UIFont systemFontOfSize:14.0];
    }
    
    return menuBtn;
}

- (UIViewController *)magicView:(VTMagicView *)magicView viewControllerAtPage:(NSUInteger)pageIndex {
    
    //    NSArray *menuArr = @[@"全部",@"待支付",@"带就诊",@"已完成",@"已取消"];
    
        // 全部预约VC
//        static NSString *SCMyPreOrderFinalAllVC = @"SCMyPreOrderFinalAllViewController";
//        Class SCMyPreOrderFinalAllViewController = NSClassFromString(SCMyPreOrderFinalAllVC);
//        BaseViewController *vc = [magicView dequeueReusablePageWithIdentifier:SCMyPreOrderFinalAllVC];
//        if (!vc) {
//            vc = [[SCMyPreOrderFinalAllViewController alloc] init];
//            
//        }
//        return vc;
    
//    static NSString *testVC = @"SCMyPreOrderFinalAllViewController";
//    Class FileListViewController = NSClassFromString(testVC);
//    BaseViewController *vc = [magicView dequeueReusablePageWithIdentifier:testVC];
//    if (!vc) {
//        vc = [[FileListViewController alloc] init];
//        ((SCMyPreOrderFinalAllViewController *)vc).preOrderState=(int)pageIndex;
//        ((SCMyPreOrderFinalAllViewController *)vc).delegate=self;
//
//    }else{
//        ((SCMyPreOrderFinalAllViewController *)vc).preOrderState=(int)pageIndex;
//        ((SCMyPreOrderFinalAllViewController *)vc).delegate=self;
//    }
//    
//    return vc;
    
  
    
  
    SCMyPreOrderFinalAllViewController *vc=[[SCMyPreOrderFinalAllViewController alloc]init];
    vc.preOrderState=(int)pageIndex;
    vc.delegate=self;
    
    
    return vc;

}

    //从所有预约订单中获得总量
-(void)allPreorderCount:(int)count{

//    self.navigationItem.title=[NSString stringWithFormat:@"我的预约(%i)",count];
//    if (count==0) {
//        self.navigationItem.title=@"我的预约";
//    }
//
    self.navigationItem.title=@"我的预约";

}



//- (void)magicView:(VTMagicView *)magicView didSelectItemAtIndex:(NSUInteger)itemIndex {
//    NSLog(@"----%zd", itemIndex);
//    
//    static NSString *testVC = @"SCMyPreOrderFinalAllViewController";
//    Class FileListViewController = NSClassFromString(testVC);
//    BaseViewController *vc = [magicView dequeueReusablePageWithIdentifier:testVC];
//    if (!vc) {
//        vc = [[FileListViewController alloc] init];
//        ((SCMyPreOrderFinalAllViewController *)vc).preOrderState=(int)itemIndex;
//        
//        ((SCMyPreOrderFinalAllViewController *)vc).delegate=self;
//        
//    }
//    
//
//}




@end
