package ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 肖稳华 on 2017/4/11.
 */

public class PingIps {

	public static void main(String args[]){

		String command = "net view";
		Runtime r = Runtime.getRuntime();
		Process p = null;
		try {
			p = r.exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

		getHostnames(getIPs());
	}

	public static Map<String,String> getHostnames(List<String> ips){

		Map<String,String> map = new HashMap<String,String>();
		System.out.println("正在提取hostname...");
		for(String ip : ips){
			String command = "ping -a " + ip;
			Runtime r = Runtime.getRuntime();
			Process p;
			try {
				p = r.exec(command);
				BufferedReader br = new BufferedReader(new InputStreamReader(p
																				 .getInputStream()));
				String inline;
				while ((inline = br.readLine()) != null) {
					if(inline.indexOf("[") > -1){
						int start = inline.indexOf("Ping ");
						int end = inline.indexOf("[");
						String hostname = inline.substring(start+"Ping ".length(),end-1);
						System.out.println(hostname);
						map.put(ip,hostname);
					}
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("提取结束！");
		return map;
	}

	public static List<String>  getIPs()
	{
		List<String> list = new ArrayList<String>();
		boolean flag = false;
		int count=0;
		Runtime r = Runtime.getRuntime();
		Process p;
		try {
			p = r.exec("arp -a");
			BufferedReader br = new BufferedReader(new InputStreamReader(p
																			 .getInputStream()));
			String inline;
			while ((inline = br.readLine()) != null) {
				if(inline.indexOf("接口") > -1){
					flag = !flag;
					if(!flag){
						//碰到下一个"接口"退出循环
						break;
					}
				}
				if(flag){
					count++;
					if(count > 2){
						//有效IP
						String[] str=inline.split(" {4}");
						list.add(str[0]);
					}
				}
				System.out.println(inline);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(list);
		return list;
	}
}
