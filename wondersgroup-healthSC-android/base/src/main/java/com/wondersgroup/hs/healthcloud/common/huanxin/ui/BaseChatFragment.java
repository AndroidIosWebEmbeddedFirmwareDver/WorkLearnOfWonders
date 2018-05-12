package com.wondersgroup.hs.healthcloud.common.huanxin.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.ClipboardManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hyphenate.EMChatRoomChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMMucSharedFile;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.PathUtil;
import com.wondersgroup.hs.healthcloud.base.R;
import com.wondersgroup.hs.healthcloud.common.BaseFragment;
import com.wondersgroup.hs.healthcloud.common.entity.PhotoModel;
import com.wondersgroup.hs.healthcloud.common.entity.event.EasemobUnreadMsgChangeEvent;
import com.wondersgroup.hs.healthcloud.common.huanxin.EaseConstant;
import com.wondersgroup.hs.healthcloud.common.huanxin.controller.EaseUI;
import com.wondersgroup.hs.healthcloud.common.huanxin.domain.EaseEmojicon;
import com.wondersgroup.hs.healthcloud.common.huanxin.model.EaseAtMessageHelper;
import com.wondersgroup.hs.healthcloud.common.huanxin.utils.EaseCommonUtils;
import com.wondersgroup.hs.healthcloud.common.huanxin.utils.EaseUserUtils;
import com.wondersgroup.hs.healthcloud.common.huanxin.widget.EaseChatExtendMenu;
import com.wondersgroup.hs.healthcloud.common.huanxin.widget.EaseChatInputMenu;
import com.wondersgroup.hs.healthcloud.common.huanxin.widget.EaseChatMessageList;
import com.wondersgroup.hs.healthcloud.common.huanxin.widget.EaseVoiceRecorderView;
import com.wondersgroup.hs.healthcloud.common.huanxin.widget.chatrow.EaseCustomChatRowProvider;
import com.wondersgroup.hs.healthcloud.common.logic.PhotoManager;
import com.wondersgroup.hs.healthcloud.common.util.BaseConstant;
import com.wondersgroup.hs.healthcloud.common.util.PermissionType;
import com.wondersgroup.hs.healthcloud.common.util.RequestPermissionAuthorize;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.TitleBar;
import com.wondersgroup.hs.healthcloud.common.view.photopick.PhotoPicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 可以直接new出来使用的聊天对话页面fragment，
 * 使用时需调用setArguments方法传入chatType(会话类型)和userId(用户或群id)
 * app也可继承此fragment续写
 * <br/>
 * <br/>
 * 参数传入示例可查看demo里的ChatActivity
 */
public class BaseChatFragment extends BaseFragment {
    protected static final String TAG = "EaseChatFragment";
    protected static final int REQUEST_CODE_MAP = 1;
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;

    /**
     * 传入fragment的参数
     */
    protected Bundle fragmentArgs;
    protected int chatType;
    protected String toChatUsername;
    protected String toChatUserAvatar;
    protected String fromChatNickname;
    protected String fromChatUserAvatar;
    protected EaseChatMessageList messageList;
    protected EaseChatInputMenu inputMenu;

    protected EMConversation conversation;

    protected InputMethodManager inputManager;
    protected ClipboardManager clipboard;

    protected Handler handler = new Handler();
    protected File cameraFile;
    protected EaseVoiceRecorderView voiceRecorderView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ListView listView;

    protected boolean isloading;
    protected boolean haveMoreData = true;
    protected int pagesize = 20;
    protected GroupListener groupListener;
    protected EMMessage contextMenuMessage;

    protected static final int ITEM_TAKE_PICTURE = 1;
    protected static final int ITEM_PICTURE = 2;
    protected static final int ITEM_LOCATION = 3;

    protected int[] itemStrings = {R.string.attach_take_pic, R.string.attach_picture, R.string.attach_location};
    protected int[] itemdrawables = {R.drawable.ease_chat_takepic_selector, R.drawable.ease_chat_image_selector,
            R.drawable.ease_chat_location_selector};
    protected int[] itemIds = {ITEM_TAKE_PICTURE, ITEM_PICTURE, ITEM_LOCATION};
    private EMChatRoomChangeListener chatRoomChangeListener;
    private boolean isMessageListInited;
    protected MyItemClickListener extendMenuItemClickListener;
    protected TitleBar mTitleBar;
    protected PhotoPicker mPhotoPicker;
    protected RelativeLayout mContent;
    private RequestPermissionAuthorize permission;

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ease_fragment_chat, null);
        mContent = (RelativeLayout) rootView.findViewById(R.id.rl_content);
        fragmentArgs = getArguments();
        //sun add
        if (fragmentArgs == null) {
            fragmentArgs = new Bundle();
            fragmentArgs.putString(EaseConstant.EXTRA_CHAT_TO_USER_ID, "");
        }
        // 判断单聊还是群聊
        chatType = fragmentArgs.getInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        // 会话人或群组id
        toChatUsername = fragmentArgs.getString(EaseConstant.EXTRA_CHAT_TO_USER_ID, "");
//        toChatUserAvatar = fragmentArgs.getString(EaseConstant.EXTRA_CHAT_TO_USER_AVATAR,"");
        fromChatNickname = fragmentArgs.getString(EaseConstant.EXTRA_CHAT_FROM_NICK_NAME, "");
        fromChatUserAvatar = fragmentArgs.getString(EaseConstant.EXTRA_CHAT_FROM_USER_AVATAR,"");
        permission = RequestPermissionAuthorize.build(mBaseActivity);
        mBaseActivity.setPermission(permission);

        return rootView;
    }

    /**
     * init view
     */
    @Override
    protected void initViews() {
        //初始化头部
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.setLeftImageResource(R.mipmap.ic_back);
//        mTitleBar.setBackgroundResource(R.color.bc8);
        mTitleBar.setLeftTextColor(getResources().getColor(R.color.tc1));
        mTitleBar.setActionTextColor(getResources().getColor(R.color.tc1));
        mTitleBar.setTitleColor(getResources().getColor(R.color.tc1));
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mBaseActivity instanceof BaseActivity) {
////                    ((BaseActivity) mBaseActivity).toMain();
//                }
                mBaseActivity.finish();
            }
        });
        // 按住说话录音控件
        voiceRecorderView = (EaseVoiceRecorderView) findViewById(R.id.voice_recorder);

        // 消息列表layout
        messageList = (EaseChatMessageList) findViewById(R.id.message_list);
        //单聊显示昵称
//        if (chatType == EaseConstant.CHATTYPE_SINGLE)
            messageList.setShowUserNick(false);
        listView = messageList.getListView();

        extendMenuItemClickListener = new MyItemClickListener();
        inputMenu = (EaseChatInputMenu) findViewById(R.id.input_menu);
        registerExtendMenuItem();
        // init input menu
        inputMenu.init(null);
        inputMenu.setChatInputMenuListener(new EaseChatInputMenu.ChatInputMenuListener() {

            @Override
            public void onSendMessage(String content) {
                // 发送文本消息
                sendTextMessage(content);
            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {
                //发送大表情(动态表情)
                sendBigExpressionMessage(emojicon.getName(), emojicon.getIdentityCode());
            }

            @Override
            public boolean onPressToSpeakBtnTouch(final View v, final MotionEvent event) {
                final boolean[] flag = new boolean[1];
                if (!permission.reservedForPermission(PermissionType.RECORD_AUDIO, new RequestPermissionAuthorize.PermissionSuccessCallBack() {
                    @Override
                    public void permissionSuccess() {
                        flag[0] = isOnPressToSpeakBtnTouch(v, event);
                    }
                })) {//发送语音，申请权限

                } else {
                    flag[0] = isOnPressToSpeakBtnTouch(v, event);
                }
                return flag[0];
            }

            private boolean isOnPressToSpeakBtnTouch(View v, MotionEvent event) {
                return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {

                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                        // 发送语音消息
                        sendVoiceMessage(voiceFilePath, voiceTimeLength);
                    }
                });
            }

        });

        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);

        inputManager = (InputMethodManager) mBaseActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        clipboard = (ClipboardManager) mBaseActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        mBaseActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mPhotoPicker = new PhotoPicker(this);
    }

    /**
     * 设置属性，监听等
     */
    @Override
    protected void initData(Bundle savedInstanceState) {
//        mTitleBar.setTitle(toChatUsername);
        if (chatType == EaseConstant.CHATTYPE_SINGLE) { // 单聊
            // 设置标题
            if (EaseUserUtils.getUserInfo(toChatUsername) != null) {
                mTitleBar.setTitle(EaseUserUtils.getUserInfo(toChatUsername).getNickname());
            }
        } else {
            if (chatType == EaseConstant.CHATTYPE_GROUP) {
                // 群聊
                EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
                if (group != null)
                    mTitleBar.setTitle(group.getGroupName());
                // 监听当前会话的群聊解散被T事件
                groupListener = new GroupListener();
                EMClient.getInstance().groupManager().addGroupChangeListener(groupListener);
            } else {
                onChatRoomViewCreation();
            }

        }
        if (chatType != EaseConstant.CHATTYPE_GROUP) {
            onConversationInit();
            onMessageListInit();
        }

        setRefreshLayoutListener();

        // show forward message if the message is not null
//        String forward_msg_id = getArguments().getString("forward_msg_id","");
//        if (forward_msg_id != null) {
//            // 发送要转发的消息
//            forwardMessage(forward_msg_id);
//        }
    }

    /**
     * 注册底部菜单扩展栏item; 覆盖此方法时如果不覆盖已有item，item的id需大于3
     */

    protected void registerExtendMenuItem() {
        for (int i = 0; i < itemStrings.length; i++) {
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], extendMenuItemClickListener);
        }
    }


    protected void onConversationInit() {
        // 获取当前conversation对象
        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername, EaseCommonUtils.getConversationType(chatType), true);
        // 把此会话的未读数置为0
        conversation.markAllMessagesAsRead();
        //sun add
        EventBus.getDefault().post(new EasemobUnreadMsgChangeEvent());
        // 初始化db时，每个conversation加载数目是getChatOptions().getNumberOfMessagesLoaded
        // 这个数目如果比用户期望进入会话界面时显示的个数不一样，就多加载一些
        final List<EMMessage> msgs = conversation.getAllMessages();
        int msgCount = msgs != null ? msgs.size() : 0;
        if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
            String msgId = null;
            if (msgs != null && msgs.size() > 0) {
                msgId = msgs.get(0).getMsgId();
            }
            conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
        }

    }

    protected void onMessageListInit() {
        messageList.init(toChatUsername, chatType, chatFragmentListener != null ?
                chatFragmentListener.onSetCustomChatRowProvider() : null);
        //设置list item里的控件的点击事件
        setListItemClickListener();

        messageList.getListView().setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                inputMenu.hideExtendMenuContainer();
                return false;
            }
        });

        isMessageListInited = true;
    }

    protected void setListItemClickListener() {
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                if (chatFragmentListener != null) {
                    chatFragmentListener.onAvatarClick(username);
                }
            }

            @Override
            public void onUserAvatarLongClick(String username) {

            }

            @Override
            public void onResendClick(final EMMessage message) {
                UIUtil.showAlert(mBaseActivity, mBaseActivity.getResources().getString(R.string.resend), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resendMessage(message);
                    }
                });
            }

            @Override
            public void onBubbleLongClick(EMMessage message) {
                contextMenuMessage = message;
                if (chatFragmentListener != null) {
                    chatFragmentListener.onMessageBubbleLongClick(message);
                }
            }

            @Override
            public boolean onBubbleClick(EMMessage message) {
                if (chatFragmentListener != null) {
                    return chatFragmentListener.onMessageBubbleClick(message);
                }
                return false;
            }
        });
    }

    protected void setRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
                            List<EMMessage> messages;
                            try {
                                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                                    messages = conversation.loadMoreMsgFromDB(messageList.getItem(0).getMsgId(),
                                            pagesize);
                                } else {
                                    messages = conversation.loadMoreMsgFromDB(messageList.getItem(0).getMsgId(),
                                            pagesize);
                                }
                            } catch (Exception e1) {
                                swipeRefreshLayout.setRefreshing(false);
                                return;
                            }
                            if (messages.size() > 0) {
                                messageList.refreshSeekTo(messages.size() - 1);
                                if (messages.size() != pagesize) {
                                    haveMoreData = false;
                                }
                            } else {
                                haveMoreData = false;
                            }

                            isloading = false;

                        } else {
                            Toast.makeText(mBaseActivity, mBaseActivity.getResources().getString(R.string.no_more_messages),
                                    Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 600);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) { // 发送照片
                if (cameraFile != null && cameraFile.exists())
                    sendImageMessage(cameraFile.getAbsolutePath());
            } else if (requestCode == BaseConstant.REQUEST_CODE_PICK) { // 发送本地图片
                mPhotoPicker.dealResult(requestCode, resultCode, data, new PhotoManager.OnLocalRecentListener() {
                    @Override
                    public void onPhotoLoaded(List<PhotoModel> photos) {
                        List<String> lstPhotos = new ArrayList<>();
                        for (PhotoModel photo : photos) {
                            lstPhotos.add(photo.getThumbPath());
                        }

                        sendPicByPath(lstPhotos.toArray(new String[]{}));
                    }
                });
            }
//            else if (requestCode == REQUEST_CODE_LOCAL) { // 发送本地图片
//                if (data != null) {
//                    Uri selectedImage = data.getData();
//                    if (selectedImage != null) {
//                        sendPicByUri(selectedImage);
//                    }
//                }
//            }
            else if (requestCode == REQUEST_CODE_MAP) { // 地图
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                String locationAddress = data.getStringExtra("address");
                if (locationAddress != null && !locationAddress.equals("")) {
                    sendLocationMessage(latitude, longitude, locationAddress);
                } else {
                    Toast.makeText(mBaseActivity, R.string.unable_to_get_loaction, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {

            for (EMMessage message : messages) {
                String username = null;
                // group message
                if (message.getChatType() == ChatType.GroupChat || message.getChatType() == ChatType.ChatRoom) {
                    username = message.getTo();
                } else {
                    // single chat message
                    username = message.getFrom();
                }

                // if the message is for current conversation
                if (username.equals(toChatUsername) || message.getTo().equals(toChatUsername)) {
                    messageList.refreshSelectLast();
                    EaseUI.getInstance().getNotifier().vibrateAndPlayTone(message);
                    conversation.markMessageAsRead(message.getMsgId());
                } else {
                    EaseUI.getInstance().getNotifier().onNewMsg(message);
                }
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {

        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            if (isMessageListInited) {
                messageList.refresh();
            }
        }

        @Override
        public void onMessageDelivered(List<EMMessage> messages) {
            if (isMessageListInited) {
                messageList.refresh();
            }
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            if (isMessageListInited) {
                messageList.refresh();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (isMessageListInited)
            messageList.refresh();
        EaseUI.getInstance().pushActivity(getActivity());
        // register the event listener when enter the foreground
        EMClient.getInstance().chatManager().addMessageListener(msgListener);

        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // unregister this event listener when this activity enters the
        // background
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);

        // 把此activity 从foreground activity 列表里移除
        EaseUI.getInstance().popActivity(mBaseActivity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (groupListener != null) {
            EMClient.getInstance().groupManager().removeGroupChangeListener(groupListener);
        }
        if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
        }

        if (chatRoomChangeListener != null) {
            EMClient.getInstance().chatroomManager().removeChatRoomChangeListener(chatRoomChangeListener);
        }
    }

    public void onBackPressed() {
        if (inputMenu.onBackPressed()) {
            getActivity().finish();
            if (chatType == EaseConstant.CHATTYPE_GROUP) {
                EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
                EaseAtMessageHelper.get().cleanToAtUserList();
            }
            if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
                EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
            }
        }
    }

    protected void onChatRoomViewCreation() {
        final ProgressDialog pd = ProgressDialog.show(mBaseActivity, "", "Joining......");
        EMClient.getInstance().chatroomManager().joinChatRoom(toChatUsername, new EMValueCallBack<EMChatRoom>() {

            @Override
            public void onSuccess(final EMChatRoom value) {
                mBaseActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mBaseActivity.isFinishing() || !toChatUsername.equals(value.getId()))
                            return;
                        pd.dismiss();
                        EMChatRoom room = EMClient.getInstance().chatroomManager().getChatRoom(toChatUsername);
                        if (room != null) {
                            mTitleBar.setTitle(room.getName());
                        } else {
                            mTitleBar.setTitle(toChatUsername);
                        }
                        EMLog.d(TAG, "join room success : " + room.getName());
                        addChatRoomChangeListenr();
                        onConversationInit();
                        onMessageListInit();
                    }
                });
            }

            @Override
            public void onError(final int error, String errorMsg) {
                // TODO Auto-generated method stub
                EMLog.d(TAG, "join room failure : " + error);
                mBaseActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                    }
                });
                mBaseActivity.finish();
            }
        });
    }


    protected void addChatRoomChangeListenr() {
//        chatRoomChangeListener = new EMChatRoomChangeListener() {
//
//            @Override
//            public void onChatRoomDestroyed(String roomId, String roomName) {
//                if (roomId.equals(toChatUsername)) {
//                    showChatroomToast(" room : " + roomId + " with room name : " + roomName + " was destroyed");
//                    mBaseActivity.finish();
//                }
//            }
//
//            @Override
//            public void onMemberJoined(String roomId, String participant) {
//                showChatroomToast("member : " + participant + " join the room : " + roomId);
//            }
//
//            @Override
//            public void onMemberExited(String roomId, String roomName, String participant) {
//                showChatroomToast("member : " + participant + " leave the room : " + roomId + " room name : " + roomName);
//            }
//
//            @Override
//            public void onMemberKicked(String roomId, String roomName, String participant) {
//                if (roomId.equals(toChatUsername)) {
//                    String curUser = EMClient.getInstance().getCurrentUser();
//                    if (curUser.equals(participant)) {
//                        EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
//                        mBaseActivity.finish();
//                    } else {
//                        showChatroomToast("member : " + participant + " was kicked from the room : " + roomId + " room name : " + roomName);
//                    }
//                }
//            }
//
//        };
//
//        EMClient.getInstance().chatroomManager().addChatRoomChangeListener(chatRoomChangeListener);
    }

    protected void showChatroomToast(final String toastContent) {
        mBaseActivity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(mBaseActivity, toastContent, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 扩展菜单栏item点击事件
     */
    class MyItemClickListener implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener {

        @Override
        public void onClick(int itemId, View view) {
            if (chatFragmentListener != null) {
                if (chatFragmentListener.onExtendMenuItemClick(itemId, view)) {
                    return;
                }
            }
            switch (itemId) {
                case ITEM_TAKE_PICTURE: // 拍照
                    selectPicFromCamera();
                    break;
                case ITEM_PICTURE:
                    selectPicFromLocal(); // 图库选择图片
                    break;
                case ITEM_LOCATION: // 位置
                    //TODO
//                    startActivityForResult(new Intent(mBaseActivity, EaseBaiduMapActivity.class), REQUEST_CODE_MAP);
                    break;

                default:
                    break;
            }
        }

    }


    //发送消息方法
    //==========================================================================
    protected void sendTextMessage(String content) {
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        sendMessage(message);
    }

    protected void sendBigExpressionMessage(String name, String identityCode) {
        EMMessage message = EaseCommonUtils.createExpressionMessage(toChatUsername, name, identityCode);
        sendMessage(message);
    }

    protected void sendVoiceMessage(String filePath, int length) {
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername);
        sendMessage(message);
    }

    protected void sendImageMessage(String imagePath) {
        EMMessage message = EMMessage.createImageSendMessage(imagePath, true, toChatUsername);
        sendMessage(message);
    }

    protected void sendLocationMessage(double latitude, double longitude, String locationAddress) {
        EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, toChatUsername);
        sendMessage(message);
    }

    protected void sendVideoMessage(String videoPath, String thumbPath, int videoLength) {
        EMMessage message = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername);
        sendMessage(message);
    }

    protected void sendFileMessage(String filePath) {
        EMMessage message = EMMessage.createFileSendMessage(filePath, toChatUsername);
        sendMessage(message);
    }

    protected void sendMessage(EMMessage message) {
        if (chatFragmentListener != null) {
            //设置扩展属性
            chatFragmentListener.onSetMessageAttributes(message);
        }
//        //设置强制推送
//        message.setAttribute("em_force_notification", true);
        // 如果是群聊，设置chattype,默认是单聊
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            message.setChatType(ChatType.GroupChat);
        } else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            message.setChatType(ChatType.ChatRoom);
        }
        //设置公共属性 头像昵称
        setCommonMessageAttributes(message);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        //刷新ui
        if (isMessageListInited) {
            messageList.refreshSelectLast();
        }
    }

    private void setCommonMessageAttributes(EMMessage message) {
        message.setAttribute(EaseConstant.EXTRA_CHAT_FROM_NICK_NAME, fromChatNickname);
        message.setAttribute(EaseConstant.EXTRA_CHAT_FROM_USER_AVATAR, fromChatUserAvatar);
    }


    public void resendMessage(EMMessage message) {
        message.setStatus(EMMessage.Status.CREATE);
        EMClient.getInstance().chatManager().sendMessage(message);
        messageList.refresh();
    }

    //===================================================================================

    /**
     * //     * 根据图库图片path发送图片
     * //     *
     * //     * @param paths
     * //
     */
    private void sendPicByPath(String[] paths) {
        for (String path : paths) {
            File file = new File(path);
            if (!file.exists()) {
                UIUtil.toastShort(mBaseActivity, "找不到图片");
                return;

            }
            sendImageMessage(file.getAbsolutePath());
        }
    }

    /**
     * 根据图库图片uri发送图片
     *
     * @param selectedImage
     */
    protected void sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = mBaseActivity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(mBaseActivity, R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            sendImageMessage(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(mBaseActivity, R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            sendImageMessage(file.getAbsolutePath());
        }

    }

    /**
     * 根据uri发送文件
     *
     * @param uri
     */
    protected void sendFileByUri(Uri uri) {
        String filePath = null;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;

            try {
                cursor = mBaseActivity.getContentResolver().query(uri, filePathColumn, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            Toast.makeText(mBaseActivity, R.string.File_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }
        //大于10M不让发送
        if (file.length() > 10 * 1024 * 1024) {
            Toast.makeText(mBaseActivity, R.string.The_file_is_not_greater_than_10_m, Toast.LENGTH_SHORT).show();
            return;
        }
        sendFileMessage(filePath);
    }

    /**
     * 照相获取图片
     */
    protected void selectPicFromCamera() {
        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(mBaseActivity, R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        cameraFile = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
                + System.currentTimeMillis() + ".jpg");
        cameraFile.getParentFile().mkdirs();
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                REQUEST_CODE_CAMERA);
    }

//    /**
//     * 从图库获取图片
//     */
//    protected void selectPicFromLocal() {
//        Intent intent;
//        if (Build.VERSION.SDK_INT < 19) {
//            intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//
//        } else {
//            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        }
//        startActivityForResult(intent, REQUEST_CODE_LOCAL);
//    }

    /**
     * //     * 从图库获取图片
     * //
     */
    public void selectPicFromLocal() {
        mPhotoPicker.setMaxCount(5);
        mPhotoPicker.pickPhoto();
    }


    /**
     * 点击清空聊天记录
     */
    protected void emptyHistory() {
        String msg = getResources().getString(R.string.Whether_to_empty_all_chats);
        UIUtil.showAlert(mBaseActivity, mBaseActivity.getResources().getString(R.string.resend), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清空会话
                EMClient.getInstance().chatManager().deleteConversation(toChatUsername, true);
                messageList.refresh();
            }
        });
    }

    /**
     * 点击进入群组详情
     */
    protected void toGroupDetails() {
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(mBaseActivity, R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
            if (chatFragmentListener != null) {
                chatFragmentListener.onEnterToChatDetails();
            }
        } else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            if (chatFragmentListener != null) {
                chatFragmentListener.onEnterToChatDetails();
            }
        }
    }

    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard() {
        if (mBaseActivity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (mBaseActivity.getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(mBaseActivity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 转发消息
     *
     * @param forward_msg_id
     */
    protected void forwardMessage(String forward_msg_id) {
        final EMMessage forward_msg = EMClient.getInstance().chatManager().getMessage(forward_msg_id);
        EMMessage.Type type = forward_msg.getType();
        switch (type) {
            case TXT:
                if (forward_msg.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)) {
                    sendBigExpressionMessage(((EMTextMessageBody) forward_msg.getBody()).getMessage(),
                            forward_msg.getStringAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, null));
                } else {
                    // 获取消息内容，发送消息
                    String content = ((EMTextMessageBody) forward_msg.getBody()).getMessage();
                    sendTextMessage(content);
                }
                break;
            case IMAGE:
                // 发送图片
                String filePath = ((EMImageMessageBody) forward_msg.getBody()).getLocalUrl();
                if (filePath != null) {
                    File file = new File(filePath);
                    if (!file.exists()) {
                        // 不存在大图发送缩略图
                        filePath = ((EMImageMessageBody) forward_msg.getBody()).thumbnailLocalPath();
                    }
                    sendImageMessage(filePath);
                }
                break;
            default:
                break;
        }

        if (forward_msg.getChatType() == EMMessage.ChatType.ChatRoom) {
            EMClient.getInstance().chatroomManager().leaveChatRoom(forward_msg.getTo());
        }
    }

    /**
     * 监测群组解散或者被T事件
     */
    class GroupListener extends EaseGroupListener {

        @Override
        public void onUserRemoved(final String groupId, String groupName) {
            getActivity().runOnUiThread(new Runnable() {

                public void run() {
                    if (toChatUsername.equals(groupId)) {
                        Toast.makeText(getActivity(), R.string.you_are_group, Toast.LENGTH_LONG).show();
                        Activity activity = getActivity();
                        if (activity != null && !activity.isFinishing()) {
                            activity.finish();
                        }
                    }
                }
            });
        }

        @Override
        public void onGroupDestroyed(final String groupId, String groupName) {
            // prompt group is dismissed and finish this activity
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (toChatUsername.equals(groupId)) {
                        Toast.makeText(getActivity(), R.string.the_current_group_destroyed, Toast.LENGTH_LONG).show();
                        Activity activity = getActivity();
                        if (activity != null && !activity.isFinishing()) {
                            activity.finish();
                        }
                    }
                }
            });
        }

        @Override
        public void onAnnouncementChanged(String groupId, String announcement) {

        }

        @Override
        public void onSharedFileAdded(String groupId, EMMucSharedFile sharedFile) {

        }

        @Override
        public void onSharedFileDeleted(String groupId, String fileId) {

        }

    }


    protected EaseChatFragmentListener chatFragmentListener;

    public void setChatFragmentListener(EaseChatFragmentListener chatFragmentListener) {
        this.chatFragmentListener = chatFragmentListener;
    }

    public interface EaseChatFragmentListener {
        /**
         * 设置消息扩展属性
         */
        void onSetMessageAttributes(EMMessage message);

        /**
         * 进入会话详情
         */
        void onEnterToChatDetails();

        /**
         * 用户头像点击事件
         *
         * @param username
         */
        void onAvatarClick(String username);

        /**
         * 消息气泡框点击事件
         */
        boolean onMessageBubbleClick(EMMessage message);

        /**
         * 消息气泡框长按事件
         */
        void onMessageBubbleLongClick(EMMessage message);

        /**
         * 扩展输入栏item点击事件,如果要覆盖EaseChatFragment已有的点击事件，return true
         *
         * @param view
         * @param itemId
         * @return
         */
        boolean onExtendMenuItemClick(int itemId, View view);

        /**
         * 设置自定义chatrow提供者
         *
         * @return
         */
        EaseCustomChatRowProvider onSetCustomChatRowProvider();

    }
}
