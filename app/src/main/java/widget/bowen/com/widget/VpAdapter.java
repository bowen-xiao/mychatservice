package widget.bowen.com.widget;

/**
 * Created by 肖稳华 on 2017/3/18.
 */


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class VpAdapter extends PagerAdapter {
	private List<View> viewlist;

	public VpAdapter(List<View> viewlist) {
		this.viewlist = viewlist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return viewlist.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}


	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(viewlist.get(position), 0);
		return viewlist.get(position);
	}

}