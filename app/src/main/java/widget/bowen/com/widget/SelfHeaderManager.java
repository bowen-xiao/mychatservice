package widget.bowen.com.widget;

import android.content.Context;
import android.view.View;

/**
 * Created by 肖稳华 on 2017/2/16.
 */

public abstract class SelfHeaderManager {

	protected   Context   context;
	protected View            selfHeader;

	//获取到视图
	public abstract View getSelfHeaderView();

	//获取头部的高度
	public int getHeaderHeight(){
		selfHeader.measure(0,0);
		int measuredHeight = selfHeader.getMeasuredHeight();
		return measuredHeight;
	}

	//修改状态
	public abstract void changeToIdle();
	//需要修改文字,以及动画效果，箭头向下旋转
	public abstract void changeToPushDown() ;
	//需要修改文字,以及动画效果，箭头向上旋转
	public abstract void changeToReleaseRefresh();

	public abstract void  changeToRefreshing();

	//刷新完成的操作
	public abstract void refreshComplete();

	public abstract void handlerScale(float scale);
}
