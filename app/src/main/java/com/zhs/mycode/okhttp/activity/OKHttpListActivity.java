package com.zhs.mycode.okhttp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhs.mycode.R;
import com.zhs.mycode.okhttp.adapter.OKHttpListAdapter;
import com.zhs.mycode.okhttp.domain.DataBean;
import com.zhs.mycode.okhttp.utils.CacheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;


/**
 * Created by Administrator on 2016/12/24.
 * 在列表中显示图片
 */
public class OKHttpListActivity extends Activity {

    private static final String TAG = OKHttpListActivity.class.getSimpleName();
    private OKHttpListAdapter adapter;

    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_nodata)
    TextView tvNodata;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttplist);
        ButterKnife.bind(this);

        getDataFromNet();


    }

    private void getDataFromNet() {
        url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";

        //得到缓存的数据
        String saveJson = CacheUtils.getString(this,url);
        if (!TextUtils.isEmpty(saveJson)){//系统的方法，判断字符串是否为空
            //解析数据
            processData(saveJson);
        }

        OkHttpUtils
                .post()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }
    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            tvNodata.setVisibility(View.VISIBLE);
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            tvNodata.setVisibility(View.GONE);

            switch (id) {
                case 100:
                    Toast.makeText(OKHttpListActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OKHttpListActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
            //解析数据和显示数据
            if (response != null){
                //
                CacheUtils.putString(OKHttpListActivity.this,url,response);

                processData(response);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
            progressBar.setProgress((int) (100 * progress));
        }
    }

    /**
     *
     * @param json
     */
    private void processData(String json) {

        //解析数据
        DataBean dataBean = parsedJson(json);
        //
        List<DataBean.ItemData> datas = dataBean.getTrailers();

        if (datas != null && datas.size()>0){
            //有数据
            tvNodata.setVisibility(View.GONE);
            //显示适配器
            adapter = new OKHttpListAdapter(OKHttpListActivity.this,datas);
            listview.setAdapter(adapter);
        }else {
            //没有数据
            tvNodata.setVisibility(View.VISIBLE);
        }
        //有没有数据都要显示进度条
        progressBar.setVisibility(View.GONE);

    }


    /**
     * 解析json数据
     * @param response
     * @return
     */
    private DataBean parsedJson(String response) {
        DataBean dataBean = new DataBean();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.optJSONArray("trailers");
            if (jsonArray != null && jsonArray.length() > 0) {
                List<DataBean.ItemData> trailers = new ArrayList<>();
                dataBean.setTrailers(trailers);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);

                    if (jsonObjectItem != null) {

                        DataBean.ItemData mediaItem = new DataBean.ItemData();

                        String movieName = jsonObjectItem.optString("movieName");//name
                        mediaItem.setMovieName(movieName);

                        String videoTitle = jsonObjectItem.optString("videoTitle");//desc
                        mediaItem.setVideoTitle(videoTitle);

                        String imageUrl = jsonObjectItem.optString("coverImg");//imageUrl
                        mediaItem.setCoverImg(imageUrl);

                        String hightUrl = jsonObjectItem.optString("hightUrl");//data
                        mediaItem.setHightUrl(hightUrl);

                        //把数据添加到集合
                        trailers.add(mediaItem);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataBean;
    }

}
