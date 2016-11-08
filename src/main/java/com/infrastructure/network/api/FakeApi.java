package com.infrastructure.network.api;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/11.
 */

public interface FakeApi {
    @POST("jeecg/appInterfaceCtrl.do?load&method=/user/login")
    Observable<String> fake(@Query("data") String data);

}
