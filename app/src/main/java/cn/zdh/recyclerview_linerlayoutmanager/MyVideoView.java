package cn.zdh.recyclerview_linerlayoutmanager;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class MyVideoView extends VideoView {
    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(w,h);


    }
}
