package com.zhs.mycode.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zhs.mycode.okhttp.base.BaseFragment;


/**
 * Created by Administrator on 2016/12/21.
 * 其他的Fragment
 */

public class OtherFragment extends BaseFragment {

    private static final String TAG = OtherFragment.class.getSimpleName();
    private TextView view;

    @Override
    protected View initView() {
        Log.i(TAG,"其他Fragment的页面被初始化了。。。");
        view = new TextView(mContext);
        view.setTextSize(20);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(Color.RED);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.i(TAG,"其他Fragment的数据被初始化了。。。");
        view.setText("其他框架集合");
    }
}
