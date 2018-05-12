//
//  SearchResultViewModel.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SearchResultAllModel.h"
#define SearchReslutURL @"home/search/all"

/*
   首页搜索的所有数据-医院、文章、医生  
 */



@interface SearchResultViewModel : BaseViewModel
@property (nonatomic, strong) SearchResultAllModel * allModel;

- (void)getSearchResultList:(NSString * )keyword  success:(void(^)(void))success
                    failure: (void(^)(NSError * error))failure;


@end
