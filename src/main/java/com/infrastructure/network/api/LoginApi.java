package com.infrastructure.network.api;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/10.
 */

public interface LoginApi {
    //@POST("")
    //<String> login(@Url String url);
    @POST("jeecg/appInterfaceCtrl.do?load&method=/user/login")
    Observable<String> login(@Query("data") String data);
}
