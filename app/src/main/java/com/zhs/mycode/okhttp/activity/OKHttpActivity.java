package com.zhs.mycode.okhttp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhs.mycode.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/22.
 * okhttp
 */
public class OKHttpActivity extends Activity {

    //配置编码格式
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    //okhttp客户端
    OkHttpClient client = new OkHttpClient();

    private Button btn_get_post;
    //这是get请求
    private static final int GET = 1;
    //这是post请求
    private static final int POST = 2;
    private static final String TAG = OKHttpActivity.class.getSimpleName();
    @Bind(R.id.btn_get_post)
    Button btnGetPost;
    @Bind(R.id.btn_get_okhttputils)
    Button btngetokhttputils;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.btn_downloadfile)
    Button btn_downloadfile;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btn_uploadfile)
    Button btn_uploadfile;
    @Bind(R.id.iv_icon)
    ImageView iv_icon;
    @Bind(R.id.btn_image)
    Button btn_image;
    @Bind(R.id.btn_image_list)
    Button btn_image_list;

    //聚合数据的邮编信息url
    public static final String JUHE_POST_URL = "http://v.juhe.cn/postcode/query?postcode=073100&key=53f072318d1c0cbd34b3eb9f7e474ca7";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET:
                    //get方式获取数据
                    text.setText((String) msg.obj);
                    break;
                case POST:
                    //post方式获取数据
                    text.setText((String) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_get_post,
            R.id.btn_get_okhttputils,
            R.id.btn_downloadfile,
            R.id.btn_uploadfile,
            R.id.btn_image,
            R.id.btn_image_list})
    void onClick(View view) {
        text.setText("");
        switch (view.getId()) {
            case R.id.btn_get_post://使用原生的okhttp请求网络数据，get和post两种方式.
                // 都在子线程中！！！
                //get方式
                //getDataFromGet();
                //post方式
                getDataFromPost();
                break;
            case R.id.btn_get_okhttputils://使用封装的okhttp-utils的get和post方式请求网络数据
                //使用封装的okhttp-utils的get方式获取数据
                //getDataGETByOkhttpUtils();
                //使用封装的okhttp-utils的post方式获取数据
                getDataPOSTByOkhttpUtils();
                break;
            case R.id.btn_downloadfile://下载文件
                downloadFile();
                break;
            case R.id.btn_uploadfile://文件上传
                multiFileUpload();
                break;
            case R.id.btn_image://请求单张图片        可以做上传头像
                getImage();
                break;
            case R.id.btn_image_list://请求列表中的图片，跳转到别的activity
                Intent intent = new Intent(this,OKHttpListActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 使用原生okhttp请求网络数据
     */
    private void getDataFromGet() {

        //触发子线程,因为okhttp不能再主线程里运行
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = get(JUHE_POST_URL);
                    Log.i("甄贺帅", result);
                    Message msg = Message.obtain();
                    msg.what = GET;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 使用post请求网络数据
     */
    private void getDataFromPost() {

        //触发子线程,因为okhttp不能再主线程里运行
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = post(JUHE_POST_URL, "");
                    Log.i("甄贺帅", result);
                    Message msg = Message.obtain();
                    msg.what = POST;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    //okhttp的get请求 原生
    String get(String url) throws IOException {
        //创建请求
        Request request = new Request.Builder()
                .url(url)//加入url地址
                .build();//创建

        Response response = client.newCall(request).execute();//访问并返回响应
        return response.body().string();//返回请求url得到的内容
    }


    /**
     * okhttp的post请求 原生
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    /**
     * 通过封装好的类库获取数据,使用okhttp-utils的get请求网络文本数据
     */
    public void getDataGETByOkhttpUtils() {

        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        url = "http://v.juhe.cn/postcode/query?postcode=073100&key=53f072318d1c0cbd34b3eb9f7e474ca7";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    /**
     * 通过封装好的类库获取数据,使用okhttp-utils的get请求网络文本数据
     */
    public void getDataPOSTByOkhttpUtils() {

        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        url = "http://v.juhe.cn/postcode/query?postcode=073100&key=53f072318d1c0cbd34b3eb9f7e474ca7";
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
            text.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            text.setText("onResponse:" + response);

            switch (id) {
                case 100:
                    Toast.makeText(OKHttpActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OKHttpActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
            progressBar.setProgress((int) (100 * progress));
        }
    }


    /**
     * 使用okhttp-utils 下载大文件
     */
    public void downloadFile() {
        String url = "http://192.168.0.100:8080/FileUpload/upload/a22fb1ce1ef247fea85bcf177eec4dd1_IMG_0449.MOV";

        OkHttpUtils//
                .get()//get方式
                .url(url)//指定url地址，本次用的是本机的ip
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "okhttp-test.MOV")//
                {

                    @Override
                    public void onBefore(Request request, int id) {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        progressBar.setProgress((int) (100 * progress));
                        Log.e(TAG, "inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                    }
                });
    }


    /**
     * 使用okhttp-utils上传多个或者单个文件
     */
    public void multiFileUpload() {
        String mBaseUrl = "http://192.168.0.100:8080/FileUpload/FileUploadServlet";
        File file = new File(Environment.getExternalStorageDirectory(), "afu.png");
        File file2 = new File(Environment.getExternalStorageDirectory(), "jsonweixin.txt");
        if (!file.exists() || !file2.exists()) {
            Toast.makeText(OKHttpActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", "张鸿洋");
        params.put("password", "123");


        String url = mBaseUrl;
        OkHttpUtils.post()//
                .addFile("mFile", "server_afu.jpg", file)//
                .addFile("mFile", "server_test.txt", file2)//
                .url(url)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }

    public void getImage() {
        text.setText("");
        String url = "http://192.168.0.100:8080/FileUpload/upload/663380ea2d3e43638ed497980c90e9ea_IMG_0623.PNG";
        OkHttpUtils
                .get()//
                .url(url)//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        text.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        Log.e("TAG", "onResponse：complete");
                        iv_icon.setImageBitmap(bitmap);
                    }
                });
    }


}
