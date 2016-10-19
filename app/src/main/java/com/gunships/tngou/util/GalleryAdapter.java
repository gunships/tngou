package com.gunships.tngou.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gunships.tngou.R;
import com.gunships.tngou.application.App;
import com.gunships.tngou.model.entity.Gallery;
import com.gunships.tngou.model.entity.GalleryPicture;
import com.gunships.tngou.model.entity.Picture;
import com.gunships.tngou.view.GalleryListFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 甘书须 on 2016/10/13.
 */
public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {




    public interface OnItemClickListener{
        void itemClick(View view, int position);
        void itemLongClick(View view , int position);
    }
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private OnItemClickListener onItemClickListener;
    private final static String headerUrl = "http://tnfs.tngou.net/img";
    private LayoutInflater inflater;
    private List<Picture> list;
    private List<View> listView;
    private Context context;
    int screenWidth;
    int screenHeight;
    //private Bitmap bitmaps;
    private Handler mHander;


    public GalleryAdapter(final Context context){
        super();
        inflater = LayoutInflater.from(context);

        this.context = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.widthPixels;
        mHander = new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case 1:
                        Toast.makeText(context,"文件存放到目录"+App.file,Toast.LENGTH_LONG).show();
                }
                super.handleMessage(msg);
            }
        };
    }
    public void setData(List<Picture> list){
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_picture_unit,parent,false);
        GalleryHolder viewHolder = new GalleryHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final GalleryHolder newsHolder = (GalleryHolder)holder;
//        Log.i("适配器",list.get(position).getSrc()+" ");
        if(onItemClickListener!=null) {
            newsHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.itemClick(newsHolder.itemView,newsHolder.getAdapterPosition());
                }
            });
            newsHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.itemLongClick(newsHolder.itemView,newsHolder.getLayoutPosition());
                    return true;
                }
            });
        }

//         imageLoader.getBitmap(list.get(position).getImg(), holder.imageView, context);
           Picasso.with(context).load(headerUrl + list.get(position).getSrc()).placeholder(R.mipmap.ic_loading_large).into(newsHolder.imageView);
    }


    public void onSaveBitmap(int position) {
        singleThreadExecutor.execute(new SavaBitmapTask(list.get(position).getSrc()));
    }

    public class SavaBitmapTask implements Runnable{
        String src;
        String name;
        public SavaBitmapTask(String src){
            this.src = src;
            this.name = src.substring(0,src.indexOf("."));
        }
        @Override
        public void run() {
            SaveBitmapUtil.save(name,headerUrl+src);
            Message msg = new Message();
            msg.what = 1;
            mHander.sendMessage(msg);
        }

    }
    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }else {
            return list.size();
        }

    }

    public static class GalleryHolder extends RecyclerView.ViewHolder{
        //LinearLayout items;
        ImageView imageView;

        public GalleryHolder(View itemView) {
            super(itemView);
            //items = (LinearLayout)itemView.findViewById(R.id.linear_picture);
            imageView = (ImageView)itemView.findViewById(R.id.imageview_picture);
        }
    }

    public void refreshList(List<Picture> list){
        list.addAll(this.list);
        this.list = list;
    }

    public void loadList(List<Picture> list){
        this.list.addAll(list);

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
    public void closeThreadPool() {
        singleThreadExecutor.shutdown();
    }
}
