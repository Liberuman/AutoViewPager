package com.sxu.autoviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.Scroller;

/*******************************************************************************
 * Description: 实现ViewPager左拉加载更多的功能
 *
 * Author: Freeman
 *
 * Date: 2018/5/3
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class LoadMoreViewPager extends ViewPager {

	private int miniVelocity;
	private int itemWidth;
	private int scrollRange;
	private VelocityTracker velocityTracker;
	private Scroller scroller;

	public LoadMoreViewPager(Context context) {
		this(context, null);
	}

	public LoadMoreViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		scroller = new Scroller(context);
		velocityTracker = VelocityTracker.obtain();
		ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
		miniVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
	}

	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		Log.i("out", "deltax==" + deltaX + " scrollX=" + scrollX + " scrollRangeX=" + scrollRangeX + " maxOverScrollX=" + maxOverScrollX);
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, 300, scrollRangeY, 300, maxOverScrollY, true);
	}

//	@Override
//	public void computeScroll() {
//		super.computeScroll();
//		if (!scroller.isFinished() && scroller.computeScrollOffset()) {
//			Log.i("out", "currentX==" + scroller.getCurrY());
//			scrollTo(-scroller.getCurrX(), 0);
//
//			invalidate();
//		} else {
//			if (itemWidth != 0) {
//				int index = Math.round(scroller.getCurrX() / itemWidth);
//				scrollTo(index * itemWidth, 0);
//			}
//		}
//	}

	private int getMaxX() {
		if (scrollRange == 0) {
			int count;
			if (getAdapter() != null && (count = getAdapter().getCount()) > 0) {
				Log.i("out", "pageWidth=" + getAdapter().getPageWidth(0) * getMeasuredWidth() + " pageMargin=" + getPageMargin());
				itemWidth = (int) (getAdapter().getPageWidth(0) + getPageMargin());
				scrollRange = (int) (count * getAdapter().getPageWidth(0) * getMeasuredWidth()
						+ (count - 1) * getPageMargin());
			}
		}

		return scrollRange;
	}

//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//		velocityTracker.addMovement(ev);
//		if (ev.getActionMasked() == MotionEvent.ACTION_UP) {
//			velocityTracker.computeCurrentVelocity(1000);
//			int velocityX = (int) velocityTracker.getXVelocity();
//			Log.i("out", "velocityX=" + velocityX + " miniVelocity=" + miniVelocity + " maxX=" + getMaxX());
//			if (velocityX > miniVelocity) {
//				scroller.fling(getScrollX(), getScrollY(), velocityX, 0, 0, getMaxX(), 0, 0);
//			}
//			return false;
//		}
//
//		return super.onTouchEvent(ev);
//	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		velocityTracker.recycle();
		velocityTracker = null;
	}
}
