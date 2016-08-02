package com.houbingshuai.androidframework.mvc;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.houbingshuai.androidframework.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2016/7/28.
 * <p/>
 * Android的控制层的重任通常落在了众多的Activity的肩上。
 * 这句话也就暗含了不要在Activity中写代码，
 * 要通过Activity交割Model业务逻辑层处理，
 * 这样做的另外一个原因是Android中的Actiivity的响应时间是5s，
 * 如果耗时的操作放在这里，程序就很容易被回收掉。
 */
public class ControllerActivtiy extends Activity {
    Model model;
    String url = "http://image.baidu.com/channel/listjson?pn=1&rn=1&tag1=美女&tag2=全部&ie=utf8";

    private Button button;
    private ImageView imageView;
    private String imageUrl;

    Gson gson;

    private Bitmap bitm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
        initData();
    }

    public void initView() {
        button = findView(R.id.button);
        imageView = findView(R.id.imageView);
    }

    public void initData() {
        gson = new Gson();
        model = new ModelUrl();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getData(url, new ModelListener() {
                    @Override
                    void onSuccess(String result) {
                        super.onSuccess(result);
                        PictureBean pictureBean = gson.fromJson(result, PictureBean.class);
                        List<PictureBean.DataBean> list = pictureBean.getData();
                        if (list != null) {
                            imageUrl = list.get(0).getImage_url();
                            bitm = getHttpBitmap(imageUrl);
                            Message message = new Message();
                            handler.sendEmptyMessage(0);
                        }
                        //得到可用的图片
                    }

                    @Override
                    void onError() {
                        super.onError();
                    }
                });
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            imageView.setImageBitmap(bitm);
        }
    };

    /**
     * 获取网落图片资源
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
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
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;

    }


    protected <T extends View> T findView(int id) {
        //return返回view时,加上泛型T
        return (T) findViewById(id);
    }
}
