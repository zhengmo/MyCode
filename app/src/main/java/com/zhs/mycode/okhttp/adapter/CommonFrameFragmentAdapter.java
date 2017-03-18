package com.zhs.mycode.okhttp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/12/22.
 * 常用矿建的Fragment
 */

public class CommonFrameFragmentAdapter extends BaseAdapter {

    private final Context mContext;
    private final String[] mDatas;

    public CommonFrameFragmentAdapter(Context context, String[] datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return (mDatas ==null)?0:mDatas.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        textView.setText(mDatas[position]);
        textView.setPadding(50,10,0,10);
        return textView;
    }
}
