package com.wonders.health.venus.open.user.module.home.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ContactStatus;
import com.wonders.health.venus.open.user.entity.ContactVO;
import com.wonders.health.venus.open.user.logic.RegistrationManager;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.view.TitleBar;
import com.wondersgroup.hs.healthcloud.common.view.swipemenurecyclerview.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunning on 16/1/4.
 * 就诊人列表
 */
public class ContactListActivity extends BaseActivity {

    public static final String CONTACT_STATUS_FLAG = "contact_status_flag";
    public static final String CONTACT_ID = "contact_id";

    private SwipeMenuRecyclerView listView;

    private List<ContactVO> mData;

    private AdapterForContact adapter;

    private TitleBar.TextAction titleAction;

    @Override
    protected void initViews() {
        setContentView(R.layout.contact_list_layout);
        mTitleBar.setTitle("就诊人");
        listView = (SwipeMenuRecyclerView) findViewById(R.id.contact_list_view);
        initSwipeListView();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(ContactListActivity.this, RegistrationContactActivity.class);
//                intent.putExtra(CONTACT_STATUS_FLAG, ContactStatus.RETRIEVE);
//                intent.putExtra(CONTACT_ID, mData.get(position).getId());
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mData == null || mData.isEmpty()) {
            loadData(Constant.TYPE_INIT);
        } else {
            loadData(Constant.TYPE_RELOAD);
        }
    }

    private void initSwipeListView() {
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
//                openItem.setBackground(new ColorDrawable(Color.RED));
//                openItem.setWidth(UIUtil.dip2px(90));
//                openItem.setTitle("删除");
//                openItem.setTitleSize(16);
//                openItem.setTitleColor(Color.WHITE);
//                menu.addMenuItem(openItem);
//            }
//        };
//        listView.setMenuCreator(creator);
//        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
//                UIUtil.showConfirm(ContactListActivity.this, "提示", "是否删除当前联系人", "删除", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ContactVO vo = mData.get(position);
//                        if (vo.getIsDefault() == 1) {
//                            UIUtil.toastShort(ContactListActivity.this, "默认联系人不能删除");
//                        } else {
//                            RegistrationManager.getInstance().deleteContact(vo.getId(), new ResponseCallback() {
//                                @Override
//                                public void onSuccess(ResponseInfo responseInfo) {
//                                    super.onSuccess(responseInfo);
//                                    loadData(Constant.TYPE_INIT);
//                                }
//                            });
//                        }
//                    }
//                });
//                return false;
//            }
//        });
    }

    private void loadData(int type) {
        if (mData == null) {
            mData = new ArrayList();
        }
        /*RegistrationManager.getInstance().queryContactList(new FinalResponseCallback<RegistrationManager.RegistrationContactResponse>(this, type) {

            @Override
            public void onSuccess(RegistrationManager.RegistrationContactResponse response) {
                super.onSuccess(response);
                if (response != null)
                    if (response.isListEmpty()) {
                        setIsEmpty(response.isListEmpty());
                        initContactTitleBar(response);
                    } else {
                        bindView(response);
                    }
            }

            @Override
            public void onReload() {
                super.onReload();
                loadData(Constant.TYPE_INIT);
            }
        })*/;
    }

    private void initContactTitleBar(RegistrationManager.RegistrationContactResponse mResponse) {
        if ((mResponse.getList().size() < 5 && titleAction == null) || (mResponse.isListEmpty() && titleAction == null)) {
            titleAction = new TitleBar.TextAction("添加") {
                @Override
                public void performAction(View view) {
                    Intent intent = new Intent(ContactListActivity.this, RegistrationContactActivity.class);
                    intent.putExtra(CONTACT_STATUS_FLAG, ContactStatus.CREATE);
                    startActivity(intent);
                }
            };
            mTitleBar.addAction(titleAction);
        } else if (mResponse.getList().size() == 5 && titleAction != null) {
            mTitleBar.removeAction(titleAction);
        }
    }

    private void bindView(RegistrationManager.RegistrationContactResponse mResponse) {
        initContactTitleBar(mResponse);
//        if (adapter == null) {
//            adapter = new AdapterForContact();
//            mData.addAll(mResponse.getList());
//            listView.setAdapter(adapter);
//        } else {
//            mData.clear();
//            mData.addAll(mResponse.getList());
//            adapter.notifyDataSetChanged();
//        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }


    private class AdapterForContact extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ContactVO vo = (ContactVO) getItem(position);
            ContactViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ContactListActivity.this).inflate(R.layout.contact_list_item, null);
                viewHolder = new ContactViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ContactViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(vo.getName());
            viewHolder.age.setText(String.valueOf(vo.getAge()) + " 岁");
            if (vo.getIsDefault() == 1) {
                viewHolder.isDefault.setVisibility(View.VISIBLE);
            } else {
                viewHolder.isDefault.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
    }

    private class ContactViewHolder {

        TextView name, age, isDefault;

        public ContactViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.contact_list_name);
            age = (TextView) view.findViewById(R.id.contact_list_age);
            isDefault = (TextView) view.findViewById(R.id.contact_list_default);
        }
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }
}
