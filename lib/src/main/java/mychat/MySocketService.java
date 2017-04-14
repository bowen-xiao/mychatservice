package mychat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static mychat.MyClient.textEncode;

/**
 * Created by 肖稳华 on 2017/4/7.
 */

public class MySocketService {

	public static int port = 9632;
	private ServerSocket socket;
	public static List<Socket> clients = new ArrayList<>();

	//最大连接人数
	private static int count = 25630;
	private Lock lock;

	public void severStart() {
		lock = new ReentrantLock();
		try {
			socket = new ServerSocket(port);

			System.out.println ("==============服务器启动=================");
			while (count > 0){

				count--;
				final Socket accept = socket.accept();
				clients.add(accept);
				System.out.println ( accept + " 进入,当前聊天室人数 :  " + clients.size() );

				//用于输入数据
				new Thread(){
					@Override
					public void run() {
						byte[] bytes = new byte[100];
						while (true){
							try {
								System.in.read(bytes);
								String inputStr = new String(bytes);
								inputStr = inputStr.trim();
								sendAll(inputStr);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}.start();

				new Thread(){
					@Override
					public void run() {
						try {
							InputStreamReader inSR = new InputStreamReader(accept.getInputStream(), "UTF-8");
							BufferedReader br = new BufferedReader(inSR);

							DataOutputStream out = new DataOutputStream(accept.getOutputStream());
							BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, textEncode));

							String inputStr = null;
							while ((inputStr = br.readLine()) != null){
								lock.lock();
								System.out.println( accept + " : " + inputStr);
								sendAll( " From : " + accept + "  : " + inputStr);
								lock.unlock();
//								writer.write(inputStr + "\r\n");
//								writer.flush();
							}
						} catch (IOException e) {
							lock.lock();
//							e.printStackTrace();
							//客户端连接失败
							clients.remove(accept);
							count++;
							System.out.println(accept + "退出；当前聊天室人数 : " + clients.size());
							lock.unlock();
						}
					}
				}.start();

			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("服务器关闭");
		}
	}

	public static void sendAll(String msg){
		for (int i = 0; i < clients.size(); i++) {
			Socket socket = clients.get(i);
			try {
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, textEncode));
				writer.write(msg + "\r\n");
				writer.flush();
			} catch (IOException e) {
				//e.printStackTrace();
				clients.remove(socket);
				System.out.println(socket + " : 失去连接 , 当前聊天室人数 :" + clients.size());
			}
		}
	}




	public static void main(String args[]){
		//开始接受请求
		new MySocketService().severStart();
	}


}
