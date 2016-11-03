package com.infrastructure.util;


import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Administrator on 2016/10/19.
 */

public class UILUtil {

    public static void  dispaly(String url, ImageView imageView, final int defResId) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                ((ImageView) view).setImageResource(defResId);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                ((ImageView) view).setImageResource(defResId);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                try {
                    String url = (String) view.getTag();
                    if (url != null && url.startsWith("http")) {
                        if (!imageUri.equals(url)) {
                            return;
                        }
                    }
                } catch (ClassCastException e){
                }
                ((ImageView) view).setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

}
