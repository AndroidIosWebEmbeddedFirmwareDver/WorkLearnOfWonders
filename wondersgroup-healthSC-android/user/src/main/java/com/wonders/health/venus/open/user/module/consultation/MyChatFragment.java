package com.wonders.health.venus.open.user.module.consultation;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;


import com.hyphenate.EMCallBack;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.huanxin.EaseConstant;
import com.wondersgroup.hs.healthcloud.common.huanxin.domain.EaseUser;
import com.wondersgroup.hs.healthcloud.common.huanxin.ui.BaseChatFragment;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import java.util.Map;



/**
 * 类描述：咨询
 * 创建人：sunzhenyu
 * 创建时间：2016/3/31 16:43
 */
public class MyChatFragment extends BaseChatFragment {
    private UserManager userManager = UserManager.getInstance();
    private Map<String, EaseUser> users = ChatHelper.getInstance().getChatAvatorList();
    private View sendFlowerLayout;
//    private String toChatDoctorUid;
//    private String toChatDoctorName;
//    private String toChatDoctorAvatar;
//    private boolean isDoctorReplyToday;

    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
//        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();
        getIntentData();
//        mTitleBar.addAction(new TitleBar.ImageAction(R.mipmap.ic_chat_doctor) {//跳转到医生详情
//            @Override
//            public void performAction(View view) {
//                mBaseActivity.startActivity(new Intent(mBaseActivity, DoctorDetailActivity.class)
//                        .putExtra(ChatConstant.EXTRA_DOCTOR_UID, toChatDoctorUid).putExtra(DoctorDetailActivity.EXTRA_ISSHOWCONSULT,false),true);
//            }
//        });
    }

    private void getIntentData() {
//        toChatDoctorUid = fragmentArgs.getString(ChatConstant.EXTRA_DOCTOR_UID,"");
//        toChatDoctorName= fragmentArgs.getString(EaseConstant.EXTRA_CHAT_TO_USER_NAME,"");
//        toChatDoctorAvatar = fragmentArgs.getString(ChatConstant.EXTRA_DOCTOR_AVATAR,"");
    }

    @Override
    protected void registerExtendMenuItem() {
        itemStrings = new int[]{R.string.attach_picture};
//        itemdrawables = new int[]{R.drawable.chat_image_selector};
        itemIds = new int[]{BaseChatFragment.ITEM_PICTURE};
        for (int i = 0; i < itemStrings.length; i++) {
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], extendMenuItemClickListener);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mTitleBar.setTitle(toChatUsername);

        if (!userManager.isEMLogin()) {
            UIUtil.showLoadingView(mContent, "登录中...");
            ChatManager.getInstance().login(userManager.getUser().talkId,
                    userManager.getUser().talkPwd,
                    new EMCallBack() {//回调
                        @Override
                        public void onSuccess() {
                            mBaseActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    UIUtil.hideAllNoticeView(mContent);
                                    loadData();
                                }
                            });
                        }

                        @Override
                        public void onProgress(int progress, String status) {
                        }

                        @Override
                        public void onError(int code, String message) {
                            mBaseActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    UIUtil.showErrorView(mContent, "登录失败，点击重新登录", new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            initData(null);
                                        }
                                    });
                                }
                            });

                        }
                    });
        } else {
            loadData();
        }
    }

    private void loadData() {
        setChatAvatar();

        if (TextUtils.isEmpty(toChatUsername) || TextUtils.isEmpty(toChatUserAvatar)) {
            loadToChatUserInfo(toChatUsername);
        } else {
            users.get(toChatUsername).setAvatar(toChatUserAvatar);
            messageList.refresh();
        }

        isShowSendFlower();
    }

    /**
     * 设置聊天头像
     */
    private void setChatAvatar() {
        EaseUser user = new EaseUser(UserManager.getInstance().getUser().talkId);
        user.setAvatar(UserManager.getInstance().getUser().avatar);
        users.put(UserManager.getInstance().getUser().talkId, user);
        EaseUser toChatUser = new EaseUser(toChatUsername);
        toChatUser.setAvatar(toChatUserAvatar);
        users.put(toChatUsername, toChatUser);
        messageList.refresh();
    }

    /**
     * 获取聊天医生的信息
     *
     * @param toChatUsername
     */
    private void loadToChatUserInfo(final String toChatUsername) {
//        ChatManager.getInstance().getDoctorInfo(toChatUsername, new ResponseCallback<HuanxinDoctorListResponse>() {
//            @Override
//            public void onSuccess(HuanxinDoctorListResponse t) {
//                super.onSuccess(t);
//                if (t != null) {
//                    users.get(toChatUsername).setAvatar(t.getList().get(0).avatar);
//                    messageList.refresh();
//                    toChatDoctorUid = t.getList().get(0).id;
//                }
//            }
//
//            @Override
//            public boolean isShowNotice() {
//                return false;
//            }
//        });
    }

    public void isShowSendFlower() {
//        List<EMMessage> messages=conversation.getAllMessages();
//        for (EMMessage message : messages) {
//            //医生当天有回复
//            if (message.direct() == EMMessage.Direct.RECEIVE) {
//                TimeInfo todayTime= DateUtils.getTodayStartAndEndTime();
//                if (message.getMsgTime() > todayTime.getStartTime() && message.getMsgTime() < todayTime.getEndTime()) {
//                    isDoctorReplyToday=true;
//                    break;
//                }
//            }
//        }
//        canSendFlower();

    }

    private void canSendFlower() {
//        ChatManager.getInstance().canSendFlower(UserManager.getInstance().getUser().uid, toChatDoctorUid, new ResponseCallback<FlowerModel>() {
//            @Override
//            public void onSuccess(FlowerModel obj) {
//                super.onSuccess(obj);
//                if (obj != null) {
//                    try {
//                        boolean canSendFlower = Boolean.parseBoolean(obj.flag);
//                        if (canSendFlower && isDoctorReplyToday) {
//                            addSendFlowerLayout();
//                        }
//                    } catch (Exception e) {
//                    }
//                }
//            }
//
//            @Override
//            public boolean isShowNotice() {
//                return false;
//            }
//        });
    }

    private void addSendFlowerLayout() {
//        if (sendFlowerLayout == null) {
//            sendFlowerLayout = View.inflate(mBaseActivity, R.layout.layout_chat_send_flower, null);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, UIUtil.dip2px(40));
//            inputMenu.getPrimaryMenuContainer().addView(sendFlowerLayout, 0);
//            sendFlowerLayout.setLayoutParams(params);
//            sendFlowerLayout.setOnClickListener(new View.OnClickListener() {//跳转到送鲜花
//                @Override
//                public void onClick(View v) {
//                    mBaseActivity.startActivity(new Intent(mBaseActivity, SendFlowerNewActivity.class)
//                            .putExtra(SendFlowerNewActivity.KEY_DOCTOR_ID,toChatDoctorUid));
//                }
//            });
//        }
    }

//    public void onEvent(SendFlowerSuccessEvent event) {//送鲜花成功
//        inputMenu.getPrimaryMenuContainer().removeView(sendFlowerLayout);
//    }
//
//    public void onEvent(EasemobUnreadMsgChangeEvent event) {
//        if (!isDoctorReplyToday) {//医生当天回复，显示送鲜花
//            if (event.message != null) {
//                TimeInfo todayTime = DateUtils.getTodayStartAndEndTime();
//                if (event.message.getMsgTime() > todayTime.getStartTime() && event.message.getMsgTime() < todayTime.getEndTime()) {
//                    isDoctorReplyToday = true;
//                    canSendFlower();
//                }
//            }
//        }
//    }

}
