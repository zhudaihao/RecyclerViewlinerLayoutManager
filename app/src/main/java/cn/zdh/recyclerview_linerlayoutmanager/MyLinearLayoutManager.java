package cn.zdh.recyclerview_linerlayoutmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * 自定义linearLayoutManager
 */
public class MyLinearLayoutManager extends LinearLayoutManager implements RecyclerView.OnChildAttachStateChangeListener {
    //Y轴滑动距离
    private int dy;
    //回调接口  监听itemView 滑入 滑出
    private OnViewPagerListener onViewPagerListener;
    //实现吸附效果类
    private PagerSnapHelper pagerSnapHelper;

    public void setOnViewPagerListener(OnViewPagerListener onViewPagerListener) {
        this.onViewPagerListener = onViewPagerListener;
    }

    public MyLinearLayoutManager(Context context) {
        super(context);
        pagerSnapHelper = new PagerSnapHelper();
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        pagerSnapHelper = new PagerSnapHelper();
    }

    //------------------------------------------监听layoutManager 完全添加到recyclerView的回调--------------------------------------------------------


    /**
     * 当layoutManager 完全加入到recyclerView 回调这个方法
     *
     * @param view-->recyclerView
     */
    @Override
    public void onAttachedToWindow(RecyclerView view) {
        //recyclerView调用监听itemView滑入 滑出监听事件-->实现接口方法才会回调
        view.addOnChildAttachStateChangeListener(this);

        //把recyclerView 绑定到 吸附帮助类里面
        pagerSnapHelper.attachToRecyclerView(view);

        super.onAttachedToWindow(view);
    }


//------------------------------------------监听recyclerView滑动距离--------------------------------------------------------

    /**
     * 设置 是否监听Y轴滑动距离  true是监听 默认false
     *
     * @return
     */
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.dy = dy;
        return super.scrollVerticallyBy(dy, recycler, state);

    }

    //------------------------------------------监听itemView 滑入 滑出 回调--------------------------------------------------------

    /**
     * 监听itemView 滑入recyclerView的回调(滑入一点点就回调)
     *
     * @param view -->itemView
     */
    @Override
    public void onChildViewAttachedToWindow(@NonNull View view) {
        //获recyclerView的高
        int height = getHeight();
        if (dy >= height || dy == 0) {
            if (onViewPagerListener != null) {
                onViewPagerListener.onPageSelected(view);
            }
        }
        Log.e("zdh", "----------dy" + dy + "---------height" + height);
    }

    /**
     * 监听itemView 滑出recyclerView的回调（完全移除才回调）
     *
     * @param view---》itemView
     */
    @Override
    public void onChildViewDetachedFromWindow(@NonNull View view) {
        Log.e("zdh", "----------onChildViewDetachedFromWindow");

        if (onViewPagerListener != null) {
            onViewPagerListener.onPageRelease(view);
        }
    }

//------------------------------------------监听recyclerView滑动状态 --------------------------------------------------------

    /**
     * SCROLL_STATE_IDLE 闲置
     * SCROLL_STATE_DRAGGING 拖动
     * SCROLL_STATE_SETTLING 惯性滑动
     *
     * @param state 滑动状态
     */

    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            //没有滑动时处理吸附效果
            case RecyclerView.SCROLL_STATE_IDLE:
                //参数layoutManager 对象
                View snapView = pagerSnapHelper.findSnapView(this);
                //判断snapView不为空
                assert snapView != null;
                //判断回调接口不为空-->说明滑动了
                if (onViewPagerListener != null) {
                    onViewPagerListener.onPageSelected(snapView);
                }
                break;
        }


        super.onScrollStateChanged(state);

    }
}
