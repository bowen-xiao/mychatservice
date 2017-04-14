package widget.bowen.com.widget;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RefreshActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refresh);

		//找到view
		final RefreshView refreshView = (RefreshView) findViewById(R.id.activity_refresh);
		//设置头部管理器
		refreshView.setHeaderManager(new MeiTuanHeaderManger(this));
		refreshView.setOnRefreshingListener(new RefreshView.OnRefreshingListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						refreshView.completeRefresh();
					}
				},5000);
			}
		});

		initRecyclerView();

		/*new Thread(new Runnable() {
			@Override
			public void run() {
				initDownLoad();
			}
		}).start();*/

	}

	//去下载内容
	/*private void initDownLoad() {

		try {
			URL url = new URL("http://www.gdsi.gov.cn/gdsiapp.apk");
			URLConnection connection = url.openConnection();
			connection.connect();
			// this will be useful so that you can show a typical 0-100% progress bar
			int fileLength = connection.getContentLength();
			// download the file
			InputStream input = new BufferedInputStream(connection.getInputStream());
			OutputStream output = new FileOutputStream("/sdcard/BarcodeScanner-debug.apk");
			byte data[] = new byte[1024];
			long total = 0;
			int count;
			Log.e("fileLength",fileLength + ":fileLength");
			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				Bundle resultData = new Bundle();
				resultData.putInt("progress" ,(int) (total * 100 / fileLength));
				Log.e("test",total + ":total");

				output.write(data, 0, count);
			}
			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}*/

	//初始化视图
	private void initRecyclerView(){
		List<Msg> msgs = new ArrayList<>();
		for (int i = 1; i < 55; i++) {
			msgs.add(new Msg("标题" + "i",i));
		}
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		MsgAdapter adapter = new MsgAdapter(msgs,this);
		recyclerView.setAdapter(adapter);
	}
}
