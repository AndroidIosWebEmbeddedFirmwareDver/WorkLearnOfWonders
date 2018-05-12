package com.wondersgroup.hs.healthcloud.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.wondersgroup.hs.healthcloud.common.R;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/7/18 16:48
 */
public class BitmapTools {
    private Context mContext;

    public BitmapTools(Context context) {
        mContext = context;
    }

    public void display(ImageView container, String uri) {
        display(container, uri, SizeType.MEDIUM, 0, null);
    }

    public void display(ImageView container, String uri, int defaultDrawableRes) {
        display(container, uri, SizeType.MEDIUM, defaultDrawableRes, null);
    }

    public void display(ImageView container, String uri, SizeType sizeType) {
        display(container, uri, sizeType, 0, null);
    }

    public void clearViewTask(ImageView container) {
        Glide.clear(container);
    }

    public Bitmap decodeBitmap(String path, int w, int h) {
        Bitmap bitmap = null;
        try {
            bitmap = new StreamBitmapDecoder(mContext).decode(new FileInputStream(new File(path)), w, h).get();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public File getDiskCachePath() {
       return Glide.getPhotoCacheDir(mContext);
    }

    public void clearDiskCache(final ResponseCallback<String> callback) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (callback != null) {
                    callback.onStart();
                }
            }

            @Override
            protected Void doInBackground(Void... params) {
                Glide.get(mContext).clearDiskCache();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (callback != null) {
                    callback.onSuccess("清除缓存成功！");
                    callback.onFinish();
                }
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                if (callback != null) {
                    callback.onCancelled();
                }
            }
        }.execute();
    }

    public void display(ImageView container, String uri, RequestListener<String, GlideDrawable> listener) {
        display(container, uri, SizeType.LARGE, 0, listener);
    }

    public void load(String uri, @NonNull final ResponseCallback<Drawable> callback) {
        if (isFinish()) {
            return;
        }
        Glide.with(mContext).load(uri).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                resource.start();
                callback.onSuccess(resource);
                callback.onFinish();
            }

            @Override
            public void onStart() {
                super.onStart();
                callback.onStart();
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                callback.onFailure(e);
                callback.onFinish();
            }
        });
    }

    public Target<GlideDrawable> display(ImageView container, String uri, SizeType sizeType, int defaultDrawableRes, RequestListener<String, GlideDrawable> listener) {
        if (isFinish()) {
            return null;
        }
        switch (sizeType) {
            case LARGE:
                if (defaultDrawableRes == 0) {
                    defaultDrawableRes = R.drawable.ic_default_large;
                }
                break;
            case MEDIUM:
                if (defaultDrawableRes == 0) {
                    defaultDrawableRes = R.drawable.ic_default_normal;
                }
                break;
            case SMALL:
                if (defaultDrawableRes == 0) {
                    defaultDrawableRes = R.drawable.ic_default_small;
                }
                break;
            case CUSTOM:
                break;
        }
        Animation animation = new AlphaAnimation(0.3f ,1);
        animation.setDuration(300);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        Target<GlideDrawable> target = Glide.with(mContext).load(uri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(listener)
                .animate(animation)
                .placeholder(defaultDrawableRes).error(defaultDrawableRes)
                .into(container);
        return target;
    }



    public enum SizeType {
        LARGE, MEDIUM, SMALL, CUSTOM
    }

    private boolean isFinish() {
        if (mContext instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && ((Activity)mContext).isDestroyed()) {
                return true;
            }
            return ((Activity) mContext).isFinishing();
        }
        return false;
    }

    public void display(View view, Drawable drawable) {
        if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        if (drawable instanceof GifDrawable) {
            ((GifDrawable) drawable).start();
        }
    }

    public void saveDrawable(Drawable drawable, String path) throws Exception {
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            FileUtil.saveBitmap(bitmap, path);
        } else if (drawable instanceof GlideBitmapDrawable) {
            Bitmap bitmap = ((GlideBitmapDrawable) drawable).getBitmap();
            FileUtil.saveBitmap(bitmap, path);
        } else if (drawable instanceof GifDrawable) {
            if (!path.endsWith("gif")) {
                path += ".gif";
            }
            FileUtil.streamToFile(new ByteArrayInputStream(((GifDrawable) drawable).getData()), path, false, null);
        }
    }
}
