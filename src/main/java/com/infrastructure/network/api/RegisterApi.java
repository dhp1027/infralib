package com.infrastructure.network.api;

import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/11.
 */

public interface RegisterApi {
    @POST("")
    Observable<String> register(@Url String url);
}
