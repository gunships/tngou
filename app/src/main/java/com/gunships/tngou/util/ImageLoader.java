package com.gunships.tngou.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import libcore.io.DiskLruCache;


/**
 * Created by 甘书须 on 2016/10/2.
 */
public class ImageLoader {
    private ImageLoader instance=null;
    private final static String TAG = "ImageLoader";
    private final static String headerUrl = "http://tnfs.tngou.net/img";
    private final static int UPDATA = 0;
    //构造线程池
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    //最大内存
    final static int maxCache = (int) Runtime.getRuntime().maxMemory();
    //Lrucache缓存大小为最大可用内存1/5
    private static int cacheSize = maxCache/8;
    //disklrucache缓存空间10M
    private static final int DISK_CACHE_SIZE = 1024*1024*20;
    /*图片缓存*/
    private static LruCache<String, Bitmap> lruCache;
    /*图片硬盘缓存核心类*/
    private static DiskLruCache mDiskLruCache;

    private Handler mHandler;


    public ImageLoader(Context context) {

        lruCache = new LruCache<String, Bitmap>(cacheSize) {
            protected int sizeOf(String key, Bitmap bitmap) {
                if (bitmap != null) {
                    // 计算存储bitmap所占用的字节数
                    return bitmap.getByteCount();
                } else {
                    return 0;
                }
            }
        };
        File cacheDir = getDiskCacheDir(context, "bitmap");
        if (!cacheDir.exists()){
            cacheDir.mkdirs();
        }
        try {
        mDiskLruCache = DiskLruCache.open(cacheDir,getAppVersion(context),1, DISK_CACHE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mHandler = new Handler(){//Hander更新UI
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case UPDATA:
                        getBitmapFromCache(msg.getData().getString("key"));
            }
                super.handleMessage(msg);
            }
        };


    }

    /**
     *  外部调用方法
     */
    public void getBitmap(final String imageUrl, final ImageView imageView, Context context){
        String key = getMD5Key(imageUrl);
        Bitmap bitmap = getBitmapFromCache(key);

        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
            cancleDownload(imageUrl,imageView);//取消下载
            return;

        }

        if(cancleDownload(imageUrl,imageView)){
            ImageDownloadTask task = new ImageDownloadTask(imageView);
            Zhanwei_Image  zhanwei_image = new Zhanwei_Image(task);
            //先把占位的图片放进去  
            imageView.setImageDrawable(zhanwei_image);
            task.url = imageUrl;
            // task执行任务  
            executors.execute(task);
        }

    }

     /*-----------------------------缓存读取-------------------------------------*/

    /** 此方法用于优化  ： 用户直接翻到哪个就先加载哪个、
     * @param imageUrl                - URL
     * @param imageView          - imageView
     *  core： 给当前的 imageView 得到给他下载的 task
     */
    private boolean cancleDownload(String imageUrl,ImageView imageView){
        // 给当前的 imageView 得到给他下载的 task
        ImageDownloadTask task = getImageDownloadTask(imageView);
        if(null != task){
            String down_key = task.url;
            if( null == down_key || !down_key.equals(imageUrl)){
                task.cancel(true);        // imageview和 url的key不一样       取消下载
            }else{
                return false;      //正在下载：
            }
        }
        return true;            //没有正在下载
    }

    /**
     *  从缓存中读取图片
     */
    public Bitmap getBitmapFromCache(String key){
        Bitmap bitmap;
        //先从LruCache中读取
        Log.i(TAG,"Bitmap in the LruCache."+key);
        synchronized (lruCache){
            bitmap=lruCache.get(key);
            if(bitmap!=null){
                //Log.i(TAG,"Bitmap in the LruCache."+key);
//                lruCache.remove(key);
//                lruCache.put(key,bitmap);
                return bitmap;
            }
        }
        //如果没有再从先从DiskLruCache中读取
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if(snapshot!=null){
            InputStream is = snapshot.getInputStream(0);
            bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //再没有则返回空
        return null;
    }




 /*-----------------------------网络加载-------------------------------------*/
    /**
     *  得到下载任务
     */
    private ImageDownloadTask getImageDownloadTask(ImageView imageView) {
        if( null != imageView){
            Drawable drawable = imageView.getDrawable();
            if( drawable instanceof Zhanwei_Image)
                return ((Zhanwei_Image)drawable).getImageDownloadTask();

        }
        return null;

    }

    /**
     *  占位的图片或者颜色  用来绑定相应的图片
     */
    private class Zhanwei_Image extends ColorDrawable {
        //里面存放 相应 的异步 处理时加载好的图片 ----- 相应的 task
        private final WeakReference<ImageDownloadTask> taskReference;
        public Zhanwei_Image(ImageDownloadTask task){
            super(Color.rgb(162,159,159));
            taskReference = new WeakReference<ImageDownloadTask>(task);
        }
        // 返回去这个 task 用于比较
        public ImageDownloadTask getImageDownloadTask(){
            return taskReference.get();
        }
    }

    /**
     *  图片下载任务类
     */
    private class ImageDownloadTask implements Runnable,Future {
        private String url;
        private ImageView imageView;

        public ImageDownloadTask(ImageView imageView) {

            this.imageView = imageView;
        }

        @Override
        public void run() {
            addLruCache(url,imageView);
        }


        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public Object get() throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    }

        /**
         * 图片加入缓存方法
         */
        public boolean addLruCache(String url,ImageView imageView) {
            String key = getMD5Key(url);
            Bitmap bitmap = null;
            boolean result = false;
            try {
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if (editor != null) {
                    OutputStream out = editor.newOutputStream(0);
                    if (downLoadImgge(url, out)) {
                        result = true;
                        Log.i(TAG, "下载成功:"+key);
                        editor.commit();
                    } else {
                        Log.i(TAG, "下载失败");
                        editor.abort();
                    }
                }
                mDiskLruCache.flush();
                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                if (snapshot != null) {
                    bitmap = BitmapFactory.decodeStream(snapshot.getInputStream(0));
                    if (bitmap != null)
                        lruCache.put(key, bitmap);
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bitmap",bitmap);
                    msg.what = UPDATA;
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return result;
            }

        }

        private boolean downLoadImgge(String imageUrl, OutputStream out) {
            boolean reslut = false;
            URL url = null;
            HttpURLConnection httpUrlConnection = null;
            BufferedOutputStream bfOut = null;
            InputStream in = null;
            try {
                url = new URL(headerUrl+imageUrl);
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                httpUrlConnection.setRequestMethod("GET");// 设置请求的方式
                httpUrlConnection.setReadTimeout(5000);// 设置超时的时间
                httpUrlConnection.setConnectTimeout(5000);// 设置链接超时的时间
                if (httpUrlConnection.getResponseCode() == 200) {
                    in = httpUrlConnection.getInputStream();//获取输入流
                    bfOut = new BufferedOutputStream(out, 1024);//创建BufferedOutputStream
                    int b;
                    while ((b = in.read()) != -1) {//把输入流写输入OutputStream
                        bfOut.write(b);
                    }
                    reslut = true;
                } else {
                    Log.w(TAG, "链接失败");
                    reslut = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
                try {
                    if (bfOut != null)
                        bfOut.close();
                    if (in != null)
                        in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return reslut;
        }





    /*------------------------------------------------------------------*/

        /**
         * MD5加密
         */
        public String getMD5Key(String imageUrl) {

            MessageDigest messageDigest = null;

            try {
                messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.reset();
                messageDigest.update(imageUrl.getBytes("UTF-8"));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            byte[] bytes = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                if (Integer.toHexString(0xFF & bytes[i]).length() == 1) {
                    stringBuilder.append("0").append(Integer.toHexString(0xFF & bytes[i]));
                } else {
                    stringBuilder.append(Integer.toHexString(0xFF & bytes[i]));
                }
            }
            return stringBuilder.toString();
        }

        /**
         * 获取当前版本
         */
        public int getAppVersion(Context mContext) {
            String versionName = "";
            int versioncode = 0;
            try {
                // ---get the package info---
                PackageManager pm = mContext.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                versionName = pi.versionName;
                versioncode = pi.versionCode;
                if (versionName == null || versionName.length() <= 0) {
                    return 0;
                }
            } catch (Exception e) {
                Log.e("VersionInfo", "Exception", e);
            }
            return versioncode;
        }

        /**
         * 获取当前cache路径
         */
        public File getDiskCacheDir(Context context, String uniqueName) {
            String cachePath;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
            return new File(cachePath + File.separator + uniqueName);
        }


}
