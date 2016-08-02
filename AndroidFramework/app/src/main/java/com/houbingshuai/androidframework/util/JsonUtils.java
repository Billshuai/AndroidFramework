package com.houbingshuai.androidframework.util;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by houge on 2015/11/6.
 * Json解析工具类
 */
public class JsonUtils {
    public static <T> List<T> toListBean(String text, Class<T> clz){
        if(TextUtils.isEmpty(text)){
            return null;
        }
        return JSON.parseArray(text, clz);
    }

    public static boolean getBooleanFromJson(String text, String key){
        if(TextUtils.isEmpty(text)){
            return false;
        }else{
            String result=JSON.parseObject(text).getString(key);
            return Boolean.valueOf(result);
        }
    }

    public static String getStringFromJson(String text, String key){
        if(TextUtils.isEmpty(text)){
            return null;
        }else{
            return JSON.parseObject(text).getString(key);
        }
    }

    public static JSONObject getJSONObjectFromJson(String text, String key){
        if(TextUtils.isEmpty(text)){
            return null;
        }else{
            return JSON.parseObject(text).getJSONObject(key);
        }
    }

    public static JSONArray getJSONArrayFromJson(String text, String key){
        if(TextUtils.isEmpty(text)){
            return null;
        }else{
            return JSON.parseObject(text).getJSONArray(key);
        }
    }

    public static int getIntFromJson(String text, String key){
        if(TextUtils.isEmpty(text)){
            return 0;
        }else{
            return JSON.parseObject(text).getIntValue(key);
        }
    }

    public static long getLongFromJson(String text, String key){
        if(TextUtils.isEmpty(text)){
            return 0;
        }else{
            return JSON.parseObject(text).getLongValue(key);
        }
    }

    public static <T> T toBean(String text, Class<T> clz){
        if(TextUtils.isEmpty(text)){
            return null;
        }
        return JSON.parseObject(text, clz);
    }
}
