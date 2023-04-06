package com.hfut.lianya.net;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.hfut.lianya.GlobalApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private String token;
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        SharedPreferences sharedPreferences = GlobalApplication.getInstance().getSharedPreferences("user", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        Request request = chain.request()
                .newBuilder()
                .addHeader("token", token)
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}
