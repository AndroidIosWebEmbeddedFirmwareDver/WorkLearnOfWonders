package com.wonders.health.venus.open.doctor.module.consultation;

import android.os.Bundle;
import android.view.View;

import com.hyphenate.EMCallBack;
import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.entity.event.EasemobUnreadMsgChangeEvent;
import com.wondersgroup.hs.healthcloud.common.huanxin.domain.EaseUser;
import com.wondersgroup.hs.healthcloud.common.huanxin.ui.BaseChatFragment;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import java.util.Map;

import de.greenrobot.event.EventBus;


/**
 * 类描述：咨询
 * 创建人：sunzhenyu
 * 创建时间：2016/3/31 16:43
 */
public class MyChatFragment extends BaseChatFragment {
    private UserManager userManager = UserManager.getInstance();
    private Map<String, EaseUser> users = ChatHelper.getInstance().getChatAvatorList();
    private String toChatPatientUid;
    private String toChatPatientAvatar;

    @Override
    protected void initViews() {
        super.initViews();
        getIntentData();
    }

    private void getIntentData() {
        toChatPatientUid = fragmentArgs.getString(ChatConstant.EXTRA_PATIENT_UID, "");
        toChatPatientAvatar = fragmentArgs.getString(ChatConstant.EXTRA_PATIENT_AVATAR, "");
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
        mTitleBar.setTitle("咨询");
        if (!userManager.isEMLogin()) {
            UIUtil.showLoadingView(mContent, "登录中...");
            ChatManager.getInstance().login(userManager.getUser().talkid,
                    userManager.getUser().talkpwd,
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

        loadToChatUserInfo(toChatUsername);
    }

    /**
     * 设置聊天头像
     */
    private void setChatAvatar() {
        EaseUser user = new EaseUser(UserManager.getInstance().getUser().talkid);
        user.setAvatar(UserManager.getInstance().getUser().avatar);
        users.put(UserManager.getInstance().getUser().talkid, user);
        EaseUser toChatUser = new EaseUser(toChatUsername);
        toChatUser.setAvatar(toChatPatientAvatar);
        users.put(toChatUsername, toChatUser);
        messageList.refresh();

    }



    /**
     * 获取聊天患者的信息
     *
     * @param toChatUsername
     */
    private void loadToChatUserInfo(final String toChatUsername) {
//        ChatManager.getInstance().getPatientInfo(toChatUsername, new ResponseCallback<HuanxinPatientInfoListResponse>() {
//            @Override
//            public void onSuccess(HuanxinPatientInfoListResponse t) {
//                super.onSuccess(t);
//                if (t != null) {
//                    users.get(toChatUsername).setAvatar(t.getList().get(0).avatar);
//                    messageList.refresh();
//                    toChatPatientUid = t.getList().get(0).uid;
//                    mChildren = t.getList().get(0).children;
//                    if (mChildren != null && mChildren.size() > 0) {
//                        mTitleBar.removeAllActions();
//                        mTitleBar.addAction(new TitleBar.ImageAction(R.mipmap.ic_consult_user) {//跳转到患者详情
//                            @Override
//                            public void performAction(View view) {
//                                Intent intent = new Intent(mBaseActivity, EyeHealthActivity.class);
//                                intent.putExtra(EyeHealthActivity.BIND_CHILD_LIST, mChildren);
//                                mBaseActivity.startActivity(intent);
//                            }
//                        });
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

    @Override
    public void onDestroy() {
        EventBus.getDefault().post(new EasemobUnreadMsgChangeEvent());
        super.onDestroy();
    }
}
