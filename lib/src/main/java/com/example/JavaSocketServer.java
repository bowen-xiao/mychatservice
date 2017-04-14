package com.example;

/**JavaSocketServer
 * Created by 肖稳华 on 2017/4/7.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 本程序实现了一个TCP程序的服务器编程部分。 使用Socket套接字进行编程，完成的是基于TCP可靠服务实现与客户端的双通信。 客户端的编程见本包中的类Client
 *
 * @author HAN
 */

public class JavaSocketServer {

	//Size of receive buffer
	private static final int BUFSIZE=1024;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int servPort = 8689;

		//Create a server socket to accept client connection request
		ServerSocket servSocket =null;
		int recvMsgSize=0;
		byte[] receivBuf=new byte[BUFSIZE];

		try {
			servSocket=new ServerSocket(servPort);
			while(true){
				Socket clientSocket=servSocket.accept();
				SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
				System.out.println("Handling client at "+ clientAddress);

				InputStream in =clientSocket.getInputStream();
				OutputStream out= clientSocket.getOutputStream();

				//receive until client close connection,indicate by -l return
				while((recvMsgSize=in.read(receivBuf))!=-1){
					String receivedData=new String(receivBuf);
					System.out.println(receivedData);
					out.write(receivBuf, 0, recvMsgSize);
				}
				clientSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}