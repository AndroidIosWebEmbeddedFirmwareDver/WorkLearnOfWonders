//
//  UpdateInfoModel.h
//  VaccinePatient
//
//  Created by maorenchao on 16/6/16.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"

@interface UpdateInfoModel : BaseModel
//"updateInfo":{// APP版本更新信息(存在该节点说明有更新,不存在则无更新)
    //            "lastVersion":"1.0.0",// 最新版本号
    //            "enforceUpdate":0,// 是否强制升级,0-非强制,1-强制
    //            "updateLogs":[// 更新信息
    //                          "全新上线！",
    //                          "敬请期待！"
    //                          ]
    //        },
@property(nonatomic, strong)NSString *lastVersion;
@property(nonatomic, strong)NSNumber *forceUpdate;
@property(nonatomic, strong)NSNumber *hasUpdate;
@property(nonatomic, strong)NSString *updateMsg;
@property(nonatomic, strong)NSString *downloadUrl;
@end
