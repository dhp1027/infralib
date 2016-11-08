package com.infrastructure.network;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/10/11.
 */

public class NetworkHelper {

    private String json;

    public NetworkHelper(String json) {
        this.json = json;
    }

    public int getResult() {
        try {
            JSONObject object = new JSONObject(json);
            return object.getInt("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getMessage() {
        try {
            JSONObject jo = new JSONObject(json);
            return jo.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getData() {
        try {
            JSONObject jo = new JSONObject(json);
            return jo.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public <T> T getData(Type typeOfT) {
        try {
            JSONObject jo = new JSONObject(json);
            Gson gson = new Gson();
            String data = jo.getString("data");
            try {
                return gson.fromJson(data,typeOfT);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
