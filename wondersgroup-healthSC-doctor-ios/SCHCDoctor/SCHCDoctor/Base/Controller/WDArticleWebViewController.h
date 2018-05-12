//
//  WDArticleWebViewController.h
//  EyeProtection
//
//  Created by 杜凯 on 16/3/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDBaseWebViewController.h"
#import "AritcleModel.h"

@interface WDArticleWebViewController : WDBaseWebViewController
@property (nonatomic,copy)NSString * articleId;
@property (nonatomic,strong)AritcleModel * model;

@end
