package com.hfut.lianya.net.api;

import com.hfut.lianya.bean.Deliveries;
import com.hfut.lianya.bean.Fkpb;
import com.hfut.lianya.bean.WorkerDoingTask;
import com.hfut.lianya.bean.WorkerDoneTask;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.base.NetConstant;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IDashboardApiService {
    @GET(NetConstant.GET_PIECE_RATE_WAGE)
    Observable<HttpRespondBody<Double>> getPieceRateWage(@Query("userNo") String userNo);

    @GET(NetConstant.GET_HOURLY_WAGE)
    Observable<HttpRespondBody<Double>> getHourlyWage(@Query("userNo") String userNo);
    @GET(NetConstant.GET_TASK)
    Observable<HttpRespondBody<List<Fkpb>>> getTask(@Query("userNo") String userNo, @Query("state") char state);

    @GET(NetConstant.GET_WORKER_DOING_TASK)
    Observable<HttpRespondBody<List<Fkpb>>> getWorkerDoingTask(@Query("userNo") String userNo);
    @GET(NetConstant.SCAN)
    Observable<HttpRespondBody> scan(@Query("packageId") String packageId, @Query("userNo") String userNo, @Query("operate") char operate);
    @GET(NetConstant.SCAN)
    Observable<HttpRespondBody> complete(@Query("packageId") String packageId, @Query("userNo") String userNo, @Query("operate") char operate);
    @GET(NetConstant.GET_PACKAGE_INFO)
    Observable<HttpRespondBody<Fkpb>> getPackageInfo(@Query("packageId") String packageId);
    @GET(NetConstant.START)
    Observable<HttpRespondBody> start(@Query("packageId") String packageId, @Query("userNo") String userNo, @Query("pauseTime") long pauseTime);
    @GET(NetConstant.GET_DELIVERING)
    Observable<HttpRespondBody<List<Deliveries>>> getDelivering(@Query("userNo") String userNo);
    @GET(NetConstant.GET_FOR_DELIVER)
    Observable<HttpRespondBody<List<Deliveries>>> getForDeliver();
    @GET(NetConstant.TAKE_TASK)
    Observable<HttpRespondBody> takeTask(@Query("packageId") String packageId, @Query("userNo") String userNo);
    @GET(NetConstant.DELIVERED)
    Observable<HttpRespondBody> delivered(@Query("packageId") String packageId, @Query("currentWorkstation") String currentWorkstation);
    @GET(NetConstant.DONE_TASK)
    Observable<HttpRespondBody<List<WorkerDoneTask>>> getDoneTask();
    @GET(NetConstant.DOING_TASK)
    Observable<HttpRespondBody<List<WorkerDoingTask>>> getDoingTask();
    @GET(NetConstant.TO_BE_DONE_TASK)
    Observable<HttpRespondBody<List<Fkpb>>> getToBeDoneTask();
    @GET(NetConstant.BED_DELIVERED)
    Observable<HttpRespondBody> bedDelivered(@Query("packageId") String packageId, @Query("currentWorkstation") String currentWorkstation);
    @FormUrlEncoded
    @POST(NetConstant.POST_TEST)
    Observable<HttpRespondBody> postTest(@Field("s") String s);
}
