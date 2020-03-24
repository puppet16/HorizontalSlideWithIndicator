package com.luck.horizontalslide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class OneActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private HomeGoldPositionItemAdapter mHomeGoldPositionItemAdapter;
    private List<HomeGoldPositionItemBean> mHomeGoldPositionItemBeanList = new ArrayList<>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this,HomeGoldPostionSpaceItemDecoration.GRID_NUM);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new HomeGoldPostionSpaceItemDecoration(this));
        mHomeGoldPositionItemAdapter = new HomeGoldPositionItemAdapter(this);
        mRecyclerView.setAdapter(mHomeGoldPositionItemAdapter);
        setData();
    }

    private void setData() {
        mHomeGoldPositionItemBeanList.clear();
        for (int i = 0; i < 20; i++) {
            HomeGoldPositionItemBean bean = new HomeGoldPositionItemBean( String.valueOf(R.drawable.card_icon_home_gold_all), "条目"+i);
            mHomeGoldPositionItemBeanList.add(bean);
        }
        mHomeGoldPositionItemAdapter.setNewData(mHomeGoldPositionItemBeanList);
        mHomeGoldPositionItemAdapter.notifyDataSetChanged();
        mHomeGoldPositionItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                startActivity(new Intent(OneActivity.this, TwoActivity.class));
            }
        });
    }
}
