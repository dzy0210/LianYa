package com.hfut.lianya.net.api;


import com.hfut.lianya.bean.Authentication;
import com.hfut.lianya.bean.User;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.base.NetConstant;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IUserApiService {
    @POST(NetConstant.LOGIN)
    Observable<HttpRespondBody<Authentication>> login(@Body User user);
}
