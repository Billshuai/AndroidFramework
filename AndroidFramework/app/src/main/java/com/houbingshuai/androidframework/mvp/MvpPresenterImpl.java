package com.houbingshuai.androidframework.mvp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.houbingshuai.androidframework.mvc.PictureBean;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class MvpPresenterImpl implements MvpPresenter, MvpModel.ModelListener {

    MvpView mvpView;
    MvpModel mvpModel;

    Gson gson;
    private Bitmap bitm;
    private String imageUrl;

    public MvpPresenterImpl(MvpView mvpView) {
        this.mvpView = mvpView;
        this.mvpModel = new MvpModelImpl();
        gson = new Gson();
    }

    @Override
    public void onSuccess(String result) {

        PictureBean pictureBean = gson.fromJson(result, PictureBean.class);
        List<PictureBean.DataBean> list = pictureBean.getData();
        if (list != null) {
            imageUrl = list.get(0).getImage_url();
            bitm = getHttpBitmap(imageUrl);
        }
        if (mvpView != null) {
            mvpView.setSuccess(bitm);
            mvpView.hideProgress();
        }

    }

    @Override
    public void onError() {
        if (mvpView != null) {
            mvpView.setError();
            mvpView.hideProgress();
        }
    }

    @Override
    public void getData(String url) {
        if (mvpView != null) {
            mvpView.showProgress();
        }
        mvpModel.getData(url, this);
    }

    /**
     * 获取网落图片资源
     *
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;

    }
}
