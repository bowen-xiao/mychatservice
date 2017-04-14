package com.example;

public class MyClass {

	/*//可以运行的
	public static void main(String args[]){
		String url = "http://192.168.13.93:8080/travelHttp/get?cont=3510&secretkey=7cQqDx8JHeqrbQLx0wD%2ByyZYX7hPr1l1c2xM6sdPtgU+KlZXpC2%2BS5ijup4PvoxNkIsjhzrOLtY=&cmd=SEND_SMS&type=1&mobile=17099946539";
		try {
			System.out.println(URLEncoder.encode(url, "utf-8"));
			System.out.println(URLEncoder.encode("7cQqDx8JHeqrbQLx0wD+y0b4IHlPxjBmAmoCsmJ0Qst+JK0z6XkdrnJ8G3vrOuLKkIsjhzrOLtY", "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String replace ="12/151/51/51";
		System.out.println(replace.replaceAll("\\/","|"));

	}*/
	public static void main(String args[]){
		/**Demo demo1 = new Demo("one");
		 Demo demo2 = new Demo("two");
		 demo1.start();
		 demo2.start(); */
		/*Demo demo = new Demo();
		Thread t1 = new Thread(demo,"one");
		Thread t2 = new Thread(demo,"two");
		Thread t3 = new Thread(demo,"three");
		Thread t4 = new Thread(demo,"four");
		t1.start();
		t2.start();
		t3.start();
		t4.start();*/
		/*float fValue = 1.9f;
		float nextUp = Math.nextUp(fValue);
		System.out.println("tep : " + nextUp);
		if(nextUp > fValue){

		}*/
		String url = ".dfdfsfs.dfsdfds.png";
		System.out.println("tep : " + url.replaceFirst(".","www:"));
//		url.replaceFirst()
		/*float tep = fValue % 1;
		if(tep < 0.5f && tep != 0.5f){
			tep = ((int)fValue / 1.0f) * 1.0f;
		}else{
			tep = ((int)fValue / 1.0f) + 0.5f;
		}
		System.out.println("tep : " + tep);*/
		/**
		 for(int i= 0 ; i< 40; i++){
		 System.out.println("mian : " + i);
		 }*/
	}
}

class Demo implements Runnable { //extends Thread{
	/**private String name;
	 Demo(String name){
	 //this.name = name;

	 }*/
	private  int tick = 100;
	Object obj = new Object();
	public void run(){
		while(true){
			//synchronized(obj)
			{
				if(tick > 0){
					try{
						Thread.sleep(5);
					}catch(Exception e){}
					System.out.println( Thread.currentThread().getName() + " : " + tick--);
				}
			}
		}

	}
}
