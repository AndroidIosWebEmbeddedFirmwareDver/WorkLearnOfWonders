//
//  RootTabBarViewModel.m
//  EyeProtection
//
//  Created by Jam on 16/2/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "RootTabBarViewModel.h"
#import "BadgeModel.h"
#import "UserDefaults.h"
#import "BaseViewController.h"
#import "BaseNavigationController.h"

#define TAB_IMAGE_KEY @"TabbarItemImageUrls"


@implementation RootTabBarViewModel


- (id)init {
    self = [super init];
    if (self) {
        [self initialize];
        
    }
    return self;
}

#pragma mark - 配置Tab上的视图
//0:视图Class名、1:Tab的title、2:Tab选中图片、3:Tab未选中图片
- (NSArray *)tabbarViewControllers {
    if (!self.tabModel) {
        [self getLocalTablImages];
    }
    return @[@[@"HomeViewController", @"工作台", self.tabModel.tabImages[0][0], self.tabModel.tabImages[0][1]],
             @[@"ProfileViewController", @"个人", self.tabModel.tabImages[1][0], self.tabModel.tabImages[1][1]],
             ];
}


- (void)initialize {
    //去本地拉取Tab图片信息
    [self getLocalTablImages];
    //初始化Tab的viewcontroller
    [self initTabBarControllers];
    
    [self bindViewModel];
    
}

#pragma mark - 本地拉取Tab图片信息 如果没有，取默认
- (void)getLocalTablImages {

//    UserDefaults *defaults = [UserDefaults shareDefaults];
//    if([defaults objectForKey: TAB_IMAGE_KEY]) {
//        NSDictionary *dic = [defaults objectForKey: TAB_IMAGE_KEY];
//        NSArray *list    = @[@[dic[@"tab1Sel"], dic[@"tab1"]],
//                             @[dic[@"tab2Sel"], dic[@"tab2"]],
//                             @[dic[@"tabBg"]]];
//        
//        if([self tabImageIsDownloadComplete: list]) {
//            self.tabModel.tabImages         = [NSArray arrayWithArray: list];
//            self.tabModel.isBundleImage     = NO;
//            self.tabModel.downloadComplete  = YES;
//        }
//    }

    if (!self.tabModel) {
        self.tabModel    = [[TabbarItemModel alloc] init];
        self.tabModel.tabImages = @[@[@"tab1_s", @"tab1_n"],
                                    @[@"tab2_s", @"tab2_n"],
                                    @[@"tab_bg"]];
        self.tabModel.isBundleImage     = YES;
        self.tabModel.downloadComplete  = YES;
    }
}

#pragma mark - 检查图片是否已经下载完
- (BOOL)tabImageIsDownloadComplete: (NSArray *)list {
    SDImageCache *cache = [SDImageCache  sharedImageCache];
    BOOL isComplete = YES;
    for (NSArray *imageList in list) {
        if(![cache imageFromDiskCacheForKey: imageList[0]]) {
            isComplete = NO;
            break;
        }
        if ((int)(imageList.count) > 1) {
            if(![cache imageFromDiskCacheForKey: imageList[1]]) {
                isComplete = NO;
                break;
            }
        }
    }
    return isComplete;
}


#pragma mark - 绑定RAC
- (void)bindViewModel {
    
}


#pragma mark - 加载TabBar上的ViewController列表
- (void)initTabBarControllers {
    
    NSMutableArray *controllers = [[NSMutableArray alloc] initWithCapacity: 5];
        
    NSArray *itemParms = [self tabbarViewControllers];

    for (int i=0; i<[itemParms count]; i++) {
        NSArray *parms = [itemParms objectAtIndex:i];
        
        BaseViewController* vc = [[NSClassFromString(parms[0]) alloc] init];
        vc.hasBack = NO;
//        if ([vc isKindOfClass: [WDProfileViewController class]]) {
//            vc.needHiddenBar = YES;
//        }
        BaseNavigationController* navController = [[BaseNavigationController alloc] initWithRootViewController: vc];
        navController.Tag=i;
//        vc.title = parms[1];
//        vc.tabBarItem.title  = parms[1];
//        navController.Tag =i;
        [vc.tabBarItem setTitlePositionAdjustment:UIOffsetMake(0, -4)];
        
        [vc.tabBarItem setSelectedImage:[[UIImage imageNamed:parms[2]] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal]];
        [vc.tabBarItem setImage:[[UIImage imageNamed:parms[3]] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal]];
        vc.tabBarItem.imageInsets = UIEdgeInsetsMake(6, 0, -6, 0);
    
        [controllers addObject: navController];
    }
    
    self.tabbarControllers = [NSArray arrayWithArray: controllers];
}

//#pragma mark - 检查是否需要重新下载Tab
//- (void)checkTabbarItemImageNeedDown {
//        
//    [self.adapter request: UI_TAB_IMAGE params: nil class: nil responseType:Response_Object method:Request_GET needLogin: NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
//        NSLog(@"%@", response);
//        NSArray *imageList = response.data;
//        if (imageList.count == 5) {
//            self.tabModel.tabImages = @[@[imageList[5],imageList[1]],
//                                        @[imageList[6],imageList[2]],
//                                        @[imageList[0]]
//                                        ];
//        }
//        if (self.tabModel.isBundleImage) {
//            [self downTabImageWithModel: self.tabModel];
//        }
//        else {
//            
//            BOOL needReload = NO;
//            SDImageCache *cache = [SDImageCache  sharedImageCache];
//            for (NSString *newUrl in self.tabModel.tabImages) {
//                if (![cache imageFromDiskCacheForKey: newUrl]) {
//                    needReload = YES;
//                    break;
//                }
//            }
//            //有新的TabImage需要下载
//            if (needReload) {
//                for (NSString *oldUrl in self.tabModel.tabImages) {
//                    [cache removeImageForKey: oldUrl fromDisk: YES];
//                }
//                self.tabModel = response.data;
//                self.tabModel.isBundleImage = NO;
//            }
//        }
//
//    } failure:^(NSURLSessionDataTask *task, NSError *error) {
//        NSLog(@"%@",error);
//    }];
//}
//
//
//#pragma mark - 下载TabImage
//- (void)downTabImageWithModel:(TabbarItemModel *)tabModel {
//    for (NSArray *imageList in tabModel.tabImages) {
//        for (NSString *imageUrl in imageList) {
//            
//            [[TaskService service] downloadImage: imageUrl complete:^{
//                if ([self tabImageIsDownloadComplete: tabModel.tabImages]) {
//                    tabModel.isBundleImage = NO;
//                    tabModel.downloadComplete = YES;
//                    [self saveTabImage: tabModel];
//                    
//                    self.tabModel = tabModel;
//                }
//            } failure:^(NSError *error) {
//                
//            }];
//            
//        }
//    }
//}
//#pragma mark - TabImage下载完成，保存到本地
//- (void)saveTabImage:(TabbarItemModel *)tabModel {
//    
//    NSArray *keyList    = @[@[@"tab1Sel", @"tab1"],
//                            @[@"tab2Sel", @"tab2"],
//                            @[@"tabBg"]];
//
//    UserDefaults *defaults      = [UserDefaults shareDefaults];
//    NSMutableDictionary *muDic  = [NSMutableDictionary dictionary];
//    for (int i=0; i< tabModel.tabImages.count; i++) {
//        NSArray *aList = tabModel.tabImages[i];
//        if ((int)(aList.count) > 0) {
//            [muDic setObject: aList[0] forKey: keyList[i][0]];
//        }
//        if ((int)(aList.count) > 1) {
//            [muDic setObject: aList[0] forKey: keyList[i][1]];
//        }
//
//    }
//    
//    [defaults setObject: muDic forKey: TAB_IMAGE_KEY];
//    
//}


//- (void)initBadgeNumbersForTab {
//    int tabCount = (int)[self.tabbarControllers count];
//    NSMutableArray *list = [NSMutableArray arrayWithCapacity: tabCount];
//    
//    for (int i=0; i< tabCount; i++) {
//        BadgeModel *badge = [[BadgeModel alloc] init];
//        badge.badgeIndex = i;
//        [list addObject: badge];
//    }
//    self.tabbarBadgeList = [NSArray arrayWithArray: list];
//}


@end
