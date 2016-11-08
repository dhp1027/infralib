package com.infrastructure.network;


import com.infrastructure.network.api.FakeApi;
import com.infrastructure.network.api.LoginApi;
import com.infrastructure.network.api.RegisterApi;
import com.infrastructure.network.api.UpdatePhotos;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2016/10/10.
 */

public class Network {

    static FakeApi fakeApi;

    public static String IP = "http://10.251.251.123:8080/";

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.MILLISECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
    //private static GsonConverterFactory converterFactory = GsonConverterFactory.create();
    private static ScalarsConverterFactory converterFactory = ScalarsConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static FakeApi getFakeApi() {
        if (fakeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(IP)
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            fakeApi = retrofit.create(FakeApi.class);
        }
        return fakeApi;
    }

    public static LoginApi getLoginApi() {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(IP)
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
        return retrofit.create(LoginApi.class);
    }

    public static RegisterApi getRegisterApi() {

            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(IP)
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
        return retrofit.create(RegisterApi.class);
    }


    public static UpdatePhotos updatePhotos() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(IP)
                .addConverterFactory(converterFactory)
                .build();
        return retrofit.create(UpdatePhotos.class);
    }






}
