package widget.bowen.com.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {

	@BindView(R.id.input_text)
	EditText     inputText;
	@BindView(R.id.send)
	Button       send;
	@BindView(R.id.received_text)
	TextView     receivedText;
	@BindView(R.id.activity_chat)
	LinearLayout activityChat;

	String address = "192.168.13.119";
	int port = 8888;
	private Socket socket;
	private OutputStream outputStream;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		ButterKnife.bind(this);
		initSocket();
	}

	private void initSocket(){

			new Thread(){
				@Override
				public void run() {
					try {
					getConnection();

					while (true){
							/*InputStream inputStream = socket.getInputStream();
							byte[] b = new byte[1024];
							inputStream.read(b);
							Log.e("main","input"+new String(b));*/

						DataInputStream dis = new DataInputStream(socket.getInputStream());

						//监听服务器回复消息
						String line = null;
						try {
							while((line = dis.readUTF()) != null){
								//						System.out.println("client receive msg from server: " + line);
								Log.e("main",line);
							}
						} catch (IOException e) {
							e.printStackTrace();
							try {
								sleep(1500L);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							//重新连接
							getConnection();
						}
					}
					} catch (IOException e) {
						e.printStackTrace();
						Log.e("main","err one : "+ e.getMessage());
						//重新连接
						getConnection();
					}
				}
			}.start();

	}

	//重新获取连接
	private void getConnection(){
		try {
			//如果已经连接就不再连接
			if(socket != null && socket.isConnected()){return;}
			socket = new Socket(address, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//关闭连接
	private void closeConnection(){
		try {
			socket.getInputStream().close();
			socket.getOutputStream().close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		closeConnection();
		super.onDestroy();
	}

	@OnClick(value =  {R.id.send})
	public void sendMsg(View view){
		String str = inputText.getText().toString();
		try {
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(str);//发给服务端
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("main"," msg two : " + e.getMessage());
		}
		inputText.setText("");
	}

}
