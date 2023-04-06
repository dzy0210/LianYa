package com.hfut.lianya.net.api;

import com.hfut.lianya.bean.Abnormality;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.base.NetConstant;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IAbnormalityApiService {
    @POST(NetConstant.SUBMIT_ABNORMALITY)
    Observable<HttpRespondBody> submit(@Body Abnormality abnormality);
    @POST(NetConstant.EDIT_ABNORMALITY)
    Observable<HttpRespondBody> editAbnormality(@Body Abnormality abnormality);
    @POST(NetConstant.CANCEL_ABNORMALITY)
    Observable<HttpRespondBody> cancelAbnormality(@Body Abnormality abnormality);
    @GET(NetConstant.GET_DEALING_ABNORMALITY)
    Observable<HttpRespondBody<List<Abnormality>>> getDealingAbnormality(@Query("userNo") String userNo);
    @GET(NetConstant.GET_HISTORY_ABNORMALITY)
    Observable<HttpRespondBody<List<Abnormality>>> getHistoryAbnormality(@Query("userNo") String userNo);
    @GET(NetConstant.GET_DEALING_ABNORMALITY_BY_DEALER)
    Observable<HttpRespondBody<List<Abnormality>>> getDealingAbnormalityByDealer(@Query("userNo") String userNo);
    @GET(NetConstant.GET_HISTORY_ABNORMALITY_BY_DEALER)
    Observable<HttpRespondBody<List<Abnormality>>> getHistoryAbnormalityByDealer(@Query("userNo") String userNo);
}
