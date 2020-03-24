package com.luck.horizontalslide.gridpager;

/**
 * ============================================================
 * 作 者 : 李桐桐
 * 创建日期 ： 2020-03-23 18:11
 * 描 述 :
 * ============================================================
 **/

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {

    private int mIndicatorChildSelectColor = Color.RED;
    private int mIndicatorChildNormalColor = Color.GRAY;

    /**
     * 指示器整体高度
     */
    private int mIndicatorHeight = 16;

    /**
     * 指示器画笔线条宽度，即指示器子View高度
     */
    private int mIndicatorChildHeight = 2;

    /**
     * 指示器宽度
     */
    private int mIndicatorChildWidth = 16;
    /**
     * 指示器子view间距
     */
    private int mIndicatorItemPadding = 4;

    // 是否需要显示指示器
    private boolean mIsDisplayIndicator = true;

    // 指示器与page间距
    private int mIndicatorMarginTop = 10;
    // 指示器与底部间距
    private int mIndicatorMarginBottom = 10;

    /**
     * 滑动动画插值器，开始和结束速度变慢
     */
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private final Paint mPaint = new Paint();
    private Context mContext;

    public LinePagerIndicatorDecoration(Context context) {
        mContext = context;
        mPaint.setStrokeWidth((float)mIndicatorChildHeight);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int itemCount = parent.getAdapter().getItemCount();
        if(itemCount < 2 || !mIsDisplayIndicator) return;
        // center horizontally, calculate width and subtract half from center
        float totalLength = mIndicatorChildWidth * itemCount;
        float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

        // center vertically in the allotted space
        float indicatorPosY = parent.getHeight() - mIndicatorMarginBottom - mIndicatorChildHeight;

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);


        // find active page (which should be highlighted)
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int activePosition = layoutManager.findFirstVisibleItemPosition();
        if (activePosition == RecyclerView.NO_POSITION) {
            return;
        }

        // find offset of active page (if the user is scrolling)
        final View activeChild = layoutManager.findViewByPosition(activePosition);
        int left = activeChild.getLeft();
        int width = activeChild.getWidth();

        // on swipe the active item will be positioned from [-width, 0]
        // interpolate offset for smooth animation
        float progress = mInterpolator.getInterpolation(left * -1 / (float) width);

        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount);
    }

    private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
        mPaint.setColor(mIndicatorChildNormalColor);

        // width of item indicator including padding
        final float itemWidth = mIndicatorChildWidth + mIndicatorItemPadding;

        float start = indicatorStartX;
        for (int i = 0; i < itemCount; i++) {
            // draw the line for every item
            c.drawLine(start, indicatorPosY, start + mIndicatorChildWidth, indicatorPosY, mPaint);
            start += itemWidth;
        }
    }

    private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                int highlightPosition, float progress, int itemCount) {
        mPaint.setColor(mIndicatorChildSelectColor);

        // width of item indicator including padding
        final float itemWidth = mIndicatorChildWidth + mIndicatorItemPadding;

        if (progress == 0F) {
            // no swipe, draw a normal indicator
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;
            c.drawLine(highlightStart, indicatorPosY,
                    highlightStart + mIndicatorChildWidth, indicatorPosY, mPaint);
        } else {
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;
            // calculate partial highlight
            float partialLength = mIndicatorChildWidth * progress;

            // draw the cut off highlight
            c.drawLine(highlightStart + partialLength, indicatorPosY,
                    highlightStart + mIndicatorChildWidth, indicatorPosY, mPaint);

            // draw the highlight overlapping to the next item as well
            if (highlightPosition < itemCount - 1) {
                highlightStart += itemWidth;
                c.drawLine(highlightStart, indicatorPosY,
                        highlightStart + partialLength, indicatorPosY, mPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemCount = parent.getAdapter().getItemCount();
        if(itemCount < 2 || !mIsDisplayIndicator) return;
        outRect.bottom = mIndicatorHeight;
    }

    public void setIndicatorChildSelectColor(int mIndicatorChildSelectColor) {
        this.mIndicatorChildSelectColor = mIndicatorChildSelectColor;
    }

    public void setIndicatorChildNormalColor(int mIndicatorChildNormalColor) {
        this.mIndicatorChildNormalColor = mIndicatorChildNormalColor;
    }

    public void setIndicatorChildHeight(int mIndicatorChildHeight) {
        this.mIndicatorChildHeight = mIndicatorChildHeight;
        mPaint.setStrokeWidth((float)this.mIndicatorChildHeight);
        calculateIndicatorHeight();
    }

    public void setIndicatorChildWidth(int mIndicatorChildWidth) {
        this.mIndicatorChildWidth = mIndicatorChildWidth;
    }

    public void setIndicatorItemPadding(int mIndicatorItemPadding) {
        this.mIndicatorItemPadding = mIndicatorItemPadding;
    }

    public void setIsDisplayIndicator(boolean mIsDisplayIndicator) {
        this.mIsDisplayIndicator = mIsDisplayIndicator;
    }

    public void setIndicatorMarginTop(int mIndicatorMarginTop) {
        this.mIndicatorMarginTop = mIndicatorMarginTop;
        calculateIndicatorHeight();
    }

    public void setIndicatorMarginBottom(int mIndicatorMarginBottom) {
        this.mIndicatorMarginBottom = mIndicatorMarginBottom;
        calculateIndicatorHeight();
    }

    /**
     * 计算指示器高度
     */
    private void calculateIndicatorHeight() {
        mIndicatorHeight = mIndicatorMarginTop + mIndicatorMarginBottom+ mIndicatorChildHeight;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());
    }
}
