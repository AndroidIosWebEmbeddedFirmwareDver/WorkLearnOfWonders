//
//  SCEvaluationModel.h
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

/// 医院评价模型
/*
 "id": 21,
 "uid": "132dwf4fgrf4gt5",
 "nickName":"健康用户1101",
 "content": "里约奥运会4X100米混合泳接力比赛，是菲尔普斯在奥运舞台的最后一战。此前他已亲口宣布自己熬不到下一",
 "createTime": "2016-11-02 15:11"
 */
#import "BaseModel.h"

@interface SCEvaluationModel : BaseModel

/// 评价ID
@property (nonatomic, copy) NSString *mID;
/// 评价用户id
@property (nonatomic, copy) NSString *uid;
/// 评价人用户名
@property (nonatomic, copy) NSString *nickName;
/// 评价内容
@property (nonatomic, copy) NSString *content;
/// 创建时间
@property (nonatomic, copy) NSString *createTime;

@end
