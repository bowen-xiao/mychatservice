package chat;

/**
 * Created by  on 2017/4/7.
 */

import java.io.*;
import java.net.*;

public class SocketClientEx1 {
	public static void main(String[] args) {
		System.out.println("Client");
		try {
			Socket clientSocket = new Socket("localhost", 8696);
			System.out.println("Client1:" + clientSocket);

			DataInputStream dataIS = new DataInputStream(clientSocket.getInputStream());
			InputStreamReader inSR = new InputStreamReader(dataIS, "UTF-8");
			BufferedReader br = new BufferedReader(inSR);

			DataOutputStream dataOS = new DataOutputStream(clientSocket.getOutputStream());
			OutputStreamWriter outSW = new OutputStreamWriter(dataOS, "UTF-8");
			BufferedWriter bw = new BufferedWriter(outSW);

			//杈撳叆淇℃伅
			byte bytes[] = new byte[100];
			while(true) {
				System.out.println("----------------------------------");
				System.in.read(bytes);
				String str = new String(bytes);
				str = str.trim();
				if (str == "exit") {
					break;
				}


				bw.write(str + "\r\n");
				bw.flush();


				//发送数据
				while((str = br.readLine()) != null) {
					str = str.trim();
					System.out.println("加上分行符" + str);
					break;
				}

			}

			inSR.close();
			dataIS.close();
			dataOS.close();
			clientSocket.close();
		} catch(UnknownHostException uhe) {
			System.out.println("Error:" + uhe.getMessage());
		} catch(ConnectException ce) {
			System.out.println("Error:" + ce.getMessage());
		} catch(IOException ioe) {
			System.out.println("Error:" + ioe.getMessage());
		} finally {
		}
	}
}
