package mychat;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static mychat.MyClient.hostIp;

/**
 * Created by肖 on 2017/4/11.
 */

public class MoreClient {

	private static       Lock         lock            = new ReentrantLock();
	public               int          i               = 0;
	static               List<Socket> sockets         = new ArrayList<>();
	private final static Condition    addCondition    = lock.newCondition();
	private final static Condition    removeCondition = lock.newCondition();

	public static void main(String args[]){
		MoreClient client = new MoreClient();

		client.startThread();
		client.startThread();
		client.startThread();
		client.remove();
		client.remove();
	}

	private  void remove(){
		new Thread(){
			@Override
			public void run() {
				try {
					while (true){
						lock.lock();
						if(i > 1){
							Socket socket = sockets.get(0);
							socket.close();
							sockets.remove(socket);
							System.out.println(socket + "退出 ,当前连接总数 : " + sockets.size());
							Thread.sleep(650L);
							i--;
						}
						lock.unlock();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private  void startThread(){
		new Thread(){
			@Override
			public void run() {
				initMoreClient();
			}
		}.start();
	}

	private void initMoreClient(){
		try {
			while ( i < 10) {
				lock.lock();
				if(i < 8){
					Socket socket = new Socket(hostIp, MySocketService.port);
					sockets.add(socket);
					i++;
					socket.getOutputStream().write(("my is "+ i  + "\r\n").getBytes());
					socket.getOutputStream().flush();
					System.out.println(i + "加入聊天室");
					Thread.sleep(600L);
				}
				lock.unlock();

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
