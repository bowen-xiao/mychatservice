package com.example;

/**
 * Created by 肖稳华 on 2017/3/17.
 */

public class ColorAlphaUtils {

	public static void main(String args[]){

		for (int i = 0; i < 100; i++) {
			float percent = i * 0.01f;
			String hexAlpha = getAlphaByPercent(percent);
			System.out.println(String.format("%d%% | %s", i, hexAlpha));
		}
	}

	//根据透明度取值 16进制的值
	private static String getAlphaByPercent(float percent){
		int hexValue = Math.round(percent * 1.0f * 255);
		//转化成大写
		String result = Integer.toHexString(hexValue).toUpperCase();
		//不足2位需要补0
		if (result.length() == 1) result = "0" + result;
		return result;
	}

}
