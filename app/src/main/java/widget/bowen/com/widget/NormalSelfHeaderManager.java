package widget.bowen.com.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 肖稳华 on 2017/2/16.
 */

public class NormalSelfHeaderManager extends SelfHeaderManager  {

	private RotateAnimation upAnimation;
	private RotateAnimation downAnimation;
	private TextView        tvStatus;
	private ImageView       ivArrows;
	private ImageView ivCircle;
	private AnimationDrawable animationDrawable;

	public NormalSelfHeaderManager(Context context) {
		this.context = context;
		initAnimation();
	}

	//动画效果
	private void initAnimation() {
		upAnimation = new RotateAnimation(0, 180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
										  RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		upAnimation.setDuration(100);
		upAnimation.setFillAfter(true);

		downAnimation = new RotateAnimation( 180,0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
											RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		downAnimation.setDuration(100);
		downAnimation.setFillAfter(true);
	}

	//获取到视图
	public View getSelfHeaderView(){
		if(selfHeader == null){
			selfHeader = View.inflate(context, R.layout.view_refresh_header_normal, null);
			selfHeader.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			tvStatus = (TextView) selfHeader.findViewById(R.id.tv_normal_refresh_header_status);
			ivArrows = (ImageView) selfHeader.findViewById(R.id.iv_normal_refresh_header_arrow);
			ivCircle = (ImageView) selfHeader.findViewById(R.id.iv_normal_refresh_header_chrysanthemum);
			animationDrawable = (AnimationDrawable) ivCircle.getDrawable();
		}
		return selfHeader;
	}

	//获取头部的高度
	public int getHeaderHeight(){
		selfHeader.measure(0,0);
		int measuredHeight = selfHeader.getMeasuredHeight();
		return measuredHeight;
	}


	//修改状态
	public void changeToIdle() {

	}

	//需要修改文字,以及动画效果，箭头向下旋转
	public void changeToPushDown() {
		downAnimation.setDuration(300);
		tvStatus.setText("下拉刷新");
		ivArrows.startAnimation(downAnimation);
	}

	//需要修改文字,以及动画效果，箭头向上旋转
	public void changeToReleaseRefresh() {
		tvStatus.setText("释放刷新");
		ivArrows.startAnimation(upAnimation);
	}


	public void changeToRefreshing() {
		tvStatus.setText("刷新中...");
		ivArrows.clearAnimation();
		ivArrows.setVisibility(View.INVISIBLE);
		ivCircle.setVisibility(View.VISIBLE);
		animationDrawable.start();
	}

	//刷新完成的操作
	public void refreshComplete() {
		ivArrows.startAnimation(downAnimation);
		ivCircle.setVisibility(View.INVISIBLE);
		downAnimation.setDuration(0);
		ivArrows.setVisibility(View.VISIBLE);
		tvStatus.setText("下拉刷新");
	}

	@Override
	public void handlerScale(float scale) {
		//如果没有实现可以不做实现
	}
}
