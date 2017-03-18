package com.zhs.mycode.xutils.annotation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhs.mycode.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/12/26.
 * Fragment
 */
@ContentView(R.layout.fragment_demo)
public class DemoFragment extends Fragment {

    @ViewInject(R.id.btn_fragment)
    private Button button;
    @ViewInject(R.id.tv_text)
    private TextView textView;

    //上下文
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //给context赋值
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return x.view().inject(this,inflater,container);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"我是按钮，我在Fragment中被初始化，点击了",Toast.LENGTH_SHORT).show();
            }
        });
        textView.setText("我也被初始化了");
    }
}
