package com.sxu.autoviewpager;

import android.view.View;

/*******************************************************************************
 * Description: MyViewPager动画
 *
 * Author: Freeman
 *
 * Date: 2018/5/3
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class PageTransformerFactory {

	public final static MyViewPager.PageTransformer getScaleTransformer(final MyViewPager viewPager,
                        final int mPagerPaddingLeft, final float defaultScale, final float defaultAlpha) {

		return new MyViewPager.PageTransformer() {
			@Override
			public void transformPage(View page, float position) {
				position = (page.getLeft() - (viewPager.getScrollX() + mPagerPaddingLeft)) * 1.0f / viewPager.getWidth();
				page.setPivotY(page.getHeight() / 2);
				if (position >= -1 && position <= 0) { // 当前页面滑出屏幕(0-->-1)
					float scaleRatio = (1 - defaultScale) * Math.abs(position);
					float alpha = (1 - defaultAlpha) * Math.abs(position);
					page.setScaleX(1 - scaleRatio);
					page.setScaleY(1 - scaleRatio);
					page.setPivotX(page.getWidth());
					page.setAlpha(1 - alpha);
				} else if (position > 0 && position <= 1) { // 新页面滑入(1-->0)
					float scaleRatio = (1 - defaultScale) * (1 - position);
					float alpha = (1 - defaultAlpha) * (1 - position);
					page.setPivotX(0);
					page.setScaleX(defaultScale + scaleRatio);
					page.setScaleY(defaultScale + scaleRatio);
					page.setAlpha(defaultAlpha + alpha);
				} else {
					/**
					 * Nothing
					 */
				}
			}
		};
	}

	public final static MyViewPager.PageTransformer scaleTransformer = new MyViewPager.PageTransformer() {
		@Override
		public void transformPage(View page, float position) {
			float defaultAlpha = 0.85f;
			float defaultScale = 0.6f;
			position = position - (int)position;
			if (position >= -1 && position <= 0) { // 当前页面滑出屏幕(0-->-1)
				float scaleRatio = (1 - defaultScale) * Math.abs(position);
				page.setScaleX(1 - scaleRatio);
				page.setScaleY(1 - scaleRatio);
				page.setPivotX(0f);
				page.setPivotX(page.getWidth());
				float alpha = (1 - defaultAlpha) * Math.abs(position);
				page.setAlpha(1 - alpha);
			} else if (position > 0 && position <= 1) { // 新页面滑入(1-->0)
				float scaleRatio = (1 - defaultScale) * (1 - position);
				page.setPivotX(0);
				page.setScaleX(defaultScale + scaleRatio);
				page.setScaleY(defaultScale + scaleRatio);
				float alpha = (1 - defaultAlpha) * (1 - position);
				page.setAlpha(defaultAlpha + alpha);
			} else {
				/**
				 * Nothing
				 */
			}
		}
	};

	public final static MyViewPager.PageTransformer fadeTransformer = new MyViewPager.PageTransformer() {
		@Override
		public void transformPage(View page, float position) {
			page.setAlpha(1 - Math.abs(position));
		}
	};

	public final static MyViewPager.PageTransformer flipTransformer = new MyViewPager.PageTransformer() {
		@Override
		public void transformPage(View page, float position) {
			if (position > 0) {
				page.setTranslationX(-page.getWidth() * position);
			}
			page.setPivotX(0);
			page.setAlpha(1 - Math.abs(position));
			page.setRotationY(90 * position);
			page.setCameraDistance(10000);
		}
	};

	public final static MyViewPager.PageTransformer rotateTransformer = new MyViewPager.PageTransformer() {
		@Override
		public void transformPage(View page, float position) {
			page.setPivotX(page.getWidth()/2);
			page.setTranslationX(-page.getWidth() * position);
			page.setRotationY(90 * position);
			page.setCameraDistance(10000);
			page.setAlpha(1 - Math.abs(position));
		}
	};

	public final static MyViewPager.PageTransformer turntableTransformer = new MyViewPager.PageTransformer() {
		@Override
		public void transformPage(View page, float position) {
			page.setRotation(40 * position);
			page.setTranslationX(800 * position);
		}
	};

	public final static MyViewPager.PageTransformer squareTransformer = new MyViewPager.PageTransformer() {
		@Override
		public void transformPage(View page, float position) {
			if (position >= -1 && position <= 0) {
				page.setPivotX(page.getWidth());
			} else {
				page.setPivotX(0);
			}
			page.setRotationY(90 *position);
			page.setCameraDistance(10000);
		}
	};

	public final static MyViewPager.PageTransformer cascadingTransformer = new MyViewPager.PageTransformer() {
		@Override
		public void transformPage(View page, float position) {
			if (position >= -1 && position <= 0) {
				page.setTranslationX(-page.getWidth() * position);
				page.setAlpha(1 - Math.abs(position));
				page.setScaleX(1 + 0.5f * position);
				page.setScaleY(1 + 0.5f * position);
				page.setCameraDistance(10000);
			}
		}
	};
}
