package com.wondersgroup.hs.healthcloud.common.view.photopick;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wondersgroup.hs.healthcloud.common.CommonActivity;
import com.wondersgroup.hs.healthcloud.common.R;
import com.wondersgroup.hs.healthcloud.common.dao.PhotoDao;
import com.wondersgroup.hs.healthcloud.common.entity.AlbumModel;
import com.wondersgroup.hs.healthcloud.common.entity.PhotoModel;
import com.wondersgroup.hs.healthcloud.common.logic.PhotoManager;
import com.wondersgroup.hs.healthcloud.common.util.AnimationUtil;
import com.wondersgroup.hs.healthcloud.common.util.BaseConstant;
import com.wondersgroup.hs.healthcloud.common.util.PermissionType;
import com.wondersgroup.hs.healthcloud.common.util.RequestPermissionAuthorize;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.BaseRecyclerView;
import com.wondersgroup.hs.healthcloud.common.view.TitleBar;
import com.wondersgroup.hs.healthcloud.common.view.photopick.adapter.AlbumAdapter;
import com.wondersgroup.hs.healthcloud.common.view.photopick.adapter.PhotoSelectorAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PhotoPickActivity extends CommonActivity implements PhotoSelectorAdapter.onItemClickListener, PhotoSelectorAdapter.onPhotoItemCheckedListener,
        BaseRecyclerView.OnItemClickListener, View.OnClickListener {

    public static final String EXTRA_PARAM = "param";

    private BaseRecyclerView mGvPhotos;
    private BaseRecyclerView lvAblum;
    private TextView tvAlbum, tvPreview;
    private TextView mRightText;
    private PhotoManager mPhotoManager;
    private PhotoSelectorAdapter mPhotoAdapter;
    private AlbumAdapter mAlbumAdapter;
    private RelativeLayout layoutAlbum;
    private ArrayList<PhotoModel> mSelectPhotos;
    private File mCurrentPhotoFile;

    private PhotoPicker.PhotoParam mParam;

    @Override
    protected void initViews() {
        setContentView(R.layout.fragment_photo_pick);
        mGvPhotos = (BaseRecyclerView) findViewById(R.id.gv_photos_ar);
        lvAblum = (BaseRecyclerView) findViewById(R.id.lv_ablum_ar);
        tvAlbum = (TextView) findViewById(R.id.tv_album_ar);
        tvPreview = (TextView) findViewById(R.id.tv_preview_ar);
        layoutAlbum = (RelativeLayout) findViewById(R.id.layout_album_ar);

        mGvPhotos.setHasFixedSize(true);
        lvAblum.setHasFixedSize(true);
        mTitleBar.setTitle("图片选择");
        mTitleBar.setDividerColor(getResources().getColor(R.color.divider_color));

        layoutAlbum.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    hideAlbum();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (getIntent().getExtras() != null) {
            mParam = (PhotoPicker.PhotoParam) getIntent().getSerializableExtra(EXTRA_PARAM);
        }

        if (mParam == null) {
            finish();
            return;
        }

        mPhotoManager = new PhotoManager(this);

        mSelectPhotos = new ArrayList<PhotoModel>();

        tvAlbum.setOnClickListener(this);
        tvPreview.setOnClickListener(this);

        mPhotoAdapter = new PhotoSelectorAdapter(this, new ArrayList<PhotoModel>(), this, this);
        mGvPhotos.setAdapter(mPhotoAdapter);

        mAlbumAdapter = new AlbumAdapter(this, new ArrayList<AlbumModel>());
        lvAblum.setAdapter(mAlbumAdapter);
        lvAblum.setOnItemClickListener(this);

        mPhotoManager.getRecent(reccentListener); // 更新最近照片
        mPhotoManager.updateAlbum(albumListener); // 跟新相册信息

        mRightText = (TextView) mTitleBar.addAction(new TitleBar.TextAction("确定(0/" + mParam.mMaxCount +")") {

            @Override
            public void performAction(View view) {
                ok(); // 选完照片
            }

        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_album_ar) {
            album();

        } else if (i == R.id.tv_preview_ar) {
            preview();

        } else {
        }
    }

    /** 拍照 */
    private void catchPicture() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss", Locale.getDefault());
        String fileName = dateFormat.format(date) + ".jpg";
        BaseConstant.PHOTO_DIR.mkdirs();
        mCurrentPhotoFile = new File(BaseConstant.PHOTO_DIR, fileName);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
        startActivityForResult(intent, BaseConstant.REQUEST_CODE_TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == BaseConstant.REQUEST_CODE_TAKE_PHOTO) {
                mSelectPhotos.clear();
                if (mCurrentPhotoFile == null) return;
                PhotoModel photoModel = new PhotoModel(mCurrentPhotoFile.getAbsolutePath());
                new PhotoDao(this).addPhoto(mCurrentPhotoFile.getAbsolutePath());
                // selected.clear();
                // //--keep all
                // selected photos
                // tvNumber.setText("(0)");
                // //--keep all
                // selected photos
                // ///////////////////////////////////////////////////////////////////////////////////////////
                if (mSelectPhotos.size() >= mParam.mMaxCount) {
                    UIUtil.toastShort(this, String.format(getString(R.string.max_img_limit_reached), mParam.mMaxCount));
                    photoModel.setChecked(false);
                    mPhotoAdapter.notifyDataSetChanged();
                } else {
                    if (!mSelectPhotos.contains(photoModel)) {
                        mSelectPhotos.add(photoModel);
                    }
                }
                ok();
            } else if (requestCode == BaseConstant.REQUEST_CODE_CROP) {
                mParam.mNeedCrop = false;
                ok();
            }
        }
    }

    /** 完成 */
    private void ok() {
        if (mSelectPhotos.isEmpty()) {
            setResult(RESULT_CANCELED);
        } else {
            if (mParam.mNeedCrop) {
                PhotoModel model = mSelectPhotos.get(0);
                genTempFile(model);
                Intent intent = PhotoPicker.getCropImageIntent(Uri.fromFile(new File(model.getOriginalPath())),
                        Uri.fromFile(new File(model.getThumbPath())), mParam);
                try {
                    startActivityForResult(intent, BaseConstant.REQUEST_CODE_CROP);
                    return;
                } catch (ActivityNotFoundException e) {
                    Intent data = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(PhotoViewActivity.EXTRA_PHOTOS, mSelectPhotos);
                    data.putExtras(bundle);
                    setResult(RESULT_OK, data);
                }
            } else {
                Intent data = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(PhotoViewActivity.EXTRA_PHOTOS, mSelectPhotos);
                data.putExtras(bundle);
                setResult(RESULT_OK, data);
            }
        }
        finish();
    }

    /** 预览照片 */
    private void preview() {
        startActivity(new Intent(this, PhotoViewActivity.class).putExtra(PhotoViewActivity.EXTRA_PHOTOS,
                getPhotoPaths(mSelectPhotos)).putExtra(PhotoViewActivity.EXTRA_TYPE, PhotoViewActivity.TYPE_LOCAL));
    }

    private void album() {
        if (layoutAlbum.getVisibility() == View.GONE) {
            popAlbum();
        } else {
            hideAlbum();
        }
    }

    private String genTempFile(PhotoModel model) {
        File tempFile = new File(BaseConstant.TEMP_PATH);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        File f = new File(BaseConstant.TEMP_PATH, System.currentTimeMillis() + ".jpg");
        model.setThumbPath(f.getAbsolutePath());
        try {
            f.createNewFile();
        } catch (IOException e) {
        }
        return f.getAbsolutePath();
    }

    /** 弹出相册列表 */
    private void popAlbum() {
        layoutAlbum.setVisibility(View.VISIBLE);
        new AnimationUtil(getApplicationContext(), R.anim.translate_up_current).setLinearInterpolator().startAnimation(
                layoutAlbum);
    }

    /** 隐藏相册列表 */
    private void hideAlbum() {
        new AnimationUtil(getApplicationContext(), R.anim.translate_down).setLinearInterpolator().startAnimation(
                layoutAlbum);
        layoutAlbum.setVisibility(View.GONE);
    }

    /** 清空选中的图片 */
    @SuppressWarnings("unused")
    private void reset() {
        mSelectPhotos.clear();
        mRightText.setText("确定(0" + mParam.mMaxCount + ")");
        tvPreview.setEnabled(false);
    }

    @Override
    /** 点击查看照片 */
    public void onItemClick(int position) {
        PhotoModel photoModel = mPhotoAdapter.getItem(position);
        if (photoModel != null) {
            if (photoModel.getType() == PhotoModel.TYPE_CAMERA) {
                permission = RequestPermissionAuthorize.build(PhotoPickActivity.this);
                if(!permission.reservedForPermission(PermissionType.CAMERA, new RequestPermissionAuthorize.PermissionSuccessCallBack() {
                    @Override
                    public void permissionSuccess() {
                        catchPicture();
                    }
                })){
                }else{
                    catchPicture();
                }
            } else {
                ArrayList<String> list = new ArrayList<String>();
                list.add(photoModel.getOriginalPath());
                startActivity(new Intent(this, PhotoViewActivity.class).putExtra(PhotoViewActivity.EXTRA_PHOTOS, list)
                        .putExtra(PhotoViewActivity.EXTRA_TYPE, PhotoViewActivity.TYPE_LOCAL));
            }
        }
    }

    @Override
    /** 照片选中状态改变之后 */
    public void onCheckedChanged(PhotoModel photoModel, CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (mSelectPhotos.size() >= mParam.mMaxCount) {
                UIUtil.toastShort(this, String.format(getString(R.string.max_img_limit_reached), mParam.mMaxCount));
                photoModel.setChecked(false);
                buttonView.setChecked(false);
                mPhotoAdapter.notifyDataSetChanged();
            } else if (!mSelectPhotos.contains(photoModel)) {
                mSelectPhotos.add(photoModel);
            }
            tvPreview.setEnabled(true);
        } else {
            mSelectPhotos.remove(photoModel);
        }

        mRightText.setText("确定(" + mSelectPhotos.size() + "/" + mParam.mMaxCount + ")");

        if (mSelectPhotos.isEmpty()) {
            tvPreview.setEnabled(false);
            tvPreview.setText(getString(R.string.preview));
        }
    }

    @Override
    public void onBackPressed() {
        if (layoutAlbum.getVisibility() == View.VISIBLE) {
            hideAlbum();
        } else
            super.onBackPressed();
    }

    @Override
    /** 相册列表点击事件 */
    public void onItemClick(RecyclerView parent, View view, int position, long id) {
        AlbumModel current = mAlbumAdapter.getItem(position);
        for (int i = 0; i < mAlbumAdapter.getItemCount(); i++) {
            AlbumModel album = mAlbumAdapter.getItem(i);
            if (i == position) {
                album.setCheck(true);
            } else {
                album.setCheck(false);
            }
        }

        mAlbumAdapter.notifyDataSetChanged();
        hideAlbum();
        tvAlbum.setText(current.getName());

        // 更新照片列表
        if (AlbumModel.TYPE_ALL == current.getType()) {
            mPhotoManager.getRecent(reccentListener);
        } else {
            mPhotoManager.getAlbum(current.getName(), reccentListener); // 获取选中相册的照片
        }
    }

    private PhotoManager.OnLocalAlbumListener albumListener = new PhotoManager.OnLocalAlbumListener() {
        @Override
        public void onAlbumLoaded(List<AlbumModel> albums) {
            mAlbumAdapter.refreshList(albums);
        }
    };

    private PhotoManager.OnLocalRecentListener reccentListener = new PhotoManager.OnLocalRecentListener() {
        @Override
        public void onPhotoLoaded(List<PhotoModel> photos) {
            for (PhotoModel model : photos) {
                if (mSelectPhotos.contains(model)) {
                    model.setChecked(true);
                }
            }
            mPhotoAdapter.refreshList(photos);
            // mGvPhotos.smoothScrollToPosition(0); // 滚动到顶端
            // reset(); //--keep selected photos

        }
    };

    private ArrayList<String> getPhotoPaths(List<PhotoModel> photos) {
        ArrayList<String> list = new ArrayList<String>();
        for (PhotoModel model : photos) {
            list.add(model.getOriginalPath());
        }
        return list;
    }

    @Override
    protected boolean isStatusBarDarkMode() {
        return true;
    }
}
