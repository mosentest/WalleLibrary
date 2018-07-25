package com.rmyh.recyclerviewsuspend.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.rmyh.recyclerviewsuspend.ItemDecoration.DeviderDecoration;
import com.rmyh.recyclerviewsuspend.ItemDecoration.SectionDecoration;
import com.rmyh.recyclerviewsuspend.R;
import com.rmyh.recyclerviewsuspend.common.MyItemTouchHelper;
import com.rmyh.recyclerviewsuspend.common.OnRecyclerItemClickListener;
import com.rmyh.recyclerviewsuspend.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TotalActivity extends AppCompatActivity {

    @InjectView(R.id.text_recycler)
    RecyclerView textRecycler;
    List<String> list =  new ArrayList<>();
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        ButterKnife.inject(this);

        getSupportActionBar().hide();
        initData();

        textRecycler.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter adapter = new RecyclerAdapter(list);
        textRecycler.addItemDecoration(new DeviderDecoration(this));
        textRecycler.setAdapter(adapter);
        itemTouchHelper = new ItemTouchHelper(new MyItemTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(textRecycler);

        textRecycler.addOnItemTouchListener(new OnRecyclerItemClickListener(textRecycler) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                ToastUtils.showToast(viewHolder.getAdapterPosition()+1+"");
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                ToastUtils.showToast(viewHolder.getAdapterPosition()+1+"");
                //当 item 被长按且不是第一个时，开始拖曳这个 item
                if (viewHolder.getLayoutPosition() != 0) {
                    itemTouchHelper.startDrag(viewHolder);
                }
            }
        });
    }

    private void initData() {
        list.clear();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");
        list.add("20");
        list.add("21");
        list.add("22");
        list.add("23");
        list.add("24");
        list.add("25");
    }
}
