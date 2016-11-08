package com.infrastructure.network;

/**
 * Created by Administrator on 2016/10/24.
 */

public interface ProgressListener {
    /**
     * @param progress 已下载或已上传字节数
     * @param total 总字节数
     * @param done 是否完成
     */
    void onProgress(long progress, long total, boolean done);
}
