package com.wondersgroup.hs.healthcloud.common.logic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Handler;

import com.wondersgroup.hs.healthcloud.common.dao.PhotoDao;
import com.wondersgroup.hs.healthcloud.common.entity.AlbumModel;
import com.wondersgroup.hs.healthcloud.common.entity.PhotoModel;
import com.wondersgroup.hs.healthcloud.common.util.BaseConstant;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 用来处理图片选择
 * PhotoManager
 * chenbo
 * 2015年3月17日 上午10:26:15
 *
 * @version 1.0
 */
public class PhotoManager {

    private static Object lock = new Object();
    private PhotoDao mPhotoDao;
    private Handler mMainHandler;
    private Context mContext;

    public PhotoManager(Context context) {
        mPhotoDao = new PhotoDao(context);
        mMainHandler = new Handler(context.getMainLooper());
        mContext = context;
    }

    /**
     * 获取本地图库照片回调
     */
    public interface OnLocalRecentListener {
        public void onPhotoLoaded(List<PhotoModel> photos);
    }

    /**
     * 获取本地相册信息回调
     */
    public interface OnLocalAlbumListener {
        public void onAlbumLoaded(List<AlbumModel> albums);
    }


    /**
     * 获取所有的图片，按添加时间排序
     * getRecent
     *
     * @param listener
     * @since 1.0
     */
    public void getRecent(final OnLocalRecentListener listener) {
        new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    final List<PhotoModel> list = new ArrayList<PhotoModel>();
                    PhotoModel model = new PhotoModel();
                    model.setType(PhotoModel.TYPE_CAMERA);
                    list.add(model);
                    list.addAll(mPhotoDao.getCurrent());
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onPhotoLoaded(list);
                        }
                    });
                }
            }
        }.start();
    }


    /**
     * 获取专辑列表
     * updateAlbum
     *
     * @param listener
     * @since 1.0
     */
    public void updateAlbum(final OnLocalAlbumListener listener) {
        new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    final List<AlbumModel> list = mPhotoDao.getAlbums();
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onAlbumLoaded(list);
                        }
                    });
                }
            }
        }.start();
    }

    /**
     * 获取专辑下的图片
     * getAlbum
     *
     * @param name
     * @param listener
     * @since 1.0
     */
    public void getAlbum(final String name, final OnLocalRecentListener listener) {
        new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    final List<PhotoModel> list = mPhotoDao.getAlbum(name);
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onPhotoLoaded(list);
                        }
                    });
                }
            }
        }.start();
    }

    /**
     * 批量生成缩略图
     * getThumb
     *
     * @param list
     * @param w        指定宽度
     * @param h        指定高度
     * @param listener
     * @since 1.0
     */
    public void getThumb(final List<PhotoModel> list, final int w, final int h, final OnLocalRecentListener listener) {
        new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    for (PhotoModel model : list) {
                        model.setThumbPath(processPhoto(model.getOriginalPath(), w, h));
                    }
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onPhotoLoaded(list);
                        }
                    });
                }
            }
        }.start();
    }

    private String processPhoto(String filePath, int w, int h) {
        Bitmap bitmap= new BitmapTools(mContext).decodeBitmap(filePath, w, h);
        if (bitmap == null) {
            return null;
        }
//        bitmap = rotateBitmapIfNeeded(filePath, bitmap);
        File tempDir = new File(BaseConstant.TEMP_PATH);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        File fileOut = new File(tempDir, new File(filePath).getName());
        FileUtil.saveBitmap(bitmap, fileOut.getAbsolutePath());
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return fileOut.getAbsolutePath();
    }

    private synchronized Bitmap rotateBitmapIfNeeded(String uri, Bitmap bitmap) {
        Bitmap result = bitmap;
        File bitmapFile = new File(uri);
        if (bitmapFile != null && bitmapFile.exists()) {
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(bitmapFile.getPath());
            } catch (Throwable e) {
                return result;
            }
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            int angle = 0;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
                default:
                    angle = 0;
                    break;
            }
            if (angle != 0) {
                Matrix m = new Matrix();
                m.postRotate(angle);
                result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                bitmap.recycle();
                bitmap = null;
            }
        }
        return result;
    }


}
