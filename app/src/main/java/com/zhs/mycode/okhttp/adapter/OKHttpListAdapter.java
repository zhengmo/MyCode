package com.zhs.mycode.okhttp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhs.mycode.R;
import com.zhs.mycode.okhttp.domain.DataBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

import okhttp3.Call;

import static com.zhs.mycode.R.id.iv_icon;

/**
 * Created by Administrator on 2016/12/24.
 */

public class OKHttpListAdapter extends BaseAdapter {

    private final Context context;
    private final List<DataBean.ItemData> datas;

    public OKHttpListAdapter(Context context, List<DataBean.ItemData> datas) {
        this.context = context;
        this.datas = datas;

    }

    @Override
    public int getCount() {
        return datas.size();
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
        final ViewHolder holder ;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_okhttp_list_image,null);
            holder = new ViewHolder();
            holder.iv_icon = (ImageView) convertView.findViewById(iv_icon);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //根据位置得到数据
        DataBean.ItemData itemData= datas.get(position);
        //设置名称和描述
        holder.tv_name.setText(itemData.getMovieName());
        holder.tv_desc.setText(itemData.getVideoTitle());
        //在列表中使用OKHttp-utils请求图片
        String url = "http://192.168.0.100:8080/FileUpload/upload/663380ea2d3e43638ed497980c90e9ea_IMG_0623.PNG";
        OkHttpUtils
                .get()//
                .url(itemData.getCoverImg())//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        Log.e("TAG", "onResponse：complete");
                        holder.iv_icon.setImageBitmap(bitmap);
                    }
                });

        return convertView;
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;
    }

}
