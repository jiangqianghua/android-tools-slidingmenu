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
	 * �Զ�������ʱ������
	 * @param context
	 * @param attrs
	 */
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs ,defStyle);
		// ��ȡ���Ƕ���ĳ�Ա����
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
//		// ��50 dp ת������ֵ px
		//mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
	}
	
	/**
	 * δ��������ʱ������
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
		// ��50 dp ת������ֵ px
		mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics());
	}

	/**
	 * δ��������ʱ������
	 * @param context
	 * @param attrs
	 */
	public SlidingMenu(Context context) {
		super(context, null);
	}
	
	/**
	 * ������view ���Լ��Ŀ��
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if(!once)
		{
			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);
			// ����mMenu���
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
			// ����content���
			mContent.getLayoutParams().width = mScreenWidth ;
			//mWapper.getLayoutParams().width = mScreenWidth;
			once = true ; 
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		// ��menu���أ�����ƫ����
		
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
			//scrollX ���صĿ��
			int scrollX = getScrollX();
			if(scrollX >= mMenuWidth /2)
			{
				// ���ز˵�
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false ;
			}
			else
			{
				//��ʾ�˵�
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
	 * �򿪲˵�
	 */
	public void openMenu()
	{
		if(isOpen) return ;
		this.smoothScrollTo(0, 0);
		isOpen = true ;
	}
	/**
	 * �رղ˵�
	 */
	public void closeMenu()
	{
		if(!isOpen)return ;
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = false ;
	}
	/**
	 * �л��˵�
	 */
	public void toggle()
	{
		if(isOpen)
			closeMenu();
		else
			openMenu();
	}
	
	/**
	 * ����ʱ����
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
//		/////////////�������д������ʵ�ֳ���໬ ��һ����ʽ
//		float scale = l*1.0f/mMenuWidth;  //  1 �� 0
//		// �˵�ƫ����
//		ViewHelper.setTranslationX(mMenu, mMenuWidth*scale);  
//		
//		//QQ�໬����Ч��
//		/**
//		����1���������� 1.0 ~ 0.7 ����Ч��
//		ratio : 1.0 ~ 0.0
//		0.7 + 0.3 * ratio
//		����2�� �˵���ƫ������Ҫ�޸�
//		����3: �˵���ʾ�������Լ�͸���ȱ任
//		ratio : 1.0 ~ 0.0
//		���ţ� 0.7 ~ 1.0
//		1.0 -��ratio * 0.3
//		͸���ȣ� 0.6 ~ 1.0
//		1.0 - ratio * 0.4 
//		**/
//		// ������������  1.0 �� 0.7
//		float rightScale = 0.7f + 0.3f * scale ;
//		// ����context���������ĵ�
//		ViewHelper.setPivotX(mContent, 0);
//		ViewHelper.setPivotY(mContent, mContent.getHeight()/2);
//		// ����x
//		ViewHelper.setScaleX(mContent, rightScale);
//		// ����y
//		ViewHelper.setScaleY(mContent, rightScale);
//		
//		// �˵����ű���
//		float leftScale = 1.0f - scale*0.3f ;
//		float leftAlpha = 0.6f+0.4f*(1-scale);
//		// ����x
//		ViewHelper.setScaleX(mMenu, leftScale);
//		// ����y
//		ViewHelper.setScaleY(mMenu, leftScale);
//		// ͸����
//		ViewHelper.setAlpha(mMenu, leftAlpha);
		
//		/////////////�������д������ʵ�ֳ���໬ �ڶ�����ʽ	
		float ratio = l * 1.0f / mMenuWidth;
		float menuScale = 1.0f - ratio * 0.3f;
		float menuAlpha = 1.0f - ratio * 0.4f;
		ViewHelper.setTranslationX(mMenu, l * 0.7f);//�˵�ƫ����
		ViewHelper.setScaleX(mMenu, menuScale);//�˵�����
		ViewHelper.setScaleY(mMenu, menuScale);//�˵�����
		ViewHelper.setAlpha(mMenu, menuAlpha);//�˵�͸����
		float contentScale = 0.7f + 0.3f * ratio;
		ViewHelper.setScaleX(mContent, contentScale);//������������
		ViewHelper.setScaleY(mContent, contentScale);//������������
		//�����������ĵ�����
		ViewHelper.setPivotX(mContent, 0);//���ĵ�X�������ƶ���0
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);//���ĵ�Y���򲻱�
		
		
	}
}
