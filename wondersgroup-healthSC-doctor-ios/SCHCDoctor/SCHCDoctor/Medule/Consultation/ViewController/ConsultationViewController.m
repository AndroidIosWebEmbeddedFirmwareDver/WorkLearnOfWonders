//
//  ConsultationViewController.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ConsultationViewController.h"
#import "ConsultationViewModel.h"
#import "ConsultationCell.h"
#import "SCMessageViewController.h"

@interface ConsultationViewController () <EaseConversationListViewControllerDataSource,EaseConversationListViewControllerDelegate>

@property (nonatomic, strong) ConsultationViewModel *viewModel;

@end

@implementation ConsultationViewController

-(instancetype)init{
    self = [super init];
    if (self) {
        self.viewModel = [ConsultationViewModel new];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];

    self.title = @"图文咨询";
//    [[EMClient sharedClient].chatManager getConversation:@"sctest001" type:EMConversationTypeChat createIfNotExist:YES];    
    
    // Do any additional setup after loading the view.
    self.showRefreshHeader = YES;
    self.dataSource = self;
    self.delegate = self;
    //首次进入加载数据
    [self tableViewDidTriggerHeaderRefresh];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
//    [super tableView:tableView cellForRowAtIndexPath:indexPath];
    NSString *CellIdentifier = [EaseConversationCell cellIdentifierWithModel:nil];
    ConsultationCell *cell = (ConsultationCell *)[tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    // Configure the cell...
    if (cell == nil) {
        cell = [[ConsultationCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
    
    if ([self.dataArray count] <= indexPath.row) {
        return cell;
    }
    
    id<IConversationModel> model = [self.dataArray objectAtIndex:indexPath.row];
    EMMessage *lastReceivedMsg = model.conversation.lastReceivedMessage;
    model.avatarURLPath = lastReceivedMsg.ext[@"avatar"];
    model.title = lastReceivedMsg.ext[@"nickName"];
    cell.model = model;
    cell.avatarView.showBadge = NO;
    cell.badge = model.conversation.unreadMessagesCount;
    
    
    cell.detailLabel.attributedText =  [[EaseEmotionEscape sharedInstance] attStringFromTextForChatting:[self latestMessageTitleForConversationModel:model]textFont:cell.detailLabel.font];
    cell.timeLabel.text = [self latestMessageTimeForConversationModel:model];

    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 80;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 10;
}

- (NSString *)latestMessageTitleForConversationModel:(id<IConversationModel>)conversationModel {
    NSString *latestMessageTitle = @"";
    EMMessage *lastMessage = [conversationModel.conversation latestMessage];
    if (lastMessage) {
        EMMessageBody *messageBody = lastMessage.body;
        switch (messageBody.type) {
            case EMMessageBodyTypeImage:{
                latestMessageTitle = NSLocalizedString(@"message.image1", @"[image]");
            } break;
            case EMMessageBodyTypeText:{
                // 表情映射。
                NSString *didReceiveText = [EaseConvertToCommonEmoticonsHelper
                                            convertToSystemEmoticons:((EMTextMessageBody *)messageBody).text];
                latestMessageTitle = didReceiveText;
                if ([lastMessage.ext objectForKey:MESSAGE_ATTR_IS_BIG_EXPRESSION]) {
                    latestMessageTitle = @"[动画表情]";
                }
            } break;
            case EMMessageBodyTypeVoice:{
                latestMessageTitle = NSLocalizedString(@"message.voice1", @"[voice]");
            } break;
            case EMMessageBodyTypeLocation: {
                latestMessageTitle = NSLocalizedString(@"message.location1", @"[location]");
            } break;
            case EMMessageBodyTypeVideo: {
                latestMessageTitle = NSLocalizedString(@"message.video1", @"[video]");
            } break;
            case EMMessageBodyTypeFile: {
                latestMessageTitle = NSLocalizedString(@"message.file1", @"[file]");
            } break;
            default: {
            } break;
        }
    }
    
    return latestMessageTitle;
}

- (NSString *)latestMessageTimeForConversationModel:(id<IConversationModel>)conversationModel {
    NSString *latestMessageTime = @"";
    EMMessage *lastMessage = [conversationModel.conversation latestMessage];;
    if (lastMessage) {
        latestMessageTime = [NSDate formattedTimeFromTimeInterval:lastMessage.timestamp];
    }
    return latestMessageTime;
}

- (void)conversationListViewController:(EaseConversationListViewController *)conversationListViewController
            didSelectConversationModel:(id<IConversationModel>)conversationModel
{
    //样例展示为根据conversationModel，进入不同的会话ViewController
    if (conversationModel) {
        EMConversation *conversation = conversationModel.conversation;
        if (conversation) {
            SCMessageViewController *chatController = [[SCMessageViewController alloc] initWithConversationChatter:conversation.conversationId conversationType:EMConversationTypeChat];
//            EMMessage *msg = conversation.latestMessage;
//            if (msg.direction == EMMessageDirectionSend) {
//                chatController.title = msg.to;
//            } else {
//                chatController.title = msg.from;
//            }
            EMMessage *lastReceivedMsg = conversation.lastReceivedMessage;
            chatController.title = lastReceivedMsg.ext[@"nickName"];
            
            chatController.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:chatController animated:YES];
        }
        //[[NSNotificationCenter defaultCenter] postNotificationName:@"setupUnreadMessageCount" object:nil];
        //[self.tableView reloadData];
    }
}



/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
