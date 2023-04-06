package com.hfut.lianya.net.api;

import com.hfut.lianya.bean.SingleEfficiency;
import com.hfut.lianya.bean.SummarizedEfficiency;
import com.hfut.lianya.net.HttpRespondBody;
import com.hfut.lianya.net.base.NetConstant;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface IEfficiencyApiService {

    @GET(NetConstant.SUMMARIZED)
    Observable<HttpRespondBody<List<SummarizedEfficiency>>> getSummarizedEfficiency();
    @GET(NetConstant.SINGLE)
    Observable<HttpRespondBody<List<SingleEfficiency>>> getSingleEfficiency();
}
