package com.hfut.lianya.net.api;

import com.hfut.lianya.bean.InitData;
import com.hfut.lianya.bean.Lack;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.base.NetConstant;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ICommonApiService {
    @GET(NetConstant.GET_VERSION)
    Observable<HttpRespondBody<Integer>> getVersion();
    @GET(NetConstant.GET_INIT_DATA)
    Observable<HttpRespondBody<InitData>> getInitData();
    @GET(NetConstant.GET_LACK)
    Observable<HttpRespondBody<List<Lack>>>getLack();
//    @GET(NetConstant.VERSION)
//    Call<ResultAppInfo>getAppInfo();
//
//    @POST(NetConstant.SCAN)
//    Call<Integer>getScan();
}
