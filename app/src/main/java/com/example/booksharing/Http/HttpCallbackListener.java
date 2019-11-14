package com.example.booksharing.Http;

/*
 *@author gregorio
 *期用回调机制
 * onFinnish()方法表示服务器响应成功调用
 * onError()便是进行网络操作时出现错误时调用
 *
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
