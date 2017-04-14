package widget.bowen.com.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 肖稳华 on 2017/3/2.
 */

public class MyDragView extends View {
	Context context;
	private Bitmap mDrawBitmap;

	public MyDragView(Context context) {
		this(context,null);
	}

	public MyDragView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MyDragView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		mDrawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

		//这样就可以出现在屏幕的最中间
		int widthPixels = (int) ((displayMetrics.widthPixels - mDrawBitmap.getWidth() ) * 0.5f);
		int heightPixels = (int) ((displayMetrics.heightPixels - mDrawBitmap.getHeight() ) * 0.5f);
		mBitmapPoint = new PointF(widthPixels,heightPixels);
	}

	PointF mBitmapPoint;

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mDrawBitmap,mBitmapPoint.x,mBitmapPoint.y,null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				mBitmapPoint.set(event.getX(),event.getY());
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				mBitmapPoint.set(event.getX(),event.getY());
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				break;

		}
		return true;
	}
}
