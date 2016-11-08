package com.infrastructure.network;

/**
 * Created by Administrator on 2016/11/3.
 */

public class ApiException extends Exception {
    private final int code;
    private String displayMessage;

    public static final int UNKNOWN = 1000;
    public static final int PARSE_ERROR = 1001;
    public static final int TIMEOUT_ERROR = 1002;
    public static final int CONNECT_ERROR = 1003;
    public static final int UNKNOWNHOST_ERROR = 1004;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String msg) {
        this.displayMessage = msg + "(code:" + code + ")";
    }
}
