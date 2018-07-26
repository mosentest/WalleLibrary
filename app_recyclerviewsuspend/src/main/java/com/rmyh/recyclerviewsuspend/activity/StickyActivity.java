package com.rmyh.recyclerviewsuspend.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rmyh.recyclerviewsuspend.ItemDecoration.SectionDecoration;
import com.rmyh.recyclerviewsuspend.R;
import com.rmyh.recyclerviewsuspend.bean.ConfigBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StickyActivity extends AppCompatActivity {

    @InjectView(R.id.text_recycler)
    RecyclerView textRecycler;
    private List<ConfigBean> list = new ArrayList<>();
    private List<String> NameBean = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        getSupportActionBar().hide();
        initData();

        textRecycler.setLayoutManager(new LinearLayoutManager(this));
        ConfigRecyclerAdapter adapter = new ConfigRecyclerAdapter(list);
        textRecycler.addItemDecoration(new SectionDecoration(list, this, new SectionDecoration.DecorationCallback() {
            @Override
            public String getGroupId(int position) {
                if (NameBean.get(position) != null) {
                    return NameBean.get(position);
                }
                return "-1";
            }

            @Override
            public String getGroupFirstLine(int position) {
                if (NameBean.get(position) != null) {
                    return NameBean.get(position);
                }
                return "";
            }
        }));
        textRecycler.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //((LinearLayoutManager) textRecycler.getLayoutManager()).scrollToPositionWithOffset(1, 0);
            }
        }, 1000);

    }

    private void initData() {
        list.clear();
        NameBean.clear();
        list.add(create(0, ""));
        list.add(create(1, ""));
        for (int i = 0; i < 24; i++) {
            list.add(create(2, "" + i));
        }
        NameBean.add("111111111");
        NameBean.add("111111111");
        NameBean.add("222222222");
        NameBean.add("222222222");
        NameBean.add("222222222");
        NameBean.add("222222222");
        NameBean.add("222222222");
        NameBean.add("222222222");
        NameBean.add("222222222");
        NameBean.add("333333333");
        NameBean.add("333333333");
        NameBean.add("333333333");
        NameBean.add("444444444");
        NameBean.add("444444444");
        NameBean.add("444444444");
        NameBean.add("444444444");
        NameBean.add("444444444");
        NameBean.add("444444444");
        NameBean.add("444444444");
        NameBean.add("444444444");
        NameBean.add("444444444");
        NameBean.add("444444444");
        NameBean.add("444444444");
        NameBean.add("444444444");
        NameBean.add("444444444");
        NameBean.add("444444444");
    }

    private ConfigBean create(int type, String name) {
        ConfigBean configBean = new ConfigBean();
        configBean.type = type;
        configBean.name = name;
        return configBean;
    }
}
