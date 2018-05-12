package com.wonders.health.venus.open.user.view;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wonders.health.venus.open.user.R;

/**
 * Created by win10 on 2018/4/13.
 */

public class MyPopuWindow extends PopupWindow {
    private Context mContext;
    private String [] mStrings;
    private ListView listView;
    private MyAdapter myAdapter;

    private MyClickListener mYitemClickListener;
    private int mWith;
    private int mHight;
    private LinearLayout parentLinear;
    private int backgroundResourceId = 0;
    private String  textColor = "#060606";
    private int textSize  =16;

    public MyPopuWindow(Context context, String [] strings, int w, int h,int groundResourceId) {
        this.mContext = context;
        this.mStrings = strings;
        this.mWith = w;
        this.mHight = h;
        this.backgroundResourceId = groundResourceId;
        if(backgroundResourceId==0){
            backgroundResourceId = R.drawable.bg_listview_popuwindow_white;
        }

        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(h);

        initView();

        this.setBackgroundDrawable(mContext.getResources().getDrawable(backgroundResourceId));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setContentView(parentLinear);
        this.update();
        this.setAnimationStyle(R.style.popwin_anim_style);

    }
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            this.dismiss();
        }
    }

    private void initView() {
        listView = new ListView(mContext);
//        listView.setBackgroundResource(R.drawable.bg_layout_frame_blue);
        listView.setSelector(R.drawable.listview_item_selector);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setVerticalScrollBarEnabled(false);

        listView.setDivider(new ColorDrawable(Color.parseColor("#ffffff")));

        listView.setDividerHeight(2);

        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mYitemClickListener.ItemClickListener(position,mStrings[position]);
            }
        });
        parentLinear = new LinearLayout(mContext);
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 2);

//        TextView tv_top = new TextView(mContext);
//        tv_top.setBackgroundColor(Color.parseColor("#ffffff"));
//        tv_top.setLayoutParams(rlp);
//        parentLinear.addView(tv_top);//顶部线条
        parentLinear.setOrientation(LinearLayout.VERTICAL);
        parentLinear.addView(listView);//中间

        TextView tv_bottom = new TextView(mContext);
        tv_bottom.setBackgroundColor(Color.parseColor("#ffffff"));
        tv_bottom.setLayoutParams(rlp);
        parentLinear.addView(tv_bottom);//底部线条
    }

    private  class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mStrings.length;
        }

        @Override
        public Object getItem(int position) {
            return mStrings[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
//			if (convertView == null) {
            view = createSingleView(mStrings[position]);
//			} else {
//				view = convertView;
//			}
            return view;
        }

        private LinearLayout createSingleView(String name) {
            LinearLayout ll = new LinearLayout(mContext);
            ll.setBackgroundColor(Color.TRANSPARENT);
            ll.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            ll.setOrientation(LinearLayout.HORIZONTAL);
            TextView tv = new TextView(mContext);
            tv.setText(name);
            tv.setFocusable(false);
            tv.setTextSize(textSize);
            tv.setPadding(0, 20, 0, 20);
            tv.setTextColor(Color.parseColor(textColor));
            ll.addView(tv, rlp);
            return ll;
        }

    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public interface MyClickListener {
        void ItemClickListener(int index, String str);
    }

    public void setOnMyItemClickListener(MyClickListener listener) {
        mYitemClickListener = listener;
    }
}
