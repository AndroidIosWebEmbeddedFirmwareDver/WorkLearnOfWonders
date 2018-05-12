//
//  HomeViewModel.m
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/5.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "HomeViewModel.h"
#import "EaseUI.h"

@implementation HomeViewModel

-(void)reloadDatas:(HomeRedTipModel *)tipModel {
    
    HomeFunctionModel *model = [HomeFunctionModel new];
    model.image = @"图文咨询";
    model.content = @"图文咨询";
    model.invalid = @0;
    model.isNeedRedPoint = @YES;
    
    HomeFunctionModel *model1 = [HomeFunctionModel new];
    model1.image = @"患者管理";
    model1.content = @"患者管理";
    model1.invalid = @0;
    model1.isNeedRedPoint = tipModel.hasNewPatient;

    HomeFunctionModel *model2 = [HomeFunctionModel new];
    model2.image = @"转诊服务";
    model2.content = @"转诊服务";
    model2.invalid = @0;
    model2.isNeedRedPoint = tipModel.hasNewReferral;

    HomeFunctionModel *model3 = [HomeFunctionModel new];
    model3.image = @"我的预约";
    model3.content = @"我的预约";
    model3.invalid = @1;
    model3.isNeedRedPoint = @NO;

    HomeFunctionModel *model4 = [HomeFunctionModel new];
    model4.image = @"电话咨询";
    model4.content = @"电话咨询";
    model4.invalid = @1;
    model4.isNeedRedPoint = @NO;

    HomeFunctionModel *model5 = [HomeFunctionModel new];
    model5.image = @"消息";
    model5.content = @"消息";
    model5.invalid = @1;
    model5.isNeedRedPoint = @NO;

    self.datas = @[model,model1,model2,model3,model4,model5];
}

-(void)getEaseRedTip {

    
}

-(void)getHomeRedTip {

    [self reloadDatas:nil];
    return;
    
    NSDictionary *dic = @{@"orgCode":[UserManager manager].uid};
    WS(weakSelf)
    
    [self.adapter request:INDEX_PROMPT
                   params:dic
                    class:[HomeRedTipModel class]
             responseType:Response_Object
                   method:Request_GET
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {

                      HomeRedTipModel *model = response.data;
                      [weakSelf reloadDatas:model];
                      weakSelf.requestCompeleteType = RequestCompeleteSuccess;

                  } failure:^(NSURLSessionDataTask *task, NSError *error) {
                      weakSelf.requestCompeleteType = RequestCompeleteError;
                  }];
    
}

@end
