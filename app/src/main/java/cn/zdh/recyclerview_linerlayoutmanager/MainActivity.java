package cn.zdh.recyclerview_linerlayoutmanager;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<VideoBean> list = new ArrayList<>();
    private MyLinearLayoutManager myLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        initStatus();

        initView();

        initListener();

    }

    private void initView() {
        myLinearLayoutManager = new MyLinearLayoutManager(this);
        recyclerView.setLayoutManager(myLinearLayoutManager);

        list.add(new VideoBean(R.raw.video_1, R.mipmap.img_video_1));
        list.add(new VideoBean(R.raw.video_2, R.mipmap.img_video_2));
        list.add(new VideoBean(R.raw.video_1, R.mipmap.img_video_1));
        list.add(new VideoBean(R.raw.video_2, R.mipmap.img_video_2));
        list.add(new VideoBean(R.raw.video_1, R.mipmap.img_video_1));
        list.add(new VideoBean(R.raw.video_2, R.mipmap.img_video_2));
        myAdapter = new MyAdapter(list, this);
        recyclerView.setAdapter(myAdapter);


    }


    @Override
    protected void onPause() {
        super.onPause();

        
    }

    /**
     * 监听
     */
    private void initListener() {
/**
 * 监听item的状态变化
 * 注意这个方法有bug--》当LayoutManager 没有初始化完成这个监听有问题
 */
//        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
//            @Override
//            public void onChildViewAttachedToWindow(@NonNull View view) {
//                //item滑入进recyclerView
//            }
//
//            @Override
//            public void onChildViewDetachedFromWindow(@NonNull View view) {
//                //item滑出recyclerView
//
//
//            }
//        });


        //监听itemView 滑入滑出回调
        myLinearLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onPageRelease(View itemView) {
                //滑出
                stopVideo(itemView);

            }

            @Override
            public void onPageSelected(View itemView) {
                //滑入
                playVideo(itemView);

            }
        });
    }


    /**
     * 开始播放
     *
     * @param itemView 适配器的itemView
     */
    public void playVideo(View itemView) {
        final ImageView ivPreload = itemView.findViewById(R.id.iv_preload);
        final ImageView ivPlay = itemView.findViewById(R.id.iv_play);
        final MyVideoView myVideoView = itemView.findViewById(R.id.videoView);
        //开始播放视频
        myVideoView.start();
        //视频预加载好后回调
        myVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {

                //设置是否循环播放
                mp.setLooping(true);
                //设置预加载图片 隐藏
                ivPreload.animate().alpha(0).setDuration(200).start();
                return false;

            }
        });

        //监听点暂停 播放按钮 点击事件
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断视频是否在播放 播放就停止播放  停止播放就播放
                boolean playing = myVideoView.isPlaying();
                if (playing) {
                    //视频在播放

                    //暂停播放
                    myVideoView.pause();
                    //设置 暂停播放按钮显示
                    ivPlay.animate().alpha(1).start();

                } else {
                    //视频停止播放

                    //开始播放
                    myVideoView.start();
                    //设置播放按钮隐藏
                    ivPlay.animate().alpha(0).start();


                }


            }
        });


    }


    /**
     * 停止播放（播放完了）
     *
     * @param itemView 适配器的itemView
     */
    public void stopVideo(View itemView) {
        ImageView ivPreload = itemView.findViewById(R.id.iv_preload);
        ImageView ivPlay = itemView.findViewById(R.id.iv_play);
        MyVideoView myVideoView = itemView.findViewById(R.id.videoView);

        //alpha透明度0隐藏 ；透明度1显示
        //设置  预加载图片   显示
        ivPreload.animate().alpha(1f).start();
        //设置  暂停播放按钮  隐藏
        ivPlay.animate().alpha(0f).start();
        //停止播放视频
        myVideoView.stopPlayback();

    }


    /**
     * Java代码实现沉浸式
     */
    private void initStatus() {
        //版本大于等于4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //获取到状态栏设置的两条属性
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            //在4.4之后又有两种情况  第一种 4.4-5.0   第二种 5.0以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //第二种 5.0以上
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
                window.setStatusBarColor(0);
            } else {
                //第一种 4.4-5.0
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentStatus | flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }

    }

}
