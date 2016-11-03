package com.infrastructure.network;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by Administrator on 2016/10/8.
 */

public class VolleyUtil {

    private static VolleyUtil instance;
    Context context;
    RequestQueue requestQueue;
    public static final int RESPONSE_OK = 0x00;
    public static final int ERROR = 0x01;

    private VolleyUtil(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public static void init(Context context) {
        if (instance == null) {
            synchronized (VolleyUtil.class) {
                instance = new VolleyUtil(context);
            }
        }
    }
    public static VolleyUtil getInstance(Context context) {
        if (instance == null) {
            throw new RuntimeException(context.toString()
                    + " must init VolleyUtil in your Application");
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    /**
     *
     * @param requestCode
     * @param url
     * @param params
     * @param handler
     * @param queue
     */
    public void doPost(final int requestCode, String url, final Map<String,String> params, final Handler handler, RequestQueue queue) {
        //if network not connected
        // handler.setMessage();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Message msg = Message.obtain();
                msg.what = requestCode;
                msg.arg1 = RESPONSE_OK;
                msg.obj = response;
                handler.sendMessage(msg);
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = requestCode;
                msg.arg1 = ERROR;
                handler.sendMessage(msg);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        if (request != null) {
            queue.add(request);
        } else {
            Message msg = Message.obtain();
            msg.what = requestCode;
            msg.arg1 = ERROR;
            handler.sendMessage(msg);
        }

    }
}
