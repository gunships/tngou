package com.gunships.tngou.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 甘书须 on 2016/10/1.
 */
public class GalleryNews {
    private boolean status;//状态
    private int total;//该ID后+分类筛选后的图片总数
    private List<Gallery> tngou;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Gallery> getTngou() {
        return tngou;
    }

    public void setTngou(List<Gallery> tngou) {
        this.tngou = tngou;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(Gallery g:tngou){
            String a =g.toString();
            stringBuilder.append(a+"\n");
        }

        return "GalleryNews{" +
                "status=" + status +
                ", total=" + total +
                ", tngou:\n" + stringBuilder.toString() +
                '}';
    }
}
