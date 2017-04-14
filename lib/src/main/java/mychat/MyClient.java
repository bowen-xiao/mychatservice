package mychat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static java.lang.System.exit;

/**
 * Created by xiao on 2017/4/7.
 */

public class MyClient {
	static String hostIp = "192.168.13.119";
	static String textEncode = "UTF-8";
	public static void main(String args[]){
		DataInputStream input = null;
		BufferedWriter writer = null;
		try {
			Socket socket = new Socket(hostIp, MySocketService.port);
			System.out.println(socket + " : 连接成功 " );

			//向服务器发送出数据
			input = new DataInputStream(socket.getInputStream());
			final BufferedReader reader = new BufferedReader(new InputStreamReader(input,textEncode));

			//服务器回写的数据,??要显??
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			writer = new BufferedWriter(new OutputStreamWriter(out,textEncode));

			new Thread(){
				@Override
				public void run() {
					String inputStr = null;
					try {
						while(true){
							while ((inputStr = reader.readLine()) != null){
								inputStr = inputStr.trim();
								System.out.println("服务器回复 : " + inputStr + "");
								//结果本次循环
								break;
							}
						}
					} catch (IOException e) {
						//e.printStackTrace();
						System.out.println(" 服务器连接失败,程序即将退出 ");
						System.exit(0);
					}
				}
			}.start();

			byte[] bytes = new byte[1024];
			while (true){
				System.out.println("-----------请输入要发的内容-------------");
				System.in.read(bytes);
				String inputStr = new String(bytes);
				inputStr = inputStr.trim();
				if(inputStr.contains("exit")){
					//退出系统
					exit(0);
					socket.close();
					return;
				}
				// rn 必须加上
				writer.write(inputStr + "\r\n");
				writer.flush();
				//获取服务器回写的信息
				/*new Thread(){
					@Override
					public void run() {
						String inputStr = null;
						try {
							while ((inputStr = reader.readLine()) != null){
								inputStr = inputStr.trim();
								System.out.println("服务器回复 : " + inputStr);
								//结果本次循环
							}
						} catch (IOException e) {
							//e.printStackTrace();
							System.out.println(" 服务器推动连接,程序即将退出 ");
							System.exit(0);
						}
					}
				}.start();*/
				/*while ((inputStr = reader.readLine()) != null){
					inputStr = inputStr.trim();
					System.out.println("服务器回复 : " + inputStr);
					//结果本次循环
					break;
				}*/
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(" msg : " + e.getMessage());
		}
	}
}
