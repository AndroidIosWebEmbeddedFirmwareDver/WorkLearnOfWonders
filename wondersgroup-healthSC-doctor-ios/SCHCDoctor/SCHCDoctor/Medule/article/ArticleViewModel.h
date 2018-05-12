//
//  ArticleViewModel.h
//  VaccinePatient
//
//  Created by maorenchao on 16/6/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "AritcleModel.h"

@interface ArticleViewModel : BaseViewModel
@property(nonatomic, strong)NSArray *articles;

- (instancetype)initWithUsePlace:(NSInteger)usePlace; //使用场景  1 首页推荐 2 发现推荐|string
- (instancetype)initWithCategory:(NSString *)category; //根据类别
- (instancetype)initWithColectScene; //收藏
- (instancetype)initWithArticleId:(NSString *)articleId;  //文章操作 收藏等

- (void)getRecommentArticles;
- (void)loardingMore;

- (void)articleDisCollectArticleWithId: (NSString *)articleId
                              complete: (IMPLCompleteBlock)complete
                               failure: (IMPLFailuredBlock)failure;
#pragma mark -文章操作 －增加分享数 － 收藏 － 取消收藏
//收藏
- (void)articleCollectArticleWithId: (NSString *)articleId
                           complete: (IMPLCompleteBlock)complete
                            failure: (IMPLFailuredBlock)failure;

//检查文章是否收藏
- (void)articleCheckCollectWithArticleId: (NSString *)articleId
                                complete: (IMPLCompleteWithResponseBlock)complete
                                 failure: (IMPLFailuredBlock)failure;

//增加分享数
- (void)articleAddShareWithArticleId: (NSString *)articleId
                            complete: (IMPLCompleteBlock)complete
                             failure: (IMPLFailuredBlock)failure;

@end
