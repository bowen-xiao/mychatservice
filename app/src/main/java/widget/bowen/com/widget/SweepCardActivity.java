package widget.bowen.com.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class SweepCardActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		setContentView(R.layout.activity_swep_card);

//		WebView webView = (WebView) findViewById(R.id.my_web_view);
//		webView.loadUrl("http://192.168.13.93:8080/wap/guideInfo/g_comment_list.html?id=130");
//		com.tencent.smtt.sdk.WebView webView2 = new com.tencent.smtt.sdk.WebView(this);
//		int width = webView2.getView().getWidth();
	}

/*	WebView     tbsWebView;
//	ProgressBar web_bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swep_card);

		tbsWebView = (WebView) findViewById(R.id.my_web_view);
		tbsWebView.loadUrl("http://192.168.13.93:8080/wap/guideInfo/g_comment_list.html?id=130");

		WebSettings settings = tbsWebView.getSettings();
		settings.setJavaScriptEnabled(true);

		// 设置加载进来的页面自适应手机屏幕
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);

		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setDisplayZoomControls(true);


		//settings.setUserAgent( );

		tbsWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.e("WebView ", "shouldOverrideUrlLoading " + url);
				view.loadUrl(url);
				return true;
			}

		});

		tbsWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				*//*web_bar.setProgress(newProgress);

				if (newProgress == 100) {
					web_bar.setVisibility(View.GONE);
				} else {
					web_bar.setVisibility(View.VISIBLE);
				}*//*
			}

		});
	}

	@Override
	protected void onDestroy() {
		if (tbsWebView != null){
			tbsWebView.removeAllViews();
			tbsWebView.destroy();
		}
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && tbsWebView.canGoBack()) {
			tbsWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}*/
}
