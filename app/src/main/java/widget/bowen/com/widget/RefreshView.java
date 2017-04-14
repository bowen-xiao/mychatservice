package widget.bowen.com.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by 肖稳华 on 2017/2/16.
 */

public class RefreshView extends LinearLayout {

	private final Context context;
	private LinearLayout wholeHeader;

	//隐藏时候的高度值，是负高度
	private int minWholeHeaderHeight;
	//最大显示的高度 = 头部高度 * 可见系数
	private int maxWholeHeaderHeight;
	//可见系数，可以手动修改
	private float maxWholeHeaderHeightRadio = 0.3f;
	private float downY;


	public RefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context);
	}

	//实始化操作
	private void init(Context context) {
		//设置父级菜单为
		this.setOrientation(LinearLayout.VERTICAL);
//		1 头布局的根布局
		initWholeHeaderView(context);
//		2 默认布局样式
//		initSelfHeaderView(context);
	}

	private SelfHeaderManager headerManager;

	//默认的头样式
	private void initSelfHeaderView() {
		View selfHeaderView = headerManager.getSelfHeaderView();
		minWholeHeaderHeight = - headerManager.getHeaderHeight();
		maxWholeHeaderHeight = (int) (headerManager.getHeaderHeight() * maxWholeHeaderHeightRadio);

		wholeHeader.setPadding(0, minWholeHeaderHeight, 0, 0);
		wholeHeader.addView(selfHeaderView);
	}

	//头部的根布局
	private void initWholeHeaderView(Context context) {
		wholeHeader = new LinearLayout(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		wholeHeader.setLayoutParams(layoutParams);
		wholeHeader.setBackgroundColor(Color.RED);
		addView(wholeHeader);
	}

	public void setHeaderManager(SelfHeaderManager headerManager) {
		this.headerManager = headerManager;
		initSelfHeaderView();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downY = event.getY();
				return true;
			case MotionEvent.ACTION_MOVE:
				if(handlerMove(event)){
					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
				if(handlerUp()){
					return true;
				}
				break;
		}
		return super.onTouchEvent(event);
	}

	private boolean handlerUp() {
		//还原初始值
		downY = 0;
		//释放刷新
		if(currentStatus == RefreshStatus.RELEASE_REFRESH){

			//刷新中的操作
			beginRefresh();
		}else{
			//状态还原
			currentStatus = RefreshStatus.IDLE;
		//隐藏头部内容
			hideRefreshHeader();
			handlerStatus();
		}
		return wholeHeader.getPaddingTop() > minWholeHeaderHeight;
	}

	//处理状态
	private void beginRefresh() {
		//修改状态
		currentStatus = RefreshStatus.REFRESHING;
		//将刷新头的回到 0
		setRefreshHeaderPaddingToZero();
		handlerStatus();
		if(mOnRefreshingListener != null){
			mOnRefreshingListener.onRefresh();
		}
	}

	//刷新中的操作，需要activity配合使用
	private OnRefreshingListener mOnRefreshingListener;

	public void setOnRefreshingListener(OnRefreshingListener mOnRefreshingListener) {
		this.mOnRefreshingListener = mOnRefreshingListener;
	}

	//完成刷新的动作
	public void completeRefresh() {
		// 1 隐藏头部内容
		hideRefreshHeader();
		// 2 更新控件的状态
		currentStatus = RefreshStatus.IDLE;
		// 3 通知头部管理器去更新UI
		headerManager.refreshComplete();
	}

	public interface OnRefreshingListener{
		void  onRefresh();
	}


	private void setRefreshHeaderPaddingToZero() {
		ValueAnimator animator = ValueAnimator.ofInt(wholeHeader.getPaddingTop(), 0);
		animator.setDuration(300);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int currentPaddingTop = (int) animation.getAnimatedValue();
				wholeHeader.setPadding(0,currentPaddingTop,0,0);
			}
		});
		animator.start();
	}

	//去隐藏头部
	private void hideRefreshHeader() {
		ValueAnimator animator = ValueAnimator.ofInt(wholeHeader.getPaddingTop(),minWholeHeaderHeight);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int currentPaddingTop = (int) animation.getAnimatedValue();
				wholeHeader.setPadding(0,currentPaddingTop,0,0);
			}
		});
		animator.setDuration(300);
		animator.start();
	}

	private float dragRadio = 1.8f;

	private boolean handlerMove(MotionEvent event) {
		//锁住页面不再进行操作
		if(currentStatus == RefreshStatus.REFRESHING){
			return false;
		}
		//onTouchEvent被拦截了，需要将值补上
		if(downY == 0){
			downY = event.getY();
		}

		int moveY = (int) (event.getY() );
		int dy = (int) (moveY - downY);
		if(dy > 0){
			int paddingTop = (int) (dy / dragRadio + minWholeHeaderHeight);
			paddingTop = Math.min(paddingTop,maxWholeHeaderHeight);

			//下拉出来的状态
			if(paddingTop > 0 && currentStatus != RefreshStatus.RELEASE_REFRESH){
				//将状态改变 释放刷新
				Log.e("msg","将状态改变 释放刷新");
				currentStatus = RefreshStatus.RELEASE_REFRESH;
				//处理控件的状态
				handlerStatus();

			}else if(paddingTop <= 0){
				if(currentStatus != RefreshStatus.PUSH_DOWN){
					//将状态改变 下拉刷新
					currentStatus = RefreshStatus.PUSH_DOWN;
					Log.e("msg","将状态改变 下拉刷新");
					handlerStatus();
				}
				//计算下拉的值百分比
				float scale = 1.0f - ( paddingTop*1.0f / minWholeHeaderHeight );
				Log.e("main",scale + " : scale ");
				headerManager.handlerScale(scale);
			}

			wholeHeader.setPadding(0, paddingTop, 0, 0);
			return true;
		}
		return false;
	}

	//处理控件状态
	private void handlerStatus() {
		switch (currentStatus){
			case IDLE:
				headerManager.changeToIdle();
				break;
			case PUSH_DOWN:
				headerManager.changeToPushDown();
				break;
			case RELEASE_REFRESH:
				headerManager.changeToReleaseRefresh();
				break;
			case REFRESHING:
				headerManager.changeToRefreshing();
				break;
		}
	}

	//控件的当前状态 release
	private RefreshStatus currentStatus =  RefreshStatus.IDLE;

	public enum RefreshStatus{
		IDLE,PUSH_DOWN,RELEASE_REFRESH,REFRESHING
	}

	//去判断是否需要拦截
	private float interceptDownX;
	private float interceptDownY;

	//事件拦截
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()){
			case MotionEvent.ACTION_DOWN:
				interceptDownX = ev.getX();
				interceptDownY = ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				//判断是否需要拦截事件
				float dy = (ev.getY() - interceptDownY);
				if(Math.abs(ev.getX() - interceptDownX) < Math.abs(dy)){
					//是向下划动，且里面的子视图需要已经到了顶部

					if(dy > 0 && handlerRefresh()){
						return true;
					}
					//刷新中拦截所有事件,禁止滚动操作
					if(currentStatus == RefreshStatus.REFRESHING){
						return true;
					}

				}
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	RecyclerView mRecyclerView ;
	ScrollView   mScrollView ;

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		View targetView = getChildAt(1);
		if(targetView instanceof RecyclerView){
			mRecyclerView = (RecyclerView) targetView;
		}else if(targetView instanceof ScrollView){
			mScrollView = (ScrollView) targetView;
		}
	}

	//可以兼容更多的视图
	private boolean handlerRefresh() {
		if(RefreshScrollingUtil.isRecyclerViewToTop(mRecyclerView)){
			return true;
		}
		if(RefreshScrollingUtil.isScrollViewOrWebViewToTop(mScrollView)){
			return true;
		}
		return false;
	}
}
