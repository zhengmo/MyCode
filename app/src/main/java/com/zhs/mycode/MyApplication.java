package com.zhs.mycode;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/12/26.
 * 代表整个软件，全局
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //xUtils3初始化
        x.Ext.init(this);
        x.Ext.setDebug(true);//是否输出debug日志，开启debug会影响性能
    }
}
