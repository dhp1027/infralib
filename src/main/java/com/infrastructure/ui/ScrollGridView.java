package com.infrastructure.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/10/13.
 */

public class ScrollGridView extends GridView {
    public ScrollGridView(Context context){
        super(context);
    }
    public ScrollGridView(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    public ScrollGridView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int h = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, h);
    }
}

