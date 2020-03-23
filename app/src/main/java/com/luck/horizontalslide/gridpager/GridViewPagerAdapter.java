package com.luck.horizontalslide.gridpager;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.luck.horizontalslide.R;

import java.util.List;

/**
 * ============================================================
 * 作 者 : 李桐桐
 * 创建日期 ： 2020-03-23 15:32
 * 描 述 :
 * ============================================================
 **/
public class GridViewPagerAdapter extends RecyclerView.Adapter<GridViewPagerAdapter.ViewHolder> {

    private ViewGroup.LayoutParams layoutParamsMatch;
    private LinearLayout.LayoutParams imageLp;
    private LinearLayout.LayoutParams textLp;
    private int widthPixels;

    private ParamsBean mParamsBean;
    private Context mContext;
    private List<String> mData;

    public GridViewPagerAdapter(Context context) {
        mContext = context;
        widthPixels = context.getResources().getDisplayMetrics().widthPixels;
    }

    public void setParamsBean(ParamsBean mParamsBean) {
        this.mParamsBean = mParamsBean;
        layoutParamsMatch = new ViewGroup.LayoutParams(widthPixels / mParamsBean.getColCount(), ViewGroup.LayoutParams.WRAP_CONTENT);
        imageLp = new LinearLayout.LayoutParams(mParamsBean.getIconWidth(), mParamsBean.getIconHeight());
        textLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textLp.topMargin = mParamsBean.getTextIconMargin();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gridpager_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FlexboxLayout flexboxLayout = holder.mFlexBox;
        flexboxLayout.removeAllViews();
        // 循环添加每页数据
        int pageSizeCount = mParamsBean.getPageSize();
        // 如果是最后一页，判断最后一页是否够每页的大小
        if (position == getItemCount() - 1) {
            pageSizeCount = mParamsBean.getDataAllCount() % mParamsBean.getPageSize() > 0 ? mParamsBean.getDataAllCount() % mParamsBean.getPageSize() : mParamsBean.getPageSize();
        }
        for (int i = 0; i < pageSizeCount; i++) {
            View view = View.inflate(mContext, R.layout.gridpager_item, null);
            LinearLayout layout = view.findViewById(R.id.ll_layout);
            layout.setLayoutParams(layoutParamsMatch);
            ImageView imageView = view.findViewById(R.id.item_image);
            imageView.setLayoutParams(imageLp);
            TextView textView = view.findViewById(R.id.item_text);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mParamsBean.getTextSize());
            textView.setTextColor(mParamsBean.getTextColor());
            textView.setLayoutParams(textLp);
            if (mParamsBean.getImageLoaderCallback() != null) {
                mParamsBean.getImageLoaderCallback().displayImageText(imageView, textView, position * mParamsBean.getPageSize() + i);
            }
            int finalI = i;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mParamsBean.getItemClickListener() != null) {
                        mParamsBean.getItemClickListener().itemClick(position * mParamsBean.getPageSize() + finalI);
                    }
                }
            });
            flexboxLayout.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setNewData(List<String> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    /**
     * item点击回调
     */
    public interface GridItemClickListener {
        void itemClick(int position);
    }
    /**
     * 图片加载
     */
    public interface ImageTextLoaderCallback {
        void displayImageText(ImageView imageView, TextView textView, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        FlexboxLayout mFlexBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFlexBox = itemView.findViewById(R.id.flex_layout);
        }
    }

    public static class ParamsBean {
        private int mColCount;
        private int mIconWidth, mIconHeight;
        private int mTextIconMargin;
        private int mPageSize;
        private int mDataAllCount;
        private int mTextSize;
        private int mTextColor;
        private GridItemClickListener mItemClickListener;
        private ImageTextLoaderCallback mImageLoaderCallback;

        public GridItemClickListener getItemClickListener() {
            return mItemClickListener;
        }

        public void setItemClickListener(GridItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;
        }

        public ImageTextLoaderCallback getImageLoaderCallback() {
            return mImageLoaderCallback;
        }

        public void setImageLoaderCallback(ImageTextLoaderCallback mImageLoaderCallback) {
            this.mImageLoaderCallback = mImageLoaderCallback;
        }

        public int getColCount() {
            return mColCount;
        }

        public void setColCount(int mColCount) {
            this.mColCount = mColCount;
        }

        public int getIconWidth() {
            return mIconWidth;
        }

        public void setIconWidth(int mIconWidth) {
            this.mIconWidth = mIconWidth;
        }

        public int getIconHeight() {
            return mIconHeight;
        }

        public void setIconHeight(int mIconHeight) {
            this.mIconHeight = mIconHeight;
        }

        public int getTextIconMargin() {
            return mTextIconMargin;
        }

        public void setTextIconMargin(int mTextIconMargin) {
            this.mTextIconMargin = mTextIconMargin;
        }

        public int getPageSize() {
            return mPageSize;
        }

        public void setPageSize(int mPageSize) {
            this.mPageSize = mPageSize;
        }

        public int getDataAllCount() {
            return mDataAllCount;
        }

        public void setDataAllCount(int mDataAllCount) {
            this.mDataAllCount = mDataAllCount;
        }

        public int getTextSize() {
            return mTextSize;
        }

        public void setTextSize(int mTextSize) {
            this.mTextSize = mTextSize;
        }

        public int getTextColor() {
            return mTextColor;
        }

        public void setTextColor(int mTextColor) {
            this.mTextColor = mTextColor;
        }
    }
}
