package com.infrastructure.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.infrastructure.R;
import com.infrastructure.util.LogUtil;
import com.infrastructure.util.UIUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/11/1.
 */

public class RotationViewPager extends ViewPager{
    final static String tag = RotationViewPager.class.getName();
    Context context;
    List<View> dots = new ArrayList<>();

    Timer timer = new Timer();
    long interval = 5000;
    int mRotationCount = 1;
    boolean enableRotation = true;

    public RotationViewPager(Context context) {
        super(context);
        this.context = context;
    }

    public RotationViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        super.addOnPageChangeListener(listener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (getAdapter().getCount() == 1) {
                return;
            }
            mRotationCount++;
            if (mRotationCount >= getAdapter().getCount()) {
                mRotationCount = 0;
            }
            Calendar calendar = Calendar.getInstance();
            int s = calendar.get(Calendar.SECOND);
            LogUtil.e(tag, "====>mRotationCount=" + mRotationCount + ",t=" + s);
            post(new Runnable() {
                @Override
                public void run() {

                    if (enableRotation) {
                        setCurrentItem(mRotationCount);
                    }
                }
            });
        }
    };
    public void startRotation() {
        //timer.cancel();
        try {
            Field field;
            field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            MyScroller mScroller = new MyScroller(getContext(), new AccelerateDecelerateInterpolator());
            field.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        timer.schedule(task,0,interval);
    }

    public void stopRatation() {
        timer.cancel();
    }

    public void setRotationInterval(long mills) {
        interval = mills;
    }

    private View createIndicateView(Context context) {
        View view = new View(context);
        int h = UIUtil.dpTopx((Activity) context, 7);
        int pad = UIUtil.dpTopx((Activity) context, 2);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(h, h);
        lp.leftMargin = lp.rightMargin = pad;
        view.setLayoutParams(lp);
        view.setBackgroundResource(R.drawable.icon_dot_white);
        return view;
    }

//    public void initAdapter(List<View> views) {
//        setAdapter(new RotationAdapter(context, views));
//        enableRotation = false;
//    }

    public void initRotationAdapter(List<View> views) {
        if (views.size() >= 4) {
            enableRotation = true;
        } else {
            enableRotation = false;
        }
        setAdapter(new RotationAdapter(context, views));
    }

    public void initIndicator(LinearLayout layout) {
        layout.removeAllViews();
        dots.clear();
        int count = getAdapter().getCount();
        if (count <= 1) {
            return;
        }
        int dotCount = enableRotation ? getAdapter().getCount() - 2 : getAdapter().getCount();
        for (int i = 0; i < dotCount; i++) {
            View view = createIndicateView(context);
            dots.add(view);
            layout.addView(view);
        }

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                LogUtil.e(tag, "====>onPageSelected pos=" + position);
//                if (enableRotation == true) {
//                    int truthCount = getAdapter().getCount() - 2;
//                    if (position == 0) {
//                        setCurrentItem(truthCount, false);
//                        setIndicator(truthCount);
//                        mRotationCount = truthCount;
//                    } else if(position > truthCount) {
//                        setIndicator(0);
//                        setCurrentItem(1,false);
//                        mRotationCount = 1;
//                    }else {
//                        setIndicator(position - 1);
//                        mRotationCount = position - 1;
//                    }
//                }

//                if (enableRotation == true) {
//                    int count = getAdapter().getCount();
//                    if (mRotationCount == 0) {
//                        setCurrentItem(count-2, false);
//                        setIndicator(count-3);
//                        mRotationCount = count-2;
//                    } else if(mRotationCount >= count -1) {
//                        setIndicator(0);
//                        setCurrentItem(1,false);
//                        mRotationCount = 1;
//                    }else {
//                        setIndicator(mRotationCount - 1);
//                        //mRotationCount = mRotationCount - 1;
//                    }
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state != 0) {
                    return;
                }
                LogUtil.e(tag, "====>onPageScrollStateChanged state=" + state);
                if (enableRotation == true) {
                    int count = getAdapter().getCount();
                    if (mRotationCount == 0) {
                        setCurrentItem(count-2, false);
                        setIndicator(count-3);
                        mRotationCount = count-2;
                    } else if(mRotationCount >= count -1) {
                        setIndicator(0);
                        setCurrentItem(1,false);
                        mRotationCount = 1;
                    }else {
                        setIndicator(mRotationCount - 1);
                        //mRotationCount = mRotationCount - 1;
                    }
                }
            }


        });
        setCurrentItem(1);
        setIndicator(0);
    }

    private void setIndicator(int index) {
        if (index >= dots.size()) {
            return;
        }
        for (View dot : dots) {
            dot.setBackgroundResource(R.drawable.icon_dot_white);
        }
        dots.get(index).setBackgroundResource(R.drawable.icon_dot_grey);
    }

    public static class RotationAdapter extends PagerAdapter {
        List<View> views;

        public RotationAdapter(Context context,List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }

    class MyScroller extends Scroller {
        private int mDuration = 500;

        public MyScroller(Context context) {
            super(context);
        }

        public MyScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }
}
