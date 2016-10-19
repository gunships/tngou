package com.gunships.tngou.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.gunships.tngou.application.App;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 甘书须 on 2016/10/17.
 */
public class SaveBitmapUtil {

    public static void save(String fileName,String url){
        File file = App.file;
        String name = getMD5Key(fileName);
        File newFile = new File(file+File.separator+name+".jpg");
        try {
            if(!newFile.exists()) {
            newFile.createNewFile();
        }
            //Log.i("path","getName()"+newFile.getName()+"getAbsolutePath()"+newFile.getAbsolutePath()+"getPath()"+newFile.getPath());
            FileOutputStream fos = new FileOutputStream(newFile);
            if(downLoadImgge(url,fos)) {
            }else{
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(App.instance.getApplicationContext(),"保存失败，无法获取到图片",Toast.LENGTH_LONG).show();
                    }
                });
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static boolean downLoadImgge(String imageUrl, FileOutputStream out) {
        Bitmap reslut ;
        URL url = null;
        HttpURLConnection httpUrlConnection = null;
        BufferedOutputStream bfOut = null;
        InputStream in = null;
        try {
            url = new URL(imageUrl);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setRequestMethod("GET");// 设置请求的方式
            httpUrlConnection.setReadTimeout(5000);// 设置超时的时间
            httpUrlConnection.setConnectTimeout(5000);// 设置链接超时的时间
            if (httpUrlConnection.getResponseCode() == 200) {
                in = httpUrlConnection.getInputStream();//获取输入流
                bfOut = new BufferedOutputStream(out, 1024);//创建BufferedOutputStream
                int len;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) != -1) {//把输入流写输入OutputStream
                    bfOut.write(buffer,0,len);
                }
                return true;
            } else {
                return false;
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
        return false;
    }

    /**
     * MD5加密
     */
    public static String getMD5Key(String imageUrl) {

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

}
