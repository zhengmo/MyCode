package com.zhs.mycode.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zhs.mycode.okhttp.base.BaseFragment;


/**
 * Created by Administrator on 2016/12/21.
 * 第三方的Fragment
 */

public class ThirdPartyFragment extends BaseFragment {

    private static final String TAG = ThirdPartyFragment.class.getSimpleName();
    private TextView view;

    @Override
    protected View initView() {
        Log.i(TAG,"第三方Fragment的页面被初始化了。。。");
        view = new TextView(mContext);
        view.setTextSize(20);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(Color.RED);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.i(TAG,"第三方Fragment的数据被初始化了。。。");
        view.setText("第三方框架集合");
    }
}
