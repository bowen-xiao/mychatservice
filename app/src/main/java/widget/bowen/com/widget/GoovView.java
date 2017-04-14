package widget.bowen.com.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by 肖稳华 on 2017/2/15.
 */

public class GoovView extends View {

	private Paint mPaint;
	Path path;
	private int statusBarHeight;
	//文本的绘制区域
	private Rect textRect;

	public GoovView(Context context) {
		this(context,null);
	}

	public GoovView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public GoovView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private OnGooViewChangeListener listener;
	public interface OnGooViewChangeListener{
		/**
		 * GooView的消失处理
		 */
		void onDisappear();

		/**
		 * GooView重置处理
		 */
		void onReset();
	}

	public void setOnGooViewChangeListener(OnGooViewChangeListener listener){
		this.listener=listener;
	}

	//实始化画笔
	private void init() {
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		path = new Path();
		textRect = new Rect();
	}

	//两个圆的圆心位置
	PointF stableCircle = new PointF(200f,200f);
	PointF dragCircle = new PointF(100f,100f);

	//两个圆的半径
	float stableRadius = 30f;
	float dragRadius = 30f;

	PointF[] stablePoints = new PointF[]{
		new PointF(400f,300f),
		new PointF(400f,400f)
	};

	PointF[] dragPoints = new PointF[]{
		new PointF(100f,300f),
		new PointF(100f,400f)
	};

	PointF controlPoint = new PointF(250f,350f);

	private  float maxDragDistance = 200f;
	private  float minRadius= 5f;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		canvas.translate(0,-statusBarHeight);
		// 拖拽圆拖得越远 固定圆的半径就变得更小
		// 需要计算这个比值
		float distanceBetween2Points = GeometryUtil.getDistanceBetween2Points(dragCircle, stableCircle);
		//计算这个比值
		float percent = distanceBetween2Points / maxDragDistance;
		float tepRadius = GeometryUtil.evaluateValue(percent, stableRadius, minRadius);
		if(tepRadius < minRadius){
			tepRadius = minRadius;
		}

		//拖拽完后的操作，如果超过半径就不显示，在半径内就弹回原点
		if(!isDisappear){
			//超过范围不绘制以下内容
			if(!isOutOfRange){
				// 1 得到2个圆心之间的斜率
				float dx = dragCircle.x - stableCircle.x;
				float dy = dragCircle.y - stableCircle.y;
				double linek = 0;
				if(dx != 0){
					linek = dy / dx ;
				}

				// 2 通过公式 计算 path的四个点的正确位置
				stablePoints = GeometryUtil.getIntersectionPoints(stableCircle,tepRadius,linek);
				dragPoints = GeometryUtil.getIntersectionPoints(dragCircle,dragRadius,linek);
				controlPoint = GeometryUtil.getMiddlePoint(stableCircle,dragCircle);

				// 1 绘制中间图形，四个点的连接，确定开始位置 固定圆点1
				path.moveTo(stablePoints[0].x,stablePoints[0].y);
				// 2 固定圆点1位置 到 动态圆点1 的贝塞尔曲线
				path.quadTo(controlPoint.x,controlPoint.y,dragPoints[0].x,dragPoints[0].y);
				// 3 动态圆点1 到动态圆点2 直线连接
				path.lineTo(dragPoints[1].x,dragPoints[1].y);
				// 4 动态圆点2 到 静态圆点2 曲线连接
				path.quadTo(controlPoint.x,controlPoint.y,stablePoints[1].x,stablePoints[1].y);
				// 5 闭合整个图形
				path.close();

				//两个圆及相关图形绘制
				canvas.drawPath(path,mPaint);
				path.reset();
				canvas.drawCircle(stableCircle.x,stableCircle.y,tepRadius,mPaint);
			}
			canvas.drawCircle(dragCircle.x,dragCircle.y,dragRadius,mPaint);
			drawText(canvas);
		}
		canvas.restore();
	}

	//绘制文本
	private void drawText(Canvas canvas) {
		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(30);
		mPaint.getTextBounds(text,0,text.length(),textRect);
		float x = dragCircle.x - textRect.width() * 0.5f;//x坐标为拖拽圆心x - 文本框宽度 *0.5
		float y = dragCircle.y + textRect.height() * 0.5f;//y坐标为拖拽圆心y - 文本框高度 *0.5
		canvas.drawText(text,x,y,mPaint);
		mPaint.setColor(Color.RED);
	}

	//是否超出过最大半径
	boolean isOutOfRange = false;

	private boolean isDisappear = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//计算移动的距离应用到移动圆的上
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				isOutOfRange = false;
				isDisappear = false;
				float rawX = event.getRawX();
				float rawY = event.getRawY() ;
				dragCircle.set(rawX,rawY);
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				rawX = event.getRawX();
				rawY = event.getRawY();
				dragCircle.set(rawX,rawY);
				//两点之间的距离，是否超过了最大距离
				float between2Points = GeometryUtil.getDistanceBetween2Points(dragCircle, stableCircle);
				if(between2Points > maxDragDistance){
					isOutOfRange = true;
				}
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				//在松开手的时候，如果超过最大范围就消失,没有超过就回到原点
				// 1 计算距离是否已经超过最大范围
				between2Points = GeometryUtil.getDistanceBetween2Points(dragCircle, stableCircle);
				if(isOutOfRange){
					if(between2Points > maxDragDistance){
						isDisappear = true;
						if(listener!=null){
							listener.onDisappear();
						}
					}else{
						dragCircle.set(stableCircle.x,stableCircle.y);
						if(listener!=null){
							listener.onReset();
						}
					}
				}else{
					final PointF lastPoint = new PointF(dragCircle.x,dragCircle.y);
					// 2 获取到最后的位置，然后做平移动画
					ValueAnimator animator = ValueAnimator.ofFloat(between2Points, 0);
					animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							//变化值的百分比
							float percent = animation.getAnimatedFraction();
							dragCircle = GeometryUtil.getPointByPercent(lastPoint, stableCircle, percent);
							invalidate();
						}
					});
					animator.setDuration(1800L);
					animator.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							super.onAnimationEnd(animation);
							//做重置操作
							if(listener!=null){
								Log.i("test", "onReset");
								listener.onReset();
							}
						}
					});
					animator.setInterpolator(new OvershootInterpolator());
					animator.start();
				}
				invalidate();
				break;
		}

		return true;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		statusBarHeight = getStatusBarHeight(this);
	}

	private int getStatusBarHeight(View view){
		Rect rect = new Rect();
		view.getWindowVisibleDisplayFrame(rect);
		return rect.top;
	}

	private String text = "";

	public void setText(String text) {
		this.text = text;
	}

	//设置圆显示的坐标
	public void setPosition(float x, float y) {
		stableCircle.set(x,y);
		dragCircle.set(x,y);
		invalidate();
	}
}
