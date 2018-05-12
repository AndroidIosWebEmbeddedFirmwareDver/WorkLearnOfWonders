//
//  SearchResultViewController.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"
#import "HomeSearchViewController.h"
@interface SearchResultViewController : BaseViewController
@property (nonatomic,strong) NSString * searchBarText;
@property (nonatomic,assign) HomeSearchType type; 
@end
