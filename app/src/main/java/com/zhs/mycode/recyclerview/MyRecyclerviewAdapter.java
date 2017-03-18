package com.zhs.mycode.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhs.mycode.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/15.
 */

public class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.ViewHolder> {


    private final Context context;
    private ArrayList<String> datas;

    public MyRecyclerviewAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    /**
     * 相当于listview中的getView方法中创建View和Viewholder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_recyclerview, null);
        return new ViewHolder(itemView);
    }

    /**
     * 相当于getView方法中绑定数据的代码
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //根据位置得到对应的数据
//        String data = datas.get(position);
//        holder.tv_title.setText(data);
        holder.setData(datas.get(position));//在holder类里面写也行，布局已经加载，但是没有数据
    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 添加数据
     * @param position
     * @param data
     */
    public void addData(int position, String data) {
        datas.add(position,data);
        //刷新适配器
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        datas.remove(position);
        //刷新适配器
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon;
        private TextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_recyclerview_icon);
            tv_title = (TextView) itemView.findViewById(R.id.tv_recyclerview_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "data===" + datas.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v,datas.get(getLayoutPosition()));
                    }

                }
            });

        }

        public void setData(String s) {
            tv_title.setText(s);
        }



    }

    /**
     * 点击RecyclerView某条的监听
     */
    public interface OnItemClickListener{
        /**
         * 当recyclerview某个被点击的时候回调
         * @param view
         * @param data
         */
        public void onItemClick(View view,String data);
    }

    /**
     * 设置recyclerview某条的监听,从外界传进来
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;
}
