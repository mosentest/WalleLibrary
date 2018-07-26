package com.rmyh.recyclerviewsuspend.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rmyh.recyclerviewsuspend.R;
import com.rmyh.recyclerviewsuspend.bean.ConfigBean;
import com.rmyh.recyclerviewsuspend.utils.TextViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 参考这里https://www.jb51.net/article/104061.htm
 * https://blog.csdn.net/baidu_34012226/article/details/52326410
 */
public class StickyItemActivity extends AppCompatActivity {

    @InjectView(R.id.text_recycler)
    RecyclerView textRecycler;
    @InjectView(R.id.rb_1)
    TextView rb1;
    @InjectView(R.id.rb_2)
    TextView rb2;
    @InjectView(R.id.rb_3)
    TextView rb3;
    @InjectView(R.id.rb_4)
    TextView rb4;
    @InjectView(R.id.ll_menu)
    LinearLayout llMenu;
    @InjectView(R.id.activity_main)
    FrameLayout activityMain;
    @InjectView(R.id.fl_content)
    FrameLayout flContent;

    private List<ConfigBean> list = new ArrayList<>();

    private int menuHeight;

    private ConfigRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick_item);
        ButterKnife.inject(this);
        //菜单先隐藏
        llMenu.setVisibility(View.GONE);

        getSupportActionBar().hide();
        initData();

        textRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ConfigRecyclerAdapter(list);
        textRecycler.setAdapter(adapter);
        adapter.setRecyclerView(textRecycler);
        adapter.setContext(this);
        adapter.setMenuListener(new MenuListener() {
            @Override
            public void onMenuShow(boolean isShow) {
                if (isShow) {
                    llMenu.setVisibility(View.VISIBLE);
                } else {
                    clickPos(-1);
                    llMenu.setVisibility(View.GONE);
                    flContent.setVisibility(View.GONE);
                }
            }

            @Override
            public void currentClick(int position) {
                clickPos(position);
            }
        });
    }

    private void clickPos(int position) {
        TextViewUtils.setBg(rb1, R.drawable.rg_text);
        TextViewUtils.setBg(rb2, R.drawable.rg_text);
        TextViewUtils.setBg(rb3, R.drawable.rg_text);
        TextViewUtils.setBg(rb4, R.drawable.rg_text);
        switch (position) {
            case 0:
                TextViewUtils.setBg(rb1, R.drawable.rg_text_focus);
                //打开相应的菜单
                flContent.setVisibility(View.VISIBLE);
                break;
            case 1:
                TextViewUtils.setBg(rb2, R.drawable.rg_text_focus);
                //打开相应的菜单
                flContent.setVisibility(View.VISIBLE);
                break;
            case 2:
                TextViewUtils.setBg(rb3, R.drawable.rg_text_focus);
                //打开相应的菜单
                flContent.setVisibility(View.VISIBLE);
                break;
            case 3:
                TextViewUtils.setBg(rb4, R.drawable.rg_text_focus);
                //打开相应的菜单
                flContent.setVisibility(View.VISIBLE);
                break;
        }
    }


    /**
     * https://blog.csdn.net/lj402159806/article/details/53380089
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //这里还没出来。。
        menuHeight = llMenu.getHeight();
    }

    private void initData() {
        list.clear();
        list.add(create(0, ""));
        list.add(create(1, ""));
        for (int i = 0; i < 24; i++) {
            list.add(create(2, "" + i));
        }
    }

    private ConfigBean create(int type, String name) {
        ConfigBean configBean = new ConfigBean();
        configBean.type = type;
        configBean.name = name;
        return configBean;
    }

    @OnClick({R.id.rb_1, R.id.rb_2, R.id.rb_3, R.id.rb_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_1:
                clickPos(0);
                break;
            case R.id.rb_2:
                clickPos(1);
                break;
            case R.id.rb_3:
                clickPos(2);
                break;
            case R.id.rb_4:
                clickPos(3);
                break;
        }
    }
}
