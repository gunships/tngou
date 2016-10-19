package com.gunships.tngou.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gunships.tngou.R;
import com.gunships.tngou.model.biz.GetGalleryclass;
import com.gunships.tngou.model.entity.Galleryclass;
import com.squareup.picasso.Picasso;

/**
 * Created by 甘书须 on 2016/10/12.
 */
public class ClassAdapter extends RecyclerView.Adapter {
    public interface OnItemClickListener{
        void itemClick(View view, int position, int idData);
        void itemLongClick(View view , int position,int idData);
    }
    private OnItemClickListener onItemClickListener;
    private Galleryclass[] galleryclasses = GetGalleryclass.galleryclasses;
    private LayoutInflater mInflater;
    private Context context;
    public ClassAdapter(Context context){
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ClassHolder(mInflater.inflate(R.layout.recycler_class_unit,parent,false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ClassHolder mHolder = (ClassHolder)holder;
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.itemClick(mHolder.itemView,mHolder.getLayoutPosition(),galleryclasses[position].getId());
            }
        });
        mHolder.tv.setText(galleryclasses[position].getName());

        Picasso.with(context).load(galleryclasses[position].getDescription()).resize(300,500).centerCrop().into(mHolder.imgv);
    }

    @Override
    public int getItemCount() {
        return galleryclasses.length;
    }

    public static class ClassHolder extends RecyclerView.ViewHolder{
        private ImageView imgv;
        private TextView tv;

        public ClassHolder(View itemView) {
            super(itemView);
            imgv = (ImageView) itemView.findViewById(R.id.class_gallery_img);
            tv = (TextView) itemView.findViewById(R.id.class_gallery_titles);
        }
    }
}
