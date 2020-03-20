package com.luck.horizontalslide;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.luck.horizontalslide.gridpager.GridViewPager;
import com.luck.library.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;


/**
 * ============================================================
 * 作 者 : 李桐桐
 * 创建日期 ： 2020-03-20 11:29
 * 描 述 :
 * ============================================================
 **/

public class ThreeActivity extends Activity implements View.OnClickListener{

    private String[] titles = {"选课", "英语", "数学", "语文", "美术", "分级阅读", "国际游学"};
    private String[] icons = {"http:\\/\\/k12static.xdf.cn\\/wxbackend\\/banner\\/20200313\\/decac1cc67842f2df3bd4d83128cc9da.png",
            "http:\\/\\/k12static.xdf.cn\\/wxbackend\\/banner\\/20200316\\/7372e3a9699ca261b3d7bb39a2ecae03.png",
            "http:\\/\\/k12static.xdf.cn\\/wxbackend\\/banner\\/20200316\\/693e441a077ad17cba385fbaa09dfc60.png",
            "http:\\/\\/k12static.xdf.cn\\/wxbackend\\/banner\\/20200316\\/fa511383d54e7a4007e8434ce6ed8787.png",
            "http:\\/\\/k12static.xdf.cn\\/wxbackend\\/banner\\/20200313\\/5e0b3d48f2da6702c5caff96e96dee34.png",
            "http:\\/\\/k12static.xdf.cn\\/wxbackend\\/banner\\/20200316\\/c72d68508a421e3363ef16d68995f7b6.png",
            "http:\\/\\/k12static.xdf.cn\\/wxbackend\\/banner\\/20200316\\/005ef44ef4626917e096bda2075b1f49.png",
    };

    private List<HomeGoldPositionItemBean> mHomeGoldPositionItemBeanList = new ArrayList<>();
    private  GridViewPager gridViewPager;
    private int itemCount = 43;
    private Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        gridViewPager = findViewById(R.id.gridviewpager);
        btn1 = findViewById(R.id.btu_one_line);
        btn2 = findViewById(R.id.btu_two_line);
        btn3 = findViewById(R.id.btu_refresh_less);
        btn4 = findViewById(R.id.btu_refresh_more);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        initPage();
    }

    protected void initPage() {
        initData();

        gridViewPager
                // 设置数据总数量
                .setDataAllCount(mHomeGoldPositionItemBeanList.size())
                // 数据绑定
                .setImageTextLoaderInterface(new GridViewPager.ImageTextLoaderInterface() {
                    @Override
                    public void displayImageText(ImageView imageView, TextView textView, int position) {
                        // 自己进行数据的绑定，灵活度更高，不受任何限制
                        BitmapRequestBuilder builder = Glide.with(getApplicationContext()).load(mHomeGoldPositionItemBeanList.get(position).getImage()).asBitmap().placeholder(R.drawable.card_icon_home_gold_all).error(R.drawable.card_icon_home_gold_all);
                        builder.into(imageView);
                        textView.setText(mHomeGoldPositionItemBeanList.get(position).getText());
                    }
                })
                // Item点击
                .setGridItemClickListener(new GridViewPager.GridItemClickListener() {
                    @Override
                    public void click(int position) {
                        Toast.makeText(getBaseContext(), "点击了" + mHomeGoldPositionItemBeanList.get(position).getText() + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btu_refresh_more:
                itemCount = 62;
                initData();
                break;
            case R.id.btu_refresh_less:
                itemCount = 24;
                initData();
                break;
            case R.id.btu_one_line:
                itemCount = 4;
                initData();
                break;
            case R.id.btu_two_line:
                itemCount = 7;
                initData();
                break;
        }
        // 刷新
        gridViewPager.setDataAllCount(mHomeGoldPositionItemBeanList.size()).show();
    }

    /**
     * 初始化数据源，这里使用本地图标作为示例
     */
    private void initData() {
        mHomeGoldPositionItemBeanList.clear();
        for (int i = 0; i < itemCount; i++) {
            HomeGoldPositionItemBean bean = new HomeGoldPositionItemBean(icons[i % 7], titles[i % 7]+i);
            mHomeGoldPositionItemBeanList.add(bean);
        }
    }
}

