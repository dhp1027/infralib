package com.infrastructure.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2016/10/17.
 */

public class UIUtil {

    public static int dpTopx(Activity context, int dp) {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return (int) metrics.density * dp;
    }
}
