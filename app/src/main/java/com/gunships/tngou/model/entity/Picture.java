package com.gunships.tngou.model.entity;

import java.io.Serializable;

/**
 * Created by 甘书须 on 2016/9/29.
 */
public class Picture implements Serializable{
    private int id;
    private int gallery; //图片库
    private String src; //图片地址

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGallery() {
        return gallery;
    }

    public void setGallery(int gallery) {
        this.gallery = gallery;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
