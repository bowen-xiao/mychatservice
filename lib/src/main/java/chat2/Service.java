package chat2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by  肖 on 2017/4/10.
 */

public class Service {

	public static int port = 9632;

	public static List<Socket> clients = new ArrayList<>();

	public static void main(String args[]) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("--------------服务已经启动----------------");
		while (true) {
			try {
				final Socket client = serverSocket.accept();
				clients.add(client);
				System.out.println(client + " 进入聊天室 ，当前人数 " + clients.size());
				final InputStream inStream = client.getInputStream();
				final OutputStream outStream = client.getOutputStream();
				new Thread() {
					@Override
					public void run() {
						try {
							byte[] bytes = new byte[2048];
							StringBuilder stringBuilder = new StringBuilder();
							int readBytes = 0;
							//这里是一个阻塞的方法
							while ((readBytes = inStream.read(bytes)) > 0) {
								stringBuilder.append(new String(bytes, 0, readBytes));
								//去掉换行
								String tep = stringBuilder.toString().trim();//stringBuilder.reverse().toString().trim();
								//必须有数据，连接的时候会收到一个空的包
								if (tep != null && tep.length() > 0) {
									//有些客户端是用  readLine 所以必须加一个换行符
									//									outStream.write((tep + "\r\n").getBytes());
									//									outStream.flush();
									sendAll(tep);
									System.out.println(
										client.getInetAddress() + ":" + client.getPort() + " say : " + stringBuilder.toString());
									//将数据清空
									stringBuilder.setLength(0);
								}
							}
						} catch (IOException e) {
							clients.remove(client);
							System.out.println(client + " 失去联系，当前人数 " + clients.size());
						}
					}
				}.start();
			} catch (IOException e) {
				//e.printStackTrace();
				//				clients.remove(client);
				//				System.out.println( client + " 退出聊天室 ，当前人数 " + clients.size());
			}
		}
	}


	private static void sendAll(final String tep) {

		for (int i = 0; i < clients.size(); i++) {
			Socket client = null;
			try {
				client = clients.get(i);
				final OutputStream outStream = client.getOutputStream();
				outStream.write((tep + "\r\n").getBytes());
				outStream.flush();
			} catch (IOException e) {
				//e.printStackTrace();
				clients.remove(client);
				System.out.println(client + " 失去联系，当前人数 " + clients.size());
			}
		}
	}

}
