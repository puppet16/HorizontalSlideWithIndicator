package com.luck.horizontalslide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.luck.horizontalslide.last.PageRecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private HomeGoldPositionItemAdapter mHomeGoldPositionItemAdapter;
    private List<HomeGoldPositionItemBean> mHomeGoldPositionItemBeanList = new ArrayList<>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this,HomeGoldPostionSpaceItemDecoration.GRID_NUM);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new HomeGoldPostionSpaceItemDecoration(this));
        mHomeGoldPositionItemAdapter = new HomeGoldPositionItemAdapter(this);
        mRecyclerView.setAdapter(mHomeGoldPositionItemAdapter);
        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);

        setData();
    }

    private void setData() {
        mHomeGoldPositionItemBeanList.clear();
        for (int i = 0; i < 20; i++) {
            HomeGoldPositionItemBean bean = new HomeGoldPositionItemBean( R.drawable.card_icon_home_gold_all, "条目"+i);
            mHomeGoldPositionItemBeanList.add(bean);
        }
        mHomeGoldPositionItemAdapter.setNewData(mHomeGoldPositionItemBeanList);
        mHomeGoldPositionItemAdapter.notifyDataSetChanged();
        mHomeGoldPositionItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                startActivity(new Intent(MainActivity.this, TwoActivity.class));
            }
        });
    }
//
//    private void initListener() {
//        // 设置指示器
//        mRecyclerView.setIndicator((PageIndicatorView) findViewById(R.id.indicator));
//        // 设置行数和列数
//        mRecyclerView.setPageSize(1, 2);
//
//        // 设置页间距
//        mRecyclerView.setPageMargin(30);
//        // 设置数据
//        mRecyclerView.setAdapter(myAdapter=mRecyclerView.new PageAdapter(list_tequan1, new PageRecyclerView.CallBack() {
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.tequan_item, parent, false);
//                return new MyHolder(view);
//            }
//
//            @Override
//            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//                Map<String,Object> map= (Map<String, Object>) list_tequan1.get(position);
//                Glide.with(MainActivity.this).load(map.get("img").toString()).into( ((MyHolder) holder).iv_huanyuan_zuo);
//                ((MyHolder) holder).gv_text.setText((String) map.get("txt"));
//            }
//
//            @Override
//            public void onItemClickListener(View view, int position) {
////                                        Toast.makeText(HuiYuanCenterActivity.this, "点击："
////                                                + list_tequan1.get(position), Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onItemLongClickListener(View view, int position) {
////                                        Toast.makeText(HuiYuanCenterActivity.this, "删除："
////                                                + list_tequan1.get(position), Toast.LENGTH_SHORT).show();
////                                        myAdapter.remove(position);
//            }
//        }));
//    }
}
