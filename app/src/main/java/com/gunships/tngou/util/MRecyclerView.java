package com.gunships.tngou.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;


/**
 * Created by 甘书须 on 2016/10/11.
 */
public class MRecyclerView extends RecyclerView{

    //private OnLoadMoreListener onLoadMoreListener;

    private Context context;


    public MRecyclerView(Context context) {super(context);}

    public MRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addListener(){
        //addOnScrollListener(new AutoLoadScrollListener());
    }

//    public class AutoLoadScrollListener extends OnScrollListener{
//        public AutoLoadScrollListener(){
//
//            super();
//        }
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy){
//            super.onScrolled(recyclerView,dx,dy);
//            if(getLayoutManager() instanceof LinearLayoutManager) {
//                int lastVisiableItem = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
//                int totalItem = getAdapter().getItemCount();
//                  if(lastVisiableItem>=totalItem-2&&dy>0){
//                  }
//            }
//        }
//        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//            switch (newState){
//                case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
//                    //对于滚动不加载图片的尝试
//                    //((NewsAdpter)getAdapter()).resumeLoad();
//                    //getAdapter().notifyDataSetChanged();
//                    break;
//                case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
//                   // ((NewsAdpter)getAdapter()).pauseLoad();
//                    break;
//                case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under
//                    //((NewsAdpter)getAdapter()).pauseLoad();
//                    break;
//            }
//        }
//
//    }

}
