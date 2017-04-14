package chatroom;

/**
 * Created by 肖稳华 on 2017/4/7.
 */
import java.net.Socket;
import java.util.Scanner;

/**
 * 客户端
 * @author Administrator
 *
 */
public class Client {

	private int port = 9000;
	private String ip="127.0.0.1";
	private static Socket socket;
	private String cliName;
	public Client(){
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() throws Exception {

		System.out.println("-----客户端已开启-----");
		System.out.println("请输入客户端名字：");
		Scanner sc = new Scanner(System.in);
		cliName = sc.next();
		socket = new Socket(ip,port);
	}

	public void hands() throws Exception{
		Thread threadReader = new Thread(new ThreadReader(socket.getInputStream()),Thread.currentThread().getName());
		Thread threadWriter = new Thread(new ThreadWriter(socket.getOutputStream()));
		threadWriter.setName(cliName);
		threadReader.start();
		threadWriter.start();

	}

	public static void main(String[] args) throws Exception  {
		Client client = new Client();
		client.hands();
	}

}