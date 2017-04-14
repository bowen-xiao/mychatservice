package widget.bowen.com.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by 肖稳华 on 2017/2/14.
 */

public class SettingView extends RelativeLayout {


	private ImageView tvToggle;

	public SettingView(Context context) {
		this(context,null);
	}

	public SettingView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs);
	}

	//初始化操作
	private void init(AttributeSet attrs) {
		View view = View.inflate(getContext(),R.layout.view_setting_item,this);
		TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SettingView);
		int myBackground = array.getInt(R.styleable.SettingView_my_background, 0);

		//获取传递过来的属性
		String title = array.getString(R.styleable.SettingView_my_title);
		//设置默认值
		boolean toggle = array.getBoolean(R.styleable.SettingView_my_toggle,isToggle);

		int bg =   R.drawable.seting_first_selector;
		switch (myBackground){
			case 2:
				bg = R.drawable.seting_last_selector;
				break;
			case 1:
				bg = R.drawable.seting_middle_selector;
				break;
		}

		//回收资源
		array.recycle();
		//设置可以点击就可以看到点击背景效果
		view.setClickable(true);
		tvToggle = (ImageView) this.findViewById(R.id.view_iv_toggle);

		//设置背景
		view.setBackgroundResource(bg);

		L.e(" my_title : " + title);
		TextView tvTitle = (TextView) this.findViewById(R.id.view_tv_title);
		tvTitle.setText(title);

		setToggle(toggle);
	}

	boolean isToggle = false;

	public void toggle(){
		setToggle(!isToggle);
	}

	public void setToggle(boolean isToggle){
		tvToggle.setImageResource(isToggle ? R.drawable.on : R.drawable.off);
		this.isToggle = isToggle;
	}

}
