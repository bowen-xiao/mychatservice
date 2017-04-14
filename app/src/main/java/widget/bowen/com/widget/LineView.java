package widget.bowen.com.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by 肖稳华 on 2017/2/17.
 */
public class LineView extends View {

	//需要去绘制的图片
	private Bitmap src;
	//屏幕的宽度
	private int windWidth;
	//最大的输入天数
	private final int max_days = 7;
	private Bitmap scaledBitmap;

	public LineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		//原来图片的大小获取
		src = BitmapFactory.decodeResource(getResources(), R.mipmap.movie_timeline);
		//获取屏幕的宽度
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		windWidth = metrics.widthPixels;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measureWidth = measureWidth(widthMeasureSpec);
		int measureHeight = (int) (measureWidth * src.getHeight() * 1.0f / src.getWidth());

		setMeasuredDimension(measureWidth,measureHeight);
	}

	//测量后的宽度
	private int measureWidth(int widthMeasureSpec) {
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		int size = MeasureSpec.getSize(widthMeasureSpec);
		int result = 0;
		switch (mode){
			//精确值与未指定为一类
			case  MeasureSpec.UNSPECIFIED:
			case  MeasureSpec.EXACTLY:
				result = size;
				break;
			//这里为屏幕宽度
			case  MeasureSpec.AT_MOST:
				//取最小值
				result = (int) Math.min(src.getWidth() * 1.0f * max_days, windWidth);
				break;
		}
		return result;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		//最后控件显示的大小,onMeasure之后会调用
		float scaleWidth = w * 1.0f / max_days + 0.5f;
		//创建后的新图片
		scaledBitmap = Bitmap.createScaledBitmap(src, (int) scaleWidth, (int) scaleWidth * src.getHeight() / src.getWidth(), true);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < max_days; i++) {
			canvas.drawBitmap(scaledBitmap,i*scaledBitmap.getWidth(),0,null);
		}
	}
}
