package com.zhs.mycode.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zhs.mycode.R;
import com.zhs.mycode.json.activity.NativeJsonPraseActivity;
import com.zhs.mycode.okhttp.activity.OKHttpActivity;
import com.zhs.mycode.okhttp.adapter.CommonFrameFragmentAdapter;
import com.zhs.mycode.okhttp.base.BaseFragment;
import com.zhs.mycode.recyclerview.RecyclerViewActivity;
import com.zhs.mycode.xutils.XUtils3MainActivity;


/**
 * Created by Administrator on 2016/12/21.
 * 常用框架的Fragment
 */

public class CommonFrameFragment extends BaseFragment {

    private static final String TAG = CommonFrameFragment.class.getSimpleName();
    private ListView mListview;
    String[] datas;
    private CommonFrameFragmentAdapter adapter;


    @Override
    protected View initView() {
        Log.i(TAG,"常用框架Fragment的页面被初始化了。。。");
        View view = View.inflate(mContext, R.layout.fragment_common_frame,null);
        mListview = (ListView) view.findViewById(R.id.listview);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = datas[position];
                //根据点击的item项跳转到不同的模块
                /*switch (data.toLowerCase()){
                    case "okhttp":
                        Intent intent = new Intent(mContext,OKHttpActivity.class);
                        mContext.startActivity(intent);
                        break;
                }*/
                if (data.toLowerCase().equals("okhttp")){
                    Intent intent = new Intent(mContext,OKHttpActivity.class);
                    mContext.startActivity(intent);
                } else if (data.toLowerCase().equals("nativejsonparse")) {
                    //点击条目跳转到手动解析json页面
                    Intent intent = new Intent(mContext, NativeJsonPraseActivity.class);
                    mContext.startActivity(intent);
                }else if (data.toLowerCase().equals("xutils3")) {
                    //点击条目跳转到手动解析json页面
                    Intent intent = new Intent(mContext, XUtils3MainActivity.class);
                    mContext.startActivity(intent);
                }else if (data.toLowerCase().equals("recyclerview")) {
                    //点击条目跳转到手动解析json页面
                    Intent intent = new Intent(mContext, RecyclerViewActivity.class);
                    mContext.startActivity(intent);
                }
                Toast.makeText(mContext,data,Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }


    @Override
    protected void initData() {
        super.initData();
        //准备数据
        datas = new String[]{
                "RecyclerView",
                "OKHttp",
                "nativeJsonParse",
                "Gson",
                "FastJson",
                "xUtils3",
                "JSOUP",
                "Retrofit2",
                "Fresco",
                "Glide",
                "greenDao",
                "RxJava",
                "volley",
                "picasso",
                "evenBus",
                "jcvideoplayer",
                "pulltorefresh",
                "Expandablelistview",
                "UniversalVideoView",
                "Kotlin",
                "....."};
        Log.i(TAG,"常用框架Fragment的数据被初始化了。。。");
        //设置适配器
        adapter = new CommonFrameFragmentAdapter(mContext,datas);
        //关联适配器
        mListview.setAdapter(adapter);
    }

}
