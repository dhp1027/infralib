package com.infrastructure.network;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/11/3.
 */

public abstract class ApiSubscriber<T> extends Subscriber<T> {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    //出错提示
    private final String networkMsg;
    private final String parseMsg;
    private final String unknownMsg;


    protected ApiSubscriber(String networkMsg, String parseMsg, String unknownMsg) {
        this.networkMsg = networkMsg;
        this.parseMsg = parseMsg;
        this.unknownMsg = unknownMsg;
    }

    protected ApiSubscriber() {
        this.networkMsg = "网络错误";
        this.parseMsg = "解析错误";
        this.unknownMsg = "未知错误";
    }

    @Override
    public void onError(Throwable e) {
        Throwable throwable = e;
        //获取最根源的异常
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }

        ApiException ex;
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex = new ApiException(e, httpException.code());
                    ex.setDisplayMessage(networkMsg);  //均视为网络错误
                    onError(ex);
                    break;
            }
        } else if (e instanceof SocketTimeoutException) {
            ex = new ApiException(e, ApiException.TIMEOUT_ERROR);
            ex.setDisplayMessage("连接超时");
            onError(ex);
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ApiException.CONNECT_ERROR);
            ex.setDisplayMessage("连接失败");
            onError(ex);
        } else if (e instanceof UnknownHostException) {
            ex = new ApiException(e, ApiException.UNKNOWNHOST_ERROR);
            ex.setDisplayMessage("网络错误");
            onError(ex);
        } else if (e instanceof ResultException) {
            ResultException resultException = (ResultException) e;
            ex = new ApiException(resultException, resultException.getErrCode());
            onError(ex);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(e, ApiException.PARSE_ERROR);
            ex.setDisplayMessage(parseMsg);            //均视为解析错误
            onError(ex);
        } else {
            ex = new ApiException(e, ApiException.UNKNOWN);
            ex.setDisplayMessage(unknownMsg);          //未知错误
            onError(ex);
        }
    }


    /**
     * 错误回调
     */
    protected abstract void onError(ApiException ex);

    /**
     * 权限错误，需要实现重新登录操作
     */
    //protected abstract void onPermissionError(ApiException ex);

    /**
     * 服务器返回的错误
     */
    //protected abstract void onResultError(ApiException ex);

    @Override
    public void onCompleted() {

    }


}
