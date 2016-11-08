package com.infrastructure.network.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;


/**
 * Created by Administrator on 2016/10/20.
 */

public interface UpdatePhotos {
    @Multipart
    @POST("") //jeecg/appInterfaceCtrl.do?upload
    Call<String> upload(@Url String url,
                        @Part("method") String method,
                        @Part("data") String data,
                        @Part("photos\"; filename=\"upload.jpg") RequestBody photos);
}
