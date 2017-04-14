package widget.bowen.com.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 肖稳华 on 2017/2/10.
 */

public class MyCanvas extends View {

	private Paint mPaint;

	//用于new关键字加载控件
	public MyCanvas(Context context) {
		this(context,null);
	}

	//xml中声名需要使用
	public MyCanvas(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	//xml中声名需要使用,自带style字段时调用
	public MyCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	//初始化内容
	private void init() {
		LogUtils.d("初始化");
		L.e("带详情信息的Log 初始化");
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(5.0f);
	}

	//圆的中心点位置
	PointF mCircleCenter = new PointF(240,400);
	//圆的半径
	private int mRadius = 200;

	PointF mLeft = new PointF(140,340);
	private int eyeRadius = 60;
	PointF mRight = new PointF(340,340);

	PointF mLineStart1 = new PointF(110, 260);
	PointF mLineEnd1   = new PointF(370, 260);

	PointF mLineStart2 = new PointF(240, 260);
	PointF mLineEnd2   = new PointF(240, 480);
	PointF mLineEnd3   = new PointF(180, 420);

	//嘴巴的矩形区域
	RectF mMouth = new RectF(100.0f,440.0f,380.0f,560.0f);

	RectF mEar = new RectF(15.0f,300.0f,45.0f,500.0f);

	//右边的耳朵，是一个贝塞尔曲线
	Path mRightEar = new Path();

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//圆脸
		canvas.drawCircle(mCircleCenter.x,mCircleCenter.y,mRadius,mPaint);

		//两只眼睛
		canvas.drawCircle(mLeft.x,mLeft.y,eyeRadius,mPaint);
		canvas.drawCircle(mRight.x,mRight.y,eyeRadius,mPaint);
		//眉毛
		canvas.drawLine(mLineStart1.x, mLineStart1.y, mLineEnd1.x, mLineEnd1.y, mPaint);
		//鼻子
		canvas.drawLine(mLineStart2.x, mLineStart2.y, mLineEnd2.x, mLineEnd2.y, mPaint);
		canvas.drawLine(mLineEnd2.x, mLineEnd2.y, mLineEnd3.x, mLineEnd3.y, mPaint);
		//嘴巴
		//drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
		canvas.drawArc(mMouth.left,mMouth.top,mMouth.right,mMouth.bottom,30.0f,120.0f,false,mPaint);

		canvas.drawRect(mEar,mPaint);

		mRightEar.moveTo(420,310);

		//第一个控制点，第二个终点
		//耳朵的路线
		mRightEar.quadTo(480,280,440,360);
		mRightEar.quadTo(480,320,430,460);
		canvas.drawPath(mRightEar,mPaint);
	}
}
