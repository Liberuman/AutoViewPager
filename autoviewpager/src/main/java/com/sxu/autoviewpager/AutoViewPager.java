package com.sxu.autoviewpager;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/*******************************************************************************
 * FileName: AutoViewPager
 * <p/>
 * Description: 可自动播放的ViewPager
 * <p/>
 * Author: juhg
 * <p/>
 * Version: v1.0
 * <p/>
 * Date: 16/8/1
 * <p/>
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class AutoViewPager extends RelativeLayout implements MyViewPager.OnPageChangeListener, Runnable {

    private int mDefaultPager;
    private int mItemCount;
    private int mIndicatorSize;
    private int mIndicatorLayoutMargin;
    private int mIndicatorGap;
    private int mAutoSwitchTime;
    private int mIndicatorGravity;
    private int mIndicatorNormalIcon;
    private int mIndicatorSelectedIcon;
    private int mPagerMargin;
    private int mPagerWidth;
    private int mPagerPaddingLeft;
    private int mPagerPaddingRight;
    private int mTransformer;
    private float mDefaultAlpha;
    private float mDefaultScale;
    private boolean mIsAutoPlay;
    private boolean mLoopPlay;
    private boolean mShowIndicator;

    private LinearLayout indicatorLayout;
    private MyViewPager viewPager;
    private Context context;
    private ImageView[] indicatorIcons;
    private OnItemView itemView;
    private Handler handler = new Handler();

    public final static int INDICATOR_GRAVITY_TOP = 1;
    public final static int INDICATOR_GRAVITY_BOTTOM = 2;

    public final static int TRANSFORM_TYPE_NONE = 0;
    public final static int TRANSFORM_TYPE_FADE = 1;
    public final static int TRANSFORM_TYPE_SCALE = 2;
    public final static int TRANSFORM_TYPE_ROTATE = 3;
    public final static int TRANSFORM_TYPE_FLIP = 4;
    public final static int TRANSFORM_TYPE_SQUARE = 5;
    public final static int TRANSFORM_TYPE_TURNTABLE = 6;
    public final static int TRANSFORM_TYPE_CASCADING = 7;

    public AutoViewPager(Context context) {
        this(context, null);
    }

    public AutoViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoViewPager(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AutoViewPager);
        mIndicatorSize = (int)array.getDimension(R.styleable.AutoViewPager_indicatorIconSize, 0);
        mIndicatorLayoutMargin = (int) array.getDimension(R.styleable.AutoViewPager_indicatorLayoutMargin, 10);
        mIndicatorGap = (int) array.getDimension(R.styleable.AutoViewPager_indicatorGap, 10);
        mIndicatorNormalIcon = array.getResourceId(R.styleable.AutoViewPager_indicatorNormalIcon, 0);
        mIndicatorSelectedIcon = array.getResourceId(R.styleable.AutoViewPager_indicatorSelectedIcon, 0);
        mAutoSwitchTime = array.getInteger(R.styleable.AutoViewPager_autoSwitchTime, 4000);
        mIsAutoPlay = array.getBoolean(R.styleable.AutoViewPager_isAutoPlay, false);
        mLoopPlay = array.getBoolean(R.styleable.AutoViewPager_loopPlay, false);
        mShowIndicator = array.getBoolean(R.styleable.AutoViewPager_showIndicator, false);
        mIndicatorGravity = array.getInt(R.styleable.AutoViewPager_indicatorGravity, INDICATOR_GRAVITY_BOTTOM);
        mPagerMargin = array.getDimensionPixelOffset(R.styleable.AutoViewPager_pagerMargin, 0);
        mPagerPaddingLeft = array.getDimensionPixelOffset(R.styleable.AutoViewPager_pagerPaddingLeft, 0);
        mPagerPaddingRight = array.getDimensionPixelOffset(R.styleable.AutoViewPager_pagerPaddingRight, 0);
        mPagerWidth = array.getDimensionPixelOffset(R.styleable.AutoViewPager_pagerWidth, 0);
        mTransformer = array.getInt(R.styleable.AutoViewPager_transformer, TRANSFORM_TYPE_NONE);
        array.recycle();
        init();
    }

    private void init() {
        viewPager = new MyViewPager(context);
        viewPager.setId(generateId());
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(mPagerMargin);
        viewPager.setPadding(mPagerPaddingLeft, 0, mPagerPaddingRight, 0);
        viewPager.addOnPageChangeListener(this);
        setPagerTransformer();
        addView(viewPager);
    }

    private void setPagerTransformer() {
        MyViewPager.PageTransformer pageTransformer = null;
        switch (mTransformer) {
            case TRANSFORM_TYPE_FADE:
                pageTransformer = PageTransformerFactory.fadeTransformer;
                break;
            case TRANSFORM_TYPE_SCALE:
                pageTransformer = PageTransformerFactory.getScaleTransformer(viewPager, mPagerPaddingLeft, mDefaultScale, mDefaultAlpha);
                break;
            case TRANSFORM_TYPE_ROTATE:
                pageTransformer = PageTransformerFactory.rotateTransformer;
                break;
            case TRANSFORM_TYPE_FLIP:
                pageTransformer = PageTransformerFactory.flipTransformer;
                break;
            case TRANSFORM_TYPE_SQUARE:
                pageTransformer = PageTransformerFactory.squareTransformer;
                break;
            case TRANSFORM_TYPE_TURNTABLE:
                pageTransformer = PageTransformerFactory.turntableTransformer;
                break;
            case TRANSFORM_TYPE_CASCADING:
                pageTransformer = PageTransformerFactory.cascadingTransformer;
                break;
            default:
                break;
        }

        if (pageTransformer != null) {
            viewPager.setPageTransformer(true, pageTransformer);
        }
    }

    public int getCurrentItem() {
        return viewPager.getCurrentItem() % mItemCount;
    }

    public void setCurrentItem(int index) {
        mDefaultPager = index;
    }

    public void setIndicatorIconSize(int size) {
        this.mIndicatorSize = size;
    }

    public void setIndicatorLayoutMargin(int margin) {
        this.mIndicatorLayoutMargin = margin;
    }

    public void setIndicatorGap(int gap) {
        this.mIndicatorGap = gap;
    }

    public void setmIndicatorNormalIcon(int resId) {
        mIndicatorNormalIcon = resId;
    }

    public void setmIndicatorSelectedIcon(int resId) {
        mIndicatorNormalIcon = resId;
    }

    public void setSwitchTime(int switchTime) {
        this.mAutoSwitchTime = switchTime;
    }

    public void setAutoPlay(boolean isAutoPlay) {
        this.mIsAutoPlay = isAutoPlay;
    }

    public void setLoopPlay(boolean isLoopPlay) {
        this.mLoopPlay = isLoopPlay;
    }

    public void setShowIndicator(boolean showIndicator) {
        this.mShowIndicator = showIndicator;
    }

    public void setPagerTransformer(MyViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(true, transformer);
    }

    public void setPagerTransformer(int transformer) {
        this.mTransformer = transformer;
        setPagerTransformer();
    }

    public void setDefaultScale(float defaultScale) {
        mDefaultScale = defaultScale;
        setPagerTransformer();
    }

    public void setDefaultAlpha(float defaultAlpha) {
        mDefaultAlpha = defaultAlpha;
        setPagerTransformer();
    }

    public void setVelocityLimit(int velocityLimit) {
        viewPager.setVelocityLimit(velocityLimit);
    }

    public void setPagerPaddingLeft(int pagerPaddingLeft) {
        this.mPagerPaddingLeft = pagerPaddingLeft;
        viewPager.setPadding(mPagerPaddingLeft, 0, mPagerPaddingRight, 0);
    }

    public void setmPagerPaddingRight(int pagerPaddingRight) {
        this.mPagerPaddingRight = pagerPaddingRight;
        viewPager.setPadding(mPagerPaddingLeft, 0, mPagerPaddingRight, 0);
    }

    public void setPagerPadding(int pagerPadding) {
        this.mPagerPaddingLeft = pagerPadding;
        this.mPagerPaddingRight = pagerPadding;
        viewPager.setPadding(mPagerPaddingLeft, 0, mPagerPaddingRight, 0);
    }

    public int getPagerPaddingLeft() {
        return mPagerPaddingLeft;
    }

    public int getPagerPaddingRight() {
        return mPagerPaddingRight;
    }


    public void setPagerMargin(int margin) {
        viewPager.setPageMargin(margin);
    }

    public int getPagerMargin() {
        return viewPager.getPageMargin();
    }

    public void setPagerWidth(int pagerWidth) {
        mPagerWidth = pagerWidth;
    }

    public int getPagerWidth() {
        return mPagerWidth;
    }


    @Override
    public void run() {
        if (mIsAutoPlay) {
            int index = viewPager.getCurrentItem();
            index++;
            viewPager.setCurrentItem(index);
        }
    }

    private void addIndicatorLayout() {
        indicatorLayout = new LinearLayout(context);
        indicatorLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        indicatorLayout.setOrientation(LinearLayout.HORIZONTAL);
        indicatorIcons = new ImageView[mItemCount];
        int gap = mIndicatorGap / 2;
        for (int i = 0; i < mItemCount; i++) {
            indicatorIcons[i] = new ImageView(context);
            LinearLayout.LayoutParams params;
            if (mIndicatorSize > 0) {
                params = new LinearLayout.LayoutParams(mIndicatorSize, mIndicatorSize);
            } else {
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            params.leftMargin = params.rightMargin = gap;
            if (mDefaultPager == i) {
                indicatorIcons[i].setImageResource(mIndicatorSelectedIcon);
            } else {
                indicatorIcons[i].setImageResource(mIndicatorNormalIcon);
            }
            indicatorLayout.addView(indicatorIcons[i], params);
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        if (mIndicatorGravity == INDICATOR_GRAVITY_TOP) {
            params.topMargin = mIndicatorLayoutMargin;
            params.addRule(ALIGN_TOP, viewPager.getId());
        } else {
            params.bottomMargin = mIndicatorLayoutMargin;
            params.addRule(ALIGN_BOTTOM, viewPager.getId());
        }
        addView(indicatorLayout, params);
    }

    public void setAdapter(int count) {
        if (count <= 0) {
            return;
        }

        this.mItemCount = count;
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOffscreenPageLimit(mItemCount);
        viewPager.setCurrentItem(mDefaultPager);
        if (mShowIndicator) {
            addIndicatorLayout();
        }

        if (mIsAutoPlay) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int index = viewPager.getCurrentItem();
                    index++;
                    viewPager.setCurrentItem(index);
                }
            }, mAutoSwitchTime);
        }
    }

    public void setAdapter(int count, int limit) {
        setAdapter(count);
        viewPager.setOffscreenPageLimit(limit);
    }

    /**
     * 为View生成唯一的Id
     * @return
     */
    private int generateId() {
        for (;;) {
            AtomicInteger sNextGeneratedId = new AtomicInteger(1);
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    private void resetIndicatorIcon(int position) {
        for (int i = 0; i < mItemCount; i++) {
            if (position == i) {
                indicatorIcons[i].setImageResource(mIndicatorSelectedIcon);
            } else {
                indicatorIcons[i].setImageResource(mIndicatorNormalIcon);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(final int position) {
        if (mShowIndicator) {
            resetIndicatorIcon(position % mItemCount);
        }
        if (mIsAutoPlay || mLoopPlay) {
            handler.removeCallbacks(this);
            handler.postDelayed(this, mAutoSwitchTime);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 设置切换过程的时间
     * @param duration
     */
    public void setDuration(int duration) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            MyScroller scroller = new MyScroller(viewPager.getContext(), new LinearInterpolator());
            field.set(viewPager, scroller);
            scroller.setDuration(duration);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void setItemView(OnItemView itemView) {
        this.itemView = itemView;
    }

    public interface OnItemView {
        View getView(ViewGroup container, int position);
    }

    public class MyPagerAdapter extends CustomPagerAdapter {

        @Override
        public float getPageWidth(int position) {
            if (mPagerWidth != 0) {
                return mPagerWidth * 1.0f / Resources.getSystem().getDisplayMetrics().widthPixels;
            }

            return super.getPageWidth(position);
        }

        @Override
        public int getCount() {
            if (mIsAutoPlay || mLoopPlay) {
                return Integer.MAX_VALUE;
            } else {
                return mItemCount;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return itemView.getView(container, position % mItemCount);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public static class MyScroller extends Scroller {

        private int mDuration;

        public MyScroller(Context context) {
            super(context);
        }

        public MyScroller(Context context,  Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            this.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setDuration(int duration) {
            this.mDuration = duration;
        }
    }
}
