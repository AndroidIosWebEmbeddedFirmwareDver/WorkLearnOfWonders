package com.wondersgroup.hs.healthcloud.common.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.util.SparseArray;

import com.wondersgroup.hs.healthcloud.common.entity.AlbumModel;
import com.wondersgroup.hs.healthcloud.common.entity.PhotoModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取本地图集数据库数据
 * PhotoDao
 * chenbo
 */
public class PhotoDao {

	private ContentResolver mResolver;
	private Context mContext;

	public PhotoDao(Context context) {
		mResolver = context.getContentResolver();
		mContext = context;
	}

	public List<PhotoModel> getCurrent() {

		Cursor cursor = mResolver.query(Media.EXTERNAL_CONTENT_URI, new String[] {ImageColumns._ID, ImageColumns.DATA,
				ImageColumns.DATE_ADDED, ImageColumns.SIZE, ImageColumns.MIME_TYPE }, null, null, ImageColumns.DATE_ADDED);
		if (cursor == null || !cursor.moveToNext())
			return new ArrayList<PhotoModel>();
		List<PhotoModel> photos = new ArrayList<PhotoModel>();
//		SparseArray<String> thumbnails = getThumbNails();
		cursor.moveToLast();
		try {
			do {
				if (!isGif(cursor.getString(cursor.getColumnIndex(ImageColumns.MIME_TYPE)))
						&& cursor.getLong(cursor.getColumnIndex(ImageColumns.SIZE)) > 1024 * 10) {
					PhotoModel photoModel = new PhotoModel();
					photoModel.setType(PhotoModel.TYPE_PHOTO);
					photoModel.setOriginalPath(cursor.getString(cursor.getColumnIndex(ImageColumns.DATA)));
//					photoModel.setLocalThumbPath(thumbnails.get(cursor.getInt(cursor.getColumnIndex(ImageColumns._ID))));
					photos.add(photoModel);
				}
			} while (cursor.moveToPrevious());
		} finally {
			cursor.close();
		}
		return photos;
	}

	/**
	 * 获取所有专辑
	 * getAlbums
	 * @return
	 * @since 1.0
	 */
	public List<AlbumModel> getAlbums() {
		List<AlbumModel> albums = new ArrayList<AlbumModel>();
		Map<String, AlbumModel> map = new HashMap<String, AlbumModel>();
		Cursor cursor = mResolver.query(Media.EXTERNAL_CONTENT_URI, new String[] { ImageColumns.DATA,
				ImageColumns.BUCKET_DISPLAY_NAME, ImageColumns.SIZE, ImageColumns.MIME_TYPE }, null, null, null);
		if (cursor == null || !cursor.moveToNext())
			return new ArrayList<AlbumModel>();
		cursor.moveToLast();
		AlbumModel current = new AlbumModel("最近照片", 0, cursor.getString(cursor.getColumnIndex(ImageColumns.DATA)), true);
		current.setType(AlbumModel.TYPE_ALL);
		albums.add(current);
		try {
			while (cursor.moveToPrevious()) {
				if (isGif(cursor.getString(cursor.getColumnIndex(ImageColumns.MIME_TYPE)))
						|| cursor.getInt(cursor.getColumnIndex(ImageColumns.SIZE)) < 1024 * 10)
					continue;

				current.increaseCount();
				String name = cursor.getString(cursor.getColumnIndex(ImageColumns.BUCKET_DISPLAY_NAME));
				if (map.keySet().contains(name))
					map.get(name).increaseCount();
				else {
					AlbumModel album = new AlbumModel(name, 1, cursor.getString(cursor.getColumnIndex(ImageColumns.DATA)));
					album.setType(AlbumModel.TYPE_ALBUM);
					map.put(name, album);
					albums.add(album);
				}
			}
		} finally {
			cursor.close();
		}
		return albums;
	}

	/**获取专辑下所有图片*/
	public List<PhotoModel> getAlbum(String name) {
		Cursor cursor = mResolver.query(Media.EXTERNAL_CONTENT_URI, new String[] { ImageColumns._ID,ImageColumns.BUCKET_DISPLAY_NAME,
						ImageColumns.DATA, ImageColumns.DATE_ADDED, ImageColumns.SIZE, ImageColumns.MIME_TYPE }, "bucket_display_name = ?",
				new String[] { name }, ImageColumns.DATE_ADDED);
		if (cursor == null || !cursor.moveToNext())
			return new ArrayList<PhotoModel>();
		List<PhotoModel> photos = new ArrayList<PhotoModel>();
//		SparseArray<String> thumbnails = getThumbNails();
		cursor.moveToLast();
		try {
			do {
				if (!isGif(cursor.getString(cursor.getColumnIndex(ImageColumns.MIME_TYPE)))
						&& cursor.getLong(cursor.getColumnIndex(ImageColumns.SIZE)) > 1024 * 10) {
					PhotoModel photoModel = new PhotoModel();
					photoModel.setType(PhotoModel.TYPE_PHOTO);
					photoModel.setOriginalPath(cursor.getString(cursor.getColumnIndex(ImageColumns.DATA)));
//					photoModel.setLocalThumbPath(thumbnails.get(cursor.getInt(cursor.getColumnIndex(ImageColumns._ID))));
					photos.add(photoModel);
				}
			} while (cursor.moveToPrevious());
		} finally {
			cursor.close();
		}
		return photos;
	}

	// 获取缩略图
	private SparseArray<String> getThumbNails() {

		String[] projection = {  MediaStore.Images.Thumbnails.IMAGE_ID,
				MediaStore.Images.Thumbnails.DATA };
		Cursor cursor = null;
		try {
			cursor = mResolver.query( MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection,
					null, null, null);
			cursor.moveToLast();
			SparseArray<String> thumbnails = new SparseArray<>();
			while (cursor.moveToPrevious()){
				int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID));
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
				thumbnails.put(id, path);
			}
			return thumbnails;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public int deletePhoto(String path) {
		return mResolver.delete(Media.EXTERNAL_CONTENT_URI, ImageColumns.DATA + "=?", new String[] {path});
	}

	public void addPhoto(String path) {
		MediaScannerConnection.scanFile(mContext,
				new String[] { path }, null,
				new MediaScannerConnection.OnScanCompletedListener() {
					public void onScanCompleted(String path, Uri uri) {
						Log.i("ExternalStorage", "Scanned " + path + ":");
						Log.i("ExternalStorage", "-> uri=" + uri);
					}
				});
	}

	private boolean isGif(String mineType) {
		return "image/gif".equals(mineType);
	}
}
