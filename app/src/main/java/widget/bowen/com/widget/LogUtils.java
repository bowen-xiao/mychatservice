package widget.bowen.com.widget;

import android.util.Log;

/**
 * Created by 肖稳华 on 2017/2/10.
 */

public class LogUtils {

	public static void d(String logMsg){
		Log.e(Thread.currentThread().getName() , logMsg);

		Thread current = Thread.currentThread();
		System.out.println(current.getPriority());
		System.out.println(current.getName());
		System.out.println(current.activeCount());
		System.out.println(current.getId());
		System.out.println(current.getThreadGroup());
		System.out.println(current.getStackTrace());
		System.out.println(current.hashCode());
		System.out.println(current.toString());
		//打印详细信息
		System.out.println(getTraceInfo());

		String Method = Thread.currentThread().getStackTrace()[1].getClassName();
		System.out.println(Method);

		L.e("带详情信息的Log");
	}

	public static String getTraceInfo(){
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		int index = 4;
		String className = stackTrace[index].getFileName();
		String methodName = stackTrace[index].getMethodName();
		int lineNumber = stackTrace[index].getLineNumber();

		return className + " : className ;" + methodName + " : methodName ; " + lineNumber + " : lineNumber;" + stackTrace[index].getClassName();
	}

}
