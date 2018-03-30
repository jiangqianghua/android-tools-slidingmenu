package com.example.qq_50_slidingmenu.view;

import com.example.qq_50_slidingmenu.R;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {

	private LinearLayout mWapper ;
	private ViewGroup mMenu ; 
	private ViewGroup mContent ; 
	private int mScreenWidth ; 
	// dp
	private int mMenuRightPadding = 50 ;
	
	private boolean once = false ;
	
	private int mMenuWidth ;
	
	private boolean isOpen = false ;
	/**
	 * 自定义属性时，调用
	 * @param context
	 * @param attrs
	 */
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs ,defStyle);
		// 获取我们定义的成员变量
//		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyle, 0);
//		int n = a.getIndexCount();
//		for(int i = 0 ; i < n ; i++)
//		{
//			int attr = a.getIndex(i);
//			switch(attr)
//			{
//			case R.styleable.SlidingMenu_rightPadding:
//				mMenuRightPadding = a.getDimensionPixelOffset(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));
//				
//			}
//		}
//		a.recycle(); 
//		// TODO Auto-generated constructor stub
//		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//		DisplayMetrics outMetrics = new DisplayMetrics();
//		wm.getDefaultDisplay().getMetrics(outMetrics);
//		mScreenWidth = outMetrics.widthPixels ;
//		// 把50 dp 转成像素值 px
		//mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
	}
	
	/**
	 * 未定义属性时，调用
	 * @param context
	 * @param attrs
	 */
	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
//		// TODO Auto-generated constructor stub
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels ;
		// 把50 dp 转成像素值 px
		mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics());
	}

	/**
	 * 未定义属性时，调用
	 * @param context
	 * @param attrs
	 */
	public SlidingMenu(Context context) {
		super(context, null);
	}
	
	/**
	 * 设置子view 和自己的宽高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if(!once)
		{
			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);
			// 设置mMenu宽度
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
			// 设置content宽度
			mContent.getLayoutParams().width = mScreenWidth ;
			//mWapper.getLayoutParams().width = mScreenWidth;
			once = true ; 
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		// 将menu隐藏，设置偏移量
		
		super.onLayout(changed, l, t, r, b);
		if(changed)
			this.scrollTo(mMenuWidth, 0);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int action  = ev.getAction() ;
		switch (action) {
		case MotionEvent.ACTION_UP:
			//scrollX 隐藏的宽度
			int scrollX = getScrollX();
			if(scrollX >= mMenuWidth /2)
			{
				// 隐藏菜单
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false ;
			}
			else
			{
				//显示菜单
				this.smoothScrollTo(0, 0);
				isOpen = true ;
			}
			
			return true ;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	/**
	 * 打开菜单
	 */
	public void openMenu()
	{
		if(isOpen) return ;
		this.smoothScrollTo(0, 0);
		isOpen = true ;
	}
	/**
	 * 关闭菜单
	 */
	public void closeMenu()
	{
		if(!isOpen)return ;
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = false ;
	}
	/**
	 * 切换菜单
	 */
	public void toggle()
	{
		if(isOpen)
			closeMenu();
		else
			openMenu();
	}
	
	/**
	 * 滚动时发生
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
//		/////////////以下两行代码可以实现抽屉侧滑 第一种形式
//		float scale = l*1.0f/mMenuWidth;  //  1 ～ 0
//		// 菜单偏移量
//		ViewHelper.setTranslationX(mMenu, mMenuWidth*scale);  
//		
//		//QQ侧滑缩放效果
//		/**
//		区别1：内容区域 1.0 ~ 0.7 缩放效果
//		ratio : 1.0 ~ 0.0
//		0.7 + 0.3 * ratio
//		区别2： 菜单的偏移量需要修改
//		区别3: 菜单显示有缩放以及透明度变换
//		ratio : 1.0 ~ 0.0
//		缩放： 0.7 ~ 1.0
//		1.0 -　ratio * 0.3
//		透明度： 0.6 ~ 1.0
//		1.0 - ratio * 0.4 
//		**/
//		// 内容区域缩放  1.0 ～ 0.7
//		float rightScale = 0.7f + 0.3f * scale ;
//		// 设置context缩缩放中心点
//		ViewHelper.setPivotX(mContent, 0);
//		ViewHelper.setPivotY(mContent, mContent.getHeight()/2);
//		// 缩放x
//		ViewHelper.setScaleX(mContent, rightScale);
//		// 缩放y
//		ViewHelper.setScaleY(mContent, rightScale);
//		
//		// 菜单缩放比例
//		float leftScale = 1.0f - scale*0.3f ;
//		float leftAlpha = 0.6f+0.4f*(1-scale);
//		// 缩放x
//		ViewHelper.setScaleX(mMenu, leftScale);
//		// 缩放y
//		ViewHelper.setScaleY(mMenu, leftScale);
//		// 透明度
//		ViewHelper.setAlpha(mMenu, leftAlpha);
		
//		/////////////以下两行代码可以实现抽屉侧滑 第二种形式	
		float ratio = l * 1.0f / mMenuWidth;
		float menuScale = 1.0f - ratio * 0.3f;
		float menuAlpha = 1.0f - ratio * 0.4f;
		ViewHelper.setTranslationX(mMenu, l * 0.7f);//菜单偏移量
		ViewHelper.setScaleX(mMenu, menuScale);//菜单缩放
		ViewHelper.setScaleY(mMenu, menuScale);//菜单缩放
		ViewHelper.setAlpha(mMenu, menuAlpha);//菜单透明度
		float contentScale = 0.7f + 0.3f * ratio;
		ViewHelper.setScaleX(mContent, contentScale);//内容区域缩放
		ViewHelper.setScaleY(mContent, contentScale);//内容区域缩放
		//设置缩放中心点左移
		ViewHelper.setPivotX(mContent, 0);//中心点X方向左移动到0
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);//中心点Y方向不变
		
		
	}
}
