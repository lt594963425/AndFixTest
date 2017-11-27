package com.mvp.lt.andfixtest;


import android.app.Application;

/**
 * 工程名 ： BaiLan
 * 包名   ： com.example.ggxiaozhi.store.the_basket.base
 * 作者名 ： 志先生_
 * 日期   ： 2017/8/22
 * 时间   ： 20:40
 * 功能   ： 全局Appcation 进行全局统一配置
 */

public class App extends Application {
    private static App context ;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this ;
    }
    public static App getContext(){
        return context;
    }

}
