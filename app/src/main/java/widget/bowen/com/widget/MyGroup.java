package widget.bowen.com.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by 肖稳华 on 2017/2/13.
 */

public class MyGroup extends ViewGroup {

	public MyGroup(Context context) {
		this(context,null);
	}

	public MyGroup(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MyGroup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	//初始化操作
	private void init() {
		L.e(this.getClass().getSimpleName() + " : 带详情信息的Log 初始化");

		for (int i = 4 ; i > 0 ;i--) {
			ImageView imageView = new ImageView(getContext());
			imageView.setImageResource(R.mipmap.ic_launcher);
			addView(imageView);
		}
	}

	@Override
	protected void onLayout(boolean b, int left, int top, int right, int bottom) {
		int count = getChildCount();
		int width = 80;
		for (int index = 0 ; index < count;index++){
			View view = getChildAt(index);
			//横向展示
//			view.layout(index * width , 0 , (index+1) *width, width);
			//纵向展示
			view.layout(0 , index * width ,  width ,width * (index+1) );
		}


	}
}
