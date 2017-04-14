package widget.bowen.com.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by 肖稳华 on 2017/3/2.
 */

public class SweepCardView extends FrameLayout {

	private int mScreenWidth;
	private int mScreenHeight;
	private int outOffX;
	private int outOffY;
	private float centerPercentX;
	private float centerPercentY;

	//几个点的位置
	private PointF mCircleCenterPoint;
	private PointF mCircleLeftPoint;

	public SweepCardView(Context context) {
		this(context,null);
	}

	public SweepCardView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public SweepCardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	//初始化
	private void init() {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		//屏幕的宽度
		mScreenWidth = metrics.widthPixels;
		mScreenHeight = metrics.heightPixels;
		for (int i = 4 ; i > 0 ;i--) {
			ImageView imageView = new ImageView(getContext());
			imageView.setImageResource(R.mipmap.ic_launcher);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			addView(imageView);
		}
		mOldLastPoint = new PointF(0, 0);
	}



	//子视图
	List<View> mChildren = new ArrayList<>();
	public void setChildrenView(List<View> children){
		mChildren = children;
	}

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
		L.e("measureWidthSpec :" + measureWidthSpec);
		L.e("measureHeightSpec :" + measureHeightSpec);
		//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			//测量结果
			int makeMeasureSpec = MeasureSpec.makeMeasureSpec(measureWidthSpec / 3, MeasureSpec.EXACTLY);
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

	//确定view的摆放
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		int count = getChildCount();
		int width = 240;

		for (int index = 0 ; index < count;index++){
			View view = getChildAt(index);
			//横向展示
			//			view.layout(index * width , 0 , (index+1) *width, width);
			int childTop = (int) ((index * width * 0.1f) + mScreenWidth * 0.2f);
			int childLeft = (int) (mScreenWidth * 0.5f - width * 0.5f);
			int outOff = (int) (20 * 1.0f * index);
			//纵向展示
			view.layout((int) (childLeft - outOff), childTop  , (int) (childLeft + width + outOff), (int) (childTop + width * 1.5f));
			//需要修正中心点的轨迹
			mOldLastPoint.set(view.getX() + (0.5f * view.getWidth()), view.getY() + (0.5f * view.getHeight()));
			//左上角的点位置
			mCircleLeftPoint = new PointF(view.getX(), view.getY());
		}

		if(mMovePoint != null && state != ViewStatus.IDLE){
			View lastView = getChildAt(count - 1);
			int outOffWidth = (int) (0.5f * lastView.getWidth());
			int outOffHeight = (int) (0.5f * lastView.getHeight());
			lastView.layout((int) mMovePoint.x - outOffWidth,
							(int) mMovePoint.y - outOffHeight  ,
							(int)(mMovePoint.x + lastView.getWidth() - outOffWidth),
							(int)(mMovePoint.y + lastView.getHeight() - outOffHeight));
		}



	}

	//最后一个view中心点的位置
	PointF mMovePoint;
	//最后一个view中心点的位置
	PointF mOldLastPoint;



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//在动画过程中，不作任何操作
		if(state == ViewStatus.ANIMATION){
			return false;
		}
		if(mMovePoint == null){
			mMovePoint = new PointF(event.getX() - outOffX, event.getY() - outOffY);
		}
		View lastView = getChildAt(getChildCount() -1);

		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				//需要判断是否在图形上才响应事件

				//判断点在子view上才响应事件
				if(event.getX() > lastView.getLeft()
				   && event.getY() > lastView.getTop()
					&& event.getX() < lastView.getRight()
					&& event.getY() < lastView.getBottom()
					){
					//获取到偏移量
					if(mOldLastPoint != null){
						outOffX = (int) (event.getX() - mOldLastPoint.x);
						outOffY = (int) (event.getY() - mOldLastPoint.y);
						//圆心所在的点
						mCircleCenterPoint = new PointF(event.getX(), event.getY());
						centerPercentX = event.getX() / (mOldLastPoint.x + lastView.getWidth());
						centerPercentY = event.getY()/ (mOldLastPoint.y + lastView.getHeight());
						L.e(" outOffX : " + outOffX + " ; event.getX() : " + event.getX() + " ; percent : " +
							centerPercentX);
					}
					state = ViewStatus.DRAG;
					mMovePoint.set(event.getX() - outOffX, event.getY() - outOffY);
					requestLayout();
					doRotationAnimation();
					//做旋转动画
				}else{
					//不处理事件，如果事件不在控件内
					return super.onTouchEvent(event);
				}

				break;
			case MotionEvent.ACTION_MOVE:
				state = ViewStatus.DRAG;
//				mMovePoint.set(event.getX(), event.getY());
				mMovePoint.set(event.getX() - outOffX, event.getY() - outOffY);
				requestLayout();
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				state = ViewStatus.ANIMATION;
				//回到原来的位置
				doAnimation();
				isAnimation = false;
				break;

		}
		return true;
	}

	boolean isAnimation = false;
	//做旋转动画
	private void doRotationAnimation(){
		if(isAnimation){return;}
		isAnimation = true;
		View lastView = getChildAt(getChildCount() - 1);
		int viewWidth = lastView.getMeasuredWidth();
		int viewHeight = lastView.getMeasuredHeight();
		float dia = (float) Math.sqrt((viewWidth * viewWidth + viewHeight * viewHeight ) / 4.0f) * 2;

		// 1 手指所在的点
		L.e(" movePoint : " + mCircleCenterPoint.toString());
		// 计算这个点在中心点的位置
		float angle = CircleUtil.getAngle(mCircleCenterPoint.x , mCircleCenterPoint.y, (int) dia);
		int quadrant = CircleUtil.getQuadrant(mCircleCenterPoint.x , mCircleCenterPoint.y, (int) dia);
		/*L.e(" quadrant : " +quadrant);

		L.e(" movePoint angle : " + angle);

		// 2 图形的中心点
		L.e("movePoint mOldLastPoint : " + mOldLastPoint.toString());
		// 3 旋转的终点
		L.e(" movePoint : " + mOldLastPoint.toString());*/

		float aTan = 0.0f;

		//需要计算中心点的位置
		RotateAnimation animation = new RotateAnimation(0, (float) aTan,
														RELATIVE_TO_SELF, centerPercentX,
														RELATIVE_TO_SELF, centerPercentY);
		L.e("realangle : " + aTan);
		animation.setDuration(1500L);
		animation.setFillAfter(true);
		animation.setInterpolator(new AccelerateInterpolator());
		lastView.startAnimation(animation);
	}

	//回位操作
	private void doAnimation(){

		float between2Points = GeometryUtil.getDistanceBetween2Points(mMovePoint, mOldLastPoint);
		//记录最后一个点的位置
		final PointF lastPoint = new PointF(mMovePoint.x, mMovePoint.y);
		ValueAnimator animator = ValueAnimator.ofFloat(between2Points, 0);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				//变化值的百分比
				float percent = animation.getAnimatedFraction();
				mMovePoint = GeometryUtil.getPointByPercent(lastPoint, mOldLastPoint, percent);
				requestLayout();
			}
		});
		animator.setDuration(600L);
		//动画完成时的操作
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				//做重置操作
				state = ViewStatus.IDLE;
			}
		});
		animator.setInterpolator(new OvershootInterpolator());
		animator.start();
	}

	private ViewStatus state = ViewStatus.IDLE;

	//三种状态，空闲，拖动中，
	public enum ViewStatus{
		IDLE,DRAG,ANIMATION
	}
}
