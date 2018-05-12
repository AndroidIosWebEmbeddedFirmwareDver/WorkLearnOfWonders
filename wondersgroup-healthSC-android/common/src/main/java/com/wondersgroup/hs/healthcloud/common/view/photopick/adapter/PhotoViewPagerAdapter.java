package com.wondersgroup.hs.healthcloud.common.view.photopick.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wondersgroup.hs.healthcloud.common.R;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.view.photoview.PhotoView;
import com.wondersgroup.hs.healthcloud.common.view.photoview.PhotoViewAttacher;
import com.wondersgroup.hs.healthcloud.common.view.viewpager.RecyclingPagerAdapter;

import java.util.List;

/**
 * 图集模式
 * PhotoViewPagerAdapter
 * chenbo
 * 2015年3月17日 下午2:25:45
 * @version 1.0
 */
public class PhotoViewPagerAdapter extends RecyclingPagerAdapter {
    private Context mContext;
    private List<String> mPhotos;
    private BitmapTools mBitmapTools;

    public PhotoViewPagerAdapter(Context context, List<String> photos) {
        mContext = context;
        mPhotos = photos;
        
        mBitmapTools = new BitmapTools(context);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container) {
        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_photo_view, null);
            holder = new Holder(convertView);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final PhotoView photoView = holder.photoView;
        final String url = mPhotos.get(position);
        photoView.init();

        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                ((Activity)mContext).finish();
            }
        });
        mBitmapTools.display(photoView, url);
        return convertView;
    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }
    
    static class Holder {
        public PhotoView photoView;

        public Holder(View v) {
            v.setTag(this);
            photoView = (PhotoView) v.findViewById(R.id.photoview);
        }
        
    }

}
