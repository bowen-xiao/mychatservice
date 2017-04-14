package widget.bowen.com.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 肖稳华 on 2017/2/14.
 */

public class CircleGroup extends ViewGroup {

	public CircleGroup(Context context) {
		this(context,null);
	}

	public CircleGroup(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public CircleGroup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	//初始化操作
	private void init() {
//		L.e("初始化操作");
	}

	//开始角度
	int startAngle;
	//圆的直径
	int dia = 480 ;
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);
			//孩子的宽度
			int childWidth = childView.getMeasuredWidth();
			float tep = dia / 3.0f;
			int left = (int) (dia / 2 + Math.round(Math.cos(Math.toRadians(startAngle)) * tep - childWidth / 2));
			int right = left + childWidth;
			int top = (int) (dia / 2 + Math.round(Math.sin(Math.toRadians(startAngle)) * tep - childWidth / 2));
			int bottom = top + childWidth;
			childView.layout(left,top,right,bottom);
			startAngle+= 360/getChildCount();
		}
	}

	//因为控件只对自己进行测量，不会对子view进行测量,所以需要单独测量子view
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		//传入的模式，wapContent ,未指定,就设置为包裹背景
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		int size = MeasureSpec.getSize(widthMeasureSpec);
		int measureWidthSpec = 0;
		int measureHeightSpec = 0;
		if(mode != MeasureSpec.EXACTLY){
			measureHeightSpec = measureWidthSpec = Math.min(getSuggestedMinimumWidth(),getDefaultWidth());
			if(measureHeightSpec == 0){
				measureHeightSpec = measureWidthSpec = getDefaultWidth();
			}
		}else{
			//取一个最小值,不能超过屏幕的宽度
			measureHeightSpec = measureWidthSpec = Math.min(getDefaultWidth(),size);
		}
		//测量控件自身宽高
		setMeasuredDimension(measureWidthSpec,measureHeightSpec);
		//使用控件真实的宽高
		dia = measureWidthSpec;
		L.e("measureWidthSpec :" + measureWidthSpec);
		L.e("measureHeightSpec :" + measureHeightSpec);
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			//测量结果
			int makeMeasureSpec = MeasureSpec.makeMeasureSpec(dia / 3, MeasureSpec.EXACTLY);
			child.measure(makeMeasureSpec,makeMeasureSpec);
		}
	}

	//获取屏幕的宽度和高度，取一个最小值，解决平板上显示的问题
	private int getDefaultWidth() {
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		int widthPixels = displayMetrics.widthPixels;
		int heightPixels = displayMetrics.heightPixels;
		return Math.min(widthPixels,heightPixels);
	}

	//设置图片和文字添加到视图中去
	public void setData(String[] texts,int[] imgs){
		for (int i = 0; i < texts.length; i++) {
			View childView = View.inflate(getContext(), R.layout.circle_item, null);
			TextView tv = (TextView) childView.findViewById(R.id.tv);
			ImageView iv = (ImageView) childView.findViewById(R.id.iv);
			tv.setText(texts[i]);
			iv.setImageResource(imgs[i]);
			addView(childView);
		}
	}

	//记录最后移动的点位置
	float lastX ;
	float lastY ;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		// 1 获取到第一个点
		// 2 获取到移动后的点
		// 3 计算2点之间的角度（需要考虑四个相限的问题）
		// 4 重新加载新布局
		// 5 记录最后一个点的位置更新到成员变量
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				lastX = event.getX();
				lastY = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				//开始第一次点在圆中的角度
				float startAngle = CircleUtil.getAngle(lastX, lastY, dia);
				//后面点击点的位置,在圆里面的角度
				float endAngle = CircleUtil.getAngle(x, y, dia);
				//获取对应的相限，判断值
				int quadrant = CircleUtil.getQuadrant(x, y, dia);
				//需要去移动的角度
				float angle ;
				if(quadrant == 1 || quadrant == 4){
					angle = endAngle - startAngle;
				}else{
					angle = startAngle - endAngle;
				}
				this.startAngle += angle;
				requestLayout();
				lastX = event.getX();
				lastY = event.getY();

				break;
			case MotionEvent.ACTION_UP:
				break;
		}

		//这里需要拦截所有的事件处理
		return true;
	}
}
