package com.luck.horizontalslide;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


public class HomeGoldPostionSpaceItemDecoration extends RecyclerView.ItemDecoration {
    public static final int GRID_NUM = 2;
    private Context mContext;
    public HomeGoldPostionSpaceItemDecoration(Context context) {
        mContext = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        outRect.top = Utils.dp2px(mContext,16);
    }
}
