package com.infrastructure.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * Created by Administrator on 2016/10/19.
 */

public class UILUtil {


    public static void initImageLoader(Context context) {

        DisplayImageOptions defOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // not default
                .cacheOnDisk(true) // not default
                .build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                //.memoryCacheExtraOptions(480, 800)/*解析图片时候使用的最大尺寸，默认为屏幕设备宽高*/
                //.diskCacheExtraOptions(480, 800, null)/*从网络下载图片后保存到磁盘时使用的图片尺寸及压缩方法，如果不设置则保存原始图片*/
                //.threadPoolSize(3)/*线程池的大小，默认值为3,注意不要设置的过大，过大之后会有OOM问题*/
                //.threadPriority(Thread.NORM_PRIORITY - 1)/*设置线程的优先级别：5-1*/
                /*
                * tasksProcessingOrder:设置图片加载和显示队列处理的类型 默认为QueueProcessingType.
                FIFO注:如果设置了taskExecutor或者taskExecutorForCachedImages 此设置无效
                */
                //.tasksProcessingOrder(QueueProcessingType.FIFO)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))/*设置内存缓存 默认为一个当前应用可用内存的1/8大小的LruMemoryCache*/
                //.memoryCacheSize(2 * 1024 * 1014)/*设置内存缓存的最大大小 默认为一个当前应用可用内存的1/8    */
                .memoryCacheSizePercentage(13)/*设置内存缓存最大大小占当前应用可用内存的百分比 默认为一个当前应用可用内存的1/8*/
                .diskCache(new UnlimitedDiskCache(StorageUtils.getCacheDirectory(context.getApplicationContext())))/*默认为StorageUtils.getCacheDirectory(getApplicationContext()),即/mnt/sdcard/android/data/包名/cache/*/
                .diskCacheSize(50 * 1024 * 1024)/*设置硬盘缓存的最大大小*/
                .diskCacheFileCount(100)/*设置硬盘缓存的文件的最多个数*/
                //.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())/*设置硬盘缓存文件名生成规范*/
                //.imageDownloader(new BaseImageDownloader(context))/*设置图片下载器*/
                //.imageDecoder(DefaultConfigurationFactory.createImageDecoder(false))/*设置图片解码器*/
                .defaultDisplayImageOptions(defOptions)/*设置默认的图片显示选项,display时没有指定就使用该optionss*/
                .denyCacheImageMultipleSizesInMemory()/*不缓存图片的多种尺寸在内存中*/
                //.writeDebugLogs() /*打印调试Log,注意上线之前要去掉这句话*/
                .imageDownloader(
                        new BaseImageDownloader(context, 5 * 1000, 30 * 1000)/*（connectTimeout,readTimeout）超时时间*/
                )/*图片下载器的设置*/
                .build();
        ImageLoader.getInstance().init(configuration);/*使用基本配置信息初始化ImageLoader*/
    }

    /**
     * @param url
     * @param imageView
     * @param defResId
     */
    public static void dispaly(String url, ImageView imageView, final int defResId) {
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
                } catch (ClassCastException e) {
                }
                ((ImageView) view).setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    public static void dispaly(String url, ImageView imageView, DisplayImageOptions options) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url,imageView,options);
    }

    public static void dispaly(String url, ImageView imageView, final int defResId, ImageSize size, final boolean preventDislocation) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, new ImageViewAware(imageView), null, size, new ImageLoadingListener() {
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
                if (preventDislocation) {
                    try {
                        String url = (String) view.getTag();
                        if (url != null && url.startsWith("http")) {
                            if (!imageUri.equals(url)) {
                                return;
                            }
                        }
                    } catch (ClassCastException e) {

                    }
                }
                ((ImageView) view).setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        },null);
    }

    /**
     * 从内存卡中异步加载本地图片
     *
     * @param uri
     * @param imageView
     */
    public void displayFromSDCard(String uri, ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        ImageLoader.getInstance().displayImage("file://" + uri, imageView);
    }

    /**
     * 从assets文件夹中异步加载图片
     *
     * @param imageName 图片名称，带后缀的，例如：1.png
     * @param imageView
     */
    public void dispalyFromAssets(String imageName, ImageView imageView,int defResId) {
        // String imageUri = "assets://image.png"; // from assets
        String url = "assert://" + imageName;
        ImageLoader.getInstance().displayImage("assets://" + imageName,
                imageView);
        dispaly(url,imageView,defResId);
    }
    //6225 7687 6269 8221
    /**
     * 从drawable中异步加载本地图片
     *
     * @param imageId
     * @param imageView
     */
    public void displayFromDrawable(int imageId, ImageView imageView) {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        ImageLoader.getInstance().displayImage("drawable://" + imageId,
                imageView);
    }

    /**
     * 从内容提提供者中抓取图片
     */
    public void displayFromContent(String uri, ImageView imageView) {
        // String imageUri = "content://media/external/audio/albumart/13"; //
        // from content provider
        ImageLoader.getInstance().displayImage("content://" + uri, imageView);
    }
}
