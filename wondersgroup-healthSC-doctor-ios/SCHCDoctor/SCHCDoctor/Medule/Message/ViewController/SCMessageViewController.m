//
//  SCMessageViewController.m
//  SCHCPatient
//
//  Created by Gu Jiajun on 2017/6/1.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "SCMessageViewController.h"


@interface SCMessageViewController () <EMChatManagerDelegate,EaseMessageViewControllerDataSource>


@end

@implementation SCMessageViewController

- (void)viewDidLoad {
    //先设置每页数量
    self.messageCountOfPage = 10;

    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.view.backgroundColor = [UIColor bc2Color];
    self.tableView.backgroundColor = [UIColor bc2Color];
    self.showRefreshHeader = YES;
    self.dataSource = self;
    
    [self.chatBarMoreView removeItematIndex:1];
    [self.chatBarMoreView removeItematIndex:2];
    [self.chatBarMoreView removeItematIndex:2];
    
    [self.chatBarMoreView updateItemWithImage:[UIImage imageNamed:@"图片"] highlightedImage:nil title:nil atIndex:0];
    [self.chatBarMoreView updateItemWithImage:[UIImage imageNamed:@"相机"] highlightedImage:nil title:nil atIndex:1];
    
    
    [[EaseBaseMessageCell appearance] setSendBubbleBackgroundImage:[[UIImage imageNamed:@"对话框发"] stretchableImageWithLeftCapWidth:8 topCapHeight:30]];//设置发送气泡
    
    [[EaseBaseMessageCell appearance] setRecvBubbleBackgroundImage:[[UIImage imageNamed:@"对话框收"] stretchableImageWithLeftCapWidth:20 topCapHeight:30]];//设置接收气泡
    
//    EMMessage *msg = [self.conversation latestMessage];
//    
//    [self.conversation loadMessagesStartFromId:msg.messageId count:2 searchDirection:EMMessageSearchDirectionUp completion:^(NSArray *aMessages, EMError *aError) {
//        for (NSInteger i = 0; i < aMessages.count; i++) {
//            [self addMessageToDataSource:aMessages[i] progress:nil];
//        }
//        
//        //单独添加一条
//        [self addMessageToDataSource:msg progress:nil];
//    }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

//- (void)tableViewDidTriggerHeaderRefresh {
//    //子类需要重写此方法
//    
//    EMMessage *msg = [self.messsagesSource lastObject];
//    
//    [self.conversation loadMessagesStartFromId:msg.messageId count:2 searchDirection:EMMessageSearchDirectionUp completion:^(NSArray *aMessages, EMError *aError) {
//        for (NSInteger i = 0; i < aMessages.count; i++) {
//            [self addMessageToDataSource:aMessages[i] progress:nil];
//        }
//        
//        [self.tableView.mj_header endRefreshing];
//
//    }];
//}

/*!
 @method
 @brief 获取消息自定义cell
 @discussion 用户根据messageModel判断是否显示自定义cell。返回nil显示默认cell，否则显示用户自定义cell
 @param tableView 当前消息视图的tableView
 @param messageModel 消息模型
 @result 返回用户自定义cell
 */
//- (UITableViewCell *)messageViewController:(UITableView *)tableView
//                       cellForMessageModel:(id<IMessageModel>)messageModel;

/*!
 @method
 @brief 获取消息cell高度
 @discussion 用户根据messageModel判断，是否自定义显示cell的高度
 @param viewController 当前消息视图
 @param messageModel 消息模型
 @param cellWidth 视图宽度
 @result 返回用户自定义cell
 */
//- (CGFloat)messageViewController:(EaseMessageViewController *)viewController
//           heightForMessageModel:(id<IMessageModel>)messageModel
//                   withCellWidth:(CGFloat)cellWidth {
//    
//}

//具体创建自定义Cell的样例：
- (UITableViewCell *)messageViewController:(UITableView *)tableView cellForMessageModel:(id<IMessageModel>)model
{
    //样例为如果消息是文本消息显示用户自定义cell
    if (model.bodyType == EMMessageBodyTypeText) {
        NSString *CellIdentifier = [EaseMessageCell cellIdentifierWithModel:model];
        //CustomMessageCell为用户自定义cell,继承了EaseBaseMessageCell
        EaseMessageCell *cell = (EaseMessageCell *)[tableView dequeueReusableCellWithIdentifier:CellIdentifier];
        if (cell == nil) {
            cell = [[EaseMessageCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier model:model];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            cell.bubbleMaxWidth = 250;
            
        }
        cell.model = model;
        
        return cell;
    }
    return nil;
}

- (CGFloat)messageViewController:(EaseMessageViewController *)viewController
           heightForMessageModel:(id<IMessageModel>)messageModel
                   withCellWidth:(CGFloat)cellWidth
{
    //样例为如果消息是文本消息使用用户自定义cell的高度
    if (messageModel.bodyType == EMMessageBodyTypeText) {
        //CustomMessageCell为用户自定义cell,继承了EaseBaseMessageCell
        return [EaseMessageCell cellHeightWithModel:messageModel];
    }
    return 0.f;
}

- (void)messagesDidReceive:(NSArray *)aMessages {
    NSLog(@"aMessages");
}

- (void)messagesDidDeliver:(NSArray *)aMessages {
    
}

- (id<IMessageModel>)messageViewController:(EaseMessageViewController *)viewController
                           modelForMessage:(EMMessage *)message {
    
    id<IMessageModel> model = nil;
    model = [[EaseMessageModel alloc] initWithMessage:message];
    
    model.avatarImage = [UIImage imageNamed:@"EaseUIResource.bundle/user"];//默认头像
    
    if (message.direction == EMMessageDirectionSend) {
        //是自己，直接取user里的头像
        model.avatarURLPath = @"http://tva2.sinaimg.cn/crop.0.0.1024.1024.180/718878b5jw8esr2geokzkj20sg0sg0ts.jpg";//头像网络地址
        model.nickname = @"真名";
    } else {
        EMMessage *lastReceivedMsg = self.conversation.lastReceivedMessage;
        NSDictionary *ext = message.ext;
        if ([lastReceivedMsg.ext[@"avatar"] isEqualToString:message.ext[@"avatar"]]) {
            model.avatarURLPath = ext[@"avatar"];//头像网络地址
        } else {
            model.avatarURLPath = lastReceivedMsg.ext[@"avatar"];//新头像网络地址
        }
        
        if ([lastReceivedMsg.ext[@"nickName"] isEqualToString:message.ext[@"nickName"]]) {
            model.nickname = ext[@"nickName"];//model.nickname;//用户昵称
        } else {
            model.nickname = lastReceivedMsg.ext[@"nickName"];//新头像网络地址
        }
    }
   
    
    return model;
}


@end
