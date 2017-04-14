package thread2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Ф on 2017/4/11.
 */

public class ProducerConsumer {
	final Lock      lock     = new ReentrantLock();
	final Condition notFull  = lock.newCondition();
	final Condition notEmpty = lock.newCondition();
	final Object[] items = new Object[100];
	int putptr, takeptr, count;

	//����
	public void put(Object x) throws InterruptedException {
		lock.lock();
		try {
			while (count == items.length){
				notFull.await();
			}
			System.err.println(Thread.currentThread().getName() + " :: +++++++++++������� : "  + x);
			items[putptr] = x;
			if (++putptr == items.length) putptr = 0;
			++count;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	//����
	public Object take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0){
				notEmpty.await();
			}
			Object x = items[takeptr];
			if (++takeptr == items.length) takeptr = 0;
			--count;
			System.out.println(Thread.currentThread().getName() + " ::----------��ȡ���� : "  + x);
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}




}
