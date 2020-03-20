package com.luck.horizontalslide;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


public class HomeGoldPositionItemAdapter extends BaseQuickAdapter<HomeGoldPositionItemBean, BaseViewHolder> {
    private Context mContext;
    public HomeGoldPositionItemAdapter(Context context){
        super(R.layout.item_home_gold_position_card, null);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, HomeGoldPositionItemBean item) {
        if(null == item) return;
        viewHolder.setText(R.id.tv_txt,item.text);
        viewHolder.setImageResource(R.id.iv_icon, item.image);

    }
}
