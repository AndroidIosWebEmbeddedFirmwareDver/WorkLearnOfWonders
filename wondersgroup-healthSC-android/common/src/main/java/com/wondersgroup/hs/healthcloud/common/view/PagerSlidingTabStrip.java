/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wondersgroup.hs.healthcloud.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wondersgroup.hs.healthcloud.common.R;
import com.wondersgroup.hs.healthcloud.common.util.LogUtils;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;


public class PagerSlidingTabStrip extends HorizontalScrollView {

	private static final int DEFAULT_DOT_DIP = 8;
	private static final int DEFAULT_DOT_MARGIN_DIP = 8;
	private int redPointDip = 6;//红点大小
	private int redPointPosition = 1;//红点位置 1:right 0:left
	private static final int POSITION_LEFT=0;
	private static final int POSITION_RIGHT=1;
	private int tabWidth;
	private boolean mUnderlineFollowTextWidth=false;

	public interface IconTabProvider {
		int getPageIconResId(int position);
	}

	private static final int[] ATTRS = new int[] {
			android.R.attr.textSize,
			android.R.attr.textColor
	};
	private LinearLayout.LayoutParams defaultTabLayoutParams;
	private LinearLayout.LayoutParams expandedTabLayoutParams;

	private final PageListener pageListener = new PageListener();
	public OnPageChangeListener delegatePageListener;

	protected LinearLayout tabsContainer;
	protected ViewPager pager;


	protected int tabCount;

	private int currentPosition = 0;
	private float currentPositionOffset = 0f;

	private Paint rectPaint;
	private Paint dividerPaint;

	private int indicatorColor = 0xFF666666;
	private int underlineColor = 0x1A000000;
	private int dividerColor = 0;

	protected boolean shouldExpand = false;
	protected boolean textAllCaps = false;

	private int scrollOffset = 52;
	private int indicatorHeight = 8;
	private int underlineHeight = 1;
	private int underlinePadding = 0;
	private int dividerPadding = 12;
	protected int tabPadding = 12;
	private int dividerWidth = 1;


	protected int tabTextSize = 13;
	protected int tabTextColor = 0xFF666666;

	protected int selectedPosition = 0;
	protected int selectedTabTextSize = 15;
	protected int selectedTabTextColor = 0xFF297fff;

	private Typeface tabTypeface = null;
	private int tabTypefaceStyle = Typeface.BOLD;

	private int lastScrollX = 0;

//	private int tabBackgroundResId = R.drawable.ic_launcher;

	protected Locale locale;

	private boolean isWithBadge = false;
	protected int[] tabIcons;
	protected int[] selectedTabIcons;
	private boolean wrapChild;
	public PagerSlidingTabStrip(Context context) {
		this(context, null);
	}

	public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

//		setFillViewport(true);
		setWillNotDraw(false);

		tabsContainer = new LinearLayout(context);
		tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		addView(tabsContainer);

		DisplayMetrics dm = getResources().getDisplayMetrics();

		scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
		indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
		underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
		dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
		tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
		dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
		tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);
		tabTextSize = a.getDimensionPixelOffset(R.styleable.PagerSlidingTabStrip_pstsTabTextSize, tabTextSize);
		indicatorColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsIndicatorColor, indicatorColor);
		underlineColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsUnderlineColor, underlineColor);
		dividerColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor, dividerColor);
		indicatorHeight = a.getDimensionPixelOffset(R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight, indicatorHeight);
		underlineHeight = a.getDimensionPixelOffset(R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight, underlineHeight);
		dividerPadding = a.getDimensionPixelOffset(R.styleable.PagerSlidingTabStrip_pstsDividerPadding, dividerPadding);
		tabPadding = a.getDimensionPixelOffset(R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight, tabPadding);
//		tabBackgroundResId = a.getResourceId(R.styleable.PagerSlidingTabStrip_pstsTabBackground, tabBackgroundResId);
		shouldExpand = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand, shouldExpand);
		scrollOffset = a.getDimensionPixelOffset(R.styleable.PagerSlidingTabStrip_pstsScrollOffset, scrollOffset);
		textAllCaps = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsTextAllCaps, textAllCaps);

		selectedTabTextSize=a.getDimensionPixelOffset(R.styleable.PagerSlidingTabStrip_pstsSelectedTabTextSize, selectedTabTextSize);
		selectedTabTextColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsSelectedTabTextColor, selectedTabTextColor);
		tabTextColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsTabTextColor, tabTextColor);
		underlinePadding =  a.getDimensionPixelOffset(R.styleable.PagerSlidingTabStrip_pstsUnderlinePadding, underlinePadding);
		mUnderlineFollowTextWidth = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsUnderlineFollowTextWidth, false);
		redPointDip =  a.getDimensionPixelOffset(R.styleable.PagerSlidingTabStrip_redPointDip, redPointDip);
		redPointPosition =  a.getInt(R.styleable.PagerSlidingTabStrip_redPointPosition, 0);

		wrapChild = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsWrapChild, false);
		a.recycle();

		rectPaint = new Paint();
		rectPaint.setAntiAlias(true);
		rectPaint.setStyle(Style.FILL);

		dividerPaint = new Paint();
		dividerPaint.setAntiAlias(true);
		dividerPaint.setStrokeWidth(dividerWidth);

		defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

		if (locale == null) {
			locale = getResources().getConfiguration().locale;
		}
	}

	public void setViewPager(ViewPager pager) {
		this.pager = pager;

		if (pager.getAdapter() == null) {
			throw new IllegalStateException("ViewPager does not have adapter instance.");
		}

		pager.addOnPageChangeListener(pageListener);

		selectedPosition = pager.getCurrentItem();

		notifyDataSetChanged();
	}

	public void setCurrentItem(int pos, boolean anim) {
		if (pager != null) {
			pager.setCurrentItem(pos, anim);
		}
	}

	public void setOnPageChangeListener(OnPageChangeListener listener) {
		this.delegatePageListener = listener;
	}

	public void notifyDataSetChanged() {

		tabsContainer.removeAllViews();

		tabCount = pager.getAdapter().getCount();

		for (int i = 0; i < tabCount; i++) {

			if (pager.getAdapter() instanceof IconTabProvider) {
				addIconTab(i, ((IconTabProvider) pager.getAdapter()).getPageIconResId(i));
			} else {
				if (isWithBadge) {
					addTextNumTab(i, pager.getAdapter().getPageTitle(i) + "");
				} else {
					addTextTab(i, pager.getAdapter().getPageTitle(i) + "");
				}
			}

		}

		updateTabStyles();

		getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@SuppressWarnings("deprecation")
			@SuppressLint("NewApi")
			@Override
			public void onGlobalLayout() {

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					getViewTreeObserver().removeGlobalOnLayoutListener(this);
				} else {
					getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}

				currentPosition = pager.getCurrentItem();
				scrollToChild(currentPosition, 0);
			}
		});

	}

	protected void addTextTab(final int position, String title) {

		TextView tab = new TextView(getContext());
		CalligraphyUtils.applyFontToTextView(getContext(), tab, CalligraphyConfig.get().getFontPath());

		tab.setText(title);
		tab.setGravity(Gravity.CENTER);
		tab.setSingleLine();

		addTab(position, tab);
	}

	private void addIconTab(final int position, int resId) {

		ImageButton tab = new ImageButton(getContext());
		tab.setImageResource(resId);

		addTab(position, tab);

	}

	protected void addTab(final int position, View tab) {
		tab.setFocusable(true);
		tab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(position);
			}
		});

		if(tabWidth == 0){
			tab.setPadding(tabPadding, 0, tabPadding, 0);
		}
		tabsContainer.addView(tab, position, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
		if(tabWidth > 0){
			tab.getLayoutParams().width = tabWidth;
		}
	}

	private void addTextNumTab(final int position, String title) {
		RelativeLayout container = new RelativeLayout(getContext());
		TextView tab = new TextView(getContext());
		CalligraphyUtils.applyFontToTextView(getContext(), tab, CalligraphyConfig.get().getFontPath());

		tab.setText(title);
		tab.setSingleLine();
		tab.setId(position + 10086);
		RelativeLayout.LayoutParams tabLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tabLp.addRule(RelativeLayout.CENTER_IN_PARENT);
		container.addView(tab, tabLp);
		container.setTag(tab);

		View dot = new View(getContext());
		dot.setBackgroundResource(R.drawable.dot_shape);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(dipToPixels(redPointDip), dipToPixels(redPointDip));
		if(redPointPosition==POSITION_LEFT) {
			lp.addRule(RelativeLayout.LEFT_OF,position + 10086);
			lp.addRule(RelativeLayout.CENTER_VERTICAL);
			lp.setMargins(0, 0, dipToPixels(DEFAULT_DOT_MARGIN_DIP), 0);
		}else{
			lp.addRule(RelativeLayout.RIGHT_OF, position + 10086);
			lp.setMargins(0, dipToPixels(DEFAULT_DOT_MARGIN_DIP), 0, 0);
		}
		container.addView(dot, lp);
		dot.setVisibility(View.GONE);

		addTab(position, container);
	}

	/**
	 * 设置是否显示红点
	 *
	 * @param position
	 * @param isVisibility
	 */
	public void setBadgeVisibility(int position, boolean isVisibility) {
		if (isWithBadge) {
			ViewGroup container = (ViewGroup) tabsContainer.getChildAt(position);
			View dot = container.getChildAt(1);
			if (dot != null) {
				if (isVisibility) {
					dot.setVisibility(View.VISIBLE);
				} else {
					dot.setVisibility(View.GONE);
				}
			}
		}
	}

	/**
	 * 设置tab的宽度
	 * @param tabWidth
	 */
	public void setTabWidth(int tabWidth) {
		this.tabWidth = tabWidth;
	}

	protected void updateTabStyles() {

		if (pager.getAdapter() instanceof IconTabProvider) {
			for (int i = 0; i < tabCount; i++) {

				ImageButton v = (ImageButton) tabsContainer.getChildAt(i);
				if (selectedTabIcons.length == tabCount) {
					if (i == selectedPosition) {
						v.setImageResource(selectedTabIcons[i]);
					} else {
						v.setImageResource(tabIcons[i]);
					}
				}
			}
		} else {

			for (int i = 0; i < tabCount; i++) {

				View v = tabsContainer.getChildAt(i);

//			v.setBackgroundResource(tabBackgroundResId);

				if (v instanceof RelativeLayout) {

					TextView tab = (TextView) v.getTag();
					tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
					tab.setTextColor(tabTextColor);

					if (textAllCaps) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
							tab.setAllCaps(true);
						} else {
							tab.setText(tab.getText().toString().toUpperCase(locale));
						}
					}
					if (i == selectedPosition) {
						tab.setTextColor(selectedTabTextColor);
						tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, selectedTabTextSize);
					}
				} else if (v instanceof TextView || v instanceof FrameLayout) {
					TextView tab = null;
					if (v instanceof TextView) {
						tab = (TextView) v;
					}
					if (v instanceof FrameLayout) {

						tab = (TextView) ((FrameLayout) v).findViewById(i + 10086);
					}
					if (tab == null) {
						return;
					}


					tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
					tab.setTextColor(tabTextColor);

					if (textAllCaps) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
							tab.setAllCaps(true);
						} else {
							tab.setText(tab.getText().toString().toUpperCase(locale));
						}
					}
					if (i == selectedPosition) {
						tab.setTextColor(selectedTabTextColor);
						tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, selectedTabTextSize);
					}
				}
			}

		}

	}

	private void scrollToChild(int position, int offset) {

		if (tabCount == 0) {
			return;
		}

		int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

		if (position > 0 || offset > 0) {
			newScrollX -= scrollOffset;
		}

		if (newScrollX != lastScrollX) {
			lastScrollX = newScrollX;
			scrollTo(newScrollX, 0);
		}

	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (!shouldExpand && !wrapChild) {
			float f = getMeasuredWidth() / (float)tabsContainer.getMeasuredWidth();
			if (f > 1.01) {
				int w = 0;
				for (int i = 0; i < tabCount; i++) {
					View child = tabsContainer.getChildAt(i);
					if (i != tabCount - 1) {
						int width = (int)(child.getMeasuredWidth() * f);
						child.setLayoutParams(new LinearLayout.LayoutParams(width,
								ViewGroup.LayoutParams.MATCH_PARENT));
						w += width;
					} else {
						child.setLayoutParams(new LinearLayout.LayoutParams(getMeasuredWidth() - w,
								ViewGroup.LayoutParams.MATCH_PARENT));
					}
				}
				setFillViewport(true);
			} else if (f < 1.01) {
				setShouldExpand(true);
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (isInEditMode() || tabCount == 0) {
			return;
		}

		final int height = getHeight();

		rectPaint.setColor(indicatorColor);

		if (mUnderlineFollowTextWidth) {
			View currentTab = tabsContainer.getChildAt(currentPosition);
			TextView tv;
			int childViewLeft=0;
			if(isWithBadge){
				RelativeLayout child = (RelativeLayout)currentTab;
				childViewLeft = child.getLeft();
				tv = (TextView) child.getChildAt(0);
			}else{
				tv = (TextView) currentTab;
			}
			int txWidth = (int) tv.getPaint().measureText(tv.getText().toString());
			float lineLeft = (childViewLeft+tv.getLeft())+tv.getMeasuredWidth()/2-txWidth/2;
			float lineRight = (childViewLeft+tv.getLeft())+tv.getMeasuredWidth()/2+txWidth/2;
			if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {
				TextView nextTab;
				if(isWithBadge){
					RelativeLayout child = (RelativeLayout) tabsContainer.getChildAt(currentPosition + 1);
					childViewLeft = child.getLeft();
					nextTab = (TextView) child.getChildAt(0);
				}else{
					nextTab = (TextView) tabsContainer.getChildAt(currentPosition + 1);
				}
				int nextTabWidth = (int) nextTab.getPaint().measureText(nextTab.getText().toString());
				final float nextTabLeft = (childViewLeft+nextTab.getLeft())+nextTab.getMeasuredWidth()/2-nextTabWidth/2;
				final float nextTabRight = (childViewLeft+nextTab.getLeft())+nextTab.getMeasuredWidth()/2+nextTabWidth/2;
				lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
				lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
//				LogUtils.d("szy-->"+"/nextTabWidth="+nextTabWidth+"/lineLeft="+lineLeft+"/lineRight="+lineRight);
			}
			canvas.drawRect(lineLeft, height - indicatorHeight,lineRight , height, rectPaint);
		} else {
			View currentTab = tabsContainer.getChildAt(currentPosition);
			float lineLeft = currentTab.getLeft();
			float lineRight = currentTab.getRight();
			if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {
				View nextTab = tabsContainer.getChildAt(currentPosition + 1);
				final float nextTabLeft = nextTab.getLeft();
				final float nextTabRight = nextTab.getRight();
				lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
				lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
			}
			canvas.drawRect(lineLeft + underlinePadding, height - indicatorHeight, lineRight - underlinePadding, height, rectPaint);
		}

		rectPaint.setColor(underlineColor);
		canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);

		if (dividerColor != 0) {
			dividerPaint.setColor(dividerColor);
			for (int i = 0; i < tabCount - 1; i++) {
				View tab = tabsContainer.getChildAt(i);
				canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
			}
		}
	}

	private class PageListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			currentPosition = position;
			currentPositionOffset = positionOffset;

			scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));

			invalidate();

			if (delegatePageListener != null) {
				delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE) {
				scrollToChild(pager.getCurrentItem(), 0);
			}

			if (delegatePageListener != null) {
				delegatePageListener.onPageScrollStateChanged(state);
			}
		}

		@Override
		public void onPageSelected(int position) {
			selectedPosition = position;
			updateTabStyles();
			if (delegatePageListener != null) {
				delegatePageListener.onPageSelected(position);
			}
		}

	}

	public void setSelectedTabTextSize(int selectedTabTextSize) {
		this.selectedTabTextSize = selectedTabTextSize;
		invalidate();
	}


	public int getSelectedTabTextSize() {
		return this.selectedTabTextSize;
	}

	public void setSelectedTabTextColor(int selectedTabTextColor) {
		this.selectedTabTextColor = selectedTabTextSize;
		invalidate();
	}

	public void setSelectedTabTextColorResource(int resId) {
		this.selectedTabTextColor = getResources().getColor(resId);
		invalidate();
	}

	public int getSelectedTabTextColor() {
		return this.selectedTabTextColor;
	}

	public void setIndicatorColor(int indicatorColor) {
		this.indicatorColor = indicatorColor;
		invalidate();
	}

	public void setIndicatorColorResource(int resId) {
		this.indicatorColor = getResources().getColor(resId);
		invalidate();
	}

	public int getIndicatorColor() {
		return this.indicatorColor;
	}

	public void setIndicatorHeight(int indicatorLineHeightPx) {
		this.indicatorHeight = indicatorLineHeightPx;
		invalidate();
	}

	public int getIndicatorHeight() {
		return indicatorHeight;
	}

	public void setUnderlineColor(int underlineColor) {
		this.underlineColor = underlineColor;
		invalidate();
	}

	public void setUnderlineColorResource(int resId) {
		this.underlineColor = getResources().getColor(resId);
		invalidate();
	}

	public int getUnderlineColor() {
		return underlineColor;
	}

	public void setDividerColor(int dividerColor) {
		this.dividerColor = dividerColor;
		invalidate();
	}

	public void setDividerColorResource(int resId) {
		this.dividerColor = getResources().getColor(resId);
		invalidate();
	}

	public int getDividerColor() {
		return dividerColor;
	}

	public void setUnderlineHeight(int underlineHeightPx) {
		this.underlineHeight = underlineHeightPx;
		invalidate();
	}

	public int getUnderlineHeight() {
		return underlineHeight;
	}

	public void setDividerPadding(int dividerPaddingPx) {
		this.dividerPadding = dividerPaddingPx;
		invalidate();
	}

	public int getDividerPadding() {
		return dividerPadding;
	}

	public void setScrollOffset(int scrollOffsetPx) {
		this.scrollOffset = scrollOffsetPx;
		invalidate();
	}

	public int getScrollOffset() {
		return scrollOffset;
	}

	public void setShouldExpand(boolean shouldExpand) {
		this.shouldExpand = shouldExpand;
		setFillViewport(shouldExpand);
		requestLayout();
	}

	public boolean getShouldExpand() {
		return shouldExpand;
	}

	public boolean isTextAllCaps() {
		return textAllCaps;
	}

	public void setAllCaps(boolean textAllCaps) {
		this.textAllCaps = textAllCaps;
	}

	public void setTextSize(int textSizePx) {
		this.tabTextSize = textSizePx;
		updateTabStyles();
	}

	public int getTextSize() {
		return tabTextSize;
	}

	public void setTextColor(int textColor) {
		this.tabTextColor = textColor;
		updateTabStyles();
	}

	public void setTextColorResource(int resId) {
		this.tabTextColor = getResources().getColor(resId);
		updateTabStyles();
	}

	public int getTextColor() {
		return tabTextColor;
	}

	public void setTypeface(Typeface typeface, int style) {
		this.tabTypeface = typeface;
//		this.tabTypefaceStyle = style;
		updateTabStyles();
	}
	public void setSelectedTextColor(int textColor) {
		this.selectedTabTextColor = textColor;
		updateTabStyles();
	}

	public void setSelectedTextColorResource(int resId) {
		this.selectedTabTextColor = getResources().getColor(resId);
		updateTabStyles();
	}
	public int getSelectedTextColor() {
		return selectedTabTextColor;
	}
//	public void setTabBackground(int resId) {
//		this.tabBackgroundResId = resId;
//	}
//
//	public int getTabBackground() {
//		return tabBackgroundResId;
//	}

	public void setTabPaddingLeftRight(int paddingPx) {
		this.tabPadding = paddingPx;
		updateTabStyles();
	}

	public int getTabPaddingLeftRight() {
		return tabPadding;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		SavedState savedState = (SavedState) state;
		super.onRestoreInstanceState(savedState.getSuperState());
		currentPosition = savedState.currentPosition;
		requestLayout();
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState savedState = new SavedState(superState);
		savedState.currentPosition = currentPosition;
		return savedState;
	}

	static class SavedState extends BaseSavedState {
		int currentPosition;

		public SavedState(Parcelable superState) {
			super(superState);
		}

		private SavedState(Parcel in) {
			super(in);
			currentPosition = in.readInt();
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(currentPosition);
		}

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

	public void setTabTextSize(int tabTextSize) {
		tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, getContext().getResources().getDisplayMetrics());
		this.tabTextSize = tabTextSize;
	}

	public boolean isWithBadge() {
		return isWithBadge;
	}

	public void setIsWithBadge(boolean isWithBadge) {
		this.isWithBadge = isWithBadge;
	}

	private int dipToPixels(int dip) {
		Resources r = getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
		return (int) px;
	}

	public void setSelectedTabIcons(int[] selectedTabIcons) {
		this.selectedTabIcons = selectedTabIcons;
	}
	public void setTabIcons(int[] tabIcons) {
		this.tabIcons = tabIcons;
	}

	public boolean isWrapChild() {
		return wrapChild;
	}

	public void setWrapChild(boolean wrapChild) {
		this.wrapChild = wrapChild;
	}
}


