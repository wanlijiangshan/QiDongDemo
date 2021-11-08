package com.android.qidong;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;


public class MyImageView extends AppCompatImageView {

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(viewWidth, viewWidth * 4 / 3);
    }
}
