package com.houbingshuai.androidframework.mvc;

/**
 * Created by Administrator on 2016/7/28.
 * 我们针对业务模型，建立的数据结构和相关的类，就可以理解为AndroidApp的Model，
 * Model是与View无关，而与业务相关的。对数据库的操作、对网络等的操作都应该在Model里面处理，
 * 当然对业务计算等操作也是必须放在的该层的。就是应用程序中二进制的数据。
 */
public interface Model {
    void getData(String url,ModelListener modelListener);
}
