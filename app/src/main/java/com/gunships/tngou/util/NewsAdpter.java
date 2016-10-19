package com.gunships.tngou.util;

import android.content.Context;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gunships.tngou.R;
import com.gunships.tngou.model.entity.Gallery;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;


/**
 * Created by 甘书须 on 2016/9/30.
 */
public class NewsAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    public interface OnItemClickListener{
        void itemClick(View view,int position,int idData);
    }
    private OnItemClickListener onItemClickListener;
    private final static String headerUrl = "http://tnfs.tngou.net/img";
    private LayoutInflater inflater;
    private List<Gallery> list;
    private Context context;
    private ImageLoader imageLoader;
    private boolean isLoad=true;
    boolean isSlidingToLast = false;

    public NewsAdpter(Context context, List<Gallery> list){
        super();
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        imageLoader = new ImageLoader(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public NewImgHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_news_unit,parent,false);
        NewImgHolder viewHolder = new NewImgHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NewImgHolder newsHolder = (NewImgHolder)holder;
//        Log.i("适配器",list.get(position).toString()+" ");
        if(onItemClickListener!=null) {
            newsHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.itemClick(newsHolder.itemView,newsHolder.getAdapterPosition(),list.get(position).getId());
                }
            });
        }
        if(isLoad) {
//          imageLoader.getBitmap(list.get(position).getImg(), holder.imageView, context);
            Picasso.with(context).load(headerUrl + list.get(position).getImg()).placeholder(R.mipmap.ic_loading_large)
                    .resize(500,700).centerInside().into(newsHolder.imageView);
        }
        newsHolder.tv.setText(list.get(position).getTitle());
    }


    @Override
    public int getItemCount() {
            return list.size();
    }

    public static class NewImgHolder extends RecyclerView.ViewHolder{
        //LinearLayout item;
        TextView tv;
        ImageView imageView;

        public NewImgHolder(View itemView) {
            super(itemView);
            //item = (LinearLayout)itemView.findViewById(R.id.linear1);
            tv = (TextView) itemView.findViewById(R.id.new_gallery_titles);
            imageView = (ImageView)itemView.findViewById(R.id.new_gallery_img);
        }
    }

    public class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }
        @Override public String key() { return "square()"; }
    }

    public boolean resumeLoad(){
        isLoad = true;
        return isLoad;
    }

    public boolean pauseLoad(){
        isLoad = false;
        return isLoad;
    }


    public void refreshList(List<Gallery> list){
        list.addAll(this.list);
        this.list = list;
    }

    public void loadList(List<Gallery> list){
        this.list.addAll(list);

    }

    public void clear(){
        if(this.list.size()>100){
            this.list.clear();
        }
    }


}
