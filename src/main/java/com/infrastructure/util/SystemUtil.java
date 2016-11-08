package com.infrastructure.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2016/11/8.
 */

public class SystemUtil {

    public void hideInputMethod(Activity context) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static String getAppVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        String packageName = context.getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        String appVersion = packageInfo.versionName;
        return appVersion;
    }
}
