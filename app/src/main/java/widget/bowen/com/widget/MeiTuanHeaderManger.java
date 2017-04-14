package widget.bowen.com.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by 肖稳华 on 2017/2/17.
 */

public class MeiTuanHeaderManger extends SelfHeaderManager {

	private ImageView ivPushDown;
	private ImageView ivRefreshing;

	public MeiTuanHeaderManger(Context context) {
		this.context = context;
	}

	@Override
	public View getSelfHeaderView() {
		if(selfHeader == null){
			selfHeader = View.inflate(context,R.layout.view_refresh_header_meituan,null);
			selfHeader.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			ivPushDown = (ImageView) selfHeader.findViewById(R.id.iv_meituan_pull_down);
			ivRefreshing = (ImageView) selfHeader.findViewById(R.id.iv_meituan_release_refreshing);
		}
		return selfHeader;
	}

	@Override
	public void changeToIdle() {

	}

	@Override
	public void changeToPushDown() {
		ivPushDown.setVisibility(View.VISIBLE);
		ivRefreshing.setVisibility(View.INVISIBLE);
	}

	@Override
	public void changeToReleaseRefresh() {
		ivPushDown.setVisibility(View.INVISIBLE);
		ivRefreshing.setVisibility(View.VISIBLE);
		ivRefreshing.setImageResource(R.drawable.release_refresh);
		AnimationDrawable releaseAnimationDrawable = (AnimationDrawable) ivRefreshing.getDrawable();
		releaseAnimationDrawable.start();
	}

	@Override
	public void changeToRefreshing() {
		ivRefreshing.setImageResource(R.drawable.refresh_mt_refreshing);
		AnimationDrawable refreshingAnimationDrawable = (AnimationDrawable) ivRefreshing.getDrawable();
		refreshingAnimationDrawable.start();
	}

	@Override
	public void refreshComplete() {
		ivPushDown.setVisibility(View.VISIBLE);
		ivRefreshing.setVisibility(View.INVISIBLE);
	}

	@Override
	public void handlerScale(float scale) {
		ivPushDown.setScaleX(scale);
		ivPushDown.setScaleY(scale);
	}
}
