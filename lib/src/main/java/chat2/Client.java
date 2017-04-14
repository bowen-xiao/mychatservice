package chat2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by 肖 on 2017/4/10.
 */

public class Client {
	public static void main(String args[]){
		try {
			final Socket socket = new Socket("192.168.13.119",Service.port);
			final boolean[] flag = {true};
			while(flag[0]){

				OutputStream outputStream = socket.getOutputStream();
				System.out.println("------等待输入------");
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String line = reader.readLine();
				outputStream.write(line.getBytes());

				new Thread(){
					@Override
					public void run() {
						InputStream inputStream = null;
						try {
							inputStream = socket.getInputStream();
							byte[] bytes = new byte[1024];
							int readByte = 0;
							StringBuilder stringBuilder = new StringBuilder();
							while ((readByte = inputStream.read(bytes) ) > 0){
								String tep = new String(bytes, 0, readByte).trim();
								if(tep != null && tep.length() > 0){
									stringBuilder.append(tep);
									System.out.println(" 服务器回复 ：" + tep);
								}
							}
						} catch (IOException e) {
							//e.printStackTrace();
							System.out.println("与服务器断开连接,程序退出");
							flag[0] = false;
						}
					}
				}.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
