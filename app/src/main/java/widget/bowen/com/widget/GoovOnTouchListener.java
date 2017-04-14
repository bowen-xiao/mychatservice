package widget.bowen.com.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by 肖稳华 on 2017/2/15.
 */

public class GoovOnTouchListener implements View.OnTouchListener,GoovView.OnGooViewChangeListener{

	GoovView goovView;
	private final WindowManager windowManager;

	private       TextView                   tv_unread_msg_count;
	private final WindowManager.LayoutParams params;

	public GoovOnTouchListener(Context context){
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		goovView = new GoovView(context);
		goovView.setOnGooViewChangeListener(this);
		params = new WindowManager.LayoutParams();
		//宽度和高度是
		params.height = WindowManager.LayoutParams.MATCH_PARENT;
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.format = PixelFormat.TRANSLUCENT;//类型是透明
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//获取TextView控件的父布局即条目的根部局,通过根部局,让RecyclerView不要拦截事件
		v.getParent().requestDisallowInterceptTouchEvent(true);
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				L.e("ACTION_DOWN");
				tv_unread_msg_count = (TextView) v;
				tv_unread_msg_count.setVisibility(View.INVISIBLE);
				float rawX = event.getRawX();
				float rawY = event.getRawY();
				goovView.setPosition(rawX, rawY);
				goovView.setText(tv_unread_msg_count.getText().toString());
				windowManager.addView(goovView,params);
				break;
			case MotionEvent.ACTION_MOVE:
				L.e("ACTION_MOVE");
				break;
			case MotionEvent.ACTION_UP:
				L.err("ACTION_UP");
				break;
		}
		goovView.onTouchEvent(event);
		//拦截下事件，需要自己进行处理
		return true;
	}

	@Override
	public void onDisappear() {
		if (goovView.getParent() != null) {
			windowManager.removeView(goovView);
		}
	}

	@Override
	public void onReset() {
		//重置操作
		//1,移除GooView
		//WindowManager的addView方法是将GooView添加到root根部局中
		//removeView是将GooView从root根部局中移除,当移除后的GooView再次尝试从root中移除就会抛出
		//View not attached to window manager这样的异常
		//是由已经被WindowManager移除的视图,再此被移除
		//如果已经添加到root上的GooView会有一个Parent(父视图),判断父视图是否为空,就可以规避这个bug
		if (goovView.getParent() != null) {
			Log.i("test", "removeView");
			windowManager.removeView(goovView);
			//2,让TextView显示出来
			tv_unread_msg_count.setVisibility(View.VISIBLE);
		}
	}
}
