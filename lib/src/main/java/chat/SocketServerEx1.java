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
			serverSocket = new ServerSocket(8696, 8993);		// �������˿ںţ������������

			//������Ӵ���
			while(connects < 3) {
				connects++;
				System.out.println("--------------------�ȴ�����--------------------------");
				final Socket clientSocket = serverSocket.accept();	//�ȴ�����
				System.out.println("�� " + connects + " ����");
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
		System.out.println("������");

		InputStreamReader inSR = null;
		OutputStreamWriter outSW = null;
		try {
			//��ȡ����
			inSR = new InputStreamReader(client.getInputStream(), "UTF-8");
			BufferedReader br = new BufferedReader(inSR);

			outSW = new OutputStreamWriter(client.getOutputStream(), "UTF-8");
			BufferedWriter bw = new BufferedWriter(outSW);

			String str = "";
			while((str = br.readLine()) != null) {
				str = str.trim();
				System.out.println("�յ��ͻ�������Ϣ : " + str);

				bw.write("�������ظ���Ϣ :  " + str + " \r\n");	//��ͻ��˷�����Ϣ�����Ϸ��з��Ա�ͻ��˽�
				bw.flush();
			}

		} finally {
			//System.out.println("Cleaning up connection: " + client);
			inSR.close();
			outSW.close();
			client.close();
		}
		System.out.println("�ѶϿ�");
	}
}
