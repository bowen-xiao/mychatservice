package chat2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by  Ф on 2017/4/10.
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

		System.out.println("--------------�����Ѿ�����----------------");
		while (true) {
			try {
				final Socket client = serverSocket.accept();
				clients.add(client);
				System.out.println(client + " ���������� ����ǰ���� " + clients.size());
				final InputStream inStream = client.getInputStream();
				final OutputStream outStream = client.getOutputStream();
				new Thread() {
					@Override
					public void run() {
						try {
							byte[] bytes = new byte[2048];
							StringBuilder stringBuilder = new StringBuilder();
							int readBytes = 0;
							//������һ�������ķ���
							while ((readBytes = inStream.read(bytes)) > 0) {
								stringBuilder.append(new String(bytes, 0, readBytes));
								//ȥ������
								String tep = stringBuilder.toString().trim();//stringBuilder.reverse().toString().trim();
								//���������ݣ����ӵ�ʱ����յ�һ���յİ�
								if (tep != null && tep.length() > 0) {
									//��Щ�ͻ�������  readLine ���Ա����һ�����з�
									//									outStream.write((tep + "\r\n").getBytes());
									//									outStream.flush();
									sendAll(tep);
									System.out.println(
										client.getInetAddress() + ":" + client.getPort() + " say : " + stringBuilder.toString());
									//���������
									stringBuilder.setLength(0);
								}
							}
						} catch (IOException e) {
							clients.remove(client);
							System.out.println(client + " ʧȥ��ϵ����ǰ���� " + clients.size());
						}
					}
				}.start();
			} catch (IOException e) {
				//e.printStackTrace();
				//				clients.remove(client);
				//				System.out.println( client + " �˳������� ����ǰ���� " + clients.size());
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
				System.out.println(client + " ʧȥ��ϵ����ǰ���� " + clients.size());
			}
		}
	}

}
