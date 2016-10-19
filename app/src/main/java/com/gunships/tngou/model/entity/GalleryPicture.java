package com.gunships.tngou.model.entity;

import java.util.List;

/**
 * Created by 甘书须 on 2016/10/13.
 */
public class GalleryPicture {

    private boolean status;
    private int count;
    private int fcount;
    private int galleryclass;
    private int id;
    private String url;
    private String img;
    private int rcount;
    private int size;
    private long time;
    private String title;
    private List<Picture> list;

    public List<Picture> getList() {
        return list;
    }

    public void setList(List<Picture> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getGalleryclass() {
        return galleryclass;
    }

    public void setGalleryclass(int galleryclass) {
        this.galleryclass = galleryclass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GalleryPicture{" +
                "status=" + status +
                ", count=" + count +
                ", fcount=" + fcount +
                ", galleryclass=" + galleryclass +
                ", id=" + id +
                ", url='" + url + '\'' +
                ", img='" + img + '\'' +
                ", rcount=" + rcount +
                ", size=" + size +
                ", time=" + time +
                ", title='" + title + '\'' +
                ", list=" + list +
                '}';
    }
}
