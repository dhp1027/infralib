package com.infrastructure.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/8.
 */

public class ToastUtil {

    public static void showText(Context context,CharSequence text) {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
