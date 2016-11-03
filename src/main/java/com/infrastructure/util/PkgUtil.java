package com.infrastructure.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2016/10/28.
 */

public class PkgUtil {

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
