package com.hfut.lianya.net;

import com.hfut.lianya.net.api.IAbnormalityApiService;
import com.hfut.lianya.net.api.ICommonApiService;
import com.hfut.lianya.net.api.IDashboardApiService;
import com.hfut.lianya.net.api.IEfficiencyApiService;
import com.hfut.lianya.net.api.ILeaveApiService;
import com.hfut.lianya.net.api.INotificationApiService;
import com.hfut.lianya.net.api.IUserApiService;
import com.hfut.lianya.net.base.NetConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static OkHttpClient mOkHttpClient;

    static {
        initOkHttpClient();
    }

    public static IAbnormalityApiService getAbnormalityAPI() {
        return createApi(IAbnormalityApiService.class, NetConstant.BASE_URL);
    }
    public static IDashboardApiService getDashboardAPI() {
        return createApi(IDashboardApiService.class, NetConstant.BASE_URL);
    }
    public static INotificationApiService getNotificationAPI() {
        return createApi(INotificationApiService.class, NetConstant.BASE_URL);
    }
    public static ILeaveApiService getLeaveAPI() {
        return createApi(ILeaveApiService.class, NetConstant.BASE_URL);
    }
    public static IEfficiencyApiService getEfficiencyAPI() {
        return createApi(IEfficiencyApiService.class, NetConstant.BASE_URL);
    }
    public static ICommonApiService getCommonAPI() {
        return createApi(ICommonApiService.class, NetConstant.BASE_URL);
    }
    public static IUserApiService getUserAPI() {
        return createApi(IUserApiService.class, NetConstant.BASE_URL);
    }
    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (Retrofit.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    mOkHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS).
                            addInterceptor(interceptor)
                            .build();
                }
            }
        }
    }
}