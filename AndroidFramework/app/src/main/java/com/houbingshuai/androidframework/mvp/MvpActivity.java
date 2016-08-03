package com.houbingshuai.androidframework.mvp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.houbingshuai.androidframework.R;
import com.houbingshuai.androidframework.mvc.ModelListener;
import com.houbingshuai.androidframework.mvc.PictureBean;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class MvpActivity extends Activity implements MvpView {

    MvpPresenter mvpPresenter;
    String url = "http://image.baidu.com/channel/listjson?pn=1&rn=1&tag1=美女&tag2=全部&ie=utf8";

    private Button button;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Bitmap bitmap;

    Message message1,message2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_activity);
        initView();
        initData();
    }

    public void initView() {
        button = findView(R.id.button);
        imageView = findView(R.id.imageView);
        progressBar = findView(R.id.progressbar);
    }

    public void initData() {
        mvpPresenter = new MvpPresenterImpl(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.getData(url);
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    imageView.setImageBitmap(bitmap);
                    break;
                case 2:
                    progressBar.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        message2 = handler.obtainMessage();
        message2.what = 2;
        handler.sendMessage(message2);
    }

    @Override
    public void setError() {
        button.setText("Error");
    }

    @Override
    public void setSuccess(Bitmap bitmap) {
        this.bitmap = bitmap;
        message1 = handler.obtainMessage();
        message1.what = 1;
        handler.sendMessage(message1);
    }


    protected <T extends View> T findView(int id) {
        //return返回view时,加上泛型T
        return (T) findViewById(id);
    }
}
