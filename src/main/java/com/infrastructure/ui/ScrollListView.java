package com.infrastructure.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/9/24.
 */

public class ScrollListView extends ListView {

    public ScrollListView(Context context){
        super(context);
    }
    public ScrollListView(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    public ScrollListView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int h = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, h);
    }
}
