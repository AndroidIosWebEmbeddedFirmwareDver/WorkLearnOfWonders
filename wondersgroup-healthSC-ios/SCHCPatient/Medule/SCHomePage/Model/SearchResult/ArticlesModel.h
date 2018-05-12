//
//  ArticlesModel.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"
#import "ArticlessModel.h"

@interface ArticlesModel : BaseModel
/*
 "id": "21",
 "thumb": "http://img.wdjky.com/2a2041da1452772418608.jpg?imageView2",
 "title": "三成都那个医院儿科好？",
 "desc": "成都的那个医院儿科好...................",
 "pv": "21",
 "url": "http://10.1.1.1/web/article/new/articleView?from=newsArticle&for_type=article&id=21&isToken=1"
 */
@property (nonatomic,strong) NSNumber * more;
@property (nonatomic,strong) NSArray <ArticlessModel * > * content;
@end
