//
//  ArticleViewModel.m
//  VaccinePatient
//
//  Created by maorenchao on 16/6/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ArticleViewModel.h"
#import "ArticleInfoModel.h"

typedef NS_ENUM(NSInteger, ArticleViewModelUserScene) {
    AVM_Scene_ByUsePlace,
    AVM_Scene_ByCatagory,
    AVM_Scene_ByCollect,
    AVM_Scene_Oprate
};

@interface ArticleViewModel()
@property(nonatomic, assign)ArticleViewModelUserScene scene;

@property(nonatomic, assign)NSInteger useplace;//使用场景  1 首页推荐 2 发现推荐|string
@property(nonatomic, strong)NSString *catagory;//类别

@property(nonatomic, strong)NSString *articleId;

@end

@implementation ArticleViewModel

- (instancetype)initWithUsePlace:(NSInteger)usePlace
{
    self = [super init];
    if (self) {
        self.useplace = usePlace;
        self.scene = AVM_Scene_ByUsePlace;
    }
    return self;
}

- (instancetype)initWithCategory:(NSString *)category
{
    self = [super init];
    if (self) {
        self.catagory = category;
        self.scene = AVM_Scene_ByCatagory;
    }
    return self;
}
- (instancetype)initWithColectScene
{
    self = [super init];
    if (self) {
        self.scene = AVM_Scene_ByCollect;
    }
    return self;
}

- (instancetype)initWithArticleId:(NSString *)articleId
{
    self = [super init];
    if (self) {
        self.articleId = articleId;
        self.scene = AVM_Scene_Oprate;
    }
    return self;
}

#pragma mark -get list

- (void)getRecommentArticles
{
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    self.hasMore = NO;
    self.moreParams = nil;
    
    [self.adapter request:[self getRequetURL] params:[self getParames] class:[AritcleModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        ListModel *listModel = response.data;
        self.articles = listModel.content;
        self.hasMore = listModel.more.boolValue;
        if (self.hasMore) {
            self.moreParams = listModel.more_params;
        }else {
        
        }
        
        if (self.hasMore == NO) {
            self.moreParams = nil;
        }
        else
        {
            self.moreParams = listModel.more_params;
        }
        
        if (self.articles.count != 0) {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }
        else
        {
            self.requestCompeleteType = RequestCompeleteEmpty;
        }
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        self.requestCompeleteType = RequestCompeleteError;
    }];
    
}

- (void)loardingMore
{
    [self.adapter request:[self getRequetURL] params:[self getParames] class:[AritcleModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        ListModel *listModel = response.data;
        self.articles = [self.articles arrayByAddingObjectsFromArray:listModel.content];
        self.hasMore = listModel.more.boolValue;
        if (self.hasMore == NO) {
            self.moreParams = nil;
        }
        else
        {
            self.moreParams = listModel.more_params;
        }
        
        if (self.articles.count != 0) {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }
        else
        {
            self.requestCompeleteType = RequestCompeleteEmpty;
        }
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        self.requestCompeleteType = RequestCompeleteError;
    }];
}

#pragma mark -文章操作 －增加分享数 － 收藏 － 取消收藏
//收藏
- (void)articleCollectArticleWithId: (NSString *)articleId
                           complete: (IMPLCompleteBlock)complete
                            failure: (IMPLFailuredBlock)failure
{
    if ([UserManager manager].uid.length == 0) {
        failure(nil);
        return;
    }
    if (articleId.length > 0) {
        self.articleId = articleId;
    }
    else
    {
        failure(nil);
        return;
    }
    
    NSMutableDictionary *dic = [NSMutableDictionary dictionary];
    [dic setObject:[UserManager manager].uid forKey:@"uid"];
    [dic setObject:self.articleId forKey:@"articleid"];
    
    [self.adapter request:Article_Do_Collect params:dic class:nil responseType:Response_Message method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        [MBProgressHUDHelper showHudWithText: response.message];
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        failure(error);
    }];
}
//取消收藏
- (void)articleDisCollectArticleWithId: (NSString *)articleId
                              complete: (IMPLCompleteBlock)complete
                               failure: (IMPLFailuredBlock)failure
{
    if ([UserManager manager].uid.length == 0) {
        failure(nil);
        return;
    }
    if (articleId.length > 0) {
        self.articleId = articleId;
    }
    else
    {
        failure(nil);
        return;
    }
    
    NSMutableDictionary *dic = [NSMutableDictionary dictionary];
    [dic setObject:[UserManager manager].uid forKey:@"uid"];
    [dic setObject:self.articleId forKey:@"articleid"];
    
    [self.adapter request:Article_Collect_Cancel params:dic class:nil responseType:Response_Message method:Request_DELETE needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        [MBProgressHUDHelper showHudWithText: response.message];
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        failure(error);
    }];
}
//增加分享数
- (void)articleAddShareWithArticleId: (NSString *)articleId
                            complete: (IMPLCompleteBlock)complete
                             failure: (IMPLFailuredBlock)failure
{
    if (articleId.length > 0) {
        self.articleId = articleId;
    }
    else
    {
        failure(nil);
        return;
    }
    
    NSMutableDictionary *dic = [NSMutableDictionary dictionary];
    [dic setObject:self.articleId forKey:@"articleid"];
    
    [self.adapter request:Article_AddShare params:dic class:nil responseType:Response_Message method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        failure(error);
    }];
}
//检查文章是否收藏
- (void)articleCheckCollectWithArticleId: (NSString *)articleId
                                complete: (IMPLCompleteWithResponseBlock)complete
                                 failure: (IMPLFailuredBlock)failure
{
    if (articleId.length > 0) {
        self.articleId = articleId;
    }else {
        failure(nil);
        return;
    }
    
    NSMutableDictionary *dic = [NSMutableDictionary dictionary];
    if ([UserManager manager].uid.length != 0) {
        [dic setObject:[UserManager manager].uid forKey:@"uid"];
    }

    [dic setObject:self.articleId forKey:@"id"];
    
    [self.adapter request:Article_Check_Collect params:dic class:[ArticleInfoModel class] responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        complete(response.data);
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        failure(error);
    }];
}

#pragma mark -tool
- (NSDictionary *)getParames
{
    NSMutableDictionary *dic = [NSMutableDictionary dictionary];
    
    if (self.scene == AVM_Scene_ByCatagory) {
        [dic setObject:self.catagory?self.catagory:@"" forKey:@"tabid"];
    }
    else if (self.scene == AVM_Scene_ByUsePlace)
    {
        [dic setObject:[NSNumber numberWithInteger:self.useplace] forKey:@"useplace"];
    }
    else if (self.scene == AVM_Scene_ByCollect)
    {
        if ([UserManager manager].uid.length > 0) {
            [dic setObject:[UserManager manager].uid forKey:@"uid"];
        }
    }
    
    if (self.moreParams) {
        [dic setObject: self.moreParams[@"flag"] forKey: @"flag"];
    }
    
    if (dic.allKeys.count == 0) {
        return nil;
    }
    
    return dic;
}

- (NSString *)getRequetURL
{
    if (self.scene == AVM_Scene_ByCatagory) {
        return Article_Query_Tab;
    }
    else if (self.scene == AVM_Scene_ByUsePlace)
    {
        return Article_Query;
    }
    else if (self.scene == AVM_Scene_ByCollect)
    {
        return Article_Collects;
    }
    return nil;
}

@end
