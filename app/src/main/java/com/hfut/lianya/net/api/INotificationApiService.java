package com.hfut.lianya.net.api;

import com.hfut.lianya.bean.Notification;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.base.NetConstant;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface INotificationApiService {
    @GET(NetConstant.NOTIFICATION_NUM)
    Observable<HttpRespondBody<Integer>> getNotificationNum(@Query("userNo") String userNo, @Query("userType") char userType);

    @GET(NetConstant.GET_NOTIFICATION)
    Observable<HttpRespondBody<List<Notification>>>getNotification(@Query("userNo") String userNo, @Query("userType") char userType, @Query("state") char state);

    @GET(NetConstant.READ_NOTIFICATION)
    Observable<HttpRespondBody>readNotification(@Query("id") int id);
    
//    @GET(NetConstant.VERSION)
//    Call<ResultAppInfo>getAppInfo();
//
//    @POST(NetConstant.SCAN)
//    Call<Integer>getScan();
}
