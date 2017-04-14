package thread2;

/**
 * Created by Фon 2017/4/11.
 */

public class MyThread {

	public static int count = 0;

	public static void main(String args[]){
		final ProducerConsumer consumer = new ProducerConsumer();

		addData(consumer);
		addData(consumer);
		getData(consumer);
		getData(consumer);
	}


	private static void addData(final ProducerConsumer consumer ){

		new Thread(){
			@Override
			public void run() {

				while (true){

					try {
						java.util.Random r=new java.util.Random();
						Thread.sleep(r.nextInt(200) + 100);
						count++;
						consumer.put(count);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	private static void getData(final ProducerConsumer consumer ){

		new Thread(){
			@Override
			public void run() {
				while (true){
					try {
						java.util.Random r=new java.util.Random();
						Thread.sleep(r.nextInt(100) + 200);
						consumer.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
