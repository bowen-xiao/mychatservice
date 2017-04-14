package widget.bowen.com.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class HorizontalScrollViewActivity extends AppCompatActivity {

	private LinearLayout mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int[] colors = new int[]{Color.RED, Color.BLUE, Color.DKGRAY};
		setContentView(R.layout.activity_horizontal_scroll_view);
		mAdapter = (LinearLayout) findViewById(R.id.linear_layout_root);
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		int widthPixels = displayMetrics.widthPixels;
		for (int i = 0; i < 10; i++) {
			View view = LayoutInflater.from(this).inflate(R.layout.viewitem, null);
			view.findViewById(R.id.card_view_root).setBackgroundColor(colors[i%colors.length]);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthPixels, (int) (0.4f * widthPixels));
			params.rightMargin = (int) (-0.5f * widthPixels);
			view.setLayoutParams(params);
			mAdapter.addView(view);
		}
	}
}
