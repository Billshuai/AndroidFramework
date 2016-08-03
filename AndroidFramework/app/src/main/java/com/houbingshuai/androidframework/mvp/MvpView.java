package com.houbingshuai.androidframework.mvp;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/8/3.
 */
public interface MvpView {
    /**
     * 为了用户体验
     */

    //加载中
    void showProgress();
    //加载中隐藏
    void hideProgress();
    //加载数据失败
    void setError();
    //加载数据成功
    void setSuccess(Bitmap bitmap);

}
