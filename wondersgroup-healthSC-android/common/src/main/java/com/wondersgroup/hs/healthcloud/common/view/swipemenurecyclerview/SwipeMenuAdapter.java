/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wondersgroup.hs.healthcloud.common.view.swipemenurecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wondersgroup.hs.healthcloud.common.R;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;

/**
 * Created by Yan Zhenjie on 2016/7/27.
 */
public class SwipeMenuAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    /**
     * Swipe menu creator。
     */
    private SwipeMenuCreator mSwipeMenuCreator;

    /**
     * Swipe menu click listener。
     */
    private OnSwipeMenuItemClickListener mSwipeMenuItemClickListener;
    private int itemSelectorId;
    private BaseRecyclerView.OnItemClickListener onItemClickListener;


    public SwipeMenuAdapter(RecyclerView parent, RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        this.recyclerView = parent;

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            public void onItemRangeInserted(int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            public void onItemRangeRemoved(int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }

            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                notifyItemRangeRemoved(fromPosition, toPosition - fromPosition);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return adapter.getItemViewType(position);
    }

    /**
     * Set to create menu listener.
     *
     * @param swipeMenuCreator listener.
     */
    void setSwipeMenuCreator(SwipeMenuCreator swipeMenuCreator) {
        this.mSwipeMenuCreator = swipeMenuCreator;
    }

    /**
     * Set to click menu listener.
     *
     * @param swipeMenuItemClickListener listener.
     */
    void setSwipeMenuItemClickListener(OnSwipeMenuItemClickListener swipeMenuItemClickListener) {
        this.mSwipeMenuItemClickListener = swipeMenuItemClickListener;
    }

    public void setItemSelectorId(int itemSelectorId) {
        this.itemSelectorId = itemSelectorId;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = adapter.onCreateViewHolder(parent, viewType);
        if (mSwipeMenuCreator != null) {
            SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_menu_item_default, parent, false);
            SwipeHolder swipeHolder = new SwipeHolder(swipeMenuLayout, holder);

            SwipeMenu swipeLeftMenu = new SwipeMenu(swipeMenuLayout, viewType);
            SwipeMenu swipeRightMenu = new SwipeMenu(swipeMenuLayout, viewType);

            mSwipeMenuCreator.onCreateMenu(swipeLeftMenu, swipeRightMenu, viewType);

            int leftMenuCount = swipeLeftMenu.getMenuItems().size();
            if (leftMenuCount > 0) {
                SwipeMenuView swipeLeftMenuView = (SwipeMenuView) swipeMenuLayout.findViewById(R.id.swipe_left);
                swipeLeftMenuView.bindMenu(swipeLeftMenu, SwipeMenuRecyclerView.LEFT_DIRECTION);
                swipeLeftMenuView.bindMenuItemClickListener(mSwipeMenuItemClickListener, swipeMenuLayout);
            }

            int rightMenuCount = swipeRightMenu.getMenuItems().size();
            if (rightMenuCount > 0) {
                SwipeMenuView swipeRightMenuView = (SwipeMenuView) swipeMenuLayout.findViewById(R.id.swipe_right);
                swipeRightMenuView.bindMenu(swipeRightMenu, SwipeMenuRecyclerView.RIGHT_DIRECTION);
                swipeRightMenuView.bindMenuItemClickListener(mSwipeMenuItemClickListener, swipeMenuLayout);
            }

            if (leftMenuCount > 0 || rightMenuCount > 0) {
                View contentView = holder.itemView;
                ViewGroup viewGroup = (ViewGroup) swipeMenuLayout.findViewById(R.id.swipe_content);
                viewGroup.addView(contentView);
            }
            swipeHolder.childHolder = holder;
            return swipeHolder;
        }
        return holder;
    }

    static class SwipeHolder extends RecyclerView.ViewHolder {
        public SwipeMenuLayout swipeMenuLayout;
        public ViewGroup contentLayout;
        public RecyclerView.ViewHolder childHolder;

        public SwipeHolder(View itemView, RecyclerView.ViewHolder childHolder) {
            super(itemView);
            swipeMenuLayout = (SwipeMenuLayout) itemView;
            contentLayout = (ViewGroup) itemView.findViewById(R.id.swipe_content);
            this.childHolder = childHolder;
        }
    }


    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        View itemView = holder.itemView;
        if (itemView instanceof SwipeMenuLayout) {
            SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) itemView;
            int childCount = swipeMenuLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = swipeMenuLayout.getChildAt(i);
                if (childView instanceof SwipeMenuView) {
                    ((SwipeMenuView) childView).bindAdapterViewHolder(holder);
                }
            }
        }
        if (holder instanceof SwipeHolder) {
            adapter.onBindViewHolder(((SwipeHolder) holder).childHolder, position);
        } else {
            adapter.onBindViewHolder(holder, position);
        }
        if (itemSelectorId != 0) {
             holder.itemView.setBackgroundResource(itemSelectorId);
        }
        //  注册点击事件
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(recyclerView, view, position, getItemId(position));
                }
            });
        }
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        adapter.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return adapter.getItemId(position);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
            adapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
            adapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        return adapter.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        adapter.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount();
    }

    public void setOnItemClickListener(BaseRecyclerView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}