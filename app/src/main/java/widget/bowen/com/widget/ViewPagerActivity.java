package widget.bowen.com.widget;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import widget.bowen.com.widget.transform.PageZoomTransformer;
//		setContentView(R.layout.activity_view_pager);

public class ViewPagerActivity extends Activity {

	private LinearLayout container;
	private ViewPager    viewpager;
	private VpAdapter    adapter;
	private List<View> viewlist = new ArrayList<View>();

	private int screenWidth = 0;
	private String[] data = { "第一页", "第二页", "第三页", "第四页", "第五页", "第六页" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager);
		// 獲取屏幕寬度
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;

		initData();
		initView();


	}

	private void initView() {
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		float with =  - displayMetrics.widthPixels * 0.4f;
		container = (LinearLayout) this.findViewById(R.id.container);
		viewpager = (ViewPager) this.findViewById(R.id.viewpager);
		viewpager.setOffscreenPageLimit(5); // viewpager缓存页数
		viewpager.setPageMargin((int) with); // 设置各页面的间距

		/*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			screenWidth / 3, LinearLayout.LayoutParams.WRAP_CONTENT);
		viewpager.setLayoutParams(params);*/

		adapter = new VpAdapter(viewlist);
		viewpager.setAdapter(adapter);
		viewpager.setCurrentItem(1);

		// 将父节点Layout事件分发给viewpager，否则只能滑动中间的一个view对象
		container.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return viewpager.dispatchTouchEvent(event);
			}
		});
		viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//				e(position + " : position ;" + positionOffset + " : positionOffset; " + positionOffsetPixels + " : positionOffsetPixels");
//				存活的个数
			}

			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		PageZoomTransformer pageZoomTransformer = new PageZoomTransformer();
		viewpager.setPageTransformer(true, pageZoomTransformer);

	}

	private void initData() {
		int size = data.length;
		int[] colors = new int[]{Color.RED,Color.BLUE,Color.DKGRAY};
		for (int i = 0; i < size; i++) {
			View view = LayoutInflater.from(this).inflate(R.layout.viewitem, null);
			view.findViewById(R.id.card_view_root).setBackgroundColor(colors[i%colors.length]);
			((TextView)view.findViewById(R.id.textView1)).setText(data[i]);
			viewlist.add(view);
		}
	}

	protected void e(String str){
		Log.e("main -- " , str);
	}

}
