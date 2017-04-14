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
			System.out.println(socket + " : ���ӳɹ� " );

			//����������ͳ�����
			input = new DataInputStream(socket.getInputStream());
			final BufferedReader reader = new BufferedReader(new InputStreamReader(input,textEncode));

			//��������д������,??Ҫ��??
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
								System.out.println("�������ظ� : " + inputStr + "");
								//�������ѭ��
								break;
							}
						}
					} catch (IOException e) {
						//e.printStackTrace();
						System.out.println(" ����������ʧ��,���򼴽��˳� ");
						System.exit(0);
					}
				}
			}.start();

			byte[] bytes = new byte[1024];
			while (true){
				System.out.println("-----------������Ҫ��������-------------");
				System.in.read(bytes);
				String inputStr = new String(bytes);
				inputStr = inputStr.trim();
				if(inputStr.contains("exit")){
					//�˳�ϵͳ
					exit(0);
					socket.close();
					return;
				}
				// rn �������
				writer.write(inputStr + "\r\n");
				writer.flush();
				//��ȡ��������д����Ϣ
				/*new Thread(){
					@Override
					public void run() {
						String inputStr = null;
						try {
							while ((inputStr = reader.readLine()) != null){
								inputStr = inputStr.trim();
								System.out.println("�������ظ� : " + inputStr);
								//�������ѭ��
							}
						} catch (IOException e) {
							//e.printStackTrace();
							System.out.println(" �������ƶ�����,���򼴽��˳� ");
							System.exit(0);
						}
					}
				}.start();*/
				/*while ((inputStr = reader.readLine()) != null){
					inputStr = inputStr.trim();
					System.out.println("�������ظ� : " + inputStr);
					//�������ѭ��
					break;
				}*/
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(" msg : " + e.getMessage());
		}
	}
}
