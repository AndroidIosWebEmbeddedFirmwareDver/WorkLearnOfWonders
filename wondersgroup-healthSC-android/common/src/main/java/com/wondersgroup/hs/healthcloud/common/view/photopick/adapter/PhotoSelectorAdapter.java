package com.wondersgroup.hs.healthcloud.common.view.photopick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wondersgroup.hs.healthcloud.common.BaseAdapter;
import com.wondersgroup.hs.healthcloud.common.R;
import com.wondersgroup.hs.healthcloud.common.entity.PhotoModel;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import java.util.ArrayList;

/**
 * 图片选择列表
 * PhotoSelectorAdapter
 * chenbo
 * 2015年3月16日 下午4:33:01
 * @version 1.0
 */
public class PhotoSelectorAdapter extends BaseAdapter<PhotoModel, RecyclerView.ViewHolder> {

	private int itemWidth;
	private int horizentalNum = 3;
	private onPhotoItemCheckedListener listener;
	private onItemClickListener mCallback;
	
	private BitmapTools mBitmapTools;

	private PhotoSelectorAdapter(Context context, ArrayList<PhotoModel> models) {
		super(context, models);
        mBitmapTools = new BitmapTools(context);
	}

	public PhotoSelectorAdapter(Context context, ArrayList<PhotoModel> models, onPhotoItemCheckedListener listener, onItemClickListener mCallback) {
		this(context, models);
		setItemWidth();
		this.listener = listener;
		this.mCallback = mCallback;
	}

	public static interface onPhotoItemCheckedListener {
        public void onCheckedChanged(PhotoModel photoModel,
                                     CompoundButton buttonView, boolean isChecked);
    }

    public interface onItemClickListener {
        public void onItemClick(int position);
    }

	public void setItemWidth() {
	    int screenWidth = SystemUtil.getScreenWidth();
		int horizentalSpace = mContext.getResources().getDimensionPixelSize(R.dimen.S);
		this.itemWidth = (screenWidth - (horizentalSpace * (horizentalNum - 1))) / horizentalNum;
	}

	/*@Override
    public int getAdapterItemCount() {
        return 2;
    }*/

	@Override
	public int getItemViewType(int position) {
	    return getItem(position).getType();
	}
	
	@Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
	    switch (viewtype) {
        case PhotoModel.TYPE_CAMERA:
            View view = mInflater.inflate(R.layout.view_camera, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(itemWidth, itemWidth));
            return new CameraHolder(view);
        case PhotoModel.TYPE_PHOTO:
        default:
            View item = mInflater.inflate(R.layout.layout_photoitem, null);
            item.setLayoutParams(new RecyclerView.LayoutParams(itemWidth, itemWidth));
            return new PhotoHolder(item);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final PhotoModel model = getItem(position);
        switch (model.getType()) {
        case PhotoModel.TYPE_CAMERA:
            CameraHolder cameraHolder = (CameraHolder) holder;
            cameraHolder.cameraText.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onItemClick(position);
                    }
                }
            });
            break;
        case PhotoModel.TYPE_PHOTO:
            PhotoHolder photoHolder = (PhotoHolder) holder;
            final ImageView ivPhoto = photoHolder.ivPhoto;
            final CheckBox cbPhoto = photoHolder.cbPhoto;
            final View cover = photoHolder.ivCover;
            mBitmapTools.display(ivPhoto, model.getOriginalPath(), BitmapTools.SizeType.SMALL);
            ivPhoto.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onItemClick(position);
                    }
                }
            });
            cbPhoto.setChecked(model.isChecked());
            if (cbPhoto.isChecked()) {
                cover.setVisibility(View.VISIBLE);
            } else {
                cover.setVisibility(View.INVISIBLE);
            }
            cbPhoto.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    model.setChecked(cbPhoto.isChecked());
                    if (cbPhoto.isChecked()) {
                        cover.setVisibility(View.VISIBLE);
                    } else {
                        cover.setVisibility(View.INVISIBLE);
                    }
                    if (listener != null) {
                        listener.onCheckedChanged(model, cbPhoto, cbPhoto.isChecked());
                    }
                }
            });

            break;
        default:
            break;
            
        }
    }
	
	public static class PhotoHolder extends RecyclerView.ViewHolder {
	    private ImageView ivPhoto;
	    private CheckBox cbPhoto;
        private View ivCover;
	    
        public PhotoHolder(View v) {
            super(v);
            ivPhoto = (ImageView) v.findViewById(R.id.iv_photo_lpsi);
            UIUtil.setTouchEffect(ivPhoto);
            cbPhoto = (CheckBox) v.findViewById(R.id.cb_photo_lpsi);
            ivCover = v.findViewById(R.id.iv_cover);
        }
	}
	
	public static class CameraHolder extends RecyclerView.ViewHolder {
	    TextView cameraText;
	    
        public CameraHolder(View v) {
            super(v);
            cameraText = (TextView) v.findViewById(R.id.tv_camera_vc);
        }
	    
	}
}
