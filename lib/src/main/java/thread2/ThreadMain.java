package thread2;

/**
 * Created by 肖稳华 on 2017/4/11.
 */

public class ThreadMain {
	public static void main(String args[]){
		final BoundedBuffer buffer = new BoundedBuffer();
		new Thread(){
			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					try {
						Thread.sleep(200L);
						buffer.put(i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

		new Thread(){
			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					try {
						Thread.sleep(200L);
						buffer.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}



}
