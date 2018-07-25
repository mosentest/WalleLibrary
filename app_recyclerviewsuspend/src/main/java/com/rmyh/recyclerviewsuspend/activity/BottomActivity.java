package com.rmyh.recyclerviewsuspend.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rmyh.recyclerviewsuspend.ItemDecoration.DeviderDecoration;
import com.rmyh.recyclerviewsuspend.ItemDecoration.PaddingDecoration;
import com.rmyh.recyclerviewsuspend.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wen on 2017/8/8.
 */

public class BottomActivity extends AppCompatActivity {

    @InjectView(R.id.text_recycler)
    RecyclerView textRecycler;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        getSupportActionBar().hide();
        initData();

        textRecycler.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter adapter = new RecyclerAdapter(list);
        textRecycler.addItemDecoration(new DeviderDecoration(this));
        textRecycler.setAdapter(adapter);
    }
    private void initData() {
        list.clear();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
    }
}
