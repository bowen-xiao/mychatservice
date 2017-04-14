package widget.bowen.com.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import widget.bowen.com.widget.bean.PieEntity;

/**
 * Created by 肖稳华 on 2017/2/13.
 */

public class MyPie extends View {

	private Paint mPaint;

	//控件的大小
	private int   with;
	private int   height;
	private float radius;
	private Paint linePaint;


	public MyPie(Context context) {
		this(context,null);
	}

	public MyPie(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MyPie(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	//初始化，画笔
	private void init() {
		mPaint = new Paint();
		//抗锯齿
		mPaint.setAntiAlias(true);

		//用于画线的画笔
		linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setColor(Color.BLACK);
		linePaint.setStrokeWidth(3);
		linePaint.setTextSize(20);

		rectF =new RectF();
		touchRectF =new RectF();
	}

	//圆的矩形区域
	RectF rectF ;
	//点击的矩形区域
	RectF touchRectF;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		with = w;
		height = h;

		//圆形的半径
		radius = Math.min(w, h) * 0.75f / 2;
		rectF.left = -radius;
		rectF.top = -radius;
		rectF.right = radius;
		rectF.bottom = radius;

		float scaleSize = 1.07f;
		touchRectF.left = rectF.left * scaleSize;
		touchRectF.top = rectF.top * scaleSize;
		touchRectF.right = rectF.right * scaleSize;
		touchRectF.bottom = rectF.bottom * scaleSize;
	}

	Path path = new Path();
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//将坐标系移动到屏幕中间来
		canvas.save();
		canvas.translate(with /2 ,height /2);
		drawPie(canvas);
		canvas.restore();
	}

	//绘制饼装图
	private void drawPie(Canvas canvas) {
		float startAngle = 0.0f;
		for (int i = 0; i< entities.size() ;i++){
			//重置位置
			path.moveTo(0,0);
			PieEntity entity = entities.get(i);
			//扇形的颜色
			mPaint.setColor(entity.getColor());
			//绘制扇形的角度
			float sweepAngle = entity.getValue() / totalValue *360 - 1;

			//点击以后的区域
			if(position == i){
				path.arcTo(touchRectF, startAngle, sweepAngle);
			}else{
				path.arcTo(rectF, startAngle, sweepAngle);
			}

			canvas.drawPath(path,mPaint);

			//计算狐度的角度
			double radians = Math.toRadians(startAngle + sweepAngle / 2);
			float startX = (float) (radius * Math.cos(radians));
			float startY = (float) (radius * Math.sin(radians));
			float endX = (float) ((radius + 30) * Math.cos(radians));
			float endY = (float) ((radius + 30) * Math.sin(radians));
			canvas.drawLine(startX,startY,endX,endY,linePaint);

			//记录开始的角度值到集合，需要进行查询
			angles[i] = startAngle;

			//下一次的开始角度
			startAngle += sweepAngle + 1;
			path.reset();//重置颜色等值

			//打印百分比的字
			String percent = String.format("%.1f",entity.getValue() / totalValue * 100);
			percent = percent + "%";
			float textWith = 0.0f;
			if(startAngle % 360.0f >= 90.0f && startAngle % 360.0f <= 270.0f){
				textWith = linePaint.measureText(percent);
			}
			canvas.drawText(percent,endX - textWith,endY,linePaint);

		}
	}

	float totalValue;
	List<PieEntity> entities;
	//饼状图角度的个数
	float[] angles;
	//点击的索引对
	int position = -1;

	//设置数据
	public void setData(List<PieEntity> entities){
		this.entities = entities;
		//计算需要画的内容的总量
		for (PieEntity entity: entities) {
			totalValue += entity.getValue();
		}
		angles = new float[entities.size()];
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//点击事件处理
		//1 获取点击区域，
		//2 是否有效点击区域
		//3 获取到点击区域的角度值
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN :
				//纠结坐标系到饼状图圆点
				float x = event.getX() -  with/2;
				float y = event.getY() -  height/2;
				//获取到点击的角度
				float touchAngle = MathUtil.getTouchAngle(x, y);
				//点击的半径，是否在有效的区域
				float touchRadius = (float) Math.sqrt(x * x + y * y);

				//为有效的点击事件
				if(touchRadius < radius){

					//这个返回值很有意思  返回 (-(插入点) - 1)，
					// 否则返回 (-(插入点) - 1)这句话要注意：要是查询的的值小于数组里面
//					的最小值那么结果(-(0)-1结果就是-1)，如果查询的 值大于数组里面的
//					y最大值。那么结果就是(-(它的索引值)-1结果就是-1-(1+索引值))
					int searchResult = Arrays.binarySearch(angles, touchAngle);

					//得到了角度所在集合的索引值
					if(searchResult < 0 ){
						position = - searchResult - 1 - 1;
					}else{
						position = searchResult;
					}
					L.e(" position : " + position);
					//重新绘制
					invalidate();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return super.onTouchEvent(event);
	}


}
