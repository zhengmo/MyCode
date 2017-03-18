package com.zhs.mycode;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import com.zhs.mycode.okhttp.base.BaseFragment;
import com.zhs.mycode.fragment.CommonFrameFragment;
import com.zhs.mycode.fragment.CustomFragment;
import com.zhs.mycode.fragment.OtherFragment;
import com.zhs.mycode.fragment.ThirdPartyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/21.
 */
public class MainActivity extends FragmentActivity{

    @Bind(R.id.rg_main)
    RadioGroup mRg_main;
    //碎片集合
    private List<BaseFragment> mBaseFragment;
    //选中的Fragment的对应的位置
    private int position;
    //当前显示的碎片
    private Fragment mContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        //初始化Fragment
        initFragment();

        //初始化监听
        initListener();



    }

    private void initListener() {
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中常用框架
        mRg_main.check(R.id.rb_common_frame);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_common_frame://常用框架
                    position = 0;
                    break;
                case R.id.rb_thirdparty://第三方
                    position = 1;
                    break;
                case R.id.rb_custom://自定义
                    position = 2;
                    break;
                case R.id.rb_other://其他
                    position = 3;
                    break;
                default:
                    position = 0;
                    break;
            }
            //根据位置position得到对应的Fragment
            BaseFragment to = getFragment();
            //替换
            switchFragment(mContent,to);
        }
    }

    /**
     *
     * @param from 刚显示的Fragment马上就要被隐藏
     * @param to    马上要切换到的Fragment，一会儿要显示
     */
    private void switchFragment(Fragment from,Fragment to){

        if (from != to){
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //判断有没有被添加过
            if (!to.isAdded()){
                //to没有被添加
                //隐藏from
                if (from!=null){
                    ft.hide(from);
                }
                //添加to
                if (to!=null){
                    ft.add(R.id.fl_content,to).commit();
                }
            }else{
                //to已经被添加
                //隐藏from
                if (from!=null){
                    ft.hide(from);
                }
                //显示to
                if (to!=null){
                    ft.show(to).commit();
                }
            }
        }
    }

    /**
     * 这个方法每次切换碎片都会重新加载布局,耗资源
     * @param to
     */
    private void switchFragment(BaseFragment to) {
        //得到Fragmentmanager
        FragmentManager fm = getSupportFragmentManager();
        //2.开启事物
        FragmentTransaction transaction = fm.beginTransaction();
        //3.替换
        transaction.replace(R.id.fl_content,to);
        //4.提交事物
        transaction.commit();
    }

    /**
     * 根据位置得到对应的Fragment
     * @return
     */
    private BaseFragment getFragment() {
        return mBaseFragment.get(position);
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new CommonFrameFragment());//常用框架Fragment
        mBaseFragment.add(new ThirdPartyFragment());//第三方Fragment
        mBaseFragment.add(new CustomFragment());//自定义控件Fragment
        mBaseFragment.add(new OtherFragment());//其他Fragment

    }

    @OnClick({R.id.rb_common_frame,R.id.rb_thirdparty,R.id.rb_custom,R.id.rb_other})
    void btnClick(View view){
        switch (view.getId()){
            case R.id.rb_common_frame:
                //
                break;
            case R.id.rb_thirdparty:
                //
                break;
            case R.id.rb_custom:
                //
                break;
            case R.id.rb_other:
                //
                break;
        }

    }

}
