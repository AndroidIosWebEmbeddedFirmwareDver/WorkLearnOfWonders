package com.wonders.health.venus.open.doctor.module.consultation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.huanxin.EaseConstant;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wang on 2017/6/5.
 */

public class ChatListActivity extends BaseActivity {

    @BindView(R.id.pull_view)
    PullToRefreshView pullToRefreshView;

    @BindView(R.id.recycler_view)
    BaseRecyclerView recyclerView;

    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;

    private ChatListAdapter adapter;

    private List<EMConversation> conversationList = new ArrayList<EMConversation>();


    @Override
    protected void initViews() {
        setContentView(R.layout.activity_chat_list);
        mTitleBar.setTitle("图文咨询");
        pullToRefreshView.setRefreshEnable(true);
        pullToRefreshView.setLoadMoreEnable(false);
        pullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                loadData();

            }
        });
        itemClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showEmpty();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        UserManager userManager = UserManager.getInstance();
        ChatManager.getInstance().login(userManager.getUser().talkid,
                userManager.getUser().talkpwd,
                new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        ChatListActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showEmpty();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                    }

                    @Override
                    public void onError(int code, String message) {
                        ChatListActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                UIUtil.showErrorView(pullToRefreshView, "登录失败，点击重新登录", new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        initData(null);
                                    }
                                });
                            }
                        });

                    }
                });
    }

    private void itemClick() {
        recyclerView.setOnItemClickListener(new BaseRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent intent = new Intent(ChatListActivity.this, MyChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TO_USER_ID, conversationList.get(position).getLastMessage().getUserName());
                intent.putExtra(EaseConstant.EXTRA_CHAT_TO_USER_NAME, conversationList.get(position).getLastMessage().getUserName());
                intent.putExtra(EaseConstant.EXTRA_CHAT_FROM_USER_ID, conversationList.get(position).getLastMessage().getFrom());
                intent.putExtra(EaseConstant.EXTRA_CHAT_FROM_NICK_NAME, UserManager.getInstance().getUser().name);
                intent.putExtra(EaseConstant.EXTRA_CHAT_FROM_USER_AVATAR, "http://og3xulzx6.bkt.clouddn.com/1479375178427.jpg");
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        if (conversationList.size() > 0) {
            conversationList.clear();
        }
        conversationList.addAll(loadConversationList());
        setAdapter();
        pullToRefreshView.onHeaderRefreshComplete();
    }

    private void showEmpty() {
        if (loadConversationList().size() == 0) {
            rlEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            UIUtil.showEmptyView(rlEmpty, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    loadData();
                }
            });
        } else {
            rlEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            loadData();
        }

    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new ChatListAdapter(this, conversationList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }


    /**
     * load conversation list
     *
     * @return +
     */
    protected List<EMConversation> loadConversationList() {
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }

        return list;
    }

    /**
     * sort conversations according time stamp of last message
     *
     * @param conversationList
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

}
