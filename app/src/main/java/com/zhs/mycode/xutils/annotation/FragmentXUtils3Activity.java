package com.zhs.mycode.xutils.annotation;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.zhs.mycode.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_fragment_xutils3)
public class FragmentXUtils3Activity extends FragmentActivity {

    @ViewInject(R.id.tv_title)
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_xutils3);
        x.view().inject(this);

//        //在Fragment中使用注解
        textview.setText("在Fragment中使用注解");

        //1.得到FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        //2.开启事物
        FragmentTransaction tt = fm.beginTransaction();
        //3.替换Fragment
        tt.replace(R.id.fl_content,new DemoFragment());
        //4.提交
        tt.commit();
    }
}
