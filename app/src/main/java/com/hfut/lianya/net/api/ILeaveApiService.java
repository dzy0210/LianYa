package com.hfut.lianya.net.api;

import com.hfut.lianya.bean.Leave;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.base.NetConstant;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ILeaveApiService {
    @POST(NetConstant.NEW_LEAVE)
    Observable<HttpRespondBody> submitLeave(@Body Leave leave);
    @POST(NetConstant.EDIT_LEAVE)
    Observable<HttpRespondBody> editLeave(@Body Leave leave);
    @POST(NetConstant.CANCEL_LEAVE)
    Observable<HttpRespondBody> cancelLeave(@Body Leave leave);

    @POST(NetConstant.DEAL_LEAVE)
    Observable<HttpRespondBody> dealLeave(@Body Leave leave);

    @GET(NetConstant.GET_DEALING_LEAVES)
    Observable<HttpRespondBody<List<Leave>>>getDealingLeaves(@Query("userNo") String userNo);

    @GET(NetConstant.GET_DEALING_LEAVES_BY_DEALER)
    Observable<HttpRespondBody<List<Leave>>>getDealingLeavesByDealer(@Query("userNo") String userNo);

    @GET(NetConstant.GET_HISTORY_LEAVES)
    Observable<HttpRespondBody<List<Leave>>>getHistoryLeaves(@Query("userNo") String userNo);

    @GET(NetConstant.GET_HISTORY_LEAVES_BY_DEALER)
    Observable<HttpRespondBody<List<Leave>>> getHistoryLeavesByDealer(@Query("userNo") String userNo);
}
