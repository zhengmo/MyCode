package com.zhs.mycode.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhs.mycode.R;

import java.util.ArrayList;

public class RecyclerViewActivity extends Activity implements View.OnClickListener {


    private TextView tvTitle;
    private Button btnAdd;
    private Button btnDelete;
    private Button btnList;
    private Button btnGrid;
    private Button btnFlow;
    private RecyclerView recyclerview;
    private ArrayList<String> datas;
    private MyRecyclerviewAdapter adapter;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-02-15 00:39:53 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        btnAdd = (Button)findViewById( R.id.btn_add );
        btnDelete = (Button)findViewById( R.id.btn_delete );
        btnList = (Button)findViewById( R.id.btn_list );
        btnGrid = (Button)findViewById( R.id.btn_grid );
        btnFlow = (Button)findViewById( R.id.btn_flow );
        tvTitle = (TextView) findViewById(R.id.tv_title);
        recyclerview = (RecyclerView)findViewById( R.id.recyclerview );

        tvTitle.setText("RecyclerView");


        btnAdd.setOnClickListener( this );
        btnDelete.setOnClickListener( this );
        btnList.setOnClickListener( this );
        btnGrid.setOnClickListener( this );
        btnFlow.setOnClickListener( this );


    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-02-15 00:39:53 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == btnAdd ) {
            // 添加
            adapter.addData(0,"New_content");
            recyclerview.scrollToPosition(0);
        } else if ( v == btnDelete ) {
            // 删除某一条
            adapter.removeData(0);
        } else if ( v == btnList ) {
            // Handle clicks for btnList
            //设置LayoutManager
            recyclerview.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this,LinearLayoutManager.VERTICAL,false));
        } else if ( v == btnGrid ) {
            // Handle clicks for btnGrid
            //设置LayoutManager
            recyclerview.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        } else if ( v == btnFlow ) {
            //设置瀑布流
            recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        findViews();

        initData();
//        Toast.makeText(this, "走到initData方法了"+datas.toString(), Toast.LENGTH_SHORT).show();
        //设置RecyclerView的适配器
        adapter = new MyRecyclerviewAdapter(RecyclerViewActivity.this,datas);
        recyclerview.setAdapter(adapter);

        //默认是listview方式显示
        recyclerview.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this,LinearLayoutManager.VERTICAL,false));

        //分割线
        recyclerview.addItemDecoration(new DividerListItemDecoration(this,DividerListItemDecoration.VERTICAL_LIST));

        //设置行点击事件
        adapter.setOnItemClickListener(new MyRecyclerviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(RecyclerViewActivity.this, "data===" + data,Toast.LENGTH_SHORT).show();
            }
        });

        //设置动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());


    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add("content_" + i);
        }
    }
}
