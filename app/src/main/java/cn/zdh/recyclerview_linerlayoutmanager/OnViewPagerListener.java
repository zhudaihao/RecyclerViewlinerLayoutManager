package cn.zdh.recyclerview_linerlayoutmanager;

import android.view.View;

/**
 * 视频播放 /停止播放  回调接口
 */
public interface OnViewPagerListener {

    //停止播放
    void onPageRelease(View itemView);

    //播放
    void onPageSelected(View itemView);
}
