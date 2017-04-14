package chat;

/**
 * Created by xiao on 2017/4/7.
 */

import java.io.*;
import java.net.*;

public class SocketServerEx1 {
	public static void main(String args[]) {
		System.out.println("Server");

		ServerSocket serverSocket = null;
		int connects = 0;
		try {
			serverSocket = new ServerSocket(8696, 8993);		// 服务器端口号，最大连接数据

			//最多连接次数
			while(connects < 3) {
				connects++;
				System.out.println("--------------------等待连接--------------------------");
				final Socket clientSocket = serverSocket.accept();	//等待连接
				System.out.println("第 " + connects + " 连接");
				new Thread(){
					@Override
					public void run() {
						try {
							ServiceClient(clientSocket);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}.start();
			}

			serverSocket.close();
		} catch(IOException ioe) {
			System.out.println("service Error: " + ioe);
		}
	}

	public static void ServiceClient(Socket client) throws IOException {
		System.out.println("已连接");

		InputStreamReader inSR = null;
		OutputStreamWriter outSW = null;
		try {
			//读取数据
			inSR = new InputStreamReader(client.getInputStream(), "UTF-8");
			BufferedReader br = new BufferedReader(inSR);

			outSW = new OutputStreamWriter(client.getOutputStream(), "UTF-8");
			BufferedWriter bw = new BufferedWriter(outSW);

			String str = "";
			while((str = br.readLine()) != null) {
				str = str.trim();
				System.out.println("收到客户端面消息 : " + str);

				bw.write("服务器回复消息 :  " + str + " \r\n");	//向客户端反馈消息，加上分行符以便客户端接
				bw.flush();
			}

		} finally {
			//System.out.println("Cleaning up connection: " + client);
			inSR.close();
			outSW.close();
			client.close();
		}
		System.out.println("已断开");
	}
}
