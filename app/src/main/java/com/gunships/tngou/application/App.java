package com.gunships.tngou.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;


import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

/**
 * Created by 甘书须 on 2016/9/29.
 */
public class App extends Application{

    public static App instance =null;
    public final static String URL = "http://www.tngou.net/tnfs/api/";
    public static DisplayImageOptions mOptions;
    public static File file;

    @Override
    public void onCreate(){
        super.onCreate();
//        initImageLoader(this);
        instance =this;
        file = creatFile();
    }
    public File creatFile(){
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(),"tngou");
        Log.i("APP",file.getAbsolutePath());
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }
//    /**
//     * 初始化ImageLoader
//     */
//    private void initImageLoader(Context context) {
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//                .threadPriority(Thread.NORM_PRIORITY - 2)
//                .denyCacheImageMultipleSizesInMemory()
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                .diskCacheSize(200 * 1024 * 1024) // 200 Mb
//                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                //.writeDebugLogs() // Remove for release app
//                .build();
//        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config);
//
//
//        mOptions = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.mipmap.ic_loading_large)   //加载过程中
//                .showImageForEmptyUri(R.mipmap.ic_loading_large) //uri为空时
//                .showImageOnFail(R.mipmap.ic_loading_large)      //加载失败时
//                .cacheOnDisk(true)
//                .cacheInMemory(true)                             //允许cache在内存和磁盘中
//                .bitmapConfig(Bitmap.Config.RGB_565)             //图片压缩质量参数
//                .build();
//    }
}
