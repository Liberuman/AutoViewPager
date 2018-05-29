package com.sxu.autoviewpagerdemo;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sxu.autoviewpager.AutoViewPager;

public class MainActivity extends AppCompatActivity {

	private int[] items = new int[] {
			R.mipmap.image_01,
			R.mipmap.image_02,
			R.mipmap.image_03,
			R.mipmap.image_04,
			R.mipmap.image_05,
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AutoViewPager viewPager1 = findViewById(R.id.pager1);
		AutoViewPager viewPager2 = findViewById(R.id.pager2);
		AutoViewPager viewPager3 = findViewById(R.id.pager3);
		AutoViewPager viewPager4 = findViewById(R.id.pager4);
		AutoViewPager viewPager5 = findViewById(R.id.pager5);
		AutoViewPager viewPager6 = findViewById(R.id.pager6);
		AutoViewPager viewPager7 = findViewById(R.id.pager7);
		AutoViewPager viewPager8 = findViewById(R.id.pager8);
		AutoViewPager viewPager9 = findViewById(R.id.pager9);

		viewPager1.setDefaultScale(0.85f);
		viewPager1.setDefaultAlpha(0.5f);
		viewPager2.setPagerWidth((getScreenWidth() - dpToPx(12)) / 2);
		setPagerAdapter(viewPager1);
		setPagerAdapter(viewPager2);
		setPagerAdapter(viewPager3);
		setPagerAdapter(viewPager4);
		setPagerAdapter(viewPager5);
		setPagerAdapter(viewPager6);
		setPagerAdapter(viewPager7);
		setPagerAdapter(viewPager8);
		setPagerAdapter(viewPager9);
	}

	private void setPagerAdapter(final AutoViewPager viewPager) {
		viewPager.setItemView(new AutoViewPager.OnItemView() {
			@Override
			public View getView(ViewGroup container, int position) {
				ImageView imageView = new ImageView(MainActivity.this);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setImageResource(items[position]);
				container.addView(imageView);
				return imageView;
			}
		});
		viewPager.setAdapter(items.length);
	}

	private int dpToPx(int dp) {
		return (int) (Resources.getSystem().getDisplayMetrics().density * dp + 0.5f);
	}

	private int getScreenWidth() {
		return Resources.getSystem().getDisplayMetrics().widthPixels;
	}
}
