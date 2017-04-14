package widget.bowen.com.widget;

import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;

/**
 * Created by 肖稳华 on 2017/3/3.
 */

public class MyApplication extends android.app.Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
		//TbsDownloader.needDownload(getApplicationContext(), false);

		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

			@Override
			public void onViewInitFinished(boolean arg0) {
				// TODO Auto-generated method stub
				Log.e("app", " onViewInitFinished is " + arg0);
			}

			@Override
			public void onCoreInitFinished() {
				// TODO Auto-generated method stub

			}
		};
		QbSdk.setTbsListener(new TbsListener() {
			@Override
			public void onDownloadFinish(int i) {
				Log.d("app","onDownloadFinish");
			}

			@Override
			public void onInstallFinish(int i) {
				Log.d("app","onInstallFinish");
			}

			@Override
			public void onDownloadProgress(int i) {
				Log.d("app","onDownloadProgress:"+i);
			}
		});

		QbSdk.initX5Environment(getApplicationContext(),  cb);

		TbsDownloader.needDownload(getApplicationContext(), true);
	}

/*
	private void initTbs() {
		//搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

			@Override
			public void onViewInitFinished(boolean arg0) {
				LogUtil.i("View是否初始化完成:" + arg0);
			}

			@Override
			public void onCoreInitFinished() {
				LogUtil.i("X5内核初始化完成");
			}
		};

		QbSdk.setTbsListener(new TbsListener() {
			@Override
			public void onDownloadFinish(int i) {
				LogUtil.i("腾讯X5内核 下载结束");
			}

			@Override
			public void onInstallFinish(int i) {
				LogUtil.i("腾讯X5内核 安装完成");
			}

			@Override
			public void onDownloadProgress(int i) {
				LogUtil.i("腾讯X5内核 下载进度:%" + i);
			}
		});

		QbSdk.initX5Environment(getApplicationContext(), cb);
	}*/


}
