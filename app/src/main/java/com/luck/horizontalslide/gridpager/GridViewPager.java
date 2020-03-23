package com.luck.horizontalslide.gridpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.horizontalslide.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * recycleview的方式来实现美团app的首页标签效果
 */
public class GridViewPager extends FrameLayout {

    private RecyclerView mRvPage;
    private ImageView mIvBg;

    private GridViewPagerAdapter mPagerAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    /**
     * 列表
     */
    //子控件显示的宽度
    private int mIndicatorChildWidth = 8;
    //子控件显示的高度
    private int mIndicatorChildHeight = 8;
    //两个子控件之间的间距
    private int mIndicatorChildMargin = 8;
    //正常情况下显示的颜色
    private int mIndicatorChildNormalColor = Color.GRAY;
    //选中的时候现实的颜色
    private int mIndicatorChildSelectColor = Color.RED;
    // 是否是圆形的指示点
    private boolean mIndicatorChildIsCircle = true;
    // 是否需要显示指示器
    private boolean mIsDisplayIndicator = true;
    // 指示器与page间距
    private int mIndicatorMarginTop = 10;
    // 指示器与底部间距
    private int mIndicatorMarginBottom = 10;

    /**
     * 指示点
     */
    private IndicatorView mIndicatorView;
    // page上间距
    private int mPagerMarginTop = 10;
    // page下间距
    private int mPagerMarginBottom = 10;
    // 行间距间距
    private int mVerticalSpacing = 10;
    // icon 宽度
    private int mIconWidth = 50;
    // icon 高度
    private int mIconHeight = 50;
    // 文字颜色
    private int mTextColor = Color.BLACK;
    // 文字大小
    private int mTextSize = 10;
    // icon 文字 的间距
    private int mTextIconMargin = 5;
    // 行数
    private int mRowCount = 2;
    // 列数
    private int mColCount = 4;
    // 每页大小
    private int mPageSize = 8;
    // 数据总数
    private int mDataAllCount = 0;
    // 背景颜色
    private int mBackgroundColor = Color.WHITE;
    //设置的列数
    private int mSettingRowCount = 2;

    /**
     * item点击监听
     */
    private GridViewPagerAdapter.GridItemClickListener mGridItemClickListener;

    private GridViewPagerAdapter.ImageTextLoaderCallback mImageTextLoaderInterface;

    private BackgroundImageLoaderInterface backgroundImageLoaderInterface;

    public GridViewPager(Context context) {
        this(context, null);
    }

    public GridViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleTypedArray(attrs);
        initView();
        setBackgroundColor(mBackgroundColor);
    }

    /**
     * 添加布局
     */
    private void initView() {
        View view = View.inflate(getContext(), R.layout.gridpager_layout, null);
        mIvBg = view.findViewById(R.id.iv_bg);
        mRvPage = view.findViewById(R.id.recycleview);
        mIndicatorView = view.findViewById(R.id.scv);
        addView(view);
        // 设置分页滑动
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRvPage);
        //
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRvPage.setLayoutManager(mLinearLayoutManager);
        // 滚动监听
        mRvPage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_IDLE:
                        // recyclerview已经停止滚动
                        // 设置指示点
                        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                        mIndicatorView.setSelectPosition(firstVisibleItem);
                        break;
                    case SCROLL_STATE_DRAGGING:
                        // recyclerview正在被拖拽
                        break;
                    case SCROLL_STATE_SETTLING:
                        // recyclerview正在依靠惯性滚动
                        break;
                }
            }
        });
    }

    /**
     * 初始化参数
     *
     * @param attrs
     */
    private void handleTypedArray(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.GridViewPager);
        mPagerMarginTop = typedArray.getDimensionPixelSize(R.styleable.GridViewPager_pager_marginTop, AndDensityUtils.dip2px(getContext(), mPagerMarginTop));
        mPagerMarginBottom = typedArray.getDimensionPixelSize(R.styleable.GridViewPager_pager_marginBottom, AndDensityUtils.dip2px(getContext(), mPagerMarginBottom));
        mVerticalSpacing = typedArray.getDimensionPixelSize(R.styleable.GridViewPager_page_verticalSpacing, AndDensityUtils.dip2px(getContext(), mVerticalSpacing));
        mBackgroundColor = typedArray.getColor(R.styleable.GridViewPager_page_backgroundColor, Color.WHITE);
        mIconWidth = typedArray.getDimensionPixelSize(R.styleable.GridViewPager_page_imgWidth, AndDensityUtils.dip2px(getContext(), mIconWidth));
        mIconHeight = typedArray.getDimensionPixelSize(R.styleable.GridViewPager_page_imgHeight, AndDensityUtils.dip2px(getContext(), mIconHeight));
        mTextColor = typedArray.getColor(R.styleable.GridViewPager_page_textColor, Color.BLACK);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.GridViewPager_page_textSize, AndDensityUtils.sp2px(getContext(), mTextSize));
        mTextIconMargin = typedArray.getDimensionPixelSize(R.styleable.GridViewPager_page_imgTextMargin, AndDensityUtils.dip2px(getContext(), mTextIconMargin));
        mRowCount = typedArray.getInt(R.styleable.GridViewPager_page_rowCount, mRowCount);
        mColCount = typedArray.getInt(R.styleable.GridViewPager_page_colCount, mColCount);
        mSettingRowCount = mRowCount;
        // 指示器
        mIndicatorChildWidth = typedArray.getDimensionPixelSize(R.styleable.GridViewPager_indicator_childWidth, AndDensityUtils.dip2px(getContext(), mIndicatorChildWidth));
        mIndicatorChildHeight = typedArray.getDimensionPixelSize(R.styleable.GridViewPager_indicator_childHeight, AndDensityUtils.dip2px(getContext(), mIndicatorChildHeight));
        mIndicatorChildMargin = typedArray.getDimensionPixelSize(R.styleable.GridViewPager_indicator_childMargin, AndDensityUtils.dip2px(getContext(), mIndicatorChildMargin));
        mIndicatorChildNormalColor = typedArray.getColor(R.styleable.GridViewPager_indicator_normalColor, Color.GRAY);
        mIndicatorChildSelectColor = typedArray.getColor(R.styleable.GridViewPager_indicator_selectColor, Color.RED);
        mIndicatorChildIsCircle = typedArray.getBoolean(R.styleable.GridViewPager_indicator_isCircle, true);
        mIsDisplayIndicator = typedArray.getBoolean(R.styleable.GridViewPager_indicator_isDisplay, true);
        mIndicatorMarginTop = typedArray.getDimensionPixelSize(R.styleable.GridViewPager_indicator_marginTop, mVerticalSpacing);
        mIndicatorMarginBottom = typedArray.getDimensionPixelSize(R.styleable.GridViewPager_indicator_marginBottom, mVerticalSpacing);
        typedArray.recycle();
    }

    /**
     * 设置数据总数
     * 并同时计算行数，不大于设置的行数
     *
     * @param mDataAllCount
     * @return
     */
    public GridViewPager setDataAllCount(int mDataAllCount) {
        if (mDataAllCount > 0) {
            this.mDataAllCount = mDataAllCount;
            if (mDataAllCount < mColCount) {
                mRowCount = 1;
            } else {
                int temp = (int) Math.ceil(mDataAllCount * 1f / mColCount);
                if (temp < mSettingRowCount) {
                    mRowCount = temp;
                } else {
                    mRowCount = mSettingRowCount;
                }
            }
        }
        return this;
    }

    /**
     * 设置列数
     *
     * @param mColCount
     * @return
     */
    public GridViewPager setColCount(int mColCount) {
        if (mColCount > 0) {
            this.mColCount = mColCount;
        }
        return this;
    }

    /**
     * 设置行数
     *
     * @param mRowCount
     * @return
     */
    public GridViewPager setRowCount(int mRowCount) {
        if (mRowCount > 0) {
            this.mRowCount = mRowCount;
            this.mSettingRowCount = mRowCount;
        }
        return this;
    }

    /**
     * 上下边距
     *
     * @param mPagerMarginTop
     */
    public GridViewPager setPagerMarginTop(int mPagerMarginTop) {
        this.mPagerMarginTop = AndDensityUtils.dip2px(getContext(), mPagerMarginTop);
        return this;
    }

    public GridViewPager setPagerMarginBottom(int mPagerMarginBottom) {
        this.mPagerMarginBottom = AndDensityUtils.dip2px(getContext(), mPagerMarginBottom);
        return this;
    }

    /**
     * 设置 纵向间距
     *
     * @param mVerticalSpacing
     * @return
     */
    public GridViewPager setVerticalSpacing(int mVerticalSpacing) {
        this.mVerticalSpacing = AndDensityUtils.dip2px(getContext(), mVerticalSpacing);
        return this;
    }

    /**
     * 设置 icon 宽度
     *
     * @param mIconWidth
     * @return
     */
    public GridViewPager setIconWidth(int mIconWidth) {
        this.mIconWidth = AndDensityUtils.dip2px(getContext(), mIconWidth);
        return this;
    }

    /**
     * 设置 icon 高度
     *
     * @param mIconHeight
     * @return
     */
    public GridViewPager setIconHeight(int mIconHeight) {
        this.mIconHeight = AndDensityUtils.dip2px(getContext(), mIconHeight);
        return this;
    }

    /**
     * 设置 字体颜色
     *
     * @param mTextColor
     * @return
     */
    public GridViewPager setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        return this;
    }

    /**
     * 设置 背景颜色
     *
     * @param backgroundColor
     * @return
     */
    public GridViewPager setGridViewPagerBackgroundColor(int backgroundColor) {
        setBackgroundColor(backgroundColor);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param mTextSize
     * @return
     */
    public GridViewPager setTextSize(int mTextSize) {
        this.mTextSize = AndDensityUtils.sp2px(getContext(), mTextSize);
        return this;
    }

    /**
     * 设置字体与icon的间距
     *
     * @param mTextIconMargin
     * @return
     */
    public GridViewPager setTextIconMargin(int mTextIconMargin) {
        this.mTextIconMargin = AndDensityUtils.dip2px(getContext(), mTextIconMargin);
        return this;
    }

    /**
     * 设置指示器的宽度
     *
     * @param mChildWidth
     * @return
     */
    public GridViewPager setPointChildWidth(int mChildWidth) {
        this.mIndicatorChildWidth = AndDensityUtils.dip2px(getContext(), mChildWidth);
        return this;
    }

    /**
     * 设置指示器的高度
     *
     * @param mChildHeight
     * @return
     */
    public GridViewPager setIndicatorChildHeight(int mChildHeight) {
        this.mIndicatorChildHeight = AndDensityUtils.dip2px(getContext(), mChildHeight);
        return this;
    }

    /**
     * 设置指示器的间距
     *
     * @param mChildMargin
     * @return
     */
    public GridViewPager setIndicatorChildMargin(int mChildMargin) {
        this.mIndicatorChildMargin = AndDensityUtils.dip2px(getContext(), mChildMargin);
        return this;
    }

    /**
     * 设置指示器是否为圆形
     *
     * @param mIsCircle
     * @return
     */
    public GridViewPager setIndicatorIsCircle(boolean mIsCircle) {
        this.mIndicatorChildIsCircle = mIsCircle;
        return this;
    }

    /**
     * 设置指示器未选中颜色
     *
     * @param mNormalColor
     * @return
     */
    public GridViewPager setIndicatorNormalColor(int mNormalColor) {
        this.mIndicatorChildNormalColor = mNormalColor;
        return this;
    }

    /**
     * 设置指示器选中的颜色
     *
     * @param mSelectColor
     * @return
     */
    public GridViewPager setIndicatorSelectColor(int mSelectColor) {
        this.mIndicatorChildSelectColor = mSelectColor;
        return this;
    }

    /**
     * 设置指示器是否显示
     *
     * @param mIsDisplay
     * @return
     */
    public GridViewPager setIndicatorIsDisplay(boolean mIsDisplay) {
        this.mIsDisplayIndicator = mIsDisplay;
        return this;
    }

    /**
     * 设置指示器与page的间距
     *
     * @param mIndicatorMarginTop
     * @return
     */
    public GridViewPager setIndicatorMarginTop(int mIndicatorMarginTop) {
        this.mIndicatorMarginTop = AndDensityUtils.dip2px(getContext(), mIndicatorMarginTop);
        return this;
    }

    /**
     * 设置指示器与底部的间距
     *
     * @param mIndicatorMarginBottom
     * @return
     */
    public GridViewPager setIndicatorMarginBottom(int mIndicatorMarginBottom) {
        this.mIndicatorMarginBottom = AndDensityUtils.dip2px(getContext(), mIndicatorMarginBottom);
        return this;
    }

    /**
     * 设置 Item 点击监听
     *
     * @param mGridItemClickListener
     */
    public GridViewPager setGridItemClickListener(GridViewPagerAdapter.GridItemClickListener mGridItemClickListener) {
        this.mGridItemClickListener = mGridItemClickListener;
        return this;
    }


    /**
     * 设置 图片加载
     *
     * @param imageTextLoaderInterface
     */
    public GridViewPager setImageTextLoaderCallback(GridViewPagerAdapter.ImageTextLoaderCallback imageTextLoaderInterface) {
        this.mImageTextLoaderInterface = imageTextLoaderInterface;
        return this;
    }

    /**
     * 设置背景图片
     *
     * @param backgroundImageLoaderInterface
     * @return
     */
    public GridViewPager setBackgroundImageLoader(BackgroundImageLoaderInterface backgroundImageLoaderInterface) {
        this.backgroundImageLoaderInterface = backgroundImageLoaderInterface;
        return this;
    }

    /**
     * 显示
     */
    public void show() {
        if (mDataAllCount == 0) {
            return;
        }
        // 设置高度
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, getAutoHeight());
        rl.topMargin = mPagerMarginTop;
        rl.bottomMargin = mPagerMarginBottom;
        mRvPage.setLayoutParams(rl);
        // 总页数
        final int page = getTotalPageCount();
        // 显示指示器
        mIndicatorView.setVisibility((mIsDisplayIndicator && page > 1) ? View.VISIBLE : View.GONE);
        if (mIsDisplayIndicator && page > 1) {
            RelativeLayout.LayoutParams pointParams = (RelativeLayout.LayoutParams) mIndicatorView.getLayoutParams();
            pointParams.topMargin = mIndicatorMarginTop;
            pointParams.bottomMargin = mIndicatorMarginBottom;
            mIndicatorView.setLayoutParams(pointParams);
            // 设置指示器
            mIndicatorView
                    .setChildWidth(mIndicatorChildWidth)
                    .setChildHeight(mIndicatorChildHeight)
                    .setChildMargin(mIndicatorChildMargin)
                    .setIsCircle(mIndicatorChildIsCircle)
                    .setNormalColor(mIndicatorChildNormalColor)
                    .setSelectColor(mIndicatorChildSelectColor)
                    .setPointCheckedChangeListener(new IndicatorView.PointCheckedChangeListener() {
                        @Override
                        public void checkedChange(int position) {
                            if (position >= 0 && position < page) {
                                // 指示点点击，滚动到对应的页
                                mLinearLayoutManager.scrollToPositionWithOffset(position, 0);
                            }
                        }
                    })
                    .addChild(page);
        }
        // 设置背景图片
        if (backgroundImageLoaderInterface != null) {
            RelativeLayout.LayoutParams bgImageViewRl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, getAllHeight());
            mIvBg.setLayoutParams(bgImageViewRl);
            backgroundImageLoaderInterface.setBackgroundImg(mIvBg);
        }

        // 设置数据
        setAdapter(page);
    }

    /**
     * 获取总页数
     *
     * @return
     */
    private int getTotalPageCount() {
        // 每页数据大小
        mPageSize = mRowCount * mColCount;
        return mDataAllCount / mPageSize + (mDataAllCount % mPageSize > 0 ? 1 : 0);
    }

    /**
     * 动态计算view高度
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽-测量规则的模式和大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // 设置wrap_content的默认宽 / 高值
        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, getAllHeight());
        }
    }

    /**
     * 设置数据
     */
    private List<String> stringList = new ArrayList<>();

    private void setAdapter(int page) {
        stringList.clear();
        for (int i = 0; i < page; i++) {
            stringList.add(i + "");
        }
        GridViewPagerAdapter.ParamsBean bean = new GridViewPagerAdapter.ParamsBean();
        bean.setColCount(mColCount);
        bean.setDataAllCount(mDataAllCount);
        bean.setIconHeight(mIconHeight);
        bean.setIconWidth(mIconWidth);
        bean.setPageSize(mPageSize);
        bean.setTextColor(mTextColor);
        bean.setTextIconMargin(mTextIconMargin);
        bean.setTextSize(mTextSize);
        bean.setImageLoaderCallback(mImageTextLoaderInterface);
        bean.setItemClickListener(mGridItemClickListener);
        if (mPagerAdapter == null) {
            mPagerAdapter = new GridViewPagerAdapter(getContext());
            mPagerAdapter.setParamsBean(bean);
            mRvPage.setAdapter(mPagerAdapter);
            mPagerAdapter.setNewData(stringList);
        } else {
            mPagerAdapter.setParamsBean(bean);
            mPagerAdapter.setNewData(stringList);
        }
    }

    /**
     * 刷新指定页码数据（每页行列数、数据总数不变，只有某个数据的值改变时使用）
     *
     * @param position
     */
    public void notifyItemChanged(int position) {
        // 总页数
        int page = mDataAllCount / mPageSize + (mDataAllCount % mPageSize > 0 ? 1 : 0);
        if (position >= 0 && position < page && mPagerAdapter != null) {
            mPagerAdapter.notifyItemChanged(position);
        }
    }

    /**
     * 计算recycleview高度
     *
     * @return
     */
    private int getAutoHeight() {
        return getOnesHeight() * mRowCount + (mRowCount - 1) * mVerticalSpacing;
    }

    /**
     * 获取一行的高度
     *
     * @return
     */
    private int getOnesHeight() {
        return (int) (mIconHeight + mTextIconMargin + mTextSize * 1.133);
    }

    /**
     * 计算总高度
     *
     * @return
     */
    private int getAllHeight() {
        // 总高
        int page = getTotalPageCount();
        int recycleviewH = getAutoHeight();
        if (mIsDisplayIndicator && page > 1) {
            recycleviewH += mPagerMarginTop + mPagerMarginBottom + mIndicatorMarginTop + mIndicatorMarginBottom + mIndicatorChildHeight;
        } else {
            recycleviewH += mPagerMarginTop + mPagerMarginBottom;
        }
        return recycleviewH;
    }

    public interface BackgroundImageLoaderInterface {
        void setBackgroundImg(ImageView bgImageView);
    }
}
