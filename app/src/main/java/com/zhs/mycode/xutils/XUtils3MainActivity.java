package com.zhs.mycode.xutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhs.mycode.R;
import com.zhs.mycode.xutils.annotation.FragmentXUtils3Activity;
import com.zhs.mycode.xutils.net.XUtils3NetActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_xutils3_main)
public class XUtils3MainActivity extends Activity {

    @ViewInject(R.id.tv_title)
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_xutils3_main);
//        x.view().inject(XUtils3MainActivity.this);
        x.view().inject(this);

        //设置标题
        textView.setText("xUtils3的使用");
    }


    @Event({R.id.btn_annotation,R.id.btn_net,R.id.btn_image,R.id.btn_image_list})
    private void onClickEvent(View view){
        switch (view.getId()){
            case R.id.btn_annotation://注解模块
                Toast.makeText(this,"注解模块被点击了",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, FragmentXUtils3Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_net://联网模块
                Toast.makeText(this,"联网模块被点击了",Toast.LENGTH_SHORT).show();
                intent = new Intent(this, XUtils3NetActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_image://加载图片
                Toast.makeText(this,"加载图片被点击了",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_image_list://加载图片列表
                Toast.makeText(this,"加载图片列表被点击了",Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
