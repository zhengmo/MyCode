package com.zhs.mycode.json.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhs.mycode.R;
import com.zhs.mycode.json.bean.DataInfo;
import com.zhs.mycode.json.bean.ShopInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zhs.mycode.R.id.tv_native_last;
import static com.zhs.mycode.R.id.tv_native_orignal;

//手动解析页面

/**
 * 1.将json格式的字符串{}转换为Java对象
 * 2.将json格式的字符串[]转换为Java对象的list集合
 * 3.复杂的json数据解析
 * 4.特殊的json数据解析
 */
public class NativeJsonPraseActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_native_tojavaobject)
    Button btnNativeTojavaobject;
    @Bind(R.id.btn_native_tojavalist)
    Button btnNativeTojavalist;
    @Bind(R.id.btn_native_complex)
    Button btnNativeComplex;
    @Bind(R.id.btn_native_special)
    Button btnNativeSpecial;
    @Bind(tv_native_orignal)
    TextView tvNativeOrignal;
    @Bind(tv_native_last)
    TextView tvNativeLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_json_prase);
        ButterKnife.bind(this);
        //把标题栏改为“JSON”
        tvTitle.setText("手动JSON解析");

    }

    //初始化监听
    @OnClick({R.id.btn_native_tojavaobject,R.id.btn_native_tojavalist,R.id.btn_native_complex,R.id.btn_native_special})
    void onClick(View view){
        switch (view.getId()){
            //1.将json格式的字符串{}转换为Java对象
            case R.id.btn_native_tojavaobject:
                jsonToJavaObjectByNative();
                break;

            //2.将json格式的字符串[]转换为Java对象的list集合
            case R.id.btn_native_tojavalist:
                jsonToJavaListByNative();
                break;

            //3.复杂的json数据解析
            case R.id.btn_native_complex:
                jsonToJavaOfComplex();
                break;

            //4.特殊的json数据解析
            case R.id.btn_native_special:
                break;
        }
    }

    private void jsonToJavaOfComplex() {
        //1.获取json数据
        //json串
        String json = "{\n" +
                "    \"data\": {\n" +
                "        \"count\": 5,\n" +
                "        \"items\": [\n" +
                "            {\n" +
                "                \"id\": 45,\n" +
                "                \"title\": \"坚果\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 132,\n" +
                "                \"title\": \"炒货\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 166,\n" +
                "                \"title\": \"蜜饯\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 195,\n" +
                "                \"title\": \"果脯\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 196,\n" +
                "                \"title\": \"礼盒\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"rs_code\": \"1000\",\n" +
                "    \"rs_msg\": \"success\"\n" +
                "}";
        //封装Java对象
        DataInfo dataInfo = new DataInfo();

        //2.解析json数据
        try {
            JSONObject jsonObject = new JSONObject(json);

            //第一层解析
            JSONObject data = jsonObject.optJSONObject("data");
            String rs_code = jsonObject.optString("rs_code");
            String rs_msg = jsonObject.optString("rs_msg");
            //第一层封装
            dataInfo.setRs_code(rs_code);
            dataInfo.setRs_msg(rs_msg);
            DataInfo.DataBean dataBean = new DataInfo.DataBean();
            dataInfo.setData(dataBean);


            //第二层解析
            int count = data.optInt("count");
            JSONArray items = data.optJSONArray("items");
            //第二层数据的封装
            dataBean.setCount(count);
            List<DataInfo.DataBean.ItemsBean> itemsBean = new ArrayList<>();
            dataBean.setItems(itemsBean);

            //第三层解析
            for (int i = 0; i < items.length(); i++) {
                JSONObject jsonObject1 = items.optJSONObject(i);
                if (jsonObject1 != null){
                    int id = jsonObject1.optInt("id");
                    String title = jsonObject1.optString("title");

                    //第三层数据的封装
                    DataInfo.DataBean.ItemsBean bean = new DataInfo.DataBean.ItemsBean();
                    bean.setId(id);
                    bean.setTitle(title);
                    itemsBean.add(bean);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //3.显示json数据
        tvNativeOrignal.setText(json);
        tvNativeLast.setText(dataInfo.toString());

    }


    /**
     * 2.将json格式的字符串[]转换为Java对象的list集合
     */
    private void jsonToJavaListByNative() {
        //获取/创建json数据
        String json = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"imagePath\": \"http://192.168.10.165:8080/f1.jpg\",\n" +
                "        \"name\": \"大虾1\",\n" +
                "        \"price\": 12.3\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"imagePath\": \"http://192.168.10.165:8080/f2.jpg\",\n" +
                "        \"name\": \"大虾2\",\n" +
                "        \"price\": 12.5\n" +
                "    }\n" +
                "]";
        //解析json
        List<ShopInfo> shops = null;
        try {
            JSONArray jsonArray = new JSONArray(json);
            shops = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null){
                    int id = jsonObject.optInt("id");
                    String name = jsonObject.optString("name");
                    double price = jsonObject.optDouble("price");
                    String imagePath = jsonObject.optString("imagePath");
                    //封装Java对象
                    ShopInfo shopInfo = new ShopInfo(id,name,price,imagePath);
                    //添加到集合
                    shops.add(shopInfo);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //显示JSON数据
        tvNativeOrignal.setText(json);
        tvNativeLast.setText(shops.toString());
    }

    /**
     * 1.将json格式的字符串{}转换为Java对象
     */
    private void jsonToJavaObjectByNative() {
        // 1 获取或创建JSON数据
        String json = "{\n" +
                "\t\"id\":2, \"name\":\"大虾\", \n" +
                "\t\"price\":12.3, \n" +
                "\t\"imagePath\":\"http://192.168.10.165:8080/L05_Server/images/f1.jpg\"\n" +
                "}\n";

        List<ShopInfo> shops = new ArrayList<>();
        ShopInfo shop = new ShopInfo();
        JSONObject jsonObject = null;
        //2 解析json数据
        try {
            jsonObject = new JSONObject(json);
            int id = jsonObject.optInt("id");
            String name = jsonObject.optString("name");
            double price = jsonObject.optDouble("price");
            String imagePath = jsonObject.optString("imagePath");

            //把JSON数据装到实体对象中
            shop.setId(id);
            shop.setName(name);
            shop.setPrice(price);
            shop.setImagePath(imagePath);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //3 显示json数据

        tvNativeOrignal.setText(json);
        tvNativeLast.setText(shop.toString());

    }


}
