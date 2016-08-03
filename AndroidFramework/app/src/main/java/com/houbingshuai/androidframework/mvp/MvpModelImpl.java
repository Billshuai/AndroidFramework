package com.houbingshuai.androidframework.mvp;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2016/8/2.
 */
public class MvpModelImpl implements MvpModel {
    @Override
    public void getData(String url, final ModelListener modelListener) {
        OkHttpClient okHttpClient = new OkHttpClient();
//        RequestBody requestBody =new FormEncodingBuilder().add().build();
        Request request = new Request.Builder().url(url).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    if (modelListener != null) {
                        modelListener.onSuccess(json);
                    } else {
                        modelListener.onError();
                    }
                }
            }
        });
    }
}
