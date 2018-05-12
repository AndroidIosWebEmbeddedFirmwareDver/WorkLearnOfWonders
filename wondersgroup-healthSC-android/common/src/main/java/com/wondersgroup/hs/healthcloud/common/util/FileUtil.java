package com.wondersgroup.hs.healthcloud.common.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件操作工具类
 * FileUtil
 * sunzhenyu
 * 2015年3月6日 下午4:16:35
 *
 * @version 1.0
 */
public class FileUtil {
    private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte
    private static final String DEFAULT_CACHE_DIR = "default_cache_dir";

    /**
     * 检查sdcard是否挂载
     *
     * @return
     */
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 写文件
     *
     * @param path
     * @param str
     */
    public static void writeStr(String path, String str) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File file = new File(path).getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(path));
            out.write(str.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读文件
     *
     * @param path
     * @return
     */
    public static String readStr(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        File file = new File(path);
        if (file.exists()) {
            byte[] bytes = getFileBytes(file);
            if (bytes != null) {
                return new String(bytes);
            }
        }
        return "";
    }

    /**
     * 保存图片到本地
     *
     * @param bitmap
     * @param desPath
     * @return
     */
    public static boolean saveBitmap(Bitmap bitmap, String desPath) {
        File file = new File(desPath);
        File parent = new File(file.getParent());
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            bitmap.compress(CompressFormat.JPEG, 80, os);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            file.delete();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 文件base64转码
     *
     * @param path
     * @return
     */
    public static String base64File(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        File file = new File(path);
        if (file.exists()) {
            return Base64.encodeToString(getFileBytes(file), Base64.DEFAULT);
        }
        return "";
    }

    /**
     * 保存流到本地
     *
     * @param is
     * @param desPath
     */
    public static void streamToFile(InputStream is, String desPath, boolean append, OnProgressListener l) throws Exception {
        BufferedInputStream bis = null;
        FileOutputStream fileOutputStream = null;
        try {
            if (append) {
                fileOutputStream = new FileOutputStream(desPath, true);
            } else {
                fileOutputStream = new FileOutputStream(desPath);
            }
            bis = new BufferedInputStream(is);

            byte[] tmp = new byte[4096];
            int len;
            long current = new File(desPath).length();
            if (l != null) {
                l.onProgress(0);
            }
            while ((len = bis.read(tmp)) != -1) {
                fileOutputStream.write(tmp, 0, len);
                current += len;
                if (l != null) {
                    l.onProgress(current);
                }
            }
            fileOutputStream.flush();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }

    public static interface OnProgressListener {
        void onProgress(long len);
    }

    /**
     * 文件转换为byte数组
     */
    public static byte[] getFileBytes(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static String inputStreamToStr(InputStream in) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1024];
            int n;
            while ((n = in.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            in.close();
            bos.close();
            return bos.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据文件类型过滤
     *
     * @param dir  文件路径
     * @param type 文件类型
     * @return
     */
    public static File[] getFilesByType(String dir, final String type) {
        File file = new File(dir);
        if (!file.exists()) {
            return null;
        }
        return file.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File file, String name) {
                return name.endsWith(type);
            }
        });
    }

    /**
     * 删除目录下的所有文件
     */
    public static boolean deleteDirectory(String sPath, FileFilter fileFilter) {
        if (TextUtils.isEmpty(sPath)) {
            return false;
        }
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        File[] files = dirFile.listFiles(fileFilter);
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除目录下的所有文件
     */
    public static boolean deleteDirectory(String sPath) {
        if (TextUtils.isEmpty(sPath)) {
            return false;
        }
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 删除文件
     */
    public static boolean deleteFile(String sPath) {
        if (TextUtils.isEmpty(sPath)) {
            return false;
        }
        boolean flag = false;
        File file = new File(sPath);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * Check how much usable space is available at a given path.
     *
     * @param path The path to check
     * @return The space available in bytes
     */
    @SuppressWarnings("deprecation")
    @TargetApi(9)
    public static long getUsableSpace(File path) {
        if (ApiCompatibleUtil.hasGingerbread()) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }

    // 递归
    public static long getFileSize(File f) {
        long size = 0;
        File[] flist = f.listFiles();
        if (flist == null) return 0;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    public static void copyAssets(Context context, String assetFileName, String desFile) throws IOException {
        InputStream in = context.getAssets().open(assetFileName);
        OutputStream out = new FileOutputStream(desFile);

        byte[] buff = new byte[1024];
        int len;
        while ((len = in.read(buff)) > 0) {
            out.write(buff, 0, len);
        }

        out.close();
        in.close();
    }

    /*
     * private File getDiskCacheDir(Context context, String uniqueName) { final
     * String cachePath = context.getCacheDir().getPath(); return new
     * File(cachePath + File.separator + uniqueName); }
     */

    /**
     * Get a usable cache directory (external if available, internal otherwise).
     *
     * @param context    The context to use
     * @param uniqueName A unique directory name to append to the cache dir
     * @return The cache dir
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use
        // external cache dir
        // otherwise use internal cache dir

        // TODO: getCacheDir() should be moved to a background thread as it
        // attempts to create the
        // directory if it does not exist (no disk access should happen on the
        // main/UI thread).
        final String cachePath;
        if (isExternalMounted() && null != getExternalCacheDir(context)) {
            cachePath = getExternalCacheDir(context).getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        Log.i("Cache dir", cachePath + File.separator + uniqueName);
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    private static File getExternalCacheDir(Context context) {
        // TODO: This needs to be moved to a background thread to ensure no disk
        // access on the
        // main/UI thread as unfortunately getExternalCacheDir() calls mkdirs()
        // for us (even
        // though the Volley library will later try and call mkdirs() as well
        // from a background
        // thread).
        return context.getExternalCacheDir();
    }

    @SuppressLint("NewApi")
    private static boolean isExternalMounted() {
        if (ApiCompatibleUtil.hasGingerbread()) {
            return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable();
        }
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static String getPhotoPath() {
        File dir = new File(Environment.getExternalStorageDirectory(), "/sdp_mpos/photo");
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }

    /**
     * 批量压缩文件（夹）
     *
     * @param resFileList 要压缩的文件（夹）列表
     * @param zipFile     生成的压缩文件
     * @throws IOException 当压缩过程出错时抛出
     */
    public static void zipFiles(Collection<File> resFileList, File zipFile) throws IOException {
        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), BUFF_SIZE));
        for (File resFile : resFileList) {
            zipFile(resFile, zipout, "");
        }
        zipout.close();
    }

    /**
     * 压缩文件
     *
     * @param resFile  需要压缩的文件（夹）
     * @param zipout   压缩的目的文件
     * @param rootpath 压缩的文件路径
     * @throws FileNotFoundException 找不到文件时抛出
     * @throws IOException           当压缩过程出错时抛出
     */
    private static void zipFile(File resFile, ZipOutputStream zipout, String rootpath) throws FileNotFoundException, IOException {
        rootpath = rootpath + (rootpath.trim().length() == 0 ? "" : File.separator) + resFile.getName();
        rootpath = new String(rootpath.getBytes("8859_1"), "GB2312");
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            for (File file : fileList) {
                zipFile(file, zipout, rootpath);
            }
        } else {

            try {
                byte buffer[] = new byte[BUFF_SIZE];
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile), BUFF_SIZE);
                zipout.putNextEntry(new ZipEntry(rootpath));
                int realLength;
                while ((realLength = in.read(buffer)) != -1) {
                    zipout.write(buffer, 0, realLength);
                }
                in.close();
                zipout.flush();
                zipout.closeEntry();
            } catch (OutOfMemoryError e) {
            }

        }
    }

    public static boolean isSDCardAvailable() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static File getDefaultCacheFile(Context context) {
        return new File(context.getCacheDir(), DEFAULT_CACHE_DIR);
    }


}
