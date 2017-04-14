package widget.bowen.com.widget.transform;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by 肖稳华 on 2017/3/18.
 */

public class PageZoomTransformer implements ViewPager.PageTransformer {
	private static float MIN_SCALE = 0.85f;

	private static float MIN_ALPHA = 0.96f;

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void transformPage(View view, float position) {
		/*int pageWidth = view.getWidth();
		int pageHeight = view.getHeight();

		ViewPager group = (ViewPager) view.getParent();

		//已经看不见的
		if (position < -1) { // [-Infinity,-1)
			// This page is way off-screen to the left.
			view.setAlpha(0);
			view.setElevation(0);
			//view.setBackgroundColor(Color.RED);
		} else if (position <= 0) { // [-1,1]

			// Modify the default slide transition to
			// shrink the page as well
			float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
			float vertMargin = pageHeight * (1 - scaleFactor) / 2;
			float horzMargin = pageWidth * (1 - scaleFactor) / 2;
			if (position < 0) {
				view.setTranslationX(horzMargin - vertMargin / 2);
			} else {
				view.setTranslationX(-horzMargin + vertMargin / 2);
			}
			// Scale the page down (between MIN_SCALE and 1)
			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);
			// Fade the page relative to its size.
			view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
									  / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
			view.setElevation(MIN_ALPHA + (scaleFactor - MIN_SCALE)
										  / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

			ViewGroup parent = (ViewGroup) view.getParent();
			int childCount = parent.getChildCount();
			//Log.e("main-------",position + " position : childCount : " + childCount);
			view.setBackgroundColor(Color.YELLOW);

			//向左滑动  ,需要更改右边视图的大小
		}  else if (position <= 1) {
			//向右滑动,需要更改左边视图的大小
			float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
			float vertMargin = pageHeight * (1 - scaleFactor) / 2;
			float horzMargin = pageWidth * (1 - scaleFactor) / 2;
			if (position < 0) {
				view.setTranslationX(horzMargin - vertMargin / 2);
			} else {
				view.setTranslationX(-horzMargin + vertMargin / 2);
			}
			// Scale the page down (between MIN_SCALE and 1)
			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);
			// Fade the page relative to its size.
			view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
									  / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
			view.setElevation(MIN_ALPHA + (scaleFactor - MIN_SCALE)
										  / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

			ViewGroup parent = (ViewGroup) view.getParent();
			int childCount = parent.getChildCount();
			//Log.e("main-------",position + " position : childCount : " + childCount);
			view.setBackgroundColor(Color.BLUE);

		}else { // (1,+Infinity]
			// This page is way off-screen to the right.
			view.setAlpha(0);
			view.setElevation(0);
			//view.setBackgroundColor(Color.BLACK);
		}*/
		final float normalizedposition = Math.abs(Math.abs(position) - 1);
		//比例值
		float scaleY = normalizedposition / 2 + 0.56f;
		view.setScaleX(scaleY);
		view.setScaleY(scaleY);
		//设置高度
		view.setElevation(scaleY);
		//不可见的时候
		if(position < -1){
			view.setAlpha(1);
			view.setElevation(0);
		//可见的时候
		}else if (position <= 1){
			/*float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
			Log.e("main", " scaleFactor : " + scaleFactor);
			*//*view.setElevation(MIN_ALPHA + (scaleFactor - MIN_SCALE)
										  / (1 - MIN_SCALE) * (1 - MIN_ALPHA));*//*
			view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
									  / (1 - MIN_SCALE) * (1 - MIN_ALPHA));*/
		}else { // (1,+Infinity]
			// This page is way off-screen to the right.
			view.setAlpha(1);
			view.setElevation(0);
			//view.setBackgroundColor(Color.BLACK);
		}
	}
}