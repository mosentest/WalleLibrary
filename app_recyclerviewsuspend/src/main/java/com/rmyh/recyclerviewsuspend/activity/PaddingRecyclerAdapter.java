package com.rmyh.recyclerviewsuspend.activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rmyh.recyclerviewsuspend.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2017/8/8.
 */

public class PaddingRecyclerAdapter extends RecyclerView.Adapter {

    List<String> list =  new ArrayList<>();

    public PaddingRecyclerAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.padding_date_item, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).setData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView viewById;

        public MyViewHolder(View itemView) {
            super(itemView);
            viewById =  (TextView) itemView.findViewById(R.id.text);
        }

        public void setData(int position) {
            viewById.setText(list.get(position));
        }
    }
}